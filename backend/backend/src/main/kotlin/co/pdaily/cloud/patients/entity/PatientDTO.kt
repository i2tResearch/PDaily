package co.pdaily.cloud.patients.entity

import co.pdaily.cloud.doctors.entity.Doctor
import java.util.*

class PatientDTO(var id: UUID? = null,
                 var document: String? = null,
                 var name: String? = null,
                 var age: Int = 0,
                 var gender: PatientGender? = null){

    fun toPatient(): Patient {
        val patient = Patient()
        patient.id = this.id ?: throw Exception("No puede haber un paciente sin id")
        patient.document = this.document ?: throw Exception("El valor del documento es requerido")
        patient.name = this.name
        patient.age = this.age
        patient.gender = this.gender
        return patient
    }
}

fun Patient.toDTO() = PatientDTO(id, document, name, age, gender)
