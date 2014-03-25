package com.vsu.common.net.ssdp;

/**
 * @author Victor Su
 */
public class SsdpNotifyMsg {
    private String NT;       /* service type */
    private String NTS;      /* notify type */
    private String USN;      /* unique service name */
    private String location; /* location */

    public SsdpNotifyMsg(String NT, String NTS, String USN, String location) {
        this.NT = NT;
        this.NTS = NTS;
        this.USN = USN;
        this.location = location;
    }

    public String getNT() {
        return NT;
    }

    public void setNT(String NT) {
        this.NT = NT;
    }

    public String getNTS() {
        return NTS;
    }

    public void setNTS(String NTS) {
        this.NTS = NTS;
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

        content.append(SsdpConstants.SL_NOTIFY).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HOST).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_NT + ": " + NT).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_NTS + ": " + NTS).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_USN + ": " + USN).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_LOCATION + ": " + location).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.NEWLINE);

        return content.toString();
    }
}