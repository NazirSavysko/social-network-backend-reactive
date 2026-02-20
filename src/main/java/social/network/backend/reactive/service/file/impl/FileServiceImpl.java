package social.network.backend.reactive.service.file.impl;

import lombok.val;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import social.network.backend.reactive.service.file.FileService;

import static java.lang.System.getenv;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;
import static java.util.Base64.getDecoder;
import static reactor.core.scheduler.Schedulers.boundedElastic;

@Service
public final class FileServiceImpl implements FileService {

    private static final String THE_SOURCE_DIRECTORY = getenv().getOrDefault("APP_DATA_DIR", "data");

    @Override
    public Mono<String> writeToFile(final String directoryName, final String content) {
        return Mono.fromCallable(() -> {
            String cleanBase64 = content;
            String extension = "jpg";

            if (content.contains(",")) {
                val parts = content.split(",");
                val mimeType = parts[0].split(";")[0];
                val slashIndex = mimeType.indexOf('/');
                if (slashIndex != -1) {
                    extension = mimeType.substring(slashIndex + 1);
                }
                cleanBase64 = parts[1];
            }

            val data = getDecoder().decode(cleanBase64);

            val dirPath = get(THE_SOURCE_DIRECTORY, directoryName);
            if (!exists(dirPath)) {
                createDirectories(dirPath);
            }

            val fileName = generateFileName() + "." + extension;
            val filePath = dirPath.resolve(fileName);

            write(filePath, data);

            return directoryName + "/" + fileName;
        }).subscribeOn(boundedElastic());
    }

    public Mono<Resource> getFileAsResource(final String filePath) {
        return Mono.fromCallable(() -> {
            val path = get(THE_SOURCE_DIRECTORY, filePath);
            final Resource resource = new FileSystemResource(path);

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + filePath);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
    @Override
    public Mono<Void> deleteFile(final String filePath) {
        return Mono.fromRunnable(() -> {
            try {
                deleteIfExists(get(THE_SOURCE_DIRECTORY, filePath));
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete file", e);
            }
        }).subscribeOn(boundedElastic()).then();
    }

    private String generateFileName() {
        return System.currentTimeMillis() + "_" + java.util.UUID.randomUUID();
    }
}