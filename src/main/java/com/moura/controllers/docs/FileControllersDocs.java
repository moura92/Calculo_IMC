package com.moura.controllers.docs;

import com.moura.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File Endpoint")
public interface FileControllersDocs {

    UploadFileResponseDTO uploadFile(MultipartFile file);
    List<UploadFileResponseDTO> uploadMultiplefiles(MultipartFile[] files);
    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
}
