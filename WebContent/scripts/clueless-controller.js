
var clueless = angular.module('clueless', ['angular-websocket']).controller('ctrlr', ['$scope', '$websocket', function($scope,  $websocket){
	
	$scope.test = "Response from server: ";
	
	$scope.isValid = function(){
		return true;
	}
	
	
	 // var ws = $websocket('ws://localhost:8080/Clue-Less/socket');
	var ws = $websocket('ws://localhost:8080/Clue-Less/socket');
	  //Not sure how to hook this in
	  ws.onMessage(function(event) {
	    $scope.test += event.data;
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
	
