package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.MessageService;
import domain.Actor;
import domain.Message;
import forms.MessageForm;
import forms.StringFinderForm;

@Controller
@RequestMapping("message/")
public class MessageController extends AbstractController {
	
	//Services
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}
	
	//Listing-----------------------------------------------------------

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public ModelAndView filter() {
		ModelAndView result;
		StringFinderForm form = new StringFinderForm();
		result = createEditModelAndView(form);
		return result;
	}

	@RequestMapping(value="/list", method= RequestMethod.POST, params = "list")
	public ModelAndView list(StringFinderForm form, final BindingResult binding) {
		ModelAndView result;

		if(binding.hasErrors()){
			result = createEditModelAndView(form);
		} else {
			try {
				form.setMoment(new Date());
				result = createEditModelAndView(form);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(form, "message.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value="/list", method= RequestMethod.POST, params = "cancel")
	public ModelAndView cancel(StringFinderForm form, final BindingResult binding) {
		ModelAndView result;
			try {
				form.setMoment(null);
				form.setKeyword(null);
				result = createEditModelAndView(form);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(form, "m.commit.error");
			}
		return result;
	}
	
	//Create-----------------------------------------------------------
	@RequestMapping(value="/create" , method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;
		MessageForm form = new MessageForm();
		
		form.setIsBroadcast(false);
		res = this.createEditModelAndView(form,false);
		return res;
		
	}
	
	@RequestMapping(value="/createBroadcast" , method=RequestMethod.GET)
	public ModelAndView createBroadcast(){
		ModelAndView res;
		List <Actor> actors = new ArrayList<>(actorService.findAll());
		if(!LoginService.hasRole("ADMIN")){
			res = new ModelAndView("error/access");
		}else{
		MessageForm form = new MessageForm();
		String recipients = " ";
		
		for (int i = 0; i < actors.toArray().length; i++) {
			recipients = recipients + actors.get(i).getUserAccount().getUsername();
			if(i<actors.toArray().length-1){
				recipients = recipients + " , ";
			}
		}
		
		form.setRecipients(recipients);
		form.setIsBroadcast(true);
		form.setTags("SYSTEM");
		res = this.createEditModelAndView(form ,true);
		}
		return res;
		
	}
	
	//Save-------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(MessageForm form, BindingResult binding){
		ModelAndView res;
		
		if(binding.hasErrors()){
			System.out.println(binding);
			res = createEditModelAndView(form,form.getIsBroadcast());
		}else{
			try {
				
				Collection<Message> messages = messageService.reconstruct(form, binding);
				for (Message m : messages) {
					messageService.save(m);
				}
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable e) {
				e.printStackTrace();
				res = createEditModelAndView(form, form.getIsBroadcast(), "message.commit.error");
			}
		}
		return res;
	}
	
	
	//Delete-----------------------------------------------------------
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView delete(@RequestParam int messageId){
		ModelAndView res;
		Message message = messageService.findOne(messageId);
		Actor logged = actorService.getByUserAccount(LoginService.getPrincipal());
		
		if(!(logged.equals(message.getSender())||logged.equals(message.getRecipient()))){
			res = new ModelAndView("error/access");
		}else{
			try {
				messageService.delete(message);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable e) {
				e.printStackTrace();
				res = new ModelAndView("redirect:list.do");
			}
		}
		return res;
	}
	
	
	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(MessageForm form, boolean isBroadcast){
		ModelAndView res;
		res = createEditModelAndView(form,isBroadcast, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(MessageForm form, boolean isBroadcast, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("message/edit");
		res.addObject("messageForm", form);
		res.addObject("actors", actorService.findAll());
		res.addObject("message", messageCode);
		res.addObject("isBroadcast",isBroadcast);
		
		return res;
	}
	
	// MODELANDVIEW FINDER MESSAGE 
	protected ModelAndView createEditModelAndView(StringFinderForm messageFinderForm){
		ModelAndView res;
		res = createEditModelAndView(messageFinderForm, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(StringFinderForm messageFinderForm, String messageCode){
		ModelAndView res;
		Collection<Message> messages = new HashSet<Message>();
		Authority adminAuth = new Authority();
		adminAuth.setAuthority(Authority.ADMIN);
		
		res = new ModelAndView("message/list");
		
		Actor principal = actorService.getByUserAccount(LoginService.getPrincipal());
		if(messageFinderForm.getKeyword() == null){
			messages.addAll(messageService.findByRecipient(principal));
			
		}else if(messageFinderForm.getKeyword().equals("")){
			messages.addAll(messageService.findByRecipientAndEmptyTags(principal));
		}else{
			messages.addAll(messageService.findByRecipientAndTag(principal, messageFinderForm.getKeyword()));
		}
				
		res.addObject("messageFinderForm", messageFinderForm);
		res.addObject("messages", messages);
		res.addObject("isAdmin",actorService.getByUserAccount(LoginService.getPrincipal()).getUserAccount().getAuthorities().contains(adminAuth));
		res.addObject("notificationIsSent",configurationService.find().getNotificationIsSent());
		res.addObject("message", messageCode);

		return res;
	}
	
}