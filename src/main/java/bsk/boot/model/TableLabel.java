package bsk.boot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Mateusz-PC on 24.04.2016.
 */
@Entity
@Table(name = "etykiety", schema = "public")
public class TableLabel {
    private String name;
    private int label;

    @Id
    @Column(name = "tabela", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "etykieta", nullable = false)
    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
