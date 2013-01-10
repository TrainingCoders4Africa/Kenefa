// The root URL for the RESTful services
var root= "http://localhost:8080/kenefa/rest";
var rootURLFacility = root+"/facilities";

var currentFacility;

// Retrieve facility list when application starts 
findAll();

//Register listeners
$('#btnSearch').click(function() {
	country=$('#searchCountry').val().split('.')[1];
	city=$('#searchCity').val();
	search($('#searchKey').val(), country, city); 
	return false;
});

// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function(e){
	if(e.which == 13) {
		country=$('#searchCountry').val().split('.')[1];
		city=$('#searchCity').val();
		search($('#searchKey').val(), country, city); 
		e.preventDefault();
		return false;
    }
});

//Register listeners
$('#searchCity').change(function() {
	console.log('change city :'+$('#searchCity').val()+' '+ $(this).val());
	if ($('#searchCity').val() != ''){
		var key = $('#searchCountry').val().split('.')[1];
		findByCountryCity(key, $('#searchCity').val());
	}else findAll();
	return false;
});

/*$('#btnSearchByCountryCity').click(function() {
	if ($('#searchCountry').val() == '' || $('#searchCity').val() == '')
		alert('You have to enter the country and the city');
		//findAll();
	else 
		findByCountryCity($('#searchCountry').val(), $('#searchCity').val());
	return false;
});*/

$('#facilityList a').live('click', function() {
	findById($(this).data('identity'));
});

function search(searchKey, country, city) {
	if (searchKey == '' && city != ''){
		findByCountryCity(country,city);
	}
	if (searchKey != '' && city != ''){
		findByName(searchKey, country, city);
	} 
}

function findByName(searchKey, country, city) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type: 'GET',
		url: rootURLFacility + '/search?country=' + country + '&city=' + city + '&name=' + searchKey,
		dataType: "json",
		success: renderList 
	});
}

function findByCountryCity(country,city) {
	console.log('findByCountryCity: ' + country + ' and ' + city);
	$.ajax({
		type: 'GET',
		url: rootURLFacility + '/search?country=' + country + '&city=' + city,
		dataType: "json",
		success: renderList 
	});
}

function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURLFacility + '/' + id,
		dataType: "json",
		success: function(facility){
			//$.each(data, function(index, facility) {
			console.log('findById data: ' + facility);
			currentFacility = facility;
			renderDetails(currentFacility);
			//});
		}
	});
}

function renderDetails(facility) {
	$('#id').val(facility.id);
	$('#name').val(facility.name);
	$('#city').val(facility.city);
	$('#country').val(facility.country);
	$('#address').val(facility.address);
	$('#type').val(facility.type);
	$('#img').attr('src', 'images/default.jpg');
	$('#scope').val(facility.scope);
	$('#longitude').val(facility.longitude);
	$('#latitude').val(facility.latitude);
	
	/*$.ajax({
		type: 'GET',
		url: rootURLFacility+'/' + facility.id + '/service',
		dataType: "json", // data type of response
		success: parseService
	});*/
	parseService(facility.service);
	
}

function parseService(service) {
	console.log('data-service :'+service);
	//$.each(data, function(index, service) {
		console.log('service ='+ service.pediatric + ' radiology =' + service.radiology+ ' pharmacy ='+service.pharmacy);
		$('#service_pediatric').attr('checked',false);
		$('#service_radiology').attr('checked',false);
		$('#service_pharmacy').attr('checked',false);
		if(service.pediatric==true)$('#service_pediatric').attr('checked','checked');
		if(service.radiology==true)$('#service_radiology').attr('checked','checked');
		if(service.pharmacy==true)$('#service_pharmacy').attr('checked','checked');
	//});
}

function findAll() {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURLFacility,
		dataType: "json", // data type of response
		success: renderList
	});
}


function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [data]);
	$('#facilityList li').remove();
	console.log(data);
	$.each(list, function(index, facility) {
		//$.each(facilities, function(index, facility) {
		console.log('data: '+facility);
		$('#facilityList').append('<li><a href="#" data-identity="' + facility.id + '">'+facility.name+'</a></li>');
		//});
	});
}

