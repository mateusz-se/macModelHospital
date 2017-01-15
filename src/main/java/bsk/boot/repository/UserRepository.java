package bsk.boot.repository;

import bsk.boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mateusz-PC on 23.04.2016.
 */
@Service
public interface UserRepository extends JpaRepository<User, Integer> {
    User findOne(Integer userId);

    User findByLogin(String login);

    @Query("SELECT u from User u, TableLabel t, TableLabel tl where t.name = 'uzytkownicy' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel order by u.login")
    List<User> find(@Param("userLabel") Integer userLabel);

    @Query("SELECT u from User u, TableLabel t, TableLabel tl where t.name = 'uzytkownicy' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel and u.isDoctor = 1 order by u.login")
    List<User> findDoctors(@Param("userLabel") Integer userLabel);

    @Query("SELECT u from User u, TableLabel t, TableLabel tl where t.name = 'uzytkownicy' and :userLabel >= t.label and u.userId = :userId and tl.name = 'etykiety' and tl.label <= :userLabel")
    User findOnlyOne(@Param("userId") Integer userId, @Param("userLabel") Integer userLabel);

    List<User> findAll();
}
