package com.github.charlesluxinger.bytebank.controller.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Schema(name = "Api Exception Response")
@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiExceptionResponse {

    @Schema(example = "999")
    private final int status;

    @Schema(example = "Network crashes")
    private final String title;

    @Schema(example = "Some network cable was broken.")
    private final String detail;

    @Schema(example = "/api/v1/resource/1")
    private final String path;

    @Schema(example = "2020-04-24T19:27:01.718Z")
    private final OffsetDateTime timestamp = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    public static ResponseEntity<ApiExceptionResponse> buildBadRequestResponse(final String path, final String detail) {
        return ResponseEntity
                .badRequest()
                .contentType(APPLICATION_JSON)
                .body(ApiExceptionResponse
                        .builder()
                        .status(BAD_REQUEST.value())
                        .title(BAD_REQUEST.getReasonPhrase())
                        .detail(detail)
                        .path(path)
                        .build());
    }

}
