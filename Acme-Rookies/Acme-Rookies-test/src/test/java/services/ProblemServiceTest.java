package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Problem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ProblemServiceTest extends AbstractTest {

	// Managed service --------------------------------------------------
	
	@Autowired
	private ProblemService problemService;
	
	// Support services -------------------------------------------------
	
	

	// Tests ------------------------------------------------------------
	
	@Test
	public void testCreate() {
		
		Problem problem;
		
		authenticate("company1");
		
		problem = problemService.create();
		
		Assert.isNull(problem.getHint());
		Assert.isNull(problem.getStatement());
		Assert.isNull(problem.getTitle());
		
		Assert.isTrue(problem.getAttachments().isEmpty());
		Assert.isTrue(problem.getPositions().isEmpty());
		Assert.isTrue(!problem.getIsFinal());
		
		unauthenticate();
		
	}

	@Test
	public void testSave() {

		Problem problem, saved;
		
		authenticate("company1");
		
		problem = problemService.create();
		
		problem.setHint("A lot of work");
		problem.setStatement("Statement 1");
		problem.setTitle("My BBDD is working out");
		problem.getAttachments().add("Attachment 1");
		problem.getAttachments().add("Attachment 2");
		problem.getAttachments().add("Attachment 3");
		
		saved = problemService.save(problem);
		Assert.isTrue(problemService.findAll().contains(saved));
		
		unauthenticate();
	}
	
	@Test
	public void testDelete() {
		
		Problem problem, saved;
		
		authenticate("company1");
		
		problem = problemService.create();
		
		problem.setHint("A lot of work");
		problem.setStatement("Statement 1");
		problem.setTitle("My BBDD is working out");
		problem.getAttachments().add("Attachment 1");
		problem.getAttachments().add("Attachment 2");
		problem.getAttachments().add("Attachment 3");
		
		saved = problemService.save(problem);
		problemService.delete(saved);
		
		Assert.isTrue(!problemService.findAll().contains(saved));
		
		unauthenticate();
	}
	
	// Functional testing -----------------------------------------------------------
	
	// RF 9.2
	// Editar un problema otro actor
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSaveIncorrectAuthenticated(){
		
		authenticate("auditor1");
		
		Problem problem, saved;
		
		problem = problemService.create();
		
		problem.setHint("A lot of work");
		problem.setStatement("Statement 1");
		problem.setTitle("My BBDD is working out");
		problem.getAttachments().add("Attachment 1");
		problem.getAttachments().add("Attachment 2");
		problem.getAttachments().add("Attachment 3");
		
		saved = problemService.save(problem);
		Assert.isTrue(problemService.findAll().contains(saved));
		
		unauthenticate();

	}
	
	// RF 9.2
	@Test
	public void testUpdateProblem(){
		
		Problem problem;
		
		authenticate("company1");
		
		problem = problemService.findOne(getEntityId("problem1"));
		
		problem.setStatement("hola");
		
		unauthenticate();
		
	}

}
