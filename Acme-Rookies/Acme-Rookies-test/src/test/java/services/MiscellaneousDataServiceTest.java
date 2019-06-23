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
import domain.MiscellaneousData;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class MiscellaneousDataServiceTest extends AbstractTest {
	
	@Autowired
	private MiscellaneousDataService miscellaneousDataService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Test
	public void testCreate() {
		
		authenticate("rookie1");
		
		MiscellaneousData miscellaneousData = this.miscellaneousDataService.create();
		
		Assert.isTrue(miscellaneousData.getText()==null);
		Assert.isTrue(miscellaneousData.getAttachments().isEmpty());
		
		unauthenticate();

	}
	
	@Test
	public void testSave() {
		MiscellaneousData miscellaneousData, saved;
		Rookie rookie;
		
		authenticate("rookie1");
		
		miscellaneousData = this.miscellaneousDataService.create();
		
		miscellaneousData.setText("Text Test");
		
		saved = this.miscellaneousDataService.saveTrue(miscellaneousData);
		Assert.isTrue(miscellaneousDataService.findAll().contains(saved));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated() {
		MiscellaneousData miscellaneousData, saved;
		Rookie rookie;
		
		authenticate(null);
		
		miscellaneousData = this.miscellaneousDataService.create();
		
		miscellaneousData.setText("Text Test");
		
		saved = this.miscellaneousDataService.saveTrue(miscellaneousData);
		Assert.isTrue(miscellaneousDataService.findAll().contains(saved));
		
		unauthenticate();
	
	}
	
	@Test
	public void testDelete() {
		
		authenticate("rookie1");
		
		MiscellaneousData miscellaneousData = (MiscellaneousData) miscellaneousDataService.findAll().toArray()[0];
		Curricula curricula = this.curriculaService.findByMiscellaneousData(miscellaneousData);
		
		miscellaneousDataService.delete(miscellaneousData, curricula);

		unauthenticate();
	}

}
