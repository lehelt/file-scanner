package com.swingingstring.file_scanner.controller;

import com.swingingstring.file_scanner.model.FileItem;
import com.swingingstring.file_scanner.service.FileScannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Search API", description = "Recursive file search")
public class FileScannerController {

    private final FileScannerService scannerService;

    public FileScannerController(FileScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @GetMapping("/")
    public String index() {
        return "Greetings from File Scanner App!";
    }

    @GetMapping("/getUnique")
    @Operation(summary = "Recursively list files and directories")
    public List<FileItem> getUniqueListing(@RequestParam String path) {
        return scannerService.search(path);
    }

    @GetMapping("/history")
    public String getModHistory() {
        return "Return history";
    }
}
