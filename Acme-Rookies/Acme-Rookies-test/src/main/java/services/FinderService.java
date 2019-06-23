package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import domain.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	//Managed Repository -----
	@Autowired
	private FinderRepository finderRepository;
	
	//Supporting Services -----
	
	@Autowired
	private RookieService		rookieService;

	@Autowired
	private ConfigurationService configurationService;

	
	//Simple CRUD methods -----
	public Finder create(){
		Finder result;
		result = new Finder();
		result.setPositions(new HashSet<Position>());
		return result;
	}

	
	public Collection<Finder> findAll(){
		Collection<Finder> result;
		result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	
	public Finder findOne(int finderId){
		Finder finder;
		finder = this.finderRepository.findOne(finderId);
		return finder;
	}

	public Finder findByPrincipal(){
		Finder result;
		Rookie principal= rookieService.findByPrincipal();
		result = findByRookie(principal);
		/*In case is expired, we set all its parameters to null*/
		if(result.getMoment() == null || isVoid(result) || isExpired(result)){
			result = setAllToParametersToNullAndSave(result);
		}
		return result;
	}
	
	
	public Finder save(Finder finder){
		Finder result;
		Assert.notNull(finder);
		if (finder.getId() != 0) {
			Assert.isTrue(this.esDeActorActual(finder));
			if(isVoid(finder)){
				finder.setMoment(null);
				finder.setPositions(new HashSet<Position>());
			}else{
				finder.setMoment(DateUtils.addMilliseconds(new Date(),-1));
				filterParades(finder);
			}

		}else{
			Assert.isNull(findByRookie(finder.getRookie()));

			Assert.isTrue(isVoid(finder));

			Assert.isNull(finder.getMoment());
		}
		result = this.finderRepository.save(finder);
		return result;
	}
	
	public Finder clear(Finder finder){
		Assert.notNull(finder);
		Assert.isTrue(this.esDeActorActual(finder));
		finder.setMoment(null);
		return this.setAllToParametersToNullAndSave(finder);
	}

	public void delete(Finder finder){
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		this.finderRepository.delete(finder);
	}

	public Finder findByRookie(Rookie rookie){
		Finder result;

		result = finderRepository.findByRookie(rookie.getId());

		return result;
	}
	
	//Other business methods -----
	
	private Boolean esDeActorActual(final Finder finder) {
		Boolean result;

		final Rookie principal = this.rookieService.findByPrincipal();
		final Rookie memberFromFinder = finderRepository.findOne(finder.getId()).getRookie();

		result = principal.equals(memberFromFinder);
		return result;
	}

	public Boolean isVoid(final Finder finder){
		Boolean result;

		result = (finder.getKeyword() == null || finder.getKeyword() == "")
				&& finder.getMaxSalary() == null
				&& finder.getMaxDeadline() == null
				&& finder.getMinSalary() == null;

		return result;
	}

	private Finder setAllToParametersToNullAndSave(Finder finder){
		Assert.isTrue(finder.getMoment() == null || isExpired(finder));
		Finder result;

		finder.setMoment(null);
		finder.setKeyword(null);
		finder.setMaxSalary(null);
		finder.setMaxDeadline(null);

		finder.setMinSalary(null);
		finder.setPositions(null);

		result = save(finder);
		return result;
	}

	public Boolean isExpired(Finder finder){
		Boolean result = true;
		Configuration configuration = configurationService.find();
		Double cacheTimeInHours = configuration.getFinderCacheTime();
		Date expirationMoment =  new Date();
			/*Adding Hours*/
			expirationMoment= DateUtils.addHours(expirationMoment, cacheTimeInHours.intValue());
			/*Adding Hours*/
			expirationMoment = DateUtils.addMinutes(expirationMoment,
				Double.valueOf(60 * (cacheTimeInHours - cacheTimeInHours.intValue())).intValue());

			result = finder.getMoment().after(expirationMoment);

		return result;
	}

	/*Don't declare finder parameter as final*/
	public Finder filterParades(Finder finder){
		String keyword;
		Date maxDeadline;
		Double minSalary;
		Double maxSalary;

		Collection<Position> positions;

		keyword = finder.getKeyword();

		maxSalary = finder.getMaxSalary();
		maxDeadline = finder.getMaxDeadline();
		minSalary = finder.getMinSalary();

		positions = finderRepository.filterPositions(keyword, maxSalary, maxDeadline, minSalary);
		finder.setPositions(positions);
		return finder;
	}
	
	public Double getAvgResultsPerFinder(){
		Double res = finderRepository.getAvgResultsPerFinder();
		if(res==null)res=0d;
		return res;
	}

	public Integer getMinResultsPerFinder(){
		Integer res = finderRepository.getMinResultsPerFinder();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxResultsPerFinder(){
		Integer res = finderRepository.getMaxResultsPerFinder();
		if(res==null)res=0;
		return res;
	}
	
	public Double getStdevResultsPerFinder(){
		Double res = finderRepository.getStdevResultsPerFinder();
		if(res==null)res=0d;
		return res;
	}
	
	public Double getRatioEmptyFinders(){
		Double res = finderRepository.getRatioEmptyFinders();
		if(res==null)res=0d;
		return res;
	}

}