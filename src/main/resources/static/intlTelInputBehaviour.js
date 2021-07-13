const codeField = document.querySelector("#codeField");
window.onload = function(){
	codeField.style.display = 'none';
}

const phoneInputField = document.querySelector("#phone");
const codeInputField = document.querySelector("#code");
const alertPhoneError = document.querySelector("#alert-phone-error");
const phoneInput = window.intlTelInput(phoneInputField, {
  utilsScript:
    "https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/utils.js",
});

const info = document.querySelector(".alert-info");
const error = document.querySelector(".alert-error");

const alertReset = function() {
	phoneInputField.classList.remove("error");
	alertPhoneError.innerHTML = "";
	alertPhoneError.classList.add("hide");
};

function validNumber() {
  alertReset();
  var phoneNumber = phoneInput.getNumber();
  var errorMap = ["Invalid number", "Invalid country code", "Too short", "Too long"];
  console.log("1: " + phoneNumber); // Delete
  
  if(phoneInputField.value.trim()) {
	  if(phoneInput.isValidNumber()) {
		  codeField.style.display = 'block';
		  console.log(phoneNumber); // Delete
	  } else {
		  phoneInputField.classList.add("error");
		  var errorCode = phoneInput.getValidationError();
		  console.log("errorCode: "+errorCode); // Delete
		  console.log("errorMap: "+errorMap[errorCode]); // Delete
		  alertPhoneError.innerHTML = errorMap[errorCode];
		  alertPhoneError.classList.remove("hide");
	  }
  }
  
  phoneInputField.addEventListener('change', alertReset);
  phoneInputField.addEventListener('keyup', alertReset);
}

// No more than 6 digits
codeInputField.addEventListener('input',function(){
  if (this.value.length > 6) 
     this.value = this.value.slice(0,6); 
})

var helloAjaxApp = angular.module("myApp", []);
 
helloAjaxApp.controller("myCtrl", [ '$scope', '$http', function($scope, $http) {
 
    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
     
    $scope.sendPost = function() {
        $http({
            url : '/formServlet',
            method : "POST",
            data : {
                'name' : $scope.name
            }
        }).then(function(response) {
            console.log(response.data);
            $scope.message = response.data;
        }, function(response) {
            //fail case
            console.log(response);
            $scope.message = response;
        });
 
    };
} ]);