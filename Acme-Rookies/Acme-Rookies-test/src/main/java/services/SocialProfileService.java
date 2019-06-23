package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.SocialProfile;

import repositories.SocialProfileRepository;
import security.LoginService;

@Service
@Transactional
public class SocialProfileService {

	@Autowired
	private SocialProfileRepository socialProfileRepository;

	@Autowired
	private ActorService actorService;

	public SocialProfile create() {
		SocialProfile result = new SocialProfile();
		return result;

	}

	public SocialProfile save(SocialProfile socialProfile) {
		SocialProfile result;

		Assert.notNull(socialProfile);
		Assert.isTrue(LoginService.hasRole("ADMIN")
				|| LoginService.hasRole("COMPANY")
				|| LoginService.hasRole("ROOKIE")
				|| LoginService.hasRole("AUDITOR")
				|| LoginService.hasRole("PROVIDER"));

		Actor actor = actorService.getByUserAccount(LoginService.getPrincipal());

		Collection<SocialProfile> socialProfiles;
		
		if (actor.getSocialProfiles().isEmpty()) {
			System.out.println("socialprofiles is empty");
			socialProfiles = new ArrayList<>();
		} else {
			System.out.println("socialprofiles isn't empty");
			socialProfiles = new ArrayList<>(actor.getSocialProfiles());
		}
		result = socialProfileRepository.save(socialProfile);
		if (socialProfile.getId()!=0) {// el objeto esta persistido
			result = socialProfileRepository.save(socialProfile);
		}else{
			result = socialProfileRepository.save(socialProfile);
			socialProfiles.add(result);
			actor.setSocialProfiles(socialProfiles);
			actorService.save(actor);
		}
		return result;

	}

	public void flush(){
		socialProfileRepository.flush();
	}
	public void delete(SocialProfile socialProfile) {
		
		Assert.notNull(socialProfile);
		Assert.isTrue(LoginService.hasRole("ADMIN")
				|| LoginService.hasRole("COMPANY")
				|| LoginService.hasRole("ROOKIE")
				|| LoginService.hasRole("AUDITOR")
				|| LoginService.hasRole("PROVIDER"));
		
		Actor actor = actorService.getByUserAccount(LoginService.getPrincipal());
		actor.getSocialProfiles().remove(socialProfile);
		actorService.save(actor);
		
		this.socialProfileRepository.delete(socialProfile);
		this.flush();
	}

	public Collection<SocialProfile> findAll() {
		return socialProfileRepository.findAll();
	}

	public SocialProfile findOne(int Id) {
		return socialProfileRepository.findOne(Id);
	}

	public Collection<SocialProfile> findByActor() {

		Collection<SocialProfile> socialProfiles;
		Actor actor = actorService
				.getByUserAccount(LoginService.getPrincipal());
		socialProfiles = socialProfileRepository.findByActor(actor.getId());

		return socialProfiles;

	}
}
