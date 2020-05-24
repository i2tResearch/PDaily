package co.haruk.sms.sales.salesorder.domain.model.view;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class SalesOrderView {
	public String id;
	public String sourceId;
	public String sourceName;
	public String salesRepId;
	public String salesRepName;
	public String buyerId;
	public String buyerName;
	public Long creationDate;
	public Long orderDate;
	public String comments;
	public List<OrderDetailView> orderDetails;

	protected SalesOrderView() {
	}

	public SalesOrderView(
			UUID id,
			UUID sourceId,
			String sourceName,
			UUID salesRepId,
			String salesRepName,
			UUID buyerId,
			String buyerName,
			Long creationDate,
			Long orderDate,
			String comments) {
		this.id = id.toString();
		this.sourceId = sourceId.toString();
		this.sourceName = sourceName;
		this.salesRepId = salesRepId.toString();
		this.salesRepName = salesRepName;
		this.buyerId = buyerId.toString();
		this.buyerName = buyerName;
		this.creationDate = creationDate;
		this.orderDate = orderDate;
		this.comments = comments;
	}

	public SalesOrderView(
			UUID id,
			UUID sourceId,
			String sourceName,
			UUID buyerId,
			String buyerName,
			UUID salesRepId,
			Instant creationDate,
			Instant orderDate,
			String comments) {
		this.id = id.toString();
		this.sourceId = sourceId.toString();
		this.sourceName = sourceName;
		this.buyerId = buyerId.toString();
		this.buyerName = buyerName;
		this.salesRepId = salesRepId.toString();
		this.creationDate = creationDate.toEpochMilli();
		this.orderDate = orderDate.toEpochMilli();
		this.comments = comments;
	}
}
