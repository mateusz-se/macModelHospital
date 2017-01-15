package bsk.boot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Mateusz-PC on 19.03.2016.
 */
@Entity
@Table(name = "pacjent", schema = "public")
public class Patient {
    private int idPatient;
    private String pesel;
    private String name;
    private String lastName;

    @JsonFormat(pattern="dd.MM.yyyy")
    private Date birthDay;

    private String gender;
    private String phone;
    private List<Appointment> appointments;

    public Patient() {
    }

    @Id
    @Column(name = "id_pacjent", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    @Basic
    @Column(name = "pesel", nullable = false, length = 11)
    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Basic
    @Column(name = "imie", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "nazwisko", nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "data_urodzenia", nullable = false)
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Basic
    @Column(name = "plec", nullable = false, length = 20)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "telefon", nullable = false, length = 10)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
