package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import security.LoginService;
import domain.Company;
import domain.Position;
import domain.Problem;
import forms.ProblemForm;

@Service
@Transactional
public class ProblemService {

	//Managed Repository ------------------------------------------------------------------------
	
	@Autowired
	private ProblemRepository problemRepository;
	
	//Supporting Services -----------------------------------------------------------------------
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private Validator validator;

	//Simple CRUD methods -----------------------------------------------------------------------
	
	public Problem create(){

		Problem res = new Problem();
		
		res.setAttachments(new ArrayList<String>());
		res.setIsFinal(false);
		res.setPositions(new ArrayList<Position>());
		
		return res;
	}
	
	public Collection<Problem> findAll(){
		return problemRepository.findAll();
	}
	
	public Problem findOne(int Id){
		Problem result;
		
		result = problemRepository.findOne(Id);
		
		return result;
	}
	
	public Problem save(Problem a){
		Assert.isTrue(LoginService.hasRole("COMPANY"));
		
		return problemRepository.saveAndFlush(a);
	}
	
	public void delete(Problem a){
		
		problemRepository.delete(a);
	}
	
	//Other business methods ----------------------------------------------------------------------------
	
	public Problem reconstruct(ProblemForm problemForm, BindingResult binding){
		Problem problem;
		if(problemForm.getId()!=0){
			problem = this.findOne(problemForm.getId());
			problem.setPositions(problemForm.getPositions());
			problem.setHint(problemForm.getHint());
			problem.setAttachments(problemForm.getAttachments());
			problem.setIsFinal(problemForm.getIsFinal());
			problem.setStatement(problemForm.getStatement());
			problem.setTitle(problemForm.getTitle());
		}else{
			problem = this.create();
			problem.setPositions(problemForm.getPositions());
			problem.setHint(problemForm.getHint());
			problem.setAttachments(problemForm.getAttachments());
			problem.setIsFinal(problemForm.getIsFinal());
			problem.setStatement(problemForm.getStatement());
			problem.setTitle(problemForm.getTitle());
		}
		
		validator.validate(problem, binding);
		
		if(binding.hasErrors()){
			System.out.println(binding);
			throw new ValidationException();
		}
		
		return problem;
	}
	
	public ProblemForm construct(Problem problemForm){
		ProblemForm problem;
		problem = new ProblemForm();
		
		if(problemForm.getId()==0){
			problem.setId(problemForm.getId());
			problem.setAttachments(problemForm.getAttachments());
			problem.setIsFinal(problemForm.getIsFinal());
			problem.setPositions(problemForm.getPositions());
		}else{
			problem.setAttachments(problemForm.getAttachments());
			problem.setIsFinal(problemForm.getIsFinal());
			problem.setPositions(problemForm.getPositions());
			problem.setHint(problemForm.getHint());
			problem.setId(problemForm.getId());
			problem.setStatement(problemForm.getStatement());
			problem.setTitle(problemForm.getTitle());
		}
		
		return problem;
	}
	
	public Collection<Problem> findProblemByPrincipal(){
		Company company = companyService.findByPrincipal();
		return problemRepository.findProblemByCompany(company.getId());
	}
	
	public Collection<Problem> findProblemByCompany(int id){
		return problemRepository.findProblemByCompany(id);
	}
	
	public Collection<Problem> findProblemFinal(int id){
		return problemRepository.findProblemByCompanyFinal(id);
	}
	
	public Collection<Problem> findProblemByPosition(Position ps){
		return problemRepository.findProblemByPosition(ps);
	}
	
}
