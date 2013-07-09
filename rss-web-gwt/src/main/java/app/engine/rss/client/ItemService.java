package app.engine.rss.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import app.engine.rss.shared.dto.ItemDTO;
import app.engine.rss.shared.exception.ServiceException;

@RemoteServiceRelativePath("item")
public interface ItemService extends RemoteService{

	public abstract ItemDTO[] loadItems(Long feedId) throws ServiceException;

	public abstract void downloadNewItems(Long feedId) throws ServiceException;

	public abstract void markAsRead(Long itemId);

}