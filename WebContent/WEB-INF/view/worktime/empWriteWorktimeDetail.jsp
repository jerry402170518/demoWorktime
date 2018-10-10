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

        .bg-holiday {
            background-color: rgb(241, 86, 86);
        }
    </style>
</head>

<body>
	<c:if test="${not empty deleteSuccess}">
		<script>
	  	 swal ( "${deleteSuccess}" ,  " " ,  "success" )
		  </script>
	</c:if>
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
                    <a class="nav-link" href="Worktime?action=writeWorktime_page">填寫工時</a>
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
    
    <div class="container-fluid">
        <div class="card rounded-0 mt-2 text-center">
            <div class="card-header font-weight-bold">
                填寫工時
            </div>
            <div class="card-body">
           		 <h4><span class="badge badge-success p-2">${requestScope.weekFirstDay}~${requestScope.weekLastDay}</span></h4>
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
                    				
                    				<form id="deleteForm" action="WorktimeDetail?action=deleteWorktime" method="post">
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
	                                    <input type="button" onclick="deleteFunction()" value="刪除" class="btn btn-danger delBtn">
	                                </td>
                    			</tr>
                    			</form>
                    		</c:forEach>
                            <tr>
                            <form method="post">
                            	<input type="hidden" name="weekFirstDay" value="${requestScope.weekFirstDay}">
                                <td class="align-middle">
                                    <input type="date" class="form-control" name="currentDate" value="${sessionScope.employeeInput['currentDate']}">
                                </td>
                                <td class="align-middle">
                                    <input type="text" class="form-control" name="project" value="${sessionScope.employeeInput['project']}">
                                </td>
                                <td>
                                    <textarea name="workNote" id="" cols="40" rows="3" class="form-control">${sessionScope.employeeInput['workNote']}</textarea>
                                </td>
                                <td class="align-middle">
                                    
                                    <input type="number" max="12" min="1" class="form-control mt-1" name ="hours" value="${sessionScope.employeeInput['hours']}" required>
                                    
                                </td>
                                <td class="align-middle text-center">
                            		<button type="submit" class="btn btn-secondary" formaction="WorktimeDetail?action=insertWorktime">暫存</button>
                                    
                                </td>
                            </tr>
                       		 </form>
                       		 
                    		<c:if test="${not empty requestScope.errorMsg}"><tr><td colspan="5" class="text-danger">${requestScope.errorMsg}</td></tr></c:if>
                       		 <form id="submitForm" action="WorktimeDetail?action=submitWortkime" method="post">
                       		 <input type="hidden" name="weekFirstDay" value="${requestScope.weekFirstDay}">
                            <tr>
                            	<td colspan="5" class="align-middle">
                          
               						<button type="button" onclick="submitFunction()" class="btn btn-warning">提交本周工時</button>
               					</td>
                            </tr>
                            </form>
                    </tbody>
                </table>
                <!--  -->
            </card-footer>
        </div>
    </div>
    <footer class="bg-secondary text-white text-center py-2">
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
    <script>
    function submitFunction() {
    	  swal({
    	    title: "是否提交?",
    	    text: "請確定所有工時已填寫詳細!",
    	    icon: "warning",
    	    buttons: true,
    	    dangerMode: true,
    	  })
    	  .then((willDelete) => {
    		  if (willDelete) {
    		   	  document.getElementById("submitForm").submit();
    		   }
    	  });
    	}
    function deleteFunction() {
  	  swal({
  	    title: "是否刪除?",
  	    text: " ",
  	    icon: "warning",
  	    buttons: true,
  	    dangerMode: true,
  	  })
  	  .then((willDelete) => {
  		  if (willDelete) {
  		   	  document.getElementById("deleteForm").submit();
  		   }
  	  });
  	}
    </script>
</body>

</html>