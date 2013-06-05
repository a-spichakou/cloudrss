package app.engine.rss.server;

import javax.servlet.ServletException;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

import app.engine.rss.server.FeedServiceImpl;

public class FeedServiceImplTestProxy extends FeedServiceImpl {
	private static final long serialVersionUID = 1912501171628785593L;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(

	new LocalDatastoreServiceTestConfig(), new LocalUserServiceTestConfig())

	.setEnvIsLoggedIn(true)

	.setEnvAuthDomain("test.com")

	.setEnvEmail("te...@test.com");

	@Override
	public void init() throws ServletException {

		super.init();

		helper.setUp();

	}

}
