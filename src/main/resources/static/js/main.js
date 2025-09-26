function startTime() {
	var today = new Date();
	
	var month = convertMonth(today.getMonth());
	var year = today.getFullYear();
	var date = twoDigits(today.getDate());
	var day = convertDay(today.getDay());
	document.getElementById('date').innerHTML = day + " - " + month + " " + date + ", " + year;
	
	var m = twoDigits(today.getMinutes());
	document.getElementById('time').innerHTML = twoDigits(hours12(today)) + ":" + m;
	
	var t = setTimeout(startTime, 1000);
}

function convertMonth(month){
	var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
	return monthNames[month];
}

function twoDigits(i) {
	if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
	return i;
}

function convertDay(day){
	var dayNames =  ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];
	return dayNames[day];
}

function hours12(date) { 
	return (date.getHours() + 24) % 12 || 12; 
}