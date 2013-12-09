$(function() {
	drawNetworkState = function() {
		$.ajax({
			dataType : "json",
			url : "http://localhost:8081/service/logger/networkState",
			success : function(data) {
				drawPeers(data);
			}
		});
	};	
	
	drawPeers = function(data) {
		console.log(data);
	};
	
});