<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.mongodb.client.MongoDatabase" %>
<%@ page import="org.bson.Document" %>
<%@ page import="com.mongodb.client.MongoCollection" %>
<%@ page import="com.mongodb.client.FindIterable" %>
<%@ page import="org.bson.conversions.Bson" %>
<%@ page import="com.mongodb.client.*" %>
<%@ page import="com.mongodb.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mongodb.client.model.Filters" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="static com.mongodb.client.model.Filters.gte" %>
<%@ page import="static javax.management.Query.and" %>
<%@ page import="static com.mongodb.client.model.Filters.eq" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%  MongoClient mongoClient = new MongoClient(
        new MongoClientURI(
                "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false"
        )
);
    MongoDatabase database = mongoClient.getDatabase("footballResult");
    MongoCollection<Document> collection = database.getCollection("partiteDiCalcio");
    System.out.println("Connected to the database successfully");
    System.out.println("ok");
    HashMap<String,Object> filterMap = new HashMap();
    filterMap.put("home_team", "England");
    filterMap.put("away_team", "Scotland");
    filterMap.put("home_score", "2");
    filterMap.put("away_score", "2");

//Costruzione documento filtro, implementare graficamente con una barra di filtri e dare l'input dell'utente alla hash map
    Bson filter = new Document(filterMap);
    FindIterable<Document> docIterator = collection.find().filter(filter);
    MongoCursor<Document> cursor = docIterator.iterator();
    int j=1;
    %> <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <%
    while(cursor.hasNext()){
    %>
<h4> PARTITE CON FILTRO <%=cursor.next()%> </h4>
    <% j++;}%>
<h1> PARTITA QUERY 2 <%= // una partita a caso in cuio l'italia ha giocato in casa, o una qualsiasi squadra presa in input
        collection.find(new Document("home_team", new Document("$eq","Italy"))).first().toString().substring(39)%></h1>





    <% BasicDBObject andQuery = new BasicDBObject();
List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
obj.add(new BasicDBObject("home_score","0"));
obj.add(new BasicDBObject("away_score","0"));
andQuery.put("$and",obj);
    FindIterable<Document> docIterator1 = collection.find(andQuery);
    MongoCursor<Document> cursorquery2 = docIterator1.iterator();
%> <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <%
    for(int indice =0 ; indice <10 ; indice++) {
        %>
<h2> Alcune partite finite 0 a 0  <%=cursorquery2.next().toString().substring(39)%></h2>
    <% indice ++;
    }




        /*
    Bson quertForDate = Filters.and(
            Filters.gte("date", ),
            Filters.lt("date",);
    */

//le partite dei primi due decenni del nuovo millennio si puo fare anche tra due date sottomesse dall'utente
    FindIterable<Document> iter = collection.find();
    MongoCursor<Document> cursorAll = iter.iterator();
    int index = 0;
%> <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <%
    while( cursorAll.hasNext()){
        Document currentDoc = cursorAll.next();
        String data = currentDoc.toString().substring(45,55);
        Date dateCurrent = new SimpleDateFormat("yyyy-MM-dd").parse(data);
        Date da = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
        Date a = new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01");
        if(dateCurrent.before(a) && dateCurrent.after(da) && index <20 ){
            %>
<h1> ECCOLIIIIIIIIIII VAFANGUL A CHELLA BUCCHIN E MAMMT   <%=currentDoc.toString().substring(39)%></h1><%
            index++;
        }
    }


    //tutte le partite dato un torneo
    FindIterable<Document> typeOftournment = collection.find(Filters.eq("tournament", "Friendly"));
    int f = 0;
    MongoCursor<Document> cursortournment = typeOftournment.iterator();
%> <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <%
    while(cursortournment.hasNext() && f<1000) {
        if (f % 25 != 0){
            f++;
            continue;
        }
        f++;
        %>
<h1>PARTITA AMICHEVOLE <%=cursortournment.next()%></h1>
<%;
}

    //partite in cui la squadra di casa ha segnano x(4) o piu gol T EGG FATT PUR A PROIEZION FRAAAAAATM
    Bson filter1 = Filters.gte("home_score", 4L);
    Bson project = Filters.and(Filters.eq("_id", 0L), Filters.eq("home_score", 1L), Filters.eq("away_score", 1L), Filters.eq("home_team", 1L), Filters.eq("away_team", 1L), Filters.eq("date", 1L));
    FindIterable<Document> resultProj = collection.find(filter1)
            .projection(project);
    int h=0;
    MongoCursor<Document> cursorGTE = resultProj.iterator();
    while(cursorGTE.hasNext() && h<5) {
        System.out.println(cursorGTE.next());
        h++;
    }




    FindIterable<Document> view = database.getCollection("ItalyVSFranceView").find();
    MongoCursor<Document> cursorView = view.iterator();
    while(cursorView.hasNext()){
        System.out.println("Date in view"+ cursorView.next());
    }
    // SEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

</body>
</html>
