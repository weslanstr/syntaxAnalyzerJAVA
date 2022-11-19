/**
 * Syntax Analyzer for COSC 455
 * by Wesley Lancaster
 * Submitted on 11/16/22
 * @ wlanca2@students.towson.edu
 **/

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class MainClass {
    public static void main(String[] args){

        System.out.print("""
                |
                * Syntax Analyzer for COSC 455
                * by Wesley Lancaster
                * Submitted on 11/16/22""");

        Scanner fileName = new Scanner(System.in);
        System.out.println("\n|\nplease enter directory location of .txt file.....");
        String name = fileName.nextLine();
        System.out.print(".....path entered");
        fileName.close();
        File x = new File(name);

        if(x.exists()) {
            System.out.print("....."+x.getName() +  " exists!" + "\n");
        }
        else {
            System.out.println("......file does not exist");
        }
        System.out.println("|");

        try {
            Scanner sc = new Scanner(x);
            if (!sc.hasNext() && !sc.hasNextLine()) { //if no more lexemes, read to next line
                System.out.print(" \nEMPTY FILE. NOTHING TO READ.");
                System.exit(0);
            }

            String lexeme = " ";
            next n = new next();
            position p = new position();
            kind k = new kind();
            ast a = new ast();
            value v = new value();
            int i = 0; int l = 1;
            //todo set up a if to set an error detection
            boolean noError = true;
            String text = sc.nextLine();
            System.out.print("|\nNext Line: " + text);
            // + "\nlength of this line is: " + text.length()

            //this is if the file is only one line long.
            if(!sc.hasNextLine()){
                Scanner txtSingle = new Scanner(text);
                while (txtSingle.hasNext()) {
                    lexeme = n.next(txtSingle); //calls next lexeme
                    System.out.println("\nlexeme being read is: " + lexeme);
                    //i = text.indexOf(lexeme);
                    //p.position(l, lexeme, text); //returns current position of lexeme
                    k.kind(lexeme, text, l, txtSingle); //v.value(lexeme) is called inside the kind() class

                    if (!txtSingle.hasNext()) { //if no more lexemes, read to next line
                        if (text.contains("end")) {}
                        if(!text.matches("end")){
                            System.out.print(" \nBAD TOKEN: " + lexeme);
                            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'end' ?");
                            p.position(l, lexeme, text);
                            System.exit(0);
                        }
                    }
                }
            }

//THE CORE LOOP---------------------------------------------------------------------------------------------------------
//this first while is to iterate lines
    while (sc.hasNextLine() && noError) {
        Scanner txt = new Scanner(text);

        //this while loop is to skip empty lines:
        while (text.length() == 0) {text = sc.nextLine();}

        //this second while loop is to iterate lexemes in each line.
        while (txt.hasNext()) {

            lexeme = n.next(txt); //calls next lexeme
            System.out.println("\nlexeme being read is: " + lexeme);
            //i = text.indexOf(lexeme);
            p.position(l, lexeme, text); //returns current position of lexeme
            k.kind(lexeme, text, l, txt); //v.value(lexeme) is called inside the kind() class
            System.out.print("\n");

            if (!txt.hasNext()) { //if no more lexemes, read to next line
                text = sc.nextLine();
                System.out.print("|\n");
                System.out.print("|\nNext Line: " + text + "\n");
                // + text + "\nlength of this line is: " + text.length() +
                i = 0;
                l++;
            }
        }

        if (!sc.hasNextLine()) { //internal to make sure the last line is called and ends with "end"
            if (text.contains("end")) {
                p.position(l, lexeme, text);
                System.out.print("kind is keyword program: end");
                v.value(lexeme, noError);
            }
            if(!text.matches("end")){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'end' ?\n");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }
        txt.close();
    }
            System.out.print("""
                    
                    |
                    |
                    * Syntax Analyzer for COSC455
                    * by Wesley Lancaster
                    * Submitted on 11/16/22""");
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
                System.out.print("\nvalue: NULL");
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
//todo 0 utilize value again?
//todo 1 actually determine if keyword or id
//todo 6. proper rules of assigning an id

//todo 2. proper rules of int
//todo 3. proper rules of bool
//todo 4. proper rules of if
//todo 5. proper rules of else
class kind extends MainClass {
    boolean noError = true;

    public Scanner kind(String lexeme, String text, int l, Scanner txt) {
        kind k = new kind();
        position p = new position();
        value v = new value();

        if (lexeme.contains("//")) {
            System.out.print("kind is: Single comment: " + lexeme);
            v.value(lexeme, noError);
            System.out.println(" ");
            while (txt.hasNext()) {
                txt.next();
            }
        }

        String letter = "";
        String number = "";
        String symbol = "";
        String operator = "";

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
        if (number != "") {System.out.print("number/s read: " + number);v.value(number, noError);}
        if (symbol != "") {System.out.print("symbol/s read: " + symbol+"\n");v.value(number, noError);}
        if (operator != "") {System.out.print("operator/s read: " + operator); v.value(operator, noError);}

        if (lexeme.contains("=") && lexeme.contains(":")) {
            if(lexeme.matches(":=")){
                System.out.println("kind is RelationalOperator: " + lexeme);
                v.value(lexeme, noError);
                System.out.println(" ");
            }
            else if(!letter.matches(":=")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN ':=' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (lexeme.contains("=") && lexeme.contains(">")) {
            if(lexeme.matches(">=")){
                System.out.println("kind is RelationalOperator: " + lexeme);
                v.value(lexeme, noError);
                System.out.println(" ");
            }
            else if(!letter.matches(">=")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN '>=' ?");
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
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN '=<' ?");
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
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN '!=' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if(lexeme.contains("**")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN '*'");
            p.position(l, lexeme, text);
            System.exit(0);
        }

        else if(lexeme.contains("_")) {
            if(lexeme.charAt(0) == '_'){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, '_' CANNOT BE PLACED IN START OF IDENTIFIER");
                p.position(l, lexeme, text);
                System.exit(0);
            }
            else if(lexeme.matches("_")){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, '_' IS ILLEGAL CHARACTER");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if(lexeme.matches(":")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN ':='");
            p.position(l, lexeme, text);
            System.exit(0);
        }

        else if(lexeme.contains("@")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, '@' IS ILLEGAL CHARACTER");
            p.position(l, lexeme, text);
            System.exit(0);
        }

        else if (lexeme.matches("!")) {
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, '!' ILLEGAL CHARACTER USE");
            p.position(l, lexeme, text);
            System.exit(0);
        }

        else if (letter.contains("program")) {
            System.out.print("kind is keyword: " + letter);
            v.value(lexeme, noError);
            if(!letter.matches("program") || !number.isEmpty()){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'program' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
            if(txt.hasNext()){
                letter = txt.next();
            }
            else if (!txt.hasNext()){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM MUST BE IDENTIFIED FOLLOWED BY ':' ");
                p.position(l, lexeme, text);
                System.exit(0);
            }
            if(letter.contains(":")){
                System.out.print("\nidentifier: " + letter);
                v.value(lexeme, noError);
                System.out.println(" ");
                p.position(l, lexeme, text);
            }
            else if (!letter.contains(":")){
                if(txt.hasNext()){
                    letter = txt.next();
                }
                if(letter.contains(":")){
                    System.out.print("\n" + letter);
                    p.position(l, lexeme, text);
                }
                else if(!letter.contains(":")){
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM IDENTIFIER MUST BE FOLLOWED BY ':' ?");
                    p.position(l, lexeme, text);
                    System.exit(0);
                }
            }
        }

        else if (letter.contains("int") && !letter.contains("print")) { //reads keyword 'int'
            System.out.print("kind is keyword Declaration: " + letter);
            v.value(lexeme, noError);
            if(!letter.matches("int") || !number.isEmpty()){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'int' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
            if(!txt.hasNext()){ //if int isn't given a name to initialize.
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, 'int' MUST BE USED TO INITIALIZE A IDENTIFIER");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("bool")) { //reads keyword
            if (lexeme.matches("bool")) {
                System.out.print("kind is keyword: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("bool") || !number.isEmpty()) {
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'bool' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("if")) { //reads 'if' statement
            if (lexeme.matches("if")) {
                System.out.print("kind is keyword: " + letter);
                v.value(lexeme, noError);
            }
                else if(!letter.matches("if") || !number.isEmpty()){ //misspell catcher
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'if' ?");
                    p.position(l, lexeme, text);
                    System.exit(0);
                }
            letter = txt.next();
            if(letter.contains("not")){
                if (lexeme.matches("not")) {
                    System.out.print("kind is keyword: " + letter);
                    v.value(lexeme, noError);
                }
                    else if(!letter.matches("not") || !number.isEmpty()){ //misspell catcher
                        System.out.print(" \nBAD TOKEN: " + lexeme);
                        System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'not' ?");
                        p.position(l, lexeme, text);
                        System.exit(0);
                    }
                while(!letter.contains("then")){//this is an issue
                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                    if(letter.contains("then")){
                        if(!letter.matches("then") || !number.isEmpty()){ //misspell catcher
                            System.out.print(" \nBAD TOKEN: " + lexeme);
                            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'then' ?");
                            p.position(l, lexeme, text);
                            System.exit(0);
                        }
                    }
                }
                while(txt.hasNext()) {
                    //add stuff to this so contents are also lexilized
                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                }
            }
            else if(!letter.contains("not") || !number.isEmpty()){
                while(!letter.contains("then") || !number.isEmpty()){
                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                    if(letter.contains("then")){
                        if(!letter.matches("then") || !number.isEmpty()){ //misspell catcher
                            System.out.print(" \nBAD TOKEN: " + lexeme);
                            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'then' ?");
                            p.position(l, lexeme, text);
                            System.exit(0);
                        }
                    }
                }
                while(txt.hasNext()) {
                    //add stuff to this so contents are also lexilized
                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                }
            }
        }

        else if (letter.contains("else")) {
            if (lexeme.matches("else")) {
                System.out.print("kind is keyword ConditionalStatement: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("else") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'else' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("fi")) {
            if (lexeme.matches("fi")) {
                System.out.print("kind is keyword ConditionalStatement: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("fi") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'fi' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("while")) { //while statements
            if (lexeme.matches("while")) {
                System.out.print("kind is keyword IterativeStatement: " + letter);
                v.value(lexeme, noError);
                if (txt.hasNext()) {
                letter = txt.next();
            } else {
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, 'while' MUST BE FOLLOWED BY (EXPRESSION) ");
                    p.position(l, lexeme, text);
                    System.exit(0);
            }
                if (letter.matches("not")) {
                    if (txt.hasNext()) {
                        letter = txt.next();
                    } else {
                        System.out.print(" \nBAD TOKEN: " + lexeme);
                        System.out.print(" \nSYNTAX ERROR DETECTED, 'while not' MUST BE FOLLOWED BY (EXPRESSION) ");
                        p.position(l, lexeme, text);
                        System.exit(0);
                    }
                    if (letter.contains("(")) {
                        while (!letter.contains(")")) {
                            letter = txt.next();
                            if (!txt.hasNext()) {
                                System.out.print(" \nBAD TOKEN: " + lexeme);
                                System.out.print(" \nSYNTAX ERROR DETECTED, STATEMENT MUST INCLUDE PROPER PARENTHESES ')'");
                                p.position(l, lexeme, text);
                                System.exit(0);
                            }
                        }
                    } else if (!letter.contains("(")) {
                        System.out.print(" \nBAD TOKEN: " + lexeme);
                        System.out.print(" \nSYNTAX ERROR DETECTED, STATEMENT MUST INCLUDE PROPER PARENTHESES '()'");
                        p.position(l, lexeme, text);
                        System.exit(0);
                    }
                }}
            else if (!letter.matches("while") || !number.isEmpty() || !symbol.isEmpty()) { //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'while' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("do")) {
            if (lexeme.matches("do")) {
                System.out.print("kind is keyword IterativeStatement: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("do") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'do' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("od")) {
            if (lexeme.matches("od")) {
                System.out.print("kind is keyword IterativeStatement: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("od") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'od' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("print")) {
            if (lexeme.matches("print")) {
                System.out.print("kind is keyword PrintStatement: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("print")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'print' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("false")) {
            if (lexeme.matches("false")){
                System.out.print("kind is keyword BooleanLiteral: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("false") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'false' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("true")) {
            if (lexeme.matches("true")) {
                System.out.print("kind is keyword BooleanLiteral: " + letter);
                v.value(lexeme, noError);
            }
            else if(!letter.matches("true") || !number.isEmpty()){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'true' ?");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if (letter.contains("end")) {
            if (lexeme.matches("end")){
                System.out.print("kind is keyword program: " + letter);
                v.value(lexeme, noError);
            }
            else if (!lexeme.matches("end") || !number.isEmpty()) {
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED");
                p.position(l, lexeme, text);
                System.exit(0);
            }
        }

        else if(lexeme.contains("//")){}

        else{
            System.out.print("\nIdentifier read: " + letter);
            v.value(lexeme, noError);
            System.out.println(" ");
            //TODO what are actually the rules of an identifier?
        }
        return txt;
    }
}

//THE AST TREE METH-----------------------------------------------------------------------------------------------------
class ast extends MainClass{ public void ast (String contents){} }