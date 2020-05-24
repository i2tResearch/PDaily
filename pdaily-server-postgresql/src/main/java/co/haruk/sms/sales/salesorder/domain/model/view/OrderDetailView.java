package co.haruk.sms.sales.salesorder.domain.model.view;

import java.io.Serializable;
import java.util.UUID;

public final class OrderDetailView implements Serializable {
	public String id;
	public String productId;
	public String productName;
	public String supplierId;
	public String supplierName;
	public String supplierSalesRepId;
	public String supplierSalesRepName;
	public boolean isBonification;
	public int quantity;

	protected OrderDetailView() {
	}

	public OrderDetailView(
			UUID id,
			UUID productId,
			String productName,
			UUID supplierId,
			String supplierName,
			UUID supplierSalesRepId,
			String supplierSalesRepName,
			boolean isBonification,
			int quantity) {
		String salesRepId = supplierSalesRepId != null ? supplierSalesRepId.toString() : null;
		String supId = supplierId != null ? supplierId.toString() : null;

		this.id = id.toString();
		this.productId = productId.toString();
		this.productName = productName;
		this.supplierId = supId;
		this.supplierName = supplierName;
		this.supplierSalesRepId = salesRepId;
		this.supplierSalesRepName = supplierSalesRepName;
		this.isBonification = isBonification;
		this.quantity = quantity;
	}

}
