// console.log("test")

var headerTitle = document.querySelector(".main-header-title");
var logoutBtn = document.getElementById("logoutForm");

var homePage = "http://localhost:8080/"

// Logout button in collapsible header - added condition to avoid error when in logged-out state

if(logoutBtn != null)
{
    logoutBtn.addEventListener("click", function(){
        logoutBtn.submit()
    })
}


// Clicking the Header will redirect to Main Page "/"
headerTitle.addEventListener("click", function(){
    // console.log("Hello")
    window.location.href = homePage;
})