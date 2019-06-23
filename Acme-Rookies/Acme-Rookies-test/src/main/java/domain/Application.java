package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "status")})
public class Application extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date moment;
	private String answer;
	private String codeLink;
	private Date submitMoment;
	private String status;

	// Getters and Setters ---------------------------------------------------

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
		
	@URL
	public String getCodeLink() {
		return codeLink;
	}

	public void setCodeLink(String codeLink) {
		this.codeLink = codeLink;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSubmitMoment() {
		return submitMoment;
	}

	public void setSubmitMoment(Date submitMoment) {
		this.submitMoment = submitMoment;
	}
	
	@NotBlank
	@Pattern(regexp = "^PENDING|SUBMITTED|ACCEPTED|REJECTED$")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Relationships ----------------------------------------------------------

	private Rookie rookie;
	private Problem problem;
	private Position position;
	private Curricula curricula;
	
	@Valid
	@ManyToOne(optional=true)
	public Rookie getRookie() {
		return rookie;
	}

	public void setRookie(Rookie rookie) {
		this.rookie = rookie;
	}

	@Valid
	@ManyToOne(optional=true)
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@Valid
	@ManyToOne(optional = true)
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Valid
	@ManyToOne(optional=true)
	public Curricula getCurricula() {
		return curricula;
	}

	public void setCurricula(Curricula curricula) {
		this.curricula = curricula;
	}

}
