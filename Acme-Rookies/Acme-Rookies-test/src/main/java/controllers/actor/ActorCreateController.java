package controllers.actor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.FinderService;
import controllers.AbstractController;
import domain.Admin;
import domain.Auditor;
import domain.Company;
import domain.Finder;
import domain.Provider;
import domain.Rookie;
import forms.RegisterCompanyForm;
import forms.RegisterProviderForm;
import forms.RegisterRookieAdminForm;

@Controller
@RequestMapping("actor/")
public class ActorCreateController extends AbstractController {

	// Services ----------------------------------------------------------------

	// @Autowired
	// private UserAccountService userAccountService;

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private FinderService finderService;

	// Constructors ------------------------------------------------------------

	public ActorCreateController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/registerRookie", method = RequestMethod.GET)
	public ModelAndView registerRookie() {
		RegisterRookieAdminForm form = new RegisterRookieAdminForm();
		return this.createRegisterRookieAdminModelAndView(form, "ROOKIE");
	}

	@RequestMapping(value = "/registerCompany", method = RequestMethod.GET)
	public ModelAndView registerCompany() {
		RegisterCompanyForm form = new RegisterCompanyForm();
		return this.createRegisterCompanyModelAndView(form);
	}
	
	@RequestMapping(value = "/registerProvider", method = RequestMethod.GET)
	public ModelAndView registerProvider() {
		RegisterProviderForm form = new RegisterProviderForm();
		return this.createRegisterProviderModelAndView(form);
	}

	@RequestMapping(value = "/registerAdmin", method = RequestMethod.GET)
	public ModelAndView createAdmin() {
		if(LoginService.hasRole("ADMIN")){
			RegisterRookieAdminForm form = new RegisterRookieAdminForm();
			return this.createRegisterRookieAdminModelAndView(form, "ADMIN");
		}else{
			return new ModelAndView("error/access");
		}
	}
	
	@RequestMapping(value = "/registerAuditor", method = RequestMethod.GET)
	public ModelAndView createAuditor() {
		if(LoginService.hasRole("ADMIN")){
			RegisterRookieAdminForm form = new RegisterRookieAdminForm();
			return this.createRegisterRookieAdminModelAndView(form, "AUDITOR");
		}else{
			return new ModelAndView("error/access");
		}
	}
	// SAVES-------------------------------------------------------

