package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date moment;
	
	private String keyword;
	private Double minSalary;
	private Double maxSalary;
	private Date maxDeadline;


	// Getters and Setters ---------------------------------------------------
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}
	
	public Double getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(Double minSalary) {
		this.minSalary = minSalary;
	}

	public Double getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(Double maxSalary) {
		this.maxSalary = maxSalary;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMaxDeadline() {
		return maxDeadline;
	}

	public void setMaxDeadline(Date maxDeadline) {
		this.maxDeadline = maxDeadline;
	}

	// Relationships ----------------------------------------------------------

	private Rookie rookie;
	private Collection<Position> positions;

	@Valid
	@OneToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}
	
	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

	@Valid
	@ManyToMany
	public Collection<Position> getPositions() {
		return positions;
	}

	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}


}
