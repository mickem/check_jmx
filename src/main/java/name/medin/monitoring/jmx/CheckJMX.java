package name.medin.monitoring.jmx;

import java.io.IOException;

/**
 * 
 * JMXQuery is used for local or remote request of JMX attributes It requires
 * JRE 1.5 to be used for compilation and execution. Look method main for
 * description how it can be invoked.
 * 
 */
public class CheckJMX {

	/**
	 * Main entry point.
	 * 
	 * @param args
	 */
	public static void main(final String... args) {
		CommandLineParser clp = new CommandLineParser(args);

		JMXConnection connection = null;
		try {
			SimpleClassLoader scl = new SimpleClassLoader(clp.getJars());
			clp.parseType();
			Thread.currentThread().setContextClassLoader(scl);
			connection = new JMXConnection(clp.getUrl(), clp.getProvider(), clp.getUsername(), clp.getPassword());
			long attr = connection.fetch(clp.getObject(), clp.getAttribute(), clp.getAttribute_key());
			long info = 0;
			if (clp.getInfo_attribute() != null) {
				info = connection.fetch(clp.getObject(), clp.getInfo_attribute(), clp.getInfo_key());
			}
			int status = doCheck(clp.getObject(), info, attr, clp.getWarning(), clp.getCritical());
			System.exit(status);
		} catch (Exception e) {
			System.out.println("Failed to check: " + e.getMessage());
			if (clp.isDebug())
				e.printStackTrace();
			System.exit(Constants.RETURN_UNKNOWN);
		} finally {
			try {
				if (connection != null)
					connection.disconnect();
			} catch (IOException e) {
				System.out.println("Failed to close connection: " + e.getMessage());
				if (clp.isDebug())
					e.printStackTrace();
				System.exit(Constants.RETURN_UNKNOWN);
			}
		}
	}

	private static int doCheck(final String key, final long info, final long attr, final long warning, final Long critical) {
		if (attr > critical) {
			display("CRITCAL", key, attr + " > " + critical, makePerf(key, attr, warning, critical));
			return Constants.RETURN_CRITICAL;
		}
		if (attr > warning) {
			display("WARNING", key, attr + " > " + warning, makePerf(key, attr, warning, critical));
			return Constants.RETURN_WARNING;
		}
		display("OK", key, "" + attr, makePerf(key, attr, warning, critical));
		return Constants.RETURN_OK;
	}

	private static String makePerf(final String key, final long value, final long warning, final Long critical) {
		return key + "=" + value + ";" + critical + ";" + warning;
	}

	private static void display(final String tag, final String key, final String message, final String perf) {
		System.out.println(tag + ": " + key + " = " + message + "|" + perf);
	}
}
