import com.mongodb.BasicDBObject;
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
import java.util.List;

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
        if(query.equals("one")) {
            String name = request.getParameter("team");
            FindIterable<Document> res = collection.find
                    (new Document("home_team",
                            new Document("$eq", name)))
                    .skip(50)
                    .limit(20);
            MongoCursor res1 = res.iterator();
            ArrayList<Match> ret = new ArrayList<>();
            while (res1.hasNext()) {
                Match m = new Match();
                Document curr = (Document) res1.next();
                m.setDate(curr.getString("date"));
                m.setYear(curr.getInteger("years"));
                m.setHome_team(curr.getString("home_team"));
                m.setAway_team(curr.getString("away_team"));
                m.setHome_score(curr.getInteger("home_score"));
                m.setAway_score(curr.getInteger("away_score"));
                m.setTournament((curr.getString("tournament")));
                m.setCity(curr.getString("city"));
                m.setCountry(curr.getString("country"));
                m.setNeutral(curr.getBoolean("neutral"));
                ret.add(m);
            }
                request.setAttribute("result", ret);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/OneQuery.jsp");
                dispatcher.forward(request, response);

        }
        if(query.equals("two")) {
            String nameHome = request.getParameter("homeTeam");
            String nameAway = request.getParameter("awayTeam");
            BasicDBObject andQuery = new BasicDBObject();
            List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
            obj.add(new BasicDBObject("home_score", 0));
            obj.add(new BasicDBObject("away_score", 0));
            obj.add(new BasicDBObject("away_team",nameAway));
            obj.add(new BasicDBObject("home_team",nameHome));
            andQuery.put("$and", obj);
            FindIterable<Document> docIterator1 = collection.find(andQuery);
            MongoCursor<Document> cursorquery2 = docIterator1.iterator();
            ArrayList<Match> ret = new ArrayList<>();
            while (cursorquery2.hasNext()) {
                Match m = new Match();
                Document curr = cursorquery2.next();
                m.setDate(curr.getString("date"));
                m.setYear(curr.getInteger("years"));
                m.setHome_team(curr.getString("home_team"));
                m.setAway_team(curr.getString("away_team"));
                m.setHome_score(curr.getInteger("home_score"));
                m.setAway_score(curr.getInteger("away_score"));
                m.setTournament((curr.getString("tournament")));
                m.setCity(curr.getString("city"));
                m.setCountry(curr.getString("country"));
                m.setNeutral(curr.getBoolean("neutral"));
                ret.add(m);
            }
            request.setAttribute("result2", ret);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/TwoQuery.jsp");
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
        else if(query.equals("three")){
            int startDate = Integer.parseInt(request.getParameter("startDate"));
            int endingDate = Integer.parseInt(request.getParameter("toDate"));
           FindIterable<Document> c = collection.find(new BasicDBObject(
                   "years",
                   new BasicDBObject("$gte", startDate).append("$lte", endingDate)
           ));
           MongoCursor<Document> cursorquery2 = c.iterator();
            ArrayList<Match> ret = new ArrayList<>();
            while (cursorquery2.hasNext()) {
                Match m = new Match();
                Document curr = cursorquery2.next();
                m.setDate(curr.getString("date"));
                m.setYear(curr.getInteger("years"));
                m.setHome_team(curr.getString("home_team"));
                m.setAway_team(curr.getString("away_team"));
                m.setHome_score(curr.getInteger("home_score"));
                m.setAway_score(curr.getInteger("away_score"));
                m.setTournament((curr.getString("tournament")));
                m.setCity(curr.getString("city"));
                m.setCountry(curr.getString("country"));
                m.setNeutral(curr.getBoolean("neutral"));
                ret.add(m);
            }
            request.setAttribute("result", ret);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ThreeQuery.jsp");
            dispatcher.forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
