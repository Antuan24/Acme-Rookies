
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CompanyService;
import domain.Company;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {
	
	// Services ----------------------------------------------------------------

		@Autowired
		private ActorService actorService;
		
		@Autowired
		private CompanyService companyService;

	// Constructors -----------------------------------------------------------

	public CompanyController() {
		super();
	}

	//List---------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Company> companies = companyService.findAll();
		
		result = new ModelAndView("company/list");
		result.addObject("companies", companies);
		return result;
	}

}
