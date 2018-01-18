package com.henri.code;


import java.util.regex.Pattern;

public class Main {
    private final static String LETTERS = "[a-zA-Z]+";
    private final static String NUMBERS = "[0-9]+";
    private final static Integer MAX_ARGS = 6;
    private final static Integer MIN_ARGS = 1;

    public static void main(String[] args) {
        System.out.println("ready ");
        while(true){
            try {
                loop();
            } catch(Exception e){
                System.out.println("ERROR: " + e.fillInStackTrace());
                System.exit(0);
            }
        }
    }

    private static void loop() throws Exception {
        System.out.print("> ");
        String input = System.console().readLine();
        String[] inputSplit = input.split(" ");

        validate(inputSplit);

        //System.out.println("input : " + input);

        // > show
        // $Total #$20 #$10 #$5 #$2 #$1
        if("show".equals(inputSplit[0])){
            System.out.println(" SHOW ");
        }
        // > put 1 2 3 4 5
        else if("put".equals(inputSplit[0])){
            System.out.println(" PUT ");
        }
        // > take 1 2 3 4 5
        else if("take".equals(inputSplit[0])){
            System.out.println(" TAKE ");
        }
        // > change 12
        else if("change".equals(inputSplit[0])){
            System.out.println(" CHANGE ");
        }
        // > quit
        else if ("quit".equals(inputSplit[0])) {
            System.out.println("Bye ");
            System.exit(0);
        }
        else {
            System.out.println(" invalid command! ");

        }


    }

    private static void validate(String[] inputSplit) throws Exception {
        if(inputSplit.length != MAX_ARGS && inputSplit.length != MIN_ARGS)
            throw new Exception(" incorrect number of arguments!");
        if(!Pattern.matches(LETTERS, inputSplit[0]))
            throw new Exception(" first argument can only be letters!");
        if(inputSplit.length == MAX_ARGS) {
            for (int i = 1; i < MAX_ARGS; i++) {
                if(!Pattern.matches(NUMBERS,inputSplit[i]))
                    throw new Exception(" subsequent arguments can only be integers!");

            }
        }

    }

}

