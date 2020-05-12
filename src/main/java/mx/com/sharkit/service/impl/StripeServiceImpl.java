package mx.com.sharkit.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import mx.com.sharkit.service.StripeService;
import mx.com.sharkit.service.dto.ChargeRequestDTO;

@Service
public class StripeServiceImpl implements StripeService {
	
	@Value("${stripe.secret.key}")
    private String secretKey;
	
	@PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

	@Override
	public Charge charge(ChargeRequestDTO chargeRequest) throws StripeException {
		Map<String, Object> chargeParams = new HashMap<>();
		// El monto debe ser en centavos, según la documentación
		int amount = (chargeRequest.getAmount().multiply(new BigDecimal(100))).intValue();
		chargeParams.put("amount", amount);
		chargeParams.put("currency", chargeRequest.getCurrency());
		chargeParams.put("description", chargeRequest.getDescription());
		chargeParams.put("source", chargeRequest.getStripeToken());
		return Charge.create(chargeParams);
	}

}
