package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Provider;

@Component
@Transactional
public class ProviderToStringConverter implements Converter<Provider,String> {

	@Override
	public String convert(Provider o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
