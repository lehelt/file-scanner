package com.swingingstring.file_scanner.repository;

import com.swingingstring.file_scanner.entity.HistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Spring Data repository for persisting and retrieving {@link HistoryItem} entities.
 */
public interface ScanHistoryRepository extends JpaRepository<HistoryItem, Long> {
}