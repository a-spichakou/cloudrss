package app.engine.rss.server;

import javax.servlet.ServletException;

public class ItemServiceImplTestProxy extends ItemServiceImpl implements DataStoreKeeper {
	private static final long serialVersionUID = 1912501171628785593L;
	
	@Override
	public void init() throws ServletException {
		if(!datastoreInitialized)
		{
			super.init();
			helper.setUp();
			datastoreInitialized = true;
		}	
	}

}
