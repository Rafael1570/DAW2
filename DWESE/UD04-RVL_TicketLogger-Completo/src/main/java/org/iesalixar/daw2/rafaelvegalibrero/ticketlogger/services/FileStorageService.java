package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services;

import java.io.IOException;
import java.nio.file.*;
import java.util.Locale;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.*;
import org.springframework.web.multipart.MultipartFile;

/** Almacena archivos en <app.upload-root>/uploads y devuelve su ruta web. */
@Service
public class FileStorageService {
    private static final Logger logger=LoggerFactory.getLogger(FileStorageService.class);
    private final Path uploadRoot;
    public FileStorageService(@Value("${app.upload-root}") String root) {
        this.uploadRoot=Path.of(root).toAbsolutePath().normalize();
    }
    public String saveFile(MultipartFile file) {
        if(file==null || file.isEmpty()) return null;
        try {
            String extension=getFileExtension(file.getOriginalFilename());
            String name=UUID.randomUUID()+(extension.isBlank()?"":"."+extension);
            Path dir=uploadRoot.resolve("uploads");
            Files.createDirectories(dir);
            Files.write(dir.resolve(name),file.getBytes());
            return "/uploads/"+name;
        } catch(IOException exception) {
            logger.error("No se pudo guardar el archivo",exception);
            return null;
        }
    }
    public void deleteFile(String filePath) {
        if(filePath==null || filePath.isBlank()) return;
        String name=normalizeFileName(filePath);
        if(!name.matches("[0-9a-f-]{36}\\.[a-zA-Z0-9]+")) return;
        try {Files.deleteIfExists(uploadRoot.resolve("uploads").resolve(name));}
        catch(IOException exception) {logger.warn("No se pudo eliminar el archivo {}",name,exception);}
    }
    private String getFileExtension(String fileName) {
        if(fileName==null) return "";
        int dot=fileName.lastIndexOf('.');
        return dot<0?"":fileName.substring(dot+1).toLowerCase(Locale.ROOT);
    }
    private String normalizeFileName(String path) {
        String value=path.replace('\\','/');
        return value.substring(value.lastIndexOf('/')+1);
    }
    public void removeAfterCommit(String path) {
        if(TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override public void afterCommit() {deleteFile(path);}
            });
        } else {deleteFile(path);}
    }
}
