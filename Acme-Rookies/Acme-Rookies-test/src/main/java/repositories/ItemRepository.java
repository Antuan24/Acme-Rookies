package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	
	@Query("select i from Item i where i.provider.id = ?1")
	Collection<Item> getItemsByProvider(int id);
	
	@Query("select i from Item i where i.provider.userAccount.id = ?1")
	Collection<Item> getItemsByUserAccount(int id);
	
	@Query("select avg(p.items.size) from Provider p")
	Double getAvgItemsPerProvider();
	
	@Query("select min(p.items.size) from Provider p")
	Integer getMinItemsPerProvider();
	
	@Query("select max(p.items.size) from Provider p")
	Integer getMaxItemsPerProvider();

	@Query("select avg(p.items.size) from Provider p")
	Double getStdevItemsPerProvider();
}
