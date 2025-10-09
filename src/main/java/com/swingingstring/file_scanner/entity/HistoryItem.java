package com.swingingstring.file_scanner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
public class HistoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "username")
    private String user;

    @Column(nullable = false, columnDefinition = "jsonb")
    private String fileItemsJson;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public HistoryItem(String user, String fileItemsJson) {
        this.user = user;
        this.fileItemsJson = fileItemsJson;
        this.createdAt = LocalDateTime.now();
    }
}
