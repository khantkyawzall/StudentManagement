
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value="/resources/test.css"/>">


<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

<title>Course Registration</title>
<style>
.error {
	color: red;
	margin-left: 440px;
	margin-bottom: 20px;
}
/* DataTables CSS */
.dataTables_wrapper {
	margin: 1em 0;
	font-family: Arial, sans-serif;
	font-size: 14px;
	display: grid;
	grid-template-columns: 1fr 1fr; /* Adjusted grid template columns */
}

table.dataTable {
	width: 80%;
	margin: 0 20px;
	clear: both;
	border-collapse: collapse;
	border-spacing: 0;
	grid-column: 1/-1; /* Spanning across all columns */
}

.dataTables_length, .dataTables_info, .dataTables_paginate {
	font-size: 14px;
}

.dataTables_length select {
	width: 75px;
}

.dataTables_filter input {
	width: 250px;
	grid-column: 2; /* Aligning the search input to the right */
}

.dataTables_paginate {
	text-align: right;
	padding-top: 1em;
	grid-column: 2; /* Aligning pagination to the right */
}

/* Bootstrap 5.3.0 overrides */
div.dataTables_wrapper div.dataTables_length label {
	font-weight: normal;
}

/* Optional: Styling for alternating row colors */
table.dataTable tbody tr:nth-child(odd) {
	background-color: #f9f9f9;
}

table.dataTable tbody tr:nth-child(even) {
	background-color: #ffffff;
}
</style>
</head>

<body>
	<div id="testheader">
		<div class="container">
			<div class=row>
				<div class="col-md-5 ">
					<a href="/Student_Registeration/MNU001">
						<h3>Student_Registration</h3>
					</a>
				</div>
				<div class="col-md-6">
					<p>User: ${user.name}</p>
					<p>
						Current Date : <span id="current-date"></span>
					</p>
				</div>
				<div class="col-md-1">
					<form action="logout">
						<button type="button" class="btn-basic" id="lgnout-button"
							value="Log Out">Log Out</button>
					</form>
				</div>
			</div>
		</div>

	</div>
	<!-- <div id="testsidebar">Hello World </div> -->
	<div class="container">
		<div class="sidenav">

			<button class="dropdown-btn">
				Class Management <i class="fa fa-caret-down"></i>
			</button>

			<div class="dropdown-container">
				<a href="/StudentReg/useruser/courseregisterForUser">Course
					Registration </a> <a
					href="/StudentReg/studentstudent/studentregisterForUser">Student
					Registration </a> <a
					href="/StudentReg/studentstudent/studentsearchForUser">Student
					Search </a>
			</div>

		</div>
		<div class="main_contents">
			<div id="sub_content">

				<div>

					<table class="table" id="studentTable" style="width: 80%;">

						<thead>
							<tr>
								<th>No</th>
								<th>Student Id</th>
								<th>Student Name</th>
								<th>Student Phone</th>
								<th>Courses</th>
								<th>Student Photo</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="count" value="0" />
							<c:forEach items="${student}" var="student">
								<tr>
									<td>${count + 1}</td>
									<td>${student.id}</td>
									<td>${student.name}</td>
									<td>${student.phone}</td>
									<td><c:forEach var="course" items="${student.courses}">
                ${course}<br>
										</c:forEach></td>
									<td><img src="data:image/jpeg;base64,${student.photo}"
										alt="Student Image" style="max-width: 70px; max-height: 70px;"></td>

								</tr>
								<c:set var="count" value="${count + 1}" />

							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="testfooter">
		<span>Copyright &#169; ACE Inspiration 2022</span>
	</div>
	<script>
		/* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
		var dropdown = document.getElementsByClassName("dropdown-btn");
		var i;

		for (i = 0; i < dropdown.length; i++) {
			dropdown[i].addEventListener("click", function() {
				this.classList.toggle("active");
				var dropdownContent = this.nextElementSibling;
				if (dropdownContent.style.display === "block") {
					dropdownContent.style.display = "none";
				} else {
					dropdownContent.style.display = "block";
				}
			});
		}
	</script>
	<script>
		// Get current date
		var currentDate = new Date();
		// Extract year, month, and day
		var year = currentDate.getFullYear().toString().slice(-4); // Extract last two digits of the year
		var month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // Month is zero-based, so add 1, and pad with leading zero if necessary
		var day = currentDate.getDate().toString().padStart(2, '0'); // Pad day with leading zero if necessary
		// Set the formatted date to the element
		document.getElementById('current-date').textContent = year + '.'
				+ month + '.' + day;
	</script>
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script
		src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/2.0.1/js/dataTables.bootstrap5.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

	<script>
		$(document).ready(function() {
			$('#studentTable').DataTable();
		});
	</script>
	<script>
    // Get the button for logout
    var logoutBtn = document.getElementById("lgnout-button");

    // Add click event listener for logout
    logoutBtn.onclick = function() {
        // Show alert for confirmation
        var confirmLogout = confirm("Are you sure you want to log out?");
        if (confirmLogout) {
            // Redirect to logout servlet
            window.location.href = '../logout';
        }
    }
</script>
</body>

</html>




