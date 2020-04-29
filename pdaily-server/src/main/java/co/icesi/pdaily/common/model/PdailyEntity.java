package co.icesi.pdaily.common.model;

import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.Predicate;

import javax.persistence.MappedSuperclass;

/**
 * @author cristhiank on 14/11/19
 **/
@MappedSuperclass
public abstract class PdailyEntity<T> {

	public abstract T id();

	public abstract void setId(T id);

	public boolean isPersistent() {
		return id() != null;
	}

	@Override
	public int hashCode() {
		return hashCode( super::hashCode );
	}

	protected final int hashCode(IntSupplier defaultHash) {
		if ( isPersistent() ) {
			return Objects.hash( id() );
		} else {
			try {
				return defaultHash.getAsInt();
			} catch (Exception ex) {
				return super.hashCode();
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if ( !(obj instanceof PdailyEntity) ) {
			return false;
		}
		return equalsInternal( (PdailyEntity) obj, super::equals );
	}

	protected final <X extends PdailyEntity> boolean equalsInternal(X other, Predicate<X> equalsFunction) {
		if ( other == null ) {
			return false;
		}
		if ( this == other ) {
			return true;
		}
		if ( isPersistent() && other.isPersistent() ) {
			return id().equals( other.id() );
		} else {
			return equalsFunction.test( other );
		}
	}
}
