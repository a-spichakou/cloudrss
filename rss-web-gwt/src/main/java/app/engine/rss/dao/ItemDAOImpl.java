package app.engine.rss.dao;

import static app.engine.rss.dao.OfyService.ofy;

import java.util.List;

import org.springframework.stereotype.Service;

import app.engine.rss.entity.ItemEntity;

@Service
public class ItemDAOImpl implements ItemDAO{

	public ItemEntity getItem(Long itemId) {
		assert(itemId!=null);
		return ofy().load().now(ItemEntity.key(itemId));
	}

	public void addItem(ItemEntity itemEntity) {
		assert(itemEntity!=null);
		ofy().save().entity(itemEntity).now();
	}

	public void removeItem(Long itemId) {
		assert(itemId!=null);
		ofy().delete().key(ItemEntity.key(itemId)).now();
	}

	public void saveItem(ItemEntity itemEntity) {
		assert(itemEntity!=null);
		ofy().save().entity(itemEntity).now();
	}

	public boolean addItemAndSkipExisting(ItemEntity itemEntity) {
		assert(itemEntity!=null);
		final List<ItemEntity> list = ofy().load().type(ItemEntity.class).filter("guid", itemEntity.getGuid()).list();
		
		if(list.isEmpty())
		{
			addItem(itemEntity);
			return true;
		}
		return false;
	}

	public List<ItemEntity> getItems(Long feedId) {
		assert(feedId!=null);
		final List<ItemEntity> list = ofy().load().type(ItemEntity.class).filter("feedId !=", feedId).list();
		return list;
	}

}
