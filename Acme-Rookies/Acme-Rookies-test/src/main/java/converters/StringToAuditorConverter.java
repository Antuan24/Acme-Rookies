package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AuditorRepository;

import domain.Auditor;



@Component
@Transactional
public class StringToAuditorConverter implements Converter<String,Auditor> {

	@Autowired
	AuditorRepository repository;
	
	@Override
	public Auditor convert(String s) {
		Auditor res;
		int id;
		
		try {
			if(StringUtils.isEmpty(s))
				res=null;
			else{
				id = Integer.valueOf(s);
				res = repository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
