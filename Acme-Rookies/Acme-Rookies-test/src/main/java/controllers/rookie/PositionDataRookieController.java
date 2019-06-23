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
import domain.PositionData;
import forms.PositionDataForm;

import services.CurriculaService;
import services.RookieService;
import services.PositionDataService;

@Controller
@RequestMapping("/curricula/positionData")
public class PositionDataRookieController {

	@Autowired
	private RookieService rookieService;

	@Autowired
	private PositionDataService positionDataService;

	@Autowired
	private CurriculaService curriculaService;
	

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result;
		PositionData positionData;
		PositionDataForm form;
		
		positionData = this.positionDataService.create();
		form = this.positionDataService.construct(positionData);

		form.setCurriculaId(curriculaId);
		
		result = new ModelAndView();
		result.addObject("positionDataForm", form);
		result.addObject("id", positionData.getId());
		result.addObject("curriculaId", form.getCurriculaId());

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionDataId) {
		ModelAndView result;
		PositionData positionData;

		Rookie rookie = this.rookieService.findByPrincipal();
		positionData = this.positionDataService.findOne(positionDataId);
		Curricula curricula = this.curriculaService.findByPositionData(positionData);
		PositionDataForm form = this.positionDataService.construct(positionData);
		Assert.notNull(positionData);
		
		form.setCurriculaId(curricula.getId());

		if (curricula.getRookie().equals(rookie)) {
			result = this.createEditModelAndView(form);
		} else {
			result = new ModelAndView("error/access");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("positionDataForm")@Valid PositionDataForm form, BindingResult binding) {
		ModelAndView result;
		Rookie logged = this.rookieService.findByPrincipal();
		PositionData positionData;
		
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(form);
		} else {
			try {
				positionData = this.positionDataService.reconstruct(form, binding);
				Curricula curricula = this.curriculaService.findOne(form.getCurriculaId());
				
				if(form.getId()!=0){
					this.positionDataService.saveTrue(positionData);
				}else{
					this.positionDataService.save(positionData, curricula);
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
				result = this.createEditModelAndView(form, "positionData.commit.error");
			}
		}
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int positionDataId) {
		ModelAndView result;
		final Rookie logged = this.rookieService.findByPrincipal();
		final PositionData positionData = this.positionDataService.findOne(positionDataId);
		final Curricula curricula = this.curriculaService.findByPositionData(positionData);

		if (curricula.getRookie().equals(logged)) {
			try {
				this.positionDataService.delete(positionData, curricula);
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
	protected ModelAndView createEditModelAndView(final PositionDataForm form) {
		ModelAndView result;
		result = this.createEditModelAndView(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PositionDataForm form, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("curricula/positionData/create");
		result.addObject("positionDataForm", form);
		result.addObject("message", messageCode);
		result.addObject("curriculaId", form.getCurriculaId());
		result.addObject("id", form.getId());

		return result;
	}

}
