package com.henri.code;

import org.dizitart.no2.Document;
import java.util.ArrayList;
import java.util.Arrays;

public class Register {
    private final String COUNT = "COUNT";
    private int[] denominations = {20, 10, 5, 2, 1};
    private int[] indexies = {0, 1, 2, 3, 4};
    private final int MAX_INDEX = 4;
    private final int ARRAY_LENGTH = 5;
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
        Change change = new Change(total);

        if(greedyFindChange(billsCount, change)){
            return foundChange(change);
        }
        else if(attentiveFindChange(billsCount, change)){
            return foundChange(change);
        }
        else {
            return "sorry ";
        }
    }

    private Boolean greedyFindChange(ArrayList<Integer> billsCount, Change change){
        //CASE 1: greedy-takeBillsFromRegister as many of the highest value bills without going over
        for (int i = 0; i <= MAX_INDEX; i++) {
            changeForBill(billsCount, indexies[i], denominations[i], change);
        }

        return (change.totalRemaining == 0);

    }

    private Boolean attentiveFindChange(ArrayList<Integer> billsCount, Change change){
        //CASE 2: greedy algorithm fails to find available change
        //  Begin to place high value bills back into register, and takeBillsFromRegister lower value bills in its place
        int n = 0;
        int j = 1;
        while((change.totalRemaining != 0) && j != ARRAY_LENGTH){

            if(change.getBillCount(denominations[n]) != 0){
                change.putBillsBackInRegister(denominations[n], 1);

                for (int i = j; i <= MAX_INDEX; i++)
                    changeForBill(billsCount, indexies[i], denominations[i], change);

                if(change.totalRemaining == 0)
                    return true;

                change.takeBillsFromRegister(denominations[n], 1);
            }
            j++;
            n++;
        }

        return false;
    }

    private String foundChange(Change change){
        for(int i = 0;i <= MAX_INDEX; i++){
            takeBillCount(denominations[i], change.getBillCount(denominations[i]));
        }
        return change.toString();
    }

    private ArrayList<Integer> getBillsCountList(){
        Integer[] result = new Integer[ARRAY_LENGTH];
        for(int i =0;i<denominations.length;i++) {
            if(db.documentExists(denominations[i])) {
                int count = (Integer)db.getDocForDenomination(denominations[i]).get(COUNT);
                result[i] = count;
            }
            else // no entry exists
                result[i] = 0;
        }
        return new ArrayList<>(Arrays.asList(result));
    }

    private void changeForBill(ArrayList<Integer> dbBillCounts,
                             int index,
                             int billValue,
                             Change change){

        if(dbBillCounts.get(index) != 0){
            Integer result = change.totalRemaining / billValue;
            // no need for decimal part?

            if(result >= 1 ){
                while(dbBillCounts.get(index) != 0 && result != change.getBillCount(billValue)) {
                    change.takeBillsFromRegister(billValue, 1);
                    Integer num = dbBillCounts.get(index);
                    dbBillCounts.set(index, --num);
                }
            }
        }
    }

    public void close(){
        db.close();
    }


}
