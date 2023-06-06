package com.example.atipera.controller;

import com.example.atipera.interfaces.GitService;
import com.example.atipera.model.GitRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("git/")
@AllArgsConstructor
public class GitController {
    private final GitService gitServiceImpl;

    @GetMapping
    public ResponseEntity<Flux<GitRepository>> allRepositories(@RequestParam() @Valid @NotEmpty String userName,
                                                               @RequestParam(defaultValue = "Accept: application/json") String header) {
        return new ResponseEntity<>(gitServiceImpl.getRepositories(header, userName), HttpStatus.OK);
    }
}

