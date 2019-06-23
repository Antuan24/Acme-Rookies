package controllers.company;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.PositionService;
import services.ProblemService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Problem;
import forms.ProblemForm;

@Controller
@RequestMapping("problem/company/")
public class ProblemCompanyController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private PositionService posService;
	
	@Autowired
	private CompanyService companyService;
	
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Problem> problems = problemService.findProblemByPrincipal();
		
		result = new ModelAndView("problem/list");
		result.addObject("problems",problems);
		
		return result;
	}
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Problem problem;
		ProblemForm problemForm;
		
		problem = problemService.create();
		
		problemForm = problemService.construct(problem);
		
		
		result = this.createEditModelAndView(problemForm);
		
		return result;
	}
	
	// Show --------------------------------------------------------------------
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int problemId) {

		ModelAndView result;
		Problem problem;
		Collection<Position> positions;
		
		problem = problemService.findOne(problemId);
		positions = problem.getPositions();
		
		result = new ModelAndView("problem/show");
		result.addObject("problem", problem);
		result.addObject("positions", positions);
		result.addObject("requestURI", "problem/company/show.do");

		return result;
	}
	
	// Edit --------------------------------------------------------------------
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int problemId) {

			ModelAndView result;
			ProblemForm problemForm;
			Problem problem;
			
			problem = problemService.findOne(problemId);
			problemForm = problemService.construct(problem);
			
			result = this.createEditModelAndView(problemForm);

			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute("problemForm") ProblemForm problemForm, BindingResult bindingResult) {
		ModelAndView result;
		Problem problem;
			
		try {
			problem = problemService.reconstruct(problemForm, bindingResult);
			problemService.save(problem);
			result = new ModelAndView("redirect:list.do");
		} catch (ValidationException oops) {
			result = this.createEditModelAndView(problemForm);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(problemForm,"problem.commit.error");
		}
		
		return result;
	}
	
	// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int problemId) {
			ModelAndView result;
			Problem problem;
			Company logged;
			
			problem = problemService.findOne(problemId);
			logged = companyService.findByPrincipal();
			
			if(problemService.findProblemByCompany(logged.getId()).contains(problem)){	
				problemService.delete(problem);
				result = new ModelAndView("redirect:list.do");
			}else{
				result = new ModelAndView("error/access");
			}
			
			return result;
		}
	
	//Helper methods --------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(ProblemForm problem){
		ModelAndView res;
		res = createEditModelAndView(problem, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(ProblemForm problem, String messageCode){
		
		ModelAndView res;
		Collection<Position> positions;
		int id;
		
		id = companyService.findByPrincipal().getId();
		positions = posService.findPositionsByCompany(id);
		
		res = new ModelAndView("problem/edit");
		res.addObject("problemForm", problem);
		res.addObject("positions", positions);
		res.addObject("message", messageCode);

		return res;
	}
	
}
