/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.nocturne.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author projects
 */
public interface Perform {
	public String perform(String path, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException;
}