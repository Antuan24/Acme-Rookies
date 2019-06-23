package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Quolet extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String ticker;
	private String picture;
	private String body;
	private Date publicationMoment;
	private Boolean isFinal;
	
	// Getters and Setters ---------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp="^([A-Z0-9]{4})[-][0-9]{6}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
	
	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@NotBlank
	@Size(min=1,max=100)
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public Date getPublicationMoment() {
		return publicationMoment;
	}
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public void setPublicationMoment(Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	// Relationships ----------------------------------------------------------
	
	private Audit audit;
	
	@Valid
	@ManyToOne(optional=true)
	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}
	
}
