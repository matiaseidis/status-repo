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
	
	window.setInterval(function() {
		drawNetworkState();
	}, 2000);
	
	drawPeers = function(data) {
		console.log(data);
		drawActive(data.strimers, "strimersBox");
		drawActive(data.pullers, "pullersBox");
		drawActive(data.pushers, "pushersBox");
		drawIddle(data.iddles, "iddlesBox");
		
	};
	
	drawIddle = function(iddles, idToDraw) {
		$( iddles ).each(function( index, obj ) {
			var message = obj.ip +" " + obj.port + " " + obj.clientId + " " + obj.clientState + " " + "bw: " + obj.bandWidth;
			$("#"+idToDraw + " .box").html(message);
		});
	};
	
	drawActive = function(actives, idToDraw) {
		console.log(actives);
		console.log(idToDraw);
		$( actives ).each(function( index, obj ) {
			  $.each(obj, function( peer, event ) {
				  var message = event.ip + ":" + event.port + " - " +  
				  event.planId + " " + 
				  event.clientId + " " + 
				  event.cachoDirection + " " + 
				  "date: " + event.date + " " + 
				  "bw: " + event.bandWidth + " " +
				  "progress: " + event.progress;
				  $("#"+idToDraw + " .box").html(peer + ": " + message);
				  console.log( peer + ": " + event );
			  });
		});
	};
	
	
	
});