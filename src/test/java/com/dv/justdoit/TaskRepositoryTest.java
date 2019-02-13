/**
 *
 */
package com.dv.justdoit;

import static org.junit.Assert.assertNotNull;
import java.util.Date;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import com.dv.justdoit.tasks.Task;
import com.dv.justdoit.tasks.TaskRepository;

/**
 * @author Dhawal Verma
 */

@RunWith (SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private TaskRepository    repo;

	@Test
	public void insertTask() {
		Task task = new Task();

		task.setDescription("Test");
		task.setPriority(1);
		task.setDueDate(new Date());
		task.setIsCompleted(false);

		Task subtask = new Task();

		subtask.setDescription("Test the repo.");
		subtask.setPriority(1);
		subtask.setDueDate(new Date());
		subtask.setIsCompleted(false);

		subtask.setMasterTask(task);


		entityManager.persist(task);
		entityManager.persist(subtask);
		entityManager.flush();

		Optional<Task> optionalTask = repo.findById(task.getId());
		Task           t            = optionalTask.get();

		assertNotNull(t);
		assertNotNull(
					  t.getSubtasks()
					   .size() > 0);

	}

}
