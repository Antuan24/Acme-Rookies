package controllers.provider;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import services.ProviderService;
import services.SponsorshipService;

import controllers.AbstractController;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;

@Controller
@RequestMapping("sponsorship/provider/")
public class SponsorshipProviderController extends AbstractController {

	@Autowired
	private SponsorshipService sponsorshipService;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private PositionService positionService;

	// Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);

		result = new ModelAndView("sponsorship/show");
		result.addObject("sponsorship", sponsorship);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		Provider provider;

		provider = this.providerService.findByPrincipal();
		sponsorships = this.sponsorshipService.findByProvider(provider);

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("isProvider", provider);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();

		result = this.createEditModelAndView(sponsorship);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;
		Provider provider;

		provider = this.providerService.findByPrincipal();
		sponsorship = this.sponsorshipService.findOne(sponsorshipId);

		if (sponsorship.getProvider().equals(provider)) {
			result = this.createEditModelAndView(sponsorship);
		} else {
			result = new ModelAndView("error/access");
		}
		return result;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Sponsorship sponsorship, BindingResult binding) {
		ModelAndView result;

		try {
			sponsorship = this.sponsorshipService.reconstruct(sponsorship,
					binding);
			this.sponsorshipService.save(sponsorship);

			result = new ModelAndView("redirect:list.do");

		} catch (ValidationException oops) {
			result = this.createEditModelAndView(sponsorship);

		} catch (Throwable oops) {
			result = this.createEditModelAndView(sponsorship,
					"sponsorship.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sponsorshipId) {
		ModelAndView res;
		Sponsorship sponsorship;
		Provider provider;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		provider = this.providerService.findByPrincipal();

		if (sponsorship.getProvider().equals(provider)) {
			try {
				this.sponsorshipService.delete(sponsorship);
				res = new ModelAndView("redirect:list.do");
				
			} catch (Throwable oops) {
				res = createEditModelAndView(sponsorship, "sponsorship.commit.error");
				
			}
		} else {
			res = new ModelAndView("error/access");
		}
		return res;

	}
	
	//Ancillary methods
	
	protected ModelAndView createEditModelAndView(Sponsorship sponsorship){
		ModelAndView result;
		result = this.createEditModelAndView(sponsorship,null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Sponsorship sponsorship, String messageCode){
		ModelAndView result;
		Collection<Position> positions = this.positionService.findAll();

		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("positions",positions);
		result.addObject("message", messageCode);

		return result;
	}

}
