package controllers.auditor;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.QuoletService;
import controllers.AbstractController;
import domain.Quolet;

@Controller
@RequestMapping("quolet/auditor/")
public class QuoletAuditorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private QuoletService quoletService;
	
	// Attributes -------------------------------------------------------------
	
	private int quoletId;
	private int auditId;
	
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Calendar date;
		Date current1, current2;
		
		date = Calendar.getInstance();
		date.setTime(new Date());
		current1 = date.getTime();
		
		date.set(Calendar.MONTH, date.get(Calendar.MONTH)-1);
		current2 = date.getTime();
		
		Collection<Quolet> quolets = quoletService.findQuoletByPrincipal();
		result = new ModelAndView("quolet/list");
		result.addObject("quolets",quolets);
		result.addObject("current1",current1);
		result.addObject("current2",current2);
		
		return result;
	}
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int auditId) {
		ModelAndView result;
		Quolet quolet;
		
		this.auditId=auditId;
		this.quoletId=0;
		
		quolet = quoletService.create(auditId);		
		result = this.createEditModelAndView(quolet);
		
		return result;
	}
	
	// Show --------------------------------------------------------------------
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int quoletId) {

		ModelAndView result;
		Quolet quolet;
		
		quolet = quoletService.findOne(quoletId);
		
		result = new ModelAndView("quolet/show");
		result.addObject("quolet", quolet);
		result.addObject("requestURI", "quolet/auditor/show.do");

		return result;
	}
	
	// Edit --------------------------------------------------------------------
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int quoletId) {

			ModelAndView result;
			Quolet quolet;
			
			quolet = quoletService.findOne(quoletId);
			
			this.auditId=quolet.getAudit().getId();
			this.quoletId=quoletId;
			
			if(quolet.getAudit().getAuditor().equals(auditorService.findByPrincipal()) && quolet.getIsFinal().equals(false))
				result = this.createEditModelAndView(quolet);
			else
				result = new ModelAndView("error/access");
			
			return result;
		}

	// Save -----------------------------------------------------------------

		@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
		public ModelAndView edit(Quolet quolet, BindingResult bindingResult) {
			ModelAndView result;
			Quolet saved;
			
			try {
				saved = quoletService.reconstruct(quolet, quoletId, auditId, bindingResult);
				quoletService.save(saved);
				result = new ModelAndView("redirect:list.do");
			} catch (ValidationException oops) {
				result = this.createEditModelAndView(quolet);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(quolet,"quolet.commit.error");
			}
			
			return result;
	}
	
	// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int quoletId) {
			ModelAndView result;
			Quolet quolet;
			
			quolet = quoletService.findOne(quoletId);
			
			if(quoletService.findQuoletByPrincipal().contains(quolet) && quolet.getIsFinal().equals(false)){
				quoletService.delete(quolet);
				result = new ModelAndView("redirect:list.do?auditId="+quolet.getAudit().getId());
			}else{
				result = new ModelAndView("error/access");
			}
			
			return result;
		}
	
	//Helper methods --------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Quolet quolet){
		ModelAndView res;
		res = createEditModelAndView(quolet, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Quolet quolet, String messageCode){
		
		ModelAndView res;
		
		res = new ModelAndView("quolet/edit");
		res.addObject("quolet", quolet);
		res.addObject("message", messageCode);

		return res;
	}
	
}
