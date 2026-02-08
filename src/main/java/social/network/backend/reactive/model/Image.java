package social.network.backend.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class Image {
    @Id
    private Integer id;

    private String filePath;
}