package com.vsu.common.net.ssdp;

/**
 * @author Victor Su
 *
 * Adapted from https://code.google.com/p/android-dlna/source/browse/trunk/src/org/ray/upnp/ssdp/
 */
public class SsdpSearchMsg {
    private String ST;     /* search type */
    private int MX = 3;    /* seconds to delay response */

    public SsdpSearchMsg(String ST) {
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

        content.append(SsdpConstants.SL_MSEARCH).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HOST).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_MAN + ": " + SsdpConstants.SSDP_DISCOVER).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_MX + ": " + MX).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.HEADER_ST + ": " + ST).append(SsdpConstants.NEWLINE);
        content.append(SsdpConstants.NEWLINE);

        return content.toString();
    }
}