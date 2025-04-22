package com.DependobotScanner.dependobot.service;



import com.DependobotScanner.dependobot.model.DependabotAlert;
import com.DependobotScanner.dependobot.util.RepoUrlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScanService {

    @Value("${github.token}")
    private String githubToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<DependabotAlert> scanRepo(String repoUrl) {
        String[] repoParts = RepoUrlParser.parseRepoUrl(repoUrl);
        String owner = repoParts[0];
        String repo = repoParts[1];

        String apiUrl = "https://api.github.com/repos/" + owner + "/" + repo + "/dependabot/alerts";
        System.out.println("Token: " + githubToken); // confirm it's NOT null
        System.out.println("API URL: " + apiUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(githubToken);
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<DependabotAlert[]> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    DependabotAlert[].class
            );

            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + Arrays.toString(response.getBody()));

            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            System.err.println("Error occurred while calling GitHub API: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch alerts: " + e.getMessage());
        }
    }
}