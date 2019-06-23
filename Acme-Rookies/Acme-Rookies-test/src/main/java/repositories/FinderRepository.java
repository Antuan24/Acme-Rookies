package repositories;

import domain.Position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Finder;

import java.util.Collection;
import java.util.Date;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer>{

	@Query("select p from Position p where " +
			"((:keyword is null or :keyword like '' ) or " +
				"(p.ticker like %:keyword% " +
				"or p.description like %:keyword% " +
				"or p.skills like %:keyword% " +
				"or p.technologies like %:keyword% " +
				"or p.profile like %:keyword% " +
				"or p.title like %:keyword%)) " +
			"and (:maxSalary is null or p.salary < :maxSalary) " +
			"and (:maxDeadline is null or p.deadline <= :maxDeadline) " +
			"and (:minSalary is null or p.salary > :minSalary)")
	Collection<Position> filterPositions(@Param("keyword") String keyword,
			 								@Param("maxSalary") Double maxSalary, @Param("maxDeadline") Date maxDeadline,
										   @Param("minSalary") Double minSalary);
	
	@Query("select f from Finder f where f.rookie.id = :rookieId")
	Finder findByRookie(@Param("rookieId") Integer rookieId);
	
	@Query("select avg(f.positions.size) from Finder f")
	Double getAvgResultsPerFinder();

	@Query("select min(f.positions.size) from Finder f")
	Integer getMinResultsPerFinder();
	
	@Query("select max(f.positions.size) from Finder f")
	Integer getMaxResultsPerFinder();
	
	@Query("select stddev(f.positions.size) from Finder f")
	Double getStdevResultsPerFinder();
	
	@Query("select (count(f)/(select count(f1) from Finder f1 where f1.keyword!=null or f1.minSalary!=null or f1.maxSalary!=null or f1.maxDeadline!=null or f1.positions.size!=0))*1.0 from Finder f where f.keyword is null and f.minSalary is null and f.maxSalary is null and f.maxDeadline is null and f.positions.size=0")
	Double getRatioEmptyFinders();
}

