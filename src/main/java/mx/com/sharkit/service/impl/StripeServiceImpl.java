package mx.com.sharkit.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import mx.com.sharkit.service.StripeService;
import mx.com.sharkit.service.dto.ChargeRequestDTO;

@Service
public class StripeServiceImpl implements StripeService {

	@Override
	public Charge charge(ChargeRequestDTO chargeRequest) throws StripeException {
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", chargeRequest.getAmount());
		chargeParams.put("currency", chargeRequest.getCurrency());
		chargeParams.put("description", chargeRequest.getDescription());
		chargeParams.put("source", chargeRequest.getStripeToken());
		return Charge.create(chargeParams);
	}

}
