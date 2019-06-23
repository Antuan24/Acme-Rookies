package services;

import java.util.Collections;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;

import domain.Curricula;
import domain.EducationData;
import domain.Rookie;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class CurriculaServiceTest extends AbstractTest{
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private PersonalDataService personalDataService;
	
	@Test
	public void testCreate() {
		
		authenticate("rookie1");
		
		Curricula curricula = curriculaService.create();

		Assert.isTrue(curricula.getPositionDatas().isEmpty());
		Assert.isTrue(curricula.getEducationDatas().isEmpty());
		Assert.isTrue(curricula.getMiscellaneousDatas().isEmpty());
		
		unauthenticate();

	}
	
	@Test
	public void testSave() {

		Curricula curricula, saved;
		Rookie rookie;
		PersonalData personalData;
		
		authenticate("rookie1");
		
		curricula = this.curriculaService.create();
		rookie = rookieService.findByPrincipal();
		personalData = personalDataService.findOne(getEntityId("personalData1"));
		
		curricula.setName("Name Test");
		curricula.setPersonalData(personalData);
		curricula.setPositionDatas(Collections.<PositionData> emptySet());
		curricula.setEducationDatas(Collections.<EducationData> emptySet());
		curricula.setMiscellaneousDatas(Collections.<MiscellaneousData> emptySet());
		curricula.setRookie(rookie);
		
		saved = curriculaService.save(curricula);
		this.rookieService.save(rookie);
		Assert.isTrue(curriculaService.findAll().contains(saved));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated() {
	Curricula curricula, saved;
	Rookie rookie;
	PersonalData personalData;
	
	authenticate(null);
	
	curricula = this.curriculaService.create();
	rookie = rookieService.findByPrincipal();
	personalData = personalDataService.findOne(getEntityId("personalData1"));
	
	curricula.setName("Name Test");
	curricula.setPersonalData(personalData);
	curricula.setPositionDatas(Collections.<PositionData> emptySet());
	curricula.setEducationDatas(Collections.<EducationData> emptySet());
	curricula.setMiscellaneousDatas(Collections.<MiscellaneousData> emptySet());
	curricula.setRookie(rookie);
	
	saved = curriculaService.save(curricula);
	this.rookieService.save(rookie);
	Assert.isTrue(curriculaService.findAll().contains(saved));
	
	unauthenticate();
	
	}
	
	@Test
	public void testDelete() {
		
		authenticate("rookie1");
		
		Curricula curricula = (Curricula) curriculaService.findAll().toArray()[1];
		
		curriculaService.delete(curricula);

		unauthenticate();
	}
	
}
