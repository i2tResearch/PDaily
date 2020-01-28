package co.icesi.pdaily.events.physical.injury.type.domain.model;

import co.haruk.core.domain.model.entity.Identity;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class InjuryTypeId extends Identity {

    protected InjuryTypeId() {
    }

    private InjuryTypeId(String id) {
        super( id );
    }

    private InjuryTypeId(UUID id) {
        super( id );
    }

    public static InjuryTypeId of(String id) {
        if ( id == null ) {
            return null;
        }
        return new InjuryTypeId( id );
    }

    public static InjuryTypeId ofNotNull(String id) {
        return new InjuryTypeId( id );
    }

    public static InjuryTypeId generateNew() {
        return new InjuryTypeId( UUID.randomUUID() );
    }
}
