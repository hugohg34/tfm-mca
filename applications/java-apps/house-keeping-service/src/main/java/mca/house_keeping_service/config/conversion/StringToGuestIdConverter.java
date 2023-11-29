package mca.house_keeping_service.config.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import mca.house_keeping_service.establishment.model.GuestId;

public class StringToGuestIdConverter implements Converter<String, GuestId> {

	@Override
	@Nullable
	public GuestId convert(String source) {
		return new GuestId(Long.parseLong(source));
	}
	
}
