package bsk.boot.controller;

import bsk.boot.model.TableLabel;
import bsk.boot.service.AuthenticationFacade;
import bsk.boot.service.ResponseBuilder;
import bsk.boot.repository.TableLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Mateusz-PC on 27.04.2016.
 */
@Controller
@RequestMapping("/api/tables")
class TableLabelController {

    @Autowired
    private TableLabelRepository tableLabelRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Value("${table.label}")
    private String labelTableName;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getTables() {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        return responseBuilder.build(tableLabelRepository.find(userLabel));
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String getTable(@PathVariable("name") String tableName) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        return responseBuilder.build(tableLabelRepository.findOnlyOne(tableName, userLabel));
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public ResponseEntity<TableLabel> updateTable(@PathVariable("name") String name, @RequestBody TableLabel tableLabel) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        TableLabel tl = tableLabelRepository.findOne(labelTableName);
        if (userLabel <=  tl.getLabel()){
            tableLabelRepository.save(tableLabel);
            return new ResponseEntity<>(tableLabel, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(tableLabel, HttpStatus.FORBIDDEN);
        }


    }

}

