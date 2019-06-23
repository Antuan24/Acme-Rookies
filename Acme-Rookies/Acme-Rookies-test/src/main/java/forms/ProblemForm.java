package forms;

import java.util.Collection;

import domain.Position;

public class ProblemForm {

	// Attributes -------------------------------------------------------------

	private int id;
	private String title;
	private String statement;
	private String hint;
	private Collection<String> attachments;	
	private Boolean isFinal;
	
	// Getters and Setters ----------------------------------------------------
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	public Collection<String> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(Collection<String> attachments) {
		this.attachments = attachments;
	}
	
	public Boolean getIsFinal() {
		return isFinal;
	}
	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	// Relationships -------------------------------------------------------------
	
	private Collection<Position> positions;
	
	public Collection<Position> getPositions() {
		return positions;
	}
	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}
	
	
	
}
