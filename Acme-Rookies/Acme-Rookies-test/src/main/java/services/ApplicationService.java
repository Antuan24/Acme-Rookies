package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Application;
import domain.Company;
import domain.Curricula;
import domain.Rookie;
import domain.Position;
import domain.Problem;
import forms.ApplicationCompanyForm;
import forms.ApplicationRookieForm;
//import forms.ApplicationRookieForm;

@Service
@Transactional
public class ApplicationService {

	//Managed Repository ------------------------------------------------------------------------
	
	@Autowired
	private ApplicationRepository appRepository;
	
	//Supporting Services -----------------------------------------------------------------------
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PositionService posService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private ProblemService problemService;

	//Simple CRUD methods -----------------------------------------------------------------------
	
	public Application create(Position position){

		Application res; 
		Problem problem;
		
		res = new Application();
		problem = (Problem) problemService.findProblemFinal(position.getCompany().getId()).toArray()[0];
		
		Date current = new Date(System.currentTimeMillis() - 1000);
		Rookie rookie = rookieService.findByPrincipal();
		res.setStatus("PENDING");
		res.setMoment(current);
		res.setRookie(rookie);
		res.setPosition(position);
		res.setProblem(problem);
		
		return res;
	}
	
	public void delete(Application a){
		this.appRepository.delete(a);
		appRepository.flush();
	}
	
	public Application save(Application a){
		Assert.isTrue(LoginService.hasRole("ROOKIE"));
		Curricula curricula;
		
		if(a.getStatus().equals("SUBMITTED")){
			Date current = new Date(System.currentTimeMillis() - 1000);
			a.setSubmitMoment(current);
			curricula = this.copyCurricula(a.getCurricula());
			a.setCurricula(curricula);
		}

		return appRepository.saveAndFlush(a);
	}
	
	public Application saveC(Application a){
		Assert.isTrue(LoginService.hasRole("COMPANY"));

		return appRepository.saveAndFlush(a);
	}
	
	public Application trueSave(Application a){
		return appRepository.saveAndFlush(a);
	}
	
	public Collection<Application> findAll(){
		return appRepository.findAll();
	}
	
	public Application findOne(int Id){
		return appRepository.findOne(Id);
	}
	
	//Other business methods ----------------------------------------------------------------------------
	
	public Application reconstruct(ApplicationRookieForm appForm,int id, BindingResult binding){
		Application app;
		Rookie rookie;
		
		app = this.findOne(appForm.getId());
		rookie = rookieService.findByPrincipal();
		
		app.setPosition(posService.findOne(id));
		app.setRookie(rookie);
		app.setAnswer(appForm.getAnswer());
		app.setCodeLink(appForm.getCodeLink());
		app.setProblem(appForm.getProblem());
		app.setCurricula(appForm.getCurricula());
		
		validator.validate(appForm, binding);
		if(binding.hasErrors()){
			throw new ValidationException();
		}
		return app;
	}
	
	public Application reconstruct(ApplicationCompanyForm appForm,int posId, int hId, BindingResult binding){
		Application app;
		
		app = this.findOne(appForm.getId());
		
		app.setPosition(posService.findOne(posId));
		app.setRookie(rookieService.findOne(hId));
		app.setStatus(appForm.getStatus());

		validator.validate(app, binding);
		if(binding.hasErrors()){
			throw new ValidationException();
		}
		
		return app;
	}
	
	public Curricula copyCurricula(Curricula a){
		Curricula result, saved;
		
		result = curriculaService.create();
		result.setEducationDatas(a.getEducationDatas());
		result.setRookie(a.getRookie());
		result.setMiscellaneousDatas(a.getMiscellaneousDatas());
		result.setName("Copy of "+a.getName());
		result.setPersonalData(a.getPersonalData());
		result.setPositionDatas(a.getPositionDatas());
		
		saved = curriculaService.save(result);
		
		return saved;
	}
	
	public Collection<Application> findAppByPrincipalRookie(){
		Rookie rookie = rookieService.findByPrincipal();
		return appRepository.findAppByRookie(rookie.getId());
	}
	
	public Collection<Application> findAppByPrincipalRookieAndStatus(String status){
		Rookie rookie = rookieService.findByPrincipal();
		return appRepository.findAppByRookieAndStatus(rookie.getId(),status);
	}
	
	public Collection<Application> findAppByPrincipalCompany(){
		Company company = companyService.findByPrincipal();
		return appRepository.findAppByCompany(company.getId());
	}
	
	public Collection<Application> findAppByPrincipalCompanyAndStatus(String status){
		Company company = companyService.findByPrincipal();
		return appRepository.findAppByCompanyAndStatus(company.getId(),status);
	}
	
	public Double getAvgApplicationsPerRookie(){
		Double res = appRepository.getAvgApplicationsPerRookie();
		if(res==null) res=0d;
		return res;
	}
	
	public Integer getMinApplicationsPerRookie(){
		Integer res = appRepository.getMinApplicationsPerRookie();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxApplicationsPerRookie(){
		Integer res=  appRepository.getMaxApplicationsPerRookie();
		if(res==null)res=0;
		return res;
	}
	
	public Double getStdevApplicationsPerRookie(){
		Double res = appRepository.getStdevApplicationsPerRookie();
		if(res==null)res=0d;
		return res;
	}
	
}
