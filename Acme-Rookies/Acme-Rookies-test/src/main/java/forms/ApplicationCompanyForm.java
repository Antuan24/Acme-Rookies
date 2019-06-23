package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class ApplicationCompanyForm {

	// Attributes -------------------------------------------------------------
	
	private int id;
	private String status;
	
	// Getters and Setters ---------------------------------------------------
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@NotBlank
	@Pattern(regexp = "^PENDING|SUBMITTED|ACCEPTED|REJECTED$")
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
