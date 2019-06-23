package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class PersonalDataForm {
	
	private Integer curriculaId;
	private String fullName;
	private String statement;
	private String phone;
	private String githubUrl;
	private String linkdInUrl;
	private Integer id;
	
	
	
	public Integer getCurriculaId() {
		return curriculaId;
	}
	public void setCurriculaId(Integer curriculaId) {
		this.curriculaId = curriculaId;
	}
	@NotBlank
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@NotBlank
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	@NotBlank
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGithubUrl() {
		return githubUrl;
	}
	@NotBlank
	@URL
	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}
	@NotBlank
	@URL
	public String getLinkdInUrl() {
		return linkdInUrl;
	}
	public void setLinkdInUrl(String linkdInUrl) {
		this.linkdInUrl = linkdInUrl;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
