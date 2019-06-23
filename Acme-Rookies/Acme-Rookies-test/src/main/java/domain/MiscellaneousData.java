package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousData extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String text;
	private Collection<String> attachments;	
	
	// Getters and Setters ----------------------------------------------------
	
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@ElementCollection
	public Collection<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(Collection<String> attachments) {
		this.attachments = attachments;
	}
	
	//Relationships
	
	
}
