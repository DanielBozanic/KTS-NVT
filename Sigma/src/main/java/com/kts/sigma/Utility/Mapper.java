package com.kts.sigma.Utility;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;


public class Mapper {

	public static ModelMapper mapper = new ModelMapper();
	public static Configuration configuration = mapper.getConfiguration().setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));
}
