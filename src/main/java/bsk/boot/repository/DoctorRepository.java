package bsk.boot.repository;

import bsk.boot.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mateusz-PC on 19.03.2016.
 */
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    void delete(Doctor deleted);

    List<Doctor> findAll();

    @Query("SELECT d from Doctor d, TableLabel t, TableLabel tl where t.name = 'lekarz' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel")
    List<Doctor> find(@Param("userLabel") Integer userLabel);

    @Query("SELECT d from Doctor d, TableLabel t, TableLabel tl where t.name = 'wizyta' and :userLabel >= t.label and d.idDoctor = :idDoctor and tl.name = 'etykiety' and tl.label <= :userLabel")
    Doctor findOnlyOne(@Param("idDoctor") Integer idDoctor, @Param("userLabel") Integer userLabel);

    Doctor findOne(Integer id);


}
