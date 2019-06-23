package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Problem extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String title;
	private String statement;
	private String hint;
	private Collection<String> attachments;	
	private Boolean isFinal;
	
	// Getters and Setters ----------------------------------------------------
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
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
	
	@NotEmpty
	@ElementCollection
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
	
	@ManyToMany
	public Collection<Position> getPositions() {
		return positions;
	}
	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}
	
	
	
}
