package ru.tollandev.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


class Main {


    public static void main(String[] args) {
        ArrayList<String> list;
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        list = new ArrayList<>(Arrays.asList(text.split(" ")));
        if (checkExceptions(list) == 1) {
            System.out.println(makeCalculationRoma(list.get(0), list.get(1), list.get(2)));
        } else {
            System.out.println(makeCalculationArabic(list.get(0), list.get(1), list.get(2)));
        }
    }


    private static int checkExceptions(ArrayList<String> list) {
        boolean str1 = false;
        boolean str2 = false;
        String[] checks = {"I", "V", "X", "L", "C", "D", "M"};

        if (list.size() < 2) {
            throw new RuntimeException("//т.к. строка не является математической операцией");
        }
        if (list.size() > 3) {
            throw new RuntimeException(
                    "//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор " +
                            "(+, -, /, *)");
        }
        for (String check : checks) {
            if (list.get(0).toUpperCase().contains(check)) {
                str1 = true;
            }
            if (list.get(2).toUpperCase().contains(check)) {
                str2 = true;
            }
        }
        if ((!str1 & str2) | (str1 & !str2)) {
            throw new RuntimeException("//т.к. используются одновременно разные системы счисления ");
        }
        if (str1 & str2) {
            return 1;
        } else {
            return 0;
        }
    }


    static int makeCalculationArabic(String input1, String operation, String input2) {
        int num1 = Integer.parseInt(input1);
        int num2 = Integer.parseInt(input2);
        int result = 0;
        switch (operation) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new RuntimeException("// т.к. нельзя делить на нуль");
                }
                result = num1 / num2;
                break;
            case "*":
                result = num1 * num2;
                break;
        }
        return result;
    }


    public static String makeCalculationRoma(String input1, String operation, String input2) {
        String num1 = String.valueOf(romaToArabic(input1));
        String num2 = String.valueOf(romaToArabic(input2));
        int result = makeCalculationArabic(num1, operation, num2);
        if (result < 0) {
            throw new RuntimeException("//т.к. в римской системе нет отрицательных чисел");
        }
        return arabicToRoma(result);
    }


    static int romaToArabic(String num) {
        String romanNumeral = num.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(num + " cannot be converted to a Roman Numeral");
        }

        return result;
    }


    public static String arabicToRoma(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }


}