package com.vsu.common.net.ip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author Victor Su
 */
public class IpUtil {
    /**
     * Auto detects the first non-loopback and non-virtual IP address.
     * @return  A string representation of the IP address.
     */
    public static String detectIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface current = interfaces.nextElement();

                if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = current.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress current_addr = addresses.nextElement();

                    if (current_addr.isLoopbackAddress()) {
                        continue;
                    }

                    if (current_addr instanceof Inet4Address) {
                        return current_addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
        }

        return null;
    }

}
