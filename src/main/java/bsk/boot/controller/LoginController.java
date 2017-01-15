package bsk.boot.controller;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;


@Controller
@RequestMapping("/api")
class LoginController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

}
