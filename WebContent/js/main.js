angular.module('tm-jug-ask', []);
angular.module('tm-jug-ask').controller('Main', [ '$scope', 'AskService', function($scope, AskService) {
	$scope.messages = AskService.messages;
	$scope.input = {
			message :''
	};
	$scope.sendMessage = function() {
		AskService.sendMessage($scope.input.message);
	};
} ]);

angular.module('tm-jug-ask').factory('AskService', ['$rootScope', function($rootScope) {
	var chatter = {
		messages : []
	};
	var wsUri = "ws://localhost:8081/TmJugChatter/ask";
	var websocket = new WebSocket(wsUri);
	
	websocket.onopen = function(evt) {
		$rootScope.$apply(function() {
			chatter.messages.push(evt.data);
		});
	};

	websocket.onmessage = function(evt) {
		$rootScope.$apply(function() {
			chatter.messages.push(evt.data);
		});
	};

	websocket.onclose = function(evt) {
		$rootScope.$apply(function() {
			chatter.messages.push("Disconnected.");
		});
	};

	return {
		messages : chatter.messages,
		sendMessage : function(message) {
			websocket.send(message);
		}
	};
} ]);