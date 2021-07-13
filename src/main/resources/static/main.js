var app = angular.module("SMSManagement", []);

// Controller Part
app.controller("SMSController", function($scope, $http) {

	$scope.msgs = [];
    $scope.smsForm = {
        phone: ""
    };

    function _success(res) {
        // _clearFormData();
   		console.log("Response: " + res.data);
    }
 
    function _error(res) {
        var data = res.data;
        var status = res.status;
        var header = res.header;
        var config = res.config;
        alert("Error: " + status + ":" + data);
    }

	// Clear the form
    function _clearFormData() {
        $scope.smsForm.phone = "";
    };

    // HTTP POST/PUT methods
    // Call: http://localhost:8080/msg
    $scope.submitSMS = function() {
		const phoneSaved = document.querySelector("#phoneSaved").className;
		const phoneInput = $scope.smsForm.phone;

		console.log(`Phone: ${ phoneSaved ? phoneSaved : null }`); // Delete
 
		if (phoneSaved) {
			var method = "";
	        var url = "";
	 		
			$scope.smsForm.phone = phoneSaved;
	        if ($scope.smsForm.phone) {
	            method = "POST";
	            url = '/msg';
	        } else {
	            console.log("Error");
	        }
	 
	        $http({
	            method: method,
	            url: url,
	            data: angular.toJson($scope.smsForm),
	            headers: {
	                'Content-Type': 'application/json'
	            }
	        }).then(_success, _error);

			$scope.smsForm.phone = phoneInput;
		} else {
			alert("invalid phone.");
		}
    };

    // HTTP GET- get data from sms
    // Call: http://localhost:8080/msg/{phone}/{code}
	$scope.validCode = function() {
		const phoneSaved = document.querySelector("#phoneSaved").className;
		const code = document.getElementById("code").value;
		
		console.log(`Phone: ${ phoneSaved ? phoneSaved : null }, Code: ${code}`); // Delete
		
		if (phoneSaved) {
			$http({
	            method: 'GET',
	            url: '/msg/' + phoneSaved + "/" + code
	        }).then(
	            function(res) { // success
	                $scope.employees = res.data;
					if(res.data.phone != phoneSaved) {
						alert(res.data.body);
					} else {
						var URLactual = window.location;
						window.location.href = URLactual + "hello";
					}
	            },
	            function(res) { // error
	                console.log("Error: " + res.status + " : " + res.data);
	            }
	        );
		} else {
			alert("There is no phone.");
		}
	};
});