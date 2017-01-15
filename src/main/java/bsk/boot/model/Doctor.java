package bsk.boot.model;

import javax.persistence.*;

/**
 * Created by Mateusz-PC on 19.03.2016.
 */
@Entity
@Table(name = "lekarz", schema = "public")
public class Doctor {
    private int idDoctor;
    private String name;
    private String lastName;
    private String specialization;

    @Id
    @Column(name = "id_lekarz", nullable = false)
    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
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
    @Column(name = "specjalizacja", nullable = false, length = 50)
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

}
