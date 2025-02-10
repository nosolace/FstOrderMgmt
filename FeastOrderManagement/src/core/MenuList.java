/*
    Lớp mô tả cho danh sách thực đơn
 */
package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author no-solace
 */
public class MenuList extends ArrayList<Menu> {

    //Constructor không tham số để cho đẹp, vì java có mặc định cái này @@
    public MenuList() {
    }

    //Hành vi đọc file
    public void loadFromFile(String fname) {
        File f = new File(fname);
        //Kiểm tra file có tồn tại hay không?
        if (f.exists()) {
            //Nếu tồn tại thì làm
            try (BufferedReader bf = new BufferedReader(new FileReader(fname))) {
                bf.readLine(); //Bỏ một dòng đầu vì tiêu đề
                String line; //Biến line cho việc đọc từng dòng
                //Kiểm tra dòng có trống hay không?
                while ((line = bf.readLine()) != null) {
                    //Cắt các thông tin đối tượng theo dấu phẩy
                    String[] tokens = line.split(",");
                    //Lưu mã thực đơn
                    String fstCode = tokens[0].trim();
                    //Lưu tên thực đơn
                    String name = tokens[1].trim();
                    //Lưu giá thực đơn
                    int price = Integer.parseInt(tokens[2].trim());
                    //Lưu nội dung các món
                    String ingredients = tokens[3].trim().replace("\"", "");
                    //Nạp dữ liệu vào đối tượng
                    Menu fm = new Menu(fstCode, name, price, ingredients);
                    //Thêm đối tượng vào danh sách lựa chọn thực đơn
                    this.add(fm);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    //Hành vi hiển thị thực đơn
    public void displayMenu() {
        //Setup các thành phần để print format
        String seperator = "---------------------------------------------------------------------------";
        String header = seperator + "\n" + "List of Set Menus for ordering party:" + "\n" + seperator;
        if (this.isEmpty()) {//Cần thực hiện hành vi loadFromFile trước để kiểm tra
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
        } else {
            //Sắp xếp thực đơn theo giá
            Collections.sort(this);
            //In ra header
            System.out.println(header);
            //In ra từng set thực đơn
            for (Menu option : this) {
                System.out.println(option); //In thực đơn
                System.out.println(seperator); // In ngăn cách giữa các món
            }
        }
    }
}// 100% MenuList
