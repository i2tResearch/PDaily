package co.haruk.sms.security.authorization.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.common.model.PdailyEntity;

/**
 * @author cristhiank on 1/5/20
 **/
@Entity()
@Table(name = "security_auth_activities")
public class ProtectedActivity extends PdailyEntity<ProtectedActivityId> {
	@EmbeddedId
	private ProtectedActivityId id;
	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "description"))
	private PlainName description;
	@ManyToOne
	@JoinColumn(name = "group_id", updatable = false)
	private AuthorizationGroup group;

	protected ProtectedActivity() {
	}

	private ProtectedActivity(ProtectedActivityId id, PlainName description, AuthorizationGroup group) {
		this.id = requireNonNull( id, "El identificador de la actividad es requerido" );
		setDescription( description );
		setGroup( group );
	}

	public static ProtectedActivity of(ProtectedActivityId id, PlainName description, AuthorizationGroup group) {
		return new ProtectedActivity( id, description, group );
	}

	@Override
	public ProtectedActivityId id() {
		return id;
	}

	@Override
	public void setId(ProtectedActivityId id) {
		this.id = id;
	}

	public PlainName description() {
		return description;
	}

	public AuthorizationGroup group() {
		return group;
	}

	public void setGroup(AuthorizationGroup group) {
		this.group = requireNonNull( group, "El grupo de la actividad es requerido" );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		ProtectedActivity that = (ProtectedActivity) o;
		return id.equals( that.id );
	}

	@Override
	public int hashCode() {
		return Objects.hash( id );
	}

	public void setDescription(PlainName description) {
		this.description = requireNonNull( description, "La descripción de la actividad es requerida" );
	}
}
