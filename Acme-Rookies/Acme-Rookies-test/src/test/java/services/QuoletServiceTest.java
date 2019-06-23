package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Quolet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class QuoletServiceTest extends AbstractTest {

	// Managed service --------------------------------------------------
	
	@Autowired
	private QuoletService quoletService;
	
	// Support services -------------------------------------------------
	
	

	// Tests ------------------------------------------------------------
	
	@Test
	public void testCreate() {
		
		Quolet quolet;
		
		authenticate("auditor1");
		
		quolet = quoletService.create(getEntityId("audit1"));
		
		Assert.isNull(quolet.getBody());
		Assert.isNull(quolet.getPicture());
		Assert.isNull(quolet.getPublicationMoment());
		
		Assert.isTrue(!quolet.getIsFinal());
		Assert.notNull(quolet.getAudit());
		Assert.notNull(quolet.getTicker());
		
		unauthenticate();
		
	}

	@Test
	public void testSave() {

		Quolet quolet, saved;
		
		authenticate("auditor1");
		
		quolet = quoletService.create(getEntityId("audit1"));
		
		quolet.setBody("A lot of work");
		quolet.setPicture("https://www.fotocasa.com");
		
		saved = quoletService.save(quolet);
		Assert.isTrue(quoletService.findAll().contains(saved));
		
		unauthenticate();
	}
	
	@Test
	public void testDelete() {
		
		Quolet quolet, saved;
		
		authenticate("auditor1");
		
		quolet = quoletService.create(getEntityId("audit1"));
		
		quolet.setBody("A lot of work");
		quolet.setPicture("https://www.fotocasa.com");
		
		saved = quoletService.save(quolet);
		quoletService.delete(saved);
		
		Assert.isTrue(!quoletService.findAll().contains(saved));
		
		unauthenticate();
	}
	
	// Functional testing -----------------------------------------------------------
	

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSaveIncorrectAuthenticated(){
		
		authenticate("rookie1");
		
		Quolet quolet;
		
		quolet = quoletService.create(getEntityId("audit1"));
		
		quolet.setBody("A lot of work");
		quolet.setPicture("https://www.fotocasa.com");
		
		quoletService.save(quolet);
		
		unauthenticate();

	}
	
	@Test
	public void testUpdateQuolet(){
		
		Quolet quolet;
		
		authenticate("auditor1");
		
		quolet = quoletService.findOne(getEntityId("quoletTest1"));
		
		quolet.setBody("hola");
		
		quoletService.save(quolet);
		
		unauthenticate();
		
	}

}
