
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	
	@Query("select a from Application a where a.rookie.id = ?1 order by a.status")
	Collection<Application> findAppByRookie(int id);
	
	@Query("select a from Application a where a.rookie.id = ?1 and a.status=?2 order by a.status")
	Collection<Application> findAppByRookieAndStatus(int id, String status);
	
	@Query("select a from Application a where a.position.company.id = ?1 order by a.status")
	Collection<Application> findAppByCompany(int id);
	
	@Query("select a from Application a where a.position.company.id = ?1 and a.status=?2 order by a.status")
	Collection<Application> findAppByCompanyAndStatus(int id, String status);
	
	@Query("select avg(r.applications.size) from Rookie r")
	Double getAvgApplicationsPerRookie();
	
	@Query("select min(r.applications.size) from Rookie r")
	Integer getMinApplicationsPerRookie();
	
	@Query("select max(r.applications.size) from Rookie r")
	Integer getMaxApplicationsPerRookie();
	
	@Query("select stddev(r.applications.size) from Rookie r")
	Double getStdevApplicationsPerRookie();
}
