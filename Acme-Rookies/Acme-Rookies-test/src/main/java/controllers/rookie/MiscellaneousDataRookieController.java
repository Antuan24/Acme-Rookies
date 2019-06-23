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
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

import services.CurriculaService;
import services.RookieService;
import services.MiscellaneousDataService;

@Controller
@RequestMapping("/curricula/miscellaneousData")
public class MiscellaneousDataRookieController {

	@Autowired
	private RookieService rookieService;

	@Autowired
	private MiscellaneousDataService miscellaneousDataService;

	@Autowired
	private CurriculaService curriculaService;


	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result;
		MiscellaneousData miscellaneousData;
		MiscellaneousDataForm form;

		miscellaneousData = this.miscellaneousDataService.create();
		form = this.miscellaneousDataService.construct(miscellaneousData);
		
		form.setCurriculaId(curriculaId);
		
		result = new ModelAndView();
		result.addObject("miscellaneousDataForm", form);
		result.addObject("id", miscellaneousData.getId());
		result.addObject("curriculaId", form.getCurriculaId());
		

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousDataId) {
		ModelAndView result;
		MiscellaneousData miscellaneousData;

		Rookie rookie = this.rookieService.findByPrincipal();
		miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
		Curricula curricula = this.curriculaService.findByMiscellaneousData(miscellaneousData);
		MiscellaneousDataForm form = this.miscellaneousDataService.construct(miscellaneousData);
		Assert.notNull(miscellaneousData);
		
		form.setCurriculaId(curricula.getId());

		if (curricula.getRookie().equals(rookie)) {
			result = this.createEditModelAndView(form);
		} else {
			result = new ModelAndView("error/access");
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("miscellaneousDataForm")@Valid MiscellaneousDataForm form, BindingResult binding) {
		ModelAndView result;
		Rookie logged = this.rookieService.findByPrincipal();
		MiscellaneousData miscellaneousData;
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(form);
		} else {
			try {
				miscellaneousData = this.miscellaneousDataService.reconstruct(form, binding);
				Curricula curricula = this.curriculaService.findOne(form.getCurriculaId());
				if(form.getId()!=0){
					this.miscellaneousDataService.saveTrue(miscellaneousData);
				}else{
					this.miscellaneousDataService.save(miscellaneousData, curricula);
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
				result = this.createEditModelAndView(form, "miscellaneousData.commit.error");
			}
		}
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int miscellaneousDataId) {
		ModelAndView result;
		final Rookie logged = this.rookieService.findByPrincipal();
		final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
		final Curricula curricula = this.curriculaService.findByMiscellaneousData(miscellaneousData);

		if (curricula.getRookie().equals(logged)) {
			try {
				System.out.println("borrando");
				this.miscellaneousDataService.delete(miscellaneousData, curricula);
				System.out.println("borrado");
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
	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm form) {
		ModelAndView result;
		result = this.createEditModelAndView(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm form, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("curricula/miscellaneousData/create");
		result.addObject("miscellaneousDataForm", form);
		result.addObject("message", messageCode);
		result.addObject("curriculaId", form.getCurriculaId());
		result.addObject("id", form.getId());

		return result;
	}

}
