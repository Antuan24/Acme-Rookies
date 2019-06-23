package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;

import org.hibernate.validator.constraints.NotBlank;

public class MiscellaneousDataForm {
	
	private Integer curriculaId;
	private String text;
	private Collection<String> attachments;
	private Integer id;
	
	
	public Integer getCurriculaId() {
		return curriculaId;
	}
	public void setCurriculaId(Integer curriculaId) {
		this.curriculaId = curriculaId;
	}
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	

}
