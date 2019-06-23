
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rookie;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {
	
	@Query("select r from Rookie r where r.applications.size = (select max(r1.applications.size) from Rookie r1)")
	Collection<Rookie> getMaxApplicationsRookies();
	
	
	@Query("select r from Rookie r where r.userAccount.id = ?1")
	Rookie getRookieByUserAccountId(int id);
}
