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
public class BaseResponse<T> {
    private String clientMessageId;

    private String error;

    private String path;

    private String soaErrorCode;

    private String soaErrorDesc;

    private Integer status;

    T data;
}
