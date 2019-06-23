package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.SocialProfileService;

import controllers.AbstractController;
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping("socialProfile/")
public class SocialProfileActorController extends AbstractController {

	@Autowired
	private ActorService actorService;

	@Autowired
	private SocialProfileService socialProfileService;

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		Actor actor = actorService.getByUserAccount(LoginService.getPrincipal());

		Collection<SocialProfile> socialProfiles = actor.getSocialProfiles();

		result = new ModelAndView("socialProfile/list");
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("requestURI", "socialProfile/actor/list.do");

		return result;
	}

	// Create-----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		SocialProfile socialProfile = socialProfileService.create();

		result = this.createEditModelAndView(socialProfile);

		return result;

	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		
		ModelAndView result;
		SocialProfile socialProfile = socialProfileService.findOne(socialProfileId);
		if (actorService.getByUserAccount(LoginService.getPrincipal()).getSocialProfiles().contains(socialProfile)) {
			result = new ModelAndView();
			result = this.createEditModelAndView(socialProfile);
		}else{
			result = new ModelAndView("error/access");
		}

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SocialProfile socialProfile,BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(socialProfile);
		} else
			try {

				this.socialProfileService.save(socialProfile);
				result = new ModelAndView("redirect:/actor/show.do");

			} catch (final Throwable oops) {
				for (StackTraceElement a : oops.getStackTrace()) {
					System.out.println(a);
				}
				result = this.createEditModelAndView(socialProfile,"actor.commit.error");
			}
		return result;
	}

	// Delete ------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int socialProfileId) {
		ModelAndView res;

		SocialProfile socialProfile = socialProfileService.findOne(socialProfileId);
		if (actorService.getByUserAccount(LoginService.getPrincipal()).getSocialProfiles().contains(socialProfile)) {
			System.out.println("Social profile: " + socialProfile.getNick() +socialProfile.getLink());

			try {
				this.socialProfileService.delete(socialProfile);
				res = new ModelAndView("redirect:/actor/show.do");
			} catch (Throwable oops) {
				oops.printStackTrace();
				res = createEditModelAndView(socialProfile, "actor.commit.error");
			}
		}else{
			res = new ModelAndView("error/access");
		}

		return res;
	}

	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialProfile socialProfile,final BindingResult binding) {
		ModelAndView result;

		try {
			this.socialProfileService.delete(socialProfile);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialProfile,"actor.commit.error");
		}

		return result;
	}

	// Cancel -----------------------------------------------------------------

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int socialProfileId) {
		ModelAndView result;

		SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);

		try {
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialProfile,"actor.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(SocialProfile socialProfile) {
		ModelAndView result;

		result = this.createEditModelAndView(socialProfile, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String message) {

		ModelAndView result;

		result = new ModelAndView("socialProfile/edit");

		result.addObject("socialProfile", socialProfile);
		result.addObject("message", message);

		return result;
	}
}
