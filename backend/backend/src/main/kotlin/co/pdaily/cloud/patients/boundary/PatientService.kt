package co.pdaily.cloud.patients.boundary

import co.pdaily.cloud.patients.entity.Patient
import java.util.*
import javax.enterprise.context.Dependent
import javax.transaction.Transactional

@Dependent
class PatientService {
    @Transactional
    fun saveOrUpdate(patient: Patient): Patient {
        return if (patient.id != null){
            updatePatient(patient)
        } else {
            patient.persist()
            patient
        }
    }

    fun updatePatient(changes: Patient): Patient {
        val original = Patient.findById<Patient>(changes.id) ?: throw Exception("No se encontro el paciente")
        original.updateFrom(changes)
        return original
    }

    fun findAll(): MutableList<Patient>{
        return Patient.findAll<Patient>().list<Patient>()
    }

    fun findOrFail(patientId: UUID): Patient {
        return Patient.findById(patientId) ?: throw Exception("Paciente con id: $patientId, no se encuentra en la Base de Datos")
    }

    @Transactional
    fun deleteById(patient: UUID) {
        val found = findOrFail(patient)
        found.delete()
    }
}
