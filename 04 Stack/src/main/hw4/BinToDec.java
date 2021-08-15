package hw4;
/*
 * CSE2010 Homework #4: BinToDec.java
 *
 * Complete the code below.
 */

public class BinToDec {

    public static int binToDec(String number) {
        if (number.length() == 1) {
            if (Integer.parseInt(number) == 0) return 0;
            else return 1;
        } else {
            int numberPart = Integer.parseInt(number.substring(0,1));
            if (numberPart == 1) {
                int powerOfTwo = 1;
                for (int i = 0; i < number.length()-1; i++) {
                    powerOfTwo *= 2;
                }
                return powerOfTwo + binToDec(number.substring(1));
            } else {
                return binToDec(number.substring(1));
            }
        }
    }

    // Tail-recursion
    public static int binToDecTR(String number, int result) {
        if (number.length()== 1) {
            if (Integer.parseInt(number) == 0) return result;
            else return 1 + result;
        } else {
            int numberPart = Integer.parseInt(number.substring(0,1));
            if (numberPart == 1) {
                int powerOfTwo = 1;
                for (int i = 0; i < number.length()-1; i++) {
                    powerOfTwo *= 2;
                }
                return binToDecTR(number.substring(1), powerOfTwo + result);
            } else {
                return binToDecTR(number.substring(1), result);
            }
        }
    }

}
