package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.SocialProfile;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

//	Coverage: 93.2%
//	Covered Instructions: 746
//	Missed  Instructions: 54
//	Total   Instructions: 800
	
	@Autowired
	private SocialProfileService socialProfileService;
	
	//	Se comprueba que se crean los social profile correctamente.
	@Test
	public void testCreate() {
		SocialProfile socialProfile = socialProfileService.create();
		
		Assert.isNull(socialProfile.getNick());
		Assert.isNull(socialProfile.getSocialNetwork());
		Assert.isNull(socialProfile.getLink());
	}
	
	@Test
	public void driverCreateSocialProfile(){
		
		final Object testingData[][] = {{"company1", null},
										{"company2", null},
										{"company3", null},
										{"admin",    null},
										{"rookie1",  null},
										{"rookie2",  null},
										{"rookie3",  null},
										{"rookie4",  null},
										{"rookie5",  null},
										{"rookie6",  null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateSocialProfile((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateSocialProfile(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.socialProfileService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que se guardan los social profile correctamente.
	@Test
	public void testSave() {
		
		authenticate("admin");

		SocialProfile result;
		SocialProfile socialProfile = socialProfileService.create();
		
		socialProfile.setNick("Nick");
		socialProfile.setSocialNetwork("Twitter");
		socialProfile.setLink("https://www.linksocial.com");
		
		result = socialProfileService.save(socialProfile);
		Assert.isTrue(socialProfileService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que si se intenta guardar una social profile 
	//	sin estar autenticado, salta una Illegal Argument Exception.
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated(){
		authenticate(null);

		SocialProfile socialProfile = socialProfileService.create();
		
		socialProfile.setNick("Nick");
		socialProfile.setSocialNetwork("Twitter");
		socialProfile.setLink("https://www.linksocial.com");
		
		SocialProfile result = socialProfileService.save(socialProfile);
		Assert.isTrue(socialProfileService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverSaveSocialProfile(){
		
		Object testingData[][] = {{"company1", "nick", "socialNetwork", "https://www.linksocial.com", null},
								  {"company2", "nick", "socialNetwork", "https://www.linksocial.com", null},
								  {"company3", "nick", "socialNetwork", "https://www.linksocial.com", null},
								  {"admin", "", "", "", javax.validation.ConstraintViolationException.class},
								  {"rookie1", "", "socialNetwork", "https://www.linksocial.com", javax.validation.ConstraintViolationException.class},
								  {"rookie2", "nick", "", "https://www.linksocial.com",          javax.validation.ConstraintViolationException.class},
								  {"rookie3", "nick", "socialNetwork", "link",                   javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveSocialProfile((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
								      (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateSaveSocialProfile(String username, String nick, String socialNetwork, String link, Class<?> expected){
		Class<?> caught = null;
		
		try{
			super.authenticate(username);
			SocialProfile socialProfile = socialProfileService.create();
			socialProfile.setNick(nick);
			socialProfile.setSocialNetwork(socialNetwork);
			socialProfile.setLink(link);
			socialProfile = this.socialProfileService.save(socialProfile);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que se editan los social profile correctamente.
	@Test
	public void testUpdate() {
		
		authenticate("admin");

		SocialProfile socialProfile = (SocialProfile) socialProfileService.findAll().toArray()[0];
		
		socialProfile.setNick("Nick");
		socialProfile.setSocialNetwork("Twitter");
		socialProfile.setLink("https://www.linksocial.com");
		
		SocialProfile result = socialProfileService.save(socialProfile);
		Assert.isTrue(socialProfileService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que si se intenta editar una social profile sin rellenar 
	//	los datos obligatorios, salta una Constraint Violation Exception.
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testUpdateNoData(){
		authenticate("admin");

		SocialProfile socialProfile = (SocialProfile) socialProfileService.findAll().toArray()[0];
		
		socialProfile.setNick("");
		socialProfile.setSocialNetwork("");
		socialProfile.setLink("https://www.linksocial.com");
		
		SocialProfile result = socialProfileService.save(socialProfile);
		Assert.isTrue(socialProfileService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverUpdateSocialProfile(){
		
		Object testingData[][] = {{"company1", "nick", "socialNetwork", "https://www.linksocial.com", null},
								  {"company2", "nick", "socialNetwork", "https://www.linksocial.com", null},
								  {"company3", "nick", "socialNetwork", "https://www.linksocial.com", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateSocialProfile((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
								        (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateUpdateSocialProfile(String username, String nick, String socialNetwork, String link, Class<?> expected){
		Class<?> caught = null;
		SocialProfile socialProfile = (SocialProfile) socialProfileService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			socialProfile.setNick(nick);
			socialProfile.setSocialNetwork(socialNetwork);
			socialProfile.setLink(link);
			socialProfile = this.socialProfileService.save(socialProfile);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que se borran los social profile correctamente.
	@Test
	public void testDelete() {
		
		authenticate("admin");
		
		SocialProfile result;
		SocialProfile socialProfile = socialProfileService.create();
		
		socialProfile.setNick("Nick");
		socialProfile.setSocialNetwork("Twitter");
		socialProfile.setLink("https://www.linksocial.com");
		
		result = socialProfileService.save(socialProfile);
		
		socialProfileService.delete(result);
		Assert.isTrue(!socialProfileService.findAll().contains(socialProfile));

		unauthenticate();
	}
	
	//	Se comprueba que si un actor intenta borrar los social profile 
	//	de otro actor, salta una Data Integrity Violation Exception.
	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void testDeleteIncorrectAuthenticated() {
		
		authenticate("rookie1");
		
		SocialProfile socialProfile = (SocialProfile) socialProfileService.findAll().toArray()[0];
		
		socialProfileService.delete(socialProfile);
		
		Assert.isTrue(!socialProfileService.findAll().contains(socialProfile));

		unauthenticate();
	}
	
	@Test
	public void driverDeleteSocialProfile(){
		
		Object testingData[][] = {{"admin", null}};
//								  {"company1", org.springframework.dao.DataIntegrityViolationException.class},
//								  {"company2", org.springframework.dao.DataIntegrityViolationException.class},
//								  {"company3", org.springframework.dao.DataIntegrityViolationException.class},
//								  {"rookie1",  org.springframework.dao.DataIntegrityViolationException.class},
//								  {"rookie2",  org.springframework.dao.DataIntegrityViolationException.class},
//								  {"rookie3",  org.springframework.dao.DataIntegrityViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteSocialProfile((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteSocialProfile(String username, Class<?> expected){
		Class<?> caught = null;
		SocialProfile socialProfile = (SocialProfile) socialProfileService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.socialProfileService.delete(socialProfile);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
