package bsk.boot.controller;

import bsk.boot.model.Appointment;
import bsk.boot.model.Patient;
import bsk.boot.repository.AppointmentRepository;
import bsk.boot.repository.OfficeRepository;
import bsk.boot.repository.PatientRepository;
import bsk.boot.repository.UserRepository;
import bsk.boot.service.*;
import bsk.boot.util.HibernateProxyTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
class PatientsController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableAccessResolver tableAccessResolver;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Value("${table.label}")
    private String labelTableName;

    @Value("${table.patient}")
    private String patientTableName;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getPatients() {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        return responseBuilder.build(patientRepository.find(userLabel));
    }
    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public String details(@PathVariable("Id") Integer patientId) {
        GsonBuilder b = new GsonBuilder();
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        Gson gson = b.create();
        Patient patient = patientRepository.findOnlyOne(patientId, userLabel);
        JsonElement jsonElement = gson.toJsonTree(patient);
        List<Appointment> appointments = appointmentRepository.findByIdPatient(patient.getIdPatient(), userLabel);
        for (Appointment appointment : appointments) {
            appointment.setDoctor(userRepository.findOnlyOne(appointment.getIdDoctor(), userLabel));
            appointment.setOffice(officeRepository.findOnlyOne(appointment.getIdOffice(), userLabel));
        }
        jsonElement.getAsJsonObject().add("appointments", gson.toJsonTree(appointments));
        return gson.toJson(jsonElement);
    }

    @RequestMapping(value = "/{patientId}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Patient> updatePatient(@PathVariable("patientId") Integer patientId, @RequestBody Patient patient) {
        return savePatient(patient);
    }

    private ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, patientTableName);
        if (hasSaveAccess){
            try {
                patientRepository.save(patient);
                return new ResponseEntity<Patient>(patient, HttpStatus.OK);
            }catch(Exception ex){
                return new ResponseEntity<Patient>(patient, HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<Patient>(patient, HttpStatus.FORBIDDEN);
        }
    }


    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        return savePatient(patient);
    }

    @RequestMapping(value = "/{patientId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePatient(@PathVariable("patientId") Integer patientId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, patientTableName);
        if (hasSaveAccess){
            try {
                patientRepository.delete(patientId);
                return new ResponseEntity<String>("OK", HttpStatus.OK);
            }
            catch (Exception ex) {
                return new ResponseEntity<String>("ERROR", HttpStatus.CONFLICT);
            }
        }else {
            return new ResponseEntity<String>("ERROR", HttpStatus.FORBIDDEN);
        }
    }

}
