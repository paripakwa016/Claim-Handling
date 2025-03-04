package com.pms.controller;

import com.pms.entity.Claim;
import com.pms.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping
    public ResponseEntity<?> createClaim(@RequestBody Claim claim) {
        System.out.println(claim);
        try {
            Claim savedClaim = claimService.createClaim(claim);
            return ResponseEntity.ok(savedClaim);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Claim>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClaimById(@PathVariable Long id) {
        Optional<Claim> claim = claimService.getClaimById(id);
        return claim.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClaim(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.ok("Claim deleted successfully.");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClaim(@PathVariable Long id, @RequestBody Claim updatedClaim) {
        Optional<Claim> record = claimService.getClaimById(id);
        if (record.isEmpty()) {
            return ResponseEntity.badRequest().body("Claim does not exist");
        }

        try {
            Claim claim = record.get();
            claim.setClaimDescription(updatedClaim.getClaimDescription());
            claim.setClaimStatus(updatedClaim.getClaimStatus());

            Claim savedClaim = claimService.createClaim(claim);
            return ResponseEntity.ok(savedClaim);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
