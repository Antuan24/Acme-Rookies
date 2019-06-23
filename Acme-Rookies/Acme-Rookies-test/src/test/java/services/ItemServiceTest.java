package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Item;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ItemServiceTest extends AbstractTest {

	//	Coverage: 96.2%
	//	Covered Instructions: 1.173
	//	Missed  Instructions: 46
	//	Total   Instructions: 1.219
	
	//	Acme-Rookie B-level: RI 8, RF 10
	
	@Autowired
	private ItemService itemService;
	
	@Test
	public void testCreate(){
		
		authenticate("provider1");
		
		Item item = itemService.create();
		
		Assert.isNull(item.getName());
		Assert.isNull(item.getLink());
		Assert.isNull(item.getDescription());
		Assert.notNull(item.getPictures());
		Assert.notNull(item.getProvider());
		
		unauthenticate();
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testCreateNotAuthenticated(){
		
		authenticate(null);
		
		Item item = itemService.create();
		
		Assert.isNull(item.getName());
		Assert.isNull(item.getLink());
		Assert.isNull(item.getDescription());
		Assert.notNull(item.getPictures());
		Assert.notNull(item.getProvider());
		
		unauthenticate();
	}
	
	@Test
	public void driverCreateItem(){
		
		final Object testingData[][] = {{"provider1", null},
										{"provider2", null},
										{"provider3", null}};
//										{"admin",   java.lang.IllegalArgumentException.class},
//										{"rookie1", java.lang.IllegalArgumentException.class},
//										{"rookie2", java.lang.IllegalArgumentException.class},
//										{"rookie3", java.lang.IllegalArgumentException.class},
//										{"company1", java.lang.IllegalArgumentException.class},
//										{"company2", java.lang.IllegalArgumentException.class},
//										{"company3", java.lang.IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateItem((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateItem(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.itemService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		
		authenticate("provider1");
		
		Item item = itemService.create();
		
		Collection<String> pictures = new ArrayList<String>();
		String picture1 = "picture1";
		String picture2 = "picture2";
		String picture3 = "picture3";
		pictures.add(picture1);
		pictures.add(picture2);
		pictures.add(picture3);
		
		item.setName("Name item");
		item.setLink("http://www.linkItem.com");
		item.setDescription("Description item");
		item.setPictures(pictures);
		
		Item result = itemService.save(item);
		Assert.isTrue(itemService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void driverSaveItem(){
		
//		Collection<String> empty = new ArrayList<String>();
		Collection<String> pictures = new ArrayList<String>();
		String picture1 = "picture1";
		String picture2 = "picture2";
		String picture3 = "picture3";
		pictures.add(picture1);
		pictures.add(picture2);
		pictures.add(picture3);
		
		Object testingData[][] = {{"provider1", "name", "http://www.link.com", "description", pictures, null},
								  {"provider2", "name", "http://www.link.com", "description", pictures, null},
				                  {"provider3", "name", "http://www.link.com", "description", pictures, null},
//				                  {"provider3", "name", "http://www.link.com", "description", empty, pictures, javax.validation.ConstraintViolationException.class},
				                  {"provider3", "name", "link", "description", pictures, javax.validation.ConstraintViolationException.class},
				                  {"provider3", "", "http://www.link.com", "description", pictures, javax.validation.ConstraintViolationException.class},
				                  {"provider3", "name", "link", "", pictures, javax.validation.ConstraintViolationException.class},
				                  {null, "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"auditor1", "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"auditor2", "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"company1", "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"company2", "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"rookie1", "name", "http://www.link.com", "description", pictures,  java.lang.IllegalArgumentException.class},
								  {"rookie2", "name", "http://www.link.com", "description", pictures,  java.lang.IllegalArgumentException.class},};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveItem((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
					         (String) testingData[i][3], (Collection<String>) testingData[i][4], (Class<?>)testingData[i][5]);
		}
	}
	
	protected void templateSaveItem(String username, String name, String link, String description, 
			                        Collection<String> pictures, Class<?> expected){
		Class<?> caught = null;
		Item item;
		
		try{
			super.authenticate(username);
			item = this.itemService.create();
			item.setName(name);
			item.setLink(link);
			item.setDescription(description);
			item.setPictures(pictures);
			item = this.itemService.save(item);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testUpdate(){
		
		authenticate("provider1");
		
		Item item = (Item) itemService.findAll().toArray()[0];
		
		item.setName("Name item updated");
		item.setLink("http://www.linkItemUpdated.com");
		item.setDescription("Description item updated");
		
		Item result = itemService.save(item);
		Assert.isTrue(itemService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testUpdateIncorrectData(){
		
		authenticate("provider1");
		
		Item item = (Item) itemService.findAll().toArray()[0];
		
		item.setName("");
		item.setLink("");
		item.setDescription("");
		
		Item result = itemService.save(item);
		Assert.isTrue(itemService.findAll().contains(result));
		
		unauthenticate();
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void driverUpdateItem(){
		
//		Collection<String> empty = new ArrayList<String>();
		Collection<String> pictures = new ArrayList<String>();
		String picture1 = "picture1";
		String picture2 = "picture2";
		String picture3 = "picture3";
		pictures.add(picture1);
		pictures.add(picture2);
		pictures.add(picture3);
		
		Object testingData[][] = {{"provider1", "name", "http://www.link.com", "description", pictures, null},
								  {"provider2", "name", "http://www.link.com", "description", pictures, null},
				                  {"provider3", "name", "http://www.link.com", "description", pictures, null},
//				                  {"provider3", "name", "http://www.link.com", "description", empty, pictures, javax.validation.ConstraintViolationException.class},
//				                  {"provider3", "name", "link", "description", pictures, javax.validation.ConstraintViolationException.class},
				                  {null, "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"auditor1", "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"auditor2", "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"company1", "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"company2", "name", "http://www.link.com", "description", pictures, java.lang.IllegalArgumentException.class},
								  {"rookie1", "name", "http://www.link.com", "description", pictures,  java.lang.IllegalArgumentException.class},
								  {"rookie2", "name", "http://www.link.com", "description", pictures,  java.lang.IllegalArgumentException.class},};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateItem((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
					         (String) testingData[i][3], (Collection<String>) testingData[i][4], (Class<?>)testingData[i][5]);
		}
	}
	
	protected void templateUpdateItem(String username, String name, String link, String description, 
			                        Collection<String> pictures, Class<?> expected){
		Class<?> caught = null;
		Item item = (Item) itemService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			item.setName(name);
			item.setLink(link);
			item.setDescription(description);
			item.setPictures(pictures);
			item = this.itemService.save(item);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testDelete(){
		
		authenticate("provider1");
		
		Item item = (Item) itemService.findAll().toArray()[0];
		
		itemService.delete(item);
		
		Assert.isTrue(!itemService.findAll().contains(item));
		
		unauthenticate();
	}
	
//	@Test(expected = java.lang.IllegalArgumentException.class)
//	public void testDeleteNotAuthenticated(){
//		
//		authenticate(null);
//		
//		Item item = (Item) itemService.findAll().toArray()[0];
//		
//		itemService.delete(item);
//		
//		Assert.isTrue(!itemService.findAll().contains(item));
//		
//		unauthenticate();
//	}
	
	@Test
	public void driverDeleteItem(){
		
		Object testingData[][] = {{"provider1", null},
								  {"provider2", null},
				                  {"provider3", null}};
//								  {"auditor1", java.lang.IllegalArgumentException.class},
//								  {"auditor2", java.lang.IllegalArgumentException.class},
//								  {"company1", java.lang.IllegalArgumentException.class},
//								  {"company2", java.lang.IllegalArgumentException.class},
//								  {"rookie1",  java.lang.IllegalArgumentException.class},
//								  {"rookie2",  java.lang.IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteItem((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteItem(String username, Class<?> expected){
		Class<?> caught = null;
		Item item = (Item) itemService.findAll().toArray()[0];;
		
		try{
			super.authenticate(username);
			this.itemService.delete(item);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
