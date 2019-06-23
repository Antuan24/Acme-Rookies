package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Actor;
import domain.Admin;
import domain.Auditor;
import domain.Company;
import domain.CreditCard;
import domain.Provider;
import domain.Rookie;
import forms.ProfileForm;
import forms.RegisterCompanyForm;
import forms.RegisterProviderForm;
import forms.RegisterRookieAdminForm;


@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private Validator validator;

	// Simple CRUD methods -----

	public Collection<Actor> findAll() {
		return actorRepository.findAll();
	}

	public Actor findOne(int Id) {
		return actorRepository.findOne(Id);
	}

	public Actor save(Actor actor) {

		Actor result;

		result = actorRepository.saveAndFlush(actor);
		return result;
	}

	public void delete(Actor actor) {
		
		actorRepository.delete(actor);
	}

	// Other business methods -----

	public Actor getByUserAccount(UserAccount ua) {
		return actorRepository.findByUserAccount(ua);
	}

	public Rookie registerRookie(Rookie rookie) {

		UserAccount savedua =  userAccountService.save(rookie.getUserAccount());
		CreditCard savedCredit = creditCardService.save(rookie.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		rookie.setUserAccount(savedua);
		rookie.setCreditCard(savedCredit);
		Rookie saved = rookieService.save(rookie);
		
		return saved;
	}
	
	public Company registerCompany(Company company) {

		UserAccount savedua =  userAccountService.save(company.getUserAccount());
		CreditCard savedCredit = creditCardService.save(company.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		company.setUserAccount(savedua);
		company.setCreditCard(savedCredit);
		Company saved = companyService.save(company);
		
		return saved;
	}
	
	public Provider registerProvider(Provider provider) {

		UserAccount savedua =  userAccountService.save(provider.getUserAccount());
		CreditCard savedCredit = creditCardService.save(provider.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		provider.setUserAccount(savedua);
		provider.setCreditCard(savedCredit);
		Provider saved = providerService.save(provider);
		
		return saved;
	}
	
	public Admin registerAdmin(Admin admin) {
		
		Authority adminauth = new Authority();
		adminauth.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(adminauth));

		UserAccount savedua =  userAccountService.save(admin.getUserAccount());
		CreditCard savedCredit = creditCardService.save(admin.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		admin.setUserAccount(savedua);
		admin.setCreditCard(savedCredit);
		Admin saved = adminService.save(admin);
		
		return saved;
	}
	

	public Auditor registerAuditor(Auditor auditor) {
		
		Authority adminauth = new Authority();
		adminauth.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(adminauth));

		UserAccount savedua =  userAccountService.save(auditor.getUserAccount());
		CreditCard savedCredit = creditCardService.save(auditor.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		auditor.setUserAccount(savedua);
		auditor.setCreditCard(savedCredit);
		Auditor saved = auditorService.save(auditor);
		
		return saved;
	}
	
	
/*	
	public Actor reconstructProfile(ProfileForm profileForm, BindingResult binding){
		
		Actor actor = this.findOne(profileForm.getId());
		
		System.out.println("entro en profileform y encuentro el actor: "+actor);
		
		//	Comprobamos que las contraseñas son iguales.
		
		System.out.println("passDB: "+actor.getUserAccount().getPassword());
		
		actor.getUserAccount().setUsername(profileForm.getUsername());
		
		if (profileForm.getPassword() != "") { // no se han editado
			System.out.println("se ha rellenado la contraseña y es");
			System.out.println("pass1form: "+profileForm.getPassword()+"pass2form:" + profileForm.getPassword2());
			Assert.isTrue(profileForm.getPassword().equals(profileForm.getPassword2()));
			actor.getUserAccount().setPassword(profileForm.getPassword());
		}

		actor.setName(profileForm.getName());
		actor.setMiddleName(profileForm.getMiddleName());
		actor.setSurname(profileForm.getSurname());
		actor.setAddress(profileForm.getAddress());
		
		actor.setPhone(profileForm.getPhone());
		actor.setPhoto(profileForm.getPhoto());
		actor.setEmail(profileForm.getEmail());
		
		return actor;
	}
	*/
	public Rookie reconstructRookie(RegisterRookieAdminForm form, BindingResult binding ){
		
		//Creamos la tarjeta de credito:
		CreditCard credit = creditCardService.create();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(form.getExpirationYear(), form.getExpirationMonth(), 1);
		Date date = calendar.getTime();
		
		credit.setCVV(form.getCVV());
		credit.setExpirationDate(date);
		credit.setHolder(form.getHolder());
		credit.setMake(form.getMake());
		credit.setNumber(form.getNumber());
		

		//Creamos la cuenta de usuario.
		
		UserAccount ua = userAccountService.create();
		
		ua.setPassword(form.getPassword());
		ua.setUsername(form.getUsername());
		
		// Le asignamos la authority cosrrespondiente.
		
		Authority authority = new Authority();
		authority.setAuthority("ROOKIE");
		ua.addAuthority(authority);
		
		// Creamos el actor con la useraccount sin persistir.
		
		Rookie actor = rookieService.create(ua);
		
		
		actor.setAddress(form.getAddress());
		actor.setCreditCard(credit);
		actor.setEmail(form.getEmail());
		actor.setName(form.getName());
		actor.setPhone(form.getPhone());
		actor.setPhoto(form.getPhoto());
		actor.setSurnames(form.getSurnames());
		actor.setVatNumber(form.getVatNumber());
		
		validator.validate(form, binding);		
		
		if (form.getPassword().equals(form.getPassword2())==false) {
			ObjectError error = new ObjectError(form.getPassword(), "password does not match");
			binding.addError(error);
		}		
		return actor;
	
	}
	
public Provider reconstructProvider(RegisterProviderForm form, BindingResult binding ){
		
		//Creamos la tarjeta de credito:
		CreditCard credit = creditCardService.create();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(form.getExpirationYear(), form.getExpirationMonth(), 1);
		Date date = calendar.getTime();
		
		credit.setCVV(form.getCVV());
		credit.setExpirationDate(date);
		credit.setHolder(form.getHolder());
		credit.setMake(form.getMake());
		credit.setNumber(form.getNumber());
		

		//Creamos la cuenta de usuario.
		
		UserAccount ua = userAccountService.create();
		
		ua.setPassword(form.getPassword());
		ua.setUsername(form.getUsername());
		
		// Le asignamos la authority cosrrespondiente.
		
		Authority authority = new Authority();
		authority.setAuthority("PROVIDER");
		ua.addAuthority(authority);
		
		// Creamos el actor con la useraccount sin persistir.
		
		Provider actor = providerService.create(ua);
		
		
		actor.setAddress(form.getAddress());
		actor.setCreditCard(credit);
		actor.setEmail(form.getEmail());
		actor.setName(form.getName());
		actor.setPhone(form.getPhone());
		actor.setPhoto(form.getPhoto());
		actor.setSurnames(form.getSurnames());
		actor.setVatNumber(form.getVatNumber());
		actor.setMake(form.getProviderMake());
		actor.setDebt(0d);
		validator.validate(form, binding);		
		
		if (form.getPassword().equals(form.getPassword2())==false) {
			ObjectError error = new ObjectError(form.getPassword(), "password does not match");
			binding.addError(error);
		}		
		return actor;
	
	}
	
	public Admin reconstructAdmin(RegisterRookieAdminForm form, BindingResult binding ){
		
		//Creamos la tarjeta de credito:
		CreditCard credit = creditCardService.create();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(form.getExpirationYear(), form.getExpirationMonth(), 1);
		Date date = calendar.getTime();
		
		
		credit.setCVV(form.getCVV());
		credit.setExpirationDate(date);
		credit.setHolder(form.getHolder());
		credit.setMake(form.getMake());
		credit.setNumber(form.getNumber());
		

		//Creamos la cuenta de usuario.
		
		UserAccount ua = userAccountService.create();
		
		ua.setPassword(form.getPassword());
		ua.setUsername(form.getUsername());
		
		// Le asignamos la authority cosrrespondiente.
		
		Authority authority = new Authority();
		authority.setAuthority("ADMIN");
		ua.addAuthority(authority);
		
		// Creamos el actor con la useraccount sin persistir.
		
		Admin actor = adminService.create(ua);
		
		
		actor.setAddress(form.getAddress());
		actor.setCreditCard(credit);
		actor.setEmail(form.getEmail());
		actor.setName(form.getName());
		actor.setPhone(form.getPhone());
		actor.setPhoto(form.getPhoto());
		actor.setSurnames(form.getSurnames());
		actor.setVatNumber(form.getVatNumber());
		
		validator.validate(form, binding);		
		
		if (form.getPassword().equals(form.getPassword2())==false) {
			ObjectError error = new ObjectError(form.getPassword(), "password does not match");
			binding.addError(error);
		}		
		return actor;
	
	}
	
	public Auditor reconstructAuditor(RegisterRookieAdminForm form, BindingResult binding ){
		
		//Creamos la tarjeta de credito:
		CreditCard credit = creditCardService.create();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(form.getExpirationYear(), form.getExpirationMonth(), 1);
		Date date = calendar.getTime();
		
		
		credit.setCVV(form.getCVV());
		credit.setExpirationDate(date);
		credit.setHolder(form.getHolder());
		credit.setMake(form.getMake());
		credit.setNumber(form.getNumber());
		

		//Creamos la cuenta de usuario.
		
		UserAccount ua = userAccountService.create();
		
		ua.setPassword(form.getPassword());
		ua.setUsername(form.getUsername());
		
		// Le asignamos la authority cosrrespondiente.
		
		Authority authority = new Authority();
		authority.setAuthority("AUDITOR");
		ua.addAuthority(authority);
		
		// Creamos el actor con la useraccount sin persistir.
		
		Auditor actor = auditorService.create(ua);
		
		
		actor.setAddress(form.getAddress());
		actor.setCreditCard(credit);
		actor.setEmail(form.getEmail());
		actor.setName(form.getName());
		actor.setPhone(form.getPhone());
		actor.setPhoto(form.getPhoto());
		actor.setSurnames(form.getSurnames());
		actor.setVatNumber(form.getVatNumber());
		
		validator.validate(form, binding);		
		
		if (form.getPassword().equals(form.getPassword2())==false) {
			ObjectError error = new ObjectError(form.getPassword(), "password does not match");
			binding.addError(error);
		}		
		return actor;
	
	}
	
	public Company reconstructCompany(RegisterCompanyForm form, BindingResult binding){
		
		//Creamos la tarjeta de credito:
		CreditCard credit = creditCardService.create();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(form.getExpirationYear(), form.getExpirationMonth(), 1);
		Date date = calendar.getTime();
		
		
		credit.setCVV(form.getCVV());
		credit.setExpirationDate(date);
		credit.setHolder(form.getHolder());
		credit.setMake(form.getMake());
		credit.setNumber(form.getNumber());
		

		//Creamos la cuenta de usuario.
		
		UserAccount ua = userAccountService.create();
		
		ua.setPassword(form.getPassword());
		ua.setUsername(form.getUsername());
		
		// Le asignamos la authority cosrrespondiente.
		
		Authority authority = new Authority();
		authority.setAuthority("COMPANY");
		ua.addAuthority(authority);
		
		// Creamos el actor con la useraccount sin persistir.
		
		Company actor = companyService.create(ua);
		
		actor.setCommercialName(form.getCommercialName());
		actor.setAddress(form.getAddress());
		actor.setCreditCard(credit);
		actor.setEmail(form.getEmail());
		actor.setName(form.getName());
		actor.setPhone(form.getPhone());
		actor.setPhoto(form.getPhoto());
		actor.setSurnames(form.getSurnames());
		actor.setVatNumber(form.getVatNumber());
		
		validator.validate(form, binding);		
		
		if (form.getPassword().equals(form.getPassword2())==false) {
			ObjectError error = new ObjectError(form.getPassword(), "password does not match");
			binding.addError(error);
		}		
		return actor;
	
	}
	
	public ProfileForm rellenaForm (Actor a , ProfileForm f , List<String> parts){
		Authority c = new Authority();
		c.setAuthority(Authority.COMPANY);
		
		if (parts.contains("personal") || parts.isEmpty()) {
			// personal data
			if (a.getUserAccount().getAuthorities().contains(c)) {
				Company comp = (Company) a;
				f.setCommercialName(comp.getCommercialName());
			}else{
				f.setCommercialName("default");
			}
			f.setAddress(a.getAddress());
			f.setEmail(a.getEmail());
			f.setPhone(a.getPhone());
			f.setPhoto(a.getPhoto());
			f.setSurnames(a.getSurnames());
			f.setVatNumber(a.getVatNumber());
			f.setName(a.getName());
		}
		
		if(parts.contains("credit") || parts.isEmpty()){
			//credit card
			f.setCVV(a.getCreditCard().getCVV());
			f.setExpirationMonth(a.getCreditCard().getExpirationDate().getMonth());
			f.setExpirationYear(a.getCreditCard().getExpirationDate().getYear()+1900);
			f.setHolder(a.getCreditCard().getHolder());
			f.setMake(a.getCreditCard().getMake());
			f.setNumber(a.getCreditCard().getNumber());
		}
		
		if(parts.contains("account")|| parts.isEmpty()){
			//account
			f.setPassword("default");
			f.setPassword2("default");
			f.setUsername(a.getUserAccount().getUsername());
		}
		
		
		return f;
	}
	
public String actorToJson(Actor actor){
		
		ProfileForm form = new ProfileForm();
		
		//credit card
		form.setCVV(actor.getCreditCard().getCVV());
		form.setExpirationMonth(actor.getCreditCard().getExpirationDate().getMonth());
		form.setExpirationYear(actor.getCreditCard().getExpirationDate().getYear());
		form.setHolder(actor.getCreditCard().getHolder());
		form.setMake(actor.getCreditCard().getMake());
		form.setNumber(actor.getCreditCard().getNumber());
		
		
		form.setAddress(actor.getAddress());
		form.setEmail(actor.getEmail());
		form.setCommercialName("null");
		form.setName(actor.getName());
		form.setPassword("null");
		form.setPassword2("null");
		form.setPhone(actor.getPhone());
		form.setPhoto(actor.getPhoto());
		form.setSurnames(actor.getSurnames());
		form.setUsername(actor.getUserAccount().getUsername());
		form.setVatNumber(actor.getVatNumber());
		
		String res = "";
		ObjectMapper mapper = new ObjectMapper();
		
        try {
            String json = mapper.writeValueAsString(form);
            res = json;
            System.out.println("JSON = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
		return res;
	}
	
	
	public Actor findByUsername (String username){
		return actorRepository.findByUsername(username);
	}
	
	public Collection<Actor> findBySpammer (Boolean a){
		return actorRepository.findByIsSpammer(a);
	}
}