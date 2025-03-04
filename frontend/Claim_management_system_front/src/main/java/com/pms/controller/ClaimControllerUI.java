package com.pms.controller;

import com.pms.entity.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/claims")  // Use a consistent base path
public class ClaimControllerUI {

    private final String BASE_URL = "http://localhost:9796/api/claims"; // Match the backend API

    @Autowired
    private RestTemplate restTemplate;

    // Load homepage with an empty claim object
    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("claim", new Claim());
        return "index";
    }

    // Fetch all claims
    @GetMapping("/all")
    public String getAllClaims(Model model) {
        ResponseEntity<Claim[]> response = restTemplate.getForEntity(BASE_URL, Claim[].class);
        List<Claim> claims = Arrays.asList(response.getBody());
        model.addAttribute("claims", claims);
        return "claims"; // Return to a Thymeleaf template named "claims.html"
    }

    // Fetch a single claim by ID
    @GetMapping("/{id}")
    public String getClaimById(@PathVariable Long id, Model model) {
        ResponseEntity<Claim> response = restTemplate.getForEntity(BASE_URL + "/" + id, Claim.class);
        model.addAttribute("claim", response.getBody());
        return "claim-detail"; // Return to a Thymeleaf page named "claim-detail.html"
    }

    // Create a new claim
    @PostMapping("/create")
    public String createClaim(@ModelAttribute Claim claim) {
        restTemplate.postForEntity(BASE_URL, claim, Claim.class);
        return "redirect:/claims/all"; // Redirect to claims list
    }

    // Update an existing claim
    @PostMapping("/update/{id}")
    public String updateClaim(@PathVariable Long id, @ModelAttribute Claim claim) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Claim> request = new HttpEntity<>(claim, headers);
        restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.PUT, request, Claim.class);
        return "redirect:/claims/all";
    }

    // Delete a claim
    @GetMapping("/delete/{id}")
    public String deleteClaim(@PathVariable Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
        return "redirect:/claims/all";
    }
}
