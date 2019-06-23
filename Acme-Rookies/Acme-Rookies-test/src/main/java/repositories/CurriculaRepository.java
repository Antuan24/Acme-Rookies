
package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;


@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {
	
	@Query("select avg(r.curriculas.size) from Rookie r")
	Double getAvgCurriculasPerRookie();

	@Query("select min(r.curriculas.size) from Rookie r")
	Integer getMinCurriculasPerRookie();
	
	@Query("select max(r.curriculas.size) from Rookie r")
	Integer getMaxCurriculasPerRookie();
	
	@Query("select stddev(r.curriculas.size) from Rookie r")
	Double getStdevCurriculasPerRookie();
	
	@Query("select c from Curricula c where c.rookie.id=?1")
	Collection<Curricula> findByRookieId(int id);
	
	@Query("select c from Curricula c join c.positionDatas p where p.id=?1")
	Curricula findByPositionDataId(int id);

	@Query("select c from Curricula c join c.educationDatas e where e.id=?1")
	Curricula findByEducationDataId(int id);
	
	@Query("select c from Curricula c join c.miscellaneousDatas m where m.id=?1")
	Curricula findByMiscellaneousDataId(int id);
	
	@Query("select c from Curricula c where c.personalData.id=?1")
	Curricula findByPersonalDataId(int id);
	
	
}
