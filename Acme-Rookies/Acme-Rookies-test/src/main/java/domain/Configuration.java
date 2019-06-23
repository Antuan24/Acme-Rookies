package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String banner;
	private Double finderCacheTime;

	private String defaultPhoneCode;
	private Integer finderQueryResults;
	
	private String systemName;
	private String welcomeTextEnglish;
	private String welcomeTextSpanish;
	
	private Double vatPercentage;
	private Double sponsorshipFare;

	private Boolean notificationIsSent;

	// Getters and Setters ---------------------------------------------------
	@NotNull
	public Boolean getNotificationIsSent() {
		return notificationIsSent;
	}

	public void setNotificationIsSent(Boolean notificationIsSent) {
		this.notificationIsSent = notificationIsSent;
	}
	
	@URL
	@NotEmpty
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getDefaultPhoneCode() {
		return defaultPhoneCode;
	}

	public void setDefaultPhoneCode(String defaultPhoneCode) {
		this.defaultPhoneCode = defaultPhoneCode;
	}

	@Range(min = 1, max = 24)
	public Double getFinderCacheTime() {
		return finderCacheTime;
	}

	public void setFinderCacheTime(Double finderCacheTime) {
		this.finderCacheTime = finderCacheTime;
	}

	@NotNull
	@Range(min = 10, max = 100)
	public Integer getFinderQueryResults() {
		return finderQueryResults;
	}

	public void setFinderQueryResults(Integer finderQueryResults) {
		this.finderQueryResults = finderQueryResults;
	}

	//TODO: might need to be revised.
	@NotBlank
	public String getSystemName() {
		return systemName;
	}

	
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	public String getWelcomeTextEnglish() {
		return welcomeTextEnglish;
	}

	public void setWelcomeTextEnglish(String welcomeTextEnglish) {
		this.welcomeTextEnglish = welcomeTextEnglish;
	}

	@NotBlank
	public String getWelcomeTextSpanish() {
		return welcomeTextSpanish;
	}

	public void setWelcomeTextSpanish(String welcomeTextSpanish) {
		this.welcomeTextSpanish = welcomeTextSpanish;
	}

	@NotNull
	@Range(min = 0, max = 1)
	public Double getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(Double vatPercentage) {
		this.vatPercentage = vatPercentage;
	}
	
	@NotNull
	@Range(min = 0, max = 1)
	public Double getSponsorshipFare() {
		return sponsorshipFare;
	}

	public void setSponsorshipFare(Double sponsorshipFare) {
		this.sponsorshipFare = sponsorshipFare;
	}
	
	//TODO: might need to be associated with the classes.
	//Relationships--------------------------------------------------------------------
	
	private List<Word> spamWords; 

	@Valid
	@ElementCollection
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<Word> getspamWords() {
		return spamWords;
	}

	public void setSpamWords(List<Word> spamWords) {
		this.spamWords = spamWords;
	}
	

}
