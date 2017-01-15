package bsk.boot.repository;

import bsk.boot.model.TableLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mateusz-PC on 27.04.2016.
 */
public interface TableLabelRepository extends JpaRepository<TableLabel, String> {
    List<TableLabel> findAll();
    TableLabel findOne(String name);
    @Query("SELECT tl from TableLabel t, TableLabel tl where t.name = 'etykiety' and :userLabel >= t.label order by tl.name")
    List<TableLabel> find(@Param("userLabel") Integer userLabel);

    @Query("SELECT tl from TableLabel t, TableLabel tl where t.name = 'etykiety' and :userLabel >= t.label and tl.name = :tableName")
    TableLabel findOnlyOne(@Param("tableName") String tableName, @Param("userLabel") Integer userLabel);

    @Query("SELECT tl.label from TableLabel t, TableLabel tl where t.name = 'etykiety' and :userLabel >= t.label and tl.name = :tableName")
    Integer getLabel(@Param("tableName") String tableName, @Param("userLabel") Integer userLabel);

}
