package social.network.backend.reactive.service.auth;

import social.network.backend.reactive.model.User;

import java.util.List;

public interface JwtGeneratorService {
    String generateToken(User user);
}
