package bsk.boot.controller;

import bsk.boot.service.AuthenticationFacade;
import bsk.boot.service.ResponseBuilder;
import bsk.boot.service.TableAccessResolver;

import bsk.boot.model.User;
import bsk.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/doctors")
class DoctorController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableAccessResolver tableAccessResolver;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Value("${table.label}")
    private String labelTableName;

    @Value("${table.users}")
    private String usersTableName;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getDoctors(@RequestParam(value = "isDoctor", required = false) boolean isDoctor) {
        Integer level = authenticationFacade.getUserAccessLabel();
        List<User> users;
        if (isDoctor) {
            users = userRepository.findDoctors(level);
        } else {
            users = userRepository.find(level);
        }

        return responseBuilder.build(users);
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public String details(@PathVariable("Id") Integer doctorId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        User doctor = userRepository.findOnlyOne(doctorId, userLabel);
        return responseBuilder.build(doctor);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Integer userId, @RequestBody User user) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, usersTableName);
        if (hasSaveAccess){
            User userBeforeEdition = userRepository.findOne(userId);
            if(!user.getPassword().equals(userBeforeEdition.getPassword())) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encoded = encoder.encode(user.getPassword());
                user.setPassword(encoded);
            }
            try {
                userRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(user, HttpStatus.CONFLICT);
            }
        }else {
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, usersTableName);
        if (hasSaveAccess){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encoded = encoder.encode(user.getPassword());
            user.setPassword(encoded);
            try {
                userRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } catch (Exception ex){
                return new ResponseEntity<>(user, HttpStatus.CONFLICT);
            }
        }else {
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, usersTableName);
        if (hasSaveAccess){
            try {
                userRepository.delete(userId);
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }
            catch (Exception ex) {
                return new ResponseEntity<>("ERROR", HttpStatus.CONFLICT);
            }
        }else {
            return new ResponseEntity<>("ERROR", HttpStatus.FORBIDDEN);
        }
    }
}
