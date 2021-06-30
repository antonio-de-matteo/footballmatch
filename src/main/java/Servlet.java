import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
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
import java.util.Arrays;
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
            String nameHome = request.getParameter("homeTeam");
            String nameAway = request.getParameter("awayTeam");
            String date = request.getParameter("date");
            String namescore =request.getParameter("homescore");
            String awayscore =request.getParameter("awayscore");
            HashMap<String,Object> filterMap = new HashMap();
            if(nameHome!=null) {
                filterMap.put("home_team", nameHome);
            }
            if(date!=""){
                filterMap.put("date",date);
            }
            if(nameAway!=null) {
                filterMap.put("away_team", nameAway);
            }
            if(namescore!="") {
                filterMap.put("home_score", Integer.parseInt(namescore));
            }
            if(awayscore!="") {
                filterMap.put("away_score", Integer.parseInt(awayscore));
            }
            Bson filter = new Document(filterMap);
            FindIterable<Document> docIterator = collection.find().filter(filter);
            MongoCursor<Document> cursor6 = docIterator.iterator();
            ArrayList<Match> ret = new ArrayList<>();
            while (cursor6.hasNext()) {
                Match m = new Match();
                Document curr = cursor6.next();
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
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/SixQuery.jsp");
            dispatcher.forward(request, response);
        }
        else if(query.equals("seven")){
            collection.aggregate(Arrays.asList(new Document("$project",
                            new Document("_id", 0L)
                                    .append("home_team", 1L)
                                    .append("home_score", 1L)
                                    .append("away_team", 1L)
                                    .append("away_score", 1L)
                                    .append("date", 1L)),
                    new Document("$match",
                            new Document("home_team", "Italy")
                                    .append("away_team", "France")
                                    .append("$expr",
                                            new Document("$gt", Arrays.asList("$home_score", "$away_score"))))));
            BasicDBObject andQuery = new BasicDBObject();
           List<BasicDBObject> obj = new ArrayList<>();
            obj.add(new BasicDBObject("neutral", false));
            andQuery.put("$and", obj);
            FindIterable<Document> view = database.getCollection("ItalyVSFranceView").find(andQuery);
            MongoCursor<Document> cursorView = view.iterator();
            ArrayList<Match> ret = new ArrayList<>();
            while (cursorView.hasNext()) {
                Match m = new Match();
                Document curr = cursorView.next();
                m.setDate(curr.getString("date"));
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
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/SevenQuery.jsp");
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
        else if(query.equals("four")){
            String torneo = request.getParameter("homeTeam");
            String a = request.getParameter("toview");
            int toview =Integer.parseInt(request.getParameter("toview"));
            FindIterable<Document> typeOftournment = collection.find(Filters.eq("tournament", torneo))
                    .skip(1)
                    .limit(toview);
            ArrayList<Match> ret = new ArrayList<>();
            MongoCursor<Document> cursortournment = typeOftournment.iterator();
            while (cursortournment.hasNext()) {
                Match m = new Match();
                Document curr = cursortournment.next();
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
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/FourQuery.jsp");
            dispatcher.forward(request, response);
        }
        else if(query.equals("five")){
            int toview =Integer.parseInt(request.getParameter("toview"));
            int toviewe =Integer.parseInt(request.getParameter("toviewe"));
            Bson filter1 = Filters.gte("home_score", toview);
            Bson project = Filters.and(Filters.eq("_id", 0L), Filters.eq("home_score", 1L), Filters.eq("away_score", 1L), Filters.eq("home_team", 1L), Filters.eq("away_team", 1L), Filters.eq("date", 1L));
            FindIterable<Document> resultProj = collection.find(filter1)
                    .projection(project)
                    .limit(toviewe);
            MongoCursor<Document> cursorGTE = resultProj.iterator();
            ArrayList<Match> ret = new ArrayList<>();
            MongoCursor<Document> cursortournment2 = resultProj.iterator();
            while (cursortournment2.hasNext()) {
                Match m = new Match();
                Document curr = cursortournment2.next();
                m.setDate(curr.getString("date"));
                m.setHome_team(curr.getString("home_team"));
                m.setAway_team(curr.getString("away_team"));
                m.setHome_score(curr.getInteger("home_score"));
                m.setAway_score(curr.getInteger("away_score"));
                ret.add(m);
            }
            request.setAttribute("result", ret);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/FiveQuery.jsp");
            dispatcher.forward(request, response);


        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
