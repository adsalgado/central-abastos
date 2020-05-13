package mx.com.sharkit.service.dto;

import java.math.BigDecimal;

public class ChargeRequestDTO {

	public enum Currency {
		USD, MXN, EUR;
	}

	private String description;
	private BigDecimal amount;
	private Currency currency;
	private String stripeEmail;
	private String stripeToken;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getStripeEmail() {
		return stripeEmail;
	}

	public void setStripeEmail(String stripeEmail) {
		this.stripeEmail = stripeEmail;
	}

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((stripeEmail == null) ? 0 : stripeEmail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChargeRequestDTO other = (ChargeRequestDTO) obj;
		if (!amount.equals(other.amount))
			return false;
		if (currency != other.currency)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (stripeEmail == null) {
			if (other.stripeEmail != null)
				return false;
		} else if (!stripeEmail.equals(other.stripeEmail))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChargeRequestDTO [description=" + description + ", amount=" + amount + ", currency=" + currency
				+ ", stripeEmail=" + stripeEmail + "]";
	}

}
