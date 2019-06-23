package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import domain.CreditCard;
import domain.Curricula;
import domain.Rookie;
import domain.PersonalData;
import forms.CurriculaDataForm;
import forms.PersonalDataForm;
import forms.RegisterRookieAdminForm;

import repositories.PersonalDataRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class PersonalDataService {

	// Managed repository
	@Autowired
	private PersonalDataRepository personalDataRepository;

	// Supporting services
	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private RookieService hackerService;
	
	@Autowired
	private Validator validator;

	// Constructor
	public PersonalDataService() {
		super();
	}

	public PersonalData create() {
		PersonalData result;
		
		result = new PersonalData();

		return result;

	}
	
	public PersonalData reconstruct(CurriculaDataForm form, BindingResult binding ){
		
		
		PersonalData res = this.create();
		//Creamos la tarjeta de credito:
		res.setFullName(form.getFullName());
		res.setStatement(form.getStatement());
		res.setPhone(form.getPhone());
		res.setGithubUrl(form.getGithubUrl());
		res.setLinkedInUrl(form.getLinkedInUrl());
		
		validator.validate(form, binding);		
			
		return res;
	
	}

	public PersonalData save(final PersonalData personalData, Curricula curricula) {
		Assert.isTrue(LoginService.hasRole("ROOKIE"));
		Assert.notNull(personalData);
	
		curricula = this.curriculaService.findOne(curricula.getId());
		
		final PersonalData result = this.personalDataRepository.save(personalData);
		this.flush();
		
		this.curriculaService.save(curricula);
		this.curriculaService.flush();

		return result;

	}
	
	public PersonalData saveTrue(final PersonalData personalData) {
		
		return this.personalDataRepository.save(personalData);

	}

	public void flush() {
		this.personalDataRepository.flush();
	}

	public void delete(final PersonalData personalData) {
		Assert.notNull(personalData);
		Assert.isTrue(personalData.getId() != 0);
		Assert.isTrue(this.personalDataRepository.exists(personalData.getId()));


		this.personalDataRepository.delete(personalData);

	}

	public Collection<PersonalData> findAll() {
		Collection<PersonalData> result;
		result = this.personalDataRepository.findAll();

		return result;
	}

	public PersonalData findOne(final int personalDataId) {
		Assert.isTrue(personalDataId != 0);
		PersonalData result;
		
		result = this.personalDataRepository.findOne(personalDataId);

		return result;
	}
	
	public PersonalData reconstruct(PersonalDataForm form, BindingResult binding) {
		PersonalData result;
		
		if(form.getId() == 0) {
			result = this.create();
		} else {
			result = this.findOne(form.getId());
		}
		
		result.setFullName(form.getFullName());
		result.setStatement(form.getStatement());
		result.setPhone(form.getPhone());
		result.setGithubUrl(form.getGithubUrl());
		result.setLinkedInUrl(form.getLinkdInUrl());
		
		return result;
		
	}
	
	public PersonalDataForm construct(PersonalData personalData) {
		PersonalDataForm result;
		
		result = new PersonalDataForm();
		
		result.setFullName(personalData.getFullName());
		result.setStatement(personalData.getStatement());
		result.setPhone(personalData.getPhone());
		result.setGithubUrl(personalData.getGithubUrl());
		result.setLinkdInUrl(personalData.getLinkedInUrl());
		
		return result;
	}

}
