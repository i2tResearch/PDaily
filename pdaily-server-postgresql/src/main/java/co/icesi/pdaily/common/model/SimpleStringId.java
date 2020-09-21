package co.icesi.pdaily.common.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author andres2508 on 1/5/20
 **/
@MappedSuperclass
public abstract class SimpleStringId implements Serializable {
	@Column(name = "id")
	private String id;

	protected SimpleStringId() {
	}

	protected SimpleStringId(String id) {
		this.id = requireNonNull( id ).toLowerCase();
	}

	public String text() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		SimpleStringId that = (SimpleStringId) o;
		return id.equals( that.id );
	}

	@Override
	public int hashCode() {
		return Objects.hash( id );
	}

	@Override
	public String toString() {
		return id;
	}
}
