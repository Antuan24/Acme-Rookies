package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------	
	@Autowired
	private ConfigurationService configurationService;
	
	// Tests ----------------------------------------------------------------------
	
	// ANALYSIS OF DATA COVERAGE:
	// Total coverage: 			92.6%
	// Covered Instructions: 	199
	// Missed Instructions: 	16
	// Total Instructions:		215
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateConfigurations(){
		Configuration configuration;
		super.authenticate("admin");
		configuration = configurationService.create();
		Assert.isNull(configuration.getBanner());
		Assert.isNull(configuration.getDefaultPhoneCode());
		Assert.isNull(configuration.getFinderCacheTime());
		Assert.isNull(configuration.getFinderQueryResults());
		Assert.isNull(configuration.getSystemName());
		Assert.isNull(configuration.getWelcomeTextEnglish());
		Assert.isNull(configuration.getWelcomeTextSpanish());
		super.authenticate(null);
	}
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveConfigurations(){
		Configuration configuration,saved;
		Collection<Configuration> configurations;
		super.authenticate("admin");
		configuration = configurationService.find();
		
		configuration.setDefaultPhoneCode("+66");

		saved = configurationService.save(configuration);

		configurations = configurationService.findAll();

		Assert.isTrue(configurations.contains(saved));
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testUpdateConfiguration(){
		Configuration configuration = new Configuration();
		super.authenticate("admin");
		configuration = (Configuration) configurationService.findAll().toArray()[0];

		configuration.setBanner("http://www.pixiv.com");
	
		configurationService.save(configuration);
		super.authenticate(null);
	}
	
	// Functional testing ------------------------------------------------------
	
	// RNF 14 - The system must be easy to customise at run time. Breaking business rule wrong format banner.
	// Sentence coverage: 55.6%, Covered instructions 10, Missed instructions: 8
	@Test(expected = ConstraintViolationException.class)
	public void testUpdateWrongBanner(){

		Configuration configuration;

		authenticate("admin");
			
		configuration = configurationService.find();
		configuration.setBanner("39");
	
		configurationService.save(configuration);
		
		unauthenticate();

	}
	
	// RNF 14 - The system must be easy to customise at run time. Breaking business rule empty banner.
	// Sentence coverage: 55.6%, Covered instructions 10, Missed instructions: 8
	@Test(expected = ConstraintViolationException.class)
	public void testUpdateEmptyBanner(){

		Configuration configuration;			
		configuration = configurationService.find();
		
		authenticate("admin");
		configuration.setBanner("");
		configurationService.save(configuration);

		unauthenticate();

	}
	
	
	// RNF 14 - The system must be easy to customise at run time. Breaking business rule wrong authorities.
	// Sentence coverage: 100%, Covered instructions 57
	@Test
	public void driver(){
		Object testingData[][] = {
				{"admin",null},
				{"hacker1",IllegalArgumentException.class},
				{null,IllegalArgumentException.class}
		};
		for(int i=0; i<testingData.length;i++){
			template((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
		}
	}
	
	// Auxilary method used by driver
	protected void template(String username, Class<?> expected){
		Class<?> caught = null;
		try{
			authenticate(username);
			Configuration conf = (Configuration) configurationService.findAll().toArray()[0];		
			conf.setBanner("http://www.pixiv.com");
			configurationService.save(conf);
			unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
