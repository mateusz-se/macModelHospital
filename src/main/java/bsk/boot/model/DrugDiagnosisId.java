package bsk.boot.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Mateusz-PC on 14.05.2016.
 */
@Embeddable
public class DrugDiagnosisId implements Serializable{
    private int idDiagnosis;
    private int idDrug;

    public DrugDiagnosisId(int idDiagnosis, int idDrug) {
        this.idDiagnosis = idDiagnosis;
        this.idDrug = idDrug;
    }

    public DrugDiagnosisId() {
    }

    @Column(name = "id_diagnoza", nullable = false)
    public int getIdDiagnosis() {
        return idDiagnosis;
    }

    public void setIdDiagnosis(int idDiagnosis) {
        this.idDiagnosis = idDiagnosis;
    }

    @Column(name = "id_lek", nullable = false)
    public int getIdDrug() {
        return idDrug;
    }

    public void setIdDrug(int idDrug) {
        this.idDrug = idDrug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrugDiagnosisId that = (DrugDiagnosisId) o;

        if (idDiagnosis != that.idDiagnosis) return false;
        return idDrug == that.idDrug;

    }

    @Override
    public int hashCode() {
        int result = idDiagnosis;
        result = 31 * result + idDrug;
        return result;
    }
}
