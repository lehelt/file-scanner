package com.swingingstring.file_scanner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swingingstring.file_scanner.entity.HistoryItem;
import com.swingingstring.file_scanner.model.FileItem;
import com.swingingstring.file_scanner.model.HistoryResponse;
import com.swingingstring.file_scanner.repository.ScanHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistoryServiceTest {

    private ScanHistoryRepository repository;
    private ObjectMapper objectMapper;
    private HistoryService historyService;

    @BeforeEach
    void setUp() {
        repository = mock(ScanHistoryRepository.class);
        objectMapper = mock(ObjectMapper.class);
        historyService = new HistoryService(repository, objectMapper);

        // currentUser értéket reflection-nel állítjuk, mivel private @Value
        try {
            var field = HistoryService.class.getDeclaredField("currentUser");
            field.setAccessible(true);
            field.set(historyService, "testUser");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSave() throws JsonProcessingException {
        FileItem item = new FileItem();
        item.setName("file1.txt");
        item.setPath("/tmp/file1.txt");
        item.setDirectory(false);
        List<FileItem> items = List.of(item);

        String json = "[{\"name\":\"file1.txt\"}]";
        when(objectMapper.writeValueAsString(items)).thenReturn(json);

        historyService.save(items);

        ArgumentCaptor<HistoryItem> captor = ArgumentCaptor.forClass(HistoryItem.class);
        verify(repository, times(1)).save(captor.capture());

        HistoryItem saved = captor.getValue();
        assertEquals("testUser", saved.getUser());
        assertEquals(json, saved.getFileItemsJson());
    }

    @Test
    void testGetAllHistoryItems() throws Exception {
        HistoryItem entity = new HistoryItem("testUser", "[{\"name\":\"file1.txt\"}]");
        entity.setId(1L);
        entity.setCreatedAt(LocalDateTime.now());

        when(repository.findAll()).thenReturn(List.of(entity));

        FileItem item = new FileItem();
        item.setName("file1.txt");

        when(objectMapper.readValue(anyString(), any(TypeReference.class)))
                .thenReturn(List.of(item));

        List<HistoryResponse> result = historyService.getAllHistoryItems();

        assertNotNull(result);
        assertEquals(1, result.size());
        HistoryResponse resp = result.get(0);
        assertEquals(entity.getId(), resp.getId());
        assertEquals(entity.getUser(), resp.getUsername());
        assertEquals(1, resp.getFileItems().size());
        assertEquals("file1.txt", resp.getFileItems().get(0).getName());
    }

    @Test
    void testSaveThrowsRuntimeExceptionOnJsonError() throws JsonProcessingException {
        List<FileItem> items = List.of(new FileItem());
        when(objectMapper.writeValueAsString(items)).thenThrow(JsonProcessingException.class);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> historyService.save(items));
        assertTrue(ex.getMessage().contains("Failed to serialize FileItems"));
    }

    @Test
    void testGetAllHistoryItemsThrowsRuntimeExceptionOnJsonError() throws Exception {
        HistoryItem entity = new HistoryItem("testUser", "[{}]");
        when(repository.findAll()).thenReturn(List.of(entity));
        when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenThrow(JsonProcessingException.class);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> historyService.getAllHistoryItems());
        assertTrue(ex.getMessage().contains("Failed to deserialize FileItems JSON"));
    }
}
