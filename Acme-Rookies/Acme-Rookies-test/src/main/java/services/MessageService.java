package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.LoginService;
import domain.Actor;
import domain.Message;
import domain.Word;
import forms.MessageForm;



@Service
@Transactional
public class MessageService {

	//Managed Repository ------------------------------------------------------------------------
	
	@Autowired
	private MessageRepository messageRepository;
	
	//Supporting Services -----------------------------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private Validator validator;

	//Simple CRUD methods -----------------------------------------------------------------------
	
	public Message create(Actor actor){

		Message res = new Message();
		
		res.setSender(actor);
		res.setFlagSpam(false);
		res.setTags(new ArrayList<String>());
		
		return res;
	}
	
	public Collection<Message> findAll(){
		return messageRepository.findAll();
	}
	
	public Message findOne(int Id){
		return messageRepository.findOne(Id);
	}
	
	public Message save(Message a){
		return messageRepository.saveAndFlush(a);
	}
	
	public void delete(Message a){
		if (a.getTags().contains("DELETED")) {
			messageRepository.delete(a);
		}else{
			a.getTags().add("DELETED");
			this.save(a);
		}
	}
	
	public Collection<Message> reconstruct(MessageForm form, BindingResult binding){
		
		Collection<Message> res = new ArrayList<>();
		
		List<Actor> recipients =  new ArrayList<>();
		String[] recipientsArray = form.getRecipients().split(",");
		for (int i = 0; i < recipientsArray.length; i++) {
			recipients.add(actorService.findByUsername(recipientsArray[i].trim()));
		}
		
		List<String> tags = new ArrayList<>();
		String[] tagsArray = form.getTags().split(",");
		for (int i = 0; i < tagsArray.length; i++) {
			tags.add(tagsArray[i].trim());
		}

		for (Actor a : recipients) {
			
		Message aux = this.create(actorService.getByUserAccount(LoginService.getPrincipal()));
		aux.setBody(form.getBody());
		aux.setSubject(form.getSubject());
		aux.setMoment(new Date(System.currentTimeMillis() - 1000));
	
		aux.setTags(tags);
		aux.setRecipient(a);
		
		res.add(aux);
		}
		
		if(binding != null){
			validator.validate(form,binding);
		}
		
		
		return res;
	}
	
	//Other business methods ----------------------------------------------------------------------------
	
	public Collection<Message> findBySender(Actor sender){
		return messageRepository.findBySender(sender);
	}
	
	public Collection<Message> findByRecipient(Actor recipient){
		return messageRepository.findByRecipient(recipient);
	}

	public Collection<Message> findBySenderAndTag(Actor sender, String tag){
		return messageRepository.findBySenderAndTag(sender, tag);
	}
	public Collection<Message> findByRecipientAndTag(Actor recipient, String tag){
		return messageRepository.findByRecipientAndTag(recipient, tag);
	}
	
	public Collection<Message> findBySenderAndEmptyTags(Actor sender){
		return messageRepository.findBySenderAndEmptyTags(sender);
	}
	
	public Collection<Message> findByRecipientAndEmptyTags(Actor recipient){
		return messageRepository.findByRecipientAndEmptyTags(recipient);
	}
	public Boolean hasSpam(List<Word> spamWords, Message m) {
		Boolean res = false;
		
		for (Word w : spamWords) {
			if(m.getBody().toLowerCase().contains(w.getEnglishName().toLowerCase())||
				m.getBody().toLowerCase().contains(w.getSpanishName().toLowerCase())||
				m.getSubject().toLowerCase().contains(w.getEnglishName().toLowerCase())||
				m.getSubject().toLowerCase().contains(w.getSpanishName().toLowerCase())){
				res = true;
				break;
			}
		}
		return res;
	}
	
	
}
