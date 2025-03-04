package com.pms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "policy")

public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id", length = 20)
    private Long policyId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "total_premium", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalPremium;

    @Column(name = "maturity_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal maturityAmount;

    @Column(name = "policy_term", nullable = false)
    private int policyTerm; // Number of years

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_status", nullable = false)
    private PolicyStatus policyStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "annuity_term", nullable = false)
    private AnnuityTerm annuityTerm;
}