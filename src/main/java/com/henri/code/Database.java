package com.henri.code;

import org.dizitart.no2.*;
import static org.dizitart.no2.Document.createDocument;
import static org.dizitart.no2.filters.Filters.eq;

public class Database {
    private final String COLLECTION = "cash_collection";
    private final String DENOMINATION = "DENOMINATION";
    private final String COUNT = "COUNT";
    private Nitrite db;
    private NitriteCollection collection;

    public Database() {
        String dir = System.getProperty("user.dir");
        dir = dir.concat("/test.db");

        db = Nitrite.builder()
                .filePath(dir)
                .openOrCreate();
        System.out.println(" Database file located at: " + dir);

        collection = db.getCollection(String.valueOf(COLLECTION));

    }

    public WriteResult updateRecord(Document doc){
        return collection.update(doc, true);
    }

    public WriteResult insertRecord(Document doc){
        return collection.insert(doc);
    }

    public Document getDocForDenomination(int denomination){
        Cursor results = collection.find(eq(DENOMINATION, denomination));

        return results.firstOrDefault();
    }

    public Boolean documentExists(int denomination){
        Cursor results = collection.find(eq(DENOMINATION, denomination));
        return (results.size() >= 1) ;
    }

    public Document createRecord(int denomination, int count){
        return createDocument(DENOMINATION, denomination)
                .put(COUNT, count);
    }

    public void close(){
        collection.close();
        db.close();
    }

}
