package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer>{
	
	@Query("select s from Sponsorship s where s.provider.id = ?1")
	Collection<Sponsorship> findByProviderId(int id);
	
	@Query("select s from Sponsorship s where s.position.id = ?1")
	Collection<Sponsorship> findByPositionId(int id);
	
	@Query("select avg(p.sponsorships.size) from Provider p")
	Double getAvgSponsorshipsPerProvider();
	
	@Query("select min(p.sponsorships.size) from Provider p")
	Integer getMinSponsorshipsPerProvider();
	
	@Query("select max(p.sponsorships.size) from Provider p")
	Integer getMaxSponsorshipsPerProvider();

	@Query("select avg(p.sponsorships.size) from Provider p")
	Double getStdevSponsorshipsPerProvider();
	
	@Query("select avg(p.sponsorships.size) from Position p")
	Double getAvgSponsorshipsPerPosition();
	
	@Query("select min(p.sponsorships.size) from Position p")
	Integer getMinSponsorshipsPerPosition();
	
	@Query("select max(p.sponsorships.size) from Position p")
	Integer getMaxSponsorshipsPerPosition();

	@Query("select avg(p.sponsorships.size) from Position p")
	Double getStdevSponsorshipsPerPosition();

}

