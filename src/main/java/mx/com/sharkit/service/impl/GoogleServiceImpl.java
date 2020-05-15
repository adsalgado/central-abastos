package mx.com.sharkit.service.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;

import mx.com.sharkit.service.GoogleService;

@Service
public class GoogleServiceImpl implements GoogleService {

	private final Logger log = LoggerFactory.getLogger(GoogleServiceImpl.class);

	@Value("${api.key.google}")
	private String apiKey;

	private GeoApiContext context;

	@PostConstruct
	public void init() {
		context = new GeoApiContext().setApiKey(apiKey);
	}

	@Override
	public DistanceMatrix calculaDistancia(String direccion1, String direccion2) {

		try {
			return DistanceMatrixApi
					.getDistanceMatrix(context, new String[] { direccion1 }, new String[] { direccion2 }).await();
		} catch (ApiException | InterruptedException | IOException e) {
			log.error("Exception: {}", e);
		}

		return null;

	}

	@Override
	public Long getDistanciaKilometros(String direccion1, String direccion2) {
		Long distanceInKm = null;
		DistanceMatrix matrix = calculaDistancia(direccion1, direccion2);
		if (matrix != null && matrix.rows.length > 0 && matrix.rows[0].elements.length > 0 ) {
            long distanceInMeters = matrix.rows[0].elements[0].distance.inMeters; 
            distanceInKm = distanceInMeters/1000;
            log.debug("distanceInMeters {}", distanceInMeters);
        }
		return distanceInKm;
	}

}
