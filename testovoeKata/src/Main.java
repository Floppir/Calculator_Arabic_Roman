import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    public static String calc(String input) {
        String[] parts = input.split(" ");
        int result = 0;
        String output = null;
        if (parts.length == 3) {
            if ( isDigit(parts[0]) && isDigit(parts[2])){
                int b = Integer.parseInt(parts[2]);
                int a = Integer.parseInt(parts[0]);
                switch (parts[1]) {
                    case ("/") -> result = a / b;
                    case ("*") -> result = a * b;
                    case ("+") -> result = a + b;
                    case ("-") -> result = a - b;
                    default -> throw new RuntimeException("Не корректный оперант");
            }
            output = String.valueOf(result);
            } else {
                int a = romanToArabic(parts[0]);
                int b = romanToArabic(parts[2]);
                switch (parts[1]) {
                    case ("/") -> result = a / b;
                    case ("*") -> result = a * b;
                    case ("+") -> result = a + b;
                    case ("-") -> result = a - b;
                    default -> throw new RuntimeException("Не корректный оперант");
                }
            output = arabicToRoman(result);
            }
        } else {
            throw new RuntimeException();
        }
        return output;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(calc(in.nextLine()));

    }

    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((!romanNumeral.isEmpty()) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (!romanNumeral.isEmpty()) {
            throw new IllegalArgumentException(input + " не может быть преобразован в римскую цифру");
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 100)) {
            throw new IllegalArgumentException(number + " не входит в диапазон [1,100]");
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