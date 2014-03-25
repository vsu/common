package com.vsu.common.net.ssdp;

/**
 * @author Victor Su
 *
 * Adapted from https://code.google.com/p/android-dlna/source/browse/trunk/src/org/ray/upnp/ssdp/
 */
public class SSDPSearchMsg {
    private String ST;     /* search type */
    private int MX = 3;    /* seconds to delay response */

    public SSDPSearchMsg(String ST) {
        this.ST = ST;
    }

    public String getST() {
        return ST;
    }

    public void setST(String ST) {
        this.ST = ST;
    }

    public int getMX() {
        return MX;
    }

    public void setMX(int MX) {
        this.MX = MX;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        content.append(SSDPConstants.SL_MSEARCH).append(SSDPConstants.NEWLINE);
        content.append(SSDPConstants.HOST).append(SSDPConstants.NEWLINE);
        content.append(SSDPConstants.HEADER_MAN + ": " + SSDPConstants.SSDP_DISCOVER).append(SSDPConstants.NEWLINE);
        content.append(SSDPConstants.HEADER_MX + ": " + MX).append(SSDPConstants.NEWLINE);
        content.append(SSDPConstants.HEADER_ST + ": " + ST).append(SSDPConstants.NEWLINE);
        content.append(SSDPConstants.NEWLINE);

        return content.toString();
    }
}