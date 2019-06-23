package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PositionData;

@Component
@Transactional
public class PositionDataToStringConverter implements Converter<PositionData,String> {

	@Override
	public String convert(PositionData o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
