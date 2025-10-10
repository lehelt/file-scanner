package com.swingingstring.file_scanner.service;

import com.swingingstring.file_scanner.config.ScanProperties;
import com.swingingstring.file_scanner.model.FileItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileScannerServiceTest {

    private FileScannerService fileScannerService;
    private ScanProperties properties;

    @BeforeEach
    void setUp() {
        properties = mock(ScanProperties.class);
        fileScannerService = new FileScannerService(properties);
    }

    @Test
    void testSearchWithValidDirectoryAndMatchingFiles() throws Exception {
        when(properties.getFileExtensionToScan()).thenReturn("txt");

        // Temporary test directory + files
        File tempDir = Files.createTempDirectory("testDir").toFile();
        tempDir.deleteOnExit();
        File file1 = new File(tempDir, "file1.txt");
        File file2 = new File(tempDir, "file2.csv");
        file1.createNewFile();
        file2.createNewFile();
        file1.deleteOnExit();
        file2.deleteOnExit();

        List<FileItem> result = fileScannerService.search(tempDir.getAbsolutePath());

        // Csak a file1.txt szerepel, file2.csv nem
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("file1.txt", result.get(0).getName());
        assertFalse(result.get(0).isDirectory());
    }

    @Test
    void testSearchWithNonExistingDirectory() {
        String invalidPath = "non/existing/path";

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fileScannerService.search(invalidPath)
        );

        assertTrue(exception.getMessage().contains("Invalid directory"));
    }

    @Test
    void testSearchWithDirectoryContainingSubdirectory() throws Exception {
        when(properties.getFileExtensionToScan()).thenReturn("txt");

        File tempDir = Files.createTempDirectory("parentDir").toFile();
        tempDir.deleteOnExit();
        File subDir = new File(tempDir, "sub");
        subDir.mkdir();
        subDir.deleteOnExit();

        File file1 = new File(subDir, "a.txt");
        File file2 = new File(subDir, "b.csv");
        file1.createNewFile();
        file2.createNewFile();
        file1.deleteOnExit();
        file2.deleteOnExit();

        List<FileItem> result = fileScannerService.search(tempDir.getAbsolutePath());

        assertNotNull(result);
        assertEquals(1, result.size());
        FileItem dirItem = result.get(0);
        assertTrue(dirItem.isDirectory());
        assertEquals("sub", dirItem.getName());
        assertEquals(1, dirItem.getChildren().size());
        assertEquals("a.txt", dirItem.getChildren().get(0).getName());
    }
}
