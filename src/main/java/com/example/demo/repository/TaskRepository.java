package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    // Получает список всех задач, принадлежащих конкретному пользователю.
    List<Task> findByUsername(String username);

    Optional<Task> findByIdAndUsername(int id, String username);
}