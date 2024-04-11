package com.example.timetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class User implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;
    @Email
    @Column(unique = true)
    private String email;

    @CreatedDate
    private LocalDate createdAt;

    @OneToMany(mappedBy = "assignee", fetch = FetchType.LAZY)
    List<Task> list = new ArrayList<>();



}
