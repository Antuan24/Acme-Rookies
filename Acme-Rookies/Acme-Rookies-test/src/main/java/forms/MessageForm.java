package forms;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;


public class MessageForm {

	private String recipients;
	private String subject;
	private String body;
	private String tags;
	private Boolean isBroadcast;

	public Boolean getIsBroadcast() {
		return isBroadcast;
	}
	public void setIsBroadcast(Boolean isBroadcast) {
		this.isBroadcast = isBroadcast;
	}
	@NotBlank
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	
	@NotBlank
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@NotBlank
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	

	
}
