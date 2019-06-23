package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Curricula;
import domain.Rookie;
import domain.EducationData;
import forms.EducationDataForm;

import repositories.EducationDataRepository;
import security.LoginService;

@Service
@Transactional
public class EducationDataService {

	// Managed repository
	@Autowired
	private EducationDataRepository educationDataRepository;

	// Supporting services
	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private RookieService rookieService;

	// Constructor
	public EducationDataService() {
		super();
	}

	public EducationData create() {
		EducationData result;
		
		result = new EducationData();

		return result;

	}

	public EducationData save(final EducationData educationData, Curricula curricula) {
		Assert.isTrue(LoginService.hasRole("ROOKIE"));
		Assert.notNull(educationData);
		EducationData result;

		curricula = this.curriculaService.findOne(curricula.getId());
		result = this.educationDataRepository.save(educationData);
		curricula.getEducationDatas().add(educationData);
		this.curriculaService.save(curricula);

		return result;

	}
	
	public EducationData saveTrue(final EducationData educationData) {
		Assert.isTrue(LoginService.hasRole("ROOKIE"));
		return this.educationDataRepository.save(educationData);
	}

	public void flush() {
		this.educationDataRepository.flush();
	}

	public void delete(final EducationData educationData, final Curricula curricula) {
		Assert.notNull(educationData);
		Assert.isTrue(educationData.getId() != 0);
		Assert.isTrue(this.educationDataRepository.exists(educationData.getId()));

		curricula.getEducationDatas().remove(educationData);
		this.curriculaService.save(curricula);
		this.curriculaService.flush();
		this.educationDataRepository.delete(educationData);

	}

	public void pureDelete(EducationData EducationData) {
		this.educationDataRepository.delete(EducationData);
		this.educationDataRepository.flush();

	}

	public Collection<EducationData> findAll() {
		Collection<EducationData> result;
		result = this.educationDataRepository.findAll();

		return result;
	}

	public EducationData findOne(final int educationDataId) {
		Assert.isTrue(educationDataId != 0);
		EducationData result;
		result = this.educationDataRepository.findOne(educationDataId);

		return result;
	}
	
	public EducationData reconstruct(EducationDataForm form, BindingResult binding) {
		EducationData result;
		
		if(form.getId() == 0) {
			result = this.create();
		} else {
			result = this.findOne(form.getId());
		}
		
		result.setId(form.getId());
		result.setDegree(form.getDegree());
		result.setInstitution(form.getInstitution());
		result.setMark(form.getMark());
		result.setStartDate(form.getStartDate());
		result.setEndDate(form.getEndDate());
		
		return result;
	}
	
	public EducationDataForm construct(EducationData educationData) {
		EducationDataForm result;
		
		result = new EducationDataForm();
		
		result.setId(educationData.getId());
		result.setDegree(educationData.getDegree());
		result.setInstitution(educationData.getInstitution());
		result.setMark(educationData.getMark());
		result.setStartDate(educationData.getStartDate());
		result.setEndDate(educationData.getEndDate());
		
		return result;
	}

}
