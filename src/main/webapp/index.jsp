<html>
<head>
<script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/networkStatusDrawer.js"></script>
<!-- <link rel="stylesheet" type="text/css" href="css/style.css" media="all" /> -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css" media="all" />
</head>
<body>
<h2>Plan: demo</h2>
	<div id="outerBox">
		<div id="iddlesBox"><h3>iddle</h3><div class="box"></div></div>
		<div id="pullersBox"><h3>pulling</h3><div class="box"></div></div>
		<div id="pushersBox"><h3>pushing</h3><div class="box"></div></div>
		<div id="strimersBox"><h3>striming</h3><div class="box"></div></div>
	</div>

	<script type="text/javascript">
		$(function() {
			drawNetworkState();		
		});
	</script>
</body>
</html>
