package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Position;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class PositionServiceTest extends AbstractTest {
	
//	Coverage: 88.3%
//	Covered Instructions: 767
//	Missed  Instructions: 102
//	Total   Instructions: 869
	
	@Autowired
	private PositionService positionService;
	
	@Test
	public void testCreate() {
		
		authenticate("company1");
		
		Position position = positionService.create();

		Assert.notNull(position.getTicker());
		Assert.notNull(position.getFinders());
		Assert.notNull(position.getCompany());
		Assert.isTrue(!position.getIsFinal());
		Assert.isNull(position.getTitle());
		Assert.isNull(position.getDescription());
		Assert.isNull(position.getTechnologies());
		Assert.isNull(position.getDeadline());
		Assert.isNull(position.getProfile());
		Assert.isNull(position.getSalary());
		Assert.isNull(position.getSkills());
		
		unauthenticate();
	}
	
	@Test
	public void driverCreatePosition(){
		
		final Object testingData[][] = {{"company1", null},
										{"company2", null},
										{"company3", null},
										{"admin",   NullPointerException.class},
										{"rookie1", NullPointerException.class},
										{"rookie2", NullPointerException.class},
										{"rookie3", NullPointerException.class},
										{"rookie4", NullPointerException.class},
										{"rookie5", NullPointerException.class},
										{"rookie6", NullPointerException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreatePosition((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreatePosition(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.positionService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave() {
		
		authenticate("company2");

		Position result;
		Position position = positionService.create();
		
		Date deadline = new Date(System.currentTimeMillis() + 10000);
		
		System.out.println("Ticker test: " + position.getTicker());
		position.setTitle("Title");
		position.setDescription("Description");
		position.setDeadline(deadline);
		position.setProfile("Profile");
		position.setSalary(650.25);
		position.setSkills("Skills");
		position.setTechnologies("Technologies");
		position.setIsFinal(false);
		
		result = positionService.save(position);
		Assert.isTrue(positionService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated() {
		
		authenticate(null);

		Position result;
		Position position = positionService.create();
		
		Date deadline = new Date(System.currentTimeMillis() + 1000);
		
		System.out.println("Ticker test: " + position.getTicker());
		position.setTitle("Title");
		position.setDescription("Description");
		position.setDeadline(deadline);
		position.setProfile("Profile");
		position.setSalary(650.25);
		position.setSkills("Skills");
		position.setTechnologies("Technologies");
		position.setIsFinal(true);
		
		result = positionService.save(position);
		Assert.isTrue(positionService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testSaveIncorrectData() {
		
		authenticate("company1");

		Position result;
		Position position = positionService.create();
		
		Date deadline = new Date(System.currentTimeMillis() + 1000);
		
		System.out.println("Ticker test: " + position.getTicker());
		position.setTitle("");
		position.setDescription("");
		position.setDeadline(deadline);
		position.setProfile("");
		position.setSalary(650.25);
		position.setSkills("");
		position.setTechnologies("");
		position.setIsFinal(false);
		
		result = positionService.save(position);
		Assert.isTrue(positionService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testSaveIncorrectDate() {
		
		authenticate("company1");

		Position result;
		Position position = positionService.create();
		
		Date deadline = new Date(System.currentTimeMillis());
		
		System.out.println("Ticker test: " + position.getTicker());
		position.setTitle("Title");
		position.setDescription("Description");
		position.setDeadline(deadline);
		position.setProfile("Profile");
		position.setSalary(650.25);
		position.setSkills("Skills");
		position.setTechnologies("Technologies");
		position.setIsFinal(false);
		
		result = positionService.save(position);
		Assert.isTrue(positionService.findAll().contains(result));
		
		unauthenticate();
	}
	
//	@Test
//	public void driverSavePosition(){
//		
//		Object testingData[][] = {{"company1", "title", "description", "profile", 240.5, "skills", "technologies", false, null},
//								  {"company2", "title", "description", "profile", 240.5, "skills", "technologies", false, null},
//								  {"company3", "title", "description", "profile", 240.5, "skills", "technologies", false, null}};
//		
//		for(int i = 0; i < testingData.length; i++){
//			templateSavePosition((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
//					               (Date) testingData[i][3], (String) testingData[i][4], (Double) testingData[i][5],
//					             (String) testingData[i][6], (String) testingData[i][7], (Boolean) testingData[i][8],  
//								 (Class<?>)testingData[i][9]);
//		}
//	}
//	
//	protected void templateSavePosition(String username, String title, String description, Date deadline, 
//										String profile, Double salary, String skills, String technologies,
//										Boolean isFinal, Class<?> expected){
//		Class<?> caught = null;
//		Position position;
//		
//		try{
//			super.authenticate(username);
//			position = this.positionService.create();
//			position.setTitle(title);
//			position.setDescription(description);
//			position.setDeadline(deadline);
//			position.setProfile(profile);
//			position.setSalary(salary);
//			position.setSkills(skills);
//			position.setTechnologies(technologies);
//			position.setIsFinal(isFinal);
//			position = this.positionService.save(position);
//		} catch (Throwable oops){
//			caught = oops.getClass();
//		}
//		
//		this.checkExceptions(expected, caught);
//		super.unauthenticate();
//	}
	
	@Test
	public void testUpdate() {
		
		authenticate("company1");

		Position position = (Position) positionService.findAll().toArray()[0];
		
		position.setTitle("Title updated");
		position.setDescription("Description updated");
		
		Position result = positionService.save(position);
		Assert.isTrue(positionService.findAll().contains(result));
		
		unauthenticate();
	}
	
//	@Test(expected = javax.validation.ConstraintViolationException.class)
//	public void testUpdateIncorrectData() {
//		
//		authenticate("company1");
//
//		Position position = (Position) positionService.findAll().toArray()[0];
//		
//		position.setTitle("");
//		position.setDescription("");
//		
//		Position result = positionService.save(position);
//		Assert.isTrue(positionService.findAll().contains(result));
//		
//		unauthenticate();
//	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void testUpdateNotAuthenticated() {
//		
//		authenticate(null);
//
//		Position position = (Position) positionService.findAll().toArray()[0];
//		
//		position.setTitle("Title updated");
//		position.setDescription("Description updated");
//		
//		Position result = positionService.save(position);
//		Assert.isTrue(positionService.findAll().contains(result));
//		
//		unauthenticate();
//	}
	
	@Test
	public void driverUpdatePosition(){
		
		Object testingData[][] = {{"company1", "title", "description", "profile", 240.5, "skills", "technologies", false, null},
				  				  {"company2", "title", "description", "profile", 240.5, "skills", "technologies", false, null},
				  				  {"company3", "title", "description", "profile", 240.5, "skills", "technologies", false, null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdatePosition((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
								   (String) testingData[i][3], (Double) testingData[i][4], (String) testingData[i][5], 
								   (String) testingData[i][6], (Boolean) testingData[i][7], (Class<?>)testingData[i][8]);
		}
	}
	
	protected void templateUpdatePosition(String username, String title, String description, String profile, Double salary,
										  String skills, String technologies, Boolean isFinal, Class<?> expected){
		Class<?> caught = null;
		Position position = (Position) positionService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			position.setTitle(title);
			position.setDescription(description);
//			position.setDeadline(deadline);
			position.setProfile(profile);
			position.setSalary(salary);
			position.setSkills(skills);
			position.setTechnologies(technologies);
			position.setIsFinal(isFinal);
			position = this.positionService.save(position);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testDelete() {
		
		authenticate("company1");
		
		Position position = (Position) positionService.findAll().toArray()[1];
		
		System.out.println("Status: " + position.getIsFinal());
		
		positionService.delete(position);
		
		Assert.isTrue(!positionService.findAll().contains(position));

		unauthenticate();
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void testDeleteNotAuthenticated() {
//		
//		authenticate(null);
//		
//		Position position = (Position) positionService.findAll().toArray()[1];
//		
//		System.out.println("Status: " + position.getIsFinal());
//		
//		positionService.delete(position);
//		
//		Assert.isTrue(!positionService.findAll().contains(position));
//
//		unauthenticate();
//	}
	
	@Test
	public void driverDeletePosition(){
		
		Object testingData[][] = {{"company1", IllegalArgumentException.class},
				  				  {"company2", IllegalArgumentException.class},
				  				  {"company3", IllegalArgumentException.class}};
//								  {"rookie1", IllegalArgumentException.class},
//								  {"rookie2", IllegalArgumentException.class},
//								  {"rookie3", IllegalArgumentException.class},
//								  {"admin", IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeletePosition((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeletePosition(String username, Class<?> expected){
		Class<?> caught = null;
		Position position = (Position) positionService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.positionService.delete(position);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
}
