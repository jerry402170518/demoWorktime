<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>工時系統</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B"
        crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt"
        crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
</head>

<body>
    <div class="container-fluid">
        <!--  -->
        
        <div class="row justify-content-center">
            <div class="col-12   topBlank"></div>
            <div class="col-1"></div>
            <div class="login-bg col-6" style="background-image: url(img/bg-01.jpg);">
                <h2 class="text-light title">LOGIN</h2>
            </div>
            <div class="col-1"></div>
            <div class="col-6 bg-form">
                <form class="form mt-5" id="loginForm" action="login" method="post">
                    <div class="row justify-content-center">
                        <div class="col-2 text-right mr-4 pt-1">
                            <span class="h4 text-secondary font-weight-bold">帳號test</span>
                        </div>
                        <div class="col-4" style="border-bottom: 1px solid #555555;">
                            <input type="text" class="form-control" name="inputUsername" id="inputUsername" placeholder="Username" style="border:none;">
                        </div>
                    </div>
                    <div class="row justify-content-center mt-5">
                        <div class="col-2 text-right mr-4 pt-1">
                            <span class="h4 text-secondary font-weight-bold">密碼</span>
                        </div>
                        <div class="col-4" style="border-bottom: 1px solid #555555;">
                            <input type="password" class="form-control" id="inputPassword" name="inputPassword" placeholder="Password" style="border:none;">
                        </div>
                    </div>
                    <div class="row mt-5 pb-3">
                        <div class="col-5"></div>
                        <div class="col-4">
                                <button type="submit" class="btn btn-success btn-login font-weight-bold">登入帳號</button>
                        </div>
                        <div class="col-9"></div>
                        <div class="col-3">
                            <a href="ForgetPassword?action=forgetPassword_page">忘記密碼</a>
                            <a href="StartEmployee?action=startAccount_page">啟動帳號</a></div>
                        <div class="col-1"></div>
                    </div>
                    <div class="row">
                        <div class="col-12 mb-5 pb-3 text-center errorMsg">
                     	   <c:if test="${not empty errorMsg}">
 							  <c:out value="${requestScope.errorMsg}"/>
						   </c:if>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
    </div>



    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js" integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em"
        crossorigin="anonymous"></script>
    <script src="js/login.js"></script>
</body>

</html>
