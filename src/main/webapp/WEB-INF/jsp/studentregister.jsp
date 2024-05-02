<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

.success {
	color: green;
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
					<a href="/Student_Registeration/MNU001"><h3>Student
							Registration</h3></a>
				</div>
				<div class="col-md-6">
					<p>User: ${user.name}</p>
					 
					<p>
						Current Date : <span id="current-date"></span>
					</p>
				</div>
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
				<form:form action="studentregister" method="post"
					modelAttribute="student" enctype="multipart/form-data">

					<h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student
						Registration</h2>
					<form:errors path="name" cssClass="error" />
					<h6 class="error">${error}</h6>
					<h6 class="error">${error1}</h6>
					<h6 class="error">${error2}</h6>
					<h6 class="success">${success}</h6>


					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="name" class="col-md-2 col-form-label">Name</label>
						<div class="col-md-4">
							<form:input type="text" class="form-control" path="name"></form:input>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="dob" class="col-md-2 col-form-label">DOB</label>
						<div class="col-md-4">
							<form:input type="date" class="form-control" path="dob"></form:input>
						</div>
					</div>
					<fieldset class="row mb-4">
						<div class="col-md-2"></div>
						<legend class="col-form-label col-md-2 pt-0">Gender</legend>
						<div class="col-md-4">
							<div class="form-check-inline">
								<form:radiobutton path="gender" value="Male" label="Male" />
							</div>
							<div class="form-check-inline">
								<form:radiobutton path="gender" value="Female" label="Female" />
							</div>
						</div>
					</fieldset>


					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="phone" class="col-md-2 col-form-label">Phone</label>
						<div class="col-md-4">
							<form:input type="text" class="form-control" path="phone"
								id="phone"></form:input>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="education" class="col-md-2 col-form-label">Education</label>
						<div class="col-md-4">
							<form:select class="form-select" path="education">
								<form:option value="Bachelor of Information Technology">Bachelor of Information Technology</form:option>
								<form:option value="Diploma in IT">Diploma in IT</form:option>
								<form:option value="Bachelor of Ceomputer Scienc">Bachelor of Computer Science</form:option>

							</form:select>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="courses" class="col-md-2 col-form-label">Courses</label>
						<div class="col-md-4">
							<c:forEach items="${courses}" var="course">
								<div class="form-check">
									<input type="checkbox" class="form-check-input"
										id="course${course.id}" name="courses" value="${course.id}">
									<label class="form-check-label" for="course${course.id}">${course.name}</label>
								</div>
							</c:forEach>
						</div>
					</div>

					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="name" class="col-md-2 col-form-label">Photo</label>
						<div class="col-md-4">
							<form:input type="file" path="photo" accept="image/*"
								required="required"></form:input>
						</div>
					</div>


					<div class="row mb-4">
						<div class="col-md-4"></div>

						<div class="col-md-4">
							<button type="submit" class="btn btn-secondary col-md-2"
								data-bs-toggle="modal" data-bs-target="#exampleModal">
								Add</button>

						</div>
					</div>

				</form:form>

			</div>
		</div>
	</div>
	<div id="testfooter">
		<!-- 		<span>Copyright &#169; ACE Inspiration 2022</span> -->
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
		// Validate phone number
		function validatePhoneNumber() {
			var phoneNumber = document.getElementById('phone').value;
			var phonePattern = /^\d{11}$/; // Matches a 10-digit number
			if (!phonePattern.test(phoneNumber)) {
				alert('Please enter a valid 10-digit phone number.');
				return false;
			}
			return true;
		}
		// Validate form on submit
		document.querySelector('form').addEventListener('submit', function(e) {
			if (!validatePhoneNumber()) {
				e.preventDefault();
			}
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