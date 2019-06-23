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
import domain.Word;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class WordServiceTest extends AbstractTest{

	// Service -------------------------------------------------------------
	
	@Autowired
	private WordService wordService;
	
	// Test ----------------------------------------------------------------
		
	// ANALYSIS OF DATA COVERAGE:
	// Total coverage: 			94.2%
	// Covered Instructions: 	178
	// Missed Instructions: 	11
	// Total Instructions:		189
	
	@Test
	public void testCreate(){
		
		super.authenticate("admin");
		
		Word res = wordService.create();
		
		Assert.isNull(res.getEnglishName());
		Assert.isNull(res.getSpanishName());
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		
		super.authenticate("admin");
		
		Word res = wordService.create();
		
		res.setEnglishName("Asshole");
		res.setSpanishName("Gilipollas");
		
		
		Word saved = wordService.save(res);
		Assert.isTrue(wordService.findAll().contains(saved));
		
		super.authenticate(null);
	}
	
	@Test
	public void testDelete(){
		
		super.authenticate("admin");
		
		Word res = (Word) wordService.findAll().toArray()[0];
		
		wordService.delete(res);
		Assert.isTrue(!wordService.findAll().contains(res));
		
		super.authenticate(null);
	}

	
	// Functional testing --------------------------------------------------------
	
	// RNF 14 - The system must be easy to customise at run time. Breaking business rule wrong content
	// Sentence coverage: 59.1%, Covered instructions 13, Missed instructions: 9
	@Test(expected = ConstraintViolationException.class)
	public void testWrongContent(){
		
		Word res;
		
		super.authenticate("admin");
		
		res = wordService.create();
		
		res.setEnglishName("");
		res.setSpanishName("Gilipollas");
		
		
		wordService.save(res);
		
		super.authenticate(null);

	}
	
	// RNF 14 - The system must be easy to customise at run time. Breaking business rule wrong authorities
	// Sentence coverage: 100%, Covered instructions 57, Missed instructions: 0
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
	
	// Auxiliary method used by driver
	protected void template(String username, Class<?> expected){
		Class<?> caught = null;
		try{
			authenticate(username);
			Word res = (Word) wordService.findAll().toArray()[0];			
			res.setEnglishName("owo");
			wordService.save(res);
			unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
