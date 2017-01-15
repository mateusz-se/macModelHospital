package bsk.boot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;
import java.util.List;

/**
 * Created by Mateusz-PC on 19.03.2016.
 */
@Entity
@Table(name = "wizyta", schema = "public")
public class Appointment {
    private int idAppointment;

    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss", timezone = "CET")
    private Date date;

    private String status;

    private Office office;
    private Patient patient;
    private User doctor;

    private int idOffice;
    private int idDoctor;
    private int idPatient;

    @Id
    @Column(name = "id_wizyta", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    @Basic
    @Column(name = "data_wizyty", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "status_wizyty", nullable = false, length = 20)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Basic
    @Column(name = "GABINET", nullable = false)
    public int getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(int idOffice) {
        this.idOffice = idOffice;
    }

    @Basic
    @Column(name = "lekarz", nullable = false)
    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    @Basic
    @Column(name = "pacjent", nullable = false)
    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient){
        this.idPatient = idPatient;
    }

    @Transient
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
    @Transient
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Transient
    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

}
