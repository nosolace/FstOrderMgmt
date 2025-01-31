/*
    Lớp mô tả cho đối tượng "đơn đăng ký của khách hàng"
 */
package core;

/**
 *
 * @author no-solace
 */
public class CustReg implements Comparable<CustReg> {

    //Thuộc tính của một khách hàng
    private String custCode, custName, phone, email;

    //Constructor 4 tham số cho hành vi tạo một đơn đăng ký khách hàng mới
    public CustReg(String custCode, String custName, String phone, String email) {
        this.custCode = custCode;
        this.custName = custName;
        this.phone = phone;
        this.email = email;
    }

    //Constructor 1 tham số cho hành vi equals
    public CustReg(String custCode) {
        this.custCode = custCode;
    }

    //Override hành vi equals, cùng custCode thì bằng nhau
    @Override
    public boolean equals(Object obj) {
        CustReg cr = (CustReg) obj;
        return this.custCode.equals(cr.custCode);
    }

    public String getCustCode() {
        return custCode;
    }

    public String getCustName() {
        return custName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String formatName() {
        String name = custName.substring(custName.lastIndexOf(" ") + 1, custName.length());
        return name + ", " + custName.replaceAll(name, "").trim();
    }

    public void setCustName(String custName) {
        custName = custName.trim();
        if (custName.matches("[a-zA-z\\s]{2,25}")) {
            this.custName = custName;
        }
    }

    public void setPhone(String phone) {
        phone = phone.trim();
        if (phone.matches("0[1-9][0-9]{8}")) {
            this.phone = phone;
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(CustReg other) {
        return this.formatName().compareTo(other.formatName());
    }

    @Override
    public String toString() {
        return custCode + ", " + custName + ", " + phone + ", " + email;
    }
}
