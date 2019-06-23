package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RookieRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Curricula;
import domain.Rookie;
import domain.SocialProfile;


@Service
@Transactional
public class RookieService {

	//Managed Repository -----
	
	@Autowired
	private RookieRepository rookieRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Rookie create(UserAccount ua){
		Rookie res = new Rookie();
		res.setApplications(new ArrayList<Application>());
		res.setCurriculas(new ArrayList<Curricula>());
		res.setIsBanned(false);
		res.setIsSpammer(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Rookie> findAll(){
		return rookieRepository.findAll();
	}
	
	public Rookie findOne(int Id){
		return rookieRepository.findOne(Id);
	}
	
	public Rookie save(Rookie a){
		
		Rookie saved = rookieRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Rookie a){
		rookieRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Collection<Curricula> getCurriculas(){
		Rookie rookie;
		Collection<Curricula> curriculas;
		
		rookie = this.findByPrincipal();
		curriculas = rookie.getCurriculas();
		
		for(Curricula c:curriculas){
			if(c.getName().contains("Copy of"))
				curriculas.remove(c);
		}
		
		return curriculas;
	}
	
	public Collection<Rookie> getMaxApplicationsRookies(){
		return rookieRepository.getMaxApplicationsRookies();
	}
	
	public Rookie findByPrincipal(){
		return rookieRepository.getRookieByUserAccountId(LoginService.getPrincipal().getId());
	}
}