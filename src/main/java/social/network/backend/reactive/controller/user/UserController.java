package social.network.backend.reactive.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.controller.payload.UpdateUserPayload;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.dto.user.UpdateUserDTO;
import social.network.backend.reactive.facade.user.UserProfileFacade;
import social.network.backend.reactive.validator.MonoValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{userId:\\d+}")
public final class UserController {

    private final UserProfileFacade userFacade;
    private final MonoValidator monoValidator;

    @GetMapping
    public Mono<GetUserDTO> getUserId(final @PathVariable Integer userId) {
        return this.userFacade.getUserById(userId);
    }

    @PutMapping("/update")
    public Mono<GetUserDTO> updateUser(final @PathVariable Integer userId,
                                       final @RequestBody Mono<UpdateUserPayload> updateUserPayload) {

        return updateUserPayload
                .transform(this.monoValidator::validate)
                .map(payload -> {
                    return new UpdateUserDTO(
                            userId,
                            payload.name(),
                            payload.surname(),
                            payload.email()
                    );
                })
                .as(this.userFacade::updateUser);


    }

    @DeleteMapping("/delete")
    public Mono<Void> deleteUser(final @PathVariable Integer userId) {
        return this.userFacade.deleteUser(userId);
    }

}