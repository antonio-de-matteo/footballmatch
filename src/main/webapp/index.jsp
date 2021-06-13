<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.mongodb.MongoClient" %>
<%@ page import="com.mongodb.MongoCredential" %>
<%@ page import="com.mongodb.client.MongoDatabase" %>
<%@ page import="org.bson.Document" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mongodb.client.MongoCollection" %>
<%@ page import="com.mongodb.client.FindIterable" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.bson.conversions.Bson" %>
<%@ page import="com.mongodb.client.MongoCursor" %>

<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<br/>
<% MongoClient mongo = new MongoClient( "localhost", 27017 );
    MongoCredential credential;
    credential = MongoCredential.createCredential("admin", "myDb","admin".toCharArray());
    System.out.println("Connected to the database successfully");
    MongoDatabase database = mongo.getDatabase("footballResult");
    MongoCollection<Document> collection= database.getCollection("partiteDiCalcio");
    System.out.println("ok");
    FindIterable<Document> iterable= collection.find();
    int i=1;
    Iterator it= iterable.iterator();

    while(it.hasNext()){
%>
<div>
<h3> Partita:  <%= it.next()%> </h3>
</div>
<%
        i++;
    }
    HashMap<String,Object> filterMap = new HashMap();
    filterMap.put("home_team", "England");
    filterMap.put("away_team", "Scotland");
    filterMap.put("home_score", "2");
    filterMap.put("away_score", "2");

//Costruzione documento filtro
    Bson filter = new Document(filterMap);
    FindIterable<Document> docIterator = collection.find().filter(filter);
    MongoCursor<Document> cursor = docIterator.iterator();
    int j=1;
    while(cursor.hasNext()){

    %>
<h4> PARTITE CON FILTRO <%=cursor.next()%> </h4>
    <% j++;}

%>
</body>
</html>