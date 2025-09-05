package vn.com.mb.ai.competitor.common.web.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.com.mb.ai.competitor.common.constants.Constants;
import vn.com.mb.ai.competitor.common.dto.BaseResponse;
import vn.com.mb.ai.competitor.common.enums.errors.IError;
import vn.com.mb.ai.competitor.common.exception.http.ResponseException;
import vn.com.mb.ai.competitor.common.web.service.Translator;

/**
 * @author hungdp
 */
@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    Translator translator;

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Object> handleResponseException(ResponseException ex, WebRequest request) {
        IError error = IError.INTERNAL_ERROR;
        try {
            error = ex.getError();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        // Log thong tin loi
        log.warn(ex.getMessage(), ex);

        return ResponseEntity
                .status(error.getHttpStatus())
                .body(BaseResponse.builder()
                        .clientMessageId(request.getHeader(Constants.CLIENT_MESSAGE_ID))
                        .error(translator.toLocale(error.getMessage(), ex.getParams()))
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .soaErrorCode(error.getErrorCode())
                        .soaErrorDesc(translator.toLocale(error.getMessage(), ex.getParams()))
                        .status(error.getHttpStatus())
                        .data(null)
                        .build());
    }

//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<Object> handleMaxUploadSizeExceededException(Exception ex, WebRequest request) {
//        log.warn(ex.getMessage(), ex);
//        try {
//            return handleException(ex, request);
//        } catch (Exception e) {
//            IError error = IError.FILE_UPLOAD_EXCEEDED;
//            return ResponseEntity
//                    .status(error.getHttpStatus())
//                    .body(ErrorDTO.builder()
//                            .code(error.getErrorCode())
//                            .description(translator.toLocale(error.getMessage()))
//                            .build());
//        }
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        IError error = IError.INTERNAL_ERROR;
        log.warn(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.builder()
                        .clientMessageId(request.getHeader(Constants.CLIENT_MESSAGE_ID))
                        .error(translator.toLocale(error.getMessage()))
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .soaErrorCode(error.getErrorCode())
                        .soaErrorDesc(translator.toLocale(error.getMessage()))
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleAuthorizationDeniedException(Exception ex, WebRequest request) {
        IError error = IError.FORBIDDEN_ERROR;
        log.warn(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(BaseResponse.builder()
                        .clientMessageId(request.getHeader(Constants.CLIENT_MESSAGE_ID))
                        .error(translator.toLocale(error.getMessage()))
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .soaErrorCode(error.getErrorCode())
                        .soaErrorDesc(translator.toLocale(error.getMessage()))
                        .status(HttpStatus.FORBIDDEN.value())
                        .data(null)
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity
                .status(statusCode.value())
                .body(BaseResponse.builder()
                        .clientMessageId(request.getHeader(Constants.CLIENT_MESSAGE_ID))
                        .error(ex.getMessage())
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .soaErrorCode(String.valueOf(statusCode.value()))
                        .soaErrorDesc(ex.getMessage())
                        .status(statusCode.value())
                        .data(null)
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        IError error = IError.DATA_INVALID_ERROR;
        return ResponseEntity
                .status(status.value())
                .body(BaseResponse.builder()
                        .clientMessageId(request.getHeader(Constants.CLIENT_MESSAGE_ID))
                        .error(translator.toLocale(error.getMessage()))
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .soaErrorCode(error.getErrorCode())
                        .soaErrorDesc(translator.toLocale(error.getMessage()))
                        .status(status.value())
                        .data(null)
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        IError error = IError.DATA_INVALID_ERROR;
        return ResponseEntity
                .status(status.value())
                .body(BaseResponse.builder()
                        .clientMessageId(request.getHeader(Constants.CLIENT_MESSAGE_ID))
                        .error(translator.toLocale(error.getMessage()))
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .soaErrorCode(error.getErrorCode())
                        .soaErrorDesc(translator.toLocale(error.getMessage()))
                        .status(status.value())
                        .data(null)
                        .build());
    }
}
