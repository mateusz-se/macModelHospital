package bsk.boot.repository;

import bsk.boot.model.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mateusz-PC on 08.05.2016.
 */
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer>{
    List<Diagnosis> findAll();

    Diagnosis findOne(Integer idDiagnosis);

    @Query("SELECT d from Diagnosis d, TableLabel t, TableLabel tl where t.name = 'diagnoza' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel order by d.idDiagnosis")
    List<Diagnosis> findAll(@Param("userLabel") Integer userLabel);


    @Query("SELECT d from Diagnosis d, TableLabel t, TableLabel tl where t.name = 'diagnoza' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel and d.idAppointment = :appointmentId order by d.idDiagnosis")
    List<Diagnosis> find(@Param("userLabel") Integer userLabel, @Param("appointmentId") Integer appointmentId);

    @Query("SELECT d from Diagnosis d, TableLabel t, TableLabel tl where t.name = 'diagnoza' and :userLabel >= t.label and d.idDiagnosis = :idDiagnosis and tl.name = 'etykiety' and tl.label <= :userLabel")
    Diagnosis findOnlyOne(@Param("idDiagnosis") Integer idDiagnosis, @Param("userLabel") Integer userLabel);
}
