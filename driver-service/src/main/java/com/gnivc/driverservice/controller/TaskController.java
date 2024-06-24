package com.gnivc.driverservice.controller;

import com.gnivc.driverservice.model.task.TaskInfoResponse;
import com.gnivc.driverservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/driver/company/{companyId}/driver/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskInfoResponse> getTasks(@RequestHeader("x-userId") UUID userId,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        return taskService.getTasks(userId, from, size);
    }
}
