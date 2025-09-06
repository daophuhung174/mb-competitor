package vn.com.mb.ai.competitor.common.jpa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

/**
 * @author hungdp
 */
//@Getter
//@Setter
//@Builder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity {
    @CreatedBy
    @Field(name="created_by")
    protected String createdBy;

    @CreatedDate
    @Field(name="created_at")
    protected Instant createdAt;

    @LastModifiedBy
    @Field(name="updated_by")
    protected String updatedBy;

    @LastModifiedDate
    @Field(name="updated_at")
    protected Instant updatedAt;

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
