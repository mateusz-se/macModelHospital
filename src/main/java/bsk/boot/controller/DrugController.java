package bsk.boot.controller;

import bsk.boot.model.Drug;
import bsk.boot.repository.DrugRepository;
import bsk.boot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Mateusz-PC on 09.05.2016.
 */
@Controller
@RequestMapping("/api/drugs")
class DrugController {

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private TableAccessResolver tableAccessResolver;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Value("${table.label}")
    private String labelTableName;

    @Value("${table.drug}")
    private String drugTableName;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getDrugs() {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        List<Drug> drugs = drugRepository.find(userLabel);
        return responseBuilder.build(drugs);
    }
    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public String getDrug(@PathVariable("Id") Integer drugId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        return responseBuilder.build(drugRepository.findOnlyOne(drugId, userLabel));
    }

    @RequestMapping(value = "/{drugId}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Drug> updateDrug(@PathVariable("drugId") Integer drugId, @RequestBody Drug drug) {
        return saveDrug(drug);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Drug> addDrug(@RequestBody Drug drug) {
        return saveDrug(drug);
    }

    private ResponseEntity<Drug> saveDrug(@RequestBody Drug drug) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, drugTableName);
        if (hasSaveAccess){
            try {
                drugRepository.save(drug);
                return new ResponseEntity<>(drug, HttpStatus.OK);
            }catch(Exception ex){
                return new ResponseEntity<>(drug, HttpStatus.CONFLICT);
            }
        }else {
            return new ResponseEntity<>(drug, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{drugId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDrug(@PathVariable("drugId") Integer drugId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, drugTableName);
        if (hasSaveAccess){
            try {
                drugRepository.delete(drugId);
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }catch(Exception ex){
                return new ResponseEntity<>("ERROR", HttpStatus.FORBIDDEN);
            }
        }else {
            return new ResponseEntity<>("ERROR", HttpStatus.FORBIDDEN);
        }
    }
}
