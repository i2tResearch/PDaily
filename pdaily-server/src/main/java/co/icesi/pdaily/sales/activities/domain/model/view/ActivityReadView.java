package co.icesi.pdaily.sales.activities.domain.model.view;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import co.icesi.pdaily.business.structure.address.domain.model.Geolocation;

public final class ActivityReadView {
	public String activityId;
	public String salesRepId;
	public String salesRepName;
	public String buyerId;
	public String buyerName;
	public String supplierId;
	public String supplierName;
	public String purposeId;
	public String purposeName;
	public Long creationDate;
	public Long activityDate;
	public Float latitude = 0F;
	public Float longitude = 0F;
	public String comments;
	public List<CampaignDetailReadView> campaigns;
	public List<TaskDetailReadView> tasks;

	protected ActivityReadView() {
	}

	public ActivityReadView(
			UUID activityId,
			UUID salesRepId,
			String salesRepName,
			UUID buyerId,
			String buyerName,
			UUID supplierId,
			String supplierName,
			UUID purposeId,
			String purposeName,
			Long creationDate,
			Long activityDate,
			String comments) {

		final String supplier = supplierId != null ? supplierId.toString() : null;
		final String purpose = purposeId != null ? purposeId.toString() : null;

		this.activityId = activityId.toString();
		this.salesRepId = salesRepId.toString();
		this.salesRepName = salesRepName;
		this.buyerId = buyerId.toString();
		this.buyerName = buyerName;
		this.supplierId = supplier;
		this.supplierName = supplierName;
		this.purposeId = purpose;
		this.purposeName = purposeName;
		this.creationDate = creationDate;
		this.activityDate = activityDate;
		this.comments = comments;
	}

	public ActivityReadView(
			UUID activityId,
			UUID salesRepId,
			String salesRepName,
			UUID buyerId,
			String buyerName,
			UUID supplierId,
			String supplierName,
			UUID purposeId,
			String purposeName,
			Long creationDate,
			Long activityDate,
			String comments,
			Geolocation geolocation) {

		final String supplier = supplierId != null ? supplierId.toString() : null;
		final String purpose = purposeId != null ? purposeId.toString() : null;

		this.activityId = activityId.toString();
		this.salesRepId = salesRepId.toString();
		this.salesRepName = salesRepName;
		this.buyerId = buyerId.toString();
		this.buyerName = buyerName;
		this.supplierId = supplier;
		this.supplierName = supplierName;
		this.purposeId = purpose;
		this.purposeName = purposeName;
		this.creationDate = creationDate;
		this.activityDate = activityDate;
		this.comments = comments;
		this.latitude = geolocation.latitude();
		this.longitude = geolocation.longitude();
	}

	public ActivityReadView(
			UUID activityId,
			UUID buyerId,
			String buyerName,
			UUID supplierId,
			String supplierName,
			UUID purposeId,
			String purposeName,
			Instant creationDate,
			Instant activityDate,
			String comments) {

		final String supplier = supplierId != null ? supplierId.toString() : null;
		final String purpose = purposeId != null ? purposeId.toString() : null;

		this.activityId = activityId.toString();
		this.salesRepId = null;
		this.salesRepName = null;
		this.buyerId = buyerId.toString();
		this.buyerName = buyerName;
		this.supplierId = supplier;
		this.supplierName = supplierName;
		this.purposeId = purpose;
		this.purposeName = purposeName;
		this.creationDate = creationDate.toEpochMilli();
		this.activityDate = activityDate.toEpochMilli();
		this.comments = comments;
	}

}
