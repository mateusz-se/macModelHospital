package bsk.boot.service;

import bsk.boot.model.User;
import bsk.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by Mateusz-PC on 06.01.2017.
 */
@Component
public class AuthenticationFacade implements IAuthenticationFacade{
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public int getUserAccessLabel() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userRepository.findByLogin(userName);
        return u.getLabel();
    }

}
