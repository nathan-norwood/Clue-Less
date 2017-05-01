var clueless = angular
		.module('clueless', [ 'angular-websocket' ])
		.controller(
				'ctrlr',
				[
						'$scope',
						'$websocket',
						function($scope, $websocket) {

							
							/* Globals */
							$scope.selected_game = {
								id : undefined
							};
							$scope.selected_suspect = undefined;
							$scope.game_id = undefined;
							$scope.game_name = undefined;
							$scope.game_in_lobby = false;
							$scope.suspects_in_lobby = undefined;
							$scope.player_is_host = undefined;
							$scope.options = undefined;
							$scope.makingSuggestion = false;
							$scope.moveChosen = {
								id : undefined
							};
							$scope.disprove_choice = {
									id: undefined
							}
							$scope.move_response = undefined;
							$scope.suggestion_to_disrpove = undefined;
							$scope.msgs = "The game is started! "
							$scope.is_turn = false;
							
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
									$scope.game_in_lobby = false;
									$scope.current_suspect = $scope.suspects
									.filter(function(s) {
										return s.id == $scope.selected_suspect;
									}); 
									$scope.current_suspect = $scope.current_suspect[0];

								} else if (data.type == "SUSPECTS") {
									$scope.suspects = data.suspects;

								} else if (data.type == "AVAIL_SUSPECTS") {
									$scope.avail_suspects = data.suspects;

								} else if (data.type == "LOBBY") {
									$scope.game_id = data.gameId;
									$scope.game_name = data.gameName;
									$scope.suspects_in_lobby = data.suspects;
									if ($scope.player_is_host == undefined) {
										$scope.player_is_host = data.isHost;
									}
									$scope.game_in_lobby = true;

								} else if (data.type == "TURN") {
									$scope.options = data.options;
									$scope.is_turn = true;
								} else if (data.type == "TURN2") {
									if(data.suspect != null){
										var suspect = $scope.getSuspectById(data.suspect)
										$scope.move_response = data.notice + suspect.name;
									}else{
										if(data.notice != null){
											$scope.move_response = data.notice;
										}else{
											$scope.move_response = "Moved To Hallway";	
										}
										
									}

								} else if (data.type == "BOARD_STATE") {
									 $scope.board_state = data.board;
									
								}else if(data.type == "DISPROVE"){
									
									$scope.suggestion_to_disprove = {
											suspect: $scope.getSuspectById(data.suggestion.suspect),
											weapon: $scope.getWeaponById(data.suggestion.weapon),
											room: $scope.getRoomById(data.suggestion.room)
									}
									
									
									 $scope.ways_to_disprove = $scope.cards.filter(function(c){
										return c.id == $scope.suggestion_to_disprove.suspect.id ||
										c.id == $scope.suggestion_to_disprove.weapon.id||
										c.id == $scope.suggestion_to_disprove.room.id;;
									});
									
									
									
									
									
									
									
								} else if (data.type == "MSG") {
									
									var suspectName = $scope.suspects
									.filter(function(s) {
										return s.id == data.suspect;
									});  
									
									$scope.msgs = $scope.msgs + "\n"+
									suspectName[0].name +" "+ data.msg;

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
								type : "GET_SETUP"
							}
							ws.send(setUp);

							$scope.createGame = function(game_name, suspect) {
								var newGame = {
									type : "CREATE",
									name : game_name,
									suspect : suspect
								}
								$scope.selected_suspect = suspect;
								  
								ws.send(newGame);

							}
							$scope.getAvailableSuspects = function() {
								var request = {
									type : "GET_SUSPECTS",
									game : $scope.selected_game.id
								}
								ws.send(request);
							}

							$scope.joinGame = function(suspect) {
								var joinedGame = {
									type : "JOIN",
									game : $scope.selected_game.id,
									suspect : suspect
								}
								$scope.selected_suspect = suspect;
								ws.send(joinedGame)

							}

							$scope.startGame = function() {
								var start = {
									type : "START",
									game : $scope.game_id
								}
								ws.send(start);
							}

							$scope.getMove = function() {

								$scope.test = $scope.moveChosen.id;

								if ($scope.moveChosen.id == -1) {
									$scope.makingSuggetsion = true;
									// Need to get the current location of the
									// suspect somehow
									var stateObj = $scope.board_state.filter(function(s){
										return s.suspect != null && s.suspect == $scope.current_suspect.id;
									});
									$scope.test = stateObj.length;
									 $scope.chosenLocation = $scope.getRoomById(stateObj[0].r_id);
									 $scope.test = $scope.chosenLocation;
									 $scope.makingSuggestion = true;
								} else if ($scope.moveChosen.id == -2) {
									// Making Accusation Not ready to handle
									// that
									$scope.makeAccusation();
								} else {
									$scope.chosenLocation = $scope.options.locations
											.filter(function(l) {
												return l.id == $scope.moveChosen.id;
											});

									$scope.chosenLocation = $scope.chosenLocation[0];
									if ($scope.chosenLocation.room) {
										$scope.makingSuggestion = true;
									} else {
										// call submitMove() with hallway
	
										$scope.submitMove();
									}

								}
							}

							$scope.submitMove = function(suspect, weapon) {
								// send move back to server
								var turn = null;
								//Player in room at start of turn and makes suggestion
								if ($scope.moveChosen.id == -1 ) {
									turn = {
										type : "TURN",
										selection : {
											suggestion :{
												suspect: suspect,
												weapon: weapon,
												room: $scope.chosenLocation.id,
											},
											game : $scope.game_id
										}
									}
								
								}else{
									//Player Moved to Room and Made Suggestion
									if($scope.chosenLocation.room){
										turn = {
												type : "TURN",
												selection : {			
													location: $scope.chosenLocation.id,
													suggestion :{
														suspect: suspect,
														weapon: weapon,
														room: $scope.chosenLocation.id,
													}
												},
												game : $scope.game_id
											}	
										
										$scope.move_response = "Waiting for other players to disprove";
									//Player Moved to Hallway
									} else {
										turn = {
											type : "TURN",
											selection : {
												location : $scope.chosenLocation.id,
											},
											game : $scope.game_id
										}
									}
									
								}
							$scope.makingSuggestion = false;
							$scope.options = undefined;
							
							ws.send(turn);	
						}
							
						$scope.sendDisprove = function(){
							$scope.test = $scope.disprove_choice.id;
							var disprove = {
									type: "DISPROVE",
									game_id: $scope.game_id,
									card: $scope.disprove_choice.id
							}
							$scope.suggestion_to_disprove = undefined;
							ws.send(disprove);
						}
						
						$scope.endTurn = function(){
							var end = {
									type :"ACCUSE",
									game_id: $scope.game_id,
									accusation: []
							}
							$scope.is_turn = false;
							ws.send(end);
						}
						
						$scope.makeAccusation = function(){
							//TODO
							
						}
						$scope.getSuspectById = function(id){
							var suspect = $scope.suspects
							.filter(function(s) {
								return s.id == id;
							}); 
							
							return suspect[0];
						}
						
						$scope.getWeaponById = function (id){
						var weapon = $scope.weapons
						.filter(function(w) {
							return w.id == id
						}); 
						return weapon[0];
						
						}	
						
						$scope.getRoomById = function(id){
							var room = $scope.rooms
							.filter(function(r) {
								return r.id == id;
							}); 
							
							return room[0];
						}
						
	} ]);
