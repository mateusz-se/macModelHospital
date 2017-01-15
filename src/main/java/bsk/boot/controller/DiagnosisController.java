package bsk.boot.controller;

import bsk.boot.model.*;
import bsk.boot.repository.*;
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
@RequestMapping("/api/diagnosis")
class DiagnosisController {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private DrugDiagnosisRepository drugDiagnosisRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

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

    @Value("${table.diagnosis}")
    private String diagnosisTableName;

    @Value("${table.prescriptedDrugs}")
    private String prescriptedDrugsTableName;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAllDiagnoses() {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        List<Diagnosis> diagnoses = diagnosisRepository.findAll(userLabel);
        for (Diagnosis d : diagnoses) {
            Appointment appointment = appointmentRepository.findOnlyOne(d.getIdAppointment(), userLabel);
            if (appointment != null) {
                appointment.setPatient(patientRepository.findOnlyOne(appointment.getIdPatient(), userLabel));
                appointment.setDoctor(userRepository.findOnlyOne(appointment.getIdDoctor(), userLabel));
                d.setAppointment(appointment);
            }
            d.setDrugs(drugRepository.findDrugsForDiagnosis(userLabel, d.getIdDiagnosis()));
        }
        return responseBuilder.build(diagnoses);
    }

    @RequestMapping(value = "/appointment/{appointmentId}", method = RequestMethod.GET)
    @ResponseBody
    String getdiagnoses(@PathVariable("appointmentId") Integer appointmentId ) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        List<Diagnosis> diagnoses = diagnosisRepository.find(userLabel, appointmentId);
        for (Diagnosis d : diagnoses) {
            d.setDrugs(drugRepository.findDrugsForDiagnosis(userLabel, d.getIdDiagnosis()));
        }
        return responseBuilder.build(diagnoses);
    }
    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public String getDiagnosis(@PathVariable("Id") Integer diagnosisId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        Diagnosis diagnosis = diagnosisRepository.findOnlyOne(diagnosisId, userLabel);
        diagnosis.setDrugs(drugRepository.findDrugsForDiagnosis(userLabel, diagnosis.getIdDiagnosis()));
        return responseBuilder.build(diagnosis);
    }

    @RequestMapping(value = "/{diagnosisId}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Diagnosis> updateDiagnosis(@PathVariable("diagnosisId") Integer diagnosisId, @RequestBody Diagnosis diagnosis) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, diagnosisTableName, prescriptedDrugsTableName);
        if (hasSaveAccess){
            diagnosisRepository.save(diagnosis);
            updateDrugDiagnosis(diagnosis, userLabel);
            return new ResponseEntity<>(diagnosis, HttpStatus.OK);

        }
        return new ResponseEntity<>(diagnosis, HttpStatus.FORBIDDEN);
    }

    private void updateDrugDiagnosis(Diagnosis diagnosis, Integer userLabel) {
        List<DrugDiagnosis> currentDrugDiagnosisList = drugDiagnosisRepository.findByDiagnosisId(diagnosis.getIdDiagnosis(), userLabel);
        drugDiagnosisRepository.delete(currentDrugDiagnosisList);
        currentDrugDiagnosisList.clear();
        for (Drug d : diagnosis.getDrugs()){
            DrugDiagnosis drugDiagnosis = new DrugDiagnosis(diagnosis.getIdDiagnosis(), d.getIdDrug());
            currentDrugDiagnosisList.add(drugDiagnosis);
        }
        drugDiagnosisRepository.save(currentDrugDiagnosisList);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Diagnosis> addDiagnosis(@RequestBody Diagnosis diagnosis) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, diagnosisTableName, prescriptedDrugsTableName);
        if (hasSaveAccess){
            diagnosis = diagnosisRepository.save(diagnosis);
            updateDrugDiagnosis(diagnosis, userLabel);
            return new ResponseEntity<>(diagnosis, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(diagnosis, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{diagnosisId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDiagnosis(@PathVariable("diagnosisId") Integer diagnosisId) {
        Integer userLabel = authenticationFacade.getUserAccessLabel();
        boolean hasSaveAccess = tableAccessResolver.hasSaveAccess(userLabel, labelTableName, diagnosisTableName, prescriptedDrugsTableName);
        if (hasSaveAccess){
            List<DrugDiagnosis> currentDrugDiagnosisList = drugDiagnosisRepository.findByDiagnosisId(diagnosisId, userLabel);
            drugDiagnosisRepository.delete(currentDrugDiagnosisList);
            diagnosisRepository.delete(diagnosisId);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("ERROR", HttpStatus.FORBIDDEN);
        }
    }
}
