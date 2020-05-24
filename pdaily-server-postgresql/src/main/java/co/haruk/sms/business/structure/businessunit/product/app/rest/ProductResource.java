package co.haruk.sms.business.structure.businessunit.product.app.rest;

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

import co.haruk.sms.business.structure.businessunit.product.app.ProductAppService;
import co.haruk.sms.business.structure.businessunit.product.app.ProductRequestDTO;
import co.haruk.sms.business.structure.businessunit.product.domain.model.view.ProductReadView;

@Path("business/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

	@Inject
	ProductAppService appService;

	@GET
	public List<ProductReadView> findAll() {
		return appService.findAllAsReadView();
	}

	@GET
	@Path("/{id}")
	public ProductReadView findById(@PathParam("id") String id) {
		return appService.findByIdAsReadView( id );
	}

	@GET
	@Path("/for-business-unit/{businessId}")
	public List<ProductReadView> findByBUnitAsReadView(@PathParam("businessId") String businessId) {
		return appService.findByBUnitAsReadView( businessId );
	}

	@POST
	public ProductRequestDTO saveForProduct(ProductRequestDTO dto) {
		return appService.saveForProduct( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteProduct( id );
	}
}
