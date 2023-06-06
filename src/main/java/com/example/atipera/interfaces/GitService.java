package com.example.atipera.interfaces;

import com.example.atipera.model.Branch;
import com.example.atipera.model.GitRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Set;


@Service
public interface GitService {
    Flux<GitRepository> getRepositories(String header, String userName);
    Set<Branch> getBranches(String name, String userName);
}
