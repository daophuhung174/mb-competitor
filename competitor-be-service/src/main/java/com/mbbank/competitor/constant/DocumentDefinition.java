package com.mbbank.competitor.constant;

import lombok.experimental.FieldNameConstants;

import java.time.Instant;
import java.util.List;

public class DocumentDefinition {


    @FieldNameConstants
    public record Document(
            String _id,
            String doc_name,
            String category_id,
            String bot_id,
            String description,
            String path,
            String status,
            String training_status,
            String error_message,
            String type,
            boolean deleted,
            boolean is_trained,
            Instant trained_at,
            Instant created_at,
            Instant updated_at,
            String created_by,
            String updated_by,
            String warned,
            long version,
            String chunk_type,
            List<String> role_ids,
            Instant valid_from,
            Instant valid_to

    ) {
    }


    @FieldNameConstants(onlyExplicitlyIncluded = true)
    public enum TopicDocumentType {
        @FieldNameConstants.Include processing,
        @FieldNameConstants.Include complete,
        @FieldNameConstants.Include error
    }

    @FieldNameConstants(onlyExplicitlyIncluded = true)
    public enum DocumentType {
        @FieldNameConstants.Include XLSX,
        @FieldNameConstants.Include FOLDER,
        @FieldNameConstants.Include PPTX,
        @FieldNameConstants.Include PDF,
        @FieldNameConstants.Include WORD,
        @FieldNameConstants.Include IMAGE,
        @FieldNameConstants.Include TXT
    }

    @FieldNameConstants(onlyExplicitlyIncluded = true)
    public enum DocumentStatus {
        @FieldNameConstants.Include DONE,
        @FieldNameConstants.Include PROCESS,
        @FieldNameConstants.Include ERROR
    }

    @FieldNameConstants(onlyExplicitlyIncluded = true)
    public enum DocumentTrainingStatus {
        @FieldNameConstants.Include DONE,
        @FieldNameConstants.Include PROCESS,
        @FieldNameConstants.Include ERROR
    }


    @FieldNameConstants(onlyExplicitlyIncluded = true)
    public enum DocumentDataType {
        @FieldNameConstants.Include TEXT,
        @FieldNameConstants.Include OBJECT
    }
}
