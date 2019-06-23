package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>{

	// bussines methods -----
	@Query("select a from Actor a where a.userAccount =?1")
	Actor findByUserAccount(UserAccount ua);
	
	@Query("select a from Actor a where a.userAccount.username =?1")
	Actor findByUsername(String username);
	
	@Query("select a from Actor a where a.isSpammer =?1")
	Collection<Actor> findByIsSpammer(Boolean a);
	

}
