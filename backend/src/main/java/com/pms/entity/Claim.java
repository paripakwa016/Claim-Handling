package com.pms.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "claim")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id", nullable = false, updatable = false)
    private Long claimId;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    @Temporal(TemporalType.DATE)
    @Column(name = "claim_date", nullable = false)
    private Date claimDate;

    @Column(name = "claim_description", length = 500, nullable = false)
    private String claimDescription;

    @Column(name = "claim_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal claimAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "claim_status", nullable = false)
    private ClaimStatus claimStatus = ClaimStatus.PENDING;

    public enum ClaimStatus {
        PENDING, PAID, REJECTED
    }


    public Claim() {}


    public Claim(Policy policy, Date claimDate, String claimDescription, BigDecimal claimAmount, ClaimStatus claimStatus) {
        this.policy = policy;
        this.claimDate = claimDate;
        this.claimDescription = claimDescription;
        this.claimAmount = claimAmount;
        this.claimStatus = claimStatus;
    }


    public Long getClaimId() {
        return claimId;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimDescription() {
        return claimDescription;
    }

    public void setClaimDescription(String claimDescription) {
        this.claimDescription = claimDescription;
    }

    public BigDecimal getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(BigDecimal claimAmount) {
        this.claimAmount = claimAmount;
    }

    public ClaimStatus getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(ClaimStatus claimStatus) {
        this.claimStatus = claimStatus;
    }
}
