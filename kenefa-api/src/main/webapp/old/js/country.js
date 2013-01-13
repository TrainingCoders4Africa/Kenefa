// The root URL for the RESTful services
var root= "http://localhost:8080/kenefa/rest";
var rootURLCountry = root+"/countries";

var currentCountry;

// Retrieve country list when application starts 
findAllCountry();

//Register listeners
$('#searchCountry').change(function() {
	var key = $('#searchCountry').val().split('.')[0];
	console.log('identity ' + key);
	searchCountry(key); 
	return false;
});

function searchCountry(searchKey) {
	if (searchKey != '')
		findByIdCountry(searchKey);
}


function findByIdCountry(id) {
	$('#searchCity option').remove();
	$('#searchCity').append('<option value=""></option>');
	console.log('url :' + id.toString());
	$.ajax({
		type: 'GET',
		url: rootURLCountry + '/' + id + '/cities', // retrieve all cities of current country
		dataType: "json",
		success: function(data){
			$.each(data, function(index, city) {
				//$.each(cities, function(index, city) {
					$('#searchCity').append('<option value="'+city.name+'">'+city.name+'</option>');
			//});
			});
		}
	});
	findAllFacilitiesByCountry();//refresh list facilities of selected country
}

function findAllFacilitiesByCountry() {
	console.log('findAll by Country');
	var country = $('#searchCountry').val().split('.')[1];
	var url=root;
	if(country!=undefined && country!=''){
		url= url + '/countries/' + country + '/facilities';
		$.ajax({
			type: 'GET',
			url: url,
			dataType: "json", // data type of response
			success: renderList
		});
	}
}

function findAllCountry() {
	console.log('findAll Country:');
	$.ajax({
		type: 'GET',
		url: rootURLCountry,
		dataType: "json", // data type of response
		success: renderListCountry
	});
}

function renderListCountry(data) {
	var list = data == null ? [] : (data instanceof Array ? data : [data]);
	$('#searchCountry option').remove();
	$('#searchCountry').append('<option value=""></option>');
	$.each(list, function(index, country) {
		//$.each(countries, function(index, country) {
		console.log('data: '+country);
		$('#searchCountry').append('<option value="'+country.id+'.'+country.name+'">'+country.name+'</option>');
		//});
	});
}

