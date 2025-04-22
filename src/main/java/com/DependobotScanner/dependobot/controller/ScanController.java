package com.DependobotScanner.dependobot.controller;

import com.DependobotScanner.dependobot.model.DependabotAlert;
import com.DependobotScanner.dependobot.model.RepoRequest;
import com.DependobotScanner.dependobot.service.ScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScanController {

    private final ScanService scanService;

    @PostMapping("/scan")
    public ResponseEntity<List<DependabotAlert>> scan(@RequestBody RepoRequest request) {
        List<DependabotAlert> alerts = scanService.scanRepo(request.getRepoUrl());
        return ResponseEntity.ok(alerts);
    }
}
