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
import domain.EducationData;
import domain.Rookie;
import forms.EducationDataForm;

import services.CurriculaService;
import services.EducationDataService;
import services.RookieService;

@Controller
@RequestMapping("/curricula/educationData")
public class EducationDataRookieController {

	@Autowired
	private RookieService rookieService;

	@Autowired
	private EducationDataService educationDataService;

	@Autowired
	private CurriculaService curriculaService;

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result;
		EducationData educationData;
		EducationDataForm form;

		educationData = this.educationDataService.create();
		form = this.educationDataService.construct(educationData);
		
		form.setCurriculaId(curriculaId);
		
		result = new ModelAndView();
		result.addObject("educationDataForm", form);
		result.addObject("id", educationData.getId());
		result.addObject("curriculaId", form.getCurriculaId());

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationDataId) {
		ModelAndView result;
		EducationData educationData;

		Rookie rookie = this.rookieService.findByPrincipal();
		educationData = this.educationDataService.findOne(educationDataId);
		Curricula curricula = this.curriculaService.findByEducationData(educationData);
		EducationDataForm form = this.educationDataService.construct(educationData);
		Assert.notNull(educationData);
		
		form.setCurriculaId(curricula.getId());

		if (curricula.getRookie().equals(rookie)) {
			result = this.createEditModelAndView(form);
		} else {
			result = new ModelAndView("error/access");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("educationDataForm")@Valid EducationDataForm form, BindingResult binding) {
		ModelAndView result;
		Rookie logged = this.rookieService.findByPrincipal();
		EducationData educationData;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(form);
		} else {
			try {
				educationData = this.educationDataService.reconstruct(form, binding);
				Curricula curricula = this.curriculaService.findOne(form.getCurriculaId());
				
				if(form.getId()!=0){
					this.educationDataService.saveTrue(educationData);
				}else{
					this.educationDataService.save(educationData, curricula);
				}

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
				result = this.createEditModelAndView(form, "educationData.commit.error");
			}
		}
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int educationDataId) {
		ModelAndView result;
		final Rookie logged = this.rookieService.findByPrincipal();
		final EducationData educationData = this.educationDataService.findOne(educationDataId);
		final Curricula curricula = this.curriculaService.findByEducationData(educationData);
		
		if (curricula.getRookie().equals(logged)) {
			try {
				this.educationDataService.delete(educationData, curricula);
				result = new ModelAndView("redirect:/curricula/rookie/show.do?curriculaId="+curricula.getId());
//				result.addObject("curricula", curricula);
//				result.addObject("personalData", curricula.getPersonalData());
//				result.addObject("positionDatas", curricula.getPositionDatas());
//				result.addObject("miscellaneousDatas", curricula.getMiscellaneousDatas());
//				result.addObject("educationDatas", curricula.getEducationDatas());
//				result.addObject("rookieIsOwner", curricula.getRookie().equals(logged));
//				result.addObject("hasPersonalData", curricula.getPersonalData().equals(null));
//				result.addObject("rookieId", logged.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/curricula/rookie/show.do?curriculaId="+curricula.getId());
//				result.addObject("curricula", curricula);
//				result.addObject("personalData", curricula.getPersonalData());
//				result.addObject("positionDatas", curricula.getPositionDatas());
//				result.addObject("miscellaneousDatas", curricula.getMiscellaneousDatas());
//				result.addObject("educationDatas", curricula.getEducationDatas());
//				result.addObject("rookieIsOwner", curricula.getRookie().equals(logged));
//				result.addObject("hasPersonalData", curricula.getPersonalData() == null);
//				result.addObject("rookieId", logged.getId());
			}
		} else {
			result = new ModelAndView("error/access");
		}
		return result;
	}
	
	//Ancillary methods
	protected ModelAndView createEditModelAndView(final EducationDataForm form) {
		ModelAndView result;
		result = this.createEditModelAndView(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationDataForm form, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("curricula/educationData/create");
		result.addObject("educationDataForm", form);
		result.addObject("message", messageCode);
		result.addObject("curriculaId", form.getCurriculaId());
		result.addObject("id", form.getId());

		return result;
	}

}
