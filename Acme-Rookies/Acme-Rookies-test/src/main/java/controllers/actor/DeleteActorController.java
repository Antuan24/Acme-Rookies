package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.ApplicationService;
import services.AuditService;
import services.AuditorService;
import services.CompanyService;
import services.CreditCardService;
import services.CurriculaService;
import services.FinderService;
import services.ItemService;
import services.PositionService;
import services.ProblemService;
import services.ProviderService;
import services.RookieService;
import services.SocialProfileService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Application;
import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.CreditCard;
import domain.Curricula;
import domain.EducationData;
import domain.Finder;
import domain.Item;
import domain.MiscellaneousData;
import domain.Position;
import domain.PositionData;
import domain.Problem;
import domain.Provider;
import domain.Rookie;
import domain.SocialProfile;
import domain.Sponsorship;

@Controller
@RequestMapping("actor/")
public class DeleteActorController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private AuditorService auditorService;
		
	@Autowired
	private FinderService finderService;

	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private SocialProfileService socialProfileService;

	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private CurriculaService curriculaService;
	
//DELETE ALL DATA -----------------------------------------------------------------
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteActor(){
		ModelAndView res;
		Collection <Authority> auths = LoginService.getPrincipal().getAuthorities();
		Authority rookieAuth = new Authority();
		Authority companyAuth = new Authority();
		Authority auditorAuth = new Authority();
		Authority providerAuth = new Authority();
		
		rookieAuth.setAuthority(Authority.ROOKIE);
		companyAuth.setAuthority(Authority.COMPANY);
		auditorAuth.setAuthority(Authority.AUDITOR);
		providerAuth.setAuthority(Authority.PROVIDER);

		if (auths.contains(rookieAuth)) {
			res = this.deleteRookie();
		}else if(auths.contains(companyAuth)){
			res = this.deleteCompany();
		}else if(auths.contains(auditorAuth)){
			res = this.deleteAuditor();
		}else if(auths.contains(providerAuth)){
			res = this.deleteProvider();
		}else{
			res = new ModelAndView("error/access");
		}
		
		return res;
	}
	
	//Company -------------------------------------------------------------------
		@RequestMapping(value = "/deleteCompanyData", method = RequestMethod.GET)
		public ModelAndView deleteCompany() {
			
			ModelAndView res = new ModelAndView("redirect:/j_spring_security_logout");

			Company c = companyService.findByPrincipal();
			for (Application a : applicationService.findAppByPrincipalCompany()) {
//				a.setProblem(null);
//				a.setPosition(null);
				a.setCurricula(null);
				Application saved = applicationService.trueSave(a);
				applicationService.delete(saved);
				System.out.println("deleted "+ saved );
			}
			for (Problem p : problemService.findProblemByCompany(c.getId())) {
				problemService.delete(p);
				System.out.println("deleted" + p);
			}
			if (c.getSocialProfiles().size() != 0) {
				SocialProfile[] socials = new SocialProfile[c.getSocialProfiles().size()];
				Integer cont = 0;
				for (SocialProfile sp : c.getSocialProfiles()) {
					socials[cont] = sp;
					cont++;
				}
				
				for (int i = 0; i < socials.length; i++) {
					socialProfileService.delete(socials[i]);
					System.out.println("deleted "+ socials[i]);
				}
			}

			for (Position p : c.getPositions()) {
				for (Problem p1: problemService.findProblemByPosition(p)) {
						problemService.delete(p1);
						System.out.println("deleted "+ p1);
				}
				positionService.trueDelete(p);
			}
			positionService.flush();

			CreditCard credit = c.getCreditCard();
			System.out.println(credit);
			
			c.setCreditCard(null);
			companyService.flush();
	//		Company saved = companyService.save(c);
			if(credit != null){
				creditCardService.delete(credit);
				System.out.println("deleted "+ credit);
			}


			UserAccount ua = c.getUserAccount();	
			
			companyService.delete(c);
			userAccountService.delete(ua);

			return res;
		}
		
		//Rookie -------------------------------------------------------------------
				@RequestMapping(value = "/deleteRookieData", method = RequestMethod.GET)
				public ModelAndView deleteRookie() {
					
					ModelAndView res = new ModelAndView("redirect:/j_spring_security_logout");

					Rookie h = rookieService.findByPrincipal();
					
					
					h.getCreditCard();
					h.getUserAccount();
					
					if (h.getSocialProfiles().size() != 0) {
						SocialProfile[] socials = new SocialProfile[h.getSocialProfiles().size()];
						Integer cont = 0;
						for (SocialProfile sp : h.getSocialProfiles()) {
							socials[cont] = sp;
							cont++;
						}
						
						for (int i = 0; i < socials.length; i++) {
							socialProfileService.delete(socials[i]);
							System.out.println("deleted "+ socials[i]);
						}
					}

					Collection<Application> apps = h.getApplications();
					for (Application a : apps) {
						a.setProblem(null);
						a.setPosition(null);
						a.setCurricula(null);
						//Application saved = applicationService.trueSave(a);
						applicationService.delete(a);
						System.out.println("deleted "+ a );
					}


					for (Curricula c : h.getCurriculas()) {
						c.setEducationDatas(new ArrayList<EducationData>());
						c.setMiscellaneousDatas(new ArrayList<MiscellaneousData>());
						c.setPositionDatas(new ArrayList<PositionData>());
						c.setPersonalData(null);
						c.setRookie(null);
						
						//Curricula saved = curriculaService.save(c);
						
						curriculaService.delete(c);
						
						System.out.println("deleted " + c);
					}
					
					CreditCard credit = h.getCreditCard();
					System.out.println(credit);
					
					h.setCreditCard(null);
			//		Rookie saved = rookieService.save(h);
					if(credit != null){
						creditCardService.delete(credit);
						System.out.println("deleted "+ credit);
					}

					UserAccount ua = h.getUserAccount();	
					Finder f = finderService.findByRookie(h);
					finderService.delete(f);
					System.out.println("deleted "+ f);
				
					rookieService.delete(h);
					userAccountService.delete(ua);

					return res;
				}
				
				//Auditor -------------------------------------------------------------------
				@RequestMapping(value = "/deleteAuditorData", method = RequestMethod.GET)
				public ModelAndView deleteAuditor() {
					
					ModelAndView res = new ModelAndView("redirect:/j_spring_security_logout");

					Auditor a = auditorService.findByPrincipal();					
					
					if (a.getSocialProfiles().size() != 0) {
						SocialProfile[] socials = new SocialProfile[a.getSocialProfiles().size()];
						Integer cont = 0;
						for (SocialProfile sp : a.getSocialProfiles()) {
							socials[cont] = sp;
							cont++;
						}
						
						for (int i = 0; i < socials.length; i++) {
							socialProfileService.delete(socials[i]);
							System.out.println("deleted "+ socials[i]);
						}
					}
					
					for (Audit audit : auditService.findAuditsByAuditor(a.getId())) {
						auditService.delete(audit);
						System.out.println("deleted "+ audit);
					}
					
					CreditCard credit = a.getCreditCard();
					System.out.println(credit);
					
					a.setCreditCard(null);
			//		Rookie saved = rookieService.save(h);
					if(credit != null){
						creditCardService.delete(credit);
						System.out.println("deleted "+ credit);
					}

					UserAccount ua = a.getUserAccount();	
					
				
					auditorService.delete(a);
					userAccountService.delete(ua);

					return res;
				}
				
				//Provider -------------------------------------------------------------------
				@RequestMapping(value = "/deleteProviderData", method = RequestMethod.GET)
				public ModelAndView deleteProvider() {
					
					ModelAndView res = new ModelAndView("redirect:/j_spring_security_logout");

					Provider p = providerService.findByPrincipal();					
					
					if (p.getSocialProfiles().size() != 0) {
						SocialProfile[] socials = new SocialProfile[p.getSocialProfiles().size()];
						Integer cont = 0;
						for (SocialProfile sp : p.getSocialProfiles()) {
							socials[cont] = sp;
							cont++;
						}
						
						for (int i = 0; i < socials.length; i++) {
							socialProfileService.delete(socials[i]);
							System.out.println("deleted "+ socials[i]);
						}
					}
					
					for (Item item : itemService.findItemsByPrincipal()) {
						itemService.delete(item);
						System.out.println("deleted "+ item);
					}
					
					
					for (Sponsorship s : sponsorshipService.findByProvider(p)) {
						sponsorshipService.delete(s);
						System.out.println("deleted "+ s);
					}
					
					
					CreditCard credit = p.getCreditCard();
					System.out.println(credit);
					
					p.setCreditCard(null);
					Provider saved = providerService.save(p);
					if(credit != null){
						creditCardService.delete(credit);
						System.out.println("deleted "+ credit);
					}

					UserAccount ua = p.getUserAccount();	
				
					providerService.delete(p);
					userAccountService.delete(ua);

					return res;
				}

}