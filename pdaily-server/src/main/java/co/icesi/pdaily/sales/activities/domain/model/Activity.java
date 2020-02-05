package co.icesi.pdaily.sales.activities.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.*;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.business.structure.address.domain.model.Geolocation;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.UTCDateTime;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;
import co.icesi.pdaily.sales.activities.domain.model.details.CampaignDetail;
import co.icesi.pdaily.sales.activities.domain.model.details.TaskDetail;
import co.icesi.pdaily.sales.activities.purpose.domain.model.PurposeId;

@Entity
@Table(name = "sales_activities")
@NamedQuery(name = Activity.findBySalesRepAsReadView, query = "SELECT new co.icesi.pdaily.sales.activities.domain.model.view.ActivityReadView(a.id.id, b.id.id, b.name.name, s.id.id, s.name.name, p.id.id, p.name.name, a.creationDate.date, a.activityDate.date, a.comment.text) "
		+ " FROM Activity a INNER JOIN Customer b ON a.buyerId = b.id LEFT JOIN Customer s ON a.supplierId = s.id INNER JOIN Purpose p ON a.purposeId = p.id"
		+ " WHERE a.salesRepId = :salesRepId AND a.tenant = :company")
public class Activity extends HarukTenantEntity<ActivityId> {
	private static final String PREFIX = "Activity.";
	public static final String findBySalesRepAsReadView = PREFIX + "findBySalesRepAsReadView";

	@EmbeddedId
	private ActivityId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "sales_rep_id"))
	private SalesRepId salesRepId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "buyer_id"))
	private CustomerId buyerId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "supplier_id"))
	private CustomerId supplierId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "purpose_id"))
	private PurposeId purposeId;
	@Embedded
	@AttributeOverride(name = "date", column = @Column(name = "creation_date"))
	private UTCDateTime creationDate;
	@Embedded
	@AttributeOverride(name = "date", column = @Column(name = "activity_date"))
	private UTCDateTime activityDate;
	@Embedded
	private Geolocation geolocation;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "comments"))
	private Reference comment;
	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CampaignDetail> campaignDetails;
	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TaskDetail> taskDetails;

	protected Activity() {
	}

	private Activity(
			ActivityId id,
			SalesRepId salesRepId,
			CustomerId buyerId,
			CustomerId supplierId,
			PurposeId purposeId,
			UTCDateTime activityDate,
			Geolocation geolocation,
			Reference comment) {
		setId( id );
		setSalesRepId( salesRepId );
		setBuyerId( buyerId );
		setSupplierId( supplierId );
		setPurposeId( purposeId );
		setCreationDate( UTCDateTime.actualDate() );
		setActivityDate( activityDate );
		setGeolocation( geolocation );
		setComment( comment );
		campaignDetails = new HashSet<CampaignDetail>();
		taskDetails = new HashSet<TaskDetail>();
	}

	public static Activity of(
			ActivityId id,
			SalesRepId salesRepId,
			CustomerId buyerId,
			CustomerId supplierId,
			PurposeId purposeId,
			UTCDateTime activityDate,
			Geolocation geolocation,
			Reference comment) {
		return new Activity( id, salesRepId, buyerId, supplierId, purposeId, activityDate, geolocation, comment );
	}

	@Override
	public ActivityId id() {
		return id;
	}

	@Override
	public void setId(ActivityId id) {
		this.id = id;
	}

	public PurposeId purposeId() {
		return purposeId;
	}

	public UTCDateTime activityDate() {
		return activityDate;
	}

	public UTCDateTime creationDate() {
		return creationDate;
	}

	public Geolocation geolocation() {
		return geolocation;
	}

	public Reference comment() {
		return comment;
	}

	public SalesRepId salesRepId() {
		return salesRepId;
	}

	private void setSalesRepId(SalesRepId repId) {
		this.salesRepId = requireNonNull( repId, "El representante de ventas es necesario." );
	}

	public CustomerId buyerId() {
		return buyerId;
	}

	private void setBuyerId(CustomerId buyerId) {
		this.buyerId = requireNonNull( buyerId, "El cliente es necesario." );
	}

	public CustomerId supplierId() {
		return supplierId;
	}

	private void setSupplierId(CustomerId supplierId) {
		this.supplierId = supplierId;
	}

	private void setPurposeId(PurposeId purposeId) {
		this.purposeId = requireNonNull( purposeId, "El proposito o motivo de la actividad es necesario." );
	}

	private void setCreationDate(UTCDateTime date) {
		this.creationDate = date;
	}

	private void setActivityDate(UTCDateTime date) {
		this.activityDate = requireNonNull( date, "La fecha se realiza la actividad es necesaria" );
	}

	private void setGeolocation(Geolocation geolocation) {
		this.geolocation = geolocation;
	}

	private void setComment(Reference comment) {
		this.comment = comment;
	}

	public Set<TaskDetail> taskDetails() {
		return taskDetails;
	}

	private void setTaskDetails(Set<TaskDetail> taskDetails) {
		// Removed details
		Iterator<TaskDetail> iterator = this.taskDetails.iterator();
		while ( iterator.hasNext() ) {
			TaskDetail task = iterator.next();
			if ( !taskDetails.contains( task ) ) {
				this.taskDetails.remove( task );
				task.removeAssociationWithActivity();
			}
		}
		// updates or insert new details
		for ( TaskDetail det : taskDetails ) {
			var found = StreamUtils.find( this.taskDetails, it -> it.equals( det ) );
			if ( found.isPresent() ) {
				// error if its present
				if ( !found.get().id().equals( det.id() ) ) {
					throw new IllegalStateException( "No se permiten dos tareas iguales en la actividad" );
				}
				// find and update
				found.get().setDetail( det.detail() );
			} else {
				// insert
				this.addTaskDetail( det );
			}
		}
	}

	public Set<CampaignDetail> campaignDetails() {
		return campaignDetails;
	}

	private void setCampaignDetails(Set<CampaignDetail> campaignDetails) {
		// Removed details
		Iterator<CampaignDetail> iterator = this.campaignDetails.iterator();
		while ( iterator.hasNext() ) {
			CampaignDetail campaign = iterator.next();
			if ( !campaignDetails.contains( campaign ) ) {
				this.campaignDetails.remove( campaign );
				campaign.removeAssociationWithActivity();
			}
		}
		// updates or insert new details
		for ( CampaignDetail det : campaignDetails ) {
			var found = StreamUtils.find( this.campaignDetails, it -> it.equals( det ) );
			if ( found.isPresent() ) {
				// error if its present
				if ( !found.get().id().equals( det.id() ) ) {
					throw new IllegalStateException( "No se permiten dos campañas iguales en la actividad" );
				}
				// find and update
				found.get().setDetail( det.detail() );
			} else {
				// insert
				this.addCampaignDetail( det );
			}
		}
	}

	public void addTaskDetail(TaskDetail taskDetail) {
		taskDetails.add( taskDetail );
	}

	public void addCampaignDetail(CampaignDetail campaign) {
		campaignDetails.add( campaign );
	}

	public void updateFrom(Activity activity) {
		setGeolocation( activity.geolocation );
		setComment( activity.comment );
		setPurposeId( activity.purposeId );
		setSupplierId( activity.supplierId );
		setActivityDate( activity.activityDate );
		setCampaignDetails( activity.campaignDetails );
		setTaskDetails( activity.taskDetails );
	}
}
