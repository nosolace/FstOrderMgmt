/*
    lớp mô tả cho đối tượng "thực đơn?"
 */
package core;

/**
 *
 * @author no-solace
 */
public class FeastMenu implements Comparable<FeastMenu> {

    String fstCode, name, ingredients;
    int price;

    public FeastMenu(String fstCode, String name, int price, String ingredients) {
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

    @Override
    public int compareTo(FeastMenu o) {
        return Integer.compare(this.price, o.price);
    }
}
