//package vn.com.mbbank.aicenter.beegen.portal.entity.document;
//
//import com.mbbank.competitor.validator.ValidType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Version;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//import vn.com.mb.ai.competitor.common.jpa.entity.AuditEntity;
//import vn.com.mbbank.aicenter.beegen.portal.constant.document.DocumentDefinition;
//
//import java.io.Serializable;
//import java.time.Instant;
//import java.util.List;
//import java.util.UUID;
//
//import static vn.com.mbbank.aicenter.beegen.portal.constant.MongoCollectionName.DOCUMENT;
//
//@Getter
//@Setter
//@SuperBuilder
//@NoArgsConstructor(force = true)
//@AllArgsConstructor
//@Document(collection = DOCUMENT)
//public class Documents extends AuditEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Builder.Default
//    @Field(DocumentDefinition.Document.Fields._id)
//    private String id = UUID.randomUUID().toString();
//
//    @Field(DocumentDefinition.Document.Fields.doc_name)
//    private String docName;
//
//    @Field(DocumentDefinition.Document.Fields.type)
//    private String type;
//
//    @Field(DocumentDefinition.Document.Fields.description)
//    private String description;
//
//    @Field(DocumentDefinition.Document.Fields.path)
//    private String path;
//
//    @Field(DocumentDefinition.Document.Fields.status)
//    @ValidType(in = DocumentDefinition.DocumentStatus.class)
//    private String status;
//
//    @Field(DocumentDefinition.Document.Fields.training_status)
//    @ValidType(in = DocumentDefinition.DocumentTrainingStatus.class)
//    private String trainingStatus;
//
//    @Field(DocumentDefinition.Document.Fields.is_trained)
//    private Boolean isTrained;
//
//    @Field(DocumentDefinition.Document.Fields.trained_at)
//    private Instant trainedAt;
//
//    @Field(DocumentDefinition.Document.Fields.role_ids)
//    private List<String> roleIds;
//
//}
