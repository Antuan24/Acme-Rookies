package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

	
	//Bussines methods-----
	@Query("select m from Message m where m.sender = ?1") 
	Collection<Message> findBySender(Actor sender);
	
	@Query("select m from Message m where m.recipient = ?1") 
	Collection<Message> findByRecipient(Actor recipient);
	
	@Query("select m from Message m where m.sender = ?1 and ?2 member of m.tags") 
	Collection<Message> findBySenderAndTag(Actor sender, String tag);
	
	@Query("select m from Message m where m.recipient = ?1 and ?2 member of m.tags") 
	Collection<Message> findByRecipientAndTag(Actor recipient, String tag);
	
	@Query("select m from Message m where m.sender = ?1 and m.tags.size=0") 
	Collection<Message> findBySenderAndEmptyTags(Actor sender);
	
	@Query("select m from Message m where m.recipient = ?1 and m.tags.size=0") 
	Collection<Message> findByRecipientAndEmptyTags(Actor recipient);
	
}
