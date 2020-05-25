package mx.com.sharkit.excel.objectbinding.domain;

import java.io.Serializable;

public class ErrorDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -942027260264039293L;
	private Long row;
	private String field;
	private String errorMessage;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ErrorDTO [row=" + row + ", field=" + field + ", errorMessage=" + errorMessage + "]";
	}

}
