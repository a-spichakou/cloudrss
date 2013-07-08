package app.engine.rss.dao;

import static app.engine.rss.dao.OfyService.ofy;

import org.springframework.stereotype.Service;

import app.engine.rss.entity.ItemEntity;

@Service
public class ItemDAOImpl implements ItemDAO{

	public ItemEntity getItem(long itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addItem(ItemEntity itemEntity) {
		ofy().save().entity(itemEntity).now();
	}

	public void removeItem(long itemId) {
		// TODO Auto-generated method stub
		
	}

	public void saveItem(ItemEntity itemEntity) {
		// TODO Auto-generated method stub
		
	}

}
