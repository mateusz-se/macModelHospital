package bsk.boot.repository;

import bsk.boot.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mateusz-PC on 19.03.2016.
 */
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    void delete(Patient deleted);

    List<Patient> findAll();

    @Query("SELECT p from Patient p, TableLabel t, TableLabel tl where t.name = 'pacjent' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel order by p.lastName")
    List<Patient> find(@Param("userLabel") Integer userLabel);

    @Query("SELECT p from Patient p, TableLabel t, TableLabel tl where t.name = 'pacjent' and :userLabel >= t.label and p.idPatient = :idPatient and tl.name = 'etykiety' and tl.label <= :userLabel")
    Patient findOnlyOne(@Param("idPatient") Integer idPatient, @Param("userLabel") Integer userLabel);

    Patient findOne(Integer id);

}
