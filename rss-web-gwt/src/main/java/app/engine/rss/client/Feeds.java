package app.engine.rss.client;

import app.engine.rss.entity.FeedEntity;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Feeds implements EntryPoint {

	private final FeedServiceAsync feedService = GWT
			.create(FeedServiceAsync.class);

	public void onModuleLoad() {
		AsyncCallback<FeedEntity[]> callback = new AsyncCallback(){

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			public void onSuccess(Object result) {
				// TODO Auto-generated method stub
				
			}};
		feedService.getFeeds(callback );
	}
	
	private void initActionButtons()
	{
		
	}

}
