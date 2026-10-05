package com.mthree.academy.c458.team1.food_diary_manager.api;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OpenFoodFactsAPI implements FoodAPI{

	private String openFoodFactsAPIUrl = "https://world.openfoodfacts.net/api/v2/product/";

	@Override
	public String searchFoodByName(String foodName) {

		return "";
	}

	@Override
	public String searchFoodByBarcode(String barcode) {
		String api = openFoodFactsAPIUrl + barcode + "?fields=product_name,nutriscore_data";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(api))
				.GET()
				.build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return response.body();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
