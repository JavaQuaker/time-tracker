package com.example.timetracker.repository;

import com.example.timetracker.model.TimeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeTrackerRepository extends JpaRepository<TimeTracker, Long> {
    @Override
    Optional<TimeTracker> findById(Long aLong);
//    List<TimeTracker> findByNameTimeTrackerId(Long id);
//    List<TimeTracker> findAllByNameTimeTrackerId(Long TaskId);
    Optional<TimeTracker> findByNameTimeTrackerId(long TaskId);

}
