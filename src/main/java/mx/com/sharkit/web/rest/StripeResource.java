package mx.com.sharkit.web.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import io.github.jhipster.web.util.ResponseUtil;
import mx.com.sharkit.service.StripeService;
import mx.com.sharkit.service.dto.ChargeRequestDTO;
import mx.com.sharkit.service.dto.ChargeRequestDTO.Currency;

//@RestController
@RequestMapping("/api")
public class StripeResource {
	
	private final Logger log = LoggerFactory.getLogger(StripeResource.class);

	@Value("${stripe.public.key}")
    private String publicKey;

	@Autowired
    private StripeService paymentsService;
 
    @PostMapping("/stripe/charge")
    public ResponseEntity<Charge> charge(@RequestBody ChargeRequestDTO chargeRequest)
      throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.MXN);
        // para pruebas only
        chargeRequest.setStripeToken(publicKey);

        Optional<Charge> opt = Optional.ofNullable(paymentsService.charge(chargeRequest));
        return ResponseUtil.wrapOrNotFound(opt);
    }
 
    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }

}
