package bsk.boot.controller;

import bsk.boot.model.*;
import bsk.boot.repository.OfficeRepository;
import bsk.boot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/offices")
class OfficeController {

    @Autowired
    OfficeRepository officeRepository;

    @Autowired
    private TableAccessResolver tableAccessResolver;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Value("${table.label}")
    private String labelTableName;

    @Value("${table.office}")
    private String officeTableName;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getOffices() {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        return responseBuilder.build(officeRepository.find(userLabel));
    }
    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public String details(@PathVariable("Id") Integer officeId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        Office office = officeRepository.findOnlyOne(officeId, userLabel);
        return responseBuilder.build(office);
    }

    @RequestMapping(value = "/{officeId}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Office> updateOffice(@PathVariable("officeId") Integer officeId, @RequestBody Office office) {
        return saveOffice(office);
    }

    private ResponseEntity<Office> saveOffice(@RequestBody Office office) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, officeTableName);
        if (hasSaveAccess){
            try {
                officeRepository.save(office);
                return new ResponseEntity<>(office, HttpStatus.OK);
            }catch(Exception ex){
                return new ResponseEntity<>(office, HttpStatus.CONFLICT);
            }
        }else {
            return new ResponseEntity<>(office, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Office> addOffice(@RequestBody Office office) {
        return saveOffice(office);
    }

    @RequestMapping(value = "/{officeId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> updateOffice(@PathVariable("officeId") Integer officeId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, officeTableName);
        if (hasSaveAccess){
            try {
                officeRepository.delete(officeId);
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }
            catch (Exception ex) {
                return new ResponseEntity<>("ERROR", HttpStatus.CONFLICT);
            }
        }else {
            return new ResponseEntity<>("ERROR", HttpStatus.FORBIDDEN);
        }
    }
}
