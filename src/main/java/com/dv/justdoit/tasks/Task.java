/**
 *
 */
package com.dv.justdoit.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dhawal Verma
 */
@Entity
@Table (name = "TASKS")
public class Task {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	private Long    userId;
	private String  description;
	private Integer priority;
	private Date    dueDate;
	private Integer progress;
	private Boolean isCompleted;

	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "MASTER_TASK_ID")
	@JsonIgnore
	private Task masterTask;

	@OneToMany (mappedBy = "masterTask")
	private List<Task> subtasks = new ArrayList<>();


	public Task() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getPriority() {
		return priority;
	}


	public void setPriority(Integer priority) {
		this.priority = priority;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public Integer getProgress() {
		return progress;
	}


	public void setProgress(Integer progress) {
		this.progress = progress;
	}


	public Boolean getIsCompleted() {
		return isCompleted;
	}


	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Task getMasterTask() {
		return masterTask;
	}


	public void setMasterTask(Task masterTask) {
		this.masterTask = masterTask;
	}


	public List<Task> getSubtasks() {
		return subtasks;
	}


	public void setSubtasks(List<Task> subtasks) {
		this.subtasks = subtasks;
	}


	@Override
	public String toString() {
		return "Task [id=" + id + ", userId=" + userId + ", description=" + description + ", priority=" + priority
						+ ", dueDate=" + dueDate + ", progress=" + progress + ", isCompleted=" + isCompleted
						+ ", masterTask=" + masterTask + ", subtasks=" + subtasks + "]";
	}



}
