/*
    I really do know that I missing the good old days
 */
package core;

import java.util.Date;

/**
 *
 * @author no-solace
 */
public class Order {

    private static int count = 0;
    private int orderID;
    private String customerCode, menuCode;
    private int numOfTables;
    private Date date;

    public Order(String customerCode, String menuCode, int numOfTables, Date date) {
        this.orderID = ++count;
        this.customerCode = customerCode;
        this.menuCode = menuCode;
        this.numOfTables = numOfTables;
        this.date = date;
    }

    // Constructor 1 tham số cho hành vi equal
    public Order(int orderID) {
        this.orderID = orderID;
    }
    
    public Order(String customerCode, String menuCode, Date date) {
        this.customerCode = customerCode;
        this.menuCode = menuCode;
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        Order f = (Order) obj;
        return this.orderID == f.orderID || (this.customerCode.equals(f.customerCode) && this.menuCode.equals(f.menuCode) && this.date.equals(f.date));
    }


    public int getOrderID() {
        return orderID;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public Date getEvtDate() {
        return date;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public void setEvtDate(Date evtDate) {
        this.date = evtDate;
    }

}
