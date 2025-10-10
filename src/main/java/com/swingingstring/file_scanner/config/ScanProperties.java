package com.swingingstring.file_scanner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "search")
public class ScanProperties {

    private String fileExtensionToScan;

    /**
     * Optional file extension filter applied during scanning. When set, only files ending
     * with this extension (case-insensitive, without the leading dot) are included.
     *
     * @return configured file extension to include or {@code null}/empty if no filter
     */
    public String getFileExtensionToScan(){
        return fileExtensionToScan;
    }

    /**
     * Sets the file extension to filter by (without the leading dot). For example, set to
     * "txt" to include only .txt files.
     *
     * @param fileExtensionToScan extension string without leading dot
     */
    public void setFileExtensionToScan(String fileExtensionToScan){
        this.fileExtensionToScan = fileExtensionToScan;
    }
}
