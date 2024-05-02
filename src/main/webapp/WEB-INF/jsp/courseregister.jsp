<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<!DOCTYPE html>
<html lang="en">

<head>
<link rel="stylesheet" href="resources/test.css"/>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
.success {
	color: green;
	margin-left: 440px;
	margin-bottom: 20px;
}

.error {
	color: red;
	margin-left: 440px;
	margin-bottom: 20px;
}
</style>
</head>

<body>
	<div id="testheader">
		<div class="container">
			<div class=row>
				<div class="col-md-5 ">
					<a href="Student_Registeration/MNU001"><h3>Student_Registration</h3></a>
				</div>
				<div class="col-md-6">
					<p>User: ${user.name}</p>
					<p>
						Current Date : <span id="current-date"></span>
					</p>
				</div>
			<!-- 	<div class="col-md-1">
					<a href="logout"><input type="button"
						class="btn-basic" id="lgnout-button" value="Log Out"></a>
						
				</div>  -->
				<div class="col-md-1">
    <form action="logout">
        <button type="button" class="btn-basic" id="lgnout-button" value="Log Out">Log Out</button>
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
				<a href="/StudentReg/courseregister">Course Registration </a> <a
					href="/StudentReg/student/studentregister">Student Registration
				</a> <a href="/StudentReg/student/studentsearch">Student Search </a>
			</div>
			<a href="/StudentReg/user/userlist">Users Management</a> <a
				href="/StudentReg/user/userregister">User Registeration</a>
		</div>
		<div class="main_contents">
			<div id="sub_content">
				<form:form action="courseregister" method="post"
					modelAttribute="course">
					<h2 class="col-md-6 offset-md-2 mb-5 mt-4">Course Registration</h2>
					<form:errors path="name" cssClass="error" />
					<!-- Display course name error -->
					<h3 class="error">${error1}</h3>
					<h3 class="success">${success}</h3>


					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="name" class="col-md-2 col-form-label">Name</label>
						<div class="col-md-4">
							<form:input type="text" class="form-control" path="name"></form:input>
						</div>
					</div>


					<div class="col-md-4"></div>

					<div class="col-md-6">


						<button type="submit" class="btn btn-secondary col-md-2"
							data-bs-toggle="modal" data-bs-target="#exampleModal" >Add</button>
						

					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div id="testfooter">
		<span>Copyright &#169; ACE Inspiration 2022</span>
	</div>
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
    // Get the button for logout
    var logoutBtn = document.getElementById("lgnout-button");

    // Add click event listener for logout
    logoutBtn.onclick = function() {
        // Show alert for confirmation
        var confirmLogout = confirm("Are you sure you want to log out?");
        if (confirmLogout) {
            // Redirect to logout servlet
            window.location.href = 'logout';
        }
    }
</script>
</body>

</html>