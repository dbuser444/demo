package com.example.demo.controller;


import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        Task createdTask = taskService.AddTitle(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping
    public List<Task> AllTasks(){
        return taskService.AllTitle();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetTask(@PathVariable int id){
        Task task = taskService.GetTitle(id);
        return task != null
                ? ResponseEntity.ok(task)
                : ResponseEntity.notFound().build();

    }

    @PutMapping("{id}/complete")
    public Task CompleteTask(@PathVariable int id){
        Task updateTask = taskService.completeTask(id);
        return updateTask;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteTask(@PathVariable int id){
        boolean deleted = taskService.DeleteTitle(id);
        return deleted
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

}






