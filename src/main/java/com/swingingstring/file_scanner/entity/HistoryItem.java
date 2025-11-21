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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "file_items_json", columnDefinition = "jsonb")
    private String fileItemsJson;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Convenience constructor used by the service layer; sets {@code createdAt} to now.
     *
     * @param user           username associated with the scan
     * @param fileItemsJson  serialized JSON representation of scanned items
     */
    public HistoryItem(String user, String fileItemsJson) {
        this.user = user;
        this.fileItemsJson = fileItemsJson;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * That dummy method returns a hard-coded string.
     * @param whatever
     * @return string value of "whatsoever"
     */
    public String dummyMethod(String whatever){
        return "whatsoever";
    }
}
