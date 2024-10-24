package org.library.userback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.library.userback.entity.AppUser;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationResponse {
    AppUser user;
    String token;
}
