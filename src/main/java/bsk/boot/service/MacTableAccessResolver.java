package bsk.boot.service;

import bsk.boot.repository.TableLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mateusz-PC on 06.01.2017.
 */
@Component
public class MacTableAccessResolver implements TableAccessResolver {

    @Autowired
    private TableLabelRepository tableLabelRepository;

    @Value("${table.label}")
    private String labelTableName;

    @Override
    public boolean hasSaveAccess(int userAccessLabel, String... tableNames) {

        List<String> tableNameList = Arrays.asList(tableNames);

        boolean labelAccess = tableNameList.stream()
                .filter( name -> labelTableName.equals(name))
                .map( name -> tableLabelRepository.findOne(name))
                .anyMatch(tableLabel -> userAccessLabel >= tableLabel.getLabel());

        boolean otherAccess = tableNameList.stream()
                .filter( name -> !labelTableName.equals(name))
                .map( name -> tableLabelRepository.findOne(name))
                .allMatch(tableLabel -> userAccessLabel <= tableLabel.getLabel());

        return labelAccess && otherAccess;
    }
}
