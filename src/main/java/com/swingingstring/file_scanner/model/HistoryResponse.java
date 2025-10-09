package com.swingingstring.file_scanner.model;

import lombok.Value;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class HistoryResponse {
    Long id;
    String username;
    LocalDateTime createdAt;
    List<FileItem> fileItems;
}
