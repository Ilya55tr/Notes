package com.example.Notes.services;

import com.example.Notes.dto.TaskCreateEditDto;
import com.example.Notes.dto.TaskReadDto;
import com.example.Notes.mapper.TaskCreateEditMapper;
import com.example.Notes.mapper.TaskReadMapper;
import com.example.Notes.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskReadMapper taskReadMapper;
    private final TaskCreateEditMapper taskCreateEditMapper;

    public Optional<TaskReadDto> findById(Integer id){
        return taskRepository.findById(id).map(taskReadMapper::map);
    }

    @Transactional
    public TaskReadDto createTask(TaskCreateEditDto taskDto){
        return Optional.of(taskDto)
                .map(taskCreateEditMapper::map)
                .map(task -> {
                    task.getNote().addTask(task);
                    return task;
                })
                .map(taskRepository::save)
                .map(taskReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<TaskReadDto> updateTask(Integer id, TaskCreateEditDto taskDto){
        return taskRepository.findById(id).map(entity -> taskCreateEditMapper.map(taskDto, entity))
                .map(task -> {
                    boolean flag = false;
                    for (int i = 0; i < task.getNote().getTasks().size(); i++) {
                        if (task.getNote().getTasks().get(i).equals(task)){
                            flag=true;
                        }
                    }
                    if (!flag){
                        task.getNote().addTask(task);
                    }
                    return task;
                })
                .map(taskRepository::saveAndFlush)
                .map(taskReadMapper::map);
    }

    @Transactional
    public boolean deleteTask(Integer id){
        return taskRepository.findById(id).map(task -> {
            task.getNote().removeTask(task);
            taskRepository.delete(task);
            taskRepository.flush();
            return true;
        }).orElse(false);

    }
}
