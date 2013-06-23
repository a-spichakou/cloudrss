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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Feeds implements EntryPoint {

	private final FeedServiceAsync feedService = GWT
			.create(FeedService.class);
	private final Messages messages = GWT.create(Messages.class);

	public void onModuleLoad() {
		initActionButtons();
		
		AsyncCallback<FeedDTO[]> callback = new AsyncCallback() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			public void onSuccess(Object result) {
				initFeedsInfo((FeedDTO[])result);
			}
		};
		feedService.getFeeds(callback);
	}
	
	private void initFeedsInfo(FeedDTO[] feeds)
	{
		final RootPanel newFeedInfoPanel = RootPanel.get(IFeedsUIConstants.FEED_INFO_CONTAINER);
		
		for(FeedDTO feed: feeds)
		{
			final HorizontalPanel panel = new HorizontalPanel();
			
			final Anchor feedInfo = new Anchor();
			feedInfo.setText(feed.getTitle());
			feedInfo.setHref(feed.getLink());
			panel.add(feedInfo);
			
			final Button removeFeedButton = new Button(messages.removeFeedButton());
			final ClickHandler removeHandler = new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					
				}
			};;;
			removeFeedButton.addClickHandler(removeHandler);
			panel.add(removeFeedButton);
			
			newFeedInfoPanel.add(panel);
		}
	}

	private void initActionButtons() {

		final Button addFeedButton = new Button(messages.addFeedButton());
		final TextBox urlFeed = new TextBox();
		
		final RootPanel newFeedInfoPanel = RootPanel.get(IFeedsUIConstants.NEW_FEED_INFO);
		newFeedInfoPanel.add(urlFeed);
		
		final RootPanel newFeedActionsPanel = RootPanel.get(IFeedsUIConstants.NEW_FEED_ACTIONS);
		newFeedActionsPanel.add(addFeedButton);
		
	}

}
