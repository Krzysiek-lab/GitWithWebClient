package com.example.atipera.service;

import com.example.atipera.interfaces.GitService;
import com.example.atipera.model.Branch;
import com.example.atipera.model.GitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class GitServiceImpl implements GitService {
    private WebClient webClient;

    @Override
    public Flux<GitRepository> getRepositories(String header, String userName) {
        String[] arr = header.split(": ");
        webClient = WebClient
                .builder()
                .baseUrl("https://api.github.com")
                .defaultHeader(arr[0], arr[1])
//                .defaultHeaders(headers -> headers.setBasicAuth("login", "haslo")) // todo te wartosci podac aby moc pobierac prywatne repozytoria
//                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + "token")
                .build();

        var flux = webClient
                .get()
                .uri("/users/" + userName + "/repos")
                .retrieve()
                .bodyToFlux(GitRepository.class);
        // collectList() da mi Mono<List<GitRepository>>
        // a block() zamieni to na List<GitRepository>
        var f = flux.collectList().block().stream().map(e -> {
            e.setBranchSet(getBranches(e.getName(), userName));
            return e;
        }).collect(Collectors.toSet());
        return Flux.fromIterable(f);
    }

    @Override
    public Set<Branch> getBranches(String name, String userName) {
        return new HashSet<>(Objects.requireNonNull(webClient.get()
                .uri("repos/" + userName + "/" + name + "/branches")
                .retrieve()
                .bodyToFlux(Branch.class).collectList().block()));
    }
}
