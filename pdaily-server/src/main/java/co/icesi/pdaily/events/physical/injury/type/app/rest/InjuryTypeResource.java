package co.icesi.pdaily.events.physical.injury.type.app.rest;

import co.icesi.pdaily.events.physical.injury.type.app.InjuryTypeAppService;
import co.icesi.pdaily.events.physical.injury.type.app.InjuryTypeDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/events/physical/injury")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InjuryTypeResource {
    @Inject
    InjuryTypeAppService appService;

    @GET
    public List<InjuryTypeDTO> findAll() {
        return appService.findAll();
    }

    @GET
    @Path("/{id}")
    public InjuryTypeDTO findById(@PathParam("id") String id) {
        return appService.findById( id );
    }

    @POST
    public InjuryTypeDTO saveInjuryType(InjuryTypeDTO dto) {
        return appService.saveInjuryType( dto );
    }

    @DELETE
    @Path("/{id}")
    public void deleteById(@PathParam("id") String id) {
        appService.deleteInjuryType( id );
    }
}
