package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Position;
import domain.Provider;
import domain.Sponsorship;

import repositories.SponsorshipRepository;
import security.LoginService;

@Service
@Transactional
public class SponsorshipService {
	
	//Managed repository
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	//Supporting services
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private Validator validator;
	
	//SIMPLE CRUD METHODS
	
	public Sponsorship create(){
		Assert.isTrue(LoginService.hasRole("PROVIDER"));
		Sponsorship res = new Sponsorship();
		
		res.setProvider(providerService.findByPrincipal());

		return res;
	}
	
	public Sponsorship save(Sponsorship sponsorship) {
		Assert.isTrue(LoginService.hasRole("PROVIDER"));
		Assert.notNull(sponsorship);
		
		return sponsorshipRepository.saveAndFlush(sponsorship);
	}
	
	public void delete(Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));
		
		sponsorshipRepository.delete(sponsorship);
	}
	
	public Sponsorship findOne(int sponsorshipId) {
		return sponsorshipRepository.findOne(sponsorshipId);
	}
	
	public Collection<Sponsorship> findAll() {
		return sponsorshipRepository.findAll();
	}
	
	//OTHER BUSINESS METHODS
	
	public Collection<Sponsorship> findByProvider(Provider provider) {
		Collection<Sponsorship> sponsorships;
		
		sponsorships = this.sponsorshipRepository.findByProviderId(provider.getId());
		
		return sponsorships;
	}
	
	public Collection<Sponsorship> findByPosition(Position position) {
		Collection<Sponsorship> sponsorships;
		
		sponsorships = this.sponsorshipRepository.findByPositionId(position.getId());
		
		return sponsorships;
	}
	
	public Sponsorship reconstruct(Sponsorship sponsorship, BindingResult binding) {
		Sponsorship result;
		
		if(sponsorship.getId()==0) {
			result = sponsorship;
			result.setProvider(this.providerService.findByPrincipal());

		} else {
			result = this.sponsorshipRepository.findOne(sponsorship.getId());
			result.setBanner(sponsorship.getBanner());
			result.setTarget(sponsorship.getTarget());
			result.setPosition(sponsorship.getPosition());
		}
		validator.validate(result, binding);
		if(binding.hasErrors()) {
			throw new ValidationException();
		}
		
		return result;
		
	}
	
	public Double getAvgSponsorshipsPerProvider(){
		Double res = sponsorshipRepository.getAvgSponsorshipsPerProvider();
		if(res==null)res=0d;
		return res;
	}
	
	public Integer getMinSponsorshipsPerProvider(){
		Integer res = sponsorshipRepository.getMinSponsorshipsPerProvider();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxSponsorshipsPerProvider(){
		Integer res = sponsorshipRepository.getMaxSponsorshipsPerProvider();
		if(res==null)res=0;
		return res;
	}

	public Double getStdevSponsorshipsPerProvider(){
		Double res = sponsorshipRepository.getStdevSponsorshipsPerProvider();
		if(res==null)res=0d;
		return res;
	}
	
	
	
	public Double getAvgSponsorshipsPerPosition(){
		Double res = sponsorshipRepository.getAvgSponsorshipsPerPosition();
		if(res==null)res=0d;
		return res;
	}
	
	public Integer getMinSponsorshipsPerPosition(){
		Integer res = sponsorshipRepository.getMinSponsorshipsPerPosition();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxSponsorshipsPerPosition(){
		Integer res = sponsorshipRepository.getMaxSponsorshipsPerPosition();
		if(res==null)res=0;
		return res;
	}

	public Double getStdevSponsorshipsPerPosition(){
		Double res = sponsorshipRepository.getStdevSponsorshipsPerPosition();
		if(res==null)res=0d;
		return res;
	}
}
