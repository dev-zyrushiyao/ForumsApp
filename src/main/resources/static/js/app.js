// console.log("test")


var headerTitle = document.querySelector(".main-header-title");
var logoutBtn = document.getElementById("logoutForm");
var adminDashboardBtn = document.getElementById("adminForm");
var quoteCont = document.getElementById("rand-quote-display");
var quoteText = document.getElementById("text-quote");
var quoteAuth = document.getElementById("auth-quote");


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


// Generate random quote from Third Party API

var randInt = Math.random() * 1643
var generatedQuote;

fetch("https://type.fit/api/quotes")
  .then(function(response) {
    return response.json();
  })
  .then(function(data) {
    var quoteArr = data
    generatedQuote = data[Math.round(randInt) + 1]
    console.log(generatedQuote.text);
    console.log(generatedQuote.author);

    quoteText.innerText = generatedQuote.text;
    quoteAuth.innerText = generatedQuote.author;
  });


