package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Создает новую задачу, привязывая ее к текущему пользователю.
    public Task addTask(Task task, String username) {
        task.setUsername(username);
        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }
        return taskRepository.save(task);
    }

    // Получает все задачи, принадлежащие конкретному пользователю.
    public List<Task> getAllTasks(String username) {
        return taskRepository.findByUsername(username);
    }

    // Получает одну задачу по ID, только если она принадлежит текущему пользователю.
    public Task getTaskById(int id, String username) {
        return taskRepository.findByIdAndUsername(id, username).orElse(null);
    }

    // Обновляет статус задачи на "выполнено", только если она принадлежит текущему пользователю.
    @Transactional
    public Task completeTask(int id, String username) {
        Task task = taskRepository.findByIdAndUsername(id, username).orElse(null);

        if (task != null) {
            task.setCompleted(true);
            return taskRepository.save(task);
        }
        return null;
    }

    // Удаляет задачу по ID, только если она принадлежит текущему пользователю.
    public boolean deleteTask(int id, String username) {
        Task task = taskRepository.findByIdAndUsername(id, username).orElse(null);

        if (task != null) {
            taskRepository.delete(task);
            return true;
        }
        return false;
    }
}