package vn.com.mb.ai.competitor.common.dto;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hungdp
 */
@Getter
@Setter
public class AuditDTO {
    protected String createdBy;
    protected Instant createdAt;
    protected String updatedBy;
    protected Instant updatedAt;
}
