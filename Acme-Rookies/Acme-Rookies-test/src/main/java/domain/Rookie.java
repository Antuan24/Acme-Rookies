package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Rookie extends Actor {

	// Attributes -------------------------------------------------------------

	

	// Getters and Setters ---------------------------------------------------
	

	// Relationships ----------------------------------------------------------
	
	private Collection<Curricula> curriculas;
	private Collection<Application> applications;

	@Valid
	@OneToMany(mappedBy="rookie")
	public Collection<Curricula> getCurriculas() {
		return curriculas;
	}

	public void setCurriculas(Collection<Curricula> curriculas) {
		this.curriculas = curriculas;
	}

	@Valid
	@OneToMany(mappedBy="rookie")
	public Collection<Application> getApplications() {
		return applications;
	}

	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

}
