package com.example.timetracker.specification;

import com.example.timetracker.dto.TaskParamsDTO;
import com.example.timetracker.model.Task;
import com.example.timetracker.model.TimeTracker;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskSpecification {
    public Specification<Task> build(TaskParamsDTO params) {
        return withName(params.getName())
                .and(withAssigneeId(params.getAssigneeId()));
    }
    public Specification<TimeTracker> buildTrackers(TaskParamsDTO params) {
        return withGetStartTime(params.getStartTime())
                .and(withGetStopTime(params.getStopTime()));
    }

    private Specification<Task> withName(String name) {
        return (root, query, cb) -> name == null ? cb.conjunction() : cb.equal(root.get("name"), name);
    }
    private Specification<TimeTracker> withGetStartTime(Date startTime) {
        return ((root, query, cb) -> startTime == null ? cb.conjunction() : cb.equal(root.get("startTime"), startTime));
    }
    private Specification<TimeTracker> withGetStopTime(Date stopTime) {
        return ((root, query, cb) -> stopTime == null ? cb.conjunction() : cb.equal(root.get("stopTime"), stopTime));
    }

    private Specification<Task> withAssigneeId(Long assigneeId) {
        return (root, query, cb) -> assigneeId == null ? cb.conjunction() : cb.equal(root.get("assignee").get("id"),
                assigneeId);
    }
}
