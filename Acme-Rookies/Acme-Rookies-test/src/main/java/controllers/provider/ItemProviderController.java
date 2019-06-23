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

import services.ItemService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("item/provider")
public class ItemProviderController extends AbstractController {

	// Services --------------------------------------------------------------------------------------

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ProviderService providerService;
	
	// Atributes -------------------------------------------------------------------------------------
	
	private int itemId;
	
	// Listing ---------------------------------------------------------------------------------------

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public ModelAndView list() {
		
		ModelAndView result;
		Collection<Item> items;
		
		items = itemService.findItemsByPrincipal();
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
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		
		ModelAndView result;
		Item item;
		
		this.itemId = 0;
		item = itemService.create();
		result = this.createEditModelAndView(item);
		
		return result;
	}
	
	// Show --------------------------------------------------------------------
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int itemId) {

		ModelAndView result;
		Item item;
		
		item = itemService.findOne(itemId);

		result = new ModelAndView("item/show");
		result.addObject("item", item);
		result.addObject("requestURI", "item/provider/show.do");

		return result;
	}
	
	// Edit --------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int itemId) {

		ModelAndView result;
		Item item;
		Provider logged;
		
		item = itemService.findOne(itemId);
		logged = item.getProvider();
		this.itemId = item.getId();
		
		if(providerService.findByPrincipal().equals(logged))
			result = this.createEditModelAndView(item);
		else
			result = new ModelAndView("error/access");
		
		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(Item item, BindingResult bindingResult) {
		ModelAndView result;
		item.setId(this.itemId);
		
		try {
			item = itemService.reconstruct(item,bindingResult);
			itemService.save(item);
			result = new ModelAndView("redirect:list.do");
		} catch (ValidationException oops) {
			result = this.createEditModelAndView(item);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(item,"item.commit.error");
		}
		
		return result;
	}
	
	// Delete -----------------------------------------------------------------

			@RequestMapping(value = "/delete", method = RequestMethod.GET)
			public ModelAndView delete(@RequestParam int itemId) {
				ModelAndView result;
				Item item;
				Provider logged;
				
				item = itemService.findOne(itemId);
				logged = providerService.findByPrincipal();
				
				if(item.getProvider().equals(logged)){	
					itemService.delete(item);
					result = new ModelAndView("redirect:list.do");
				}else{
					result = new ModelAndView("error/access");
				}
				
				return result;
			}
	
	//Helper methods ----------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Item item){
		ModelAndView res;
		res = createEditModelAndView(item, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Item item, String messageCode){
		
		ModelAndView res;

		res = new ModelAndView("item/edit");
		
		res.addObject("item", item);
		res.addObject("message", messageCode);
		
		return res;
	}
	
}
