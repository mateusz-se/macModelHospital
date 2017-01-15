package bsk.boot.model;

import javax.persistence.*;

/**
 * Created by Mateusz-PC on 08.05.2016.
 */
@Entity
@Table(name = "lek", schema = "public")
public class Drug {
    private int idDrug;
    private String name;
    private String description;

    @Id
    @Column(name = "id_lek", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdDrug() {
        return idDrug;
    }

    public void setIdDrug(int idDrug) {
        this.idDrug = idDrug;
    }

    @Basic
    @Column(name = "nazwa", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "opis", nullable = false, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
