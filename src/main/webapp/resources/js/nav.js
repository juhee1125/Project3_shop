function menuopen() {
	var menuOverlay = document.getElementById("menu-overlay");
    if (menuOverlay.style.width === "100%") {
        menuOverlay.style.width = "0";
    } else {
        menuOverlay.style.width = "100%";
    }
}
function toggleMenu() {
	var menuOverlay = document.getElementById("menu-overlay");
    if (menuOverlay.style.width === "100%") {
        menuOverlay.style.width = "0";
    } else {
        menuOverlay.style.width = "100%";
    }
}