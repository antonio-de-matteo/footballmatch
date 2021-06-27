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


<!DOCTYPE html>
<html lang="en">
<head>
    <title>JSP - Hello World</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="searchMatch.js"></script>

    <style>
        body {
            font-family: "Lato", sans-serif;
        }

        .sidenav {
            height: 100%;
            width: 200px;
            position: fixed;
            z-index: 1;
            top: 0;
            left: 0;
            background-color: #111;
            overflow-x: hidden;
            padding-top: 20px;
        }

        .sidenav a {
            padding: 6px 6px 6px 32px;
            text-decoration: none;
            font-size: 25px;
            color: #818181;
            display: block;
        }

        .sidenav a:hover {
            color:#111111;
        }

        .main {
            margin-left: 200px; /* Same as the width of the sidenav */
        }

        @media screen and (max-height: 450px) {
            .sidenav {padding-top: 15px;}
            .sidenav a {font-size: 18px;}
        }

    </style>
    <!-- Bootstrap CSS -->
</head>

<body id="page-top">

<!-- Page Wrapper -->
<div class="sidenav" style="background-image: url('bandiere.jpg');">
    <div class="transbox" style="margin: 30px; background-color: #ffffff; opacity: 0.9; height: 80%">
        <img src="logo_standard.png" style="width: 40%; margin-top: 30%; margin-left:30%  ">
        <a href="index.jsp">Home</a>
        <a href="OneQuery.jsp">1 Query</a>

        <a href="TwoQuery.jsp">2 Query</a>

        <a href="ThreeQuery.jsp">3 Query</a>

        <a href="FourQuery.jsp">4 Query</a>

        <a href="FiveQuery.jsp">5 Query</a>

        <a href="SixQuery.jsp">6 Query</a>

        <a href="SevenQuery.jsp">7 Query</a>


    </div>
</div>

<div class="main">

    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Search Match filtered by tournment</h6>
        </div>
        <div class="card-body">
            <form id="searchQueryUno">
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label class="text-black">Tournment Name</label>
                        <select class="form-control" name="homeTeam" required placeholder="Insert Home Team">
                            <option>Friendly</option>
                            <option>British Championship</option>
                            <option>Copa Premio Honor Argentino</option>
                            <option>Copa Premio Honor Uruguayo</option>
                            <option>Copa Lipton</option>
                            <option>Copa América</option>
                            <option>Copa Chevallier Boutell</option>
                            <option>FIFA World Cup</option>
                        </select>
                    </div>


                </div>
                <button type="submit" class="btn btn-primary right" onclick="searchMatch()">Search</button>
            </form>
        </div>
    </div>


    <div class="table-responsive" id="matchTable" style="display: none; width: 90%;
    margin-left: 5%;">
        <table class="table table-bordered"  width="100%" cellspacing="0">
            <thead>
            <tr>
                <th>Date</th>
                <th>Home Team</th>
                <th>Away Team</th>
                <th>Home score</th>
                <th>Away score</th>
                <th>Tournament</th>
                <th>City</th>
                <th>Country</th>
                <th>Neutral</th>
            </tr>
            </thead>
        </table>
    </div>
</div>


<footer class="sticky-footer bg-white">
    <div class="container my-auto">
        <div class="copyright text-center my-auto">
            <span>Copyright &copy; UNISA</span>
        </div>
    </div>
</footer>
</div>

</body>
</html>