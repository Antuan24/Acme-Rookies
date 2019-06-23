package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WordRepository;
import security.LoginService;
import domain.Configuration;
import domain.Word;


@Service
@Transactional
public class WordService {

	//Managed Repository -----
	
	@Autowired
	private WordRepository wordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private ConfigurationService configurationService;
	
	//Simple CRUD methods -----
	
	public Word create(){
		Word res = new Word();
		
		return res;
	}
	
	public Collection<Word> findAll(){
		return wordRepository.findAll();
	}
	
	public Word findOne(int Id){
		return wordRepository.findOne(Id);
	}
	
	public Word save(Word a){
		
		Assert.isTrue(LoginService.hasRole("ADMIN"));
		
		Word saved = wordRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Word a){
		
		Assert.isTrue(LoginService.hasRole("ADMIN"));
		
		Configuration config = configurationService.find();
		config.getspamWords().remove(a);
		configurationService.save(config);
		wordRepository.delete(a);
	}
	
	//Other business methods -----

}