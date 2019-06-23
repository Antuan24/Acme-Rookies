package forms;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import domain.Word;

public class ConfigurationForm {
	
	private String banner;
	private Double finderCacheTime;
	private String defaultPhoneCode;
	private Integer finderQueryResults;
	private String systemName;
	private Double vatPercentage;
	private Double sponsorshipFare;

	private String welcomeTextEnglish;
	private String welcomeTextSpanish;
	
	private List<Word> spamWords; 
	
	//For word

	private String wordEnglishName;
	private String wordSpanishName;

	@URL
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
	
	@NotNull
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

	@Valid
	@ElementCollection
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<Word> getspamWords() {
		return spamWords;
	}

	public void setSpamWords(List<Word> spamWords) {
		this.spamWords = spamWords;
	}

	@Column(unique = true)
	public String getWordEnglishName() {
		return wordEnglishName;
	}

	public void setWordEnglishName(String wordEnglishName) {
		this.wordEnglishName = wordEnglishName;
	}
	
	@Column(unique = true)
	public String getWordSpanishName() {
		return wordSpanishName;
	}

	public void setWordSpanishName(String wordSpanishName) {
		this.wordSpanishName = wordSpanishName;
	}

}
