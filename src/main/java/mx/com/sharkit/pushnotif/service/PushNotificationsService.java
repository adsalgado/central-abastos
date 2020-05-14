package mx.com.sharkit.pushnotif.service;

import java.util.ArrayList;
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

@Service
public class PushNotificationsService {

	private final Logger log = LoggerFactory.getLogger(PushNotificationsService.class);

	private static final String FIREBASE_SERVER_KEY_ANDROID = "AAAAKsiEY9M:APA91bF7qUMreQg_e9awGaJ_hVT8OI55FEXLEp0U71UE2bvgJBs9pZpQVItNKa7kxMrF_09EmAnIxfzm-oR9eoZKueVfn5WC2j8ysVo7ZLq4D1lieIetwRMPfaHM-xWZSBBVlPArYYLl";
	private static final String FIREBASE_SERVER_KEY_IOS_ = "AIzaSyBQc4pUXm9D7T6tt5X3_CMcex7Ws4rurB0";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		/**
		 * https://fcm.googleapis.com/fcm/send Content-Type:application/json
		 * Authorization:key=FIREBASE_SERVER_KEY
		 */

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY_ANDROID));
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

}
