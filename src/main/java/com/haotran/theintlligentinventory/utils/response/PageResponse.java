package com.haotran.theintlligentinventory.utils.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Page Response")
public class PageResponse<T> implements Serializable {
    @Schema(description = "Page content", example = "[{...}]")
    List<T> content;

    @Schema(description = "Page number", example = "1")
    int page;

    @Schema(description = "Page size", example = "10")
    int size;

    @Schema(description = "Total number of pages", example = "10")
    long totalElements;
}
