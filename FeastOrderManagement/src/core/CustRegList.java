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
public class CustRegList extends ArrayList<CustReg> {

    public void addRegist() {
        //Các biến lưu giá trị của Khách hàng
        String CustCode, CustName, phone, email;
        int pos; //Biến position (pos) trả về Index của Khách hàng trong List
        //Nhập CustomerCode, kiểm tra duy nhất
        do {
            String pattern = "[CGK]{1}[\\d]{4}";
            CustCode = ConsoleInputter.getStr("Input Customer Code", pattern, "Code must be unique and follow format: (C|G|K)0000!").trim();
            pos = this.indexOf(new CustReg(CustCode));
        } while (pos >= 0);
        //Nhập tên
        CustName = ConsoleInputter.getStr("Input Name", "[a-zA-Z ]{2,25}", "Name cannot be empty and be between 2 - 25 characters!").trim();
        //Nhập số đt
        phone = ConsoleInputter.getStr("Input Phone", "0[1-9][0-9]{8}", "Phone must contain 10 digits and belong to Vietnam network operator!").trim();
        //Nhập email
        email = ConsoleInputter.getStr("Input Email", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", "Email format: example@domain.com").trim();
    }

    public void update() {
        //Nhập CustomerCode để update thông tin khách hàng
        String pattern = "[CGK]{1}[\\d]{4}";
        String CustCode = ConsoleInputter.getStr("Input Customer Code", pattern, "Code must be unique and follow format: (C|G|K)0000!").trim();
        //Biến pos nhận giá trị Index của Customer trong List
        int pos = this.indexOf(new CustReg(CustCode));
        //Biến req kiểm tra tính tồn tại của Customer thông qua pos
        CustReg req = (pos >= 0) ? this.get(pos) : null;
        if (req == null) {//Customer không tồn tại
            System.out.println("This customer does not exist.");
        } else {
            //Update tên
            String CustName; //Biến lưu tên update
            do {//Vòng lặp kiểm tra tên, nếu bỏ trống lấy tên cũ
                CustName = ConsoleInputter.getStr("Input Name").trim();//Cho user nhập lại tên Sinh viên
                if (!CustName.trim().isEmpty() && !CustName.matches("^[a-zA-Z ]{2,25}")) { //Kiểm tra nếu có tên, và tên không thỏa điều kiện báo lỗi
                    System.out.println("Name must be between 2 and 20 characters!");
                }
            } while (!CustName.matches("^[a-zA-Z ]{2,25}") && !CustName.trim().isEmpty());//Vòng lặp thỏa khi?
            req.setCustName(CustName);
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
            for (CustReg cr : this) {
                System.out.printf("%-8s| %-30s| %-11s| %-30s\n", cr.getCustCode(), cr.formatName(), cr.getPhone(), cr.getEmail());
            }
            System.out.println("---------------------------------------------------------------------------");
        }
    }

    public void printByName() {
        /*
        What do we need here?
        1. Lấy tên người dùng (Tên not Họ, tên đệm) > split
        2. Lấy tên đem đi so sánh
        3. Thêm vào danh sách
        4. Sắp xếp        
         */
        //Người dùng nhập tên cần tìm kiếm
        String custName = ConsoleInputter.getStr("Input Name").toUpperCase();
        CustRegList tempList = new CustRegList();//Biến tempList lưu danh sách cần tìm
        for (CustReg cr : this) {
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
        //Kiểm tra file có tồn tại hay không?
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
                    System.out.println(code + name + phone + email);
                    CustReg cr = new CustReg(code, name, phone, email);
                    this.add(cr);
                }
                bf.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
