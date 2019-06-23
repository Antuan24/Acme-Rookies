package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "user_account")})
public abstract class Actor extends DomainEntity {

	// Attributes -----------------------------------------------------------

	private String name;
	private String surnames;
	private String vatNumber;
	
	private String photo;
	private String email;
	private String phone;
	private String address;
	private Boolean isSpammer;
	private Boolean isBanned;


	// Getters and Setters ---------------------------------------------------

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurnames() {
		return this.surnames;
	}

	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@Pattern(regexp = "^[A-Z]{3}[0-9]{8}$")
	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	
	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}
	
	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Pattern(regexp = "^[6-7-9]{1}[0-9]{8}$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}
	
	public Boolean getIsSpammer() {
		return isSpammer;
	}

	public void setIsSpammer(Boolean isSpammer) {
		this.isSpammer = isSpammer;
	}

	public Boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final Boolean isBanned) {
		this.isBanned = isBanned;
	}

	// Relationships ----------------------------------------------------------

	private Collection<SocialProfile> socialProfiles;
	private UserAccount userAccount;
	private CreditCard creditCard;

	@Valid
	@OneToOne(optional = true)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<SocialProfile> getSocialProfiles() {
		return socialProfiles;
	}

	public void setSocialProfiles(Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}
	
	@Valid
	@OneToOne
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
