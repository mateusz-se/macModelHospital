package bsk.boot.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Mateusz-PC on 23.04.2016.
 */
@Entity
@Table(name = "uzytkownicy", schema = "public")
public class User{
    private int userId;
    private String login;
    private String password;
    private String name;
    private String lastName;
    private String position;
    private int isDoctor;
    private int label;

    public User() {
    }

    @Id
    @Column(name = "id_uzytkownika", nullable= false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    @Basic
    @Column(name = "login", nullable = false)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    @Basic
    @Column(name = "pass", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Basic
    @Column(name = "imie", nullable = false)
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    @Basic
    @Column(name = "nazwisko", nullable = false)
    public String getLastName(){
        return lastName;
    }

    public void setLastName(String surname){
        this.lastName = surname;
    }
    @Basic
    @Column(name = "stanowisko", nullable = false)
    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }
    @Basic
    @Column(name = "lekarz", nullable = false)
    public int getIsDoctor(){
        return isDoctor;
    }

    public void setIsDoctor(int isDoctor){
        this.isDoctor = isDoctor;
    }
    @Basic
    @Column(name = "etykieta", nullable = false)
    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }


}
