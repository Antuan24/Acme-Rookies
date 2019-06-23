package controllers.rookie;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Curricula;
import domain.Rookie;
import domain.PersonalData;
import forms.PersonalDataForm;

import services.CurriculaService;
import services.RookieService;
import services.PersonalDataService;

@Controller
@RequestMapping("/curricula/personalData")
public class PersonalDataRookieController {

	@Autowired
	private RookieService rookieService;

	@Autowired
	private PersonalDataService personalDataService;

	@Autowired
	private CurriculaService curriculaService;
	

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int curriculaId) {
		ModelAndView result;
		PersonalData personalData;

		personalData = this.personalDataService.create();
		result = this.createEditModelAndView(personalData);

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView result;
		PersonalData personalData;

		Rookie rookie = this.rookieService.findByPrincipal();
		personalData = this.personalDataService.findOne(personalDataId);
		Curricula curricula = this.curriculaService.findByPersonalData(personalData);
		Assert.notNull(personalData);

		if (curricula.getRookie().equals(rookie)) {
			result = this.createEditModelAndView(personalData);
		} else {
			result = new ModelAndView("error/access");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("personalData") @Valid PersonalData personalData, BindingResult binding) {
		ModelAndView result;
		Rookie logged = this.rookieService.findByPrincipal();
		Curricula curricula = this.curriculaService.findByPersonalData(personalData);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(personalData);
		} else {
			try {
				this.personalDataService.save(personalData, curricula);
				result = new ModelAndView("redirect:/curricula/rookie/show.do?curriculaId="+curricula.getId());
//				result.addObject("curricula", curricula);
//				result.addObject("personalData", curricula.getPersonalData());
//				result.addObject("positionDatas", curricula.getPositionDatas());
//				result.addObject("miscellaneousDatas", curricula.getMiscellaneousDatas());
//				result.addObject("educationDatas", curricula.getEducationDatas());
//				result.addObject("rookieIsOwner", curricula.getRookie().equals(logged));
//				result.addObject("hasPersonalData", curricula.getPersonalData() == null);
//				result.addObject("rookieId", logged.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalData, "personalData.commit.error");
			}
		}
		return result;

	}
	
	//Ancillary methods
	protected ModelAndView createEditModelAndView(final PersonalData personalData) {
		ModelAndView result;
		result = this.createEditModelAndView(personalData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalData personalData, final String messageCode) {
		ModelAndView result;
		Curricula curricula = this.curriculaService.findByPersonalData(personalData);
		
		result = new ModelAndView("curricula/personalData/create");
		result.addObject("personalData", personalData);
		result.addObject("message", messageCode);
		result.addObject("personalDataId", personalData.getId());
		result.addObject("curricula", curricula);
		
		return result;
	}

}
