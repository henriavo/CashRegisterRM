package com.henri.code;


import java.util.regex.Pattern;

public class Main {
    private final static String LETTERS = "[a-zA-Z]+";
    private final static String NUMBERS = "[0-9]+";
    private final static Integer MAX_ARGS = 6;
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

        if("show".equals(inputSplit[0])){
            showStatus();
        }
        else if("put".equals(inputSplit[0])){
            register.putBillCount(20, Integer.valueOf(inputSplit[1]));
            register.putBillCount(10, Integer.valueOf(inputSplit[2]));
            register.putBillCount(5, Integer.valueOf(inputSplit[3]));
            register.putBillCount(2, Integer.valueOf(inputSplit[4]));
            register.putBillCount(1, Integer.valueOf(inputSplit[5]));

            showStatus();
        }
        else if("take".equals(inputSplit[0])){
            register.takeBillCount(20, Integer.valueOf(inputSplit[1]));
            register.takeBillCount(10, Integer.valueOf(inputSplit[2]));
            register.takeBillCount(5, Integer.valueOf(inputSplit[3]));
            register.takeBillCount(2, Integer.valueOf(inputSplit[4]));
            register.takeBillCount(1, Integer.valueOf(inputSplit[5]));

            showStatus();
        }
        else if("change".equals(inputSplit[0])){
            String result = register.getChange(Integer.valueOf(inputSplit[1]));
            System.out.println(result);
        }
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
        if(inputSplit.length != MAX_ARGS && inputSplit.length != 1  && inputSplit.length != 2)
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