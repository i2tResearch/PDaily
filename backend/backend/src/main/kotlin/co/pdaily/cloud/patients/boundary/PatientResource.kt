package co.pdaily.cloud.patients.boundary

import co.pdaily.cloud.patients.entity.PatientDTO
import co.pdaily.cloud.patients.entity.toDTO
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/patients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class PatientResource {
    @Inject
    lateinit var patientService: PatientService

    @POST
    fun saveOrUpdate(patient: PatientDTO): PatientDTO {
        val saved = patientService.saveOrUpdate(patient.toPatient())
        return saved.toDTO()
    }

    @GET
    fun findAll(): List<PatientDTO> {
        val all = patientService.findAll()
        return all.map { it.toDTO() }
    }

    @GET
    @Path("/{id}")
    fun findById(@PathParam("id") id: UUID): PatientDTO {
        val found = patientService.findOrFail(id)
        return found.toDTO()
    }

    @DELETE
    @Path("/{id}")
    fun deleteById(@PathParam("id") id: UUID) {
        patientService.deleteById(id)
    }
}
