package services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Position;
import domain.Provider;
import domain.Sponsorship;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	//	Coverage: 90.7%
	//	Covered Instructions: 829
	//	Missed  Instructions: 85
	//	Total   Instructions: 914
	
	//	Acme-Rookie A-level: RI 12, RF 13
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private ProviderService providerService;
	
	@Test
	public void testCreate() {
		
		authenticate("provider1");
		
		Sponsorship sponsorship = sponsorshipService.create();
		
		Assert.isNull(sponsorship.getBanner());
		Assert.isNull(sponsorship.getTarget());
		Assert.notNull(sponsorship.getProvider());
		
		unauthenticate();
	}
	
	@Test
	public void driverCreateSponsorship(){
		
		final Object testingData[][] = {{"provider1", null},
										{"provider2", null},
										{"provider3", null},
										{"admin",   java.lang.IllegalArgumentException.class},
										{"rookie1", java.lang.IllegalArgumentException.class},
										{"rookie2", java.lang.IllegalArgumentException.class},
										{"rookie3", java.lang.IllegalArgumentException.class},
										{"company1", java.lang.IllegalArgumentException.class},
										{"company2", java.lang.IllegalArgumentException.class},
										{"company3", java.lang.IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateSponsorship((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateSponsorship(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.sponsorshipService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		
		authenticate("provider1");
		
		Sponsorship result;
		Sponsorship sponsorship = sponsorshipService.create();
		Position position = (Position) positionService.findAll().toArray()[0];
		
		sponsorship.setBanner("http://www.banner.com");
		sponsorship.setTarget("http://www.target.com");
		sponsorship.setPosition(position);
		
		result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated(){
		
		authenticate(null);
		
		Sponsorship result;
		Sponsorship sponsorship = sponsorshipService.create();
		Position position = (Position) positionService.findAll().toArray()[0];
		
		sponsorship.setBanner("http://www.banner.com");
		sponsorship.setTarget("http://www.target.com");
		sponsorship.setPosition(position);
		
		result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("provider1");
		
		Sponsorship result;
		Sponsorship sponsorship = sponsorshipService.create();
		Position position = (Position) positionService.findAll().toArray()[0];
		
		sponsorship.setBanner("banner");
		sponsorship.setTarget("target");
		sponsorship.setPosition(position);
		
		result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverSaveSponsorship(){
		
		Position position = (Position) positionService.findAll().toArray()[0];
		Provider provider = (Provider) providerService.findAll().toArray()[0];
		
		Object testingData[][] = {{"provider1", "http://www.banner.com", "http://www.target.com", provider, position, null},
								  {"provider2", "http://www.banner.com", "http://www.target.com", provider, position, null},
								  {"provider3", "http://www.banner.com", "http://www.target.com", provider, position, null},
								  {"admin",   "http://www.banner.com", "http://www.target.com", provider, position, java.lang.IllegalArgumentException.class},
								  {"rookie1", "http://www.banner.com", "http://www.target.com", provider, position, java.lang.IllegalArgumentException.class},
								  {"rookie2", "http://www.banner.com", "http://www.target.com", provider, position, java.lang.IllegalArgumentException.class},
								  {"rookie3", "http://www.banner.com", "http://www.target.com", provider, position, java.lang.IllegalArgumentException.class},
								  {"company1", "http://www.banner.com", "http://www.target.com", provider, position, java.lang.IllegalArgumentException.class},
								  {"company2", "http://www.banner.com", "http://www.target.com", provider, position, java.lang.IllegalArgumentException.class},
								  {"company3", "http://www.banner.com", "http://www.target.com", provider, position, java.lang.IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveSponsorship((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
					                (Provider) testingData[i][3], (Position) testingData[i][4],(Class<?>)testingData[i][5]);
		}
	}
	
	protected void templateSaveSponsorship(String username, String banner, String target, 
			                               Provider provider, Position position, Class<?> expected){
		Class<?> caught = null;
		Sponsorship sponsorship;
		
		try{
			super.authenticate(username);
			sponsorship = this.sponsorshipService.create();
			sponsorship.setBanner("http://www.banner.com");
			sponsorship.setTarget("http://www.target.com");
			sponsorship.setProvider(provider);
			sponsorship.setPosition(position);
			sponsorship = this.sponsorshipService.save(sponsorship);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testUpdate() {
		
		authenticate("provider1");

		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		
		sponsorship.setBanner("http://www.bannerUpdated.com");
		sponsorship.setTarget("http://www.targetUpdated.com");
		
		Sponsorship result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNotAuthenticated() {
		
		authenticate(null);

		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		
		sponsorship.setBanner("http://www.bannerUpdated.com");
		sponsorship.setTarget("http://www.targetUpdated.com");
		
		Sponsorship result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
		
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testUpdateIncorrectData() {
		
		authenticate("provider1");

		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		
		sponsorship.setBanner("banner");
		sponsorship.setTarget("target");
		
		Sponsorship result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
		
	}
	
//	@Test
//	public void driverUpdateSponsorship(){
//		
//		Position position = (Position) positionService.findAll().toArray()[0];
//		Provider provider = (Provider) providerService.findAll().toArray()[0];
//		
//		Object testingData[][] = {{"provider1", "http://www.bannerUpdated1.com", "http://www.targetUpdated1.com", position, null},
//								  {"provider2", "http://www.bannerUpdated2.com", "http://www.targetUpdated2.com", org.springframework.dao.DataIntegrityViolationException.class},
//								  {"provider3", "http://www.bannerUpdated3.com", "http://www.targetUpdated3.com", org.springframework.dao.DataIntegrityViolationException.class}};
//		
//		for(int i = 0; i < testingData.length; i++){
//			templateUpdateSponsorship((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Position) testingData[i][3], (Class<?>)testingData[i][4]);
//		}
//	}
//	
//	protected void templateUpdateSponsorship(String username, String banner, String target, Position position, Class<?> expected){
//		Class<?> caught = null;
//		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
//		
//		try{
//			super.authenticate(username);
//			sponsorship = this.sponsorshipService.create();
//			sponsorship.setBanner(banner);
//			sponsorship.setTarget(target);
//			sponsorship.setPosition(position);
//			sponsorship = this.sponsorshipService.save(sponsorship);
//		} catch (Throwable oops){
//			caught = oops.getClass();
//		}
//		
//		this.checkExceptions(expected, caught);
//		super.unauthenticate();
//	}
	
	@Test
	public void testDelete(){
		
		authenticate("provider1");
		
		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		sponsorshipService.delete(sponsorship);
		
		Assert.isTrue(!sponsorshipService.findAll().contains(sponsorship));
		
		unauthenticate();
	}
	
	@Test
	public void testDeleteNotAuthenticated(){
		
		authenticate(null);
		
		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		sponsorshipService.delete(sponsorship);
		
		Assert.isTrue(!sponsorshipService.findAll().contains(sponsorship));
		
		unauthenticate();
	}
	
	@Test
	public void driverDeleteSponsorship(){
		
		Object testingData[][] = {{"provider1", null},
								  {"provider2", null},
								  {"provider3", null}, };
//								  {null, java.lang.IllegalArgumentException.class},};
//								  {"auditor1", java.lang.IllegalArgumentException.class},
//								  {"auditor2", java.lang.IllegalArgumentException.class},
//								  {"auditor3", java.lang.IllegalArgumentException.class},
//								  {"admin",   java.lang.IllegalArgumentException.class},
//								  {"rookie1", java.lang.IllegalArgumentException.class},
//								  {"rookie2", java.lang.IllegalArgumentException.class},
//								  {"rookie3", java.lang.IllegalArgumentException.class},
//								  {"company1", java.lang.IllegalArgumentException.class},
//								  {"company2", java.lang.IllegalArgumentException.class},
//								  {"company3", java.lang.IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteSponsorship((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteSponsorship(String username, Class<?> expected){
		Class<?> caught = null;
		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.sponsorshipService.delete(sponsorship);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
