
  $("#signin_form").submit(function(event) {
    var params = {
        "email"      : $('#email'),
        "password"   : $('#Password')
    };  
    console.log("params inside signin");
    console.log(params);
    event.preventDefault();
    $.ajax({
        type        : "POST",
        url         : "SignIn", // Location of the service
        data        : JSON.stringify(params), //Data sent to server
        contentType : "application/json", // content type sent to server
        crossDomain : true,
        async       : false,
        success: function(data, success) {
            console.log("sign in successfully");
            console.log(data);
        },
        error : function (jqXHR, textStatus, errorThrown) {
            console.log("error in sign in");
        }
    });   
     
   });
  





