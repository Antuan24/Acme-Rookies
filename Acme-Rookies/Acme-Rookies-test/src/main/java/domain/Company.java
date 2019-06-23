package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	// Attributes -------------------------------------------------------------

	private String commercialName;
	private Double auditScore;
	

	// Getters and Setters ---------------------------------------------------
	
	@NotBlank
	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}
	
	public Double getAuditScore() {
		return auditScore;
	}

	public void setAuditScore(Double auditScore) {
		this.auditScore = auditScore;
	}
	
	// Relationships --------------------------------------------------------

	private Collection<Position> positions;
	
	@Valid
	@OneToMany(mappedBy="company")
	public Collection<Position> getPositions() {
		return positions;
	}

	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}
}
