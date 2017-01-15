package bsk.boot.repository;

import bsk.boot.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mateusz-PC on 08.05.2016.
 */
public interface DrugRepository extends JpaRepository<Drug, Integer>{
    List<Drug> findAll();

    Drug findOne(Integer idDrug);

    @Query("SELECT d from Drug d, TableLabel t, TableLabel tl where t.name = 'lek' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel order by d.name")
    List<Drug> find(@Param("userLabel") Integer userLabel);

    @Query("SELECT d from Drug d, TableLabel t, TableLabel tl where t.name = 'lek' and :userLabel >= t.label and d.idDrug = :idDrug and tl.name = 'etykiety' and tl.label <= :userLabel")
    Drug findOnlyOne(@Param("idDrug") Integer idDrug, @Param("userLabel") Integer userLabel);

    @Query("SELECT d from Drug d, TableLabel t, TableLabel tl, TableLabel ddLable, DrugDiagnosis dd where t.name = 'lek' and :userLabel >= t.label " +
            "and tl.name = 'etykiety' and tl.label <= :userLabel and ddLable.label <= :userLabel and ddLable.name = 'przepisane_leki' " +
            "and dd.drugDiagnosisId.idDiagnosis = :idDiagnosis and d.idDrug = dd.drugDiagnosisId.idDrug order by d.name")
    List<Drug> findDrugsForDiagnosis(@Param("userLabel") Integer userLabel, @Param("idDiagnosis") Integer idDiagnosis);
}
