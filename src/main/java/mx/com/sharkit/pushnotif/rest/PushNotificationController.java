package mx.com.sharkit.pushnotif.rest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.sharkit.pushnotif.service.AndroidPushNotificationsService;

@RestController
@RequestMapping("/api")
public class PushNotificationController {

	private final String TOPIC = "JavaSampleApproach";

	@Autowired
	private AndroidPushNotificationsService androidPushNotificationsService;

	@GetMapping("/push/send")
	public ResponseEntity<String> send() throws JSONException {

		JSONObject body = new JSONObject();
//		body.put("to", "/topics/" + TOPIC);
		body.put("to", "fEmlU4miqjQ:APA91bGVFDGwrH__e0DBr0965HSRfDaHKryoiDs1f3nyokcZk59dcOo1aOwv7Pr6qIS4Js773znhGjMi7YSKpMgiGB8hRhCm-WhL9TFEIlrHaXW912Ao2YYXt9XJwy3TPVCtZr8MC8gx");
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", "JSA Notification");
		notification.put("body", "Happy Message!");

		JSONObject data = new JSONObject();
		data.put("Key-1", "JSA Data 1");
		data.put("Key-2", "JSA Data 2");

		body.put("notification", notification);
		body.put("data", data);

		/**
		 * { "notification": { "title": "JSA Notification", "body": "Happy Message!" },
		 * "data": { "Key-1": "JSA Data 1", "Key-2": "JSA Data 2" }, "to":
		 * "/topics/JavaSampleApproach", "priority": "high" }
		 */

		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			String firebaseResponse = pushNotification.get();

			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}
	
}
