package com.swingingstring.file_scanner.controller;

import com.swingingstring.file_scanner.model.FileItem;
import com.swingingstring.file_scanner.model.HistoryResponse;
import com.swingingstring.file_scanner.service.FileScannerService;
import com.swingingstring.file_scanner.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Scan API", description = "Recursive file search")
public class FileScannerController {

    private final FileScannerService scannerService;
    private final HistoryService historyService;

    public FileScannerController(FileScannerService scannerService, HistoryService historyService) {
        this.scannerService = scannerService;
        this.historyService = historyService;
    }

    @GetMapping("/getUnique")
    @Operation(
            summary = "Recursively list files and directories",
            description = "Performs a depth-first traversal starting at the given path and returns a de-duplicated hierarchical structure of directories and files. Optionally filtered by file extension via configuration.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Scan completed successfully",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FileItem.class)))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid directory path supplied")
            }
    )
    public List<FileItem> getUniqueListing(
            @Parameter(description = "Absolute or relative directory path to start the scan from", example = "/home/user/projects")
            @RequestParam String path) {
        List<FileItem> items = scannerService.search(path);
        historyService.save(items);
        return items;
    }

    @GetMapping("/history")
    @Operation(
            summary = "Get previous scan results per user",
            description = "Returns all persisted scan results associated with the configured application user.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "History retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HistoryResponse.class)))
            )
    )
    public List<HistoryResponse> getModHistory() {
        return historyService.getAllHistoryItems();
    }

    @GetMapping("/doc")
    @Operation(summary = "Redirect to OpenAPI JSON", description = "Convenience endpoint to jump to the OpenAPI JSON document exposed by Springdoc.")
    public ResponseEntity<Void> redirectToApiDocs() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/v3/api-docs")
                .build();
    }
}
