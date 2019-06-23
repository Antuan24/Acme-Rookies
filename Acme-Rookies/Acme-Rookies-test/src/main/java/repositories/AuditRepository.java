package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;
import domain.Company;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer>{
	
	@Query("select a from Audit a where a.auditor.id=?1")
	Collection<Audit> findAuditsByAuditor(int auditorId);

	@Query("select avg(1.0*(select avg(a.score) from Audit a where a.position = p)) from Position p")
	Double getAvgAuditScorePerPosition();
	
	@Query("select min(1.0*(select avg(a.score) from Audit a where a.position = p)) from Position p")
	Double getMinAuditScorePerPosition();
	
	@Query("select max(1.0*(select avg(a.score) from Audit a where a.position = p)) from Position p")
	Double getMaxAuditScorePerPosition();
	
	@Query("select stddev(1.0*(select avg(a.score) from Audit a where a.position = p)) from Position p")
	Double getStdevAuditScorePerPosition();
	
	@Query("select avg(a.score) from Company c join c.positions p join p.audits a group by c")
	Collection<Double> getAuditScorePerCompany();
	
	@Query("select a from Audit a where a.position.company = ?1")
	Collection<Audit> getAuditsPerCompany(Company c);
	
	@Query("select avg(c.auditScore) from Company c")
	Double getAvgAuditScorePerCompany();
	
	@Query("select min(c.auditScore) from Company c")
	Double getMinAuditScorePerCompany();
	
	@Query("select max(c.auditScore) from Company c")
	Double getMaxAuditScorePerCompany();

	@Query("select stddev(c.auditScore) from Company c")
	Double getStdevAuditScorePerCompany();
	
	
}

