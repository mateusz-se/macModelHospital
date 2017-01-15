package bsk.boot.repository;

import bsk.boot.model.DrugDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mateusz-PC on 27.04.2016.
 */
public interface DrugDiagnosisRepository extends JpaRepository<DrugDiagnosis, String> {
    List<DrugDiagnosis> findAll();
    DrugDiagnosis findOne(String name);
    @Query("SELECT dd from DrugDiagnosis dd, TableLabel t, TableLabel tl where t.name = 'przepisane_leki' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel")
    List<DrugDiagnosis> find(@Param("userLabel") Integer userLabel);

    @Query("SELECT dd from DrugDiagnosis dd, TableLabel t, TableLabel tl where t.name = 'przepisane_leki' and :userLabel >= t.label and dd.drugDiagnosisId.idDiagnosis = :idDiagnosis and tl.name = 'etykiety' and tl.label <= :userLabel")
    List<DrugDiagnosis> findByDiagnosisId(@Param("idDiagnosis") Integer idDiagnosis, @Param("userLabel") Integer userLabel);
}
