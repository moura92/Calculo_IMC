package com.moura.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {

    private String upload_Dir;

    public FileStorageConfig() {}

    public String getUpload_Dir() {
        return upload_Dir;
    }

    public void setUpload_Dir(String upload_Dir) {
        this.upload_Dir = upload_Dir;
    }
}
