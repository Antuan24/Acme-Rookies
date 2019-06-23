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
import domain.Rookie;
import domain.PositionData;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class PositionDataServiceTest extends AbstractTest {
	
	@Autowired
	private PositionDataService positionDataService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Test
	public void testCreate() {
		
		authenticate("rookie1");
		
		PositionData positionData = this.positionDataService.create();
		
		Assert.isTrue(positionData.getTitle()==null);
		Assert.isTrue(positionData.getDescription()==null);
		Assert.isTrue(positionData.getStartDate()==null);
		Assert.isTrue(positionData.getEndDate()==null);
		
		unauthenticate();

	}
	
	@Test
	public void testSave() {

		PositionData positionData, saved;
		Rookie rookie;
		
		authenticate("rookie1");
		
		positionData = this.positionDataService.create();
		
		positionData.setTitle("Title Test");
		positionData.setDescription("DescriptionTest");
		
		final Calendar cal1 = new GregorianCalendar(2000, 04, 04);
		final Calendar cal2 = new GregorianCalendar(2014, 04, 04);
		final Date start = cal1.getTime();
		final Date end = cal2.getTime();
		
		positionData.setStartDate(start);
		positionData.setEndDate(end);
		
		saved = this.positionDataService.saveTrue(positionData);
		Assert.isTrue(positionDataService.findAll().contains(saved));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated() {
		PositionData positionData, saved;
		Rookie rookie;
		
		authenticate(null);
		
		positionData = this.positionDataService.create();
		Curricula curricula = this.curriculaService.create();
		
		curricula.getPositionDatas().add(positionData);
		
		positionData.setTitle("Title Test");
		positionData.setDescription("DescriptionTest");
		
		final Calendar cal1 = new GregorianCalendar(2000, 04, 04);
		final Calendar cal2 = new GregorianCalendar(2014, 04, 04);
		final Date start = cal1.getTime();
		final Date end = cal2.getTime();
		
		positionData.setStartDate(start);
		positionData.setEndDate(end);
		
		saved = this.positionDataService.saveTrue(positionData);
		Assert.isTrue(positionDataService.findAll().contains(saved));
		
		unauthenticate();
	
	}
	
	@Test
	public void testDelete() {
		
		authenticate("rookie1");
		
		PositionData positionData = (PositionData) positionDataService.findAll().toArray()[1];
		Curricula curricula = this.curriculaService.findByPositionData(positionData);
		
		positionDataService.delete(positionData, curricula);

		unauthenticate();
	}

}
