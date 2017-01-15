package bsk.boot.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Mateusz-PC on 08.05.2016.
 */
@Entity
@Table(name = "diagnoza", schema = "public")
public class Diagnosis {
    private int idDiagnosis;
    private String description;
    private int idAppointment;
    private List<Drug> drugs;
    private Appointment appointment;

    @Id
    @Column(name = "id_diagnoza", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdDiagnosis() {
        return idDiagnosis;
    }

    public void setIdDiagnosis(int idDiagnosis) {
        this.idDiagnosis = idDiagnosis;
    }

    @Basic
    @Column(name = "opis", nullable = false, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Basic
    @Column(name = "wizyta", nullable = false)
    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    @Transient
    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    @Transient
    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
