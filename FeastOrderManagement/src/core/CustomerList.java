/*
    Lớp mô tả cho đối tượng List Đăng Ký Khách Hàng
 */
package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class CustomerList extends ArrayList<Customer> {

    public void addRegist() {
        //Các biến lưu thông tin khách hàng
        String custCode, custName, phone, email;
        int pos; //Biến position (pos) trả về Index của Khách hàng trong List
        //Nhập CustomerCode, kiểm tra tính duy nhất
        do {
            String pattern = "[CGK]{1}[\\d]{4}";
            custCode = ConsoleInputter.getStr("Input Customer Code", pattern, "Code must be unique and follow format: (C|G|K)0000!").trim();
            pos = this.indexOf(new Customer(custCode));
        } while (pos >= 0);
        //Nhập tên
        custName = ConsoleInputter.getStr("Input Name", "[a-zA-Z ]{2,25}", "Name cannot be empty and be between 2 - 25 characters!").trim();
        //Nhập số đt
        phone = ConsoleInputter.getStr("Input Phone", "0[1-9][0-9]{8}", "Phone must contain 10 digits and belong to Vietnam network operator!").trim();
        //Nhập email
        email = ConsoleInputter.getStr("Input Email", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", "Email format: example@domain.com").trim();
        //Thêm đối tượng vào danh sách
        this.add(new Customer(custCode, custName, phone, email));
        //Hỏi có muốn thêm tiếp một khách hàng mới hay không?
        if (ConsoleInputter.getBoolean("Enter new customers")) {
            this.addRegist();
        }
    }

    public void update() {
        //Nhập CustomerCode để update thông tin khách hàng
        String CUSTOMER_ID_FORMAT = "[CGK]{1}[\\d]{4}";
        String custCode = ConsoleInputter.getStr("Input Customer Code", CUSTOMER_ID_FORMAT, "Code format: (C|G|K)0000!").trim();
        //Biến pos trả về index của Customer theo code đã nhập
        int pos = this.indexOf(new Customer(custCode));
        //Biến req kiểm tra sự tồn tại của Customer thông qua index (pos)
        Customer req = (pos >= 0) ? this.get(pos) : null;
        if (req == null) {//Biến req null nên Customer không có trong List
            System.out.println("This customer does not exist.");
        } else {
            //Update tên
            String custName; //Biến lưu tên update
            do {//Vòng lặp kiểm tra tên, nếu bỏ trống lấy tên cũ
                custName = ConsoleInputter.getStr("Input Name").trim();//Cho user nhập lại tên Sinh viên
                if (!custName.trim().isEmpty() && !custName.matches("^[a-zA-Z ]{2,25}")) { //Kiểm tra nếu có tên, và tên không thỏa điều kiện báo lỗi
                    System.out.println("Name must be between 2 and 20 characters!");
                }
            } while (!custName.matches("^[a-zA-Z ]{2,25}") && !custName.trim().isEmpty());//Vòng lặp thỏa khi?
            req.setCustName(custName);
            //Update phone
            String phone;
            do {
                phone = ConsoleInputter.getStr("Input Phone");
                if (!phone.trim().isEmpty() && !phone.matches("0[1-9][0-9]{8}")) { //Kiểm tra nếu có tên, và tên không thỏa điều kiện báo lỗi
                    System.out.println("Phone must contain 10 digits and belong to Vietnam network operator!");
                }
            } while (!phone.trim().isEmpty() && !phone.matches("0[1-9][0-9]{8}"));
            req.setPhone(phone);
            //Update email
            String email;
            do {
                email = ConsoleInputter.getStr("Input Email");
                if (!phone.trim().isEmpty() && !phone.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) { //Kiểm tra nếu có tên, và tên không thỏa điều kiện báo lỗi
                    System.out.println("Email format: example@domain.com");
                }
            } while (!phone.trim().isEmpty() && !phone.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
            req.setEmail(email);
            System.out.println("The customer information has been successfully updated.");
        }
    }

    public void printList() {
        if (this.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            System.out.println("---------------------------------------------------------------------------");
            System.out.printf("%-8s| %-30s| %-11s| %-30s\n", "Code", "Customer Name", "Phone", "Email");
            System.out.println("---------------------------------------------------------------------------");
            for (Customer customer : this) {
                System.out.printf("%-8s| %-30s| %-11s| %-30s\n", customer.getCustomerCode(), customer.formatName(), customer.getPhone(), customer.getEmail());
            }
            System.out.println("---------------------------------------------------------------------------");
        }
    }

    public void printByName() {
        //Người dùng nhập tên cần tìm kiếm
        String custName = ConsoleInputter.getStr("Input Name", "^[a-zA-Z ]{2,25}", "Don't left blank").toUpperCase();
        CustomerList tempList = new CustomerList();//Biến tempList lưu danh sách cần tìm
        for (Customer cr : this) {
            //Biến name lấy TÊN trong Họ và Tên của Customer
            String name = cr.getCustName().substring(cr.getCustName().lastIndexOf(" ") + 1, cr.getCustName().length()).toUpperCase();
            if (name.contains(custName)) {//kiểm tra phù hợp thêm vào danh sách
                tempList.add(cr);
            }
        }
        Collections.sort(tempList);
        tempList.printList();
    }

    public void loadFromFile(String fname) {
        File f = new File(fname);
        if (f.exists()) {
            try {
                BufferedReader bf = new BufferedReader(new FileReader(fname));
                String line;
                while ((line = bf.readLine()) != null) {
                    String[] tokens = line.split(",");
                    String code = tokens[0].trim();
                    String name = tokens[1].trim();
                    String phone = tokens[2].trim();
                    String email = tokens[3].trim();
                    Customer cr = new Customer(code, name, phone, email);
                    this.add(cr);
                }
                bf.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
