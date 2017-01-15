package bsk.boot.service;

import bsk.boot.model.Authorities;
import bsk.boot.model.User;
import bsk.boot.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MyUserDetailService() {
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = userRepository.findByLogin(s);
        List<Authorities> a = new ArrayList<>();
        a.add(new Authorities(u.getLabel()));
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(s, u.getPassword(), a );
        return user;
    }
}
