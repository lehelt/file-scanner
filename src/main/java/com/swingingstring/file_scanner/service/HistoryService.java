package com.swingingstring.file_scanner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swingingstring.file_scanner.entity.HistoryItem;
import com.swingingstring.file_scanner.model.FileItem;

import com.swingingstring.file_scanner.repository.ScanHistoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryService {

    private final ScanHistoryRepository repository;
    private final ObjectMapper objectMapper;

    // from application.properties
    @Value("${app.user}")
    private String currentUser;

    public HistoryService(ScanHistoryRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void save(List<FileItem> items) {
        try {
            String json = objectMapper.writeValueAsString(items);
            HistoryItem history = new HistoryItem(currentUser, json);
            repository.save(history);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize FileItems to JSON", e);
        }
    }
}
