package mx.com.sharkit.service;

import com.google.maps.model.DistanceMatrix;

public interface GoogleService {
	
	DistanceMatrix calculaDistancia(String direccion1, String direccion2);

	Long getDistanciaKilometros(String direccion1, String direccion2);

}
