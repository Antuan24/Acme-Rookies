package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Admin;
import domain.Auditor;
import domain.Company;
import domain.CreditCard;
import domain.Provider;
import domain.Rookie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ActorServiceTest extends AbstractTest {

	//	Coverage: 100.0%
	//	Covered Instructions: 845
	//	Missed  Instructions: 0
	//	Total   Instructions: 845
	
	// Managed service --------------------------------------------------
	
	@Autowired
	private ApplicationService appService;
	
	// Support services -------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private CurriculaService curriculaService;

	// Tests ------------------------------------------------------------
	
	@Test
	public void driverRookie(){
		
		UserAccount ua = userAccountService.create();
		Authority auth = new Authority();
		auth.setAuthority(Authority.ROOKIE);
		ua.getAuthorities().add(auth);
		ua.setUsername("newrookie");
		ua.setPassword("password");		
		Rookie rookie = rookieService.create(ua);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("me");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("4576098756783456");
		
		rookie.setName("name");
		rookie.setSurnames("a b");
		rookie.setVatNumber("ASD12341234");
		rookie.setEmail("email@email.com");
		rookie.setPhone("612123456");
		rookie.setCreditCard(credit);
		
		Rookie rookie1 = rookieService.findOne(getEntityId("rookie1"));
		
		System.out.println(rookie1 +" " +rookie);
		
		Object testingData[][] = {
				{rookie, null},
				{rookie1, null},
				{null,NullPointerException.class}
		};
		for(int i=0; i<testingData.length;i++){
			templateRookie((Rookie) testingData[i][0],
					(Class<?>) testingData[i][1]);
		}
	}
	
	protected void templateRookie(Rookie rookie, Class<?> expected){
		Class<?> caught = null;
		try{
			
			actorService.registerRookie(rookie);
			
		}catch(Throwable oops){
			oops.printStackTrace();
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
	@Test
	public void driverCompany(){
		
		UserAccount ua = userAccountService.create();
		Authority auth = new Authority();
		auth.setAuthority(Authority.ROOKIE);
		ua.getAuthorities().add(auth);
		ua.setUsername("newcompany");
		ua.setPassword("password");		
		Company company = companyService.create(ua);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("me");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2354635425346978");
		
		company.setName("name");
		company.setSurnames("a b");
		company.setVatNumber("ASD12341234");
		company.setEmail("email@email.com");
		company.setPhone("612123456");
		company.setCreditCard(credit);
		company.setCommercialName("asdf");
		
		Company company1 = companyService.findOne(getEntityId("company1"));
		
		System.out.println(company1 +" " +company);
		
		Object testingData[][] = {
				{company, null},
				{company1, null},
				{null,NullPointerException.class}
		};
		for(int i=0; i<testingData.length;i++){
			templateCompany((Company) testingData[i][0],
					(Class<?>) testingData[i][1]);
		}
	}
	
	protected void templateCompany(Company company, Class<?> expected){
		Class<?> caught = null;
		try{
			
			actorService.registerCompany(company);
			
		}catch(Throwable oops){
			oops.printStackTrace();
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
	@Test
	public void driverAdmin(){
		
		UserAccount ua = userAccountService.create();
		Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		ua.getAuthorities().add(auth);
		ua.setUsername("newadmin");
		ua.setPassword("password");		
		Admin admin1 = adminService.create(ua);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("me");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2534746553427456");
		
		admin1.setName("name");
		admin1.setSurnames("a b");
		admin1.setVatNumber("ASD12341234");
		admin1.setEmail("email@email.com");
		admin1.setPhone("612123456");
		admin1.setCreditCard(credit);
		
		Admin admin = adminService.findOne(getEntityId("admin"));
		
		System.out.println(admin +" " +admin1);
		
		Object testingData[][] = {
				{admin, null},
				{admin1, IllegalArgumentException.class},
				{null,NullPointerException.class}
		};
		for(int i=0; i<testingData.length;i++){
			templateAdmin((Admin) testingData[i][0],
					(Class<?>) testingData[i][1]);
		}
	}
	
	protected void templateAdmin(Admin admin, Class<?> expected){
		Class<?> caught = null;
		try{
			if(admin.equals(adminService.findOne(getEntityId("admin")))){
				authenticate("admin");
			}else {
				authenticate(null);
			}
			
			actorService.registerAdmin(admin);
			
		}catch(Throwable oops){
			oops.printStackTrace();
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
	//	Acme-Rookie B-level: RI 7, RF 9.3
	
	@Test
	public void driverProvider(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("newprovider");
		userAccount.setPassword("password");		
		Provider provider = providerService.create(userAccount);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("me");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2354635425346978");
		
		provider.setName("name");
		provider.setSurnames("a b");
		provider.setVatNumber("ASD12341234");
		provider.setEmail("email@email.com");
		provider.setPhone("612123456");
		provider.setCreditCard(credit);
		provider.setMake("make");
		
		Provider provider1 = providerService.findOne(getEntityId("provider1"));
		
		System.out.println(provider1 + " " + provider);
		
		Object testingData[][] = {{provider,  null},
								  {provider1, null},
				                  {null, NullPointerException.class}};
		
		for(int i=0; i<testingData.length;i++){
			templateProvider((Provider) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}
	
	protected void templateProvider(Provider provider, Class<?> expected){
		Class<?> caught = null;
		try{
			
			actorService.registerProvider(provider);
			
		}catch(Throwable oops){
			oops.printStackTrace();
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
	//	Acme-Rookie C-level: RI 1, RF 4.2
	
	@Test
	public void testRegisterProvider(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("provider");
		userAccount.setPassword("password");		
		Provider provider = providerService.create(userAccount);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("me");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2534746553427456");
		
		provider.setName("name");
		provider.setSurnames("a b");
		provider.setVatNumber("ASD12341234");
		provider.setEmail("email@email.com");
		provider.setPhone("612123456");
		provider.setCreditCard(credit);
		provider.setMake("make");
		
		Provider result = actorService.registerProvider(provider);
		
		Assert.isTrue(actorService.findAll().contains(result));
	}
	
	@Test
	public void testRegisterAuditor(){
		
		authenticate("admin");
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.AUDITOR);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("auditor");
		userAccount.setPassword("password");		
		Auditor auditor = auditorService.create(userAccount);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("me");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2534746553427456");
		
		auditor.setName("name");
		auditor.setSurnames("a b");
		auditor.setVatNumber("ASD12341234");
		auditor.setEmail("email@email.com");
		auditor.setPhone("612123456");
		auditor.setCreditCard(credit);
		
		Auditor result = actorService.registerAuditor(auditor);
		
		Assert.isTrue(actorService.findAll().contains(result));
		
		unauthenticate();
	}

//	@Test
//	public void driverAuditor(){
//		
//		UserAccount userAccount = userAccountService.create();
//		Authority authority = new Authority();
//		authority.setAuthority(Authority.AUDITOR);
//		userAccount.getAuthorities().add(authority);
//		userAccount.setUsername("newauditor");
//		userAccount.setPassword("password");		
//		Auditor auditor = auditorService.create(userAccount);
//		
//		CreditCard credit = creditCardService.create();
//		credit.setCVV(123);
//		credit.setHolder("me");
//		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
//		credit.setMake("AMEX");
//		credit.setNumber("4576098756783456");
//		
//		auditor.setName("name");
//		auditor.setSurnames("a b");
//		auditor.setVatNumber("ASD12341234");
//		auditor.setEmail("email@email.com");
//		auditor.setPhone("612123456");
//		auditor.setCreditCard(credit);
//		
//		Auditor auditor1 = auditorService.findOne(getEntityId("auditor1"));
//		
//		System.out.println(auditor1 + " " + auditor);
//		
//		Object testingData[][] = {{auditor,  null},
//				                  {auditor1, null},
//				                  {null,NullPointerException.class}};
//		
//		for(int i=0; i<testingData.length;i++){
//			templateAuditor((Auditor) testingData[i][0], (Class<?>) testingData[i][1]);
//		}
//	}
//	
//	protected void templateAuditor(Auditor auditor, Class<?> expected){
//		Class<?> caught = null;
//		try{
//			
//			actorService.registerAuditor(auditor);
//			
//		}catch(Throwable oops){
//			oops.printStackTrace();
//			caught = oops.getClass();
//		}
//		super.checkExceptions(expected, caught);
//	}

}
