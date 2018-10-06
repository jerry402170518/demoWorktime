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
    <style>
        html {
            font-size: 20px;
        }

        .bg-holiday {
            background-color: rgb(241, 86, 86);
        }
    </style>
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark py-3 font-weight-bold">
        <a class="navbar-brand" href="Employee?action=empMain_page">工時系統</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
            aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="Employee?action=empMain_page">首頁
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">填寫工時</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Worktime?action=searchWorktime_page">查詢工時</a>
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
    <div class="card mt-1 d-flex rounded-0 text-center ">
        <div class="card-header font-weight-bold">
            工時填寫
        </div>
        <div class="card-body">
        	<div class="row d-flex justify-content-center align-items-center mb-3">
                <div class="col-5">
                    <form action="Worktime?action=writeWorktime_page" method="post">
                        <div class="form-row">
                            <p class="col mt-1 mr-2 text-right font-weight-bold">
                       		         請輸入月份:
                            </p>
                            <div class="col">
                                <input type="text" name="currentMonth" class="form-control" placeholder="範例格式:2018-09")>
                            </div>
                            <div class="col">
                                <input type="submit" class="btn btn-success" value="查詢">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <table class="table table-bordered mb-0 table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>日期</th>
                        <th>狀態</th>
                        <th>星期日</th>
                        <th>星期一</th>
                        <th>星期二</th>
                        <th>星期三</th>
                        <th>星期四</th>
                        <th>星期五</th>
                        <th>星期六</th>
                        <th>填寫</th>
                    </tr>
                </thead>
                <tbody>
                
                	<c:forEach var="worktime" items="${requestScope.worktimeList}" varStatus="loop" >
                		
                		<tr>
                		   <td><c:out value="${worktime.weekFirstDay}"/>~${requestScope.weekLastDays[loop.index]}</td>
	                 	   <td><c:out value="${worktime.status}" /></td>
	                 	    <% for(int i = 0; i < 7; i++) { 
	                 	    	pageContext.setAttribute("i", i);
	                		%>
					            <td>
					            	<c:if test="${requestScope.holiday[7*loop.index+i] == null or requestScope.holiday[7*loop.index+i] != null && requestScope.hours[7*loop.index+i]>0}"><span>${requestScope.hours[7*loop.index+i]}</span></c:if>
					            	<span class="text-danger" style="display:block">${requestScope.holiday[7*loop.index+i]}</span>
					            </td>
					        <% } %>
	                       <td>
	                       <c:choose>
							    <c:when  test="${worktime.status=='已通過' || worktime.status=='審核中'}">
								        
							    </c:when>    
							    <c:otherwise>
			                       	<form action="WorktimeDetail" method="get">
			                       		<input type="hidden" name="action" value="writeWorktimeDetail_page">
			                       		<input type="hidden" name="weekFirstDay" value="${worktime.weekFirstDay}">
			                       		<input type="submit" value="填寫" class="btn btn-primary"> 
			                       	</form>
							    </c:otherwise>
							</c:choose>
	                       </td>
	                 	</tr>
	                 	<c:if test="${worktime.status=='未通過'}">
	                 		<tr>
	                 			<td>未通過原因</td>
	                 			<td colspan="10" class="text-danger font-weight-bold"><c:out value="${worktime.note}"/></td>
	                 		</tr>
	                 	</c:if>
                	</c:forEach>
	                
                </tbody>
            </table>
        </div>

    </div>
    
    <footer class="bg-secondary text-white text-center py-2" >
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js" integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em"
        crossorigin="anonymous"></script>
</body>

</html>