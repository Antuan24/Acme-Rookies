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

import repositories.AuditRepository;
import security.LoginService;
import domain.Audit;
import domain.Auditor;
import domain.Company;

@Service
@Transactional
public class AuditService {

	//Managed Repository ------------------------------------------------------------------------
	
	@Autowired
	private AuditRepository auditRepository;
	
	//Supporting Services -----------------------------------------------------------------------
	
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private Validator validator;

	//Simple CRUD methods -----------------------------------------------------------------------
	
	public Audit create(){
		Audit res = new Audit();
		return res;
	}
	
	public Collection<Audit> findAll(){
		return auditRepository.findAll();
	}
	
	public Audit findOne(int Id){
		Audit result;
		result = auditRepository.findOne(Id);
		return result;
	}
	
	public Audit save(Audit a){
		Assert.isTrue(LoginService.hasRole("AUDITOR"));	
		return auditRepository.saveAndFlush(a);
	}
	
	public void delete(Audit a){
		Assert.isTrue(LoginService.hasRole("AUDITOR"));
		auditRepository.delete(a);
	}
	
	//Other business methods ----------------------------------------------------------------------------
	
	public Audit reconstruct(Audit audit, BindingResult binding){
		Audit res;
		if(audit.getId()!=0){
			System.out.println("recons id no 0");
			res = auditRepository.findOne(audit.getId());
			if(audit.getIsFinal().equals(true)) res.setMoment(new Date());
			else res.setMoment(audit.getMoment());
			res.setScore(audit.getScore());
			res.setText(audit.getText());
			res.setIsFinal(audit.getIsFinal());
			res.setPosition(audit.getPosition());
		}else{
			res = this.create();
			res.setMoment(new Date());
			res.setScore(audit.getScore());
			res.setText(audit.getText());
			res.setIsFinal(false);
			res.setAuditor(auditorService.findByPrincipal());
			res.setPosition(audit.getPosition());
		}
		
		validator.validate(audit, binding);
		
		if(binding.hasErrors()){
			throw new ValidationException();
		}
		
		return res;
	}

	public Collection<Audit> findAuditsByPrincipal(){
		Auditor auditor = auditorService.findByPrincipal();
		return auditRepository.findAuditsByAuditor(auditor.getId());
	}
	
	public Collection<Audit> findAuditsByAuditor(int auditorId){
		return auditRepository.findAuditsByAuditor(auditorId);
	}
	
	
	public Double getAvgAuditScorePerPosition(){
		Double res= auditRepository.getAvgAuditScorePerPosition();
		if(res==null)res=0d;
		return res;
	}
	
	public Double getMinAuditScorePerPosition(){
		Double res = auditRepository.getMinAuditScorePerPosition();
		if(res==null)res=0d;
		return res;
	}
	
	public Double getMaxAuditScorePerPosition(){
		Double res = auditRepository.getMaxAuditScorePerPosition();
		if(res==null)res=0d;
		return res;
	}
	
	public Double getStdevAuditScorePerPosition(){
		Double res = auditRepository.getStdevAuditScorePerPosition();
		if(res==null)res=0d;
		return res;
	}
	
	public Double getAvgAuditScorePerCompany(){
		Double res= auditRepository.getAvgAuditScorePerCompany();
		if(res==null)res=0d;
		return res;
	}
	
	public Double getMinAuditScorePerCompany(){
		Double res = auditRepository.getMinAuditScorePerCompany();
		if(res==null)res=0d;
		return res;
	}
	
	public Double getMaxAuditScorePerCompany(){
		Double res = auditRepository.getMaxAuditScorePerCompany();
		if(res==null)res=0d;
		return res;
	}
	
	public Double getStdevAuditScorePerCompany(){
		Double res = auditRepository.getStdevAuditScorePerCompany();
		if(res==null)res=0d;
		return res;
	}
	
	public Collection<Audit> getAuditsPerCompany(Company c){
		return this.auditRepository.getAuditsPerCompany(c);
	}

}
