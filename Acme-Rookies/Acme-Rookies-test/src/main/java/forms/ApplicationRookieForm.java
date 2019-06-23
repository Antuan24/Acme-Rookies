package forms;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.Curricula;
import domain.Problem;

public class ApplicationRookieForm {

	// Attributes -------------------------------------------------------------
	
	private int id;
	private String answer;
	private String codeLink;
	private Problem problem;
	private Curricula curricula;
	
	// Getters and Setters ---------------------------------------------------
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@NotBlank
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@NotBlank
	@URL
	public String getCodeLink() {
		return codeLink;
	}

	public void setCodeLink(String codeLink) {
		this.codeLink = codeLink;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Curricula getCurricula() {
		return curricula;
	}

	public void setCurricula(Curricula curricula) {
		this.curricula = curricula;
	}

}
