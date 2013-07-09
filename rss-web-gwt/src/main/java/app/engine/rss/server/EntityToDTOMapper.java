package app.engine.rss.server;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import app.engine.rss.shared.dto.HasDummyEmpty;

public class EntityToDTOMapper<T, T1 extends HasDummyEmpty<T1>> {	
	
	public T1 getDTO(Class<? extends T1> clazz, T entity) throws InstantiationException, IllegalAccessException, InvocationTargetException {

		final T1 dto = clazz.newInstance();
		if (entity == null) {
			return dto.getEmpty();
		}
		BeanUtils.copyProperties(dto, entity);

		return dto;
	}

	public List<T1> getDTO(Class<? extends T1> clazz, List<T> entities) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		if (entities == null) {
			return null;
		}

		final ArrayList<T1> dtos = new ArrayList<T1>();

		for (T entity : entities) {
			dtos.add(getDTO(clazz, entity));
		}
		return dtos;
	}

}
