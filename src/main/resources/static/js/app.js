// console.log("test")

var headerTitle = document.querySelector(".main-header-title");
var logoutBtn = document.getElementById("logoutForm");
var adminDashboardBtn = document.getElementById("adminForm");
// var commentContainer = document.querySelectorAll(".thread-comments")

var homePage = "http://localhost:8080/"

// Logout button in collapsible header - added condition to avoid error when in logged-out state

if(logoutBtn != null)
{
    logoutBtn.addEventListener("click", function(){
        logoutBtn.submit()
    })
}

if(adminDashboardBtn != null)
{
    adminDashboardBtn.addEventListener("click", function(){
        adminDashboardBtn.submit()
    })
}


// Clicking the Header will redirect to Main Page "/"
headerTitle.addEventListener("click", function(){
    // console.log("Hello")
    window.location.href = homePage;
})


// commentContainer.addEventListener("mouseover", function(){
//     document.querySelector(".comment-admin-btns").style.display = "initial";
    
// })

