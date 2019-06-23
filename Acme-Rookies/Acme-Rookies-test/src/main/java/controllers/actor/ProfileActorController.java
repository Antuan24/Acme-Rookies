package controllers.actor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.AdminService;
import services.AuditorService;
import services.CompanyService;
import services.CreditCardService;
import services.MessageService;
import services.ProviderService;
import services.RookieService;
import controllers.AbstractController;
import domain.Actor;
import domain.Admin;
import domain.Auditor;
import domain.Company;
import domain.CreditCard;
import domain.Provider;
import domain.Rookie;
import domain.SocialProfile;
import forms.ProfileForm;

@Controller
@RequestMapping("actor/")
public class ProfileActorController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private CreditCardService cardService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private Validator validator;
	

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) Integer actorId) {

		ModelAndView result;

		// Diferentes autoridades:
		Authority adminAuth = new Authority();
		adminAuth.setAuthority("ADMIN");

		Authority rookieAuth = new Authority();
		rookieAuth.setAuthority("ROOKIE");

		Authority companyAuth = new Authority();
		companyAuth.setAuthority("COMPANY");
		
		Authority auditorAuth = new Authority();
		auditorAuth.setAuthority("AUDITOR");
		
		Authority providerauth = new Authority();
		providerauth.setAuthority("PROVIDER");

		Boolean isCompany = false;
		
		Actor principal;
		try {
			principal = actorService.getByUserAccount(LoginService.getPrincipal());
		} catch (Exception e) {
			principal = null;
		}
		
		

		result = new ModelAndView("actor/show");

		if (actorId != null) { //accedemos al perfil de otro actor
			Actor actor = actorService.findOne(actorId);
			Collection<SocialProfile> socialProfiles = actor.getSocialProfiles();
				
			result.addObject("actor", actor); // actor que se va a mostrar
			result.addObject("logged", false); // flag para permitir editar

			if (actor.getUserAccount().getAuthorities().contains(companyAuth)) {
				isCompany = true;
			}
			if (principal == null) {
				
			}else if (principal.getUserAccount().getAuthorities().contains(adminAuth)) {
				result.addObject("principalIsAdmin", true);
			}

			result.addObject("socialProfiles", socialProfiles);

		} else {
			Actor actor = actorService.getByUserAccount(LoginService.getPrincipal());
			Collection<SocialProfile> socialProfiles = actor.getSocialProfiles();
			result.addObject("logged", true);
			
			if (actor.getUserAccount().getAuthorities().contains(adminAuth)) {
				Admin admin = adminService.findOne(actor.getId());
				result.addObject("actor", admin);
				result.addObject("principalIsAdmin", true);
			}

			if (actor.getUserAccount().getAuthorities().contains(rookieAuth)) {
				Rookie rookie = rookieService.findOne(actor.getId());
				result.addObject("actor", rookie);
			}

			if (actor.getUserAccount().getAuthorities().contains(companyAuth)) {
				isCompany = true;
				Company company = companyService.findOne(actor.getId());
				result.addObject("actor", company);
			}
			
			if (actor.getUserAccount().getAuthorities().contains(auditorAuth)) {
				Auditor auditor = auditorService.findOne(actor.getId());
				result.addObject("actor", auditor);
			}
			
			if (actor.getUserAccount().getAuthorities().contains(providerauth)) {
				Provider provider = providerService.findOne(actor.getId());
				result.addObject("isProvider", true);
				result.addObject("debt", Math.round(provider.getDebt()*1000d)/1000d);
				result.addObject("actor", provider);
			}
			
			result.addObject("socialProfiles", socialProfiles);
		}
		
		
		result.addObject("isCompany", isCompany);
		result.addObject("requestURI", "actor/show.do");

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/editPersonal", method = RequestMethod.GET)
	public ModelAndView editPersonal() {

		ModelAndView result = new ModelAndView();
		ProfileForm f = new ProfileForm();
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		ProfileForm form = actorService.rellenaForm(a, f, new ArrayList<String>());
		
		
		result = createEditModelAndView(form,"personal");

		return result;
	}
	
	@RequestMapping(value = "/editCreditCard", method = RequestMethod.GET)
	public ModelAndView editCredit() {

		ModelAndView result = new ModelAndView();
		ProfileForm f = new ProfileForm();
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		ProfileForm form = actorService.rellenaForm(a, f, new ArrayList<String>());
		result = createEditModelAndView(form,"credit");

		return result;
	}
	
	@RequestMapping(value = "/editUserAccount", method = RequestMethod.GET)
	public ModelAndView editAccount() {

		ModelAndView result = new ModelAndView();
		ProfileForm f = new ProfileForm();
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		ProfileForm form = actorService.rellenaForm(a, f, new ArrayList<String>());
		result = createEditModelAndView(form,"account");

		return result;
	}

	// Save -----------------------------------------------------------------
	// Personal Data
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "savePersonal")
	public ModelAndView savePersonal(ProfileForm profileForm, final BindingResult binding) {
		ModelAndView res;
		
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		Authority c = new Authority();
		c.setAuthority(Authority.COMPANY);
		
		List<String> aux = new ArrayList<>();
		aux.add("account");
		aux.add("credit");
		ProfileForm form = actorService.rellenaForm(a, profileForm, aux );
		if (!a.getUserAccount().getAuthorities().contains(c)) {form.setCommercialName("default"); }
		
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()) {
			System.out.println(binding);
			res = this.createEditModelAndView(form, "personal");
			return res;
		} else {
			try {
				
		if(a.getUserAccount().getAuthorities().contains(c)){
			Company company = companyService.findByPrincipal();
			company.setCommercialName(form.getCommercialName());
			company.setAddress(form.getAddress());
			company.setEmail(form.getEmail());
			company.setName(form.getName());
			company.setPhone(form.getPhone());
			company.setPhoto(form.getPhoto());
			company.setSurnames(form.getSurnames());
			company.setVatNumber(form.getVatNumber());
			
			companyService.save(company);
		}else{
			a.setAddress(form.getAddress());
			a.setEmail(form.getEmail());
			a.setName(form.getName());
			a.setPhone(form.getPhone());
			a.setPhoto(form.getPhoto());
			a.setSurnames(form.getSurnames());
			a.setVatNumber(form.getVatNumber());
			
			actorService.save(a);
		}
		
		res = new ModelAndView("redirect:show.do");
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editPersonal();
		return res;
		}
	}
}
	
	// UserAccount
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAccount")
	public ModelAndView saveAccount(ProfileForm profileForm, final BindingResult binding) {
		ModelAndView res;
		Boolean passMatch = false;		
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		
		List<String> aux = new ArrayList<>();
		aux.add("credit");
		aux.add("personal");
		ProfileForm form = actorService.rellenaForm(a, profileForm, aux );
		
		if(form.getPassword().equals(form.getPassword2())){passMatch=true;}
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()|| !passMatch) {
			System.out.println(binding);
			res = this.createEditModelAndView(form, "account");
			res.addObject("passMatch", passMatch);
			return res;
		} else {
			try {
		UserAccount account = actorService.getByUserAccount(LoginService.getPrincipal()).getUserAccount();
		account.setPassword(form.getPassword());
		account.setUsername(form.getUsername());
		userAccountService.save(account);
		res = new ModelAndView("redirect:/j_spring_security_logout");
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editAccount();
		return res;
		}
	}
}
	// UserAccount
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCredit")
	public ModelAndView saveCreditCard(ProfileForm profileForm, final BindingResult binding) {
		ModelAndView res;
		
		Boolean expired = false;
		Calendar cal = Calendar.getInstance();
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		
		List<String> aux = new ArrayList<>();
		aux.add("account");
		aux.add("personal");
		ProfileForm form = actorService.rellenaForm(a, profileForm, aux );
		
		if(form.getExpirationYear()== cal.get(Calendar.YEAR) && form.getExpirationMonth()<=cal.get(Calendar.MONTH)){
			expired = true;
		}
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()|| expired) {
			System.out.println(binding);
			res = this.createEditModelAndView(form, "credit");
			res.addObject("isExpired", expired);

			return res;
		} else {
			try {
			CreditCard credit = actorService.getByUserAccount(LoginService.getPrincipal()).getCreditCard();
			Calendar calendar = Calendar.getInstance();
			calendar.set(form.getExpirationYear(), form.getExpirationMonth(), 1);
			Date date = calendar.getTime();
			
			credit.setCVV(form.getCVV());
			credit.setExpirationDate(date);
			credit.setHolder(form.getHolder());
			credit.setMake(form.getMake());
			credit.setNumber(form.getNumber());
			
			cardService.save(credit);
			
		res = new ModelAndView("redirect:show.do");
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editCredit();
		return res;
		}
	}
}

	
//Generate JSON---------------------------------------------------------------------
	@RequestMapping(value = "/generateData", method = RequestMethod.GET)
	public ModelAndView generate() {
		
		ModelAndView res = new ModelAndView("actor/personalData");

		Actor actor = actorService.getByUserAccount(LoginService.getPrincipal());
		
		res.addObject("data", actorService.actorToJson(actor));

		return res;
	}

//Ancillary---------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final ProfileForm form, String type) {
		ModelAndView result;
		result = this.createEditModelAndView(form, type ,null);
		return result;
	}

	private ModelAndView createEditModelAndView(final ProfileForm form, String type, final String message) {

		ModelAndView result = new ModelAndView("actor/edit");
		Authority c = new Authority();
		c.setAuthority(Authority.COMPANY);
		
		
		Date d = new Date();
		Collection <Integer> months = new ArrayList<>();
		Collection <Integer> years = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			months.add(i+1);
			years.add(d.getYear()+i+1900);
		}
		
		result.addObject("message", message);
		result.addObject("profileForm", form);
		result.addObject("showAccount", type=="account");
		result.addObject("showCredit", type=="credit");
		result.addObject("showPersonal", type=="personal");
		result.addObject("isCompany", LoginService.getPrincipal().getAuthorities().contains(c));
		result.addObject("months",months);
		result.addObject("years",years);
		
		return result;
	}
}