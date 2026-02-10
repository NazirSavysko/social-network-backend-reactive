package social.network.backend.reactive.service.auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import social.network.backend.reactive.model.User;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static com.nimbusds.jose.JWSAlgorithm.HS256;

@Service
public final class JwtGeneratorServiceImpl implements JwtGeneratorService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationSeconds;

    public String generateToken(final User user) {
        try {
            val header = new JWSHeader(HS256);

            val claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .claim("preferred_username", user.getEmail())
                    .claim("roles", List.of(user.getRole().name()))
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plusSeconds(expirationSeconds)))
                    .build();

            val signedJWT = new SignedJWT(header, claimsSet);

            val signer = new MACSigner(secretKey.getBytes());
            signedJWT.sign(signer);

            return signedJWT.serialize();

        } catch (final JOSEException e) {
            throw new RuntimeException("Error generating JWT", e);
        }
    }
}