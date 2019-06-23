package controllers.company;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import controllers.AbstractController;
import domain.Application;
import forms.ApplicationCompanyForm;
import forms.ApplicationFinderForm;

@Controller
@RequestMapping("application/company/")
public class ApplicationCompanyController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService appService;
	
	// Parameters ------------------------------------------------------------------------------
	
	private int positionId;
	private int rookieId;
	
	// filter: change filter parameters and lists pparades -------------------------------------

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public ModelAndView filter() {
		ModelAndView result;
		ApplicationFinderForm form = new ApplicationFinderForm();
		result = createEditModelAndView(form);
		return result;
	}

	@RequestMapping(value="/list", method= RequestMethod.POST, params = "list")
	public ModelAndView list(@Valid ApplicationFinderForm form, final BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(form);
		} else {
			try {
				result = createEditModelAndView(form);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(form,
						"application.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value="/list", method= RequestMethod.POST, params = "cancel")
	public ModelAndView cancel(ApplicationFinderForm form, final BindingResult binding) {
		ModelAndView result;
			try {
				form.setStatus(null);
				result = createEditModelAndView(form);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(form, "application.commit.error");
			}
		return result;
	}
	
	// Show --------------------------------------------------------------------
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int appId) {

		ModelAndView result;
		
		Application app;
		app = appService.findOne(appId);

		result = new ModelAndView("application/show");
		result.addObject("app", app);
		result.addObject("requestURI", "application/rookie/show.do");

		return result;
	}
	
	// Edit --------------------------------------------------------------------
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int appId) {

			ModelAndView result;
			Application app;
			ApplicationCompanyForm appForm;
			
			appForm = new ApplicationCompanyForm();
			app = appService.findOne(appId);
			this.positionId=app.getPosition().getId();
			this.rookieId=app.getRookie().getId();
			
			appForm.setId(app.getId());
			
			result = this.createEditModelAndView(appForm);

			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(@Valid ApplicationCompanyForm appForm, BindingResult bindingResult) {
		ModelAndView result;
		Application app;
		
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(appForm);
		else
			try {
				app = appService.reconstruct(appForm, positionId, rookieId, bindingResult);
				appService.saveC(app);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(appForm,"app.commit.error");
			}
		return result;
	}
	
	//Helper methods ----------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(ApplicationCompanyForm app){
		ModelAndView res;
		res = createEditModelAndView(app, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(ApplicationCompanyForm app, String messageCode){
		
		ModelAndView res;
		
		res = new ModelAndView("application/edit");
		res.addObject("appForm", app);
		res.addObject("message", messageCode);

		return res;
	}
	
	// MODEL AND VIEW FILTER
	protected ModelAndView createEditModelAndView(ApplicationFinderForm applicationFinderForm){
		ModelAndView res;
		res = createEditModelAndView(applicationFinderForm, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(ApplicationFinderForm applicationFinderForm, String messageCode){
		ModelAndView res;
		Collection<Application> applications = new HashSet<Application>();
		Collection<String> statuses = new HashSet<String>();
		statuses.add("PENDING"); statuses.add("SUBMITTED"); statuses.add("ACCEPTED"); statuses.add("REJECTED");

		res = new ModelAndView("application/list");
		if(applicationFinderForm.getStatus() == null || applicationFinderForm.getStatus().equals("0")){
			applications.addAll(appService.findAppByPrincipalCompany());
		}else{
			applications.addAll(appService.findAppByPrincipalCompanyAndStatus(applicationFinderForm.getStatus()));
		}
		res.addObject("applicationFinderForm",applicationFinderForm);
		res.addObject("requestURI","application/company/list.do");
		res.addObject("apps", applications);
		res.addObject("statuses",statuses);
		res.addObject("message", messageCode);

		return res;
	}
	
}
