package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PositionDataRepository;

import domain.PositionData;



@Component
@Transactional
public class StringToPositionDataConverter implements Converter<String,PositionData> {

	@Autowired
	PositionDataRepository repository;
	
	@Override
	public PositionData convert(String s) {
		PositionData res;
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
