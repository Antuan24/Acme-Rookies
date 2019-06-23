package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Curricula;
import domain.Rookie;
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

import repositories.MiscellaneousDataRepository;
import security.LoginService;

@Service
@Transactional
public class MiscellaneousDataService {

	// Managed repository
	@Autowired
	private MiscellaneousDataRepository miscellaneousDataRepository;

	// Supporting services
	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private RookieService hackerService;

	// Constructor
	public MiscellaneousDataService() {
		super();
	}

	public MiscellaneousData create() {
		MiscellaneousData result;
		
		result = new MiscellaneousData();
		result.setAttachments(new HashSet<String>());

		return result;

	}

	public MiscellaneousData save(final MiscellaneousData miscellaneousData, Curricula curricula) {
		Assert.isTrue(LoginService.hasRole("ROOKIE"));
		Assert.notNull(miscellaneousData);
		MiscellaneousData result;
		
		curricula = this.curriculaService.findOne(curricula.getId());
		result = this.miscellaneousDataRepository.save(miscellaneousData);
		curricula.getMiscellaneousDatas().add(miscellaneousData);
		this.curriculaService.save(curricula);

		return result;

	}
	
	public MiscellaneousData saveTrue(final MiscellaneousData miscellaneousData) {
		Assert.isTrue(LoginService.hasRole("ROOKIE"));
	
		return this.miscellaneousDataRepository.save(miscellaneousData);

	}

	public void flush() {
		this.miscellaneousDataRepository.flush();
	}

	public void delete(final MiscellaneousData miscellaneousData, final Curricula curricula) {
		Assert.notNull(miscellaneousData);
		Assert.isTrue(miscellaneousData.getId() != 0);
		Assert.isTrue(this.miscellaneousDataRepository.exists(miscellaneousData.getId()));

		curricula.getMiscellaneousDatas().remove(miscellaneousData);
		this.curriculaService.save(curricula);
		this.curriculaService.flush();
		this.miscellaneousDataRepository.delete(miscellaneousData);

	}

	public void pureDelete(MiscellaneousData miscellaneousData) {
		this.miscellaneousDataRepository.delete(miscellaneousData);
		this.miscellaneousDataRepository.flush();

	}

	public Collection<MiscellaneousData> findAll() {
		Collection<MiscellaneousData> result;
		result = this.miscellaneousDataRepository.findAll();

		return result;
	}

	public MiscellaneousData findOne(final int miscellaneousDataId) {
		Assert.isTrue(miscellaneousDataId != 0);
		MiscellaneousData result;
		result = this.miscellaneousDataRepository.findOne(miscellaneousDataId);

		return result;
	}
	
	public MiscellaneousData reconstruct(MiscellaneousDataForm form, BindingResult binding) {
		MiscellaneousData result;
		
		if(form.getId() == 0) {
			result = this.create();
		} else {
			result = this.findOne(form.getId());
		}
		
		result.setId(form.getId());
		result.setText(form.getText());
		result.setAttachments(form.getAttachments());
		
		return result;
		
	}
	
	public MiscellaneousDataForm construct(MiscellaneousData miscellaneousData) {
		MiscellaneousDataForm result;
		
		result = new MiscellaneousDataForm();
		
		result.setId(miscellaneousData.getId());
		result.setText(miscellaneousData.getText());
		result.setAttachments(miscellaneousData.getAttachments());
		
		return result;
	}

}
