package de.mineclashtv;

import java.util.Random;

public class MainTest {

    public static void main(String[] args) {
        System.out.println(randomChars(69));
        // abcdefghijklmnopqrstuvwxyz
    }

    public static char[] randomChars(int n) {
        char[] buf = new char[n];
        String s = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();

        for(int i = 0; i < n; i++)
            buf[i] = s.charAt(random.nextInt(s.length()));

        return buf;
    }
}
