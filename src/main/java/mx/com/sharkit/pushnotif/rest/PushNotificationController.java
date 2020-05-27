package mx.com.sharkit.pushnotif.rest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.sharkit.domain.TipoUsuario;
import mx.com.sharkit.pushnotif.service.PushNotificationsService;
import mx.com.sharkit.service.PedidoService;
import mx.com.sharkit.service.dto.PedidoDTO;

@RestController
@RequestMapping("/api")
public class PushNotificationController {
	
	private final Logger log = LoggerFactory.getLogger(PushNotificationController.class);

	private final String TOPIC = "JavaSampleApproach";

	@Autowired
	private PushNotificationsService androidPushNotificationsService;
	
	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/push/send")
	public ResponseEntity<String> send() throws JSONException {

		JSONObject body = new JSONObject();
//		body.put("to", "/topics/" + TOPIC);
		body.put("to", "dt9o0fgwH_c:APA91bFyuMZb0aGXj4iAwEoqozZA4ffjWt2SRInNqln3GFLC9_9ygKQiVVIOJNFnh8Jvpi9O-3dj7UBBxWGtOgz6rj9wT4FMuGIG_VpwlppIS6ufPIizxcPatM6Y1G89T-GQ637VktR9");
//		body.put("to", null);
		body.put("priority", "high");
		
		JSONObject notification = new JSONObject();
		notification.put("title", "JSA Notification");
		notification.put("body", "Happy Message!");

		
		PedidoDTO pedido = pedidoService.findOne(34L).orElse(null);

		ObjectMapper obj = new ObjectMapper();
		String json = "";
		try {
			json = obj.writeValueAsString(pedido);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONObject data = new JSONObject();
		data.put("pedido", json);
		data.put("view", "1");

		
		body.put("notification", notification);
		body.put("data", data);

		/**
		 * { "notification": { "title": "JSA Notification", "body": "Happy Message!" },
		 * "data": { "Key-1": "JSA Data 1", "Key-2": "JSA Data 2" }, "to":
		 * "/topics/JavaSampleApproach", "priority": "high" }
		 */

		log.info(body.toString());

		try {
			HttpEntity<String> request = new HttpEntity<>(body.toString());

			CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request, TipoUsuario.PROVEEDOR);
			CompletableFuture.allOf(pushNotification).join();

			String firebaseResponse = pushNotification.get();

			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}
	
}
