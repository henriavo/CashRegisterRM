package com.henri.code;


import java.util.regex.Pattern;

public class Main {
    private final static String LETTERS = "[a-zA-Z]+";
    private final static String NUMBERS = "[0-9]+";
    private final static Integer MAX_ARGS = 6;
    private final static Integer MIN_ARGS = 1;
    private static Register register;

    public static void main(String[] args) {
        register = new Register();
        System.out.println("ready ");
        while(true){
            try {
                loop();
            } catch(Exception e){
                System.out.println("ERROR: " + e.fillInStackTrace());
                register.close();
                System.exit(0);
            }
        }
    }

    private static void loop() throws Exception {
        System.out.print("> ");
        String input = System.console().readLine();
        String[] inputSplit = input.split(" ");

        validate(inputSplit);

        // > show
        // $Total #$20 #$10 #$5 #$2 #$1
        if("show".equals(inputSplit[0])){
            showStatus();
        }
        // > put 1 2 3 4 5
        else if("put".equals(inputSplit[0])){
            register.putBillCount(20, Integer.valueOf(inputSplit[1]));
            register.putBillCount(10, Integer.valueOf(inputSplit[2]));
            register.putBillCount(5, Integer.valueOf(inputSplit[3]));
            register.putBillCount(2, Integer.valueOf(inputSplit[4]));
            register.putBillCount(1, Integer.valueOf(inputSplit[5]));

            showStatus();
        }
        // > take 1 2 3 4 5
        else if("take".equals(inputSplit[0])){

        }
        // > change 12
        else if("change".equals(inputSplit[0])){

        }
        // > quit
        else if ("quit".equals(inputSplit[0])) {
            System.out.println("Bye ");
            register.close();
            System.exit(0);
        }
        else {
            System.out.println(" invalid command! ");

        }
    }

    private static void showStatus(){
        System.out.println(register.show());
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