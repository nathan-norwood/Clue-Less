var clueless = angular.module('clueless', ['ngCookies']).controller('ctrlr', ['$scope', '$cookies', function($scope, $cookies){
	
	$scope.test = "You have passed the test: "+$cookies.get("testCookie");
	
	$scope.isValid = function(){
		return true;
	}	
	
}]);