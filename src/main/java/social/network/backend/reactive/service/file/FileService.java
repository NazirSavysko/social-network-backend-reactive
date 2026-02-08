package social.network.backend.reactive.service.file;

import reactor.core.publisher.Mono;

public interface FileService {
    Mono<String> writeToFile(String directoryName, String content);

    Mono<String> getContentFromFile(String filePath);

    Mono<Void> deleteFile(final String filePath);
}
