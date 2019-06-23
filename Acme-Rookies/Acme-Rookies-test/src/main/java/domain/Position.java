package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String ticker;
	private String title;
	private String description;
	private Date deadline;
	private String profile;
	private String skills;
	private String technologies;
	private Double salary;
	private Boolean isFinal;
	
	private Double scoreAverage;
	
	// Getters and Setters ---------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp ="^[A-Z]{4}[-][0-9]{4}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@NotBlank
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	@NotBlank
	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	@NotBlank
	public String getTechnologies() {
		return technologies;
	}

	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}

	@NotNull
	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	public Double getScoreAverage() {
		return scoreAverage;
	}

	public void setScoreAverage(Double scoreAverage) {
		this.scoreAverage = scoreAverage;
	}


	// Relationships ----------------------------------------------------------

	private Company company;
	private Collection<Finder> finders;
	private Collection<Audit> audits;
	private Collection<Sponsorship> sponsorships;
	
	@Valid
	@ManyToOne(optional = true)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToMany(mappedBy="positions")
	public Collection<Finder> getFinders() {
		return finders;
	}

	public void setFinders(Collection<Finder> finders) {
		this.finders = finders;
	}

	@Valid
	@OneToMany(mappedBy="position")
	public Collection<Audit> getAudits() {
		return audits;
	}

	public void setAudits(Collection<Audit> audits) {
		this.audits = audits;
	}

	@Valid
	@OneToMany(mappedBy="position")
	public Collection<Sponsorship> getSponsorships() {
		return sponsorships;
	}

	public void setSponsorships(Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	
}
