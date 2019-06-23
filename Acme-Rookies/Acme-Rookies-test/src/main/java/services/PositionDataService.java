package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Curricula;
import domain.Rookie;
import domain.PositionData;
import forms.PositionDataForm;

import repositories.PositionDataRepository;
import security.LoginService;

@Service
@Transactional
public class PositionDataService {

	// Managed repository
	@Autowired
	private PositionDataRepository positionDataRepository;

	// Supporting services
	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private RookieService hackerService;

	// Constructor
	public PositionDataService() {
		super();
	}

	public PositionData create() {
		PositionData result;
		
		result = new PositionData();

		return result;

	}

	public PositionData save(final PositionData positionData, Curricula curricula) {
		Assert.isTrue(LoginService.hasRole("ROOKIE"));
		Assert.notNull(positionData);
		PositionData result;
		
		curricula = this.curriculaService.findOne(curricula.getId());
		result = this.positionDataRepository.save(positionData);
		curricula.getPositionDatas().add(positionData);
		this.curriculaService.save(curricula);

		return result;

	}
	
	public PositionData saveTrue(final PositionData positionData) {
		Assert.isTrue(LoginService.hasRole("ROOKIE"));

		return this.positionDataRepository.save(positionData);
	}

	public void flush() {
		this.positionDataRepository.flush();
	}

	public void delete(final PositionData positionData, final Curricula curricula) {
		Assert.notNull(positionData);
		Assert.isTrue(positionData.getId() != 0);
		Assert.isTrue(this.positionDataRepository.exists(positionData.getId()));

		curricula.getPositionDatas().remove(positionData);
		this.curriculaService.save(curricula);
		this.curriculaService.flush();
		this.positionDataRepository.delete(positionData);

	}

	public void pureDelete(PositionData positionData) {
		this.positionDataRepository.delete(positionData);
		this.positionDataRepository.flush();

	}

	public Collection<PositionData> findAll() {
		Collection<PositionData> result;
		result = this.positionDataRepository.findAll();

		return result;
	}

	public PositionData findOne(final int positionDataId) {
		Assert.isTrue(positionDataId != 0);
		PositionData result;
		result = this.positionDataRepository.findOne(positionDataId);

		return result;
	}
	
	public PositionData reconstruct(PositionDataForm form, BindingResult binding) {
		PositionData result;
		
		if(form.getId() == 0) {
			result = this.create();
		} else {
			result = this.findOne(form.getId());
		}
		
		result.setId(form.getId());
		result.setTitle(form.getTitle());
		result.setDescription(form.getDescription());
		result.setStartDate(form.getStartDate());
		result.setEndDate(form.getEndDate());
		
		return result;
		
	}
	
	public PositionDataForm construct(PositionData positionData) {
		PositionDataForm result;
		
		result = new PositionDataForm();
		
		result.setId(positionData.getId());
		result.setTitle(positionData.getTitle());
		result.setDescription(positionData.getDescription());
		result.setStartDate(positionData.getStartDate());
		result.setEndDate(positionData.getEndDate());
		
		return result;
	}

}
