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
            String text = sc.nextLine();


            System.out.print("|\nNext Line: " + text);
            // + "\nlength of this line is: " + text.length()
            String lexeme = " ";

            next n = new next();
            position p = new position();
            kind k = new kind();
            //value v = new value();

            int i = 0; int l = 1;
            boolean noError = true;

//THE CORE LOOP---------------------------------------------------------------------------------------------------------

//this first while is to iterate lines
    while (sc.hasNextLine() && noError) {
        Scanner txt = new Scanner(text);

        //this while loop is to skip empty lines:
        while (text.length() == 0) {
            text = sc.nextLine();
        }

        //this second while loop is to iterate lexemes in each line.
        while (txt.hasNext()) {

            lexeme = n.next(txt); //calls next lexeme
            System.out.println("\nlexeme being read is: " + lexeme);
            i = text.indexOf(lexeme);
            p.position(l, lexeme, txt); //returns current position of lexeme
            k.kind(lexeme, txt, l); //v.value(lexeme) is called inside the kind() class
            //System.out.print("\n");

            if (!txt.hasNext()) { //if no more lexemes, read to next line
                text = sc.nextLine();
                //System.out.print("|\n");
                System.out.print("|\nNext Line: " + text + "\n");
                // + text + "\nlength of this line is: " + text.length() +
                i = 0;
                l++;
            }
        }

        if (!sc.hasNextLine()) { //internal to make sure the last line is called and ends with "end"
            if (text.contains("end")) {}
            if(!text.matches("end")){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'end' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
            else if(!text.contains("end")) {
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print("ERROR: FILE DOES NOT TERMINATE WITH 'end' KEYWORD ");
                p.position(l, lexeme, txt);
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
    public void position(int l, String lexeme, Scanner txt) {
        String line = txt.toString();
        int i = line.indexOf(lexeme) + lexeme.length();
            System.out.println("\nLine: "+l+ " Index: " + i);
    }
}

//THE VALUE METH--------------------------------------------------------------------------------------------------------
class value extends MainClass {
    public void value(String lexeme, boolean noError) {
        boolean result = lexeme.matches("[0-9]+");
        boolean symbol = lexeme.matches("[(,),{,},:,;]+");

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
//todo 1 lets make a big if statement.. to determine if a lexeme is an id
class kind extends MainClass {
    boolean noError = true;

    public Scanner kind(String lexeme, Scanner txt, int l) {
        kind k = new kind();
        position p = new position();
        //value v = new value();

        if (lexeme.contains("//")) {
            //v.value(lexeme, noError);
            //System.out.print("\nkind is: Single comment: " + lexeme);
            while (txt.hasNext()) {
                //System.out.print(" " + txt.next());
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

        //if (letter != "") {System.out.print("\nletter/s read: " + letter); v.value(letter, noError);}
        //if (number != "") {System.out.print("\nnumber/s read: " + number);v.value(number, noError);}
        //if (symbol != "") {System.out.print("\nsymbol/s read: " + symbol); v.value(symbol, noError);}
        //if (operator != "") {System.out.print("\noperator/s read: " + operator); v.value(operator, noError);}

        if (lexeme.contains("=") && lexeme.contains(":")) {
            if(lexeme.matches(":=")){
                System.out.print("\nkind is RelationalOperator: " + lexeme);
            }
            else if(!letter.matches(":=")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN ':=' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        if (lexeme.contains("=") && lexeme.contains(">")) {
            if(lexeme.matches(">=")){
                System.out.print("\nkind is RelationalOperator: " + lexeme);
            }
            else if(!letter.matches(">=")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN '>=' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        if (lexeme.contains("=") && lexeme.contains("<")) {
            if(lexeme.matches("=<")){
                System.out.print("\nkind is RelationalOperator: " + lexeme);
            }
            else if(!letter.matches("=<")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN '=<' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        if (lexeme.contains("=") && lexeme.contains("!")) {
            if(lexeme.matches("!=")){
                System.out.print("\nkind is RelationalOperator: " + lexeme);
            }
            else if(!letter.matches("!=")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN '!=' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        if(lexeme.contains("**")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN '*'");
            p.position(l, lexeme, txt);
            System.exit(0);
        }

        if(lexeme.matches(":")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN ':='");
            p.position(l, lexeme, txt);
            System.exit(0);
        }

        if(lexeme.contains("@")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, '@' IS ILLEGAL CHARACTER");
            p.position(l, lexeme, txt);
            System.exit(0);
        }

        if(lexeme.matches("_")){
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, '_' IS ILLEGAL CHARACTER");
            p.position(l, lexeme, txt);
            System.exit(0);
        }

        if (lexeme.matches("!")) {
            System.out.print(" \nBAD TOKEN: " + lexeme);
            System.out.print(" \nSYNTAX ERROR DETECTED, '!' ILLEGAL CHARACTER USE");
            p.position(l, lexeme, txt);
            System.exit(0);
        }

        else if (letter.contains("program")) {
            System.out.print("\nkind is keyword: " + letter);
            if(!letter.matches("program")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'program' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
            if(txt.hasNext()){
                letter = txt.next();
            }
            else if (!txt.hasNext()){
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM MUST BE IDENTIFIED FOLLOWED BY ':' ");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
            if(letter.contains(":")){
                System.out.print("\nidentifier: " + letter);
            }
            else if (!letter.contains(":")){
                if(txt.hasNext()){
                    letter = txt.next();
                }
                if(letter.contains(":")){
                    //System.out.print(letter);
                }
                else if(!letter.contains(":")){
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM IDENTIFIER MUST BE FOLLOWED BY ':' ?");
                    p.position(l, lexeme, txt);
                    System.exit(0);
                }
            }
        }

        //todo 2 (This needs to properly know the rules of a 'int' declaration, and all of the potential errors)
        else if (letter.contains("int") && !letter.contains("print")) { //reads keyword 'int'
            System.out.print("\nkind is keyword Declaration: " + letter);
            if(!letter.matches("int")){ //if int is misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'int' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
            if(!txt.hasNext()){ //if int isn't given a name to initialize.
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, 'int' MUST BE USED TO INITIALIZE A IDENTIFIER");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
            //while(!letter.contains(";"))
            //if(!txt.hasNext)
            //{} print "error this needs to end with ';'
            //{} somehow loop through each item, and return its value, kind and position
            //if identifier = true and .next() is also an identifier and not an operator, return an error.
        }

        //todo 3 (must know the proper rules of a 'bool' declaration, and all of the potential errors)
        else if (letter.contains("bool")) { //reads keyword
            System.out.print("\nkind is keyword: " + letter);
            if (lexeme.matches("bool")) {}
            else if(!letter.matches("bool")) {
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'bool' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        //todo 4 (must know the proper rules of a 'if' declaration, and all of the potential errors)
        else if (letter.contains("if")) { //reads 'if' statement
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);

            if(!letter.matches("if")){ //misspell catcher
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'if' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
            letter = txt.next();
            if(letter.contains("not")){
                if(!letter.matches("not")){ //misspell catcher
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'not' ?");
                    p.position(l, lexeme, txt);
                    System.exit(0);
                }
                while(!letter.contains("then")){//this is an issue

                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                    if(letter.contains("then")){
                        if(!letter.matches("then")){ //misspell catcher
                            System.out.print(" \nBAD TOKEN: " + lexeme);
                            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'then' ?");
                            p.position(l, lexeme, txt);
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
            else if(!letter.contains("not")){
                while(!letter.contains("then")){
                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                    if(letter.contains("then")){
                        if(!letter.matches("then")){ //misspell catcher
                            System.out.print(" \nBAD TOKEN: " + lexeme);
                            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'then' ?");
                            p.position(l, lexeme, txt);
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

        //todo 5 (must know the proper rules of a 'else' declaration, and all of the potential errors)
        else if (letter.contains("else")) {
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);
            if (lexeme.matches("else")) {}
            else if(!letter.matches("else")){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'else' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        else if (letter.contains("fi")) {
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);
            if (lexeme.matches("fi")) {}
            else if(!letter.matches("fi")){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'fi' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        if (letter.contains("while")) { //while statements
            System.out.print("\nkind is keyword IterativeStatement: " + letter);
            if (lexeme.matches("while")) {
                if (txt.hasNext()) {
                letter = txt.next();
            } else {
                    System.out.print(" \nBAD TOKEN: " + lexeme);
                    System.out.print(" \nSYNTAX ERROR DETECTED, 'while' MUST BE FOLLOWED BY (EXPRESSION) ");
                    p.position(l, lexeme, txt);
                    System.exit(0);
            }
                if (letter.equals("not")) {
                    if (txt.hasNext()) {
                        letter = txt.next();
                    } else {
                        System.out.print(" \nBAD TOKEN: " + lexeme);
                        System.out.print(" \nSYNTAX ERROR DETECTED, 'while not' MUST BE FOLLOWED BY (EXPRESSION) ");
                        p.position(l, lexeme, txt);
                        System.exit(0);
                    }

                    if (letter.contains("(")) {
                        while (!letter.contains(")")) {
                            letter = txt.next();
                            if (!txt.hasNext()) {
                                System.out.print(" \nBAD TOKEN: " + lexeme);
                                System.out.print(" \nSYNTAX ERROR DETECTED, STATEMENT MUST INCLUDE PROPER PARENTHESES ')'");
                                p.position(l, lexeme, txt);
                                System.exit(0);
                            }
                        }
                    } else if (!letter.contains("(")) {
                        System.out.print(" \nBAD TOKEN: " + lexeme);
                        System.out.print(" \nSYNTAX ERROR DETECTED, STATEMENT MUST INCLUDE PROPER PARENTHESES '()'");
                        p.position(l, lexeme, txt);
                        System.exit(0);
                    }
                }}
            else if (!letter.matches("while")) { //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'while' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        else if (letter.contains("od")) {
            System.out.print("\nkind is keyword IterativeStatement: " + letter);

            if (lexeme.matches("od")) {}
            else if(!letter.matches("od")){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'od' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        else if (letter.contains("print")) {
            System.out.print("\nkind is keyword PrintStatement: " + letter);

            if (lexeme.matches("print")) {}
            else if(!letter.matches("print")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'print' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        else if (letter.contains("false")) {
            System.out.print("\nkind is keyword BooleanLiteral: " + letter);

            if (lexeme.matches("false")) {}
            else if(!letter.matches("false")){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'false' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        else if (letter.contains("true")) {
            System.out.print("\nkind is keyword BooleanLiteral: " + letter);
            if (lexeme.matches("true")) {}
            else if(!letter.matches("true")){ //if misspelled
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'true' ?");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        else if (letter.contains("end")) {
            if (lexeme.matches("end")) {
                System.out.print(" \nBAD TOKEN: " + lexeme);
                System.out.print(" \nSYNTAX ERROR DETECTED, THIS IS NOT END OF FILE");
                p.position(l, lexeme, txt);
                System.exit(0);
            }
        }

        else if(lexeme.contains(";")){
            System.out.print("\nkind is identifier: " + letter);
        }
        else if (symbol.contains("{")) {
            //System.out.print("\nkind is symbol: " + symbol);
        } else if (symbol.contains("}")) {
            //System.out.print("\nkind is symbol: " + symbol);
        } else if (symbol.contains("[")) {
            //System.out.print("\nkind is symbol: " + symbol);
        } else if (symbol.contains("]")) {
           //System.out.print("\nkind is symbol: " + symbol);
        }
        else if (symbol.contains("(")) {
            //System.out.print("\nkind is symbol: (");
            //System.out.print("\nkind is identifier: " + letter);
        } else if (symbol.contains(")")) {
            //System.out.print("\nkind is symbol: )");
            //System.out.print("\nkind is identifier: " + letter);
        }
        else if (symbol.contains(";")) {
            //System.out.print("\nkind is symbol: " + symbol);
        } else if (symbol.contains(":")) {
            //System.out.print("\nkind is symbol: " + symbol);
        } else if (operator.contains("|")) {
            //System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("<")) {
            //System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains(">")) {
            //System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("=")) {
            //System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("+")) {
            //System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("-")) {
            //System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("%")) {
            //ystem.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("*")) {
            //System.out.print("\nkind is operator: " + operator);
        }
        else if (operator.contains("//")) {
        }
        else{
            //System.out.print("\nkind is Identifier: " + letter);
        }
        return txt;
    }

    class ast extends MainClass{    public void ast (String contents){}    }
}