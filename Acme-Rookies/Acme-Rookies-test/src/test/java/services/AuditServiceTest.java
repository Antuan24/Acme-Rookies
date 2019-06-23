package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Audit;
import domain.Auditor;
import domain.Position;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class AuditServiceTest extends AbstractTest {

	//	Coverage: 99.6%
	//	Covered Instructions: 1.346
	//	Missed  Instructions: 6
	//	Total   Instructions: 1.352
	
	//	Acme-Rookie C-level: RI 2, RF 3
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private PositionService positionService;
	
	@Test
	public void testCreate() {
		
		Audit audit = auditService.create();
		
		Assert.isNull(audit.getText());
		Assert.isNull(audit.getScore());
		
	}
	
	@Test
	public void driverCreateAudit(){
		
		final Object testingData[][] = {{"auditor1", null},
										{"auditor2", null},
										{"auditor3", null}};
//										{"provider1", java.lang.IllegalArgumentException.class},
//										{"provider2", java.lang.IllegalArgumentException.class},
//										{"provider3", java.lang.IllegalArgumentException.class},
//										{"admin",   java.lang.IllegalArgumentException.class},
//										{"rookie1", java.lang.IllegalArgumentException.class},
//										{"rookie2", java.lang.IllegalArgumentException.class},
//										{"rookie3", java.lang.IllegalArgumentException.class},
//										{"rookie4", java.lang.IllegalArgumentException.class},
//										{"rookie5", java.lang.IllegalArgumentException.class},
//										{"rookie6", java.lang.IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateAudit((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateAudit(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.auditService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		
		authenticate("auditor1");
		
		Audit audit = auditService.create();
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		Auditor auditor = (Auditor) auditorService.findAll().toArray()[0];
		Position position = (Position) positionService.findAll().toArray()[0];
		
		audit.setText("Text audit");
		audit.setScore(6.5);
		audit.setMoment(moment);
		audit.setIsFinal(false);
		audit.setAuditor(auditor);
		audit.setPosition(position);
		
		Audit result = auditService.save(audit);
		Assert.isTrue(auditService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverSaveAudit(){
		
		Position position = (Position) positionService.findAll().toArray()[0];
		Auditor auditor = (Auditor) auditorService.findAll().toArray()[0];
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		Date moment2 = new Date(System.currentTimeMillis() + 10000);
		
		Object testingData[][] = {{"auditor1", "text1", moment, 6.5, false, auditor, position, null},
								  {"auditor2", "text2", moment, 7.5, false, auditor, position, null},
				                  {"auditor3", "text3", moment, 8.5, false, auditor, position, null},
								  {"provider1", "text1", moment, 6.5, false, auditor, position, java.lang.IllegalArgumentException.class},
								  {"provider2", "text2", moment, 7.5, false, auditor, position, java.lang.IllegalArgumentException.class},
								  {"company1", "text1", moment, 6.5, false, auditor, position, java.lang.IllegalArgumentException.class},
								  {"company2", "text2", moment, 7.5, false, auditor, position, java.lang.IllegalArgumentException.class},
								  {"rookie1", "text1", moment, 6.5, false, auditor, position, java.lang.IllegalArgumentException.class},
								  {"rookie2", "text2", moment, 7.5, false, auditor, position, java.lang.IllegalArgumentException.class},
								  {"auditor2", "text2", moment2, 7.5, false, auditor, position, javax.validation.ConstraintViolationException.class},
								  {"auditor2", "", moment2, 7.5, false, auditor, position, javax.validation.ConstraintViolationException.class},
								  {"auditor2", "text2", moment,  7.5, true,  auditor, position, javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveAudit((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], 
					          (Double) testingData[i][3], (Boolean) testingData[i][4], (Auditor) testingData[i][5],
					          (Position) testingData[i][6],(Class<?>)testingData[i][7]);
		}
	}
	
	protected void templateSaveAudit(String username, String text, Date moment, Double score, 
			                         Boolean isFinal, Auditor auditor, Position position, Class<?> expected){
		Class<?> caught = null;
		Audit audit;
		
		try{
			super.authenticate(username);
			audit = this.auditService.create();
			audit.setText(text);
			audit.setScore(score);
			audit.setMoment(moment);
			audit.setIsFinal(isFinal);
			audit.setAuditor(auditor);
			audit.setPosition(position);
			audit = this.auditService.save(audit);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testUpdate(){
		
		authenticate("auditor1");
		
		Audit audit = (Audit) auditService.findAll().toArray()[1];
		
		audit.setText("Text audit updated");
		audit.setScore(7.5);
		
		Audit result = auditService.save(audit);
		Assert.isTrue(auditService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverUpdateAudit(){
		
		Object testingData[][] = {{"auditor1", "textUpdated1", 6.5, null},
								  {"auditor2", "textUpdated2", 7.5, null},
								  {"auditor3", "textUpdated3", 8.5, null}, 
								  {null, "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"provider1", "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"provider2", "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"provider3", "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"admin",   "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"rookie1", "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"rookie2", "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"rookie3", "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"rookie4", "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"rookie5", "textUpdated2", 7.5, java.lang.IllegalArgumentException.class},
								  {"rookie6", "textUpdated2", 7.5, java.lang.IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateAudit((String) testingData[i][0], (String) testingData[i][1],
					            (Double) testingData[i][2], (Class<?>)testingData[i][3]);
		}
	}
	
	protected void templateUpdateAudit(String username, String text, Double score, Class<?> expected){
		Class<?> caught = null;
		Audit audit = (Audit) auditService.findAll().toArray()[1];
		
		try{
			super.authenticate(username);
			audit.setText(text);
			audit.setScore(score);
			audit = this.auditService.save(audit);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testDelete(){
		
		authenticate("auditor1");
		
		Audit audit = (Audit) auditService.findAll().toArray()[1];
		
		auditService.delete(audit);
		
		Assert.isTrue(!auditService.findAll().contains(audit));
		
		unauthenticate();
	}
	
	@Test
	public void driverDeleteAudit(){
		
		Object testingData[][] = {{"auditor1", null},
								  {"auditor2", null},
								  {"auditor3", null}, 
								  {null, java.lang.IllegalArgumentException.class},
								  {"provider1", java.lang.IllegalArgumentException.class},
								  {"provider2", java.lang.IllegalArgumentException.class},
								  {"provider3", java.lang.IllegalArgumentException.class},
								  {"admin",   java.lang.IllegalArgumentException.class},
								  {"rookie1", java.lang.IllegalArgumentException.class},
								  {"rookie2", java.lang.IllegalArgumentException.class},
								  {"rookie3", java.lang.IllegalArgumentException.class},
								  {"rookie4", java.lang.IllegalArgumentException.class},
								  {"rookie5", java.lang.IllegalArgumentException.class},
								  {"rookie6", java.lang.IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteAudit((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteAudit(String username, Class<?> expected){
		Class<?> caught = null;
		Audit audit = (Audit) auditService.findAll().toArray()[1];
		
		try{
			super.authenticate(username);
			this.auditService.delete(audit);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
