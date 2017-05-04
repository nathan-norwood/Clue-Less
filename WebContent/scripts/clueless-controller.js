var clueless = angular
		.module('clueless', [ 'angular-websocket' ])
		.controller(
				'ctrlr',
				[
						'$scope',
						'$websocket',
						'$window',
						function($scope, $websocket, $window) {

							
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
							$scope.making_suggestion = false;
							$scope.move_chosen = {
								id : undefined
							};
							$scope.disprove_choice = {
									id: undefined
							}
							$scope.move_response = undefined;
							$scope.suggestion_to_disrpove = undefined;
							$scope.msgs = ["The game is started! "];
							$scope.is_turn = false;
							$scope.making_accusation = false;
							
							$scope.pos = [
								{id:6, x:45, y:45, gcomp:[27]},
								{id:6, x:45, y:45, gcomp:[27]},
								{id:7, x:300, y:45, gcomp:[27]},
								{id:8, x:580, y:45, gcomp:[27]},
								{id:9, x:45, y:300, gcomp:[27]},
								{id:10, x:300, y:300, gcomp:[27]},
								{id:11, x:500, y:300, gcomp:[27]},
								{id:12, x:45, y:580, gcomp:[27]},
								{id:13, x:300, y:580, gcomp:[27]},
								{id:14, x:580, y:580, gcomp:[27]},
								
								{id:15, x:215, y:75, gcomp:[27]},
								{id:16, x:485, y:75, gcomp:[27]},
								{id:17, x:85, y:200, gcomp:[27]},
								{id:18, x:345, y:200, gcomp:[27]},
								{id:19, x:615, y:200, gcomp:[27]},
								{id:20, x:215, y:430, gcomp:[27]},
								{id:21, x:485, y:430, gcomp:[27]},
								{id:22, x:85, y:470, gcomp:[27]},
								{id:23, x:345, y:470, gcomp:[27]},
								{id:24, x:615, y:470, gcomp:[27]},
								{id:25, x:215, y:600, gcomp:[27]},
								{id:26, x:485, y:600, gcomp:[27]},
								];
							
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
									canvas.width = 600;
								    canvas.height = 600;
								    context.globalAlpha = 1.0;
								    context.beginPath();
								    draw(); 
								    
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
									//setting chosen location to current location of suspect 
									//if they can Make Suggestion without moving
									var stateObj = $scope.board_state.filter(function(s){
										return s.id == $scope.current_suspect.id;
									});
									
									$scope.chosen_location = $scope.getRoomById(stateObj[0].r_id);
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
									if(suspectName.length>0){
										$scope.msgs.push(suspectName[0].name +" "+ data.msg);
									}else{
										$scope.msgs.push(data.msg);
									}

								} else if (data.type = "ENDGAME"){
									var name = $scope.getSuspectById(data.suspect).name
									$scope.end_game =  name +" "+ data.msg;
									$scope.game_id = undefined
									
									//

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

								$scope.test = $scope.move_chosen.id;

								if ($scope.move_chosen.id == -1) {
									$scope.makingSuggetsion = true;
									// Need to get the current location of the
									// suspect somehow
									var stateObj = $scope.board_state.filter(function(s){
										return s.id == $scope.current_suspect.id;
									});
									$scope.test = stateObj.length;
									 $scope.chosen_location = $scope.getRoomById(stateObj[0].r_id);
									
									 $scope.making_suggestion = true;
								} else if ($scope.move_chosen.id == -2) {
									// Making Accusation Not ready to handle
									// that
									$scope.makeAccusation();
								} else {
									$scope.chosen_location = $scope.options.locations
											.filter(function(l) {
												return l.id == $scope.move_chosen.id;
											});

									$scope.chosen_location = $scope.chosen_location[0];
									if ($scope.chosen_location.room) {
										$scope.making_suggestion = true;
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
								if ($scope.move_chosen.id == -1 ) {
									turn = {
										type : "TURN",
										selection : {
											suggestion :{
												suspect: suspect,
												weapon: weapon,
												room: $scope.chosen_location.id,
											}
											
										},
									game : $scope.game_id
									}
									$scope.move_response = "Waiting for other players to disprove";
								}else{
									//Player Moved to Room and Made Suggestion
									if($scope.chosen_location.room){
										turn = {
												type : "TURN",
												selection : {			
													location: $scope.chosen_location.id,
													suggestion :{
														suspect: suspect,
														weapon: weapon,
														room: $scope.chosen_location.id,
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
												location : $scope.chosen_location.id,
											},
											game : $scope.game_id
										}
									}
									
								}
							$scope.making_suggestion = false;
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
							$scope.move_response = undefined;
							ws.send(end);
						}
						
						$scope.makeAccusation = function(){
							$scope.making_accusation = true;
							
						}
						$scope.submitAccusation = function (suspect, weapon, room){
							var acc = {
									type : "ACCUSE",
									game_id: $scope.game_id,
									accusation: [suspect, weapon, room]		
							}
							
							$scope.test = acc;
							$scope.making_accusation = false;
							$scope.is_turn = false;
							ws.send(acc);
						}
						$scope.getSuspectById = function(id){
							var suspect = $scope.suspects
							.filter(function(s) {
								return s.id == id;
							}); 
							if(suspect != undefined){
							return suspect[0];
							}else{
								return undefined;
							}
							
						}
						
						$scope.getWeaponById = function (id){
						var weapon = $scope.weapons
						.filter(function(w) {
							return w.id == id
						}); 
						if(weapon != undefined){
						return weapon[0];
						}else{
							return undefined;
						}
						
						}	
						
						$scope.getRoomById = function(id){
							var room = $scope.rooms
							.filter(function(r) {
								return r.id == id;
							}); 
							if(room != undefined){
							return room[0];
							}else{
								return undefined;
							}
						}
						
					
						
						
						var canvas = document.getElementById('canvas');
					    var context = canvas.getContext('2d');
					    
					    $scope.img1= new Image();
					    $scope.img1.src="images/newboard.png"
					   
					    $scope.img1.onload = function(){
					    	context.drawImage($scope.img1,1,1,600,600);
					    }
					    
					    
					    function draw() {
					    	context.drawImage($scope.img1,1,1,600,600);
					        for(var i=0; i<$scope.pos.length; i++) {
					        	for(var j=0; j<$scope.pos[i].gcomp.length;j++){
					        		var comp = $scope.getSuspectById($scope.pos[i].gcomp[j]);
					        	
					        		if( comp == undefined){
					        			comp = $scope.getWeaponById($scope.pos[i].gcomp[j]);
					        		}
					        		if( comp != undefined){
					        			var comp_img =new Image();
					        			comp_img.src = "images/icons/"+comp.img;
						        		context.drawImage(comp_img,
						        				$scope.pos[i].x,
						        				$scope.pos[i].y,
						        				30,
						        				30);
					        		}
					        		
					        	}
					        }
					    }
					     
					    
					    
	} ]).filter('reverse', function() {
		  return function(items) {
			    return items.slice().reverse();
			  };
			});;
