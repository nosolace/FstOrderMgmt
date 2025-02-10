/*
 * hange this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.Date;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class OrderList extends ArrayList<Order> {

    // Thông tin khách hàng và menu, chỉ đọc
    private final CustomerList customerList;
    private final MenuList menuList;

    // Constructor 2 tham số để đọc
    public OrderList(CustomerList customerList,
            MenuList menuList) {
        this.customerList = customerList;
        this.menuList = menuList;
    }

    // Hành vi thêm một order mới
    public void addOrder() {
        // Các thông tin cần nhập liệu
        String customerCode, menuCode;
        int numberOfTable;
        Date date;

        //Nhập ID khách hàng
        String pattern = "[CGK]{1}[\\d]{4}"; // customerCode format
        int customerPosition;
        // Input customer code
        customerCode = ConsoleInputter.getStr("Input Customer Code", pattern, "Code must be unique and follow format: (C|G|K)0000!").trim();
        customerPosition = customerList.indexOf(new Customer(customerCode)); // Index of customer in List
        // Check customer valid
        if (customerPosition < 0) {
            //Thông báo khách không có trong list
            System.out.println("This customer does not exist. Input new infor below");
            // Đăng kí một khách hàng mới có code vừa mới nhập
            customerList.addRegist(customerCode); // Hành vi addRegist
            customerPosition = customerList.indexOf(new Customer(customerCode));
            System.out.println(customerPosition);
        }
        //Nhập menu code
        Object objChoice = ConsoleInputter.objMenu(menuList.toArray());
        menuCode = ((Menu) objChoice).getCode();
        //Nhập số lượng bàn
        numberOfTable = ConsoleInputter.getInt("Input Number of Tables", 1, 100);
        //Nhập ngày
        date = ConsoleInputter.getDate("Input date", "dd-MM-yyyy"); // Đã hiệu chỉnh

        //Thêm order này vào danh sách
        this.add(new Order(customerCode, menuCode, numberOfTable, date));

        String seperator = "---------------------------------------------------------------------------";
        String header = seperator + "\n" + String.format("Customer order information [Order ID: %d]", this.indexOf(new Order(customerCode, menuCode, date)) + 1) + seperator;
        System.out.println(header);
    }
}
