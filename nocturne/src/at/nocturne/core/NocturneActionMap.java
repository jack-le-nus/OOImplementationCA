/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.nocturne.core;

import java.util.*;

/**
 *
 * @author projects
 */
public class NocturneActionMap {

	private static final Map<String, Class<?>> actions = new HashMap<>();

	private NocturneActionMap() { }

	public static Map<String, Class<?>> getInstance() {
		return (actions);
	}
}
