package com.example.timetracker.specification;

import com.example.timetracker.dto.TaskParamsDTO;
import com.example.timetracker.model.Task;
import com.example.timetracker.model.TimeTracker;
import com.example.timetracker.model.User;
import jakarta.persistence.criteria.Join;
import org.hibernate.sql.exec.spi.JdbcOperationQueryInsert;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskSpecification {
    public Specification<Task> build(TaskParamsDTO params) {
        return hasAssignee(params.getAssigneeId())
                .and(hasTimeTrackerWithStartTime(params.getStartTime()));
    }
//    public Specification<TimeTracker> buildTrackers(TaskParamsDTO params) {
//        return withGetStartTime(params.getStartTime())
//                .and(withGetStopTime(params.getStopTime()));
//    }
//// проверить работоспособность спецификации по выводу объединенных таблиц (tasks, timeTrackers)
//    public Specification<Task> qwe(TaskParamsDTO params) {
//        return ((root, query, criteriaBuilder) -> {
//            Join<Task, TimeTracker> taskTimeTrackers = root.join("timeTrackers");
//            return criteriaBuilder.equal(taskTimeTrackers.get("nameTask"), taskTimeTrackers);
//        });
//    }
//
//    private Specification<Task> withName(String name) {
//        return (root, query, cb) -> name == null ? cb.conjunction() : cb.equal(root.get("name"), name);
//    }
//    private Specification<TimeTracker> withGetStartTime(Date startTime) {
//        return ((root, query, cb) -> startTime == null ? cb.conjunction() : cb.equal(root.get("startTime"), startTime));
//    }
//    private Specification<TimeTracker> withGetStopTime(Date stopTime) {
//        return ((root, query, cb) -> stopTime == null ? cb.conjunction() : cb.equal(root.get("stopTime"), stopTime));
//    }
//
//    private Specification<Task> withAssigneeId(Long assigneeId) {
//        return (root, query, cb) -> assigneeId == null ? cb.conjunction() : cb.equal(root.get("assignee").get("id"),
//                assigneeId);
//    }
    public static Specification<Task> hasAssignee(Long assigneeId) {
        return (root, cb, query) ->
                query.like(root.<String>get("assignee"),"%" +  assigneeId + "%");
    }
    public static Specification<Task> hasTimeTrackerWithStartTime(Date timeTrackerStartTime) {
        return ((root, cb, query) -> {
            Join<Task, TimeTracker> taskTimeTracker = root.join("timeTrackers");
            return query.equal(taskTimeTracker.get("startTime"), timeTrackerStartTime);
        });
    }
   
}
