package name.medin.monitoring.jmx;

public class Constants {

	/**
	 * The plugin was able to check the service and it appeared to be
	 * functioning properly.
	 */
	public static final int RETURN_OK = 0; //

	/**
	 * The plugin was able to check the service, but it appeared to be above
	 * some "warning" threshold or did not appear to be working properly.
	 */
	public static final int RETURN_WARNING = 1;
	/**
	 * either the service was not running or it was above some "critical"
	 * threshold.
	 */
	public static final int RETURN_CRITICAL = 2; // The plugin detected that
	/**
	 * arguments were supplied to the plugin or low-level failures internal to
	 * the plugin (such as unable to fork, or open a tcp socket) that prevent it
	 * from performing the specified operation. Higher-level errors (such as
	 * name resolution errors, socket timeouts, etc) are outside of the control
	 * of plugins and should generally NOT be reported as UNKNOWN states.
	 */
	public static final int RETURN_UNKNOWN = 3; // Invalid command line

}
