package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String name;
	
	// Getters and Setters ---------------------------------------------------
	
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Relationships ---------------------------------------------------------
	
	private Rookie rookie;
	private PersonalData personalData;
	private Collection<PositionData> positionDatas;
	private Collection<EducationData> educationDatas;
	private Collection<MiscellaneousData> miscellaneousDatas;

	@Valid
	@OneToOne(optional=true)
	public Rookie getRookie() {
		return rookie;
	}

	public void setRookie(Rookie rookie) {
		this.rookie = rookie;
	}

	@Valid
	@OneToOne(optional=true, cascade = CascadeType.PERSIST)
	public PersonalData getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	@Valid
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	public Collection<PositionData> getPositionDatas() {
		return positionDatas;
	}

	public void setPositionDatas(Collection<PositionData> positionDatas) {
		this.positionDatas = positionDatas;
	}

	@Valid
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	public Collection<EducationData> getEducationDatas() {
		return educationDatas;
	}

	public void setEducationDatas(Collection<EducationData> educationDatas) {
		this.educationDatas = educationDatas;
	}


	@Valid
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	public Collection<MiscellaneousData> getMiscellaneousDatas() {
		return miscellaneousDatas;
	}

	public void setMiscellaneousDatas(Collection<MiscellaneousData> miscellaneousDatas) {
		this.miscellaneousDatas = miscellaneousDatas;
	}



	
}
