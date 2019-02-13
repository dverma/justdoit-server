/**
 *
 */
package com.dv.justdoit.tasks;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dhawal Verma
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByUserId(Long userId);

}
