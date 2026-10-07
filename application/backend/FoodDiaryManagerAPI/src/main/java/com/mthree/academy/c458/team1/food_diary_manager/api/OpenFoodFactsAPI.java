package com.mthree.academy.c458.team1.food_diary_manager.api;

import com.fasterxml.jackson.core.JsonParseException;
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
	public Food searchFoodByBarcode(String barcode) throws FoodNotFoundException {

		String api = "https://world.openfoodfacts.org/api/v2/product/"
				+ barcode
				+ "?fields=product_name,code,nutriscore_data";

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(api))
				.GET()
				.build();

		try {
			HttpResponse<String> response =
					client.send(request, HttpResponse.BodyHandlers.ofString());

			String responseBody = response.body();
			System.out.println(responseBody);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(responseBody);

			// Check that a product was actually found
			if (!root.has("product") || root.get("product").isNull()) {
				throw new FoodNotFoundException(
						"Food not found for barcode: " + barcode
				);
			}

			// Get barcode
			long id_number = 0L;
			String id = root.get("code").asText();

			try {
				id_number = Long.parseLong(id);
			} catch (NumberFormatException e) {
				System.out.println("Invalid barcode: " + id);
			}

			// Get product name
			String productName = root
					.get("product")
					.get("product_name")
					.asText();

			double salt = 0;

			// Get salt information if it exists
			JsonNode negative = root
					.get("product")
					.path("nutriscore_data")
					.path("components")
					.path("negative");

			if (negative.isArray()) {
				for (JsonNode nutrient : negative) {

					if ("salt".equals(nutrient.path("id").asText())) {
						salt = nutrient.path("value").asDouble();
					}
				}
			}
			Food food = new Food();
			food.setBarcode(barcode);
			food.setName(productName);
			food.setSalt(salt);
			return food;

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
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

			JsonNode root = objectMapper.readTree(response.body());
			JsonNode products = root.get("products");
			if (products == null || !products.isArray()) {
				throw new APIException("API returned no products");
			}

			for (JsonNode product : products) {

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
