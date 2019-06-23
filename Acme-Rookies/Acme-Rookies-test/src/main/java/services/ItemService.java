package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import security.LoginService;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	//Managed Repository ------------------------------------------------------------------------
	
	@Autowired
	private ItemRepository itemRepository;
	
	//Supporting Services -----------------------------------------------------------------------
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private Validator validator;

	//Simple CRUD methods -----------------------------------------------------------------------
	
	public Item create(){

		Item res; 
		Provider provider;
		
		res = new Item();
		provider = providerService.findByPrincipal();
		
		res.setPictures(new ArrayList<String>());
		res.setProvider(provider);
		
		return res;
	}
	
	public void delete(Item a){
		this.itemRepository.delete(a);
		itemRepository.flush();
	}
	
	public Item save(Item a){
		Assert.isTrue(LoginService.hasRole("PROVIDER"));

		return itemRepository.saveAndFlush(a);
	}
	
	public Collection<Item> findAll(){
		return itemRepository.findAll();
	}
	
	public Item findOne(int Id){
		return itemRepository.findOne(Id);
	}
	
	//Other business methods ----------------------------------------------------------------------------
	
	public Item reconstruct(Item item, BindingResult binding){
		Item result;
		Provider provider;
		
		if(item.getId()==0){
			provider = providerService.findByPrincipal();
			result = this.create();
			
			result.setDescription(item.getDescription());
			result.setLink(item.getLink());
			result.setName(item.getName());
			result.setPictures(item.getPictures());
			result.setProvider(provider);
			
		}else{
			result = this.findOne(item.getId());
			
			result.setDescription(item.getDescription());
			result.setLink(item.getLink());
			result.setName(item.getName());
			result.setPictures(item.getPictures());
		}

		validator.validate(item, binding);
		if(binding.hasErrors()){
			throw new ValidationException();
		}
		
		return result;
	}
	
	public Collection<Item> findItemsByPrincipal(){
		return itemRepository.getItemsByUserAccount(LoginService.getPrincipal().getId());
	}
	
	public Collection<Item> findItemsByProvider(int id){
		return itemRepository.getItemsByProvider(id);
	}
	
	public Double getAvgItemsPerProvider(){
		Double res = itemRepository.getAvgItemsPerProvider();
		if(res==null)res=0d;
		return res;
	}
	
	public Integer getMinItemsPerProvider(){
		Integer res = itemRepository.getMinItemsPerProvider();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxItemsPerProvider(){
		Integer res = itemRepository.getMaxItemsPerProvider();
		if(res==null)res=0;
		return res;
	}

	public Double getStdevItemsPerProvider(){
		Double res = itemRepository.getStdevItemsPerProvider();
		if(res==null)res=0d;
		return res;
	}
	
}