	// ROOKIE
	@RequestMapping(value = "/registerRookie", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMember(@ModelAttribute("registerForm") RegisterRookieAdminForm registerForm, final BindingResult binding) {
		Boolean expired = false;
		Boolean passMatch = false;
		
		Calendar c = Calendar.getInstance();		
		System.out.println("el año es: " + c.get(Calendar.YEAR)+ " y el mes es: "+ c.get(Calendar.MONTH));
		if(registerForm.getExpirationYear()== c.get(Calendar.YEAR) && registerForm.getExpirationMonth()<=c.get(Calendar.MONTH)){
			expired = true;
		}
		
		if(registerForm.getPassword().equals(registerForm.getPassword2())){passMatch=true;}
		
		
			Rookie rookie = this.actorService.reconstructRookie(registerForm, binding);
			
			if (binding.hasErrors() || expired || !passMatch) {
				System.out.println(binding);
				ModelAndView res = this.createRegisterRookieAdminModelAndView(registerForm, "ROOKIE");
				res.addObject("isExpired", expired);
				res.addObject("passMatch", passMatch);
				return res;
			} else {
			try {
				Rookie h = actorService.registerRookie(rookie);
				
				Finder f = finderService.create();
				f.setRookie(h);
				finderService.save(f);

				return new ModelAndView("redirect:/");
			
		} catch (final Throwable oops) {
			oops.printStackTrace();
			RegisterRookieAdminForm form = new RegisterRookieAdminForm();
			return this.createRegisterRookieAdminModelAndView(form, "ROOKIE");
			}
		}
	}

	// COMPANY
	@RequestMapping(value = "/registerCompany", method = RequestMethod.POST, params = "save")
	public ModelAndView saveBrotherhood(@ModelAttribute("registerForm") RegisterCompanyForm registerForm, final BindingResult binding) {

		Boolean expired = false;
		Boolean passMatch = false;
		
		Calendar c = Calendar.getInstance();		
		System.out.println("el año es: " + c.get(Calendar.YEAR)+ " y el mes es: "+ c.get(Calendar.MONTH));
		if(registerForm.getExpirationYear()== c.get(Calendar.YEAR) && registerForm.getExpirationMonth()<=c.get(Calendar.MONTH)){
			expired = true;
		}
		
		if(registerForm.getPassword().equals(registerForm.getPassword2())){passMatch=true;}
		
		Company company = this.actorService.reconstructCompany(registerForm, binding);
		
		if (binding.hasErrors() || expired || !passMatch) {
			System.out.println(binding);
			ModelAndView res = this.createRegisterCompanyModelAndView(registerForm);
			res.addObject("isExpired", expired);
			res.addObject("passMatch", passMatch);
			return res;
		} else {
			try {
				actorService.registerCompany(company);
				return new ModelAndView("redirect:/");

			} catch (final Throwable oops) {
				oops.printStackTrace();
				RegisterCompanyForm form = new RegisterCompanyForm();
				return this.createRegisterCompanyModelAndView(form);
			}
		}
	}
	
	// COMPANY
		@RequestMapping(value = "/registerProvider", method = RequestMethod.POST, params = "save")
		public ModelAndView saveBrotherhood(@ModelAttribute("registerForm") RegisterProviderForm registerForm, final BindingResult binding) {

			Boolean expired = false;
			Boolean passMatch = false;
			
			Calendar c = Calendar.getInstance();		
			System.out.println("el año es: " + c.get(Calendar.YEAR)+ " y el mes es: "+ c.get(Calendar.MONTH));
			if(registerForm.getExpirationYear()== c.get(Calendar.YEAR) && registerForm.getExpirationMonth()<=c.get(Calendar.MONTH)){
				expired = true;
			}
			
			if(registerForm.getPassword().equals(registerForm.getPassword2())){passMatch=true;}
			
			Provider provider = this.actorService.reconstructProvider(registerForm, binding);
			
			if (binding.hasErrors() || expired || !passMatch) {
				System.out.println(binding);
				ModelAndView res = this.createRegisterProviderModelAndView(registerForm);
				res.addObject("isExpired", expired);
				res.addObject("passMatch", passMatch);
				return res;
			} else {
				try {
					actorService.registerProvider(provider);
					return new ModelAndView("redirect:/");

				} catch (final Throwable oops) {
					oops.printStackTrace();
					RegisterProviderForm form = new RegisterProviderForm();
					return this.createRegisterProviderModelAndView(form);
				}
			}
		}

	// ADMIN
	@RequestMapping(value = "/registerAdmin", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdmin(@ModelAttribute("registerForm") RegisterRookieAdminForm registerForm,final BindingResult binding) {
		
		Boolean expired = false;
		Boolean passMatch = false;
		
		Calendar c = Calendar.getInstance();		
		System.out.println("el año es: " + c.get(Calendar.YEAR)+ " y el mes es: "+ c.get(Calendar.MONTH));
		if(registerForm.getExpirationYear()== c.get(Calendar.YEAR) && registerForm.getExpirationMonth()<=c.get(Calendar.MONTH)){
			expired = true;
		}
		
		if(registerForm.getPassword().equals(registerForm.getPassword2())){passMatch=true;}
		
		Admin admin = this.actorService.reconstructAdmin(registerForm,binding);
		
		// Configuration config = configurationService.find();
		if (binding.hasErrors()|| expired || !passMatch) {
			ModelAndView res = this.createRegisterRookieAdminModelAndView(registerForm, "ADMIN");
			res.addObject("isExpired", expired);
			res.addObject("passMatch", passMatch);
			return res;
		} else {
			try {
				actorService.registerAdmin(admin);
				return new ModelAndView("redirect:/");

			} catch (final Throwable oops) {
				oops.printStackTrace();
				RegisterRookieAdminForm form = new RegisterRookieAdminForm();
				return this.createRegisterRookieAdminModelAndView(form, "ADMIN");
			}
		}
	}
	
	// ADMIN
	@RequestMapping(value = "/registerAuditor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAuditor(@ModelAttribute("registerForm") RegisterRookieAdminForm registerForm, final BindingResult binding) {
		
		Boolean expired = false;
		Boolean passMatch = false;
		
		Calendar c = Calendar.getInstance();		
		System.out.println("el año es: " + c.get(Calendar.YEAR)+ " y el mes es: "+ c.get(Calendar.MONTH));
		if(registerForm.getExpirationYear()== c.get(Calendar.YEAR) && registerForm.getExpirationMonth()<=c.get(Calendar.MONTH)){
			expired = true;
		}
		
		if(registerForm.getPassword().equals(registerForm.getPassword2())){passMatch=true;}
		
		Auditor auditor = this.actorService.reconstructAuditor(registerForm, binding);
		
		// Configuration config = configurationService.find();
		if (binding.hasErrors()|| expired || !passMatch) {
			ModelAndView res = this.createRegisterRookieAdminModelAndView(registerForm, "AUDITOR");
			res.addObject("isExpired", expired);
			res.addObject("passMatch", passMatch);
			return res;
		} else {
			try {
				actorService.registerAuditor(auditor);
				return new ModelAndView("redirect:/");

			} catch (final Throwable oops) {
				oops.printStackTrace();
				RegisterRookieAdminForm form = new RegisterRookieAdminForm();
				return this.createRegisterRookieAdminModelAndView(form, "AUDITOR");
			}
		}
	}

	protected ModelAndView createRegisterRookieAdminModelAndView(RegisterRookieAdminForm form, String actor) {
		ModelAndView result;
		result = this.createRegisterRookieAdminModelAndView(form, actor, null);
		return result;
	}
	
	protected ModelAndView createRegisterCompanyModelAndView(RegisterCompanyForm form) {
		ModelAndView result;
		result = this.createRegisterCompanyModelAndView(form, null);
		return result;
	}
	
	protected ModelAndView createRegisterProviderModelAndView(RegisterProviderForm form) {
		ModelAndView result;
		result = this.createRegisterProviderModelAndView(form, null);
		return result;
	}

	protected ModelAndView createRegisterRookieAdminModelAndView(RegisterRookieAdminForm form, String actor, String messageCode) {
		ModelAndView res;
		Date d = new Date();
		Collection <Integer> months = new ArrayList<>();
		Collection <Integer> years = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			months.add(i+1);
			years.add(d.getYear()+i+1900);
		}

		if (actor == "ROOKIE") {
			System.out.println("Creo las vista de registrar rookie");

			res = new ModelAndView("actor/registerRookie");
			res.addObject("registerForm", form);

		}else if(actor == "AUDITOR"){
			System.out.println("Creo las vista de registrar auditor");

			res = new ModelAndView("actor/registerAuditor");
			res.addObject("registerForm", form);
		}else{ //ADMIN
			System.out.println("Creo las vista de registrar admin");

			res = new ModelAndView("actor/registerAdmin");
			res.addObject("registerForm", form);
			
		}
		res.addObject("message", messageCode);
		res.addObject("months", months);
		res.addObject("years", years);
		return res;
	}
	
	protected ModelAndView createRegisterCompanyModelAndView(RegisterCompanyForm form, String messageCode) {
		ModelAndView res;
		
		Date d = new Date();
		Collection <Integer> months = new ArrayList<>();
		Collection <Integer> years = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			months.add(i+1);
			years.add(d.getYear()+i+1900);
		}
			
		System.out.println("Creo las vista de registrar company");
		res = new ModelAndView("actor/registerCompany");
		res.addObject("registerForm", form);
		
		res.addObject("months", months);
		res.addObject("years", years);
		res.addObject("message", messageCode);
		return res;
	}
	
	protected ModelAndView createRegisterProviderModelAndView(RegisterProviderForm form, String messageCode) {
		ModelAndView res;
		
		Date d = new Date();
		Collection <Integer> months = new ArrayList<>();
		Collection <Integer> years = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			months.add(i+1);
			years.add(d.getYear()+i+1900);
		}
			
		System.out.println("Creo las vista de registrar provider");
		res = new ModelAndView("actor/registerProvider");
		res.addObject("registerForm", form);
		
		res.addObject("months", months);
		res.addObject("years", years);
		res.addObject("message", messageCode);
		return res;
	}
	

}
