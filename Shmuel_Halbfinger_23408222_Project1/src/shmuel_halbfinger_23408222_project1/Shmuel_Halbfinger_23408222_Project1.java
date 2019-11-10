package shmuel_halbfinger_23408222_project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Shmuel_Halbfinger_23408222_Project1 {

    public static double applyOperator(char operator, double x, double y) {
        switch (operator) {
            case '+':
                return y + x;
            case '-':
                return y - x;
            case '*':
                return y * x;
            case '/':
                if (x == 0) {
                    throw new UnsupportedOperationException("Can't divide by zero");
                }
                return y / x;
        }
        return 0;
    }

    public static boolean precedence(char operator1, char operator2) {
        if (operator2 == '(' || operator2 == ')') {
            return false;
        }
        return !((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-'));
    }

    public static boolean notDigit(char c) {
        return c == '(' || c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static double calculate(String expression) {

        char[] chars = expression.replaceAll(" ", "").trim().toCharArray();

        Stack<Double> values = new ListStack<>();
        Stack<Character> operators = new ListStack<>();

        for (int i = 0; i < chars.length; i++) {

            if (chars[i] == ' ') {
                continue;
            }

            if (chars[i] >= '0' && chars[i] <= '9') {
                StringBuilder st = new StringBuilder();
                while (i < chars.length && (chars[i] >= '0' && chars[i] <= '9') || i < chars.length && chars[i] == '.') {
                    st.append(chars[i++]);
                }
                i--;
                values.push(Double.parseDouble(st.toString()));
            } else if (chars[i] == '(') {
                operators.push(chars[i]);
            } else if (chars[i] == ')') {
                while (operators.top() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*' || chars[i] == '/') {

                if (chars[i] == '-') {
                    if (i == 0 || notDigit(chars[i - 1])) {
                        StringBuilder st = new StringBuilder();
                        st.append('-');
                        i++;
                        while (i < chars.length && (chars[i] >= '0' && chars[i] <= '9') || i < chars.length && chars[i] == '.') {
                            st.append(chars[i++]);
                        }
                        i--;
                        values.push(Double.parseDouble(st.toString()));
                        continue;
                    }
                }
                while (!operators.isEmpty() && precedence(chars[i], operators.top())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(chars[i]);
            }
        }
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    public static String correctOutput(String x) {
        StringBuilder s = new StringBuilder(x.replaceAll(" ", ""));
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                while ((i < s.length() && (s.charAt(i) >= '0' && s.charAt(i) <= '9')) || (i < s.length() && s.charAt(i) == '.')) {
                    i++;
                }
                s.insert(i, ' ');
            } else if (s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/') {
                if (s.charAt(i) == '-') {
                    if (i == 0) {
                        i++;
                        while ((i < s.length() && (s.charAt(i) >= '0' && s.charAt(i) <= '9')) || (i < s.length() && s.charAt(i) == '.')) {
                            i++;
                        }
                        s.insert(i, ' ');
                        continue;
                    } else if (notDigit(s.charAt(i - 2))) {
                        i++;
                        while ((i < s.length() && (s.charAt(i) >= '0' && s.charAt(i) <= '9')) || (i < s.length() && s.charAt(i) == '.')) {
                            i++;
                        }
                        s.insert(i, ' ');
                        continue;
                    }
                }
                s.insert(i + 1, ' ');
                i++;
            }
        }
        return s.toString().trim();
    }

    public static double twoPlaces(double d) {
        DecimalFormat dfr = new DecimalFormat("#.##");
        String str = dfr.format(d);
        return Double.parseDouble(str);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String fileName = args[0];
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        List<String> lines;
        try (BufferedReader br = new BufferedReader(isr)) {
            String line;
            lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        try (FileWriter fr = new FileWriter("project1_output.txt")) {
            for (int j = 0; j < lines.size(); j++) {
                fr.write(correctOutput(lines.get(j)) + " = " + twoPlaces(calculate(lines.get(j))));
                fr.write(System.getProperty("line.separator"));

            }
        }
    }
}
