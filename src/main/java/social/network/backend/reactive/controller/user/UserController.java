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

    @ModelAttribute("user")
    public Mono<GetUserDTO> getUserId(@PathVariable final Integer userId) {
        return this.userFacade.getUserById(userId);
    }

    @GetMapping("/")
    public Mono<GetUserDTO> getUser(final @ModelAttribute("user") Mono<GetUserDTO> user) {
        return user;
    }

    @PutMapping("/update")
    public Mono<GetUserDTO> updateUser(
            final @ModelAttribute("user") Mono<GetUserDTO> user,
            final @RequestBody Mono<UpdateUserPayload> updateUserPayload) {

        return updateUserPayload
                .transform(this.monoValidator::validate)
                .zipWith(user, (payload, userDTO) -> {
                    return new UpdateUserDTO(
                            userDTO.id(),
                            payload.name(),
                            payload.surname(),
                            payload.email()
                    );
                })
                .as(this.userFacade::updateUser);


    }

    @DeleteMapping("/delete")
    public Mono<Void> deleteUser(final @ModelAttribute("user") Mono<GetUserDTO> user) {
      return user
              .flatMap(userFacade::deleteUser);

    }

}