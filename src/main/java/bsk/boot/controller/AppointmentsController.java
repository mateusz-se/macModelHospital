package bsk.boot.controller;

import bsk.boot.model.Appointment;
import bsk.boot.repository.AppointmentRepository;
import bsk.boot.repository.OfficeRepository;
import bsk.boot.repository.PatientRepository;
import bsk.boot.repository.UserRepository;
import bsk.boot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/appointments")
class AppointmentsController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TableAccessResolver tableAccessResolver;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Value("${table.label}")
    private String labelTableName;

    @Value("${table.appointment}")
    private String appointmentTableName;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAppointments() {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        List<Appointment> appointmentList = appointmentRepository.find(userLabel);
        for (Appointment appointment : appointmentList) {
            appointment.setDoctor(userRepository.findOnlyOne(appointment.getIdDoctor(), userLabel));
            appointment.setOffice(officeRepository.findOnlyOne(appointment.getIdOffice(),userLabel));
            appointment.setPatient(patientRepository.findOnlyOne(appointment.getIdPatient(),userLabel));
        }
        return responseBuilder.build(appointmentList);
    }
    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public String getAppointment(@PathVariable("Id") Integer appointmentId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        Appointment appointment = appointmentRepository.findOnlyOne(appointmentId, userLabel);
        appointment.setDoctor(userRepository.findOnlyOne(appointment.getIdDoctor(), userLabel));
        appointment.setOffice(officeRepository.findOnlyOne(appointment.getIdOffice(),userLabel));
        appointment.setPatient(patientRepository.findOnlyOne(appointment.getIdPatient(),userLabel));
       return responseBuilder.build(appointment);
    }

    @RequestMapping(value = "/{appointmentId}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Appointment> updateUser(@PathVariable("appointmentId") Integer appointmentId, @RequestBody Appointment appointment) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, appointmentTableName);
        if (hasSaveAccess){
            appointmentRepository.save(appointment);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(appointment, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Appointment> addOffice(@RequestBody Appointment appointment) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, appointmentTableName);
        if (hasSaveAccess){
            appointmentRepository.save(appointment);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(appointment, HttpStatus.FORBIDDEN);
        }
    }
    @RequestMapping(value = "/{appointmentId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAppointment(@PathVariable("appointmentId") Integer appointmentId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, appointmentTableName);
        if (hasSaveAccess){
            try {
                appointmentRepository.delete(appointmentId);
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
