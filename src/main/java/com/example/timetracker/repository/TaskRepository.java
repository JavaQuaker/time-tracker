package com.example.timetracker.repository;

import com.example.timetracker.dto.TaskTimeTrackerDataDto;
import com.example.timetracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Optional<Task> findByName(String name);
    List<Task> findAllByAssigneeId(Long userId);
//    Optional<Task> findByUserId(long id);
    //запрос который будет по конкретному пользователю вытаскивать объединенные таблицы
    //task и timeTrackers

//    @Query(value = "SELECT t.*, m.* FROM Tasks t INNER JOIN TimeTrackers m ON t.id = m.task_id " +
//        "WHERE t.assignee_id = :assignee_id", nativeQuery = true)
//    List<TaskTimeTrackerDataDto> findAllTaskTimeTrackers(@Param("assignee_id") Long assigneeId);

    //проверить метод. Должен возвращать список объектов?
}
