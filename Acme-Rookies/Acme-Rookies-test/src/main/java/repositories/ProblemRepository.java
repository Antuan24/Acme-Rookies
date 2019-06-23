
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;
import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {
	
	@Query("select p from Problem p join  p.positions p1 where p1.company.id =?1 and p.isFinal=true")
	Collection<Problem> findProblemByCompanyFinal(int id);
	
	@Query("select distinct p from Problem p join  p.positions p1 where p1.company.id =?1")
	Collection<Problem> findProblemByCompany(int id);
	
	@Query("select p from Problem p where ?1 member of p.positions")
	Collection<Problem> findProblemByPosition(Position ps);
}
