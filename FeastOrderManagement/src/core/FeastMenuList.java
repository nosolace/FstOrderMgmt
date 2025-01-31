/*
    Lớp mô tả cho danh sách thực đơn
 */
package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author no-solace
 */
public class FeastMenuList extends ArrayList<FeastMenu> {

    public void loadFromFile(String fname) {
        //Kiểm tra file có tồn tại hay không?
        File f = new File(fname);
        if (f.exists()) {
            try {
                BufferedReader bf = new BufferedReader(new FileReader(fname));
                bf.readLine();
                String line;
                while ((line = bf.readLine()) != null) {
                    String[] tokens = line.split(",");
                    String fstCode = tokens[0].trim();
                    String name = tokens[1].trim();
                    String price = tokens[2].trim();
                    String ingredients = tokens[3].trim();
                    FeastMenu fm = new FeastMenu(fstCode, name, price, ingredients);
                    this.add(fm);
                }
                bf.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void printList() {
        for (FeastMenu fM : this) {
            System.out.println(fM);
        }
    }
}
