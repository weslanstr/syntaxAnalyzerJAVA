/**
 * Syntax Analyzer for COSC 455
 * by Wesley Lancaster
 * Submitted on 11/22/22
 * @ wlanca2@students.towson.edu
 **/

import jdk.dynalink.beans.StaticClass;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class MainClass {
    public static void main(String[] args){

        System.out.print("""
                |
                * Syntax Analyzer for COSC 455
                * by Wesley Lancaster
                * Submitted on 11/22/22""");

        Scanner fileName = new Scanner(System.in);
        System.out.println("\n|\nPLEASE ENTER DIRECTORY OF FILE.....");
        String name = fileName.nextLine();
        System.out.print(".....PATH ENTERED");
        fileName.close();
        File x = new File(name);

        if(x.exists()) {
            System.out.print("....."+x.getName() +  ".....EXIST" + "\n");
        }
        else {
            System.out.println("......FILE DOES NOT EXIST");
        }
        System.out.println("|");

        try {
            Scanner sc = new Scanner(x);
            next n = new next();
            position p = new position();
            kind k = new kind();
            ast a = new ast();
            value v = new value();
            int i = 0;
            int l = 1;
            boolean noError = true;
            String lexeme = " ";
            String text = " ";

            //if no more lexemes, read to next line
            if (!sc.hasNext() && !sc.hasNextLine()) {
                System.out.print(" \nEMPTY FILE. NOTHING TO READ.");
                System.exit(0);
            }
            if(sc.hasNextLine()){
                text = sc.nextLine();
                System.out.print("|\nNext Line: " + text + "\n\n");
            }

            //to check the code contains at least one instance of 'program'
            Scanner sc2 = new Scanner(x);
            String lexeme2 = " ";
            while(!lexeme2.contains("program")){

                if(sc2.hasNext()){
                    lexeme2 = sc2.next();
                }
                if(lexeme2.contains("//")){
                    break;
                }
                else if(!sc2.hasNext()){
                    System.out.print(" \nBAD TOKEN: " + lexeme2);
                    System.out.print(" \nSYNTAX ERROR DETECTED, MUST START WITH 'program'\n");
                    System.exit(0);
                }
            }

            //this is if the file is only one line long.
            if(!sc.hasNextLine()){
                Scanner txtSingle = new Scanner(text);
                while (txtSingle.hasNext()) {
                    lexeme = n.next(txtSingle); //calls next lexeme
                    System.out.println("\nlexeme being read is: " + lexeme);
                    k.kind(lexeme, text, l, txtSingle); //v.value(lexeme) is called inside the kind() class

                    if (!txtSingle.hasNext()) { //if no more lexemes, read to next line
                        if (text.contains("end")) {}
                        if(!text.matches("end")){
                            System.out.print(" \nBAD TOKEN: " + lexeme);
                            System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'end'\n");
                            p.position(l, lexeme, text);
                            System.exit(0);
                        }
                    }
                }
            }

//THE CORE LOOP---------------------------------------------------------------------------------------------------------
//this while is to iterate lines
    while (sc.hasNextLine() && noError) {
        Scanner txt = new Scanner(text);

        //this while loop is to skip empty lines:
        while (text.length() == 0) {text = sc.nextLine();}

        //this second while loop is to iterate lexemes in each line.
        while (txt.hasNext()) {

            lexeme = n.next(txt); //calls next lexeme
            System.out.println("lexeme being read is: " + lexeme);
            p.position(l, lexeme, text); //returns current position of lexeme
            k.kind(lexeme, text, l, txt); //v.value(lexeme) is called inside the kind() class
            System.out.print("\n");

            if (!txt.hasNext()) { //if no more lexemes, read to next line
                text = sc.nextLine();
                System.out.print("|\n");
                System.out.print("|\nNext Line: " + text + "\n\n");
                i = 0;
                l++;
            }
        }

        //this checks the conditions end the last line
        if (!sc.hasNextLine()) { //internal to make sure the last line is called and ends with "end"
            if (text.contains("end")) {
                p.position(l, lexeme, text);
                System.out.print("kind is keyword program: end");
                v.value(lexeme, noError);
            }

            if(!text.matches("end")){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'end'?\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }
        txt.close();
    }
            System.out.print("""
                    
                    |                             
                    |     THE CODE HAS PASSED     
                    |                             
                    * Syntax Analyzer for COSC455 
                    * by Wesley Lancaster         
                    * Submitted on 11/22/22
                    |""");
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: file was not found (exception e)");
        }
    }
}

//THE NEXT METH---------------------------------------------------------------------------------------------------------
class next extends MainClass {
    public String next(Scanner txt) {
        String lexeme = txt.next();
        return lexeme;
    }
}

//THE POSITION METH-----------------------------------------------------------------------------------------------------
class position extends MainClass {
    public void position(int l, String lexeme, String text) {
        int i = text.indexOf(lexeme) - 2;
        if(i < 0){i = 0;}
            System.out.println("Line: "+l+ " Index: " + i);
    }
}

//THE VALUE METH--------------------------------------------------------------------------------------------------------
class value extends MainClass {
    public void value(String lexeme, boolean noError) {
        boolean result = lexeme.matches("[0-9]+");
        boolean symbol = lexeme.matches("[(,),{,},:,;,=]+");

        if(noError) {
            if (result) {
                System.out.print("\nvalue: NUM");
            } else if (symbol) {
                //System.out.print("\nvalue: NULL");
            } else {
                System.out.print("\nvalue: ID");
            }
        }
        else{
            System.out.print("\nvalue: ERROR");
            //System.exit(0);
        }
    }
}

//THE KIND METH---------------------------------------------------------------------------------------------------------
class kind extends MainClass {
    boolean noError = true;

    public Scanner kind(String lexeme, String text, int l, Scanner txt) {
        kind k = new kind();
        position p = new position();
        value v = new value();

        String letter = "";
        String number = "";
        String symbol = "";
        String operator = "";
        String previous = "";
        String ID = "ba";

        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        StringBuilder stringBuilder4 = new StringBuilder();


        for (int i = 0; i < lexeme.length(); i++) {
            if (lexeme.charAt(i) == 'a' || lexeme.charAt(i) == 'b' || lexeme.charAt(i) == 'c' || lexeme.charAt(i) == 'd'
                    || lexeme.charAt(i) == 'e' || lexeme.charAt(i) == 'f' || lexeme.charAt(i) == 'g' || lexeme.charAt(i) == 'h'
                    || lexeme.charAt(i) == 'i' || lexeme.charAt(i) == 'j' || lexeme.charAt(i) == 'k' || lexeme.charAt(i) == 'l'
                    || lexeme.charAt(i) == 'm' || lexeme.charAt(i) == 'n' || lexeme.charAt(i) == 'o' || lexeme.charAt(i) == 'p'
                    || lexeme.charAt(i) == 'q' || lexeme.charAt(i) == 'r' || lexeme.charAt(i) == 's' || lexeme.charAt(i) == 't'
                    || lexeme.charAt(i) == 'u' || lexeme.charAt(i) == 'v' || lexeme.charAt(i) == 'w' || lexeme.charAt(i) == 'x'
                    || lexeme.charAt(i) == 'y' || lexeme.charAt(i) == 'z'
                    || lexeme.charAt(i) == 'A' || lexeme.charAt(i) == 'B' || lexeme.charAt(i) == 'C' || lexeme.charAt(i) == 'D'
                    || lexeme.charAt(i) == 'E' || lexeme.charAt(i) == 'F' || lexeme.charAt(i) == 'G' || lexeme.charAt(i) == 'H'
                    || lexeme.charAt(i) == 'I' || lexeme.charAt(i) == 'J' || lexeme.charAt(i) == 'K' || lexeme.charAt(i) == 'L'
                    || lexeme.charAt(i) == 'M' || lexeme.charAt(i) == 'N' || lexeme.charAt(i) == 'O' || lexeme.charAt(i) == 'P'
                    || lexeme.charAt(i) == 'Q' || lexeme.charAt(i) == 'R' || lexeme.charAt(i) == 'S' || lexeme.charAt(i) == 'T'
                    || lexeme.charAt(i) == 'U' || lexeme.charAt(i) == 'V' || lexeme.charAt(i) == 'W' || lexeme.charAt(i) == 'X'
                    || lexeme.charAt(i) == 'Y' || lexeme.charAt(i) == 'Z') {
                char chaL = lexeme.charAt(i);
                stringBuilder1.append(letter).append(chaL);
            }
            if (lexeme.charAt(i) == '1' || lexeme.charAt(i) == '2' || lexeme.charAt(i) == '3' || lexeme.charAt(i) == '4'
                    || lexeme.charAt(i) == '5' || lexeme.charAt(i) == '6' || lexeme.charAt(i) == '7' || lexeme.charAt(i) == '8'
                    || lexeme.charAt(i) == '9' || lexeme.charAt(i) == '0') {
                char chaN = lexeme.charAt(i);
                stringBuilder2.append(number).append(chaN);
                //v.value(number, noError);
            }
            if (lexeme.charAt(i) == '{' || lexeme.charAt(i) == '}' || lexeme.charAt(i) == '(' || lexeme.charAt(i) == ')'
                    || lexeme.charAt(i) == ';' || lexeme.charAt(i) == '[' || lexeme.charAt(i) == ']' || lexeme.charAt(i) == ':') {
                char chaS = lexeme.charAt(i);
                stringBuilder3.append(symbol).append(chaS);
            }
            if (lexeme.charAt(i) == '+' || lexeme.charAt(i) == '-' || lexeme.charAt(i) == '/' || lexeme.charAt(i) == '*'
                    || lexeme.charAt(i) == '<' || lexeme.charAt(i) == '>' || lexeme.charAt(i) == '=' || lexeme.charAt(i) == '!'
                    || lexeme.charAt(i) == '|' || lexeme.charAt(i) == '%' || lexeme.charAt(i) == ':') {
                char chaR = lexeme.charAt(i);
                stringBuilder4.append(operator).append(chaR);
            }
        }
        letter = String.valueOf(stringBuilder1);
        number = String.valueOf(stringBuilder2);
        symbol = String.valueOf(stringBuilder3);
        operator = String.valueOf(stringBuilder4);


        //if (letter != "") {System.out.print(": " + letter); v.value(letter, noError);}
        if (number != "") {System.out.print("kind is number/s read: " + number);v.value(number, noError);System.out.println(" ");}
        if (symbol != "") {System.out.print("kind is symbol/s read: " + symbol);System.out.println(" ");}
        if (operator != "") {System.out.print("kind is operator/s read: " + operator);System.out.println(" ");}

        if (lexeme.contains("//")) {
            System.out.print("kind is: Single comment: //");
            v.value(lexeme, noError);
            System.out.println(" ");
            while (txt.hasNext()) {
                txt.next();
            }
        }

        else if(lexeme.contains("@") || lexeme.contains("#") || lexeme.contains("$") || lexeme.contains("?")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, ILLEGAL CHARACTER\n");
            p.position(l, lexeme, text);
            System.exit(0);
        }

        else if (lexeme.contains("=") && lexeme.contains(":")) {
            if(lexeme.matches(":=")){
                System.out.print("kind is RelationalOperator: " + lexeme + "\n");
                v.value(lexeme, noError);
            }
            else if(!letter.matches(":=")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED ':='\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("=") && lexeme.contains(">")) {
            if(lexeme.matches(">=")){
                System.out.print("kind is RelationalOperator: " + lexeme);
                v.value(lexeme, noError);
                System.out.println(" ");
            }
            else if(!letter.matches(">=")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED '>='\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("=") && lexeme.contains("<")) {
            if(lexeme.matches("=<")){
                System.out.println("kind is RelationalOperator: " + lexeme);
                v.value(lexeme, noError);
                System.out.println(" ");
            }
            else if(!letter.matches("=<")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED '=<'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("=") && lexeme.contains("!")) {
            if(lexeme.matches("!=")){
                System.out.println("kind is RelationalOperator: " + lexeme);
                v.value(lexeme, noError);
                System.out.println(" ");
            }
            else if(!letter.matches("!=")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED '!='\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if(lexeme.contains("**")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN '*'\n");
            p.position(l, lexeme, text);
            System.exit(0);
        }

        else if(lexeme.contains("_")) {
            if(lexeme.charAt(0) == '_'){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, '_' CANNOT BE PLACED IN START OF IDENTIFIER\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
            else if(lexeme.matches("_")){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, '_' IS ILLEGAL CHARACTER\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if(lexeme.matches(":")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN ':='\n");
            p.position(l, lexeme, text);
            System.exit(0);
        }

        else if (lexeme.matches("!")) {
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, '!' ILLEGAL CHARACTER USE\n");
            p.position(l, lexeme, text);
            System.exit(0);
        }

        else if (lexeme.contains("program")) {
            if(lexeme.matches("program")){
                System.out.print("kind is keyword: " + letter);
                v.value(lexeme, noError);
                System.out.print(" ");
            }
            else if(!lexeme.matches("program") || !number.isEmpty()){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'program'?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
            if(txt.hasNext()){
                lexeme = txt.next();
            }
            else if (!txt.hasNext()){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM MUST BE IDENTIFIED FOLLOWED BY ':' ");
                p.position(l, lexeme, text);
                System.exit(0);
            }
            if(lexeme.contains(":")){
                System.out.print("\nkind is identifier: " + lexeme + "\n");
                p.position(l, lexeme, text);
                System.out.print(" ");
                v.value(lexeme, noError);
                System.out.println(" ");
            }
            else if (!lexeme.contains(":")){
                if(txt.hasNext()){
                    previous = lexeme;
                    lexeme = txt.next();
                }
                if(lexeme.contains(":")){
                    System.out.print("\nkind is identifier: " + previous + "\n");
                    p.position(l, lexeme, text);
                    v.value(previous, noError);
                    System.out.println(" ");
                }
                else if(!lexeme.contains(":")){
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM IDENTIFIER MUST BE FOLLOWED BY ':'\n");
                    p.position(l, lexeme, text);
                    System.exit(0);
                }
            }
        }

        else if (lexeme.contains("int") && !lexeme.contains("print")) { //reads keyword 'int'
            System.out.print("kind is keyword Declaration: " + letter);
            v.value(lexeme, noError);
            if(!lexeme.matches("int")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'int'?\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
            if(!txt.hasNext()){ //if int isn't given a name to initialize.
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, 'int' MUST BE USED TO INITIALIZE A IDENTIFIER\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }

            if(txt.hasNext()){
                lexeme=txt.next();
                System.out.println(" ");
                System.out.println("\nlexeme being read is: " + lexeme);
                p.position(l, lexeme, text); //returns current position of lexeme
                k.kind(lexeme, text, l, txt); //v.value(lexeme) is called inside the kind() class
                System.out.print(" ");
            }
            while(!lexeme.contains(";")){
                if(txt.hasNext()){
                    lexeme=txt.next();
                    System.out.println("\nlexeme being read is: " + lexeme);
                    p.position(l, lexeme, text); //returns current position of lexeme
                    k.kind(lexeme, text, l, txt); //v.value(lexeme) is called inside the kind() class
                    System.out.print(" ");
                }
                else if(!txt.hasNext()){ //if int isn't given a name to initialize.
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED ';'\n");
                    p.position(l, lexeme, text);
                    System.exit(0);
                }
            }
        }

        else if (lexeme.contains("bool")) { //reads keyword
            if (lexeme.matches("bool")) {
                System.out.print("kind is keyword: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("bool") || !number.isEmpty()) {
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'bool'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }

            if(txt.hasNext()){
                lexeme=txt.next();
                System.out.println(" ");
                System.out.println("\nlexeme being read is: " + lexeme);
                p.position(l, lexeme, text); //returns current position of lexeme
                k.kind(lexeme, text, l, txt); //v.value(lexeme) is called inside the kind() class
                v.value(lexeme, noError);
                System.out.print(" ");
            }
            while(!lexeme.contains(";")){
                if(txt.hasNext()){
                    lexeme=txt.next();
                    System.out.println("\nlexeme being read is: " + lexeme);
                    p.position(l, lexeme, text); //returns current position of lexeme
                    k.kind(lexeme, text, l, txt); //v.value(lexeme) is called inside the kind() class
                    v.value(lexeme, noError);
                    System.out.print(" ");
                }
                else if(!txt.hasNext()){ //if int isn't given a name to initialize.
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED ';'\n");
                    p.position(l, lexeme, text);
                    System.exit(0);
                }
            }
        }

        else if (lexeme.contains("if")) { //reads 'if' statement
            if (lexeme.matches("if")) {
                System.out.print("kind is ConditionalStatement: " + letter);
                v.value(lexeme, noError);
            }
                else if(!letter.matches("if") || !number.isEmpty()){ //misspell catcher
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'if'\n");
                    p.position(l, lexeme, text);
                    System.exit(0);
                }
        }

        else if (lexeme.contains("else")) {
            if (lexeme.matches("else")) {
                System.out.print("kind is keyword ConditionalStatement: " + letter);
                v.value(lexeme, noError);
            }
            else if(!lexeme.matches("else") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'else'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("fi")) {
            if(lexeme.contains(";")){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, ';' IS NOT PART OF CONDITIONAL STATEMENT\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
            if (lexeme.matches("fi")) {
                System.out.print("kind is keyword ConditionalStatement: " + letter);
                v.value(lexeme, noError);
            }
            else if(!lexeme.matches("fi") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'fi'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("while")) { //while statements
            if (lexeme.matches("while")) {
                System.out.print("kind is keyword IterativeStatement: " + letter);
                v.value(lexeme, noError);
                System.out.println(" ");
                if (txt.hasNext()) {
                letter = txt.next();
            } else {
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, 'while' MUST BE FOLLOWED BY (EXPRESSION)\n");
                    p.position(l, lexeme, text);
                    System.exit(0);
            }
                if (letter.matches("not")) {
                    if (txt.hasNext()) {
                        letter = txt.next();
                    } else {
                        System.out.print(" \nBAD TOKEN: " + lexeme);
                        System.out.print(" \nSYNTAX ERROR DETECTED, 'while not' MUST BE FOLLOWED BY (EXPRESSION)\n");
                        p.position(l, lexeme, text);
                        System.exit(0);
                    }
                    if (letter.contains("(")) {
                        while (!letter.contains(")")) {
                            letter = txt.next();
                            if (!txt.hasNext()) {
                                System.out.print(" \nBAD TOKEN: " + lexeme);
                                System.out.print(" \nSYNTAX ERROR DETECTED, STATEMENT MUST INCLUDE PROPER PARENTHESES ')'\n");
                                p.position(l, lexeme, text);
                                System.exit(0);
                            }
                        }
                    } else if (!letter.contains("(")) {
                        System.out.print(" \nBAD TOKEN: " + lexeme);
                        System.out.print(" \nSYNTAX ERROR DETECTED, STATEMENT MUST INCLUDE PROPER PARENTHESES '()'\n");
                        p.position(l, lexeme, text);
                        System.exit(0);
                    }
                }}
            else if (!letter.matches("while") || !number.isEmpty() || !symbol.isEmpty()) { //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'while'?\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("do")) {
            if (lexeme.matches("do")) {
                System.out.print("kind is keyword IterativeStatement: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("do") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'do'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("od")) {
            if (lexeme.matches("od")) {
                System.out.print("kind is keyword IterativeStatement: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("od") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'od'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("print")) {
            if (lexeme.matches("print")) {
                System.out.print("kind is keyword PrintStatement: " + letter);
                v.value(lexeme, noError);
                System.out.println(" ");

                if(txt.hasNext()){
                    lexeme=txt.next();
                }
                else if(!txt.hasNext()){
                    System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED STATEMENT AFTER PRINT\n");
                    p.position(l, lexeme, text);
                    System.exit(0);
                }
                while(txt.hasNext()){
                    lexeme=txt.next();
                    if(lexeme.contains(";")){ //if int isn't given a name to initialize.
                        System.out.print(" \nBAD TOKEN: " + lexeme);
                        System.out.print(" \nSYNTAX ERROR DETECTED, PRINT DOES NOT REQUIRE ';'\n");
                        p.position(l, lexeme, text);
                        System.exit(0);
                    }
                    System.out.println("\nlexeme being read is: " + lexeme);
                    p.position(l, lexeme, text); //returns current position of lexeme
                    k.kind(lexeme, text, l, txt); //v.value(lexeme) is called inside the kind() class
                    System.out.print(" ");
                }
            }
            else if(lexeme.contains("(") || lexeme.contains(")")){
                System.out.print("kind is keyword PrintStatement: " + letter);
                v.value(lexeme, noError);
                System.out.println(" ");
            }
            else if(!letter.matches("print")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'print'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("false")) {
            if (lexeme.matches("false")){
                System.out.print("kind is keyword BooleanLiteral: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("false") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'false'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("true")) {
            if (lexeme.matches("true")) {
                System.out.print("kind is keyword BooleanLiteral: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("true") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'true'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("end")) {
            if (lexeme.matches("end")){
                System.out.print("kind is keyword program: " + letter);
                v.value(lexeme, noError);
            }
            else if (!lexeme.matches("end") || !number.isEmpty()) {
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, EXPECTED 'end'\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (!letter.isEmpty()){
            System.out.print("kind is Identifier: " + letter);
            v.value(lexeme, noError);
            System.out.println(" ");

            if(txt.hasNext()){
                previous = lexeme;
                lexeme=txt.next();
                System.out.println(" ");
                System.out.println("\nlexeme being read is: " + lexeme);
                p.position(l, lexeme, text); //returns current position of lexeme
                k.kind(lexeme, text, l, txt); //v.value(lexeme) is called inside the kind() class
                System.out.print(" ");
            }
        } //identifier statement
        return txt;
    }
}

//THE AST TREE METH-----------------------------------------------------------------------------------------------------
class ast extends MainClass{
    public void ast (String contents){

    }
}