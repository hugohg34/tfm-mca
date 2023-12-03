package mca.house_keeping_service.config.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import mca.house_keeping_service.reservation.model.ReservationId;

@Component
public class StringToReservaIdConverter implements Converter<String, ReservationId>{

	@Override
	@Nullable
	public ReservationId convert(String source) {
		return new ReservationId(Long.parseLong(source));
	}

}
