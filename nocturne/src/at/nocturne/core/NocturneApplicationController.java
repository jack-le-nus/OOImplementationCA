/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.nocturne.core;

import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author projects
 */
@WebServlet({"/nocturne/*", "/nocturne"})
public class NocturneApplicationController extends HttpServlet {

	private static final Logger logger = Logger.getLogger(NocturneApplicationController.class.getName());

	@Inject private BeanManager bm;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String path = req.getPathInfo();
		String result = null;

		if ((null == path) || (path.trim().length() <= 0))
			result = "/nocturne.jsp";

		else {
			result = "/mapping_not_found.jsp";
			path = path.substring(1);
			req.setAttribute("path", path);

			if (logger.isLoggable(Level.FINE))
				logger.log(Level.FINE, "Method: {0}, path: {1}"
						, new Object[]{req.getMethod().toUpperCase(), path});

			Map<String, Class<?>> actions = NocturneActionMap.getInstance();
			for (String p: actions.keySet()) {
				if (!p.startsWith(path))
					continue;
				result = null;
				Class<?> c = actions.get(p);
				req.setAttribute("actionClass", c.getName());
				Set<Bean<?>> beans = bm.getBeans(c);
				Bean<?> actionBean = bm.resolve(beans);
				CreationalContext<?> ctx = bm.createCreationalContext(actionBean);
				Object action = bm.getReference(actionBean, c, ctx);
				try {
					result = ((Perform)action).perform(path, req, resp);
				} catch (final IOException | SecurityException ex) {
					logger.log(Level.WARNING, "Exception when executing: " + c.getName(), ex);
					throw ex;
				}
				break;
			}
		}

		if ((null == result) || (result.trim().length() <= 0))
			result = "/no_result.jsp";

		if (logger.isLoggable(Level.INFO))
			logger.log(Level.INFO, "Dispatch: {0}", result);

		RequestDispatcher rd = req.getRequestDispatcher(result);
		rd.forward(req, resp);
	}
}
