/*
    Chương trình quản lý
 */
package def_package;

import core.FeastMenuList;

/**
 *
 * @author no-solace
 */
public class FeastMgmt {

    public static void main(String[] args) {
        FeastMenuList fML = new FeastMenuList();
        fML.loadFromFile("data/FeastMenu.csv");
        System.out.println(fML);
    }
}
