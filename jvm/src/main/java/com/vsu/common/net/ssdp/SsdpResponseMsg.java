package com.vsu.common.net.ssdp;

/**
 * @author Victor Su
 */
public class SsdpResponseMsg {
    private String ST;       /* service type */
    private String USN;      /* unique service name */
    private String location; /* location */

    public SsdpResponseMsg(String ST, String USN, String location) {
        this.ST = ST;
        this.USN = USN;
        this.location = location;
    }

    public String getST() {
        return ST;
    }

    public void setST(String ST) {
        this.ST = ST;
    }

    public String getUSN() {
        return USN;
    }

    public void setUSN(String USN) {
        this.USN = USN;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        content.append(SsdpConstants.SL_OK).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_ST + ": " + ST).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_USN + ": " + USN).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_LOCATION + ": " + location).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.NEWLINE);

        return content.toString();
    }
}