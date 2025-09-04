package com.mbbank.competitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String link;

}
