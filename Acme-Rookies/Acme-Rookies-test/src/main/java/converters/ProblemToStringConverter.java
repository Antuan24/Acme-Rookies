package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Problem;

@Component
@Transactional
public class ProblemToStringConverter implements Converter<Problem,String> {

	@Override
	public String convert(Problem o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
