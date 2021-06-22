
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
            color: #111111;
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
        <a href="home.jsp">Match</a>
    </div>
</div>

    <div class="main">

            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Match Project </h6>
                </div>
                <div class="card-body">

                    <b> Context </b> <br>
                    Well, what happened was that I was looking for a semi-definite easy-to-read list of international football matches and couldn't find anything decent. So I took it upon myself to collect it for my own use. I might as well share it.

                    <br> <b> Content </b> <br>
                    This dataset includes 42,183 results of international football matches starting from the very first official match in 1972 up to 2019. The matches range from FIFA World Cup to FIFI Wild Cup to regular friendly matches. The matches are strictly men's full internationals and the data does not include Olympic Games or matches where at least one of the teams was the nation's B-team, U-23 or a league select team.
                    results.csv includes the following columns:

                    <ul>
                        <li> date - date of the match </li>
                        <li> home_team - the name of the home team </li>
                    <li> away_team - the name of the away team </li>
                    <li> home_score - full-time home team score including extra time, not including penalty-shootouts </li>
                    <li>away_score - full-time away team score including extra time, not including penalty-shootouts </li>
                    <li>tournament - the name of the tournament </li>
                    <li>city - the name of the city/town/administrative unit where the match was played </li>
                    <li>country - the name of the country where the match was played </li>
                    <li> neutral - TRUE/FALSE column indicating whether the match was played at a neutral venue </li>
                    </ul>
                    <b> Acknowledgements </b> <br>
                    The data is gathered from several sources including but not limited to Wikipedia, fifa.com, rsssf.com and individual football associations' websites.


                </div>
            </div>
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