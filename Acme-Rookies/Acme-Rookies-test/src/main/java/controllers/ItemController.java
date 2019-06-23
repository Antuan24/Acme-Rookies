package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.ProviderService;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("item/")
public class ItemController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ProviderService providerService;
	
	// Listing ---------------------------------------------------------------------------------------

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public ModelAndView list() {
		
		ModelAndView result;
		Collection<Item> items;
		
		items = itemService.findAll();
		Provider principal;
		try {
			principal = providerService.findByPrincipal();
		} catch (Exception e) {
			principal = null;
		}
		
		
		result = new ModelAndView("item/list");
		result.addObject("items",items);
		if (principal != null) {
			result.addObject("principal",principal);
		}
		
		return result;
	}
	
	@RequestMapping(value="/listId", method= RequestMethod.GET)
	public ModelAndView listId(@RequestParam int providerId) {
		
		ModelAndView result;
		Collection<Item> items;
		
		items = itemService.findItemsByProvider(providerId);
		Provider principal;
		try {
			principal = providerService.findByPrincipal();
		} catch (Exception e) {
			principal = null;
		}
		
		result = new ModelAndView("item/list");
		result.addObject("items",items);
		if (principal != null) {
			result.addObject("principal",principal);
		}
		
		return result;
	}
	
}
