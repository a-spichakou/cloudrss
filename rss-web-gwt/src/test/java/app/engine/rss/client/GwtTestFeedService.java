package app.engine.rss.client;

import app.engine.rss.shared.dto.FeedDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class GwtTestFeedService extends GWTTestCase {

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
		feedService = GWT.create(FeedService.class);
		final ServiceDefTarget target = (ServiceDefTarget) feedService;
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "gwt/feed");
	}

	public void testAddFeedService() {
		delayTestFinish(10000);
		feedService.addFeed("http://google.com", new AddFeedCallBack1());
	}

	public void testGetFeedService() {
		delayTestFinish(10000);
		feedService.addFeed("http://google.com", new GetFeedCallBack1());
	}

	public void testRemoveFeedService() {
		delayTestFinish(10000);
		feedService.addFeed("http://google.com", new AddFeedCallback2());

	}

	private final class AddFeedCallback2 implements AsyncCallback<Long> {
		private final class RemoveFeedCallback1 implements AsyncCallback<Void> {
			private final class GetFeedCallback3 implements AsyncCallback<FeedDTO> {
				public void onFailure(Throwable caught) {
					fail("Request failure: " + caught.getMessage());
				}

				public void onSuccess(FeedDTO result) {
					assertNull(result.getId());
					finishTest();
				}
			}

			private final Long addedFeedId;

			private RemoveFeedCallback1(Long addedFeedId) {
				this.addedFeedId = addedFeedId;
			}

			public void onFailure(Throwable arg0) {
				fail("Request failure: " + arg0.getMessage());
			}

			public void onSuccess(Void arg0) {
				feedService.getFeed(addedFeedId, new GetFeedCallback3());
			}
		}

		public void onFailure(Throwable caught) {
			fail("Request failure: " + caught.getMessage());
		}

		public void onSuccess(final Long addedFeedId) {
			feedService.removeFeed(addedFeedId, new RemoveFeedCallback1(addedFeedId));
		}
	}

	private final class GetFeedCallBack1 implements AsyncCallback<Long> {
		private final class GetFeedCallBack2 implements AsyncCallback<FeedDTO> {
			private Long feedId;

			private GetFeedCallBack2(Long feedId) {
				this.feedId = feedId;
			}

			public void onFailure(Throwable arg0) {
				fail("Request failure: " + arg0.getMessage());
			}

			public void onSuccess(FeedDTO arg0) {
				assertEquals(feedId, arg0.getId());
			}
		}

		public void onFailure(Throwable caught) {
			fail("Request failure: " + caught.getMessage());
		}

		public void onSuccess(Long result) {
			assertNotNull(result);
			final AsyncCallback<FeedDTO> callback = new GetFeedCallBack2(result);
			feedService.getFeed(result, callback);
			finishTest();
		}
	}

	private final class AddFeedCallBack1 implements AsyncCallback<Long> {

		public void onFailure(Throwable caught) {
			fail("Request failure: " + caught.getMessage());
		}

		public void onSuccess(Long result) {
			assertNotNull(result);
			finishTest();
		}

	}
}
