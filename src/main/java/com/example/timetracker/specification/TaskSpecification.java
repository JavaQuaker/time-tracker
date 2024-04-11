package com.example.timetracker.specification;

import com.example.timetracker.dto.TaskParamsDTO;
import com.example.timetracker.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TaskSpecification {
    public Specification<Task> build(TaskParamsDTO params) {
        return withName(params.getName())
                .and(withAssigneeId(params.getAssigneeId()));


    }
    private Specification<Task> withName(String name) {
        return (root, query, cb) -> name == null ? cb.conjunction() : cb.equal(root.get("name"), name);
    }
    private Specification<Task> withAssigneeId(Long assigneeId) {
        return (root, query, cb) -> assigneeId == null ? cb.conjunction() : cb.equal(root.get("assignee").get("id"),
                assigneeId);
    }
}
