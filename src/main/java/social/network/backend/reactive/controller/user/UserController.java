package social.network.backend.reactive.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import social.network.backend.reactive.dto.user.GetUserDTO;
import social.network.backend.reactive.facade.user.UserProfileFacade;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/users/{userId:\\d+}")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileFacade userFacade;

    @ModelAttribute("user")
    public Mono<GetUserDTO> getUserId(final @PathVariable("userId") Integer userId) {
        return this.userFacade.getUserById(userId);
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<GetUserDTO>> getUser(@PathVariable Integer userId) {
        return userFacade.getUserById(userId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/update")
    @PreAuthorize(value = "hasRole('ADMIN') or #user.email() == principal.username")
    public ResponseEntity<?> updateUser(
            final @ModelAttribute(value = "user", binding = false) GetUserDTO user,
            final @RequestBody UpdateUserPayload updateUserPayload,
            final BindingResult result) {
        final UpdateUserDTO updateUserDTO = new UpdateUserDTO(
                user.id(),
                updateUserPayload.name(),
                updateUserPayload.surname(),
                updateUserPayload.email(),
                updateUserPayload.password()
        );
        final GetUserDTO updatedUser = this.userFacade.updateUser(updateUserDTO, result);

        return ok(updatedUser);
    }

    @DeleteMapping("/delete")
    @PreAuthorize(value = "hasRole('ADMIN') or #user.email() == principal.username")
    public ResponseEntity<?> deleteUser(final @ModelAttribute(value = "user", binding = false) GetUserDTO user) {
        this.userFacade.deleteUser(user.id());

        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Map.of("message", "User with id >" + user.id() + "< has been deleted"));
    }

}