package forms;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class ProfileForm {
	
	//to edit user accounts:
	private String	username;
	private String	password;
	private String	password2;
	
	//personal Data:
	private String	name;
	private String	surnames;
	private String  vatNumber;
	private String	photo;
	private String	email;
	private String	phone;
	private String	address;
	
	private String commercialName;
	
	//Credit card:
	
	private String holder;
	private String make;
	private String number;
	private Integer expirationMonth;
	private Integer expirationYear;
	private Integer CVV;
	
	@NotBlank
	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}
	
	@NotBlank
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}

	@Size(min = 16, max = 16)
	@Column(unique = true)
	@Pattern(regexp = "^[0-9]{16}$")
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

	@NotNull
	public Integer getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotNull
	public Integer getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCVV() {
		return CVV;
	}

	public void setCVV(Integer cVV) {
		CVV = cVV;
	}
	
	// end CreditCard------------------------------

	@NotBlank
	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}

	@Pattern(regexp = "^[A-Z]{3}[0-9]{8}$")
	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	@NotEmpty
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
	
	@NotEmpty
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
	
	@NotEmpty
	public String getPassword2() {
		return this.password2;
	}

	public void setPassword2(final String password2) {
		this.password2 = password2;
	}
	
	@NotEmpty
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	@NotEmpty
	public String getSurnames() {
		return this.surnames;
	}

	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@URL
	@NotEmpty
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}
	
	@Email
	@NotEmpty
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Pattern(regexp = "^([+-]\\d+\\s+)?(\\([0-9]+\\)\\s+)?([\\d\\w\\s-]+)$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}
}
