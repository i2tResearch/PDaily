package co.haruk.sms.sales.salesorder.app;

import java.util.List;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.guards.Guards;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrder;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrderId;
import co.haruk.sms.sales.salesorder.source.domain.model.OrderSourceId;

public final class SalesOrderDTO {
	public String id;
	public String sourceId;
	public String salesRepId;
	public String buyerId;
	public Long orderDate;
	public String comments;
	public List<OrderDetailDTO> orderDetails;
	private String tenantId;

	protected SalesOrderDTO() {
	}

	private SalesOrderDTO(
			String id,
			String sourceId,
			String salesRepId,
			String buyerId,
			Long orderDate,
			String comments,
			List<OrderDetailDTO> orderDetails) {
		this.id = id;
		this.sourceId = sourceId;
		this.salesRepId = salesRepId;
		this.buyerId = buyerId;
		this.orderDate = orderDate;
		this.comments = comments;
		this.orderDetails = orderDetails;
	}

	private SalesOrderDTO(
			String id,
			String sourceId,
			String salesRepId,
			String buyerId,
			Long orderDate,
			String comments,
			String tenantId,
			List<OrderDetailDTO> orderDetails) {
		this.id = id;
		this.sourceId = sourceId;
		this.salesRepId = salesRepId;
		this.buyerId = buyerId;
		this.orderDate = orderDate;
		this.comments = comments;
		this.tenantId = tenantId;
		this.orderDetails = orderDetails;
	}

	public static SalesOrderDTO of(
			String id,
			String sourceId,
			String salesRepId,
			String buyerId,
			Long orderDate,
			String comments,
			List<OrderDetailDTO> orderDetails) {
		return new SalesOrderDTO( id, sourceId, salesRepId, buyerId, orderDate, comments, orderDetails );
	}

	public static SalesOrderDTO of(
			String id,
			String sourceId,
			String salesRepId,
			String buyerId,
			Long orderDate,
			String comments) {
		return new SalesOrderDTO( id, sourceId, salesRepId, buyerId, orderDate, comments, null );
	}

	public static SalesOrderDTO of(SalesOrder salesOrder) {
		final var comments = salesOrder.comments() != null ? salesOrder.comments().text() : null;
		final var orderDetails = StreamUtils.map( salesOrder.orderDetails(), OrderDetailDTO::of );

		return new SalesOrderDTO(
				salesOrder.id().text(),
				salesOrder.sourceId().text(),
				salesOrder.salesRepId().text(),
				salesOrder.buyerId().text(),
				salesOrder.orderDate().dateAsLong(),
				comments, salesOrder.tenant.text(),
				orderDetails
		);
	}

	public SalesOrder toSalesOrder() {
		SalesOrder result = SalesOrder.of(
				SalesOrderId.of( id ),
				OrderSourceId.ofNotNull( sourceId ),
				SalesRepId.ofNotNull( salesRepId ),
				CustomerId.ofNotNull( buyerId ),
				UTCDateTime.of( orderDate ),
				Reference.of( comments )
		);
		if ( tenantId != null ) {
			result.setTenantId( TenantId.of( tenantId ) );
		}
		addOrderDetails( result );
		return result;
	}

	private void addOrderDetails(SalesOrder salesOrder) {
		Guards.require( orderDetails != null && !orderDetails.isEmpty(), "Una transferencia sin detalles no es permitida." );
		for ( OrderDetailDTO orderDetail : orderDetails ) {
			salesOrder.addOrderDetail( orderDetail.toOrderDetail( salesOrder ) );
		}
	}
}
