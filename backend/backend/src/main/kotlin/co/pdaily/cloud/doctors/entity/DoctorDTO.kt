package co.pdaily.cloud.doctors.entity

import co.pdaily.cloud.patients.entity.Patient
import java.util.*

class DoctorDTO(var id: UUID? = null,
                var name: String? = null,
                var mail: String? = null,
                var role: DoctorRole? = null) {

    fun toDoctor(): Doctor {
        val doctor = Doctor()
        doctor.id = id;
        doctor.name = this.name
        doctor.mail = this.mail ?: throw Exception("Es necesario un email para el doctor")
        doctor.role = this.role

        return doctor
    }
}

fun Doctor.toDTO() = DoctorDTO(id, name, mail, role)
