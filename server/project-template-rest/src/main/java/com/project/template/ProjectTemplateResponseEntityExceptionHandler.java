package com.project.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Objects;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.BINDING_ERRORS;
import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;
import static org.springframework.boot.web.error.ErrorAttributeOptions.of;

/**
 * @author Ouweshs28
 */
@Slf4j
@ControllerAdvice("com.project.template.rest")
public class ProjectTemplateResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorAttributes errorAttributes;

    public ProjectTemplateResponseEntityExceptionHandler(ErrorAttributes errorAttributes) {
        super();
        this.errorAttributes = errorAttributes;
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, @Nullable Object additionalBody, @NonNull HttpHeaders headers, HttpStatusCode status, @NonNull WebRequest request) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, of(BINDING_ERRORS, MESSAGE));

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());
        String reasonPhrase = httpStatus.getReasonPhrase();

        body.put("error", reasonPhrase);
        body.put("path", request.getDescription(false));
        body.put("status", status.value());

        if (additionalBody instanceof Map<?, ?> additionalBodyAsMap) {
            additionalBodyAsMap.forEach((key, value) -> body.put(String.valueOf(key), value));
        }
        return Objects.requireNonNull(super.handleExceptionInternal(ex, body, headers, status, request));
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(ex, null, headers, status, request);
    }

}