package test.integration.sbangularjs.model;

import java.time.LocalDateTime;

public class SMS {
	private String phone;
	private String code;
	private LocalDateTime date;
	private String body;
	
	public SMS() {
		
	}
	
	public SMS(SMSForm msgForm) {
		this.setPhone(msgForm.getPhone());
		this.setCode(msgForm.getCode());
		this.setDate(msgForm.getDate());
		this.setBody(msgForm.getBody());
	}
	
	public SMS(String phone, String code, LocalDateTime date, String body) {
		this.phone = phone;
		this.code = code;
		this.date = date;
		this.body = body;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
