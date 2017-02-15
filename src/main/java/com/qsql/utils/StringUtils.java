package com.qsql.utils;

import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthAbsoluteEven;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;

/**
 * Created by biezhi on 2017/2/15.
 */
public class StringUtils {


    public static String leftPad(String s, int n, char c) {
        StringBuffer sbuf = new StringBuffer();
        for(int i=0; i<n; i++){
            sbuf.append(c);
        }
        sbuf.append(s);
        return sbuf.toString();
    }

    public static void ps(char c, int count){
        for(int i=0; i<count; i++){
            System.out.print(c);
        }
        System.out.println();
    }

    public static String center(String s, int size) {
        return center(s, size, ' ');
    }

    public static String center(String s, int size, char pad) {
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }

    public static void out(CharSequence c){
        System.out.println(c);
    }

    public static String b(int count){
        return String.format("%"+count+"s", "");
    }

    public static void main(String[] args) {
//        ps('*', 100);
//        out(center(b(29) + "QSQL v1.0 (by biezhi)" + b(29), 100,'*'));
//        ps('*', 100);
//
//        String leftAlignFormat = "| %-15s | %-4d |%n";
//
//        System.out.format("+-----------------+------+%n");
//        System.out.format("| Column name     | ID   |%n");
//        System.out.format("+-----------------+------+%n");
//        for (int i = 0; i < 5; i++) {
//            System.out.format(leftAlignFormat, "some data" + i, i * i);
//        }
//        System.out.format("+-----------------+------+%n");

        V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
        rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
        rend.setWidth(new WidthAbsoluteEven(100));


        V2_AsciiTable tab1 = new V2_AsciiTable();
        tab1.addRule();
        tab1.addRow("QSQL v1.0 (by biezhi)").setAlignment(new char[]{'c'});
        tab1.addRule();

        RenderedTable rt = rend.render(tab1);
        System.out.println(rt);

        V2_AsciiTable at = new V2_AsciiTable();
        at.addRule();
        at.addRow(null, null, null, "Basic Infomation").setAlignment(new char[]{'c', 'c', 'c', 'c'});
        at.addRule();
        at.addRow("server_ip", "user_name", "db_name", "db_version").setAlignment(new char[]{'c', 'c', 'c', 'c'});
        at.addRule();
        at.addRow("server_ip", "user_name", "db_name", "db_version").setAlignment(new char[]{'c', 'c', 'c', 'c'});
        at.addRule();

        rt = rend.render(at);

        System.out.println(rt);

    }
}
