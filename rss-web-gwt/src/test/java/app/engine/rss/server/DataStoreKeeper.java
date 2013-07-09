package app.engine.rss.server;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

public interface DataStoreKeeper {
	public final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(), new LocalUserServiceTestConfig()).setEnvIsLoggedIn(true)
			.setEnvAuthDomain("test.com").setEnvEmail("te...@test.com");

}
