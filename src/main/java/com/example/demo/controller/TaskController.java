package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Вспомогательный метод для получения имени пользователя из JWT-токена
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //Создание новой задачи, привязанной к текущему пользователю.
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        String username = getCurrentUsername();
        // Используем camelCase для метода сервиса и передаем username
        Task createdTask = taskService.addTask(task, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // Получение всех задач текущего пользователя.
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        String username = getCurrentUsername();
        List<Task> tasks = taskService.getAllTasks(username);
        return ResponseEntity.ok(tasks);
    }

    // Получение задачи по ID, принадлежащей текущему пользователю.
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id){
        String username = getCurrentUsername();
        Task task = taskService.getTaskById(id, username);
        return task != null
                ? ResponseEntity.ok(task)
                : ResponseEntity.notFound().build();
    }

    // Обновление статуса задачи на "выполнено" для текущего пользователя.
    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable int id){
        String username = getCurrentUsername();
        Task updatedTask = taskService.completeTask(id, username);

        return updatedTask != null
                ? ResponseEntity.ok(updatedTask)
                : ResponseEntity.notFound().build();
    }

    // Удаление задачи по ID, принадлежащей текущему пользователю.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id){
        String username = getCurrentUsername();
        boolean deleted = taskService.deleteTask(id, username);

        return deleted
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}