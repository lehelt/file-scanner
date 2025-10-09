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
