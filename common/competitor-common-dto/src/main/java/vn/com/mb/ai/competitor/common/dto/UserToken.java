package vn.com.mb.ai.competitor.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hungdp
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    private String username;
    private String token;
    private String userId;
    private String name;
    private String clientId;
    private String sessionState;
}
