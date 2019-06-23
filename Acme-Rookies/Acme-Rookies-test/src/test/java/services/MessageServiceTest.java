package services;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Admin;
import domain.Application;
import domain.Company;
import domain.CreditCard;
import domain.Curricula;
import domain.Rookie;
import domain.Message;
import domain.Position;
import domain.Problem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Managed service --------------------------------------------------
	
	@Autowired
	private MessageService messageService;
	
	// Support services -------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private CurriculaService curriculaService;

	// Tests ------------------------------------------------------------
	
	@Test
	public void testCreateAndSave() {
		authenticate("rookie1");
		Actor actor = actorService.findOne(getEntityId("rookie1"));
		
		Message m = messageService.create(actor);
		m.setBody("body");
		m.setMoment(new Date(System.currentTimeMillis()-1000));
		m.setRecipient(actor);
		m.setSender(actor);
		m.setSubject("hello");
		m.setTags(new ArrayList<String>());
		Message saved = messageService.save(m);
		Assert.isTrue(saved.getId()!=0);

		unauthenticate();
		
	}@Test
	public void testDelete() {
		authenticate("rookie1");
		Actor actor = actorService.findOne(getEntityId("rookie1"));
		
		Message m = messageService.create(actor);
		m.setBody("body");
		m.setMoment(new Date(System.currentTimeMillis()-1000));
		m.setRecipient(actor);
		m.setSender(actor);
		m.setSubject("hello");
		m.setTags(new ArrayList<String>());
		Message saved = messageService.save(m);
		Assert.isTrue(saved.getId()!=0);

		messageService.delete(saved);
		Message me = messageService.findOne(saved.getId());
		Assert.isTrue(me.getTags().contains("DELETED"));
		
		messageService.delete(me);
		Assert.isNull(messageService.findOne(saved.getId()));
		unauthenticate();
		
	}
	

	
	@Test
	public void driverMessage(){
		Object testingData[][] = {
				{"rookie1", null},
				{null, null}
		};
		for(int i=0; i<testingData.length;i++){
			templateMessage((String)testingData[i][0],
					(Class<?>) testingData[i][1]);
		}
	}
	
	protected void templateMessage(String o, Class<?> expected){
		Class<?> caught = null;
		try{
			authenticate(o);
			
			Actor actor = actorService.findOne(getEntityId("rookie1"));
			
			Message m = messageService.create(actor);
			m.setBody("body");
			m.setMoment(new Date(System.currentTimeMillis()-1000));
			m.setRecipient(actor);
			m.setSender(actor);
			m.setSubject("hello");
			m.setTags(new ArrayList<String>());
			
			messageService.save(m);
			
			
		}catch(Throwable oops){
			oops.printStackTrace();
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	


	

}
