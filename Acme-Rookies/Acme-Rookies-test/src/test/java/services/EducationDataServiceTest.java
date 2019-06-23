package services;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curricula;
import domain.EducationData;
import domain.Rookie;
import domain.PositionData;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class EducationDataServiceTest extends AbstractTest {
	
	@Autowired
	private EducationDataService educationDataService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Test
	public void testCreate() {
		
		authenticate("rookie1");
		
		EducationData educationData = this.educationDataService.create();
		
		Assert.isTrue(educationData.getDegree()==null);
		Assert.isTrue(educationData.getInstitution()==null);
		Assert.isTrue(educationData.getMark()==null);
		Assert.isTrue(educationData.getStartDate()==null);
		Assert.isTrue(educationData.getEndDate()==null);
		
		unauthenticate();

	}
	
	@Test
	public void testSave() {
		EducationData educationData, saved;
		Rookie rookie;
		
		authenticate("rookie1");
		
		educationData = this.educationDataService.create();
		
		educationData.setDegree("Degree Test");
		educationData.setInstitution("Institution Test");
		educationData.setMark(9.0);
		
		final Calendar cal1 = new GregorianCalendar(2000, 04, 04);
		final Calendar cal2 = new GregorianCalendar(2014, 04, 04);
		final Date start = cal1.getTime();
		final Date end = cal2.getTime();
		
		educationData.setStartDate(start);
		educationData.setEndDate(end);
		
		saved = this.educationDataService.saveTrue(educationData);
		Assert.isTrue(educationDataService.findAll().contains(saved));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated() {
		EducationData educationData, saved;
		Rookie rookie;
		
		authenticate(null);
		
		educationData = this.educationDataService.create();
		
		educationData.setDegree("Degree Test");
		educationData.setInstitution("Institution Test");
		educationData.setMark(9.0);
		
		final Calendar cal1 = new GregorianCalendar(2000, 04, 04);
		final Calendar cal2 = new GregorianCalendar(2014, 04, 04);
		final Date start = cal1.getTime();
		final Date end = cal2.getTime();
		
		educationData.setStartDate(start);
		educationData.setEndDate(end);
		
		saved = this.educationDataService.saveTrue(educationData);
		Assert.isTrue(educationDataService.findAll().contains(saved));
		
		unauthenticate();
	
	}
	
	@Test
	public void testDelete() {
		
		authenticate("rookie1");
		
		EducationData educationData = (EducationData) educationDataService.findAll().toArray()[0];
		Curricula curricula = this.curriculaService.findByEducationData(educationData);
		
		educationDataService.delete(educationData, curricula);

		unauthenticate();
	}

}
