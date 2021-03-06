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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
    
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <style>
        html {
            font-size: 20px;
        }
    </style>
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark py-3 font-weight-bold">
        <a class="navbar-brand" href="#">工時系統</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
            aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">首頁
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Worktime?action=mgrCheckWorktime_page">審核工時</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Worktime?action=mgrUrgeWorktime_page">催繳工時</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Worktime?action=mgrSearchWorktime_page">查詢員工工時</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Employee?action=searchEmpInfo_page">查詢員工資料</a>
                </li>
            </ul>
            <div class="btn-group mr-2">
                <button type="button" class="btn btn-success" style="cursor:default">
                    <i class="fas fa-user mr-2"></i>${sessionScope.login.name}</button>
                <button type="button" class="btn btn-success dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true"
                    aria-expanded="false">
                    <span class="sr-only">Toggle Dropdown</span>
                </button>
                <div class="dropdown-menu">
                    <button type="button" class="dropdown-item" data-toggle="modal" data-target="#personInfo">
                        <i class="fas fa-info mr-3"></i>個人資料
                    </button>
                    <div class="dropdown-divider"></div>
                    <button type="button" class="dropdown-item" data-toggle="modal" data-target="#changePwd" data-backdrop="static">
                        <i class="fas fa-unlock-alt mr-2"></i>變更密碼
                    </button>
                </div>
            </div>
            <a href="Logout" class="btn btn-warning navBtn font-weight-bold mr-3">
                <i class="fas fa-sign-out-alt mr-1"></i>登出</a>
        </div>
    </nav>
    <div class="container-fluid mt-5">
            <div class="row mt-4 mx-1">
                    <div class="col-md-6 mt-3">
                        <div class="row no-gutters d-flex text-center">
                                <div class="col-6"><a href="Worktime?action=mgrCheckWorktime_page" class="badge badge-success rounded-0 btn-block"><i class="fas fa-user-check fa-5x pt-4"></i><div class="h3 pt-2 pb-4 font-weight-bold">審核工時</div></a></div>
                                <div class="col-6"><a href="Worktime?action=mgrUrgeWorktime_page" class="badge badge-warning rounded-0 btn-block"><i class="fas fa-bullhorn fa-5x pt-4"></i><div class="h3 pt-2 pb-4 font-weight-bold">催繳工時</div></a></div>
                                <div class="col-6"><a href="Worktime?action=mgrSearchWorktime_page" class="badge badge-warning rounded-0 btn-block"><i class="fas fa-user-clock fa-5x pt-4"><div class="h3 pt-2 pb-4 font-weight-bold">查詢員工工時</div></i></a></div>
                                <div class="col-6"><a href="Employee?action=searchEmpInfo_page" class="badge badge-success rounded-0 btn-block"><i class="fas fa-search fa-5x pt-4"></i><div class="h3 pt-2 pb-4 font-weight-bold">查詢員工資料</div></a></div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card rounded-0">
                            <div class="card-header text-center font-weight-bold">
                                本月工時審核筆數
                            </div>
                            <div class="card-body">
                               <canvas id="barCanvas" width="775" height="387" class="chartjs-render-monitor" style="display: block; "></canvas>
                                    
                            </div>
                        </div>
                    </div>
                </div>
    </div>
    

    <footer class="bg-secondary text-white text-center fixed-bottom">
        工時系統 Copyright © 2018 YanRu Lin All rights reserved
    </footer>
  <!-- personal Modal-->
    <div class="modal fade" id="personInfo" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header bg-info text-light">
                    <h5 class="modal-title" id="exampleModalLabel">
                        <i class="fas fa-info mr-3"></i>個人資料</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body p-0">
                    <table class="table text-center">
                        <tr>
                            <td>員工編號</td>
                            <td>${sessionScope.login.empno}</td>
                        </tr>
                        <tr>
                            <td>姓名</td>
                            <td>${sessionScope.login.name}</td>
                        </tr>
                        <tr>
                            <td>email</td>
                            <td>${sessionScope.login.email}</td>
                        </tr>
                        <tr>
                            <td>職位</td>
                            <td>${sessionScope.login.position}</td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary btn-block" data-dismiss="modal">關閉</button>
                </div>
            </div>
        </div>
    </div>

    <!-- changePwd Modal -->
    <div class="modal fade" id="changePwd" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header bg-warning">
                    <h5 class="modal-title text-center" id="exampleModalLabel">
                        <i class="fas fa-unlock-alt mr-2"></i>變更密碼</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form class="form" id="formChange" action="changePassword" method="post">
                        <div class="form-group">
                            <label>請輸入舊密碼</label>
                            <input name="oldPwd" id="oldPwd" class="form-control old-pw" type="password">
	                         <c:if test="${not empty oldPwdError}">
							 	<script>
						 		 	swal ( "${oldPwdError}" ,  "請輸入正確的舊密碼!" ,  "error" )
								</script>
							 </c:if>
                            </span>
                        </div>
                        <div class="form-group">
                            <label>請輸入新密碼</label>
                            <input id="newPwdFirst" name="newPwdFirst" type="password" placeholder="請輸入不得為空且少於8位的數字與英文組合" class="new-pw form-control"
                            />
                            <c:if test="${not empty pwdTheSame}">
							    <script>
							   		 swal ( "${pwdTheSame}" ,  "請輸入不一樣的新密碼!" ,  "error" )
							    </script>
							</c:if>
                        </div>
                        <div class="form-group">
                            <label>請再次輸入新密碼</label>
                            <input placeholder="再次新確認密碼" class="form-control new-pw" type="password" name="newPwdSecond" />
                            <c:if test="${not empty doubleCheckError}">
							    <script>
							   		 swal ( "${doubleCheckError}" ,  "請確認輸入的兩欄新密碼是否相同!" ,  "error" )
							    </script>
							</c:if>
                        </div>
						<c:if test="${not empty changeSuccess}">
							    <script>
							   		 swal ( "${changeSuccess}","請記住新密碼", "success" )
							    </script>
						</c:if>
                        <div class="modal-footer pr-0">
                            <button type="submit" class="btn btn-success">確定變更</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>
        window.chartColors = {
            red: 'rgb(255, 99, 132)',
            orange: 'rgb(255, 159, 64)',
            yellow: 'rgb(255, 190, 38)',
            green: 'rgb(70, 183, 53)',
            blue: 'rgb(54, 162, 235)',
            purple: 'rgb(153, 102, 255)',
            grey: 'rgb(201, 203, 207)'
        };

        var randomScalingFactor = function () {
            return Math.round(Math.random() * 10);
        };
        var MONTHS = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        var colors = Chart.helpers.color;
        var barChartData = {
            labels: ["第一周", "第二周", "第三周", "第四周", "第五周"],
            datasets: [{
                label: '此周需審核筆數',
                backgroundColor: colors(window.chartColors.green).alpha(0.8).rgbString(),
                borderColor: window.chartColors.green,
                borderWidth: 1,
                data: [
                    ${requestScope.totalCheck[0]},
                    ${requestScope.totalCheck[1]},
                    ${requestScope.totalCheck[2]},
                    ${requestScope.totalCheck[3]},
                    ${requestScope.totalCheck[4]},
                ]
            }, {
                label: '此周已通過筆數',
                backgroundColor: colors(window.chartColors.yellow).alpha(0.8).rgbString(),
                borderColor: window.chartColors.yellow,
                borderWidth: 1,
                data: [
                	${requestScope.totalPass[0]},
                	${requestScope.totalPass[1]},
                	${requestScope.totalPass[2]},
                	${requestScope.totalPass[3]},
                	${requestScope.totalPass[4]},
                ]
            }]

        };

        window.onload = function () {


            // bar
            var barCtx = document.getElementById("barCanvas").getContext("2d");
            window.myBar = new Chart(barCtx, {
                type: 'bar',
                data: barChartData,
                options: {
                    responsive: true,
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: '單位/筆'
                    }
                }
            });

        };
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js" integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em"
        crossorigin="anonymous"></script>
</body>

</html>