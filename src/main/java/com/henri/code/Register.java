package com.henri.code;

import org.dizitart.no2.Document;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.WriteResult;
import org.dizitart.no2.util.Iterables;


public class Register {
    private final String COUNT = "COUNT";
    private int[] denominations = {20, 10, 5, 2, 1};
    private Database db;

    public Register(){
        db = new Database();
    }

    public void putBillCount(int denomination, int count){

        if(db.documentExists(denomination)){
            //update case
            Document doc = db.getDocForDenomination(denomination);
            int dbCount = (Integer)doc.get(COUNT);
            dbCount += count;
            doc.put(COUNT, dbCount);

            WriteResult writeResult = db.updateRecord(doc);
            NitriteId nitriteId = Iterables.firstOrDefault(writeResult);
            //System.out.println(" updated existing nitriteId: " + nitriteId);
            //System.out.println(" denomination: " + denomination + " count: " + count);
        }
        else {
            // insert new
            Document doc = db.createRecord(denomination, count);


            WriteResult writeResult = db.insertRecord(doc);
            NitriteId nitriteId = Iterables.firstOrDefault(writeResult);
            //System.out.println(" inserted new nitriteId: " + nitriteId);
            //System.out.println(" denomination: " + denomination + " count: " + count);
        }
    }

    public String show() {
        int totalValue = 0;
        // for denominations count only
        StringBuilder sb = new StringBuilder();
        for(Integer deno:denominations){
            if(db.documentExists(deno)){
                int count = (Integer)db.getDocForDenomination(deno).get(COUNT);
                totalValue += deno * count;
                sb.append(count + " ");
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

    public void close(){
        db.close();
    }
}
