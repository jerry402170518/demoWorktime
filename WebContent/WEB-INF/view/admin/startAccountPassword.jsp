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
</head>

<body>
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="col-12" style="height: 200px;"></div>
            <div class="col-md-auto">
                <div class="card text-center" style="width: 30rem; ">
                    <div class="card-header h3 font-weight-bold">
                        啟動帳號
                    </div>
                    <div class="card-body">
                        <form action="StartEmployee?action=insertPassword" method="post">
                      	    <input type="hidden" name="empno" value="${empno}">
                            <div class="form-group">
                                <label for="password">設定密碼</label>
                                <input type="text" class="form-control" style="text-align:center" name="password" id="password" aria-describedby="empnoHelp" placeholder="請輸入密碼">
                            </div>
                            <div class="form-group">
                                <label for="repeatPassword">請再次輸入密碼</label>
                                <input type="password" class="form-control"  style="text-align:center" name="repeatPassword" id="repeatPassword" placeholder="密碼必須相同">
                            </div>
                            <button type="submit" class="btn btn-primary">啟動帳號</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js" integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em"
        crossorigin="anonymous"></script>
</body>

</html>