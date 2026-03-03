package com.moura.controllers;

import com.moura.controllers.docs.FileControllersDocs;
import com.moura.dto.UploadFileResponseDTO;
import com.moura.services.FileStorageService;
import com.moura.services.UsuarioServices;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file/v1")
public class FileControllers implements FileControllersDocs {

    private static final Logger logger = LoggerFactory.getLogger(FileControllers.class);

    @Autowired
    private FileStorageService service;

    //UPLOAD de arquivo:
    @PostMapping("/uploadFile")
    @Override
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) { // recebe o multipart atraves da request
        var fileName = service.storeFile(file); //retorna o nome do arquivo tratado

        // htpp://localhost:8080/api/file/v1/downloadFile/fileName.docx
        var fileDownload = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/downloadFile/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponseDTO(fileName, fileDownload, file.getContentType(), file.getSize());
    }

    //UPLOAD de varios arquivos:
    @PostMapping("/uploadMultiplefiles")
    @Override
    public List<UploadFileResponseDTO> uploadMultiplefiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    @Override
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = service.loadFileAsResource(fileName); // ler o arquivo em disco
        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception e){
            logger.error("Não é poddivel determinar o tipo de arquivo");
        }
        if(contentType == null){
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment=; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
