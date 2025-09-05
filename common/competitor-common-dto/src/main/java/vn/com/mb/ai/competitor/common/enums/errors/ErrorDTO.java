package vn.com.mb.ai.competitor.common.enums.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author hungdp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String description;
    private List<String> details;
}
