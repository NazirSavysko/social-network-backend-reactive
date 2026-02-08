package social.network.backend.reactive.service.file.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import social.network.backend.reactive.service.file.FileService;

import java.nio.file.Path;

import static java.lang.String.format;
import static java.lang.System.getenv;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static reactor.core.scheduler.Schedulers.boundedElastic;

@Service
public final class FileServiceImpl implements FileService {
    private static final String THE_SOURCE_DIRECTORY = getenv().getOrDefault("APP_DATA_DIR", "data");
    private static final String SUFFIX = ".txt";
    private static final String FORMAT_IMAGE_IN_BASE64 = "data:image/%s;base64,%s";

    @Override
    public Mono<String> writeToFile(final String directoryName, final String content) {

        return Mono.fromCallable(() -> {
            final String cleanBase64 = content.contains(",") ? content.split(",")[1] : content;

            final byte[] data = getDecoder().decode(cleanBase64);

            Path dirPath = get(THE_SOURCE_DIRECTORY, directoryName);
            if (!exists(dirPath)) {
                createDirectories(dirPath);
            }

            final Path tempFile = createTempFile(dirPath, generateFileName(), SUFFIX);

            write(tempFile, data);

            return tempFile.toAbsolutePath().toString();
        }).subscribeOn(boundedElastic());
    }

    @Override
    public Mono<String> getContentFromFile(final String filePath) {
        return Mono.fromCallable(() -> {
            final Path path = get(filePath);
            final byte[] bytes = readAllBytes(path);
            final String suffix = path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf(".") + 1);

            final String base64 = getEncoder().encodeToString(bytes);

            return format(FORMAT_IMAGE_IN_BASE64, suffix, base64);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> deleteFile(final String filePath) {
        return Mono.fromCallable(() -> {
                    deleteIfExists(get(filePath));

                    return null;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

    private String generateFileName() {
        return System.currentTimeMillis() + "_" + java.util.UUID.randomUUID();
    }
}
