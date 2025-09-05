package vn.com.mb.ai.competitor.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import vn.com.mb.ai.competitor.common.dto.CommonResponseCode;
import vn.com.mb.ai.competitor.common.enums.errors.DefaultError;
import vn.com.mb.ai.competitor.common.enums.errors.ErrorDTO;
import vn.com.mb.ai.competitor.common.enums.errors.IError;
import vn.com.mb.ai.competitor.common.exception.http.ResponseException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hungdp
 */
@Slf4j
public class SecurityUtils {
    public static void writeBodyError(HttpServletResponse response, ResponseException ex) throws IOException {
        IError error = new DefaultError(CommonResponseCode.UNKNOWN.getCode());
        try {
            error = ex.getError();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        // Log thong tin loi
        log.warn(ex.getMessage(), ex);

        var body = ErrorDTO.builder()
                .code(error.getErrorCode())
                .description(error.getMessage())
                .build();

        response.resetBuffer();
        response.setStatus(error.getHttpStatus());
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(body));
        response.flushBuffer(); // marks response as committed -- if we don't do this the request will go through normally!
    }
}
