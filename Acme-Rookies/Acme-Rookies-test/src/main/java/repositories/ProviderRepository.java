package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auditor;
import domain.Provider;
import domain.Rookie;
import domain.Sponsorship;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer>{

	@Query("select p from Provider p where p.userAccount.id = ?1")
	Provider getProviderByUserAccountId(int id);
	
	@Query("select p from Provider p order by p.items.size desc")
	Collection<Provider> topProvidersItems();
	
	@Query("select p from Provider p where (select count(s1)*1.0/ (select count(p1) from Provider p1) from Sponsorship s1)*1.1 <= (select count(s3)*1.0 from Sponsorship s3 where p.id = s3.provider.id)")
	Collection<Provider> getProvidersWMoreSponsorshipsThanAvg();

}

