package com.henri.code;

import org.dizitart.no2.Document;
import java.util.ArrayList;
import java.util.Arrays;

public class Register {
    private final String COUNT = "COUNT";
    private int[] denominations = {20, 10, 5, 2, 1};
    private Database db;

    public Register(){
        db = new Database();
    }

    public String show() {
        int totalValue = 0;
        StringBuilder sb = new StringBuilder();
        for(Integer deno:denominations){
            if(db.documentExists(deno)){
                int count = (Integer)db.getDocForDenomination(deno).get(COUNT);
                totalValue += deno * count;
                sb.append(count).append(" ");
            }
            else {
                // no entry exists
                sb.append("0 ");
            }
        }
        StringBuilder sb2 = new StringBuilder("$" + totalValue + " ");
        sb2.append(sb.toString());
        return sb2.toString();
    }

    public void putBillCount(int denomination, int count){
        if(db.documentExists(denomination)){
            //update case
            Document doc = db.getDocForDenomination(denomination);
            int dbCount = (Integer)doc.get(COUNT);
            dbCount += count;
            doc.put(COUNT, dbCount);

            db.updateRecord(doc);
        }
        else {
            // insert new
            Document doc = db.createRecord(denomination, count);
            db.insertRecord(doc);
        }
    }

    public void takeBillCount(int denomination, int count) {
        if(db.documentExists(denomination)){
            Document doc = db.getDocForDenomination(denomination);
            int dbCount = (Integer)doc.get(COUNT);
            dbCount -= count;

            if(dbCount < 0)
                doc.put(COUNT, 0);
            else
                doc.put(COUNT, dbCount);

            db.updateRecord(doc);
        }

    }

    public String getChange(Integer total){
        ArrayList<Integer> billsCount = getBillsCountList();
        StringBuilder sb = new StringBuilder();

        Total total1 = new Total(total);

        //TODO: use the new Change class
        // Change change = new Change();

        int count$20 = changeForBill(billsCount, 0, 20.0, total1, sb);
        int count$10 = changeForBill(billsCount, 1, 10.0, total1, sb);
        int count$5 = changeForBill(billsCount, 2, 5.0, total1, sb);
        int count$2 = changeForBill(billsCount, 3, 2.0, total1, sb);
        int count$1 = changeForBill(billsCount, 4, 1.0, total1, sb);

        //sorry message if not able to return change
        if(!(total1.total < 0.001))
            return "sorry ";
        else {
            takeBillCount(20, count$20);
            takeBillCount(10, count$10);
            takeBillCount(5, count$5);
            takeBillCount(2, count$2);
            takeBillCount(1, count$1);
            return sb.toString();
        }
    }

    public ArrayList<Integer> getBillsCountList(){
        Integer[] result = new Integer[5];
        for(int i =0;i<denominations.length;i++){
            if(db.documentExists(denominations[i])){
                int count = (Integer)db.getDocForDenomination(denominations[i]).get(COUNT);
                result[i] = count;
            }
            else {
                // no entry exists
                result[i] = 0;
            }
        }

        return new ArrayList<>(Arrays.asList(result));
    }

    public int changeForBill(ArrayList<Integer> billsCount,
                             int index,
                             double value, // ie 20
                             Total total, // what you want change for
                             StringBuilder sb){
        int billCount = 0;

        if(billsCount.get(index) != 0){
            Double result = total.total / value;
            Integer wholeResult = result.intValue();

            if(result >= 1 ){
                while(billsCount.get(index) != 0 && wholeResult != billCount) {
                    billCount++;
                    Integer num = billsCount.get(index);
                    billsCount.set(index, --num);

                }
                sb.append(billCount).append(" ");
                int newTotal = (int) (total.total - (value * billCount));
                total.setTotal(newTotal);
                return billCount;
            }
        }
        sb.append("0 ");
        return billCount;
    }

    public void close(){
        db.close();
    }

    public class Total{
        public int total = 0;
        public Total(int a){
            total = a;
        }
        public void setTotal(int a){
            total = a;
        }
    }

}
