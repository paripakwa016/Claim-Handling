package com.pms.controller;

import com.pms.entity.Claim;
import com.pms.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping
    public ResponseEntity<?> createClaim(@RequestBody Claim claim) {  // ✅ Add @RequestBody
        try {
            Claim savedClaim = claimService.createClaim(claim);
            return ResponseEntity.ok(savedClaim);  // ✅ Return a proper HTTP response
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  // ✅ Return meaningful error messages
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
}
