import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import model.Match;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/ServletQuerys")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MongoClient mongoClient = new MongoClient(
                new MongoClientURI(
                        "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false"
                )
        );
        MongoDatabase database = mongoClient.getDatabase("footballResult");
        MongoCollection<Document> collection = database.getCollection("partiteDiCalcio");
        String query = request.getParameter("query");
        if(query.equals("one")){
            String name = request.getParameter("team");
            Match m = new Match();
            Document res = collection.find(new Document("home_team", new Document("$eq", name))).first();
            m.setDate(res.getString("date"));
            m.setYear(res.getInteger("years"));
            m.setHome_team(res.getString("home_team"));
            m.setAway_team(res.getString("away_team"));
            m.setHome_score(res.getInteger("home_score"));
            m.setAway_score(res.getInteger("away_score"));
            m.setTournament((res.getString("tournament")));
            m.setCity(res.getString("city"));
            m.setCountry(res.getString("country"));
            m.setNeutral(res.getBoolean("neutral"));
            request.setAttribute("result",m);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/OneQuery.jsp");
            dispatcher.forward(request, response);
        }
        else if(query.equals("six")) {
            HashMap<String,Object> filterMap = new HashMap();
            filterMap.put("home_team", "England");
            filterMap.put("away_team", "Scotland");
            filterMap.put("home_score", "2");
            filterMap.put("away_score", "2");
            Bson filter = new Document(filterMap);
            FindIterable<Document> docIterator = collection.find().filter(filter);
            MongoCursor<Document> cursor = docIterator.iterator();
            ArrayList<Document> result = new ArrayList<>();
            while(cursor.hasNext())
                result.add(cursor.next());
            request.setAttribute("result", result);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/OneQuery.jsp");
            dispatcher.forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
