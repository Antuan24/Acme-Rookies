package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuoletRepository;
import security.LoginService;
import domain.Auditor;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	//Managed Repository ------------------------------------------------------------------------
	
	@Autowired
	private QuoletRepository quoletRepository;
	
	//Supporting Services -----------------------------------------------------------------------
	
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private Validator validator;

	//Simple CRUD methods -----------------------------------------------------------------------
	
	public Quolet create(int id){

		Quolet res = new Quolet();
		
		res.setIsFinal(false);
		res.setAudit(auditService.findOne(id));
		res.setTicker(generateTicker());

		return res;
	}
	
	public Quolet save(Quolet a){
		Assert.isTrue(LoginService.hasRole("AUDITOR"));
		if(a.getIsFinal())
			a.setPublicationMoment(new Date());
		return quoletRepository.saveAndFlush(a);
	}
	
	public void delete(Quolet a){
		quoletRepository.delete(a);
	}
	
	public Collection<Quolet> findAll(){
		return quoletRepository.findAll();
	}
	
	public Quolet findOne(int Id){
		return quoletRepository.findOne(Id);
	}
	
	//Other business methods ----------------------------------------------------------------------------
	
	public Quolet reconstruct(Quolet quolet, int quoletId, int auditId, BindingResult binding){
		Quolet result;
		
		if(quoletId!=0){
			result = this.findOne(quoletId);
			System.out.println("1: "+result.getTicker());
			result.setBody(quolet.getBody());
			result.setPicture(quolet.getPicture());
			result.setIsFinal(quolet.getIsFinal());
		}else{
			result = this.create(auditId);
			System.out.println("2: "+result.getTicker());
			result.setBody(quolet.getBody());
			result.setPicture(quolet.getPicture());
		}
		
		validator.validate(result, binding);
		
		if(binding.hasErrors()){
			System.out.println(binding.getAllErrors());
			throw new ValidationException();
		}
		
		return result;
	}
	
	public String generateTicker(){
		Date date = new Date(); // your date
		Calendar n = Calendar.getInstance();
		n.setTime(date);
		String t = "";
		String s = Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
		String m = Integer.toString(n.get(Calendar.MONTH)+1);
		if(s.length()==1) s= "0"+Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
		if(m.length()==1) m = "0"+ Integer.toString(n.get(Calendar.MONTH) +1);
		t = t + randomWordAndNumber()+ "-" + Integer.toString(n.get(Calendar.YEAR) - 2000)
				+ m
				+ s;

		return t;
	}
	
	private String randomWordAndNumber(){
		 String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 4) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	}
	
	public String getStatus(Quolet quolet){
		String res;
		Calendar current, moment;
		int m, d;
		
		current = Calendar.getInstance();
		current.setTime(new Date());
		moment = Calendar.getInstance();
		moment.setTime(quolet.getPublicationMoment());
		
		m = Math.abs(current.get(Calendar.MONTH) - moment.get(Calendar.MONTH));
		d = current.get(Calendar.DAY_OF_MONTH) - moment.get(Calendar.DAY_OF_MONTH);
				
		if((m == 0) || (m == 1 && d < 0)){
			res = "MIN";
		}else{
			if(m ==1 && d > 0){
				res = "MED";
			}else{
				res = "MAX";
			}
		}
		
		return res;
	}
	
	public Collection<Quolet> findQuoletByPrincipal(){
		Auditor auditor = auditorService.findByPrincipal();
		return quoletRepository.getQuoletByAuditor(auditor.getId());
	}
	
	public Collection<Quolet> findQuoletFinal(int id){
		return quoletRepository.getQuoletIsFinal(id);
	}
	
}
