//var clueless = angular.module('clueless', [ 'angular-websocket', 'ui.bootstrap']).controller(
//		'ctrlr', [ '$scope', '$websocket','$modal', function($scope, $websocket, $modal) {
var clueless = angular.module('clueless', [ 'angular-websocket' ]).controller(
		'ctrlr', [ '$scope', '$websocket', function($scope, $websocket ) {

		/* TODO Delete these once we are up and running */
		$scope.test = "Response from server: ";

		$scope.isValid = function() {
			return true;
		}
		/*End Delete section*/
		
		
		/*Define WebSocket for Communication with Game*/
		var ws = $websocket('ws://localhost:8080/Clue-Less/socket');
		ws.onMessage(function(event) {
			$scope.test += event.data;
			
			var data = JSON.parse(event.data);
			if(data.type == "SETUP"){
				$scope.games = data.games;
				$scope.suspects = data.suspects;
				$scope.weapsons = data.weapons;
				$scope.rooms = data.rooms;
				
			}else if(data.type == "CARDS"){
				$scope.cards = data.cards;
				
			}else if(data.type == "SUSPECTS"){
				$scope.suspects = data.suspects;
				
			}else if(data.type == "TURN"){
				$scope.options = data.options;
				
			}else if(data.type == "BOARD_STATE"){
				//$scope.board_state = data.
				
			}else if(data.type == "MSG"){
				$scope.msgs = $scope.msgs + "<br />" + data.msg;
				
			}else{
				
			}
			
			
		});

		ws.onError(function(event) {
			console.log('connection Error', event);
		});

		ws.onClose(function(event) {
			console.log('connection closed', event);
		});

		$scope.submit = function(input_text) {
			ws.send(input_text);
			console.log("Sending Message");
		}
		
		
		$scope.game_id = undefined;
		/* On load, query for available games */
		ws.send("{\"type\":\"GET_SETUP\"}");
		
		$scope.createGame = function(){
		$scope.test = "Button Clicked";
//			 var modalInstance = $modal.open({
//	                templateUrl: 'modal-form.html',
//	                controller: ModalInstanceCtrl,
//	                scope: $scope,
//	                resolve: {
//	                    userForm: function () {
//	                        return $scope.userForm;
//	                    }
//	                }
//	         });
			
			
		}
		
		
//		var ModalInstanceCtrl = function ($scope, $modalInstance, userForm) {
//		    $scope.form = {}
//		    $scope.submitForm = function () {
//		        if ($scope.form.userForm.$valid) {
//		            console.log('user form is in scope');
//		            $modalInstance.close('closed');
//		        } else {
//		            console.log('userform is not in scope');
//		        }
//		    };
//
//		    $scope.cancel = function () {
//		        $modalInstance.dismiss('cancel');
//		    };
//		};
		

	} ]);
