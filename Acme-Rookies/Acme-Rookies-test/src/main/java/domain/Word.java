package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;


import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Word extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String englishName;
	private String spanishName;

	// Getters and Setters ---------------------------------------------------

	@NotBlank
	@Column(unique = true)
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}


	@NotBlank
	@Column(unique = true)
	public String getSpanishName() {
		return spanishName;
	}

	public void setSpanishName(String spanishName) {
		this.spanishName = spanishName;
	}
}
