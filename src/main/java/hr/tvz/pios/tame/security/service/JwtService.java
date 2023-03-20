package hr.tvz.pios.tame.security.service;

import hr.tvz.pios.tame.security.domain.User;

public interface JwtService {

    boolean authenticate(String token);

    String createJwt(User user);

}
