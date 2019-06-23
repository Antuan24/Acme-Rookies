package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.AuditService;
import services.CompanyService;
import services.CurriculaService;
import services.FinderService;
import services.ItemService;
import services.ProviderService;
import services.RookieService;
import services.PositionService;
import services.SponsorshipService;

import controllers.AbstractController;


@Controller
@RequestMapping("/admin/")
public class AdminDashboardController extends AbstractController {
	
	
	@Autowired
	PositionService positionService;
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	RookieService rookieService;
	
	@Autowired
	FinderService finderService;
	
	@Autowired
	CurriculaService curriculaService;
	
	@Autowired
	AuditService auditService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	ProviderService providerService;
	
	@Autowired
	SponsorshipService sponsorshipService;
	
	//DASHBOARD--------------------------------------------------------
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView res;
	
		res = new ModelAndView("admin/dashboard");

		res.addObject("avgPositionsPerCompany", Math.round(positionService.getAvgPositionsPerCompany()*100.0d)/100.0d);
		res.addObject("minPositionsPerCompany", positionService.getMinPositionsPerCompany());
		res.addObject("maxPositionsPerCompany", positionService.getMaxPositionsPerCompany());
		res.addObject("stdevPositionsPerCompany", Math.round(positionService.getStdevPositionsPerCompany()*100.0d)/100.0d);

		res.addObject("avgApplicationsPerRookie", Math.round(applicationService.getAvgApplicationsPerRookie()*100.0d)/100.0d);
		res.addObject("minApplicationsPerRookie", applicationService.getMinApplicationsPerRookie());
		res.addObject("maxApplicationsPerRookie",applicationService.getMaxApplicationsPerRookie());
		res.addObject("stdevApplicationsPerRookie", Math.round(applicationService.getStdevApplicationsPerRookie()*100.0d)/100.0d);
		
		res.addObject("avgSalaryPerPosition", Math.round(positionService.getAvgSalaryPerPosition()*100.0d)/100.0d);
		res.addObject("minSalaryPerPosition", positionService.getMinSalaryPerPosition());
		res.addObject("maxSalaryPerPosition", positionService.getMaxSalaryPerPosition());
		res.addObject("stdevSalaryPerPosition", Math.round(positionService.getStdevSalaryPerPosition()*100.0d)/100.0d);
		
		res.addObject("avgCurriculasPerRookie", Math.round(curriculaService.getAvgCurriculasPerRookie()*100.0d)/100.0d);
		res.addObject("minCurriculasPerRookie", curriculaService.getMinCurriculasPerRookie());
		res.addObject("maxCurriculasPerRookie", curriculaService.getMaxCurriculasPerRookie());
		res.addObject("stdevCurriculasPerRookie", Math.round(curriculaService.getStdevCurriculasPerRookie()*100.0d)/100.0d);
		
		res.addObject("avgResultsPerFinder", Math.round(finderService.getAvgResultsPerFinder()*100.0d/100.0d));
		res.addObject("minResultsPerFinder", finderService.getMinResultsPerFinder());
		res.addObject("maxResultsPerFinder", finderService.getMaxResultsPerFinder());
		res.addObject("stdevResultsPerFinder", Math.round(finderService.getStdevResultsPerFinder()*100.0d/100.0d));

		res.addObject("avgAuditScorePerPosition", Math.round(auditService.getAvgAuditScorePerPosition()*100.0d)/100.0d);
		res.addObject("minAuditScorePerPosition", auditService.getMinAuditScorePerPosition());
		res.addObject("maxAuditScorePerPosition", auditService.getMaxAuditScorePerPosition());
		res.addObject("stdevAuditScorePerPosition", Math.round(auditService.getStdevAuditScorePerPosition()*100.0d)/100.0d);
		
		res.addObject("avgAuditScorePerCompany", Math.round(auditService.getAvgAuditScorePerCompany()*100.0d)/100.0d);
		res.addObject("minAuditScorePerCompany", Math.round(auditService.getMinAuditScorePerCompany()*100.0d)/100.0d);
		res.addObject("maxAuditScorePerCompany", Math.round(auditService.getMaxAuditScorePerCompany()*100.0d)/100.0d);
		res.addObject("stdevAuditScorePerCompany", Math.round(auditService.getStdevAuditScorePerCompany()*100.0d)/100.0d);
		
		res.addObject("avgItemsPerProvider", Math.round(itemService.getAvgItemsPerProvider()*100.0d)/100.0d);
		res.addObject("minItemsPerProvider", itemService.getMinItemsPerProvider());
		res.addObject("maxItemsPerProvider", itemService.getMaxItemsPerProvider());
		res.addObject("stdevItemsPerProvider", Math.round(itemService.getStdevItemsPerProvider()*100.0d)/100.0d);
		
		res.addObject("avgSponsorshipsPerProvider", Math.round(sponsorshipService.getAvgSponsorshipsPerProvider()*100.0d)/100.0d);
		res.addObject("minSponsorshipsPerProvider", sponsorshipService.getMinSponsorshipsPerProvider());
		res.addObject("maxSponsorshipsPerProvider", sponsorshipService.getMaxSponsorshipsPerProvider());
		res.addObject("stdevSponsorshipsPerProvider", Math.round(sponsorshipService.getStdevSponsorshipsPerProvider()*100.0d)/100.0d);

		res.addObject("avgSponsorshipsPerPosition", Math.round(sponsorshipService.getAvgSponsorshipsPerPosition()*100.0d)/100.0d);
		res.addObject("minSponsorshipsPerPosition", sponsorshipService.getMinSponsorshipsPerPosition());
		res.addObject("maxSponsorshipsPerPosition", sponsorshipService.getMaxSponsorshipsPerPosition());
		res.addObject("stdevSponsorshipsPerPosition", Math.round(sponsorshipService.getStdevSponsorshipsPerPosition()*100.0d)/100.0d);
		
		res.addObject("maxPositionsCompanies", companyService.getMaxPositionsCompanies());
		res.addObject("maxApplicationsRookies", rookieService.getMaxApplicationsRookies());
		
		res.addObject("bestSalaryPosition", positionService.getBestSalaryPosition());
		res.addObject("worstSalaryPosition", positionService.getWorstSalaryPosition());
		res.addObject("maxAuditScoreCompanies", companyService.getMaxAuditScoreCompanies());
		res.addObject("topProvidersItems", providerService.topProvidersItems());
		res.addObject("providersWMoreSponsorshipsThanAvg", providerService.getProvidersWMoreSponsorshipsThanAvg());
		
		res.addObject("ratioEmptyFinders", finderService.getRatioEmptyFinders());
		res.addObject("avgSalaryPositionsHighestScore", positionService.getAvgSalaryPositionsHighestScore());


		return res;
	}
	
	// SCORE
	
	//DASHBOARD--------------------------------------------------------
//	@RequestMapping(value="/score", method=RequestMethod.GET)
//	public ModelAndView score(){
//		ModelAndView res;
//		
//		res = new ModelAndView("admin/score");
////		res.addObject("customersScore", customerEndorsementService.getScoreCustomerEndorsement());
////		res.addObject("handyworkersScore", handyWorkerEndorsementService.getScoreHandyWorkerEndorsement());
//		return res;
//	}
	
	
	
	//Helper methods---------------------------------------------------

	
}