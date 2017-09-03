/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.nocturne.core;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

/**
 *
 * @author projects
 */
@HandlesTypes({Action.class})
public class NocturneInitializer implements ServletContainerInitializer {

	private static final Logger logger = Logger.getLogger(NocturneInitializer.class.getName());

	@Override
	public void onStartup(Set<Class<?>> classes, ServletContext ctx) throws ServletException {
		if (classes.size() <= 0) {
			if (logger.isLoggable(Level.INFO))
				logger.log(Level.INFO, "No @Action found. Not installing NocturneApplicationController");
			return;
		}

		if (logger.isLoggable(Level.FINE))
			logger.log(Level.FINE, "Installing NocturneApplicationController");

		ctx.addServlet("nocturne", NocturneApplicationController.class);

		Map<String, Class<?>> actions = NocturneActionMap.getInstance();
		for (Class<?> c: classes) {
			if (!Perform.class.isAssignableFrom(c)) {
				if (logger.isLoggable(Level.FINE))
					logger.log(Level.FINE, "{0} did not implement Perform; not registering"
							, c.getName());
				continue;
			}
			if (logger.isLoggable(Level.FINE))
				logger.log(Level.FINE, "Registering {0}", c.getName());

			Action action = c.getAnnotation(Action.class);
			actions.put(action.value(), c);
		}

		ctx.setAttribute("mappings", NocturneActionMap.class);
	}
	
}
