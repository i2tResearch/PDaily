package co.haruk.sms.business.structure.customer.holding.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.business.structure.customer.holding.app.HoldingCompanyAppService;
import co.haruk.sms.business.structure.customer.holding.app.HoldingCompanyDTO;

/**
 * @author cristhiank on 1/12/19
 **/
@Path("/business/customer/holding")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HoldingCompanyResource {

	@Inject
	HoldingCompanyAppService appService;

	@GET
	@Path("/for-subsidiary/{subId}")
	public List<HoldingCompanyDTO> findForSubsidiary(@PathParam("subId") String subsidiaryId) {
		return appService.findForSubsidiary( subsidiaryId );
	}

	@GET
	@Path("/{id}")
	public HoldingCompanyDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public HoldingCompanyDTO saveHolding(HoldingCompanyDTO dto) {
		return appService.saveHolding( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteHolding( id );
	}
}
