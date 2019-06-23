package controllers.rookie;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.RookieService;
import services.PersonalDataService;
import domain.Curricula;
import domain.Rookie;
import domain.PersonalData;
import forms.CurriculaDataForm;

@Controller
@RequestMapping("/curricula/rookie")
public class CurriculaRookieController {
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private PersonalDataService personalDataService;
	
	//show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		Rookie rookie = this.rookieService.findByPrincipal();
		
		Curricula curricula = this.curriculaService.findOne(curriculaId);
		Boolean isOwner = false;
		Boolean hasPersonalData = true;
		
		try {
			Rookie logged = rookieService.findByPrincipal();
			if(logged.getId() == rookie.getId()){isOwner=true;}
			if(curricula.getPersonalData() == null){hasPersonalData = false;}
		} catch (Exception e) {
			System.out.println("Cazado hehe.");
			e.printStackTrace();

		}
		
		result = new ModelAndView("curricula/show");
		result.addObject("curricula", curricula);
		result.addObject("personalData", curricula.getPersonalData());
		result.addObject("positionDatas", curricula.getPositionDatas());
		result.addObject("miscellaneousDatas", curricula.getMiscellaneousDatas());
		result.addObject("educationDatas", curricula.getEducationDatas());
		result.addObject("requestURI", "curricula/rookie/show.do");
		result.addObject("rookieIsOwner", isOwner);
		result.addObject("hasPersonalData", hasPersonalData);
		
		return result;
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Rookie rookie;
		Rookie logged;
		rookie = this.rookieService.findByPrincipal();
		logged = this.rookieService.findByPrincipal();
		final Collection<Curricula> curriculas = this.curriculaService.findByRookie(logged);
		
		
		result = new ModelAndView("curricula/list");
		result.addObject("curriculas", curriculas);
		result.addObject("rookieIsOwner", rookie.getId() == logged.getId());
		result.addObject("requestURI", "curricula/rookie/list.do");
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;		
		CurriculaDataForm form = new CurriculaDataForm();
				
		result = new ModelAndView("curricula/editForm");
		result.addObject("form", form);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculaId) {
		ModelAndView result;
		Curricula curricula;
		Rookie logged = this.rookieService.findByPrincipal();
		
		curricula = this.curriculaService.findOne(curriculaId);
		
		if(curricula.getRookie().equals(logged)) {
			result = this.createEditModelAndView(curricula);
		} else {
			result = new ModelAndView("error/access");
		}
		
		
		return result;
	}
	@RequestMapping(value = "/editForm", method = RequestMethod.POST, params = "save")
	public ModelAndView saveForm(CurriculaDataForm form, BindingResult binding) {
		ModelAndView result;
		
			Rookie logged = rookieService.findByPrincipal();
		
		if(binding.hasErrors()) {
			result = this.createEditFormModelAndView(form);
		} else {
			try {
				PersonalData data = personalDataService.saveTrue(personalDataService.reconstruct(form, binding));
				System.out.println(data);
				Curricula curricula = this.curriculaService.save(curriculaService.reconstruct(form, data, binding));
				logged.getCurriculas().add(curricula);
				this.rookieService.save(logged);
				result = new ModelAndView("curricula/list");
				result.addObject("curriculas", logged.getCurriculas());
				result.addObject("rookieIsOwner", true);
				result.addObject("requestURI", "curricula/rookie/list.do");
			} catch (Throwable oops) {
				result = this.createEditFormModelAndView(form, "curricula.commit.error");
			}
		}
		return result;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Curricula curricula, BindingResult binding) {
		ModelAndView result;
		Rookie rookie = this.rookieService.findByPrincipal();
		Rookie logged = this.rookieService.findByPrincipal();
		Collection<Curricula> curriculas = this.curriculaService.findByRookie(logged);
		
		if(binding.hasErrors()) {
			result = this.createEditModelAndView(curricula);
		} else {
			try {
				this.curriculaService.save(curricula);
				curriculas.add(curricula);
				this.rookieService.save(logged);
				result = new ModelAndView("redirect:/curricula/rookie/list.do");
//				result.addObject("curriculas", curriculas);
//				result.addObject("rookieIsOwner", rookie.getId() == logged.getId());
//				result.addObject("requestURI", "curricula/rookie/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(curricula, "curricula.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int curriculaId) {
		ModelAndView result;
		Rookie logged = this.rookieService.findByPrincipal();
		Curricula curricula = this.curriculaService.findOne(curriculaId);
		System.out.println(logged + " "+ curricula);
		
		if(curricula.getRookie().equals(logged)) {
			try {
				System.out.println("intento vaciar la curricula");
				this.curriculaService.emptyCurricula(curricula);
				System.out.println("se vacia");
				System.out.println("intento borrar la curricula");
				this.curriculaService.delete(curricula);
				System.out.println("se borra");
				result = new ModelAndView("redirect:/curricula/rookie/list.do");
				
//				result.addObject("curriculas", this.curriculaService.findByRookie(logged));
//				result.addObject("rookieIsOwner", curricula.getRookie().equals(logged));
//				result.addObject("requestURI", "curricula/rookie/list.do");
			} catch (Throwable oops) {
				System.out.println(oops);
				result = new ModelAndView("redirect:/curricula/rookie/list.do");
//				result.addObject("curriculas", this.curriculaService.findByRookie(logged));
//				result.addObject("rookieIsOwner", curricula.getRookie().equals(logged));
//				result.addObject("requestURI", "curricula/rookie/list.do");
			}
		} else {
			result = new ModelAndView("error/access");
		}
		return result;
	}
	
	//Helper methods
	
	protected ModelAndView createEditModelAndView(Curricula curricula) {
		ModelAndView result;
		
		result = this.createEditModelAndView(curricula, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Curricula curricula, String message) {
		ModelAndView result;
		
		result = new ModelAndView("curricula/edit");
		result.addObject("curricula", curricula);
		result.addObject("message", message);
		
		return result;
	}
	
	//for the form
	
	protected ModelAndView createEditFormModelAndView(CurriculaDataForm form) {
		ModelAndView result;
		
		result = this.createEditFormModelAndView(form, null);
		
		return result;
	}
	
	protected ModelAndView createEditFormModelAndView(CurriculaDataForm form, String message) {
		ModelAndView result;
		
		result = new ModelAndView("curricula/edit");
		result.addObject("form", form);
		result.addObject("message", message);
		
		return result;
	}
	
}	
