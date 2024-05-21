package com.example.timetracker.repository;

import com.example.timetracker.model.TimeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeTrackerRepository extends JpaRepository<TimeTracker, Long> {
    @Override
    Optional<TimeTracker> findById(Long aLong);
}
