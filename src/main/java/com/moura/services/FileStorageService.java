package com.moura.services;

import com.moura.config.FileStorageConfig;
import com.moura.controllers.FileControllers;
import com.moura.exception.FileNotFoundException;
import com.moura.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUpload_Dir())
                .toAbsolutePath()
                .toAbsolutePath()
                .normalize();

        this.fileStorageLocation = path;
        try {
            logger.info("Criando diretorio");
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error("Não foi possivel criar o diretorio onde os arquivos serão armazenados!");
            throw new FileStorageException("Não foi possivel criar o diretorio onde os arquivos serão armazenados!", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                logger.error("Desculpe!, o caminho do arquivo contem um sequencia de caminho invalida!" + fileName);
                throw new FileStorageException("Desculpe!, o caminho do arquivo contem um sequencia de caminho invalida!" + fileName);
            }

            logger.info("Savando diretorio em disco");
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (Exception e) {
            logger.error("Não foi possivel armazenar o arquivo " + fileName + ". Tente novamente!");
            throw new FileStorageException("Não foi possivel armazenar o arquivo " + fileName + ". Tente novamente!", e);
        }
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                logger.error("Arquivo não encontrado" + filename);
                throw new FileNotFoundException("Arquivo não encontrado" + filename);
            }
        } catch (Exception e) {
            logger.error("Arquivo não encontrado" + filename);
            throw new FileNotFoundException("Arquivo não encontrado" + filename, e);
        }
    }
}
