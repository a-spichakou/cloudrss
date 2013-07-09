package app.engine.rss.client;

import app.engine.rss.shared.dto.ItemDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class GwtTestItemService extends GWTTestCase {
	private ItemServiceAsync itemService;
	private FeedServiceAsync feedService;

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
		return "app.engine.rss.gwtJUnit";
	}

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		itemService = GWT.create(ItemService.class);
		final ServiceDefTarget target = (ServiceDefTarget) itemService;
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "gwt/item");

		feedService = GWT.create(FeedService.class);
		final ServiceDefTarget target1 = (ServiceDefTarget) feedService;
		target1.setServiceEntryPoint(GWT.getModuleBaseURL() + "gwt/feed");
	}

	public void testDownloadNewItems() {
		delayTestFinish(10000);
		feedService.addFeed("http://projects.apache.org/feeds/atom.xml", new AsyncCallback<Long>() {

			public void onSuccess(Long result) {
				itemService.downloadNewItems(result, new AsyncCallback<Void>() {

					public void onSuccess(Void result) {
						finishTest();
					}

					public void onFailure(Throwable caught) {
						fail("Unable to download new items: " + caught.getLocalizedMessage());
					}
				});
			}

			public void onFailure(Throwable caught) {
				fail("Unable to add feed: " + caught.getLocalizedMessage());

			}
		});
	}

	
	public void testLoadItems() {
		delayTestFinish(10000);
	
		feedService.addFeed("http://projects.apache.org/feeds/atom.xml", new AsyncCallback<Long>() {

			public void onSuccess(Long result) {
				assertNotNull(result);
				final Long feedId = result;
				itemService.downloadNewItems(result, new AsyncCallback<Void>() {

					public void onSuccess(Void result) {
						itemService.loadItems(feedId, new AsyncCallback<ItemDTO[]>() {
							
							public void onSuccess(ItemDTO[] result) {
								assertNotNull(result);
								assertTrue(result.length>0);
								finishTest();
							}
							
							public void onFailure(Throwable caught) {
								fail("Unable to load items: " + caught.getLocalizedMessage());
							}
						});
					}

					public void onFailure(Throwable caught) {
						fail("Unable to download new items: " + caught.getLocalizedMessage());
					}
				});
			}

			public void onFailure(Throwable caught) {
				fail("Unable to add feed: " + caught.getLocalizedMessage());

			}
		});
	}
}
