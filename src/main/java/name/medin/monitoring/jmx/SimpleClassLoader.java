package name.medin.monitoring.jmx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class SimpleClassLoader extends URLClassLoader {
	/**
	 * A simple classloader to help dynamically load extra classes.
	 * 
	 * @throws IOException
	 */
	public SimpleClassLoader(final List<String> jars) throws IOException {
		super(buildURL(jars));
	}

	private static URL[] buildURL(final List<String> jars) throws IOException {
		List<URL> urls = new ArrayList<URL>();
		for (String jar : jars) {
			File fe = new File(jar);
			urls.add(fe.getCanonicalFile().toURI().toURL());
		}
		return urls.toArray(new URL[] {});
	}
}
