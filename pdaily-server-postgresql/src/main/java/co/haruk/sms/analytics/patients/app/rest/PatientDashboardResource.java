package co.haruk.sms.analytics.patients.app.rest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.jose4j.json.internal.json_simple.JSONArray;

import co.haruk.sms.analytics.patients.domain.model.PatientQueryManager;

@Path("/reports/patient")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatientDashboardResource {
	@Inject
	public PatientQueryManager queryManager;

	@GET
	@Path("/{patientId}/resume/{startDate}/{endDate}")
	public JSONArray resumePatient(
			@PathParam("patientId") String patientId,
			@PathParam("startDate") Long startDate,
			@PathParam("endDate") Long endDate) {
		return queryManager.processQuery( startDate, endDate, patientId );
	}
}
