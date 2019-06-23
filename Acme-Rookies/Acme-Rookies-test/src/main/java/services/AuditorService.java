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

import repositories.AuditRepository;
import repositories.AuditorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Admin;
import domain.Audit;
import domain.Auditor;
import domain.Rookie;
import domain.SocialProfile;

@Service
@Transactional
public class AuditorService {

	//Managed Repository ------------------------------------------------------------------------
	
	@Autowired
	private AuditorRepository auditorRepository;
	
	//Supporting Services -----------------------------------------------------------------------

	//Simple CRUD methods -----------------------------------------------------------------------
	
	public Auditor create(UserAccount ua){
		Auditor res = new Auditor();

		res.setIsBanned(false);
		res.setIsSpammer(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Auditor> findAll(){
		return auditorRepository.findAll();
	}
	
	public Auditor findOne(int Id){
		Auditor result;
		result = auditorRepository.findOne(Id);
		return result;
	}
	
	public Auditor save(Auditor a){
		Assert.isTrue(LoginService.hasRole("ADMIN"));	
		return auditorRepository.saveAndFlush(a);
	}
	
	public void delete(Auditor a){
		//Assert.isTrue(LoginService.hasRole("ADMIN"));
		auditorRepository.delete(a);
	}
	
	//Other business methods ----------------------------------------------------------------------------

	public Auditor findByPrincipal(){
		return auditorRepository.getAuditorByUserAccountId(LoginService.getPrincipal().getId());
	}

}
