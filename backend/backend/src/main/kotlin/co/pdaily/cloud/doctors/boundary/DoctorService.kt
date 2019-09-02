package co.pdaily.cloud.doctors.boundary

import co.pdaily.cloud.doctors.entity.Doctor
import co.pdaily.cloud.patients.entity.Patient
import java.util.*
import javax.enterprise.context.Dependent
import javax.transaction.Transactional

@Dependent
class DoctorService {

    @Transactional
    fun saveOrUpdate(doctor: Doctor): Doctor {
        return if (doctor.id != null){
            updateDoctor(doctor)
        } else {
            doctor.persist()
            doctor
        }
    }

    fun updateDoctor(changes: Doctor): Doctor {
        val original = Doctor.findById<Doctor>(changes.id) ?: throw Exception("No se encontro el doctor")
        original.updateFrom(changes)
        return original
    }

    fun findAll(): MutableList<Doctor>{
        return Doctor.findAll<Doctor>().list<Doctor>()
    }

    fun findOrFail(doctorId: UUID): Doctor {
        return Doctor.findById(doctorId) ?: throw Exception("Doctor con id $doctorId no se encuentra en la Base de Datos")
    }

    @Transactional
    fun deleteById(doctorId: UUID) {
        val found = findOrFail(doctorId)
        found.delete()
    }

    @Transactional
    fun addPatient(doctorId: UUID, patient: Patient){
        val found = findOrFail(doctorId)
        found.addPatient(patient)
    }
}
