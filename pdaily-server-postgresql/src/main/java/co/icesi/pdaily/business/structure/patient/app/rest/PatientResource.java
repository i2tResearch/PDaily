package co.icesi.pdaily.business.structure.patient.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.business.structure.patient.app.PatientAppService;
import co.icesi.pdaily.business.structure.patient.app.PatientDTO;

@Path("/business/patient")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatientResource {
	@Inject
	PatientAppService appService;

	@POST
	public PatientDTO savePatient(PatientDTO dto) {
		return appService.savePatient( dto );
	}

	@GET
	public List<PatientDTO> findByPatient() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public PatientDTO findById(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@DELETE
	@Path("/{id}")
	public void deletePatient(@PathParam("id") String id) {
		appService.deletePatient( id );
	}
}
