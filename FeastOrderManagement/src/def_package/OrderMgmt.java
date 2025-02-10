/*
    Chương trình quản lý
 */
package def_package;

import core.CustomerList;
import core.MenuList;
import core.OrderList;

/**
 *
 * @author no-solace
 */
public class OrderMgmt {

    public static void main(String[] args) {

//        Date nowDate = new Date();
        //có hành vi before/after (tham số) trong lớp Date 
        CustomerList customers = new CustomerList();
        String fName = "customer.dat";
        customers.loadFromFile(fName);
        customers.printList();
        customers.saveFile(fName);

        MenuList menuList = new MenuList();
        menuList.loadFromFile("data\\FeastMenu.csv");

        OrderList orderList = new OrderList(customers, menuList);
        orderList.addOrder();
    }
}
