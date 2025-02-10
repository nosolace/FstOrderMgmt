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
        int pos; //Biến position (pos) trả về Index của Khách hàng trong List
        // Nhập CustomerCode, kiểm tra tính duy nhất
        do {
            String pattern = "[CGK]{1}[\\d]{4}";
            code = ConsoleInputter.getStr("Input Customer Code", pattern, "Code must be unique and follow format: (C|G|K)0000!").trim();
            pos = this.indexOf(new Customer(code));
            if (pos >= 0) { // Mã khách hàng đã tồn tại
                // In báo lỗi
                System.out.println("This Customer code already exists.");
            }
        } while (pos >= 0); // Nhập đúng mã duy nhất thì dừng
        // Nhập tên
        name = ConsoleInputter.getStr("Input Name", "[a-zA-Z ]{2,25}", "Name cannot be empty and be between 2 - 25 characters!").trim();
        // Nhập số đt
        phone = ConsoleInputter.getStr("Input Phone", "0[1-9][0-9]{8}", "Phone must contain 10 digits and belong to Vietnam network operator!").trim();
        // Nhập email
        email = ConsoleInputter.getStr("Input Email", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", "Email format: example@domain.com").trim();
        // Thêm đối tượng vào danh sách
        this.add(new Customer(code, name, phone, email));
        // Hỏi có muốn thêm tiếp một khách hàng mới hay không?
        if (ConsoleInputter.getBoolean("Enter new customers")) {
            this.addRegist();
        }
    }

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

    public void update() {
        // Nhập CustomerCode để update thông tin khách hàng
        String ID_format = "[CGK]{1}[\\d]{4}";
        String custCode = ConsoleInputter.getStr("Input Customer Code", ID_format, "Code format: (C|G|K)0000!").trim();
        // Biến pos trả về index của Customer trong List
        int pos = this.indexOf(new Customer(custCode));
        // Biến req kiểm tra sự tồn tại của Customer thông qua index (pos)
        Customer customerNeedUpdate = (pos >= 0) ? this.get(pos) : null;
        if (customerNeedUpdate == null) { // Biến req null nên Customer không có trong List
            System.out.println("This customer does not exist.");
        } else {
            //Update tên
            String name; //Biến lưu tên update
            do {//Vòng lặp kiểm tra tên, nếu bỏ trống lấy tên cũ
                name = ConsoleInputter.getStr("Input Name or leave it blank.").trim();//Cho user nhập lại tên Sinh viên
                if (!name.trim().isEmpty() && !name.matches("^[a-zA-Z ]{2,25}")) { //Kiểm tra nếu có tên, và tên không thỏa điều kiện báo lỗi
                    System.out.println("Name must be between 2 and 20 characters!");
                }
            } while (!name.matches("^[a-zA-Z ]{2,25}") && !name.trim().isEmpty());//Vòng lặp thỏa khi?

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
                if (!phone.trim().isEmpty() && !phone.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) { //Kiểm tra nếu có tên, và tên không thỏa điều kiện báo lỗi
                    System.out.println("Email format: example@domain.com");
                }
            } while (!phone.trim().isEmpty() && !phone.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
            customerNeedUpdate.setEmail(email);
            System.out.println("The customer information has been successfully updated.");
        }
    }

    public void printList() {
        if (this.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            //Các biến phục vụ cho việc format.
            String SEPERATOR = "---------------------------------------------------------------------------";
            String header = SEPERATOR + "\n" + String.format("%-8s| %-30s| %-11s| %-30s\n", "Code", "Customer Name", "Phone", "Email") + "\n" + SEPERATOR;
            System.out.println(header); // In header
            for (Customer customer : this) {
                System.out.println(customer);
            }
            System.out.println(SEPERATOR); //Print footer (SEPERATOR)
        }
    }

    public void printByName() {
        // Người dùng nhập tên cần tìm kiếm
        String key = ConsoleInputter.getStr("Input Name").toUpperCase();
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

    // Đọc file
    public void loadFromFile(String fname) {
        //Lấy file theo tên file
        File f = new File(fname);
        //Kiểm tra file có tồn tại
        if (!f.exists()) {
            System.out.println("tha"); // Thông báo không có file
        } else {
            try (FileInputStream fis = new FileInputStream(fname);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {
                //Lấy danh sách khách hàng
                ArrayList<Customer> customers = (ArrayList<Customer>) ois.readObject();
                //Load danh sách vào list này
                customers.forEach((customer) -> {
                    this.add(customer);
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    // Ghi/Lưu file
    public void saveFile(String fName) {
        try (FileOutputStream fos = new FileOutputStream(fName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
