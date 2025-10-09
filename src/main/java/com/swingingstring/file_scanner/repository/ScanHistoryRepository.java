package com.swingingstring.file_scanner.repository;

import com.swingingstring.file_scanner.entity.HistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScanHistoryRepository extends JpaRepository<HistoryItem, Long> {
}