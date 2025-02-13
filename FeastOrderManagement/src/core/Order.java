/*
    Lớp mô tả cho một đơn đặt hàng
 */
package core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author no-solace
 */
public class Order implements Serializable {

    protected static int count = 0;
    private int orderID;
    private String customerCode, menuCode;
    private int numOfTables;
    private Date date;

    public Order(String customerCode, String menuCode, int numOfTables, Date date) {
        Order.count = 1 + Order.count;
        this.orderID = count;
        this.customerCode = customerCode;
        this.menuCode = menuCode;
        this.numOfTables = numOfTables;
        this.date = date;
    }

    // Constructor 1 tham số cho hành vi equal
    public Order(int orderID) {
        this.orderID = orderID;
        this.customerCode = "unknown";
        this.menuCode = "unknown";
        this.date = new Date();
    }

    public Order(String customerCode, String menuCode, Date date) {
        this.orderID = 0;
        this.customerCode = customerCode;
        this.menuCode = menuCode;
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        Order f = (Order) obj;
        if (this.orderID == f.orderID) {
            return true;
        } else {
            return this.customerCode.equals(f.customerCode) && this.menuCode.equals(f.menuCode) && this.date.equals(f.date);
        }
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

    public String getEvtDate() {
        return String.format("%s", new SimpleDateFormat("dd/MM/yyyy").format(date));
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
