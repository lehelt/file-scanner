package com.swingingstring.file_scanner.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "HistoryResponse", description = "A single persisted scan result with metadata and scanned items.")
@Value
public class HistoryResponse {
    @Schema(description = "Primary key of the history entry", example = "42")
    Long id;

    @Schema(description = "User who initiated the scan", example = "demo-user")
    String username;

    @Schema(description = "Creation timestamp of the history entry")
    LocalDateTime createdAt;

    @Schema(description = "Items returned by the scan", implementation = FileItem.class)
    List<FileItem> fileItems;
}
