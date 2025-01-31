/*
    lớp mô tả cho đối tượng "thực đơn?"
 */
package core;

/**
 *
 * @author no-solace
 */
public class FeastMenu {

    String fstCode, name, price, ingredients;

    public FeastMenu(String fcode, String name, String price, String ingredients) {
        this.fstCode = fstCode;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public FeastMenu(String code) {
        this.fstCode = code;
    }

    public boolean equals(Object obj) {
        FeastMenu fm = (FeastMenu) obj;
        return this.fstCode.equals(fm.fstCode);
    }

    @Override
    public String toString() {
        return fstCode + ", " + name + ", " + price + ", " + ingredients;
    }
}
