package com.gnivc.logistservice.controller;

import com.gnivc.logistservice.model.task.TaskCreateRequest;
import com.gnivc.logistservice.model.task.TaskInfo;
import com.gnivc.logistservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logist")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/company/{companyId}/task")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskInfo createTask(@PathVariable("companyId") UUID companyId,
                               @RequestBody TaskCreateRequest taskCreateRequest) {
        return taskService.addTask(taskCreateRequest, companyId);
    }

    @GetMapping("/company/{companyId}/task/{taskId}")
    public TaskInfo getTask(@PathVariable("taskId") UUID taskId) {
        return taskService.getTaskInfo(taskId);
    }

    @GetMapping("/company/{companyId}/task")
    public List<TaskInfo> getTasks(@PathVariable("companyId") UUID companyId,
                                   @RequestParam(defaultValue = "0") int from,
                                   @RequestParam(defaultValue = "10") int size) {
        return taskService.getTasksInfo(companyId, from, size);
    }
}
