package bsk.boot.repository;

import bsk.boot.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mateusz-PC on 19.03.2016.
 */
public interface OfficeRepository extends JpaRepository<Office, Integer> {
    void delete(Office deleted);

    List<Office> findAll();

    @Query("SELECT o from Office o, TableLabel t, TableLabel tl where t.name = 'gabinet' and :userLabel >= t.label and tl.name = 'etykiety' and tl.label <= :userLabel order by o.number")
    List<Office> find(@Param("userLabel") Integer userLabel);

    @Query("SELECT o from Office o, TableLabel t, TableLabel tl where t.name = 'gabinet' and :userLabel >= t.label and o.idOffice = :idOffice and tl.name = 'etykiety' and tl.label <= :userLabel")
    Office findOnlyOne(@Param("idOffice") Integer idOffice, @Param("userLabel") Integer userLabel);

    Office findOne(Integer id);

}
