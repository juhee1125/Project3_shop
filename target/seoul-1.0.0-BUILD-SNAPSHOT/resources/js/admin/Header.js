$(document).ready(function() {
	$('.nameclick').click(function() {
		console.log("js로 넘어옴")
		var logoutButton = document.getElementById("logout");

		if (logoutButton.style.display === "none") {
            logoutButton.style.display = "block";
        } else {
            logoutButton.style.display = "none";
        }
	})
});