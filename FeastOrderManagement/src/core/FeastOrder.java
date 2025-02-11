/*
    I really do know that I missing the good old days
 */
package core;

import java.util.Date;

/**
 *
 * @author no-solace
 */
public class FeastOrder {

    private static int count = 0;
    private int orderID;
    private String custCode, fstCode;
    private int numOfTables;
    private Date evtDate;

    public FeastOrder(String custCode, String fstCode, int numOftables, Date evtDate) {
        this.orderID = ++count;
        this.custCode = custCode;
        this.fstCode = fstCode;
        this.numOfTables = numOftables;
        this.evtDate = evtDate;
    }

    public FeastOrder(int orderID) {
        this.orderID = orderID;
    }

    @Override
    public boolean equals(Object obj) {
        FeastOrder f = (FeastOrder) obj;
        return this.orderID == f.orderID;
    }

    public static int getCount() {
        return count;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getCustCode() {
        return custCode;
    }

    public String getFstCode() {
        return fstCode;
    }

    public int getNumOftables() {
        return numOfTables;
    }

    public Date getEvtDate() {
        return evtDate;
    }

    public void setFstCode(String fstCode) {
        this.fstCode = fstCode;
    }

    public void setNumOftables(int numOftables) {
        this.numOfTables = numOftables;
    }

    public void setEvtDate(Date evtDate) {
        this.evtDate = evtDate;
    }
}
