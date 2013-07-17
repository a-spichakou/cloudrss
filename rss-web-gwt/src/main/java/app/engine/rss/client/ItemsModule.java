package app.engine.rss.client;

import java.util.LinkedHashMap;

import app.engine.rss.shared.dto.FeedDTO;
import app.engine.rss.shared.dto.ItemDTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

/**
 * Items management GWT module
 * 
 * @author aspichakou
 * 
 */
public class ItemsModule implements EntryPoint {

	private final FeedServiceAsync feedService = GWT.create(FeedService.class);
	private final ItemServiceAsync itemService = GWT.create(ItemService.class);
	private long selectedFeedId = -1;
	private SelectItem selectItem;

	private final Messages messages = GWT.create(Messages.class);

	public void onModuleLoad() {
		initActionButtons();
		initItemsSection();
		refreshFeedsList();
	}

	/**
	 * Refresh feeds list
	 */
	private void refreshFeedsList() {
		final AsyncCallback<FeedDTO[]> callback = new GetFeedsCallback();
		feedService.getFeeds(callback);
	}

	/**
	 * Init feeds info
	 * 
	 * @param feeds
	 */
	private void initFeedsInfo(FeedDTO[] feeds) {
		final LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();		

		for (final FeedDTO feed : feeds) {
			valueMap.put(feed.getId().toString(), feed.getTitle());
		}		
		selectItem.setValueMap(valueMap);
		if(feeds.length>0)
		{
			selectItem.setValue(feeds[0].getId().toString());
			// Make drop down disabled in case count of feeds less or equals 1
			if(feeds.length==1)
			{
				selectItem.setDisabled(true);
			}
			else
			{
				selectItem.setDisabled(false);
			}
		}
		else
		{
			selectItem.setDisabled(true);
		}
		
	}

	/**
	 * Init records view
	 * @param items
	 */
	private void initRecordsView(ItemDTO[] items) {

		final SectionStack sectionStack = (SectionStack) UIUtils.findChildWidget(IItemsUIConstants.ITEMS_CONTAINER, IItemsUIConstants.ITEMS_SECTION_STACK_ID+"_wrapper");
		final Canvas[] children = sectionStack.getChildren();
		for(Canvas child:children)
		{
			sectionStack.removeChild(child);
		}		
		sectionStack.redraw();

		for (ItemDTO item : items) {
			final Long itemId = item.getId();
			final IButton markAsReadButton = new IButton(messages.markAsReadButton());
			markAsReadButton.addClickHandler(new MarkAsReadHandler(itemId));
			
			final SectionStackSection section = new SectionStackSection();

			final HTMLFlow htmlFlow = new HTMLFlow();
			htmlFlow.setContents(item.getDescription());
			section.setTitle(item.getTitle());

			section.setItems(htmlFlow);
			section.setControls(markAsReadButton);
			section.setExpanded(false);
			sectionStack.addSection(section);
		}
	}

	/**
	 * Init action buttons
	 */
	private void initActionButtons() {
		final RootPanel newFeedInfoPanel = RootPanel.get(IItemsUIConstants.FEED_INFO_CONTAINER);		
		final HLayout actionPanel = new HLayout();
		actionPanel.setMembersMargin(5);  
		actionPanel.setLayoutMargin(10); 		
		newFeedInfoPanel.add(actionPanel);
		
		selectItem = new SelectItem();
		final DynamicForm form = new DynamicForm();
		form.setWidth(250);
		
		actionPanel.addMember(form);
		form.setFields(selectItem);
		selectItem.setTitle(messages.selectFeed());  
				
		selectItem.addChangeHandler(new FeedSelectHandler());

		final IButton checkNewItemsButton = new IButton(messages.checkNewItemsButton());
		checkNewItemsButton.addClickHandler(new FeedClickHandler());				
		actionPanel.addMember(checkNewItemsButton);

		final Label errorLabel = new Label();
		errorLabel.getElement().setAttribute("id", IItemsUIConstants.ERROR_LABEL_ID);

		RootPanel.get(IItemsUIConstants.ERROR_LABEL_CONTAINER).add(errorLabel);
	}

	/**
	 * Init feed items view
	 */
	private void initItemsSection() {
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setID(IItemsUIConstants.ITEMS_SECTION_STACK_ID);

		sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
		sectionStack.setAnimateSections(true);
		sectionStack.setWidth("99%");
		sectionStack.setHeight("99%");
		sectionStack.setOverflow(Overflow.SCROLL);
		
		final RootPanel container = RootPanel.get(IItemsUIConstants.ITEMS_CONTAINER);
		container.add(sectionStack);

	}

	private final class MarkAsReadHandler implements ClickHandler {
		private final Long itemId;

		private MarkAsReadHandler(Long itemId) {
			this.itemId = itemId;
		}

		public void onClick(ClickEvent event) {
			itemService.markAsRead(itemId, new AsyncCallback<Void>() {
				
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					
				}
				
				public void onFailure(Throwable caught) {
					showError(caught, messages.markAsReadError(caught.getLocalizedMessage()));
				}
			});
		}
	}

	/**
	 * Handler for Feeds drop down
	 * @author aspichakou
	 *
	 */
	private final class FeedSelectHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			final String value = (String)selectItem.getValue();
			selectedFeedId = Long.parseLong(value);
			refreshViewForSelectedFeed(selectedFeedId);
		}

		private void refreshViewForSelectedFeed(long selectedFeedId) {
			itemService.loadItems(selectedFeedId, new LoadItemsCallBack());
		}
		
	}
	
	/**
	 * Handler for Feeds drop down
	 * @author aspichakou
	 *
	 */
	private final class FeedClickHandler implements ClickHandler {

		public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
			final String value = (String)selectItem.getValue();
			selectedFeedId = Long.parseLong(value);
			itemService.downloadNewItems(selectedFeedId, new AsyncCallback<Void>() {

				public void onFailure(Throwable caught) {
					showError(caught, messages.errorDownloadItems(caught.getLocalizedMessage()));
				}

				public void onSuccess(Void result) {
					itemService.loadItems(selectedFeedId, new LoadItemsCallBack());
				}
			});
		}
	}

	/**
	 * Get all available feeds callback
	 * 
	 * @author aspichakou
	 * 
	 */
	private final class GetFeedsCallback implements AsyncCallback<FeedDTO[]> {
		public void onFailure(Throwable caught) {
			showError(caught, messages.errorGetFeeds(caught.getLocalizedMessage()));
		}

		public void onSuccess(FeedDTO[] result) {
			initFeedsInfo(result);
		}
	}

	private final class LoadItemsCallBack implements AsyncCallback<app.engine.rss.shared.dto.ItemDTO[]> {
		public void onFailure(Throwable caught) {
			showError(caught, messages.errorLoadItems(caught.getLocalizedMessage()));
		}
	
		public void onSuccess(app.engine.rss.shared.dto.ItemDTO[] result) {
			initRecordsView(result);
		}
	}

	/**
	 * Show error to user method
	 * 
	 * @param caught
	 * @param message
	 */
	private void showError(Throwable caught, String message) {
		final Label errorLabel = (Label) UIUtils.findChildWidget(IItemsUIConstants.ERROR_LABEL_CONTAINER, IItemsUIConstants.ERROR_LABEL_ID);
		errorLabel.setText(message);
	}

}
