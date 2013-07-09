package app.engine.rss.dao;

import java.util.List;

import app.engine.rss.entity.ItemEntity;

public interface ItemDAO {
	
	public ItemEntity getItem(Long itemId);

	public void addItem(ItemEntity item);
	
	public void removeItem(Long itemId);
	
	public void saveItem(ItemEntity item);
	
	public boolean addItemAndSkipExisting(ItemEntity item);
	
	public List<ItemEntity> getItems(Long feedId);
}
