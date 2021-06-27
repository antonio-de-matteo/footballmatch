import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mapping {
    public static void main (String[] arg) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("src/results.csv"));
        String row;
        MongoClient mongoClient = new MongoClient(
                new MongoClientURI(
                        "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false"
                )
        );
        MongoDatabase database = mongoClient.getDatabase("footballResult");
        MongoCollection<Document> y = database.getCollection("partiteDiCalcio");
        List<Document> toInsert = new ArrayList();
        boolean i = false;
        while ((row = csvReader.readLine()) != null) {
            if(!i) {
                i= true;
                continue;
            }
            String[] data = row.split(",");
            int homeScore = Integer.parseInt(data[3]);
            int awayScore = Integer.parseInt(data[4]);
            Boolean bool = Boolean.parseBoolean(data[8]);
            Document document1 = new Document();
            document1.put("date", data[0]);
            document1.put("years", Integer.parseInt(data[0].substring(0,4)));
            document1.put("home_team", data[1]);
            document1.put("away_team", data[2]);
            document1.put("home_score", homeScore);
            document1.put("away_score", awayScore);
            document1.put("tournament", data[5]);
            document1.put("city", data[6]);
            document1.put("country", data[7]);
            document1.put("neutral", bool);
            toInsert.add(document1);
        }
        y.insertMany(toInsert);
        csvReader.close();
        System.out.println("succ");
        y.createIndex(Indexes.ascending("years"));

    }
}
