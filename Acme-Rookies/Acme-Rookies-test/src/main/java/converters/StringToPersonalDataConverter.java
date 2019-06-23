package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PersonalDataRepository;

import domain.PersonalData;



@Component
@Transactional
public class StringToPersonalDataConverter implements Converter<String,PersonalData> {

	@Autowired
	PersonalDataRepository repository;
	
	@Override
	public PersonalData convert(String s) {
		PersonalData res;
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
