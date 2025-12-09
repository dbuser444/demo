package com.example.demo.service;

import com.example.demo.model.Task;
import org.springframework.stereotype.Service;
import com.example.demo.repository.TaskRepository;

import java.util.List;


@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // вывод всех задач
    public List<Task> AllTitle() {
        return taskRepository.findAll() ;
    }

    // добавление
    public Task AddTitle(Task newTask) {
        return taskRepository.save(newTask);
    }

    //вывод по id
    public Task GetTitle(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    // удаление по Id
    public boolean DeleteTitle(int id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // поменять статус
    public Task completeTask(int id) {
        Task task = GetTitle(id);

        if(task != null) {
            task.setCompleted(true);
            return taskRepository.save(task);
        }
        return null;
    }
}













