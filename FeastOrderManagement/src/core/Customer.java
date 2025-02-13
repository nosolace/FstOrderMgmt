/*
    Lớp mô tả cho đối tượng "đơn đăng ký của khách hàng"
 */
package core;

import java.io.Serializable;

/**
 *
 * @author no-solace
 */
public class Customer implements Comparable<Customer>, Serializable {

    // Thuộc tính của một khách hàng
    private String code, name, phone, email;

    // Constructor 4 tham số cho hành vi tạo một đơn đăng ký khách hàng mới
    public Customer(String code, String name, String phone, String email) {
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Constructor 1 tham số thực hiện hành vi tìm kiếm theo mã
    public Customer(String code) {
        this.code = code;
    }

    // Hành vi so sánh hai khách hàng bằng nhau dựa vào ID
    @Override
    public boolean equals(Object obj) {
        Customer customer = (Customer) obj;
        return this.code.equals(customer.code);
    }

    // Getter ID
    public String getCode() {
        return code;
    }

    // Getter name
    public String getName() {
        return name;
    }

    // Getter phone
    public String getPhone() {
        return phone;
    }

    // Getter email
    public String getEmail() {
        return email;
    }

    // Hành vi formatName cho việc Tên, Họ 
    public String formatName() {
        // Cắt tên thành từng chuỗi theo " "
        String[] tokens = name.split(" ");
        // Kiểm tra họ tên có nhiều
        if (tokens.length <= 1) {
            return name;
        } else {
            // firstName
            String firstName = tokens[tokens.length - 1];
            // last và middle name
            String lastMidName = tokens[0];
            for (int i = 1; i <= tokens.length - 2; i++) {
                lastMidName = lastMidName + " " + tokens[i];
            }
            return firstName + ", " + lastMidName;
        }
    }

    // Setter Name
    public void setName(String name) {
        name = name.trim();
        if (name.matches("[a-zA-z ]{2,25}")) {
            this.name = name;
        }
    }

    // Setter Phone
    public void setPhone(String phone) {
        phone = phone.trim();
        if (phone.matches("0[1-9][0-9]{8}")) {
            this.phone = phone;
        }
    }

    // Setter Email
    public void setEmail(String email) {
        email = email.trim();
        if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            this.email = email;
        }
    }

    //Hành vi equalTo cho việc sắp xếp
    @Override
    public int compareTo(Customer other) {
        return this.formatName().compareTo(other.formatName());
    }

    //toString
    @Override
    public String toString() {
        return String.format("%-8s| %-30s| %-11s| %-30s", code, this.formatName(), phone, email);
    }

    //Hành bi getCustomer trả về thông tin cho Order
    public String getCustomer() {
        return String.format("%-16s: %s\n%-16s: %s\n%-16s: %s\n%-16s: %s",
                "Code", code, "Customer name", this.formatName(), "Phone number", phone, "Email", email);
    }
}
