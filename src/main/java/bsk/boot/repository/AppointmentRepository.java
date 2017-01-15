package bsk.boot.repository;

import bsk.boot.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mateusz-PC on 19.03.2016.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    void delete(Appointment deleted);

    List<Appointment> findAll();

    Appointment findOne(Integer id);

    @Query("SELECT a from Appointment a, TableLabel t, TableLabel tl where t.name = 'wizyta' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel order by a.idAppointment")
    List<Appointment> find(@Param("userLabel") Integer userLabel);

    @Query("SELECT a from Appointment a, TableLabel t, TableLabel tl where t.name = 'wizyta' and :userLabel >= t.label and a.idAppointment = :idAppointment and tl.name = 'etykiety' and tl.label <= :userLabel")
    Appointment findOnlyOne(@Param("idAppointment") Integer idAppointment, @Param("userLabel") Integer userLabel);

    @Query("SELECT a from Appointment a, TableLabel t, TableLabel tl where t.name = 'wizyta' and :userLabel >= t.label and a.idPatient = :idPatient and tl.name = 'etykiety' and tl.label <= :userLabel order by a.idAppointment")
    List<Appointment> findByIdPatient(@Param("idPatient") Integer idPatient, @Param("userLabel") Integer userLabel);
}
