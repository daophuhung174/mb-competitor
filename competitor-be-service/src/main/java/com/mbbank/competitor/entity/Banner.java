package vn.com.mb.ai.competitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "banner")
public class Banner {
    @MongoId
    @Field(name = "_id")
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    @Field(name = "name")
    private String name;
    @Field(name = "link")
    private String link;

}
