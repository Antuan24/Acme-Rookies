package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Audit;
import domain.Position;

import security.LoginService;
import services.AuditService;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	@Autowired
	private AuditService auditService;
	
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
		if(LoginService.hasRole("AUDITOR")){
			result.addObject("uri","/audit/auditor/list.do");
		}else{
			String a = "/position/show.do?positionId=" + audit.getPosition().getId();
			result.addObject("uri",a);
		}
		result.addObject("position", position);
		result.addObject("requestURI", "audit/show.do");

		return result;
	}
			
}
