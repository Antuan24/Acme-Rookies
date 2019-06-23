package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CompanyRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Configuration;
import domain.Curricula;
import domain.Company;
import domain.Rookie;
import domain.Position;
import domain.SocialProfile;


@Service
@Transactional
public class CompanyService {

	//Managed Repository -----
	
	@Autowired
	private CompanyRepository companyRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Company create(UserAccount ua){
		Company res = new Company();
		
		res.setIsBanned(false);
		res.setIsSpammer(false);
		res.setPositions(new ArrayList<Position>());
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setUserAccount(ua);
		res.setAuditScore(null);
		
		return res;
	}
	
	public Collection<Company> findAll(){
		return companyRepository.findAll();
	}
	
	public Company findOne(int Id){
		return companyRepository.findOne(Id);
	}
	
	public Company save(Company a){
		
		Company saved = companyRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Company a){
		companyRepository.delete(a);
	}
	
	public void flush(){
		companyRepository.flush();
	}
	
	//Other business methods -----
	
	public Collection<Company> getMaxPositionsCompanies(){
		return companyRepository.getMaxPositionsCompanies();
	}
	
	public Company findByPrincipal(){
		return companyRepository.getCompanyByUserAccountId(LoginService.getPrincipal().getId());
	}
	
	public Collection<Company> getMaxAuditScoreCompanies(){
		return companyRepository.getMaxAuditScoreCompanies();
	}
}