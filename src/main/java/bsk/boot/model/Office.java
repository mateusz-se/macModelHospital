package bsk.boot.model;

import javax.persistence.*;

/**
 * Created by Mateusz-PC on 19.03.2016.
 */
@Entity
@Table(name = "gabinet", schema = "public")
public class Office {
    private int idOffice;
    private int number;
    private int size;

    @Id
    @Column(name = "id_gabinet", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(int idOffice) {
        this.idOffice = idOffice;
    }

    @Basic
    @Column(name = "numer", nullable = false)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "wielkosc", nullable = false)
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
