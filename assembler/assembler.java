import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class assembler {

    public static void main(String[] args) throws IOException {

        try {
            File myFileObject = new File("input.txt");
            Scanner myReader = new Scanner(myFileObject);

            FileWriter myWriter = new FileWriter("output.txt");

            String[] addInstruction = new String[100];

            for(int i = 0; myReader.hasNextLine(); i++) {

                // Reads line by line from input.txt and stores each line into the String Object data.
                // When i = 0, it will read the first line and store into the String Object data.
                // When i = 1, it will read the second line and store into the String Object data.
                // and it continues until the end of loop.
                String data = myReader.nextLine();

                // Prints each line
                // When i = 0, it will print the first line from the file input.txt.
                // When i = 1, it will print the second line from the file input.txt.
                // and it continues until the end of loop.
                System.out.println(data);

                // The 'if' condition checks whether null value is stored in our String Object data or not.
                // If the condition is checked true, i.e, if null value is not being stored in the String Object data,
                // the contents of the 'if' statement will be executed.
                // if the condition is checked false, i.e., if null value is being stored in the String Object data,
                // the contents of the 'if' statement won't be executed.
                if(data != null) {

                    // The contents of the data String Object is being stored in the addInstruction array in increasing
                    // order or index.
                    addInstruction[i] = data;
                }
            }

            // As we are done with reading the file and storing our file contents into an array,
            // we will close the scanner.
            myReader.close();

            // This line removes null value from String array.
            addInstruction = Arrays.stream(addInstruction).filter(s -> (s !=null && s.length() > 0)).toArray(String[]::new);

            String[] splitStr = new String[100];
            String[] splitNumber = new String[100];
            int[] parseString = new int[50];

            // Cloning or copying the String array addInstruction into a String array storeReplacement.
            String[] storeReplacement = addInstruction.clone();

            // This is the first line of our output file named output.txt.
            myWriter.write("v2.0 raw\n");

            // This loop checks the length of the String array addInstruction, and iterates until the end of array.
            for (int i = 0; i < addInstruction.length; i++) {

                // The content (instruction in our case) is split into numerous parts if the words or terms has a
                // white space in between. And, those parts are stored in the String array temp.
                // For example - let's say we have "ADD $R1, $R2, $R3" in our addInstruction[0].
                // Notice that there are three white space in the above instruction.
                // So, the instruction will be split into four parts. Namely - "ADD", "$R1,", "$R2,", "$R3".
                // These individual parts will now be stored into the String array temp.
                String[] temp = addInstruction[i].split("\\s+");

                // The first part that will be stored in the temp String array will contain the operation name.
                // For example - ADD.
                // We will match the instruction that is being denoted by the first index of the String array temp
                // with our test cases below.
                // We have a total of eight test cases. Namely - ADD, SUB, MULi, DIVi, LW, SW, J and BEQ.
                // If it matches with any of the test cases, it will go inside the 'if' or 'else if' statements.
                if(temp[0].equals("ADD")) {

                    // The instruction part (ADD in this case) is deleted along with the whitespace beside it
                    // from the String array storeReplacement. storeReplacement is updated with the new String
                    // after replacement.
                    // For example - "ADD $R1, $R2, $R3" after replacement turns into "$R1, $R2, $R3"
                    storeReplacement[i] = storeReplacement[i].replace("ADD ", "");

                    // The new content of storeReplacement String array is now cloned / copied into the String array splitStr.
                    splitStr = storeReplacement.clone();

                    // '$' sign is removed from the String.
                    // For example - "$R1, $R2, $R3" after replacement turns into "R1, R2, R3"
                    splitStr[i] = splitStr[i].replace("$", "");

                    // The alphabet 'R' is removed from the String.
                    // For example - "R1, R2, R3" after replacement turns into "1, 2, 3"
                    splitStr[i] = splitStr[i].replace("R", "");

                    // The symbol ',' is removed from the String.
                    // For example - "1, 2, 3" after replacement turns into "1 2 3"
                    splitStr[i] = splitStr[i].replace(",", "");

                    // The String array splitNumber is emptied.
                    Arrays.fill(splitNumber, null);

                    // The content of the array splitStr is split on the basis of white space, and the remaining parts
                    // are stored into the String array splitNumber.
                    // For example - "1 2 3" is split into "1", "2", and "3".
                    // These are now stored in separate index of splitNumber in ascending order.
                    splitNumber = splitStr[i].split("\\s+");

                    // Each index of splitNumber is now converted into Integer, and then stored into the Integer array
                    // parseString in the order below.
                    parseString[0] = Integer.parseInt(splitNumber[0]);
                    parseString[1] = Integer.parseInt(splitNumber[1]);
                    parseString[2] = Integer.parseInt(splitNumber[2]);

                    // Each index of parseString is now converted into Binary, and is stored into the String
                    // binary1, binary2, and binary3 respectively.
                    // Integer.toBinaryString() converts the integer number to a binary string.
                    // String.format().replace(' ', '0') helps in formatting the binary string into our desired value.
                    // For example - Integer 3 if converted into binary turns into "11". For our project, we want each
                    // integer number to be converted into a 3 digit binary string.
                    // String.format().replace(' ', '0') converts "11" into "011"
                    String binary1 = String.format("%3s", Integer.toBinaryString(parseString[0])).replace(' ', '0');
                    String binary2 = String.format("%3s", Integer.toBinaryString(parseString[1])).replace(' ', '0');
                    String binary3 = String.format("%3s", Integer.toBinaryString(parseString[2])).replace(' ', '0');

                    // Our opcode for ADD is 000. So we append 000 with the binary String binary1, binary2, and
                    // binary3. We store the result in a String object called binaryString.
                    String binaryString = "000" + binary1 + binary2 + binary3;

                    // The content of the String binaryString is converted into a decimal number, and is stored into the integer
                    // variable decimal.
                    int decimal = Integer.parseInt(binaryString,2);

                    // The content of the integer variable decimal is now converted into a hexadecimal string and is stored into
                    // a String object named hexString.
                    String hexString = Integer.toString(decimal, 16);

                    // The content of binaryString is printed out.
                    System.out.println(binaryString);

                    // The content of hexString is printed out.
                    System.out.println(hexString);

                    // write function writes the content of hexString into our output.txt file.
                    myWriter.write(hexString + "\n");

                }
                else if(temp[0].equals("SUB")) {

                    // The instruction part (SUB in this case) is deleted along with the whitespace beside it
                    // from the String array storeReplacement. storeReplacement is updated with the new String
                    // after replacement.
                    // For example - "SUB $R1, $R2, $R3" after replacement turns into "$R1, $R2, $R3"
                    storeReplacement[i] = storeReplacement[i].replace("SUB ", "");

                    // The new content of storeReplacement String array is now cloned / copied into the String array splitStr.
                    splitStr = storeReplacement.clone();

                    // '$' sign is removed from the String.
                    // For example - "$R1, $R2, $R3" after replacement turns into "R1, R2, R3"
                    splitStr[i] = splitStr[i].replace("$", "");

                    // The alphabet 'R' is removed from the String.
                    // For example - "R1, R2, R3" after replacement turns into "1, 2, 3"
                    splitStr[i] = splitStr[i].replace("R", "");

                    // The symbol ',' is removed from the String.
                    // For example - "1, 2, 3" after replacement turns into "1 2 3"
                    splitStr[i] = splitStr[i].replace(",", "");

                    // The String array splitNumber is emptied.
                    Arrays.fill(splitNumber, null);

                    // The content of the array splitStr is split on the basis of white space, and the remaining parts
                    // are stored into the String array splitNumber.
                    // For example - "1 2 3" is split into "1", "2", and "3".
                    // These are now stored in separate index of splitNumber in ascending order.
                    splitNumber = splitStr[i].split("\\s+");

                    // Each index of splitNumber is now converted into Integer, and then stored into the Integer array
                    // parseString in the order below.
                    parseString[0] = Integer.parseInt(splitNumber[0]);
                    parseString[1] = Integer.parseInt(splitNumber[1]);
                    parseString[2] = Integer.parseInt(splitNumber[2]);

                    // Each index of parseString is now converted into Binary, and is stored into the String
                    // binary1, binary2, and binary3 respectively.
                    // Integer.toBinaryString() converts the integer number to a binary string.
                    // String.format().replace(' ', '0') helps in formatting the binary string into our desired value.
                    // For example - Integer 3 if converted into binary turns into "11". For our project, we want each
                    // integer number to be converted into a 3 digit binary string.
                    // String.format().replace(' ', '0') converts "11" into "011"
                    String binary1 = String.format("%3s", Integer.toBinaryString(parseString[0])).replace(' ', '0');
                    String binary2 = String.format("%3s", Integer.toBinaryString(parseString[1])).replace(' ', '0');
                    String binary3 = String.format("%3s", Integer.toBinaryString(parseString[2])).replace(' ', '0');

                    // Our opcode for SUB is 001. So we append 001 with the binary String binary1, binary2, and
                    // binary3. We store the result in a String object called binaryString.
                    String binaryString = "001" + binary1 + binary2 + binary3;

                    // The content of the String binaryString is converted into a decimal number, and is stored into the integer
                    // variable decimal.
                    int decimal = Integer.parseInt(binaryString,2);

                    // The content of the integer variable decimal is now converted into a hexadecimal string and is stored into
                    // a String object named hexString.
                    String hexString = Integer.toString(decimal, 16);

                    // The content of binaryString is printed out.
                    System.out.println(binaryString);

                    // The content of hexString is printed out.
                    System.out.println(hexString);

                    // write function writes the content of hexString into our output.txt file.
                    myWriter.write(hexString + "\n");
                }
                else if(temp[0].equals("MULi")) {

                    // The instruction part (MULi in this case) is deleted along with the whitespace beside it
                    // from the String array storeReplacement. storeReplacement is updated with the new String
                    // after replacement.
                    // For example - "MULi $R1, $R2, 3" after replacement turns into "$R1, $R2, 3"
                    storeReplacement[i] = storeReplacement[i].replace("MULi ", "");

                    // The new content of storeReplacement String array is now cloned / copied into the String array splitStr.
                    splitStr = storeReplacement.clone();

                    // '$' sign is removed from the String.
                    // For example - "$R1, $R2, 3" after replacement turns into "R1, R2, 3"
                    splitStr[i] = splitStr[i].replace("$", "");

                    // The alphabet 'R' is removed from the String.
                    // For example - "R1, R2, 3" after replacement turns into "1, 2, 3"
                    splitStr[i] = splitStr[i].replace("R", "");

                    // The symbol ',' is removed from the String.
                    // For example - "1, 2, 3" after replacement turns into "1 2 3"
                    splitStr[i] = splitStr[i].replace(",", "");

                    // The String array splitNumber is emptied.
                    Arrays.fill(splitNumber, null);

                    // The content of the array splitStr is split on the basis of white space, and the remaining parts
                    // are stored into the String array splitNumber.
                    // For example - "1 2 3" is split into "1", "2", and "3".
                    // These are now stored in separate index of splitNumber in ascending order.
                    splitNumber = splitStr[i].split("\\s+");

                    // Each index of splitNumber is now converted into Integer, and then stored into the Integer array
                    // parseString in the order below.
                    parseString[0] = Integer.parseInt(splitNumber[0]);
                    parseString[1] = Integer.parseInt(splitNumber[1]);
                    parseString[2] = Integer.parseInt(splitNumber[2]);

                    // Each index of parseString is now converted into Binary, and is stored into the String
                    // binary1, binary2, and binary3 respectively.
                    // Integer.toBinaryString() converts the integer number to a binary string.
                    // String.format().replace(' ', '0') helps in formatting the binary string into our desired value.
                    // For example - Integer 3 if converted into binary turns into "11". For our project, we want each
                    // integer number to be converted into a 3 digit binary string.
                    // String.format().replace(' ', '0') converts "11" into "011"
                    String binary1 = String.format("%3s", Integer.toBinaryString(parseString[0])).replace(' ', '0');
                    String binary2 = String.format("%3s", Integer.toBinaryString(parseString[1])).replace(' ', '0');
                    String binary3 = String.format("%3s", Integer.toBinaryString(parseString[2])).replace(' ', '0');

                    // Our opcode for MULi is 010. So we append 010 with the binary String binary1, binary2, and
                    // binary3. We store the result in a String object called binaryString.
                    String binaryString = "010" + binary1 + binary2 + binary3;

                    // The content of the String binaryString is converted into a decimal number, and is stored into the integer
                    // variable decimal.
                    int decimal = Integer.parseInt(binaryString,2);

                    // The content of the integer variable decimal is now converted into a hexadecimal string and is stored into
                    // a String object named hexString.
                    String hexString = Integer.toString(decimal, 16);

                    // The content of binaryString is printed out.
                    System.out.println(binaryString);

                    // The content of hexString is printed out.
                    System.out.println(hexString);

                    // write function writes the content of hexString into our output.txt file.
                    myWriter.write(hexString + "\n");
                }
                else if(temp[0].equals("DIVi")) {

                    // The instruction part (DIVi in this case) is deleted along with the whitespace beside it
                    // from the String array storeReplacement. storeReplacement is updated with the new String
                    // after replacement.
                    // For example - "DIVi $R1, $R2, 3" after replacement turns into "$R1, $R2, 3"
                    storeReplacement[i] = storeReplacement[i].replace("DIVi ", "");

                    // The new content of storeReplacement String array is now cloned / copied into the String array splitStr.
                    splitStr = storeReplacement.clone();

                    // '$' sign is removed from the String.
                    // For example - "$R1, $R2, 3" after replacement turns into "R1, R2, 3"
                    splitStr[i] = splitStr[i].replace("$", "");

                    // The alphabet 'R' is removed from the String.
                    // For example - "R1, R2, 3" after replacement turns into "1, 2, 3"
                    splitStr[i] = splitStr[i].replace("R", "");

                    // The symbol ',' is removed from the String.
                    // For example - "1, 2, 3" after replacement turns into "1 2 3"
                    splitStr[i] = splitStr[i].replace(",", "");

                    // The String array splitNumber is emptied.
                    Arrays.fill(splitNumber, null);

                    // The content of the array splitStr is split on the basis of white space, and the remaining parts
                    // are stored into the String array splitNumber.
                    // For example - "1 2 3" is split into "1", "2", and "3".
                    // These are now stored in separate index of splitNumber in ascending order.
                    splitNumber = splitStr[i].split("\\s+");

                    // Each index of splitNumber is now converted into Integer, and then stored into the Integer array
                    // parseString in the order below.
                    parseString[0] = Integer.parseInt(splitNumber[0]);
                    parseString[1] = Integer.parseInt(splitNumber[1]);
                    parseString[2] = Integer.parseInt(splitNumber[2]);

                    // Each index of parseString is now converted into Binary, and is stored into the String
                    // binary1, binary2, and binary3 respectively.
                    // Integer.toBinaryString() converts the integer number to a binary string.
                    // String.format().replace(' ', '0') helps in formatting the binary string into our desired value.
                    // For example - Integer 3 if converted into binary turns into "11". For our project, we want each
                    // integer number to be converted into a 3 digit binary string.
                    // String.format().replace(' ', '0') converts "11" into "011"
                    String binary1 = String.format("%3s", Integer.toBinaryString(parseString[0])).replace(' ', '0');
                    String binary2 = String.format("%3s", Integer.toBinaryString(parseString[1])).replace(' ', '0');
                    String binary3 = String.format("%3s", Integer.toBinaryString(parseString[2])).replace(' ', '0');

                    // Our opcode for DIVi is 011. So we append 011 with the binary String binary1, binary2, and
                    // binary3. We store the result in a String object called binaryString.
                    String binaryString = "011" + binary1 + binary2 + binary3;

                    // The content of the String binaryString is converted into a decimal number, and is stored into the integer
                    // variable decimal.
                    int decimal = Integer.parseInt(binaryString,2);

                    // The content of the integer variable decimal is now converted into a hexadecimal string and is stored into
                    // a String object named hexString.
                    String hexString = Integer.toString(decimal, 16);

                    // The content of binaryString is printed out.
                    System.out.println(binaryString);

                    // The content of hexString is printed out.
                    System.out.println(hexString);

                    // write function writes the content of hexString into our output.txt file.
                    myWriter.write(hexString + "\n");
                }
                else if(temp[0].equals("LW")) {

                    // The instruction part (LW in this case) is deleted along with the whitespace beside it
                    // from the String array storeReplacement. storeReplacement is updated with the new String
                    // after replacement.
                    // For example - "LW $R1, 4($R2)" after replacement turns into "$R1, 4($R2)"
                    storeReplacement[i] = storeReplacement[i].replace("LW ", "");

                    // The new content of storeReplacement String array is now cloned / copied into the String array splitStr.
                    splitStr = storeReplacement.clone();

                    // '$' sign is removed from the String.
                    // For example - "$R1, 4($R2)" after replacement turns into "R1, 4(R2)"
                    splitStr[i] = splitStr[i].replace("$", "");

                    // The alphabet 'R' is removed from the String.
                    // For example - "R1, 4(R2)" after replacement turns into "1, 4(2)"
                    splitStr[i] = splitStr[i].replace("R", "");

                    // The symbol ',' is removed from the String.
                    // For example - "1, 4(2)" after replacement turns into "1 4 (2)"
                    splitStr[i] = splitStr[i].replace(",", "");

                    // The parenthesis '()' is removed from the String.
                    // For example - "1, 4(2)" after replacement turns into "1 4 2"
                    splitStr[i] = splitStr[i].replace("(", " ");
                    splitStr[i] = splitStr[i].replace(")", "");

                    // The String array splitNumber is emptied.
                    Arrays.fill(splitNumber, null);

                    // The content of the array splitStr is split on the basis of white space, and the remaining parts
                    // are stored into the String array splitNumber.
                    // For example - "1 4 2" is split into "1", "4", and "2".
                    // These are now stored in separate index of splitNumber in ascending order.
                    splitNumber = splitStr[i].split("\\s+");

                    // Each index of splitNumber is now converted into Integer, and then stored into the Integer array
                    // parseString in the order below.
                    parseString[0] = Integer.parseInt(splitNumber[2]);
                    parseString[1] = Integer.parseInt(splitNumber[0]);
                    parseString[2] = Integer.parseInt(splitNumber[1]);

                    // Each index of parseString is now converted into Binary, and is stored into the String
                    // binary1, binary2, and binary3 respectively.
                    // Integer.toBinaryString() converts the integer number to a binary string.
                    // String.format().replace(' ', '0') helps in formatting the binary string into our desired value.
                    // For example - Integer 3 if converted into binary turns into "11". For our project, we want each
                    // integer number to be converted into a 3 digit binary string.
                    // String.format().replace(' ', '0') converts "11" into "011"
                    String binary1 = String.format("%3s", Integer.toBinaryString(parseString[0])).replace(' ', '0');
                    String binary2 = String.format("%3s", Integer.toBinaryString(parseString[1])).replace(' ', '0');
                    String binary3 = String.format("%3s", Integer.toBinaryString(parseString[2])).replace(' ', '0');

                    // Our opcode for LW is 100. So we append 000 with the binary String binary1, binary2, and
                    // binary3. We store the result in a String object called binaryString.
                    String binaryString = "100" + binary1 + binary2 + binary3;

                    // The content of the String binaryString is converted into a decimal number, and is stored into the integer
                    // variable decimal.
                    int decimal = Integer.parseInt(binaryString,2);

                    // The content of the integer variable decimal is now converted into a hexadecimal string and is stored into
                    // a String object named hexString.
                    String hexString = Integer.toString(decimal, 16);

                    // The content of binaryString is printed out.
                    System.out.println(binaryString);

                    // The content of hexString is printed out.
                    System.out.println(hexString);

                    // write function writes the content of hexString into our output.txt file.
                    myWriter.write(hexString + "\n");
                }
                else if(temp[0].equals("SW")) {

                    // The instruction part (SW in this case) is deleted along with the whitespace beside it
                    // from the String array storeReplacement. storeReplacement is updated with the new String
                    // after replacement.
                    // For example - "SW $R1, 4($R2)" after replacement turns into "$R1, 4($R2)"
                    storeReplacement[i] = storeReplacement[i].replace("SW ", "");

                    // The new content of storeReplacement String array is now cloned / copied into the String array splitStr.
                    splitStr = storeReplacement.clone();

                    // '$' sign is removed from the String.
                    // For example - "$R1, 4($R2)" after replacement turns into "R1, 4(R2)"
                    splitStr[i] = splitStr[i].replace("$", "");

                    // The alphabet 'R' is removed from the String.
                    // For example - "R1, 4(R2)" after replacement turns into "1, 4(2)"
                    splitStr[i] = splitStr[i].replace("R", "");

                    // The symbol ',' is removed from the String.
                    // For example - "1, 2, 3" after replacement turns into "1 2 3"
                    splitStr[i] = splitStr[i].replace(",", "");

                    // The parenthesis '()' is removed from the String.
                    // For example - "1, 4(2)" after replacement turns into "1 4 2"
                    splitStr[i] = splitStr[i].replace("(", " ");
                    splitStr[i] = splitStr[i].replace(")", "");

                    // The String array splitNumber is emptied.
                    Arrays.fill(splitNumber, null);

                    // The content of the array splitStr is split on the basis of white space, and the remaining parts
                    // are stored into the String array splitNumber.
                    // For example - "1 4 2" is split into "1", "4", and "2".
                    // These are now stored in separate index of splitNumber in ascending order.
                    splitNumber = splitStr[i].split("\\s+");

                    // Each index of splitNumber is now converted into Integer, and then stored into the Integer array
                    // parseString in the order below.
                    parseString[0] = Integer.parseInt(splitNumber[2]); // 2
                    parseString[1] = Integer.parseInt(splitNumber[0]); // 0
                    parseString[2] = Integer.parseInt(splitNumber[1]); // 1

                    // Each index of parseString is now converted into Binary, and is stored into the String
                    // binary1, binary2, and binary3 respectively.
                    // Integer.toBinaryString() converts the integer number to a binary string.
                    // String.format().replace(' ', '0') helps in formatting the binary string into our desired value.
                    // For example - Integer 3 if converted into binary turns into "11". For our project, we want each
                    // integer number to be converted into a 3 digit binary string.
                    // String.format().replace(' ', '0') converts "11" into "011"
                    String binary1 = String.format("%3s", Integer.toBinaryString(parseString[0])).replace(' ', '0');
                    String binary2 = String.format("%3s", Integer.toBinaryString(parseString[1])).replace(' ', '0');
                    String binary3 = String.format("%3s", Integer.toBinaryString(parseString[2])).replace(' ', '0');

                    // Our opcode for SW is 101. So we append 101 with the binary String binary1, binary2, and
                    // binary3. We store the result in a String object called binaryString.
                    String binaryString = "101" + binary1 + binary2 + binary3;

                    // The content of the String binaryString is converted into a decimal number, and is stored into the integer
                    // variable decimal.
                    int decimal = Integer.parseInt(binaryString,2);

                    // The content of the integer variable decimal is now converted into a hexadecimal string and is stored into
                    // a String object named hexString.
                    String hexString = Integer.toString(decimal, 16);

                    // The content of binaryString is printed out.
                    System.out.println(binaryString);

                    // The content of hexString is printed out.
                    System.out.println(hexString);

                    // write function writes the content of hexString into our output.txt file.
                    myWriter.write(hexString + "\n");
                }
                else if(temp[0].equals("J")) {

                    // The instruction part (J in this case) is deleted along with the whitespace beside it
                    // from the String array storeReplacement. storeReplacement is updated with the new String
                    // after replacement.
                    // For example - "J 7" after replacement turns into "7"
                    storeReplacement[i] = storeReplacement[i].replace("J ", "");

                    // The new content of storeReplacement String array is now cloned / copied into the String array splitStr.
                    splitStr = storeReplacement.clone();

                    // The String array splitNumber is emptied.
                    Arrays.fill(splitNumber, null);

                    // The content of the array splitStr is split on the basis of white space, and the remaining parts
                    // are stored into the String array splitNumber.
                    splitNumber = splitStr[i].split("\\s+");

                    // splitNumber[0] is now converted into Integer, and then stored into the 0 index of Integer array
                    // parseString.
                    parseString[0] = Integer.parseInt(splitNumber[0]);

                    // The 0 index of parseString is now converted into Binary, and is stored into the String
                    // binary1.
                    // Integer.toBinaryString() converts the integer number to a binary string.
                    // String.format().replace(' ', '0') helps in formatting the binary string into our desired value.
                    // For example - Integer 3 if converted into binary turns into "11". For our project, we want each
                    // integer number to be converted into a 9 digit binary string (For J or jump instruction).
                    // String.format().replace(' ', '0') converts "11" into "000000011"
                    String binary1 = String.format("%9s", Integer.toBinaryString(parseString[0])).replace(' ', '0');

                    // Our opcode for J is 110. So we append 110 with the binary String binary1.
                    // We store the result in a String object called binaryString.
                    String binaryString = "111" + binary1;

                    // The content of the String binaryString is converted into a decimal number, and is stored into the integer
                    // variable decimal.
                    int decimal = Integer.parseInt(binaryString,2);

                    // The content of the integer variable decimal is now converted into a hexadecimal string and is stored into
                    // a String object named hexString.
                    String hexString = Integer.toString(decimal, 16);

                    // The content of binaryString is printed out.
                    System.out.println(binaryString);

                    // The content of hexString is printed out.
                    System.out.println(hexString);

                    // write function writes the content of hexString into our output.txt file.
                    myWriter.write(hexString + "\n");
                }
                else if(temp[0].equals("BEQ")) {

                    // The instruction part (BEQ in this case) is deleted along with the whitespace beside it
                    // from the String array storeReplacement. storeReplacement is updated with the new String
                    // after replacement.
                    // For example - "BEQ $R1, $R2, 3" after replacement turns into "$R1, $R2, 3"
                    storeReplacement[i] = storeReplacement[i].replace("BEQ ", "");

                    // The new content of storeReplacement String array is now cloned / copied into the String array splitStr.
                    splitStr = storeReplacement.clone();

                    // '$' sign is removed from the String.
                    // For example - "$R1, $R2, 3" after replacement turns into "R1, R2, 3"
                    splitStr[i] = splitStr[i].replace("$", "");

                    // The alphabet 'R' is removed from the String.
                    // For example - "R1, R2, 3" after replacement turns into "1, 2, 3"
                    splitStr[i] = splitStr[i].replace("R", "");

                    // The symbol ',' is removed from the String.
                    // For example - "1, 2, 3" after replacement turns into "1 2 3"
                    splitStr[i] = splitStr[i].replace(",", "");

                    // The String array splitNumber is emptied.
                    Arrays.fill(splitNumber, null);

                    // The content of the array splitStr is split on the basis of white space, and the remaining parts
                    // are stored into the String array splitNumber.
                    // For example - "1 2 3" is split into "1", "2", and "3".
                    // These are now stored in separate index of splitNumber in ascending order.
                    splitNumber = splitStr[i].split("\\s+");

                    // Each index of splitNumber is now converted into Integer, and then stored into the Integer array
                    // parseString in the order below.
                    parseString[0] = Integer.parseInt(splitNumber[0]);
                    parseString[1] = Integer.parseInt(splitNumber[1]);
                    parseString[2] = Integer.parseInt(splitNumber[2]);

                    // Each index of parseString is now converted into Binary, and is stored into the String
                    // binary1, binary2, and binary3 respectively.
                    // Integer.toBinaryString() converts the integer number to a binary string.
                    // String.format().replace(' ', '0') helps in formatting the binary string into our desired value.
                    // For example - Integer 3 if converted into binary turns into "11". For our project, we want each
                    // integer number to be converted into a 3 digit binary string.
                    // String.format().replace(' ', '0') converts "11" into "011"
                    String binary1 = String.format("%3s", Integer.toBinaryString(parseString[0])).replace(' ', '0');
                    String binary2 = String.format("%3s", Integer.toBinaryString(parseString[1])).replace(' ', '0');
                    String binary3 = String.format("%3s", Integer.toBinaryString(parseString[2])).replace(' ', '0');

                    // Our opcode for BEQ is 111. So we append 111 with the binary String binary1, binary2, and
                    // binary3. We store the result in a String object called binaryString.
                    String binaryString = "110" + binary1 + binary2 + binary3;

                    // The content of the String binaryString is converted into a decimal number, and is stored into the integer
                    // variable decimal.
                    int decimal = Integer.parseInt(binaryString,2);

                    // The content of the integer variable decimal is now converted into a hexadecimal string and is stored into
                    // a String object named hexString.
                    String hexString = Integer.toString(decimal, 16);

                    // The content of binaryString is printed out.
                    System.out.println(binaryString);

                    // The content of hexString is printed out.
                    System.out.println(hexString);

                    // write function writes the content of hexString into our output.txt file.
                    myWriter.write(hexString + "\n");
                }
            }

            // As we are done with writing the file, we will close the FileWriter.
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

}
