package app.engine.rss.dao;

import app.engine.rss.entity.ItemEntity;

public interface ItemDAO {
	
	public ItemEntity getItem(long itemId);

	public void addItem(ItemEntity item);
	
	public void removeItem(long itemId);
	
	public void saveItem(ItemEntity item);
}
