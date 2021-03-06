package com.vmw.assignment.ng.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vmw.assignment.ng.model.entity.TaskStatus;
import com.vmw.assignment.ng.model.entity.TaskStatusId;

@Repository
public interface TaskRepository extends CrudRepository<TaskStatus, TaskStatusId> {

	@Query(value = "SELECT id, status, timestamp FROM task_status ts WHERE ts.id = :taskId ORDER BY timestamp DESC", nativeQuery = true)
	List<TaskStatus> findAllTasksByTaskId(@Param("taskId") String taskId);

}
