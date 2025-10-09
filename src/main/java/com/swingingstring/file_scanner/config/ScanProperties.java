package com.swingingstring.file_scanner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "search")
public class ScanProperties {

    private String fileExtensionToScan;

    public String getFileExtensionToScan(){
        return fileExtensionToScan;
    }

    public void setFileExtensionToScan(String fileExtensionToScan){
        this.fileExtensionToScan = fileExtensionToScan;
    }
}
