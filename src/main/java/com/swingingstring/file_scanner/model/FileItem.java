package com.swingingstring.file_scanner.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(
        name = "FileItem",
        description = "Represents a file or directory in the scanned file structure, possibly containing nested children."
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileItem {

    @Schema(
            description = "The name of the file or directory (e.g., 'example.txt' or 'documents').",
            example = "example.txt"
    )
    private String name;

    @Schema(
            description = "The absolute or relative path of the file or directory.",
            example = "/home/user/documents/example.txt"
    )
    private String path;

    @Schema(
            description = "Indicates whether this item represents a directory (true) or a file (false).",
            example = "false"
    )
    private boolean directory;

    @Schema(
            description = "Children contained in this directory. Null or empty for files.",
            implementation = FileItem.class
    )
    private List<FileItem> children;
}
