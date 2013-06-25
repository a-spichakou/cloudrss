package app.engine.rss.client;

import java.util.Iterator;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * GWT UI util class
 * @author aspichakou
 *
 */
public class UIUtils {

	/**
	 * Find child widget in container
	 * @param containerId
	 * @param widgetId
	 * @return
	 */
	public static Widget findChildWidget(String containerId, String widgetId) {
		assert (containerId != null);
		assert (widgetId != null);
		final RootPanel rootPanel = RootPanel.get(containerId);
		if (rootPanel == null) {
			return null;
		}
		final Iterator<Widget> iterator = rootPanel.iterator();
		while (iterator.hasNext()) {
			final Widget widget = iterator.next();
			if (widgetId.equals(widget.getElement().getId())) {
				return widget;
			}
		}
		return null;
	}
}
