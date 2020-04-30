package mx.com.sharkit.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import mx.com.sharkit.service.StripeService;
import mx.com.sharkit.service.dto.ChargeRequestDTO;
import mx.com.sharkit.service.dto.ChargeRequestDTO.Currency;

@RestController
@RequestMapping("/api")
public class StripeResource {
	
	private final Logger log = LoggerFactory.getLogger(StripeResource.class);


	@Autowired
    private StripeService paymentsService;
 
    @PostMapping("/charge")
    public String charge(ChargeRequestDTO chargeRequest, Model model)
      throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.MXN);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }
 
    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }

}
