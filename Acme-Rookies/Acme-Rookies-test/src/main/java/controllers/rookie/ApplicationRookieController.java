package controllers.rookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.RookieService;
import services.PositionService;
import services.ProblemService;
import controllers.AbstractController;
import domain.Application;
import domain.Curricula;
import domain.Position;
import domain.Problem;
import forms.ApplicationFinderForm;
import forms.ApplicationRookieForm;

@Controller
@RequestMapping("application/rookie/")
public class ApplicationRookieController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService appService;
	
	@Autowired
	private PositionService posService;
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private RookieService rookieService;
	
	// Parameters ------------------------------------------------------------------------------
	
	private int positionId;
	
	// filter: change filter parameters and lists parades --------------------------------------

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
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int positionId) {
		ModelAndView result;
		Position position;
		Application app;
		Collection<Problem> problems;
		
		position = posService.findOne(positionId);
		problems = problemService.findProblemFinal(position.getCompany().getId());
		
		if(problems.size()>0){
			app = appService.create(position);
			appService.save(app);
			result = new ModelAndView("redirect:list.do");
		}else{
			result = new ModelAndView("error/application");
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
			ApplicationRookieForm appForm;
			Application app;
			
			app = appService.findOne(appId);
			this.positionId = app.getPosition().getId();
			appForm = new ApplicationRookieForm();
			appForm.setId(appId);
			
			result = this.createEditModelAndView(appForm);

			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("appForm") ApplicationRookieForm appForm, BindingResult bindingResult) {
		ModelAndView result;
		Application app;
			try {
				app = appService.reconstruct(appForm,positionId,bindingResult);
				app.setStatus("SUBMITTED");
				appService.save(app);
				result = new ModelAndView("redirect:list.do");
			} catch (ValidationException oops) {
				result = this.createEditModelAndView(appForm);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(appForm,"app.commit.error");
			}
		return result;
	}
	
	//Helper methods ----------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(ApplicationRookieForm app){
		ModelAndView res;
		res = createEditModelAndView(app, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(ApplicationRookieForm appForm, String messageCode){
		
		ModelAndView res;
		Application app;
		Collection<Problem> problems;
		Collection<Curricula> curriculas = new ArrayList<Curricula>();
		
		app = appService.findOne(appForm.getId());
		problems = problemService.findProblemFinal(app.getPosition().getCompany().getId());
		curriculas = rookieService.findByPrincipal().getCurriculas();
		if(problems.size()>0){
			res = new ModelAndView("application/edit");
			res.addObject("appForm", appForm);
			res.addObject("problems", problems);
			res.addObject("curriculas", curriculas);
			res.addObject("message", messageCode);
		}else{
			res = new ModelAndView("error/application");
		}
		return res;
	}
	
	// MODELANDVIEW FILTER
	
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
			applications.addAll(appService.findAppByPrincipalRookie());
		}else{
			applications.addAll(appService.findAppByPrincipalRookieAndStatus(applicationFinderForm.getStatus()));
		}
		res.addObject("applicationFinderForm",applicationFinderForm);
		res.addObject("requestURI","application/rookie/list.do");
		res.addObject("apps", applications);
		res.addObject("statuses",statuses);
		res.addObject("message", messageCode);

		return res;
	}
	
}
