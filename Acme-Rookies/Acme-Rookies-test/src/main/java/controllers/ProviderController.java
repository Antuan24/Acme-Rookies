package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProviderService;
import domain.Provider;

@Controller
@RequestMapping("provider/")
public class ProviderController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProviderService providerService;
	
	// Listing ---------------------------------------------------------------------------------------

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public ModelAndView list() {
		
		ModelAndView result;
		Collection<Provider> providers;
		
		providers = providerService.findAll();
		
		result = new ModelAndView("provider/list");
		result.addObject("providers",providers);
		
		return result;
	}
	
}
