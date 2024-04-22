package com.hopin.HopIn.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.services.interfaces.IRideEstimationService;
import com.hopin.HopIn.services.interfaces.IRideService;

@Service
public class RideEstimationService implements IRideEstimationService{
	
	
	@Override
	public int getEstimatedTime(LocationNoIdDTO departureLocation, LocationNoIdDTO destinationLocation) {
		String location11 = departureLocation.getLatitudeString();
		String location12 = departureLocation.getLongitudeString();
		String location21 = destinationLocation.getLatitudeString();
		String location22 = destinationLocation.getLongitudeString();
		String url = setDistanceMatrixUrl(location11, location12, location21, location22);
		
		String durationText = getEstimatedInfo(url, "duration");
		System.out.println(durationText);
		
		return Integer.parseInt(durationText.split(" ")[0]);
	}
	
	@Override
	public double getEstimatedDistance(LocationNoIdDTO departureLocation, LocationNoIdDTO destinationLocation) {
		String location11 = departureLocation.getLatitudeString();
		String location12 = departureLocation.getLongitudeString();
		String location21 = destinationLocation.getLatitudeString();
		String location22 = destinationLocation.getLongitudeString();
		String url = setDistanceMatrixUrl(location11, location12, location21, location22);
		
		String distanceText = getEstimatedInfo(url, "distance");
		System.out.println(distanceText);
		return Double.parseDouble(distanceText.split(" ")[0]);
	}

	private String getEstimatedInfo(String url, String query) {
		HttpRequest r = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
		HttpClient client = HttpClient.newBuilder().build();
		String response;
		String queryResponse = null;
		try {
			response = client.send(r, HttpResponse.BodyHandlers.ofString()).body();
			System.out.println(response); 
			JSONObject obj = new JSONObject(response);
			JSONArray rows = obj.getJSONArray("rows");
			JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
			if (query == "duration") {
				JSONObject duration = elements.getJSONObject(0).getJSONObject("duration");
				queryResponse = duration.getString("text");
			} else if (query == "distance") {
				JSONObject distance = elements.getJSONObject(0).getJSONObject("distance");
				queryResponse = distance.getString("text");
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return queryResponse;
	}

	
	private String setDistanceMatrixUrl(String firstLocationLat, String firstLocationLng, String secondLocationLat, String secondLocationLng) {
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
				firstLocationLat + "," + firstLocationLng +
		"&destinations=" + 
		secondLocationLat + "," + secondLocationLng + 
		"&units=metric&key=AIzaSyADf7wmEupGmb08OGVJR1eNhvtvF6KYuiM";
		return url;
	}
	
	@Override
	public Driver getDriverClosestToDeparture(RideDTO dto, List<Driver> drivers) {
		String departureLocationLat = dto.getDepartureLocationLat();
		String departureLocationLng = dto.getDepartureLocationLng();
		
		double closestDistance = Integer.MAX_VALUE;
		Driver closestDriver = null;
		
		for (Driver driver: drivers) {
			String driverLocationLat = driver.getVehicleLocationLat();
			String driverLocationLng = driver.getVehicleLocationLng();
			
			String url = this.setDistanceMatrixUrl(departureLocationLat, departureLocationLng, driverLocationLat, driverLocationLng);
			String distanceText = this.getEstimatedInfo(url, "distance");
			double distance = Double.parseDouble(distanceText.split(" ")[0]);
			
			if (distance < closestDistance) {
				closestDistance = distance;
				closestDriver = driver;
			}
		}
		return closestDriver;
	}

	@Override
	public int getEstimatedTimeForVehicleLocation(LocationNoIdDTO departureLocation, Vehicle vehicle) {
		return this.getEstimatedTime(departureLocation, new LocationNoIdDTO(vehicle.getCurrentLocation()));
	}
}
