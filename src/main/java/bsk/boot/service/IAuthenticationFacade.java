package bsk.boot.service;

import org.springframework.security.core.Authentication;

/**
 * Created by Mateusz-PC on 06.01.2017.
 */
public interface IAuthenticationFacade {

    Authentication getAuthentication();

    int getUserAccessLabel();

}
