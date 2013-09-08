$(function() {

	drawPlan = function() {
		$("#pullerBox").empty();
		$("#pushersBox").empty();
		$.ajax({
			dataType : "json",
			url : "/service/logger/plan",
			// data: data,
			success : function(data) {
				drawPuller(data.puller);
				drawPushers(data.pushers);
			}
		});
	};

	refreshPlan = function() {
		$.ajax({
			dataType : "json",
			url : "http://localhost:8080/service/logger/plan",
			// data: data,
			success : function(data) {
				refreshPuller(data.puller);
				refreshPushers(data.pushers);
			}
		});
	};
	
	window.setInterval(function() {
		refreshPlan();
	}, 2000);

	drawPuller = function(puller) {
		progress = $.progress(puller.progress, "pullerProgressBar",
				"progress-bar-success");
		$("#pullerBox").empty();
		$("#pullerBox").append(
				'<div id="pullerBoxInner">puller '
						+ puller.ip
						+ ":"
						+ puller.port
						+ $.progressLeyend("puller-" + puller.id,
								puller.progress) + progress + '</div>');

	};

	refreshPuller = function(puller) {
		p = puller.progress;
		$("span#puller-" + puller.id).html($.progressText(puller.progress));
		$('#pullerProgressBar .progress-bar').attr('aria-valuenow', p);
		$('#pullerProgressBar .progress-bar').attr('style',
				'width: ' + p + '%;');
		$('#pullerProgressBar .progress-bar span.sr-only').html(
				p + '% Complete');
	};

	drawPushers = function(pushers) {
		$("#pushersBox").empty();
		$.each(pushers, function(id, pusher) {
			drawPusher(pusher);
		});
	};

	refreshPushers = function(pushers) {
		$.each(pushers, function(id, pusher) {
			refreshPusher(pusher);
		});
	};

	refreshPusher = function(pusher) {
		p = pusher.progress;
		console.log(pusher.progress);
		console.log($('.progress#pusher-' + pusher.id + ' .progress-bar'));
		$("span.pusher-" + pusher.id).html($.progressText(pusher.progress));
		$('.progress#pusher-' + pusher.id + ' .progress-bar').attr('aria-valuenow', p);
		$('.progress#pusher-' + pusher.id + ' .progress-bar').attr('style', 'width: ' + p + '%;');
		$('.progress#pusher-' + pusher.id + ' .progress-bar span.sr-only').html(p + '% Complete');

	};

	drawPusher = function(pusher) {
		progress = $.progress(pusher.progress, "pusher-"+pusher.id);
		return $("#pushersBox").append(
				'<div id="pusher-'
						+ pusher.id
						+ '" class="pusherBox">pusher '
						+ pusher.ip
						+ ":"
						+ pusher.port
						+ $.progressLeyend("pusher-" + pusher.id,
								pusher.progress) + progress + '</div>');
	};

	$.progressLeyend = function(id, progress) {
		return '<span class="'+id+' bold">' + $.progressText(progress)
				+ '</span>';
	};

	$.progressText = function(progress) {
		return ' Progress: ' + progress + '%';
	};

	$.progress = function(p, id, extraClass) {
		return '<div class="progress" id="' + id
				+ '"><div class="progress-bar ' + extraClass
				+ '" role="progressbar" aria-valuenow="' + p
				+ '" aria-valuemin="0" aria-valuemax="100" style="width: ' + p
				+ '%;"><span class="sr-only">' + p
				+ '% Complete</span></div></div>';
	};
});