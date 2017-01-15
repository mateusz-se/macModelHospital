package bsk.boot.model;

import javax.persistence.*;

/**
 * Created by Mateusz-PC on 14.05.2016.
 */
@Entity
@Table(name = "przepisane_leki", schema = "public")
public class DrugDiagnosis {

    private DrugDiagnosisId drugDiagnosisId;
    private Diagnosis diagnosis;
    private Drug drug;

    public DrugDiagnosis() {
    }

    public DrugDiagnosis(int idDiagnosis, int idDrug) {
        this.drugDiagnosisId = new DrugDiagnosisId(idDiagnosis,idDrug);
    }

    @EmbeddedId
    public DrugDiagnosisId getDrugDiagnosisId() {
        return drugDiagnosisId;
    }

    public void setDrugDiagnosisId(DrugDiagnosisId drugDiagnosisId) {
        this.drugDiagnosisId = drugDiagnosisId;
    }

    @Transient
    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Transient
    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrugDiagnosis that = (DrugDiagnosis) o;

        return drugDiagnosisId.equals(that.drugDiagnosisId);

    }

    @Override
    public int hashCode() {
        return drugDiagnosisId.hashCode();
    }
}
