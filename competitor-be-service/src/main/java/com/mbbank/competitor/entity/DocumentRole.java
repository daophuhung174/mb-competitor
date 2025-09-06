package com.mbbank.competitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DocumentRole {
    @Id
    @Field(name = "id")
    private String id;
    @Field(name = "document_id")
    private String documentId;
    @Field(name = "role")
    private String role;
}
