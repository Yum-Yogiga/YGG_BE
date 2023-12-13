package com.yogiga.yogiga.util.geocode;

import com.yogiga.yogiga.restaurant.entity.Restaurant;
import com.yogiga.yogiga.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GeocodingService {
    @Value("${X-NCP-APIGW-API-KEY-ID}")
    private String clientId; // 네이버 API Client ID
    @Value("${X-NCP-APIGW-API-KEY}")
    private String clientSecret; // 네이버 API Client Secret

    private final RestaurantRepository restaurantRepository;

    // Constructor with RestaurantRepository injection
    public GeocodingService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void updateRestaurantsWithCoordinates() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        restaurants.forEach(restaurant -> {
            LatLng latLng = getCoordinatesFromAddress(restaurant.getAddress());
            if (latLng != null) {
                restaurant.setLatitude(latLng.lat);
                restaurant.setLongitude(latLng.lng);
                restaurantRepository.save(restaurant);
            }
        });
    }

    private LatLng getCoordinatesFromAddress(String address) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", clientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", clientSecret)
                .build();

        Mono<GeocodeResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("query", address).build())
                .retrieve()
                .bodyToMono(GeocodeResponse.class);

        GeocodeResponse geocodeResponse = response.block(); // 비동기 호출을 동기화
        if (geocodeResponse != null && geocodeResponse.getStatus().equals("OK")) {
            List<GeocodeResponse.Address> addresses = geocodeResponse.getAddresses();
            if (!addresses.isEmpty()) {
                GeocodeResponse.Address addressInfo = addresses.get(0);
                return new LatLng(Double.parseDouble(addressInfo.getX()), Double.parseDouble(addressInfo.getY()));
            }
        }
        return null;
    }

    static class LatLng {
        double lat;
        double lng;

        public LatLng(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }
        // Getter, Setter 생략
    }
}

