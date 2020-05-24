package co.haruk.sms.sales.salesorder.app;

import co.haruk.sms.business.structure.businessunit.product.domain.model.ProductId;
import co.haruk.sms.business.structure.customer.contact.domain.model.ContactId;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrder;
import co.haruk.sms.sales.salesorder.domain.model.details.OrderDetail;
import co.haruk.sms.sales.salesorder.domain.model.details.OrderDetailId;

public final class OrderDetailDTO {
	public String id;
	public String productId;
	public String supplierId;
	public String supSalesRepId;
	public boolean isBonification;
	public int quantity;

	protected OrderDetailDTO() {
	}

	private OrderDetailDTO(String id, String productId, String supplierId, String supSalesRepId, boolean isBonification,
			int quantity) {
		this.id = id;
		this.productId = productId;
		this.supplierId = supplierId;
		this.supSalesRepId = supSalesRepId;
		this.isBonification = isBonification;
		this.quantity = quantity;
	}

	public static OrderDetailDTO of(String id, String productId, String supplierId, String supSalesRepId,
			boolean isBonification, int quantity) {
		return new OrderDetailDTO( id, productId, supplierId, supSalesRepId, isBonification, quantity );
	}

	public static OrderDetailDTO of(OrderDetail orderDetail) {
		final var supplierId = orderDetail.supplierId() != null ? orderDetail.supplierId().text() : null;
		final var supplierSalesRepId = orderDetail.supplierSalesRepId() != null ? orderDetail.supplierSalesRepId().text()
				: null;

		return new OrderDetailDTO(
				orderDetail.id().text(),
				orderDetail.productId().text(),
				supplierId,
				supplierSalesRepId,
				orderDetail.isBonification(),
				orderDetail.quantity()
		);
	}

	public OrderDetail toOrderDetail(SalesOrder salesOrder) {
		OrderDetailId orderId = id == null ? OrderDetailId.generateNew() : OrderDetailId.ofNotNull( id );
		return OrderDetail.of(
				salesOrder,
				orderId,
				ProductId.ofNotNull( productId ),
				CustomerId.of( supplierId ),
				ContactId.of( supSalesRepId ),
				isBonification,
				quantity
		);
	}
}
