package com.pms.service;

import com.pms.entity.Claim;
import com.pms.entity.Policy;
import com.pms.repository.ClaimRepository;
import com.pms.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private PolicyRepository policyRepository;

    public Claim createClaim(Claim claim) {
        if (claim.getPolicy() == null || claim.getPolicy().getPolicyId() == null) {
            throw new RuntimeException("Policy ID is required to create a claim.");
        }

        System.out.println("🔍 Received Policy ID: " + claim.getPolicy().getPolicyId());  // ✅ Debugging Log

        Policy policy = policyRepository.findById(claim.getPolicy().getPolicyId())
                .orElseThrow(() -> new RuntimeException("🚨 Policy not found in database."));

        claim.setPolicy(policy);  // ✅ Ensure policy is set before saving
        return claimRepository.save(claim);
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }

    public void deleteClaim(Long id) {
        claimRepository.deleteById(id);
    }
}
