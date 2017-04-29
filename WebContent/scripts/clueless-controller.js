var clueless = angular.module('clueless', [ 'angular-websocket' ]).controller(
		'ctrlr', [ '$scope', '$websocket', function($scope, $websocket) {

			
			$scope.test = "Response from server: ";
			/* Globals*/
			$scope.selected_game = {id : undefined};
			$scope.selected_suspect = undefined;
			$scope.game_id = undefined;
			$scope.game_name = undefined;
			$scope.game_in_lobby = false;
			$scope.suspects_in_lobby =  undefined;
			$scope.player_is_host = undefined;
			$scope.options = undefined;
			$scope.makingSuggestion = false;
			
			
			/* Define WebSocket for Communication with Game */
			var ws = $websocket('ws://localhost:8080/Clue-Less/socket');
			ws.onMessage(function(event) {
				$scope.test = event.data;

				var data = JSON.parse(event.data);
				if (data.type == "SETUP") {
					$scope.games = data.games;
					$scope.suspects = data.suspects;
					$scope.weapons = data.weapons;
					$scope.rooms = data.rooms;

				} else if (data.type == "CARDS") {
					$scope.cards = data.cards;
					$scope.game_in_lobby = false;;

				} else if (data.type == "SUSPECTS") {
					$scope.suspects = data.suspects;

				} else if(data.type == "AVAIL_SUSPECTS"){
					$scope.avail_suspects = data.suspects;
					
				}else if (data.type == "LOBBY") {
					$scope.game_id = data.gameId;
					$scope.game_name = data.gameName;
					$scope.suspects_in_lobby = data.suspects;
					if($scope.player_is_host == undefined){
						$scope.player_is_host = data.isHost;
					}
					$scope.game_in_lobby = true;

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
				$scope.selected_suspect = suspect;
				ws.send(newGame);

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
				$scope.selected_suspect = suspect;
				ws.send(joinedGame)
				
			}
			
			$scope.startGame = function(){
				var start = {
						type: "START",
						game: $scope.game_id
				}
				ws.send(start);
			}
			
			$scope.getMove = function(locationId){
				if(locationId = undefined){
					$scope.test = "WTF";
				}else{
					$scope.test = locationId;
				}
				if(locationId == -1){
					$scope.makingSuggetsion = true;
				}else if(locationId == -2){
					//Making Accusation Not ready to handle that
				}else{
				
					for (l in $scope.options.locations){
						if(l.id = locationId){
							if(l.room){
								$scope.makingSuggestion;
							}else{
								//call submitMove with location
							}
						}
					}
					
				}	
			}
			
			$scope.submitMove = function(location, suggestion){
				//send move back to server
			}
				
		} ]);
