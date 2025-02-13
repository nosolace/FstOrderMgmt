/*
    Lớp mô tả cho đối tượng List Đăng Ký Khách Hàng
 */
package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import tools.ConsoleInputter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author no-solace
 */
public class CustomerList extends ArrayList<Customer> {

    //Constructor vô tri
    public CustomerList() {
    }

    // Hành vi đăng kí khách hàng mới
    public void addRegist() {
        //Các biến lưu thông tin khách hàng
        String code, name, phone, email;
        int customerIndex; //Biến position (pos) trả về Index của Khách hàng trong List
        do {
            String pattern = "[CGK]{1}[\\d]{4}"; // format Code
            // Nhập code, kiểm tra
            code = ConsoleInputter.getStr("Input Customer Code", pattern, "Code must be unique and follow format: (C|G|K)xxxx!").trim();
            customerIndex = this.indexOf(new Customer(code)); // Lấy index vừa tạo
            if (customerIndex >= 0) { // Mã khách hàng đã tồn tại
                // In báo đã tồn tại mã
                System.out.println("This Customer code already exists.");
            }
        } while (customerIndex >= 0); // Nhập đúng mã duy nhất thì dừng
        // Nhập tên
        name = ConsoleInputter.getStr("Input Name", "[a-zA-Z ]{2,25}", "Name cannot be empty and be between 2 - 25 characters!").trim();
        // Nhập số đt
        phone = ConsoleInputter.getStr("Input Phone", "0[1-9][0-9]{8}", "Phone must contain 10 digits and belong to Vietnam network operator!").trim();
        // Nhập email
        email = ConsoleInputter.getStr("Input Email", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", "Email format: example@domain.com").trim();
        // Thêm đối tượng vào danh sách
        this.add(new Customer(code, name, phone, email));
        System.out.println("The new customer has been successfully added.");
    }

    /*
    //AddRegist 1 tham số cho việc tạo khách hàng ở Order, nếu chưa đăng kí trước
    public void addRegist(String code) {
        //Các biến lưu thông tin khách hàng
        String name, phone, email;
        // Nhập tên
        name = ConsoleInputter.getStr("Input Name", "[a-zA-Z ]{2,25}", "Name cannot be empty and be between 2 - 25 characters!").trim();
        // Nhập số đt
        phone = ConsoleInputter.getStr("Input Phone", "0[1-9][0-9]{8}", "Phone must contain 10 digits and belong to Vietnam network operator!").trim();
        // Nhập email
        email = ConsoleInputter.getStr("Input Email", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", "Email format: example@domain.com").trim();
        // Thêm đối tượng vào danh sách
        this.add(new Customer(code, name, phone, email));
    }
     */
    // Hành vi update
    public void update() {
        String ID_format = "[CGK]{1}[\\d]{4}"; // Format Customer Code
        // Nhập customer code
        String custCode = ConsoleInputter.getStr("Input Customer Code", ID_format, "Code format: (C|G|K)0000!").trim();
        // Biến pos trả về index của Customer trong List
        int customerIndex = this.indexOf(new Customer(custCode));
        // Biến req kiểm tra sự tồn tại của Customer thông qua index (pos)
        Customer customerNeedUpdate = (customerIndex >= 0) ? this.get(customerIndex) : null;
        if (customerNeedUpdate == null) { // Customer không có trong List
            System.out.println("This customer does not exist.");
        } else {
            //Update tên
            String name; //Biến lưu tên update
            do {//Vòng lặp kiểm tra tên, nếu bỏ trống lấy tên cũ
                name = ConsoleInputter.getStr("Input Name or leave it blank.").trim();//Cho user nhập lại tên Sinh viên
                if (!name.trim().isEmpty() && !name.matches("^[a-zA-Z ]{2,25}")) { //Kiểm tra nếu có tên, và tên không thỏa điều kiện báo lỗi
                    System.out.println("Name must be between 2 and 20 characters!");
                }
            } while (!name.matches("^[a-zA-Z ]{2,25}") && !name.trim().isEmpty());//Vòng lặp thỏa khi

            customerNeedUpdate.setName(name);
            //Update phone
            String phone;
            do {
                phone = ConsoleInputter.getStr("Input Phone");
                if (!phone.trim().isEmpty() && !phone.matches("0[1-9][0-9]{8}")) { //Kiểm tra nếu có tên, và tên không thỏa điều kiện báo lỗi
                    System.out.println("Phone must contain 10 digits and belong to Vietnam network operator!");
                }
            } while (!phone.trim().isEmpty() && !phone.matches("0[1-9][0-9]{8}"));
            customerNeedUpdate.setPhone(phone);
            //Update email
            String email;
            do {
                email = ConsoleInputter.getStr("Input Email");
                if (!phone.trim().isEmpty() && !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) { //Kiểm tra nếu có tên, và tên không thỏa điều kiện báo lỗi
                    System.out.println("Email format: example@domain.com");
                }
            } while (!phone.trim().isEmpty() && !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
            customerNeedUpdate.setEmail(email);
            System.out.println("The customer information has been successfully updated.");
        }
    }

    public void printList() {
        if (this.isEmpty()) { // danh sách trống
            // báo danh sách trống
            System.out.println("No one matches the search criteria!");
        } else { // Danh sách không trống
            // biến ngăn cách
            String seperator = "---------------------------------------------------------------------------";
            // biến tiêu đề (header)
            String header = seperator + "\n" + String.format("%-8s| %-30s| %-11s| %-30s\n", "Code", "Customer Name", "Phone", "Email") + seperator;
            System.out.println(header); // In header
            // Duyệt từng khách hàng
            for (Customer customer : this) {
                System.out.println(customer); // In thông tin khách hàng
            }
            System.out.println(seperator); // In ngăn cách
        }
    }

    public void printByName() {
        // Người dùng nhập tên cần tìm kiếm
        String key = ConsoleInputter.getStr("Input Name", "[a-zA-Z ]{2,25}", "Name cannot be empty and be between 2 - 25 characters!").toUpperCase();
        if (key.isEmpty()) {
            System.out.println("Blank"); // cho người dùng nhập lại ? =))
        } else {
            CustomerList customers = new CustomerList(); // Biến customers lưu tên khách hàng cần tìm
            for (Customer customer : this) { // Duyệt từng khách hàng trong list
                // Kiểm tra tên có chứa tên hay không
                if (customer.getName().contains(key)) {
                    customers.add(customer); // Có thì thêm vào danh sách
                }
            }
            Collections.sort(customers); // Sắp xếp danh sách
            customers.printList(); // In danh sách ra màn hình 
        }
    }

    // Đọc file
    public void loadFromFile(String fname) {
        //Lấy file theo tên file
        File f = new File(fname);
        //Kiểm tra file có tồn tại
        if (!f.exists()) { // Không có file
            System.out.println("This file does not exist."); // Thông báo không có file
        } else {
            try (FileInputStream fis = new FileInputStream(fname);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {
                int size = ois.readInt(); // Đọc số lượng đã lưu trước đó
                for (int i = 0; i < size; i++) {
                    Customer customer = (Customer) ois.readObject();
                    this.add(customer);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    // Ghi/Lưu file
    public void saveFile(String fName) {
        try (FileOutputStream fos = new FileOutputStream(fName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(this.size()); // Lưu số lượng để dễ đọc
            for (Customer customer : this) {
                oos.writeObject(customer);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
// no longer human 
