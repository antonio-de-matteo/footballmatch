package database;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Iterator;


public class ConnectToDB {
    public static void main( String args[]) {

        MongoClient mongo = new MongoClient( "localhost", 27017 );
        MongoCredential credential;
        credential = MongoCredential.createCredential("admin", "myDb","admin".toCharArray());
        System.out.println("Connected to the database successfully");
        MongoDatabase database = mongo.getDatabase("footballResult");
        MongoCollection<Document> collection= database.getCollection("partiteDiCalcio");
        System.out.println("ok");
        FindIterable <Document> iterable= collection.find();
        int i=1;
        Iterator it= iterable.iterator();

        while(it.hasNext()){
            System.out.println(it.next());
            i++;
        }

    }
}

