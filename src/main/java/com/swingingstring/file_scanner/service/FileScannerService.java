package com.swingingstring.file_scanner.service;

import com.swingingstring.file_scanner.config.ScanProperties;
import com.swingingstring.file_scanner.model.FileItem;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class FileScannerService {

    private final ScanProperties properties;

    public FileScannerService(ScanProperties properties) {
        this.properties = properties;
    }

    /**
     * Recursively scans the provided directory path and returns a hierarchical list of {@link FileItem}.
     * The scan is de-duplicated by absolute path to prevent cycles and repeated entries.
     * If a file extension is configured via {@link ScanProperties#getFileExtensionToScan()}, only files
     * with that extension are included; directories without any matching children are omitted.
     *
     * @param path absolute or relative directory path used as the scan root
     * @return list of file or directory items representing the immediate children under the path
     * @throws IllegalArgumentException when the provided path does not exist or is not a directory
     */
    public List<FileItem> search (String path){

        File root = new File(path);

        if (!root.exists() || !root.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory: " + path);
        }

        Set<String> visited = new HashSet<>();

        List<FileItem> result = new ArrayList<>();
        for (File file : Objects.requireNonNull(root.listFiles())) {
            FileItem item = mapToFileItem(file, visited);
            if(item != null){
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Maps a {@link File} into a {@link FileItem} and recurses through directory children.
     * Uses the {@code visited} set to avoid revisiting the same absolute path.
     * Directories with no matching children are filtered out (returns {@code null}).
     *
     * @param file    current file or directory
     * @param visited set of absolute paths already processed
     * @return mapped {@link FileItem} or {@code null} when filtered out
     */
    private FileItem mapToFileItem(File file, Set<String> visited) {

        String absolutePath = file.getAbsolutePath();

        if (!visited.add(absolutePath)) {
            return null;
        }

        if (file.isDirectory()) {
            List<FileItem> children = new ArrayList<>();

            for (File child : Objects.requireNonNull(file.listFiles())) {
                FileItem childItem = mapToFileItem(child, visited);
                if (childItem != null) {
                    children.add(childItem);
                }
            }

            if (children.isEmpty()) {
                return null;
            }

            FileItem directoryItem = new FileItem();
            directoryItem.setName(file.getName());
            directoryItem.setPath(file.getAbsolutePath());
            directoryItem.setDirectory(true);
            directoryItem.setChildren(children);
            return directoryItem;
        }

        String extension = properties.getFileExtensionToScan();

        if (extension != null && !extension.isBlank()) {
            if (!file.getName().toLowerCase().endsWith("." + extension.toLowerCase())) {
                return null;
            }
        }

        FileItem fileItem = new FileItem();
        fileItem.setName(file.getName());
        fileItem.setPath(file.getAbsolutePath());
        fileItem.setDirectory(false);
        return fileItem;
    }
}
