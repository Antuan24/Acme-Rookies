package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	@Query("select a from Admin a where a.userAccount = ?1") 
	Admin findByUserAccount(UserAccount ua);
	

	
}
