<<<<<<< HEAD
var clueless = angular.module('clueless', ['ngCookies', 'ng-websocket']
).controller('ctrlr', ['$scope', '$cookies', '$websocket', function($scope, $cookies, $websocket){
=======
var clueless = angular.module('clueless', ['ngCookies', 'angular-websocket']).controller('ctrlr', ['$scope', '$cookies', '$websocket', function($scope, $cookies, $websocket){
>>>>>>> ec7db8b1e2efd7b8e317dcf5c8fa3171eaaaa580
	
	$scope.test = "You have passed the test: "+$cookies.get("testCookie");
	
	$scope.isValid = function(){
		return true;
	}
	

	
	 // var ws = $websocket('ws://localhost:8080/Clue-Less/socket');
<<<<<<< HEAD
	
=======
>>>>>>> ec7db8b1e2efd7b8e317dcf5c8fa3171eaaaa580
	var ws = $websocket('ws://localhost:8080/Clue-Less/socket');
	  //Not sure how to hook this in
	  ws.onMessage(function(message) {
	    $scope.test = message;
	  });

	   ws.onError(function(event) {
	    console.log('connection Error', event);
	  });

	  ws.onClose(function(event) {
	    console.log('connection closed', event);
	  });

	 
	  
	  $scope.submit = function(input_text){
			ws.send(input_text);
			console.log("Sending Message");
		}

}]);
	
