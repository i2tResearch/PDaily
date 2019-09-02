package co.pdaily.cloud.doctors.boundary

import co.pdaily.cloud.doctors.entity.DoctorDTO
import co.pdaily.cloud.doctors.entity.toDTO
import co.pdaily.cloud.patients.entity.PatientDTO
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/doctors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class DoctorResource {

    @Inject
    lateinit var doctorService: DoctorService

    @POST
    fun saveOrUpdate(doctor: DoctorDTO): DoctorDTO{
        val saved = doctorService.saveOrUpdate(doctor.toDoctor())
        return saved.toDTO()
    }

    @GET
    fun findAll(): List<DoctorDTO> {
        val all = doctorService.findAll()
        return all.map { it.toDTO() }
    }

    @GET
    @Path("/{id}")
    fun findById(@PathParam("id") id: UUID): DoctorDTO {
        val found = doctorService.findOrFail(id)
        return found.toDTO()
    }

    @DELETE
    @Path("/{id}")
    fun deleteById(@PathParam("id") id: UUID) {
        doctorService.deleteById(id)
    }

    @POST
    @Path("/{id}/patient")
    fun addPatient(@PathParam("id") id: UUID, patient: PatientDTO) {
        doctorService.addPatient(id, patient.toPatient())
    }
}
