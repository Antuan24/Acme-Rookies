package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

import domain.Application;
import domain.Audit;
import domain.Company;
import domain.Finder;
import domain.Position;
import domain.Problem;
import domain.Sponsorship;

import repositories.ApplicationRepository;
import repositories.AuditRepository;
import repositories.PositionRepository;
import repositories.ProblemRepository;
import repositories.SponsorshipRepository;
import security.LoginService;

@Service
@Transactional
public class PositionService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private FinderService finderService;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private AuditRepository auditRepository;
	
	@Autowired
	private PositionRepository positionRepository;
	
	@Autowired
	private ProblemRepository problemRepository;
	
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicationService applicationService;
	
	public Position create(){
		Position result = new Position();
		
		result.setCompany(companyService.findByPrincipal());
		result.setTicker(this.generateTicker());
		result.setIsFinal(false);
		result.setFinders(new ArrayList<Finder>());
		
		return result;
	}
	
	public Position save(Position position){
		
		Assert.isTrue(LoginService.hasRole("COMPANY"));
		Position result = null;
		
		Collection<Problem> problems = problemService.findAll();
		Collection<Problem> problemsPosition = new ArrayList<Problem>();
		
		for(Problem problem: problems){
			if(problem.getPositions().contains(position)){
				problemsPosition.add(problem);
			}
		}
//		Assert.isTrue(position.getIsFinal() && problemsPosition.size() >= 2);
		System.out.println("numero de problems" + problemsPosition.size());
		System.out.println(position.getIsFinal());
		if(position.getIsFinal() && problemsPosition.size() >= 2) result = positionRepository.saveAndFlush(position);
		 else if(position.getIsFinal() == false) result = positionRepository.saveAndFlush(position);
		
		return result;
	}
	
	public Position trueSave(Position position){
		return positionRepository.saveAndFlush(position);
	}
	
	public void flush(){
		 positionRepository.flush();
	}
	
	public void delete(Position position){
		Assert.isTrue(LoginService.hasRole("COMPANY"));
		Assert.isTrue(!position.getIsFinal());
		
		Collection<Finder> finders = finderService.findAll();
		Collection<Finder> findersPosition = position.getFinders();
		
//		Collection<Application> applicationsPosition = positionRepository.getApplicationsByPosition(position.getId());
		
		finders.removeAll(findersPosition);
//		applications.removeAll(applicationsPosition);
		
		Collection<Application> applications = applicationService.findAll();
		
		for(Application application: applications){
			if(application.getPosition().equals(position)){
//				System.out.println("Application delete: " + application);
//				System.out.println("Position application: " + application.getPosition());
				applicationRepository.delete(application);
			}
		}
		
		Collection<Problem> problems = problemService.findAll();
		
		for(Problem problem: problems){
			if(problem.getPositions().contains(position)){
				problem.getPositions().remove(position);
				problemRepository.save(problem);
			}
		}
		
		Collection<Audit> audits = auditService.findAll();
//		Collection<Audit> auditsPosition = position.getAudits();
		
		for(Audit audit: audits){
			if(audit.getPosition().equals(position)){
				auditRepository.delete(audit);
			}
		}
//		audits.removeAll(auditsPosition);
		
		Collection<Sponsorship> sponsorships = sponsorshipService.findAll();
		
		for(Sponsorship sponsorship: sponsorships){
			if(sponsorship.getPosition().equals(position)){
				sponsorshipRepository.delete(sponsorship);
			}
		}
		
//		problemRepository.deleteAll();
//		applicationRepository.deleteAll();
		
		Position result = positionRepository.saveAndFlush(position);
		
		positionRepository.delete(result);
	}
	
	public void trueDelete(Position position){
		this.positionRepository.delete(position);
	}
	
	
	public Collection<Position> findPositionsIsFinal(){
		return positionRepository.getPositionsIsFinal();
	}
	
	public Collection<Position> findPositionsIsDraft(){
		return positionRepository.getPositionsIsDraft();
	}
	
	public Collection<Position> findPositionsByCompany(int company){
		return positionRepository.getPositionsByCompany(company);
	}
	
	public Collection<Position> findPublishedPositionsByCompany(int company){
		return positionRepository.getPublishedPositionsByCompany(company);
	}
	
	public Collection<Application> findApplicationsByPosition(int position){
		return positionRepository.getApplicationsByPosition(position);
	}
	
	public Collection<Position> findAll(){
		return positionRepository.findAll();
	}
	
	public Position findOne(int Id){
		return positionRepository.findOne(Id);
	}
	
	private String generateTicker(){
		Company company = companyService.findByPrincipal();
//      String cadena = position.getCompany().getCommercialName();
		String cadena = company.getCommercialName();
		System.out.println("Nombre: " + cadena);
        String subCadena = "";
        if(cadena.length() >= 4) 
        	subCadena = cadena.substring(0,4).toUpperCase();
        	
        if (cadena.length() < 4)
        	for(int i=cadena.length(); i<4; i++){
        		 subCadena = cadena.toUpperCase() + "X";
        	
        }
        String ticker = subCadena + "-" + randomNumber();
        System.out.println("Ticker: " + ticker);
        return ticker;
    }

	private String randomNumber(){
        String SALTCHARS = "0123456789";
           StringBuilder salt = new StringBuilder();
           Random rnd = new Random();
           while (salt.length() < 4) { // length of the random string.
               int index = (int) (rnd.nextFloat() * SALTCHARS.length());
               salt.append(SALTCHARS.charAt(index));
           }
           String saltStr = salt.toString();
           return saltStr;
   }
	
	public Position reconstruct(Position position, BindingResult bindingResult){
		Position result;
		if(position.getId()==0){
//			result = this.create();
			result = position;
			System.out.println("Position reconstruct: " + position.getTicker());
			result.setCompany(companyService.findByPrincipal());
//			result.setTicker(position.getTicker());
//			result.setTicker(this.generateTicker());
			result.setIsFinal(false);
			result.setFinders(new ArrayList<Finder>());
			result.setTitle(position.getTitle());
			result.setDescription(position.getDescription());
			result.setDeadline(position.getDeadline());
			result.setProfile(position.getProfile());
			result.setSkills(position.getSkills());
			result.setTechnologies(position.getTechnologies());
			result.setSalary(position.getSalary());
		}else{
			result = positionRepository.findOne(position.getId());
			result.setTitle(position.getTitle());
			result.setDescription(position.getDescription());
			result.setDeadline(position.getDeadline());
			result.setProfile(position.getProfile());
			result.setSkills(position.getSkills());
			result.setTechnologies(position.getTechnologies());
			result.setSalary(position.getSalary());
			
			Integer cont = 0;
			Collection<Problem> problems = problemService.findProblemByPosition(position);
			System.out.println("numero de problemas totales "+ problems);
			for(Problem problem: problems){
					System.out.println(problem.getTitle()+ " is final "+problem.getIsFinal());
					if(problem.getIsFinal()){
						cont++;
						System.out.println(cont);
				}
			}
			
			if(position.getIsFinal() == false){
				result.setIsFinal(false);
			}else if(position.getIsFinal() == true && cont < 2){
				result.setIsFinal(false);
			}else{
				result.setIsFinal(true);
			}
			
		}
		
		validator.validate(result, bindingResult);
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult);
			throw new ValidationException();
		}
		return result;
	}
	
	public Double getAvgPositionsPerCompany(){
		Double res = positionRepository.getAvgPositionsPerCompany();
		if(res==null)res=0d;
		return res;
	}

	public Integer getMinPositionsPerCompany(){
		Integer res = positionRepository.getMinPositionsPerCompany();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxPositionsPerCompany(){
		Integer res = positionRepository.getMaxPositionsPerCompany();
		if(res==null)res=0;
		return res;
	}
	
	public Double getStdevPositionsPerCompany(){
		Double res = positionRepository.getStdevPositionsPerCompany();
		if(res==null)res=0d;
		return res;
	}
	
	public Double getAvgSalaryPerPosition(){
		Double res = positionRepository.getAvgSalaryPerPosition();
		if(res==null)res=0d;
		return res;
	}

	public Integer getMinSalaryPerPosition(){
		Integer res = positionRepository.getMinSalaryPerPosition();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxSalaryPerPosition(){
		Integer res = positionRepository.getMaxSalaryPerPosition();
		if(res==null)res=0;
		return res;
	}
	
	public Double getStdevSalaryPerPosition(){
		Double res = positionRepository.getStdevSalaryPerPosition();
		if(res==null)res=0d;
		return res;
	}
	
	public Position getBestSalaryPosition(){
		return positionRepository.getBestSalaryPosition();
	}
	
	public Position getWorstSalaryPosition(){
		return positionRepository.getWorstSalaryPosition();
	}
	
	public Collection<Position> searchPositions(String keyword){
		System.out.println("service search " + positionRepository.searchPositions(keyword).size());
		return positionRepository.searchPositions(keyword);
	}
	
	public Collection<Position> searchPositionsWCompany(String keyword,Integer companyId){
		return positionRepository.searchPositionsWCompany(keyword,companyId);
	}
	
	public Sponsorship randomSponsorshipShown(Collection<Sponsorship> sponsorships) {
		Sponsorship sponsorship;
		ArrayList<Sponsorship> sps = new ArrayList<Sponsorship>();
		sps.addAll(sponsorships);

		int size = (int) sps.size();
		int i;
		
		if(size == 0) {
			i = 0;
		} else {
			i = (int) Math.floor(Math.random()*(size-1));
		}

		int sp = 0;
		
		if(i < 0) {
			sp = -1*i;
		} else {
			sp = i;
		}
		
		sponsorship = sps.get(sp);
		
		return sponsorship;
	}
	
	public Double getAvgSalaryPositionsHighestScore(){
		Double res = positionRepository.getAvgSalaryPositionsHighestScore();
		if(res==null)res=0d;
		return res;
	}
}
