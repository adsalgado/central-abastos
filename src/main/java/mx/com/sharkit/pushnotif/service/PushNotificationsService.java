package mx.com.sharkit.pushnotif.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.sharkit.domain.TipoUsuario;

@Service
public class PushNotificationsService {

	private final Logger log = LoggerFactory.getLogger(PushNotificationsService.class);

	private static final String FIREBASE_SERVER_KEY_CLIENTE_ANDROID = "AAAAKsiEY9M:APA91bF7qUMreQg_e9awGaJ_hVT8OI55FEXLEp0U71UE2bvgJBs9pZpQVItNKa7kxMrF_09EmAnIxfzm-oR9eoZKueVfn5WC2j8ysVo7ZLq4D1lieIetwRMPfaHM-xWZSBBVlPArYYLl";
//	private static final String FIREBASE_SERVER_KEY_CLIENTE_ANDROID = "AIzaSyCA4iHXRLkp6uYcguXjdCyLkamJ6RgD9P4";
	private static final String FIREBASE_SERVER_KEY_CLIENTE_IOS = "AIzaSyBQc4pUXm9D7T6tt5X3_CMcex7Ws4rurB0";

	private static final String FIREBASE_SERVER_KEY_PROVEEDOR_ANDROID = "AIzaSyDFhkAV8mw8GOqbHOKX5DRvri8RrMz6XkM";
	private static final String FIREBASE_SERVER_KEY_PROVEEDOR_IOS = "AIzaSyAggkHkTPFTHnRM3Yju_pSESwYZIcokZDU";

	private static final String FIREBASE_SERVER_KEY_TRANSPORTISTA_ANDROID = "AIzaSyB7Sr7KtiCGtjkmKl_PLQEUN2MDUeDwYFE";
	private static final String FIREBASE_SERVER_KEY_TRANSPORTISTA_IOS = "AIzaSyBcH69SA5lJb7kexuVixaUDlfofzOk73Fg";

	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity, Long tipoUsuario) {

		RestTemplate restTemplate = new RestTemplate();

		String fireBaseKeyAndroid = "";
		String fireBaseKeyIOS = "";
		
		if (TipoUsuario.CLIENTE.equals(tipoUsuario)) {
			fireBaseKeyAndroid = FIREBASE_SERVER_KEY_CLIENTE_ANDROID;
			fireBaseKeyIOS = FIREBASE_SERVER_KEY_CLIENTE_IOS;

		} else if (TipoUsuario.PROVEEDOR.equals(tipoUsuario)) {
			fireBaseKeyAndroid = FIREBASE_SERVER_KEY_PROVEEDOR_ANDROID;
			fireBaseKeyIOS = FIREBASE_SERVER_KEY_PROVEEDOR_IOS;
			
		} else if (TipoUsuario.TRANSPORTISTA.equals(tipoUsuario)) {
			fireBaseKeyAndroid = FIREBASE_SERVER_KEY_TRANSPORTISTA_ANDROID;
			fireBaseKeyIOS = FIREBASE_SERVER_KEY_TRANSPORTISTA_IOS;
			
		}

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + fireBaseKeyAndroid));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		interceptors.add(new HeaderRequestInterceptor("project_id", "183752745939"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
		
	}

	public HttpEntity<String> createRequestNotification(String to, String notificationTitle, String notificationBody,
			String messageTitle, Integer messageView, Object messageData) throws JSONException {
		
		JSONObject body = new JSONObject();

		body.put("to", to);
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", notificationTitle);
		notification.put("body", notificationBody);
		notification.put("click_action", "FCM_PLUGIN_ACTIVITY");

		ObjectMapper obj = new ObjectMapper();
		String json = "";
		try {
			json = obj.writeValueAsString(messageData);
		} catch (JsonProcessingException e) {
			log.error("Error de conversion json. {}", e);
		}

		JSONObject data = new JSONObject();
		data.put("data", json);
		data.put("title", messageTitle);
		data.put("view", messageView);

		body.put("notification", notification);
		body.put("data", data);

		return new HttpEntity<>(body.toString());

	}

	
	public HttpEntity<String> createRequestNotification(String to, String notificationTitle, String notificationBody,
			String messageTitle, Integer messageView, Map<String, Object> mapData) throws JSONException {
		
		JSONObject body = new JSONObject();

		body.put("to", to);
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", notificationTitle);
		notification.put("body", notificationBody);
		notification.put("click_action", "FCM_PLUGIN_ACTIVITY");

		JSONObject data = new JSONObject();
		data.put("title", messageTitle);
		data.put("view", messageView);
		
		mapData.keySet().forEach(key -> {
			try {
				data.put(key, mapData.get(key));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});

		body.put("notification", notification);
		body.put("data", data);

		return new HttpEntity<>(body.toString());

	}

}
