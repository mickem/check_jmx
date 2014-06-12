package name.medin.monitoring.jmx;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class CommandLineParser {

	@Option(name = "--type", usage = "The type of server to connect to (will be used when parsing the url). Specifying this as wls will allow you to use --url 1.2.3.4:7001 when connecting to WebLogServers")
	private String type = "normal";
	@Option(name = "--url", usage = "The url to connect to for example service:jmx:rmi:///jndi/rmi://localhost:1616/jmxrmi", required = true)
	private String url;
	@Option(name = "--warning", aliases = { "-w" }, usage = "Warning threshold for the value")
	private long warning = 100;
	@Option(name = "--critical", aliases = { "-c" }, usage = "Critical threshold for the value")
	private long critical = 200;
	@Option(name = "--attr", aliases = { "-A" }, usage = "Attribute to check for instance NonHeapMemoryUsage")
	private String attribute = "NonHeapMemoryUsage";
	@Option(name = "--info", aliases = { "-I" }, usage = "Attribute containing information for text output.")
	private String info_attribute;
	@Option(name = "--attr-key", aliases = { "-K" }, usage = "Compund key for --attr when checking compound attributes (multiple values) for instance used")
	private String attribute_key;
	@Option(name = "--info-key", aliases = { "-J" }, usage = "Compund key for --info when checking compound attributes (multiple values).")
	private String info_key;
	@Option(name = "--username", aliases = { "-u" }, usage = "Username to long with.")
	private String username;
	@Option(name = "--password", aliases = { "-p" }, usage = "Password to long with.")
	private String password;
	@Option(name = "--object", aliases = { "-O" }, usage = "Object to check for instance java.lang:type=Memory.")
	private String object = "java.lang:type=Memory";
	@Option(name = "--debug", usage = "Enable debug log (usefull when debuging checks).")
	private boolean debug;
	@Option(name = "--jar", usage = "Specify aditional JAR files to load.")
	private List<String> jars = new ArrayList<String>();
	@Option(name = "--provider", usage = "Used to specify the provider to use for connecting for WLS this is weblogic.management.remote.")
	private String provider;

	public CommandLineParser(final String... args) {
		CmdLineParser parser = new CmdLineParser(this);
		parser.setUsageWidth(80);
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			System.err.println("Failed to parse command line: " + e.getMessage());
			parser.printUsage(System.err);
			System.exit(Constants.RETURN_UNKNOWN);
		}
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the warning
	 */
	public long getWarning() {
		return warning;
	}

	/**
	 * @param warning
	 *            the warning to set
	 */
	public void setWarning(final long warning) {
		this.warning = warning;
	}

	/**
	 * @return the critical
	 */
	public Long getCritical() {
		return critical;
	}

	/**
	 * @param critical
	 *            the critical to set
	 */
	public void setCritical(final Long critical) {
		this.critical = critical;
	}

	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute
	 *            the attribute to set
	 */
	public void setAttribute(final String attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the info_attribute
	 */
	public String getInfo_attribute() {
		return info_attribute;
	}

	/**
	 * @param info_attribute
	 *            the info_attribute to set
	 */
	public void setInfo_attribute(final String info_attribute) {
		this.info_attribute = info_attribute;
	}

	/**
	 * @return the attribute_key
	 */
	public String getAttribute_key() {
		return attribute_key;
	}

	/**
	 * @param attribute_key
	 *            the attribute_key to set
	 */
	public void setAttribute_key(final String attribute_key) {
		this.attribute_key = attribute_key;
	}

	/**
	 * @return the info_key
	 */
	public String getInfo_key() {
		return info_key;
	}

	/**
	 * @param info_key
	 *            the info_key to set
	 */
	public void setInfo_key(final String info_key) {
		this.info_key = info_key;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the object
	 */
	public String getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(final String object) {
		this.object = object;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug
	 *            the debug to set
	 */
	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the jars
	 */
	public List<String> getJars() {
		return jars;
	}

	/**
	 * @param jars
	 *            the jars to set
	 */
	public void setJars(final List<String> jars) {
		this.jars = jars;
	}


	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(final String provider) {
		this.provider = provider;
	}

	public void parseType() {
		if ("wls".equalsIgnoreCase(getType())) {
			this.provider = "weblogic.management.remote";
			this.url = "service:jmx:t3://" + this.url + "/jndi/weblogic.management.mbeanservers.domainruntime";
		}
	}

}
