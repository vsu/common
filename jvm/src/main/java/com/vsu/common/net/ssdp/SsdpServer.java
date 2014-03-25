package com.vsu.common.net.ssdp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.UUID;
import java.util.Map;

/**
 * @author Victor Su
 */
public class SsdpServer extends Thread {
    private final String host;
    private final int port;
    private final String serviceName;
    private boolean running = false;

    public SsdpServer(final String host, final int port, final String serviceName) {
        this.host = host;
        this.port = port;
        this.serviceName = serviceName;
    }

    @Override
    public void start() {
        super.start();
        running = true;
    }

    @Override
    public void run() {
        final String USN = serviceName + ":" + UUID.randomUUID();
        final String location = host + ":" + port;

        SsdpSocket sock = null;

        try {
            sock = new SsdpSocket();

            SsdpNotifyMsg notifyMsg = new SsdpNotifyMsg(serviceName, SsdpConstants.SSDP_ALIVE, USN, location);
            sock.send(notifyMsg.toString());

            while (running) {
                DatagramPacket dp = sock.receive();

                String source = (dp.getAddress()).getHostAddress();

                String data = new String(dp.getData());
                String method = SsdpUtil.getMethod(data);
                Map<String, String> headers = SsdpUtil.parseHeaders(data);

                if (method.equals(SsdpConstants.SL_MSEARCH)) {
                    String searchType = headers.get(SsdpConstants.HEADER_ST);

                    if ((searchType != null) &&
                        (searchType.equals(serviceName) || searchType.equals(SsdpConstants.SSDP_ALL))) {
                        SsdpResponseMsg responseMsg = new SsdpResponseMsg(serviceName, USN, location);
                        sock.send(source, SsdpConstants.PORT, responseMsg.toString());
                    }
                }
            }

            notifyMsg = new SsdpNotifyMsg(serviceName, SsdpConstants.SSDP_BYE, USN, location);
            sock.send(notifyMsg.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sock != null) {
                sock.close();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}
