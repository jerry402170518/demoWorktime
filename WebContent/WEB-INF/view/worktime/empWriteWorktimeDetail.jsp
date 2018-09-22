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
        <a class="navbar-brand" href="empMain.html">工時系統</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
            aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="empMain.html">首頁
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Worktime?action=writeWorktime_page">填寫工時</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Worktime?action=searchWorktime_page">查詢工時</a>
                </li>
            </ul>
            <div class="btn-group mr-2">
                <button type="button" class="btn btn-success">
                    <i class="fas fa-user mr-2"></i>林彥儒</button>
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
            <a href="login.html" class="btn btn-warning navBtn font-weight-bold mr-3">
                <i class="fas fa-sign-out-alt mr-1"></i>登出</a>
        </div>
    </nav>
    <div class="container-fluid">
        <div class="card rounded-0 mt-2 text-center">
            <div class="card-header font-weight-bold">
                填寫工時
            </div>
            <div class="card-body">

            </div>
            <card-footer>
                <table class="table table-bordered mb-0 table-hover" id="project">
                    <thead class="thead-dark">
                        <tr>
                            <th class="align-middle">日期</th>
                            <th class="align-middle">專案編號</th>
                            <th class="align-middle" style="width: 30%">工作內容</th>
                            <th class="align-middle">工作時數</th>
                            <th class="align-middle">動作</th>
                        </tr>
                    </thead>
                    <tbody>
                    		<c:forEach var="worktimeDetail" items="${requestScope.worktimeDetailList}" varStatus="loop">                 			
                    				
                    				<form method="post">
                    				<input type="hidden" name="weekFirstDay" value="${requestScope.weekFirstDay}">
                    				<tr>
                    				<td class="align-middle">
	                    				<c:out value="${worktimeDetail.currentDate}"/>
	                                    <input type="hidden" class="form-control" name="currentDate" value="${worktimeDetail.currentDate}">
	                                </td>
	                                <td class="align-middle">
	                                	<c:out value="${worktimeDetail.projectNo}"/>
	                                    <input type="hidden" class="form-control" name="project" value="${worktimeDetail.projectNo}">
	                                </td>
	                                <td class="align-middle">
	                                    <c:out value="${worktimeDetail.note}"/>
	                                </td>
	                                <td class="align-middle">
	                                    <c:out value="${worktimeDetail.hours}"/>
	                                </td>
	                                <td class="align-middle">
	                                    <input type="submit" value="刪除" class="btn btn-danger delBtn" formaction="WorktimeDetail?action=deleteWorktime">
	                                </td>
                    			</tr>
                    			</form>
                    		</c:forEach>
                            <tr>
                            <form method="post">
                            	<input type="hidden" name="weekFirstDay" value="${requestScope.weekFirstDay}">
                                <td class="align-middle">
                                    <input type="date" class="form-control" name="currentDate">
                                </td>
                                <td class="align-middle">
                                    <input type="text" class="form-control" name="project">
                                </td>
                                <td>
                                    <textarea name="workNote" id="" cols="40" rows="3" class="form-control"></textarea>
                                </td>
                                <td class="align-middle">
                                    
                                    <input type="number" max="24" min="1" class="form-control mt-1" name ="hours" required>
                                    
                                </td>
                                <td class="align-middle text-center">
                            		<button type="submit" class="btn btn-secondary" formaction="WorktimeDetail?action=insertWorktime">暫存</button>
                                    
                                </td>
                            </tr>
                       		 </form>
                       		 <form action="Worktime?action=submitWortkime">
                            <tr>
                            	<td colspan="5" class="align-middle">
               						<button type="submit" class="btn btn-warning" id="addProjectBtn">提交本周工時</button>
               					</td>
                            </tr>
                            </form>
                    </tbody>
                </table>
                <!--  -->
            </card-footer>
        </div>
    </div>
    <footer class="bg-secondary text-white text-center py-2 my-4">
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
                <div class="modal-body px-0 py-0">
                    <table class="table text-center">
                        <tr>
                            <td>員工編號</td>
                            <td> 00000000</td>
                        </tr>
                        <tr>
                            <td>姓名</td>
                            <td> 林彥儒</td>
                        </tr>
                        <tr>
                            <td>email</td>
                            <td>yanru4021470518@gamil.com</td>
                        </tr>
                        <tr>
                            <td>職位</td>
                            <td>員工</td>
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
                    <form class="form" role="form">
                        <div class="form-group">
                            <label>請輸入舊密碼</label>
                            <input name="old_pw" id="old_pw" class="form-control old-pw" type="password">
                            <span class="error-msg" style="color: red"></span>
                        </div>
                        <div class="form-group">
                            <label>請輸入新密碼</label>
                            <input id="user-pwd-input" name="user-pwd-input" type="password" placeholder="請輸入不得為空且少於8位的數字與英文組合" class="new-pw form-control"
                            />
                            <span class="error-msg" style="color: red"></span>
                        </div>
                        <div class="form-group">
                            <label>請再次輸入新密碼</label>
                            <input placeholder="再次新確認密碼" class="form-control new-pw" type="password" name="user_pwd_again" />
                            <span class="error-msg" style="color: red"></span>
                        </div>

                        <div class="modal-footer pr-0">
                            <button type="button" class="btn btn-success">確定變更</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- reason Modal -->
    <div class="modal fade" id="reasonModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header bg-danger text-light">
                    <h5 class="modal-title" id="reasonModal">未通過原因</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit. Alias quaerat dolore repellat commodi ullam accusantium quas, reiciendis
                    maiores adipisci a sunt sit voluptatem, iusto quibusdam libero optio quae aut porro!
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>
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
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="js/empWriteWorktimeDetail.js"></script>
</body>

</html>