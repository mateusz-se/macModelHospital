package bsk.boot.controller;

import bsk.boot.model.TableLabel;
import bsk.boot.service.AuthenticationFacade;
import bsk.boot.service.ResponseBuilder;
import bsk.boot.repository.TableLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Mateusz-PC on 09.05.2016.
 */
@Controller
@RequestMapping("/api/access")
class AccessController {

    @Autowired
    private TableLabelRepository tableLabelRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Value("${table.label}")
    private String labelTableName;

    @Value("${table.users}")
    private String usersTableName;

    @Value("${table.patient}")
    private String patientTableName;

    @Value("${table.office}")
    private String officeTableName;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String getAccess(@PathVariable("name") String name) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasAccess = false;
        if ("appointment".equals(name)) {
            hasAccess = checkAppointmentAccess(userLabel);
        }
        return responseBuilder.build(new AccessClass(hasAccess));
    }

    private boolean checkAppointmentAccess(Integer userLabel) {
        try {
            int userAccess = tableLabelRepository.getLabel(usersTableName, userLabel);
            int patientAccess = tableLabelRepository.getLabel(patientTableName, userLabel);
            int officeAccess = tableLabelRepository.getLabel(officeTableName, userLabel);
            TableLabel labelTableLabel = tableLabelRepository.findOne(labelTableName);
            if (userLabel >= userAccess && userLabel >= patientAccess && userLabel >= officeAccess && userLabel >= labelTableLabel.getLabel()) {
                return true;
            }
            return false;
        }catch(Exception ex) {
            return false;
        }
    }
    private class AccessClass{
        private boolean hasAccess;

        public AccessClass(boolean hasAccess) {
            this.hasAccess = hasAccess;
        }
    }
}
