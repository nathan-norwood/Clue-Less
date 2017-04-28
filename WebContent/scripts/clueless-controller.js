//var clueless = angular.module('clueless', [ 'angular-websocket', 'ui.bootstrap']).controller(
//		'ctrlr', [ '$scope', '$websocket','$modal', function($scope, $websocket, $modal) {
var clueless = angular.module('clueless', [ 'angular-websocket' ]).controller(
		'ctrlr', [ '$scope', '$websocket', function($scope, $websocket) {

			
			$scope.test = "Response from server: ";
			/* Globals*/
			$scope.selected_game = {id : undefined};
			$scope.selected_suspect = {id: undefined};
			$scope.game_id = undefined;
			$scope.game_in_lobby = false;
			
			/* Define WebSocket for Communication with Game */
			var ws = $websocket('ws://localhost:8080/Clue-Less/socket');
			ws.onMessage(function(event) {
				$scope.test += event.data;

				var data = JSON.parse(event.data);
				if (data.type == "SETUP") {
					$scope.games = data.games;
					$scope.suspects = data.suspects;
					$scope.weapsons = data.weapons;
					$scope.rooms = data.rooms;

				} else if (data.type == "CARDS") {
					$scope.cards = data.cards;

				} else if (data.type == "SUSPECTS") {
					$scope.suspects = data.suspects;

				} else if(data.type == "AVAIL_SUSPECTS"){
					$scope.avail_suspects = data.suspects;
					
				} else if (data.type == "TURN") {
					$scope.options = data.options;

				} else if (data.type == "BOARD_STATE") {
					// $scope.board_state = data.

				} else if (data.type == "MSG") {
					$scope.msgs = $scope.msgs + "<br />" + data.msg;

				} else {

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

		
			/* On load, query for available games */
			var setUp = {
					type: "GET_SETUP"
			}
			ws.send(setUp);

			$scope.createGame = function(game_name, suspect) {
				var newGame = {
						type : "CREATE",
						name : game_name,
						suspect: suspect
				}
				ws.send(newGame);
				//Will eventually receive game ID from GameManager, just want to test
				//going to lobby
				$scope.game_id  = 0;
				$scope.game_in_lobby = true;
			}
			$scope.getAvailableSuspects = function(){
				var request = {
						type: "GET_SUSPECTS",
						game: $scope.selected_game.id
				}
				ws.send(request);
			}
			
			$scope.joinGame = function(suspect){
				var joinedGame = {
						type: "JOIN",
						game: $scope.selected_game.id,
						suspect: suspect
				}
				ws.send(joinedGame)
				//Will eventually receive game ID from GameManager, just want to test
				//going to lobby
				$scope.game_id  = 0;
				$scope.game_in_lobby = true;
				
			}
			

		} ]);
