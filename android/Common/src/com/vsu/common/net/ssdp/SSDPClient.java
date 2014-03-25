package com.vsu.common.net.ssdp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Victor Su
 */
public class SSDPClient {
    private SSDPSocket mSocket = null;
    
    public SSDPClient() {
        try {
            mSocket = new SSDPSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
       
    public void close() {
        if (mSocket != null) {
            mSocket.close();
        }        
    }
    
    public List<Map<String, String>> discover() {
        return discover(1000);
    }
    
    public List<Map<String, String>> discover(final String serviceName) {
        return discover(serviceName, 1000);
    }
    
    public List<Map<String, String>> discover(final int timeout) {
        return discover(SSDPConstants.SSDP_ALL, 1000);
    }
    
    public List<Map<String, String>> discover(final String serviceName, final int timeout) {
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
                
        if (mSocket != null) {
            try {
                mSocket.setTimeout(timeout);
                
                SSDPSearchMsg searchMsg = new SSDPSearchMsg(serviceName);
                mSocket.send(searchMsg.toString());
                
                try {
                    while (true) {
                        DatagramPacket dp = mSocket.receive();
        
                        String data = new String(dp.getData());
                        Map<String, String> headers = SSDPUtil.parseHeaders(data);
                        
                        if (headers.containsKey(SSDPConstants.HEADER_ST)) {
                            if (headers.get(SSDPConstants.HEADER_ST).equals(serviceName)) {
                                results.add(headers);
                            }
                        }
                    }
                } catch (SocketTimeoutException e) {                
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
