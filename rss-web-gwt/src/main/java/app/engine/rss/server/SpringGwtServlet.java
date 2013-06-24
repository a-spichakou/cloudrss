package app.engine.rss.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SpringGwtServlet extends RemoteServiceServlet {

	private static final long serialVersionUID = 4956553056300294292L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);		
	}
}