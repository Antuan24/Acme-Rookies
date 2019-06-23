package controllers.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CompanyService;
import services.PositionService;
import services.ProblemService;

import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Problem;
import forms.PositionFinderForm;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private CompanyService companyService;
	
	// List -----------------------------------------------------------------
	
	@RequestMapping(value="/list", method= RequestMethod.GET)
	public ModelAndView filter() {
		ModelAndView result;
		PositionFinderForm form = new PositionFinderForm();
		if(LoginService.hasRole("COMPANY")){
			form.setCompanyId(companyService.findByPrincipal().getId());
		}
		result = createEditModelAndView(form);
		return result;
	}

	@RequestMapping(value="/list", method= RequestMethod.POST, params = "list")
	public ModelAndView list(PositionFinderForm form, final BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(form);
		} else {
			try {
				form.setMoment(new Date());
				result = createEditModelAndView(form);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(form, "position.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/list", method= RequestMethod.POST, params = "cancel")
	public ModelAndView cancel(PositionFinderForm form, final BindingResult binding) {
		ModelAndView result;
			try {
				form.setMoment(null);
				form.setKeyword(null);
				result = createEditModelAndView(form);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(form, "position.commit.error");
			}
		return result;
	}
		
	
	// Show --------------------------------------------------------------------

		@RequestMapping(value = "/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int positionId) {

			ModelAndView result;

			Position position = positionService.findOne(positionId);

			result = new ModelAndView("position/show");
			result.addObject("position", position);
			result.addObject("requestURI", "position/company/show.do");

			return result;
		}
		
		// Create -----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			
			Position position = positionService.create();
			System.out.println("Create position: " + position.getTicker()); 

			result = this.createEditModelAndView(position);
			return result;
		}

		// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int positionId) {
			ModelAndView result;

			Position position = positionService.findOne(positionId);
			Company logged = companyService.findByPrincipal();
			if(position.getCompany().equals(logged) && position.getIsFinal().equals(false))
				result = this.createEditModelAndView(position);
			else
				result = new ModelAndView("error/access");
			return result;
		}

		// Save -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView edit( final Position position, final BindingResult bindingResult) {
			ModelAndView result = null;
			
			if (bindingResult.hasErrors()){
				System.out.println(bindingResult);
				result = this.createEditModelAndView(position);
			}else
				try {					
					Position pos = positionService.reconstruct(position, bindingResult);
					System.out.println(pos + " isFinal "+pos.getIsFinal());

					
					if(position.getId() != 0){// ya esta persistida
						System.out.println("Status 1: " + pos.getIsFinal());
						
						if(pos.getIsFinal() == true && position.getIsFinal() == true){
							positionService.save(pos);
							result = new ModelAndView("redirect:list.do");
							
						}else if(pos.getIsFinal() == false && position.getIsFinal() == true){
							System.out.println("trying to save with less than 2 problems and final mode");
							System.out.println(pos + " isFinal "+pos.getIsFinal());
							result = this.createEditModelAndView(position,"position.not.publish");
						}else{
							result = new ModelAndView("redirect:list.do");
						}
						
					}else{ //no persistida por tanto final mode = false
						System.out.println("saving new position");
						positionService.save(pos);
						result = new ModelAndView("redirect:list.do");
					}
					
				} catch (final ValidationException oops) {
					oops.printStackTrace();
					result = this.createEditModelAndView(position,
							"position.commit.error");
				} catch (final Throwable oops) {
					oops.printStackTrace();
					result = this.createEditModelAndView(position,
							"position.commit.error");
				}
			return result;
		}

		// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int positionId) {
			ModelAndView result;
			Company logged = companyService.findByPrincipal();
			Position position = positionService.findOne(positionId);
			if(position.getCompany().equals(logged)){
			try {
				positionService.delete(position);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(position, "position.commit.error");
			}
			}else
				result = new ModelAndView("error/access");

			return result;
		}
		
		@RequestMapping(value="/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Position position, BindingResult binding){
			
			ModelAndView res;
			
			try{
				this.positionService.delete(position);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				oops.printStackTrace();
				res = createEditModelAndView(position, "position.commit.error");
			}
			return res;
		}
		
		private ModelAndView createEditModelAndView(final Position position) {
			ModelAndView result;

			result = this.createEditModelAndView(position, null);
			return result;
		}

		private ModelAndView createEditModelAndView(final Position position,
				final String message) {
			ModelAndView result;
			Collection<Position> positions = this.positionService.findAll();
			Company company = companyService.findByPrincipal();
			
			Boolean isLogged = false;
			
			if(position.getCompany().equals(company)) isLogged = true;
				
			result = new ModelAndView("position/edit");
			result.addObject("position", position);
			result.addObject("message", message);
			result.addObject("positions", positions);
			result.addObject("isLogged", isLogged);
			return result;
		}
		
		// MODEL AND VIEW FILTER
		protected ModelAndView createEditModelAndView(PositionFinderForm positionFinderForm){
			ModelAndView res;
			res = createEditModelAndView(positionFinderForm, null);
			return res;
		}
		protected ModelAndView createEditModelAndView(PositionFinderForm positionFinderForm, String messageCode){
			ModelAndView res;
			Collection<Position> positions = new HashSet<Position>();
//			System.out.println("venimos");
			res = new ModelAndView("position/list");
			if(positionFinderForm.getMoment() == null || positionFinderForm.getKeyword() == null || positionFinderForm.getKeyword().equals("")){
//				System.out.println("a ver");
				if(positionFinderForm.getCompanyId()==null){
//					System.out.println("uwu?");
					positions.addAll(positionService.findPositionsIsFinal());
				}
				else positions.addAll(positionService.findPositionsByCompany(positionFinderForm.getCompanyId()));
			}else{
//				System.out.println("illo? " + positionFinderForm.getKeyword());
				if(positionFinderForm.getCompanyId()==null) positions.addAll(positionService.searchPositions(positionFinderForm.getKeyword()));
				else positions.addAll(positionService.searchPositionsWCompany(positionFinderForm.getKeyword(),positionFinderForm.getCompanyId()));
			}				
			res.addObject("positionFinderForm", positionFinderForm);
			res.addObject("positions", positions);
			res.addObject("requestURI", "position/company/list.do");
			res.addObject("message", messageCode);

			return res;
		}
}
