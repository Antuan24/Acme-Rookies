package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProviderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Configuration;
import domain.Curricula;
import domain.Item;
import domain.Provider;
import domain.SocialProfile;
import domain.Sponsorship;


@Service
@Transactional
public class ProviderService {

	//Managed Repository -----
	
	@Autowired
	private ProviderRepository providerRepository;
	
	//Supporting Services -----
	
	@Autowired
	private ConfigurationService configurationService;
	
	//Simple CRUD methods -----
	
	public Provider create(UserAccount ua){
		Provider res = new Provider();
		res.setItems(new ArrayList<Item>());
		res.setSponsorships(new ArrayList<Sponsorship>());
		res.setIsBanned(false);
		res.setIsSpammer(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Provider> findAll(){
		return providerRepository.findAll();
	}
	
	public Provider findOne(int Id){
		return providerRepository.findOne(Id);
	}
	
	public Provider save(Provider a){
		
		Provider saved = providerRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Provider a){
		providerRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Provider findByPrincipal(){
		return providerRepository.getProviderByUserAccountId(LoginService.getPrincipal().getId());
	}
	
	public Collection<Provider> topProvidersItems(){
		List<Provider> res = (List<Provider>) providerRepository.topProvidersItems();
		if(res.size()>5)res = res.subList(0, 4);
		return res;
	}
	
	public Collection<Provider> getProvidersWMoreSponsorshipsThanAvg(){
		return providerRepository.getProvidersWMoreSponsorshipsThanAvg();
	}
	
	public void addFareProvider(Sponsorship s){
		Provider provider = providerRepository.findOne(s.getProvider().getId());
		Configuration config = configurationService.find();
		Double debt = config.getSponsorshipFare()+ ((config.getSponsorshipFare() * config.getVatPercentage()));
		provider.setDebt(provider.getDebt() +debt);
		providerRepository.save(provider);
	}
}