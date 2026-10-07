package com.mthree.academy.c458.team1.food_diary_manager.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.APIException;
import com.mthree.academy.c458.team1.food_diary_manager.exceptions.FoodNotFoundException;
import com.mthree.academy.c458.team1.food_diary_manager.models.Food;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OpenFoodFactsAPI implements FoodAPI {

	private final String openFoodFactsAPIUrl = "https://world.openfoodfacts.net/api/v2/product/";

	@Override
	public Food searchFoodByBarcode(String barcode)
			throws FoodNotFoundException, APIException {

		String api = "https://world.openfoodfacts.org/api/v2/product/"
				+ barcode
				+ "?fields=product_name,code,nutriscore_data,nutriments";

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(api))
				.GET()
				.build();

		try {
			HttpResponse<String> response =
					client.send(request, HttpResponse.BodyHandlers.ofString());

			String responseBody = response.body();

			// Check HTTP response status
			if (response.statusCode() != 200) {

				if (response.statusCode() == 404) {
					throw new FoodNotFoundException(
							"Food not found for barcode: " + barcode
					);
				}

				throw new APIException(
						"API request failed with status code: "
								+ response.statusCode()
				);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(responseBody);

			// Check that a product was actually found
			if (!root.has("product") || root.get("product").isNull()) {
				throw new FoodNotFoundException(
						"Food not found for barcode: " + barcode
				);
			}

			JsonNode product = root.path("product");

			// Get product name
			String productName = product
					.path("product_name")
					.asText();

			// Get nutritional information
			JsonNode nutriments = product.path("nutriments");
			double calories = nutriments
					.path("energy-kcal_prepared_100g")
					.asDouble(0);

			if (calories == 0.0) {
				calories = nutriments
						.path("energy-kcal_100g")
						.asDouble(0);
			}

			double protein = nutriments
					.path("proteins_prepared_100g")
					.asDouble(0);

			if (protein == 0.0) {
				protein = nutriments
						.path("proteins_100g")
						.asDouble(0);
			}

			double fat = nutriments
					.path("fat_prepared_100g")
					.asDouble(0);

			if (fat == 0.0) {
				fat = nutriments
						.path("fat_100g")
						.asDouble(0);
			}

			double carbohydrates = nutriments
					.path("carbohydrates_prepared_100g")
					.asDouble(0);

			if (carbohydrates == 0.0) {
				carbohydrates = nutriments
						.path("carbohydrates_100g")
						.asDouble(0);
			}

			double sugars = nutriments
					.path("sugars_prepared_100g")
					.asDouble(0);

			if (sugars == 0.0) {
				sugars = nutriments
						.path("sugars_100g")
						.asDouble(0);
			}

			double fibre = nutriments
					.path("fiber_prepared_100g")
					.asDouble(0);

			if (fibre == 0.0) {
				fibre = nutriments
						.path("fiber_100g")
						.asDouble(0);
			}

			double salt = nutriments
					.path("salt_prepared_100g")
					.asDouble(0);

			if (salt == 0.0) {
				salt = nutriments
						.path("salt_100g")
						.asDouble(0);
			}
			// Create Food object
			Food food = new Food();

			food.setBarcode(barcode);
			food.setName(productName);

			food.setCalories(calories);
			food.setProtein(protein);
			food.setFat(fat);
			food.setCarbohydrates(carbohydrates);
			food.setSugars(sugars);
			food.setFibre(fibre);
			food.setSalt(salt);

			return food;

		} catch (JsonProcessingException e) {
			throw new APIException("Could not parse API response");

		} catch (IOException e) {
			throw new APIException("Could not communicate with API");

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new APIException("API request was interrupted");
		}
	}

	@Override
	public String getBarcodeUsingName(String foodName) throws FoodNotFoundException, APIException {

		String api = "https://world.openfoodfacts.org/cgi/search.pl"
				+ "?search_terms=" + foodName
				+ "&search_simple=1"
				+ "&action=process"
				+ "&json=1"
				+ "&fields=product_name,code";

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(api))
				.GET()
				.build();

		try {
			HttpResponse<String> response =
					client.send(request, HttpResponse.BodyHandlers.ofString());

			ObjectMapper objectMapper = new ObjectMapper();
			if (response.statusCode() != 200) {
				throw new APIException("API is unavailable");
			}

			JsonNode root = objectMapper.readTree(response.body());
			JsonNode products = root.get("products");
			if (products == null || !products.isArray()) {
				throw new APIException("API returned no products");
			}

			for (JsonNode product : products) {
				if (product.size()!=2) {
					continue;
				}

				String productName = product.get("product_name").asText();
				String barcode = product.get("code").asText();
				if (productName.equalsIgnoreCase(foodName)) {

					return barcode;
				}
			}
			throw new FoodNotFoundException("Food not found by search");
		} catch (JsonParseException e) {
			throw new APIException("Could not parse json");
		} catch (IOException e) {
			throw new APIException("Could not communicate with API");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
