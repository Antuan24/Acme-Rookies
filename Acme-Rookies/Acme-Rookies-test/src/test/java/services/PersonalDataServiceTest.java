package services;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curricula;
import domain.Rookie;
import domain.PersonalData;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class PersonalDataServiceTest extends AbstractTest {
	
	@Autowired
	private PersonalDataService personalDataService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Test
	public void testCreate() {
		
		authenticate("rookie1");
		
		PersonalData personalData = this.personalDataService.create();
		
		Assert.isTrue(personalData.getFullName()==null);
		Assert.isTrue(personalData.getStatement()==null);
		Assert.isTrue(personalData.getPhone()==null);
		Assert.isTrue(personalData.getGithubUrl()==null);
		Assert.isTrue(personalData.getLinkedInUrl()==null);
		
		unauthenticate();

	}
	
	@Test
	public void testSave() {

		PersonalData personalData, saved;
		Rookie rookie;
		
		authenticate("rookie1");
		
		personalData = this.personalDataService.create();
		Curricula curricula = this.curriculaService.create();
		
		curricula.setPersonalData(personalData);
		
		personalData.setFullName("Full Name Test");
		personalData.setStatement("Statement Test");
		personalData.setPhone("366363636");
		personalData.setGithubUrl("http://www.github.com");
		personalData.setLinkedInUrl("http://www.linkedin.com");
		
		saved = this.personalDataService.saveTrue(personalData);
		Assert.isTrue(personalDataService.findAll().contains(saved));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated() {
		PersonalData personalData, saved;
		Rookie rookie;
		
		authenticate(null);
		
		personalData = this.personalDataService.create();
		Curricula curricula = this.curriculaService.findByPersonalData(personalData);
		
		personalData.setFullName("Full Name Test");
		personalData.setStatement("Statement Test");
		personalData.setPhone("366363636");
		personalData.setGithubUrl("http://www.github.com");
		personalData.setLinkedInUrl("http://www.linkedin.com");
		
		saved = personalDataService.save(personalData, curricula);
		Assert.isTrue(curriculaService.findAll().contains(saved));
		
		unauthenticate();
	
	}
	
	@Test
	public void testDelete() {
		
		authenticate("rookie1");
		
		PersonalData personalData = (PersonalData) personalDataService.findAll().toArray()[1];
		
		personalDataService.delete(personalData);

		unauthenticate();
	}

}
