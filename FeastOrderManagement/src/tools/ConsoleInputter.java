/*
Tạo công cụ nhập dữ liệu
 */
package tools;

import java.util.Scanner;
import core.CustRegList;

/**
 *
 * @author ADMIN
 */
public class ConsoleInputter {

    public static Scanner sc = new Scanner(System.in);

    public static boolean getBoolean(String prompt) {
        System.out.println(prompt + " (Y/N, T/F, 1/0)?: ");
        String data = sc.nextLine().trim().toUpperCase();
        char c = data.charAt(0);
        return c == 'T' || c == 'Y' || c == '1';
    }

    public static int getInt(String prompt, int min, int max) {
        int result = 0;
        if (min > max) {
            max = min + max;
            min = max - min;
            max = max - min;
        }
        do {
            System.out.println(prompt + "[" + min + ", " + max + "]: ");
            result = Integer.parseInt(sc.nextLine().trim());
            if (result < min || result > max) {
                System.out.println("Value range: " + "[" + min + ", " + max + "]");
            }
        } while (result < min || result > max);
        return result;
    }

    public static double getDouble(String prompt, double min, double max) {
        double result = 0;
        if (min > max) {
            max = min + max;
            min = max - min;
            max = max - min;
        }
        do {
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Double.parseDouble(sc.nextLine().trim());
            if (result < min || result > max) {
                System.out.println("Value range: [" + min + ", " + max + "]");
            }
        } while (result < min || result > max);
        return result;
    }

    public static String getStr(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine().trim();
    }

    public static String getStr(String prompt, String pattern, String errorMsg) {
        String data;
        boolean valid;
        do {
            System.out.print(prompt + ": ");
            data = sc.nextLine().trim();
            valid = data.matches(pattern);
            if (!valid) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return data;
    }

    public static int intMenu(Object... options) {
        int n = options.length;
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }
        return getInt("Choice ", 1, n);
    }

    public static Object objMenu(Object... options) {
        int choice = intMenu(options);
        return options[choice - 1];
    }

    public static String getPhoneNum(String prompt, String errorMsg) {
        String OperatorVN = "(0[1-9])+([0-9]{8})";
        String phone;
        boolean valid;
        do {
            System.out.println(prompt + ": ");
            phone = sc.nextLine().trim();
            valid = phone.matches(OperatorVN);
            if (!valid) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return phone;
    }

    public static void main(String[] args) {
        String fname = "data/test.txt";
        CustRegList cRL = new CustRegList();
        cRL.loadFromFile(fname);
        cRL.printList();
        cRL.printByName();
    }
}
