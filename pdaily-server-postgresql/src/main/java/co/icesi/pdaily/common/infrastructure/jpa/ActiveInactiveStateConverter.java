package co.icesi.pdaily.common.infrastructure.jpa;

import javax.persistence.Converter;

import co.haruk.core.infrastructure.persistence.jpa.converters.BaseDBEnumConverter;
import co.icesi.pdaily.common.model.ActiveInactiveState;

/**
 * @author andres2508 on 10/12/19
 **/
@Converter
public class ActiveInactiveStateConverter extends BaseDBEnumConverter<ActiveInactiveState> {
}
