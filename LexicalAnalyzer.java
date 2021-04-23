import java.io.File;
import java. util. Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
public class LexicalAnalyzer {
    public void Analyzer(){
        try {
            File input = new File("C:\\Users\\User\\Desktop\\input.txt");
            Scanner sc = new Scanner(input);
            String file = "";
            String[] keywords = {"int", "float", "if", "else", "String"};
            String[] dataTypes = {"int", "float", "String"};
            ArrayList<String> keywordsArrayList = new ArrayList<>();
            while(sc.hasNextLine()) {
                String current = sc.nextLine();
                String[] currentArray = current.split(" ", 2);
                if(Arrays.asList(keywords).contains(currentArray[0])) {
                    file = file + " " + currentArray[0];
                }
            }
            System.out.print("Keywords: " + file + "\n");
            ArrayList<String> identifier = new ArrayList<String>();
            
            sc = new Scanner(input);
            while(sc.hasNextLine()) {
                String id = sc.nextLine();
                String[] idArray = id.split(" ", 2);
                if(idArray.length > 1) {
                    for(String string : dataTypes) {
                        if(string.equals(idArray[0])) {
                            String[] identifiers = idArray[1].split("( )*,( )*");
                            for(String ids: identifiers) {
                                ids = ids.replaceAll(";| ", "");
                                if(!identifier.contains(ids)) identifier.add(ids);
                            }
                        }
                    }
                }
            }
            System.out.println("Identifiers: " + identifier.toString().replaceAll("[\\[\\]]", "")); 
            LinkedList<Character> mathOperators = new LinkedList<>();
            
            sc = new Scanner(input);
            while (sc.hasNextLine()) {
                String string = sc.nextLine();
                boolean isString = false;
                for (char c : string.toCharArray()) {
                    if (isString) {
                        if (c == '"') isString = false;
                    } else {
                        if (c == '"') isString = true;
                        else if (c == '+' || c == '-' || c == '/' || c == '*' || c == '=' || c == '%') if (!mathOperators.contains(c)) mathOperators.addFirst(c);
                    }
                }
            }
            
            System.out.println("Mathematical Operators: " + mathOperators.toString().replaceAll("[\\[\\]]", ""));
            LinkedList<String> logicalOperators = new LinkedList<>();
            sc = new Scanner(input);
            while (sc.hasNextLine()) {
                String string = sc.nextLine();
                for (int i = 0; i < string.length(); i++) {
                    char charCurrent = string.charAt(i);
                    char charNext = string.charAt(i + 1 - (i == string.length() - 1? 1 : 0));
                    String operator = "" + charCurrent + charNext;
                    boolean dualSymbolOperator;
                    switch (charCurrent) {
                        case '&':
                        case '|':
                            dualSymbolOperator = charNext == charCurrent;
                        i += dualSymbolOperator? 1 : 0;
                        if (dualSymbolOperator && i < string.length() - 1 && !logicalOperators.contains(operator)) logicalOperators.add(operator);
                        else if (!dualSymbolOperator && !logicalOperators.contains(Character.toString(charCurrent))) logicalOperators.add(Character.toString(charCurrent));
                        break;
                        case '>':
                        case '<':
                        case '!':
                            dualSymbolOperator = charNext == '=';
                        i += dualSymbolOperator? 1 : 0;
                        if (dualSymbolOperator && !logicalOperators.contains(operator)) logicalOperators.add(operator);
                        else if (!dualSymbolOperator && !logicalOperators.contains(Character.toString(charCurrent))) logicalOperators.add(Character.toString(charCurrent));
                        break;
                    }
                }
            }
            
            System.out.println("Logical Operators: " + logicalOperators.toString().replaceAll("[\\[\\]]", ""));
            LinkedList<String> numbers = new LinkedList<>();
            
            sc = new Scanner(input);
            
            while(sc.hasNextLine()) {
                String string = sc.nextLine();
                char[] stringChar = string.toCharArray();
                String number = "";
                boolean foundNumber = false;
                boolean foundDecimal = false;
                boolean foundExponent = false;
                boolean foundSign = false;
                for (int i = 0; i < stringChar.length; i++) {
                    boolean isDigit = stringChar[i] >= '0' && stringChar[i] <= '9';
                    boolean isDecimal = stringChar[i] == '.';
                    boolean isExponent = stringChar[i] == 'E' || stringChar[i] == 'e';
                    boolean isSign = stringChar[i] == '+' || stringChar[i] == '-';
                    if (foundNumber) {
                        if (foundDecimal) {
                            if (isDecimal) {
                                number = "";
                                foundNumber = false;
                                foundDecimal = false;
                            } else {
                                if (foundExponent) {
                                    if (isExponent) {
                                        number = "";
                                        foundNumber = foundDecimal = foundExponent = false;
                                    } else {
                                        if (foundSign) {
                                            if (isSign) {
                                                number = "";
                                                foundNumber = foundDecimal = foundExponent = foundSign = false;
                                            } else if (isDigit) number += stringChar[i];
                                        } else {
                                            if (isSign) {
                                                number += stringChar[i];
                                                foundSign = true;
                                            } else if (isDigit) number += stringChar[i];
                                        }
                                    }
                                } else {
                                    if (isExponent) {
                                        number += stringChar[i];
                                        foundExponent = true;
                                    } else if (isSign) {
                                        number = "";
                                        foundNumber = foundDecimal = foundExponent = foundSign = false;
                                    } else if (isDigit) {
                                        number += stringChar[i];
                                    }
                                }
                            }
                        } else {
                            if (isDecimal) {
                                number += stringChar[i];
                                foundDecimal = true;
                            }
                        }
                    } else {
                        if (isDigit) {
                            if (!number.isEmpty()) {
                                if (!numbers.contains(number)) numbers.add(number);
                            }
                            number = "" + stringChar[i];
                            foundNumber = true;
                        }
                    }
                }
                
                if (!number.isEmpty()) if (!numbers.contains(number)) numbers.add(number);
            }
            System.out.println("Numerical Values: " + numbers.toString().replaceAll("[\\[\\]]", ""));
            sc = new Scanner(input);
            LinkedList<Character> others = new LinkedList<>();
            while (sc.hasNextLine()) {
                String string = sc.nextLine();
                char[] stringChar = string.toCharArray();
                for (int i = 0; i < stringChar.length; i++) {
                    boolean foundInKeywords = file.toString().replaceAll("[\\[\\],( )]", "").contains(stringChar[i] + "");
                    boolean foundInIdentifiers = identifier.toString().replaceAll("[\\[\\],( )]", "").contains(stringChar[i] + "");
                    boolean foundInMathOperators = mathOperators.contains(stringChar[i]);
                    boolean foundInLogicalOperators = logicalOperators.toString().replaceAll("[\\[\\],( )]", "").contains(stringChar[i] + "");
                    boolean foundInNumericalValues = numbers.toString().replaceAll("[\\[\\],( )]", "").contains(stringChar[i] + "");
                    boolean notSpace = stringChar[i] != ' ';
                    boolean validChar = stringChar[i] >= ' ' && stringChar[i] <= 126;
                    if (validChar && notSpace && !foundInKeywords && !foundInIdentifiers && !foundInMathOperators && !foundInLogicalOperators && ! foundInNumericalValues) {
                        if (!others.contains(stringChar[i])) others.add(stringChar[i]);
                    }
                }
            }
            String output = "";
            for (char c : others) output += " " + c;
            System.out.println("Others: " + output);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}