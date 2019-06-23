package controllers.rookie;

import controllers.AbstractController;
import domain.Finder;
import domain.Position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import java.util.Collection;
import java.util.HashSet;

@Controller
@RequestMapping("finder/rookie/")
public class FinderRookieController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FinderService finderService;

	@Autowired
	private PositionService positionService;

	// filter: change filter parameters and lists pparades -------------------------------------

	@RequestMapping(value="/filter", method= RequestMethod.GET)
	public ModelAndView filter() {
		ModelAndView result;
		result = createEditModelAndView(finderService.findByPrincipal());
		System.out.println("Finder Results:" + finderService.findByPrincipal().getPositions());
		return result;
	}

	@RequestMapping(value="/filter", method= RequestMethod.POST, params = "filter")
	public ModelAndView filter( Finder finder, final BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(finder);
		} else {
			try {
				Finder updatedFinder = finderService.save(finder);
				result = createEditModelAndView(updatedFinder);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(finder,
						"finder.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/filter", method= RequestMethod.POST, params = "clear")
	public ModelAndView clear( Finder finder, final BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(finder);
		} else {
			try {
				Finder clearedFinder = finderService.clear(finder);
				result = createEditModelAndView(clearedFinder);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(finder,
						"finder.commit.error");
			}
		}
		return result;
	}

	//Helper methods---------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Finder finder){
		ModelAndView res;
		res = createEditModelAndView(finder, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(Finder finder, String messageCode){
		ModelAndView res;
		Collection<Position> positions = new HashSet<Position>();
		String cachedMessageCode = null;

		res = new ModelAndView("finder/edit");

		if(finderService.findOne(finder.getId()).getMoment() == null
				|| finderService.isVoid(finder)
				|| finderService.isExpired(finder)){
			positions.addAll(positionService.findAll());
		}else{
			positions.addAll(finderService.findOne(finder.getId()).getPositions());
			cachedMessageCode = "finder.cachedMessage";
		}
		res.addObject("cachedMessage", cachedMessageCode);
		res.addObject("finder",finder);
		res.addObject("positions", positions);
		res.addObject("message", messageCode);

		return res;
	}
}
