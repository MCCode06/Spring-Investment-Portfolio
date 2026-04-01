package com.portfolio.spring_portfolio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fund")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fund {

    @Id
    private Long id;

    @Column(nullable = false)
    private Double totalFund;
}