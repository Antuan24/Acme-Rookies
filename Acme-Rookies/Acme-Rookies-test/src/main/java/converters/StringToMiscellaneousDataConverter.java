package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MiscellaneousDataRepository;

import domain.MiscellaneousData;



@Component
@Transactional
public class StringToMiscellaneousDataConverter implements Converter<String,MiscellaneousData> {

	@Autowired
	MiscellaneousDataRepository repository;
	
	@Override
	public MiscellaneousData convert(String s) {
		MiscellaneousData res;
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
