package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;
import domain.Rookie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// Service under test --------------------

	@Autowired
	private FinderService	finderService;
	
	@Autowired
	private RookieService rookieService;

	// Tests --------------------
	
	// ANALYSIS OF DATA COVERAGE:
	// Total coverage: 			92.1%
	// Covered Instructions: 	116
	// Missed Instructions: 	10
	// Total Instructions:		126
	

	@Test
	public void testCreate() {
		
		Finder finder = finderService.create();
		
		Assert.isTrue(finder.getPositions().isEmpty());
		Assert.isNull(finder.getKeyword());
		Assert.isNull(finder.getMinSalary());
		Assert.isNull(finder.getMaxSalary());
		Assert.isNull(finder.getMaxDeadline());
		Assert.isNull(finder.getRookie());
		Assert.isNull(finder.getMoment());
		
	}

	@Test
	public void testSave() {	
		
		Finder finder,saved;
		
		authenticate("rookie1");
		
		finder = finderService.findByPrincipal();
		finder.setKeyword("santa");
		
		saved = this.finderService.save(finder);
		Assert.isTrue(finderService.findAll().contains(saved));
		
		unauthenticate();
	}

	@Test
	public void testDelete() {
		
		authenticate("rookie3");
		
		Finder finder = (Finder) this.finderService.findAll().toArray()[0];
		
		finderService.delete(finder);
		Assert.isTrue(!finderService.findAll().contains(finder));
		
		unauthenticate();
	}
	
	// Functional testing -----------------------------------------------------------------------
	
	// RF 17.2 - Manage his or her finder, which involves updating the search criteria, listing its contents,and clearing it.
	// Test: Breaking business rule wrong finder authority
	// Sentence coverage: 69.2%, Covered instructions 18, Missed instructions: 8
	@Test(expected = IllegalArgumentException.class)
	public void testSaveOtherRookie(){
		
		Finder finder;
		Rookie rookie;
		
		authenticate("rookie1");
		
		rookie = rookieService.findOne(getEntityId("rookie2"));
		finder = finderService.findByRookie(rookie);
		
		finder.setKeyword("Australia");
		
		finderService.save(finder);
		
		unauthenticate();

	}
	
	
	// RF 17.2 - Manage his or her finder, which involves updating the search criteria, listing its contents,and clearing it
	// Test finder authority
	// Sentence coverage: 100%, Covered instructions 18, Missed instructions: 0
	@Test
	public void testSelfAssigned(){
		
		Finder finder;
		
		authenticate("rookie1");
		
		finder = finderService.findByPrincipal();
		finder.setKeyword("santa");
		
		finderService.save(finder);
		
		unauthenticate();
		
	}

}
