package name.medin.monitoring.jmx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;

public class JMXConnection {

	private final JMXConnector connector;
	private final MBeanServerConnection connection;

	public JMXConnection(final String url, final String provider, final String username, final String password) throws IOException {
		JMXServiceURL jmxUrl = new JMXServiceURL(url);
		Map<String, Object> env = new HashMap<String, Object>();
		if (username != null) {
			env.put(Context.SECURITY_PRINCIPAL, username);
			env.put(Context.SECURITY_CREDENTIALS, password);

		}
		if (provider != null)
			env.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, provider);
		connector = JMXConnectorFactory.connect(jmxUrl, env);
		connection = connector.getMBeanServerConnection();
	}

	public void disconnect() throws IOException {
		connector.close();
	}

	public long fetch(final String object, final String attribute, final String attribute_key) throws Exception {
		Object attr = connection.getAttribute(new ObjectName(object), attribute);
		if (attr instanceof CompositeDataSupport) {
			CompositeDataSupport cds = (CompositeDataSupport) attr;
			if (attribute_key == null)
				throw new ParseError("Attribute key is null for composed data " + object);
			return parseData(cds.get(attribute_key));
		} else {
			return parseData(attr);
		}
	}

	private long parseData(final Object o) {
		if (o instanceof Number) {
			return ((Number) o).longValue();
		} else if (o instanceof Boolean) {
			boolean b = ((Boolean) o).booleanValue();
			return b ? 1 : 0;
		} else {
			return Long.parseLong(o.toString());
		}
	}

}
