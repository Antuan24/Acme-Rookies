package controllers.auditor;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.AuditService;
import services.PositionService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Position;
import domain.Audit;

@Controller
@RequestMapping("audit/auditor/")
public class AuditAuditorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService auditService;

	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private PositionService positionService;
	
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Audit> audits = auditService.findAuditsByPrincipal();
		result = new ModelAndView("audit/list");
		result.addObject("audits",audits);
		
		return result;
	}
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Audit audit;
		
		audit = auditService.create();		
		
		result = this.createEditModelAndView(audit);
		
		return result;
	}
	
	// Show --------------------------------------------------------------------
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int auditId) {

		ModelAndView result;
		Audit audit;
		Position position;
		
		audit = auditService.findOne(auditId);
		position = audit.getPosition();
		
		result = new ModelAndView("audit/show");
		result.addObject("audit", audit);
		result.addObject("position", position);
		result.addObject("requestURI", "audit/auditor/show.do");

		return result;
	}
	
	// Edit --------------------------------------------------------------------
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int auditId) {

			ModelAndView result;
			Audit audit;
			
			audit = auditService.findOne(auditId);	
			System.out.println("position edit " + audit.getPosition());
			if(audit.getAuditor().equals(auditorService.findByPrincipal()) && audit.getIsFinal().equals(false))
				result = this.createEditModelAndView(audit);
			else
				result = this.list();
			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(Audit audit, BindingResult bindingResult) {
		ModelAndView result;
			
		try {
			if(audit.getId()!=0){
				Audit db = auditService.findOne(audit.getId());
				if(db.getIsFinal()){
					System.out.println("entra aqui");
					bindingResult.rejectValue("position", "audit.cant.update");
				}
			}
			System.out.println(audit.getId());
			Audit saved;
			Position position;
			audit = auditService.reconstruct(audit, bindingResult);
			saved = auditService.save(audit);
			position = saved.getPosition();
			position.getAudits().add(saved);
			positionService.trueSave(position);
			result = new ModelAndView("redirect:list.do");
		} catch (ValidationException oops) {
			result = this.createEditModelAndView(audit);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audit,"audit.commit.error");
		}
		
		return result;
	}
	
	// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int auditId) {
			ModelAndView result;
			Audit audit;
			Auditor logged;
			
			audit = auditService.findOne(auditId);
			logged = auditorService.findByPrincipal();
			
			if(auditService.findAuditsByAuditor(logged.getId()).contains(audit)){
				Position ps = audit.getPosition();
				ps.getAudits().remove(audit);
				positionService.trueSave(ps);
				auditService.delete(audit);
				result = new ModelAndView("redirect:list.do");
			}else{
				result = new ModelAndView("error/access");
			}
			
			return result;
		}
	
	//Helper methods --------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Audit audit){
		ModelAndView res;
		res = createEditModelAndView(audit, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Audit audit, String messageCode){
		
		ModelAndView res;
		
		Collection<Position> positions = positionService.findAll();
		Collection<Audit> audits = auditService.findAuditsByPrincipal();
		for(Audit a: audits){
			if(a.getPosition() != audit.getPosition()){
				positions.remove(a.getPosition());
			}
		}

		res = new ModelAndView("audit/edit");
		res.addObject("audit", audit);
		res.addObject("positions",positions);
		res.addObject("message", messageCode);

		return res;
	}
	
}
