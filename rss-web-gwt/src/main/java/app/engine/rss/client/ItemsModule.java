package app.engine.rss.client;

import app.engine.rss.shared.dto.FeedDTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Items management GWT module 
 * @author aspichakou
 *
 */
public class ItemsModule implements EntryPoint {

	private final FeedServiceAsync feedService = GWT.create(FeedService.class);
	private final ItemServiceAsync itemService = GWT.create(ItemService.class);
	
	private final Messages messages = GWT.create(Messages.class);

	public void onModuleLoad() {
		initActionButtons();
		refreshFeedsList();
	}
	
	/**
	 * Refresh feeds list
	 */
	private void refreshFeedsList()
	{
		final AsyncCallback<FeedDTO[]> callback = new GetFeedsCallback();
		feedService.getFeeds(callback);	
	}

	/**
	 * Inti feeds info
	 * @param feeds
	 */
	private void initFeedsInfo(FeedDTO[] feeds) {
		final ListBox listBox = (ListBox)UIUtils.findChildWidget(IItemsUIConstants.FEED_INFO_CONTAINER, "feedListBox");
		
		listBox.clear();
		
		for (final FeedDTO feed : feeds) {
			listBox.addItem(feed.getTitle(), feed.getId().toString());
		}
		
		listBox.setWidth("100%");
		listBox.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				final String value = listBox.getValue(listBox.getSelectedIndex());
				long feedId = Long.parseLong(value);
				refreshViewForSelectedFeed(feedId);
			}

			private void refreshViewForSelectedFeed(long feedId) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * Init action buttons
	 */
	private void initActionButtons() {
		final RootPanel newFeedInfoPanel = RootPanel.get(IItemsUIConstants.FEED_INFO_CONTAINER);
		final HorizontalPanel actionPanel = new HorizontalPanel();
		newFeedInfoPanel.add(actionPanel);
		
		final ListBox listBox = new ListBox();
		actionPanel.add(listBox);
		
		final Button markAsReadButton = new Button(messages.markAsReadButton());
		actionPanel.add(markAsReadButton);
		
		final Button removeItemButton = new Button(messages.removeItemButton());
		actionPanel.add(removeItemButton);
		
		final Button checkNewItemsButton = new Button(messages.checkNewItemsButton());
		actionPanel.add(checkNewItemsButton);
		
		final Label errorLabel = new Label();
		errorLabel.getElement().setAttribute("id", IItemsUIConstants.ERROR_LABEL_ID);
		
		RootPanel.get(IItemsUIConstants.ERROR_LABEL_CONTAINER).add(errorLabel);
	}

	/**
	 * Get all available feeds callback
	 * @author aspichakou
	 *
	 */
	private final class GetFeedsCallback implements AsyncCallback<FeedDTO[]> {
		public void onFailure(Throwable caught) {
			showError(caught, messages.errorGetFeeds());
		}

		public void onSuccess(FeedDTO[] result) {
			initFeedsInfo(result);
		}
	}

	/**
	 * Show error to user method
	 * @param caught
	 * @param message
	 */
	private void showError(Throwable caught, String message)
	{	
		final Label errorLabel = (Label)UIUtils.findChildWidget(IItemsUIConstants.ERROR_LABEL_CONTAINER, IItemsUIConstants.ERROR_LABEL_ID);
		errorLabel.setText(message + ": " + caught.getLocalizedMessage());
	}

}
