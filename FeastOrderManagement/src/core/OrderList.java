/*
    Lớp mô tả cho một List đơn đặt hàng
 */
package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
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
        String customerCode, menuCode; // mã khách hàng, mã thực đơn
        int numberOfTable; // số lượng bàn
        Date date; // ngày tổ chức
        int indexOfOrder; // Biến kiểm tra index của order sắp tạo, nếu có tồn tại trong danh sách
        int indexOfCustomer; // Biến trả về index của customer sắp tạo, nếu có tồn tại trong danh sách

        String pattern = "[CGK]{1}[\\d]{4}"; // customerCode format
        // Input customer code
        customerCode = ConsoleInputter.getStr("Input Customer Code", pattern, "Code must be unique and follow format: (C|G|K)0000!").trim();
        indexOfCustomer = customerList.indexOf(new Customer(customerCode)); // Index of customer in List
        // Check customer valid
        if (indexOfCustomer < 0) {
            // Thông báo khách không có trong list
            System.out.println("This customer does not exist.");
        } else {
            //Nhập menu code
            Object objChoice = ConsoleInputter.objFeastMenu(menuList.toArray());
            // lưu gán code menu
            menuCode = ((Menu) objChoice).getCode();
            //Nhập số lượng bàn, 1000 khách là đủ nhiều hơn thì chịu
            numberOfTable = ConsoleInputter.getInt("Input Number of Tables", 1, 100);
            //Nhập ngày
            do {
                date = ConsoleInputter.getDate("Input date", "dd/MM/yyyy");
                if (date.before(new java.util.Date())) {
                    System.out.println("Must be a valid daye in the future");
                }
            } while (date.before(new java.util.Date()));
            // Kiểm tra 3 thuộc tính có bị trùng lặp không
            indexOfOrder = this.indexOf(new Order(customerCode, menuCode, date));
            if (0 > indexOfOrder) {
                //Thêm order này vào danh sách
                this.add(new Order(customerCode, menuCode, numberOfTable, date));
                // Biến ngăn cách
                String seperator = "---------------------------------------------------------------------------";
                // index của menu đã chọn
                int indexOfMenu = menuList.indexOf(new Menu(menuCode));
                Menu menu = menuList.get(indexOfMenu); // Menu đã chọn
                // Tiêu đề
                String header = seperator + "\n" + String.format("Customer order information [Order ID: %d]\n",
                        this.indexOf(new Order(customerCode, menuCode, date)) + 1) + seperator;
                // Thông tin order
                String orderInformation = String.format("%-16s: %s\n", "Code of Set Menu", menuCode)
                        + String.format("%-16s: %s\n", "Set menu name", menu.name)
                        + String.format("%-16s: %s\n", "Event date", (new SimpleDateFormat("dd/MM/yyyy")).format(date))
                        + String.format("%-16s: %d\n", "Number of tables", numberOfTable)
                        + String.format("%-16s: %,d\n", "Price", menu.price)
                        + String.format("%s:\n%s", "Ingredients", menu.ingredients.replace("#", "\n"));
                String totalCost = String.format("%-16s: %,d", "Total cost", menu.price * numberOfTable); // Tổng tiền
                System.out.println(header); // In ra tiêu đề
                System.out.println(customerList.get(indexOfCustomer).getCustomer()); // In thông tin khách hàng
                System.out.println(seperator); // In ra ngăn cách
                System.out.println(orderInformation); // In ra thông tin order
                System.out.println(seperator); // In ra ngăn cách;
                System.out.println(totalCost); // In ra tổng tiền
                System.out.println(seperator); // In ra ngăn cách;
            } else { // Order đã tồn tại
                System.out.println("Dupplicate data !");
            }
        }
    }

    public void update() {
        String menuCode;
        int numberOfTable;
        Date date;
        // Nhập vào order ID
        int orderID = ConsoleInputter.getInt("Input order ID ", 1, this.size());
        // Lấy index theo ID
        int indexOfOrder = this.indexOf(new Order(orderID));
        // Lấy đối tượng
        Order orderNeedUpdate = this.get(indexOfOrder);
        int indexOfOrderNeedUpdate;
        do {
            // Chọn lại menu 
            Object objChoice = ConsoleInputter.objFeastMenu(menuList.toArray());
            // Lấy menu code
            menuCode = ((Menu) objChoice).getCode();
            //Nhập số lượng bàn
            numberOfTable = ConsoleInputter.getInt("Input Number of Tables", 1, 100);
            //Nhập ngày
            do {
                date = ConsoleInputter.getDate("Input date", "dd/MM/yyyy");
                if (date.before(new java.util.Date())) {
                    System.out.println("No return-time-machine here");
                }
            } while (date.before(new java.util.Date()));
            indexOfOrderNeedUpdate = this.indexOf(new Order(orderNeedUpdate.getCustomerCode(), menuCode, date));
            if (indexOfOrderNeedUpdate >= 0) {
                System.out.println("Dupplicate data!");
            }
        } while (indexOfOrderNeedUpdate >= 0);
        orderNeedUpdate.setMenuCode(menuCode);
        orderNeedUpdate.setNumOfTables(numberOfTable);
        orderNeedUpdate.setEvtDate(date);
    }

    public void displayOrderDetail() {
        if (this.isEmpty()) {
            System.out.println("No data in the system.");
        } else {
            // Biến ngăn các
            String seperator = "---------------------------------------------------------------------------";
            // Biến tiêu đề
            String header = seperator + "\n" + String.format("%-4s| %-11s| %-12s| %-9s| %-9s| %-7s|%12s",
                    "ID", "Event date", "Customer ID", "Set Menu", "Price", "Tables", "Cost") + "\n" + seperator;
            // In ra tiêu đề
            System.out.println(header);
            // Vòng lặp duyệt từng phần từ trong mảng
            for (Order order : this) {
                // Biến giá thực đơn
                int price = menuList.get(menuList.indexOf(new Menu(order.getMenuCode()))).price;
                // Biến tổng chi phí
                int cost = price * order.getNumOfTables();
                // Biến chi tiết order
                String detail = String.format("%-4d| %-11s| %-12s| %-9s| %,9d| %7d|%,12d",
                        order.getOrderID(), order.getEvtDate(), order.getCustomerCode(), order.getMenuCode(), price, order.getNumOfTables(), cost);
                // In ra chi tiết order
                System.out.println(detail);
            }
            System.out.println(seperator); // In ra ngăn cách
        }
    }

    public void loadFromFile(String fname) {
        //Lấy file theo tên file
        File f = new File(fname);
        //Kiểm tra file có tồn tại
        if (!f.exists()) {
            System.out.println("tha"); // Thông báo không có file
        } else {
            try (FileInputStream fis = new FileInputStream(fname);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {
                // Biến lưu số lượng Obj của list trong saveFile sẽ được đọc đầu tiên
                int size = ois.readInt();
                Order.count = size;
                // Load danh sách vào list này
                for (int i = 0; i < size; i++) {
                    Order order = (Order) ois.readObject();
                    this.add(order);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void saveFile(String fname) {
        try (FileOutputStream fos = new FileOutputStream(fname);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Ghi vào số lượng khách hàng
            oos.writeInt(this.size());
            // Vòng lặp duyệt từng list
            for (Order order : this) {
                oos.writeObject(order); // Ghi vào file
            }
        } catch (IOException e) { // Báo lỗi nếu có
            System.out.println(e);
        }
    }
}
