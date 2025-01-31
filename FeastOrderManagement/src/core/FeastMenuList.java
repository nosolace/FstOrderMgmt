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
                    int price = Integer.parseInt(tokens[2].trim());
                    String ingredients = tokens[3].trim().replace("\"", "");
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
        if (this.isEmpty()) {
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
        } else {
            Collections.sort(this);
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("List of Set Menus for ordering party:");
            System.out.println("---------------------------------------------------------------------------");
            for (FeastMenu fM : this) {
                System.out.printf("%-12s: %s\n", "Code", fM.fstCode);
                System.out.printf("%-12s: %s\n", "Name", fM.name);
                System.out.printf("%-12s: %,d\n", "Price", fM.price);
                System.out.printf("%-12s:\n%s\n", "Ingredients", fM.ingredients.replace("#", "\n"));
                System.out.println("---------------------------------------------------------------------------");
            }
        }
    }
}
