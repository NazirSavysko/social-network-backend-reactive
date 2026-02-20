package social.network.backend.reactive.service.file;

import reactor.core.publisher.Mono;
import org.springframework.core.io.Resource;

public interface FileService {
    Mono<String> writeToFile(String directoryName, String content);

    Mono<Resource> getFileAsResource(String filePath);

    Mono<Void> deleteFile(final String filePath);
}
