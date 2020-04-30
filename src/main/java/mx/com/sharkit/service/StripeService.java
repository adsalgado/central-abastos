package mx.com.sharkit.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import mx.com.sharkit.service.dto.ChargeRequestDTO;

public interface StripeService {

	Charge charge(ChargeRequestDTO chargeRequest) throws StripeException;
	
}
