package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auditor;
import domain.Rookie;
import domain.Sponsorship;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor, Integer>{
	
	@Query("select a from Auditor a where a.userAccount.id = ?1")
	Auditor getAuditorByUserAccountId(int id);

}

