package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RookieRepository;

import domain.Rookie;



@Component
@Transactional
public class StringToRookieConverter implements Converter<String,Rookie> {

	@Autowired
	RookieRepository repository;
	
	@Override
	public Rookie convert(String s) {
		Rookie res;
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
