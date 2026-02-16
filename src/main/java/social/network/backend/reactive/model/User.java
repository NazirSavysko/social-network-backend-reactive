package social.network.backend.reactive.model;


import lombok.*;
import social.network.backend.reactive.model.enums.Role;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



@Table(name = "social_user", schema = "social_network")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class User {

    @Id
    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private Role role;

}
