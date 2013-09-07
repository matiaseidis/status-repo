$(function(){
	
	drawPlan = function(){
		$("#pullerBox").empty();
		$("#pushersBox").empty();
		$.ajax({
			dataType : "json",
			url : "http://localhost:8080/service/logger/plan",
			//data: data,
			success : function(data) {
				drawPuller(data.puller);
				drawPushers(data.pushers);
			}
		});
	};
	
	refreshPlan = function(){
		$.ajax({
			dataType : "json",
			url : "http://localhost:8080/service/logger/plan",
			//data: data,
			success : function(data) {
				refreshPuller(data.puller);
				refreshPushers(data.pushers);
			}
		});
	};
	
	drawPuller = function(puller) {
		progress = $.progress(puller.progress, "pullerProgressBar", "progress-bar-success");
		$("#pullerBox").fadeOut('fast', function() {
			$("#pullerBox").empty();
			$("#pullerBox").append('<div id="pullerBoxInner">puller '+puller.ip+":"+puller.port+$.progressLeyend("puller-"+puller.id, puller.progress) +progress+'</div>');
			$("#pullerBox").fadeIn('fast');
		});
		
	};
	
	refreshPuller = function(puller) {
		p = puller.progress;
		$("span#puller-"+puller.id).html($.progressText(puller.progress));
		$('#pullerProgressBar .progress-bar').attr('aria-valuenow',p);
		$('#pullerProgressBar .progress-bar').attr('style','width: '+p+'%;');
		$('#pullerProgressBar .progress-bar span.sr-only').html(p+'% Complete');
	};
	
	drawPushers = function(pushers) {
		$("#pushersBox").empty();
		$.each(pushers,function(id, pusher) {
			  console.log('Indice es ' + id + ' y valor es: ' + pusher);
			  $("#pushersBox").fadeOut('fast', function(){
				  drawPusher(pusher);
				  $("#pushersBox").fadeIn('fast'); 
			  });
			});
	};
	
	refreshPushers = function(pushers) {
		$.each(pushers,function(id, pusher) {
		    refreshPusher(pusher);
		});
	};
	
	refreshPusher = function(pusher) {
		$("span#pusher-"+pusher.id).html($.progressText(pusher.progress));
		$('#pusher-'+pusher.id+' #pusher .progress-bar').attr('aria-valuenow',p);
		$('#pusher-'+pusher.id+' #pusher .progress-bar').attr('style','width: '+p+'%;');
		$('#pusher-'+pusher.id+' #pusher .progress-bar').attr('aria-valuenow',p);
		$('#pusher-'+pusher.id+' #pusher .progress-bar span.sr-only').html(p+'% Complete');
		
	};
	
	drawPusher = function(pusher) {
		progress = $.progress(pusher.progress, "pusher");
		return 	$("#pushersBox").append('<div id="pusher-'+pusher.id+'" class="pusherBox">pusher '+pusher.ip+":"+pusher.port+ $.progressLeyend("pusher-"+pusher.id, pusher.progress) +progress+'</div>');
	};
	
	$.progressLeyend = function(id, progress){
		return '<span id="'+id+'" class="bold">'+$.progressText(progress)+'</span>';
	};
	
	$.progressText = function(progress){
		return ' Progress: '+progress+'%';
	}
	
	$.progress = function(p, id, extraClass){
		return '<div class="progress" id="'+id+'"><div class="progress-bar '+extraClass+'" role="progressbar" aria-valuenow="'+p+'" aria-valuemin="0" aria-valuemax="100" style="width: '+p+'%;"><span class="sr-only">'+p+'% Complete</span></div></div>';
	};
	
	window.setInterval(function(){
	  refreshPlan();
	}, 2000);
});