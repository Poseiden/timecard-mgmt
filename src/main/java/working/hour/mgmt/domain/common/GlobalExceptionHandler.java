package working.hour.mgmt.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        log.error(String.format("Exception: %s", e.getMessage()), e);
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return ResponseEntity.status(businessException.getHttpStatus())
                    .body(ExceptionDetail.fromException(businessException));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    }

    @Getter
    @AllArgsConstructor
    private static final class ExceptionDetail {
        private final String error;
        private final String detail;

        private static ExceptionDetail fromException(BusinessException e) {
            return new ExceptionDetail(e.getErrorKey().name(), e.getDetail());
        }
    }

}
