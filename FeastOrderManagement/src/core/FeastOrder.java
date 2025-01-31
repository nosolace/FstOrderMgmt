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
    private int numOftables;
    private Date evtDate;

    public FeastOrder(String custCode, String fstCode, int numOftables, Date evtDate) {
        this.orderID = ++count;
        this.custCode = custCode;
        this.fstCode = fstCode;
        this.numOftables = numOftables;
        this.evtDate = evtDate;
    }

}
