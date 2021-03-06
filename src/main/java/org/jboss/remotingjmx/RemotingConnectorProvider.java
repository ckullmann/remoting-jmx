/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.remotingjmx;

import static org.jboss.remotingjmx.Constants.PROTOCOL_HTTPS_REMOTING_JMX;
import static org.jboss.remotingjmx.Constants.PROTOCOL_HTTP_REMOTING_JMX;
import static org.jboss.remotingjmx.Constants.PROTOCOL_REMOTE;
import static org.jboss.remotingjmx.Constants.PROTOCOL_REMOTE_HTTP;
import static org.jboss.remotingjmx.Constants.PROTOCOL_REMOTE_HTTPS;
import static org.jboss.remotingjmx.Constants.PROTOCOL_REMOTING_JMX;

import java.io.IOException;
import java.util.Map;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorProvider;
import javax.management.remote.JMXServiceURL;

import org.jboss.logging.Logger;

/**
 * The JMXConnectorProvider implementation for use with Remoting.
 *
 * @author <a href="mailto:darran.lofthouse@jboss.com">Darran Lofthouse</a>
 */
public class RemotingConnectorProvider implements JMXConnectorProvider {

    private static final Logger log = Logger.getLogger(RemotingConnectorProvider.class);

    @SuppressWarnings("deprecation")
    public JMXConnector newJMXConnector(JMXServiceURL serviceURL, Map<String, ?> environment) throws IOException {
        String protocol = serviceURL.getProtocol();
        switch (protocol) {
            case PROTOCOL_REMOTE:
            case PROTOCOL_REMOTING_JMX:
            case PROTOCOL_REMOTE_HTTP:
            case PROTOCOL_HTTP_REMOTING_JMX:
            case PROTOCOL_REMOTE_HTTPS:
            case PROTOCOL_HTTPS_REMOTING_JMX:
                return new RemotingConnector(serviceURL, environment);
            default:
                log.tracef("Protocol (%s) not recognised by this provider.", protocol);

                return null;
        }
    }

    /**
     * Get the version string of the remoting connector provider.
     *
     * @return the version string.
     */
    public static String getVersionString() {
        return Version.getVersionString();
    }

}
