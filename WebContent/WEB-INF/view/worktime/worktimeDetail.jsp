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
    
    <div class="container-fluid">
        <div class="card rounded-0 text-center border-0">
            <card-footer>
                 <table class="table table-bordered mb-0 table-hover" id="project">
                    <thead class="thead-dark">
                        <tr>
                            <th class="align-middle">日期</th>
                            <th class="align-middle">專案編號</th>
                            <th class="align-middle" style="width: 30%">工作內容</th>
                            <th class="align-middle">工作時數</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<c:forEach var="worktimeDetail" items="${requestScope.worktimeDetailList}" varStatus="loop">                 			
                    				<input type="hidden" name="weekFirstDay" value="${requestScope.weekFirstDay}">
                    				<tr>
                    				<td class="align-middle">
	                    				<c:out value="${worktimeDetail.currentDate}"/>
	                                </td>
	                                <td class="align-middle">
	                                	<c:out value="${worktimeDetail.projectNo}"/>
	                                </td>
	                                <td class="align-middle">
	                                    <c:out value="${worktimeDetail.note}"/>
	                                </td>
	                                <td class="align-middle">
	                                    <c:out value="${worktimeDetail.hours}"/>
	                                </td>
                    			</tr>  
	                             <c:if test="${not empty note}">
	                                <tr class="font-weight-bold text-danger">
	                                	<td>未通過原因</td>
	                                	<td colspan="3">${requestScope.note}</td>
	                                </tr>
	                             </c:if>	
                    	</c:forEach>
                    </tbody>
                </table>
            </card-footer>
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