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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Feeds management GWT module 
 * @author aspichakou
 *
 */
public class FeedsModule implements EntryPoint {

	private final FeedServiceAsync feedService = GWT.create(FeedService.class);
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
		final RootPanel newFeedInfoPanel = RootPanel.get(IFeedsUIConstants.FEED_INFO_CONTAINER);
		newFeedInfoPanel.clear();

		for (final FeedDTO feed : feeds) {
			final HorizontalPanel panel = new HorizontalPanel();

			final Anchor feedInfo = new Anchor();
			feedInfo.setText(feed.getTitle());
			feedInfo.setHref(feed.getLink());
			panel.add(feedInfo);

			final Button removeFeedButton = new Button(messages.removeFeedButton());
			final ClickHandler removeHandler = new ClickHandler() {
				private Long feedId = feed.getId();
				public void onClick(ClickEvent event) {
					
					feedService.removeFeed(feedId, new AsyncCallback<Void>() {

						public void onFailure(Throwable caught) {
							showError(caught, messages.errorRemoveFeed());							
						}

						public void onSuccess(Void result) {
							refreshFeedsList();
						}
					} );
				}
			};		
			removeFeedButton.addClickHandler(removeHandler);
			panel.add(removeFeedButton);

			newFeedInfoPanel.add(panel);
		}
	}

	/**
	 * Init action buttons
	 */
	private void initActionButtons() {

		final Button addFeedButton = new Button(messages.addFeedButton());
		final ClickHandler handler = new AddNewFeedHandler();
		addFeedButton.addClickHandler(handler);
		
		final TextBox urlFeed = new TextBox();
		urlFeed.getElement().setAttribute("id", IFeedsUIConstants.NEW_FEED_URL_TXTBOX_ID);

		final RootPanel newFeedInfoPanel = RootPanel.get(IFeedsUIConstants.NEW_FEED_INFO);
		newFeedInfoPanel.add(urlFeed);

		final RootPanel newFeedActionsPanel = RootPanel.get(IFeedsUIConstants.NEW_FEED_ACTIONS);
		newFeedActionsPanel.add(addFeedButton);
		
		final Label errorLabel = new Label();
		errorLabel.getElement().setAttribute("id", IFeedsUIConstants.NEW_FEED_ERROR_ID);
		
		RootPanel.get(IFeedsUIConstants.FEED_ERROR_CONTAINER).add(errorLabel);
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
	 * Add new feed handler
	 * @author aspichakou
	 *
	 */
	private final class AddNewFeedHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			final TextBox urlTextBox = (TextBox)UIUtils.findChildWidget(IFeedsUIConstants.NEW_FEED_INFO, IFeedsUIConstants.NEW_FEED_URL_TXTBOX_ID);
			
			AsyncCallback<Long> callback = new AsyncCallback<Long>() {

				public void onFailure(Throwable caught) {
					showError(caught, messages.errorAddNewFeed());
				}

				public void onSuccess(Long result) {
					refreshFeedsList();
				}
			};
			feedService.addFeed(urlTextBox.getText(), callback);
		}
	}
	
	/**
	 * Show error to user method
	 * @param caught
	 * @param message
	 */
	private void showError(Throwable caught, String message)
	{	
		final Label errorLabel = (Label)UIUtils.findChildWidget(IFeedsUIConstants.FEED_ERROR_CONTAINER, IFeedsUIConstants.NEW_FEED_ERROR_ID);
		errorLabel.setText(message + " " + caught.getLocalizedMessage());
	}

}
