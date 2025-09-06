package vn.com.mb.ai.competitor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import vn.com.mb.ai.competitor.common.dto.AuditDTO;
import vn.com.mb.ai.competitor.common.jpa.entity.AuditEntity;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Document(collection = "banner")
public class Banner extends AuditEntity {
//    @MongoId
    @Id
    @Field(name = "_id")
//    @Builder.Default
    private String id = UUID.randomUUID().toString();
    @Field(name = "name")
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    @Field(name = "link")
    private String link;

}
