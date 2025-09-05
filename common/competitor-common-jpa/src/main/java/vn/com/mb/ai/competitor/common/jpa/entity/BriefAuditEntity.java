package vn.com.mb.ai.competitor.common.jpa.entity;


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
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BriefAuditEntity {
    @CreatedDate
    @Field(name="created_at")
    protected Instant createdAt;

    @LastModifiedBy
    @Field(name="updated_by")
    protected String updatedBy;

    @LastModifiedDate
    @Field(name="updated_at")
    protected Instant updatedAt;

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
