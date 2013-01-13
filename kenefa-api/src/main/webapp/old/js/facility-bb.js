// The root URL for the RESTful services
//key api google map: AIzaSyDno49S5R6xDxWMgiiLAvJ3hAVSFKygoLo
var rootURL = "http://localhost:8080/kenefa/rest/facilities";
/***Country***/
var rootURLCountry = "http://localhost:8080/kenefa/rest/countries";

// Models
window.Facility = Backbone.Model.extend({
    urlRoot:rootURL,
    defaults:{
        "id":null,
        "name":"",
        "type":"PUBLIC",
        "country":"SENEGAL",
        "city":"DAKAR"
    }
});

window.FacilityCollection = Backbone.Collection.extend({
    model:Facility,
    url:rootURL
});


// Views

/***/
window.FacilityListView = Backbone.View.extend({
    tagName:'ul',
    initialize:function () {
        this.model.bind("reset", this.render, this);
        var self = this;
        this.model.bind("add", function (facility) {
            $(self.el).append(new FacilityListItemView({model:facility}).render().el);
        });
    },

    render:function (eventName) {
        _.each(this.model.models, function (facility) {
            $(this.el).append(new FacilityListItemView({model:facility}).render().el);
        }, this);
        return this;
    }
});

window.FacilityListItemView = Backbone.View.extend({
    tagName:"li",
    template:_.template($('#tpl-facility-list-item').html()),
    initialize:function () {
        this.model.bind("change", this.render, this);
        this.model.bind("destroy", this.close, this);
    },
    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    },
    close:function () {
        $(this.el).unbind();
        $(this.el).remove();
    }
});

window.FacilityView = Backbone.View.extend({
    template:_.template($('#tpl-facility-details').html()),
    initialize:function () {
        this.model.bind("change", this.render, this);
    },
    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    },
    events:{
        "change input":"change",
        "click .save":"saveFacility",
        "click .delete":"deleteFacility"
    },
    change:function (event) {
        var target = event.target;
        console.log('changing ' + target.id + ' from: ' + target.defaultValue + ' to: ' + target.value);
        // You could change your model on the spot, like this:
        // var change = {};
        // change[target.name] = target.value;
        // this.model.set(change);
    },

    saveFacility:function () {
        this.model.set({
            //id:$('#id').val(),
            name:$('#name').val(),
            city:$('#city').val(),
            country:$('#country').val(),
            region:$('#type').val(),
        });
        if (this.model.isNew()) {
            var self = this;
            app.facilityList.create(this.model, {
                success:function () {
                    app.navigate('/facilities/' + self.model.id, false);
                },
                error : function (){
                    alert('unsuccessful save!!!');
                }
            });
        } else {
            alert('modification');
            this.model.save();
        }

        return false;
    },
    deleteFacility:function () {
        this.model.destroy({
            success:function () {
                alert('Facility deleted successfully');
                window.history.back();
            }
        });
        return false;
    },
    close:function () {
        $(this.el).unbind();
        $(this.el).empty();
    }
});

window.HeaderView = Backbone.View.extend({

    template:_.template($('#tpl-header').html()),

    initialize:function () {
        this.render();
    },

    render:function (eventName) {
        $(this.el).html(this.template());
        return this;
    },

    events:{
        "click .new":"newFacility"
    },

    newFacility:function (event) {
        app.navigate("facilities/new", true);
        return false;
    }
});

/*liztener country*/
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
/**Country model**/

window.Country = Backbone.Model.extend({
    urlRoot:rootURLCountry
});

window.CountryCollection = Backbone.Collection.extend({
    model:Country,
    url:rootURLCountry
});

// Views

/***/
window.CountryListView = Backbone.View.extend({
    tagName:'select',
    id:'searchCountry',
    initialize:function () {
        
    },

    render:function (eventName) {
        _.each(this.model.models, function (country) {
           // console.log('--- country :'+JSON.stringify(country));
            $(this.el).append(new CountryListItemView({model:country}).render().el);
        }, this);
        return this;
    },
    
    events:{
        "change":"change"
    },
    change:function (event) {
        var target = event.target;
        console.log('changing ' + target.id + ' from: ' + target.defaultValue + ' to: ' + target.value);
        
    }

});

window.CountryListItemView = Backbone.View.extend({
    tagName:'option',
    template:_.template($('#tpl-AllCountry').html()),
    initialize:function () {
         this.render;
         /*this.model.bind("change", alert('change'), this);*/
    },
    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    }
});


// Router
var AppRouter = Backbone.Router.extend({

    routes:{
        "":"list",
        "facilities/new":"newFacility",
        "facilities/:id":"facilityDetails"
    },

    initialize:function () {
        $('#header').html(new HeaderView().render().el);
    },

    list:function () {
        this.facilityList = new FacilityCollection();
        var self = this;
        this.facilityList.fetch({
            success:function () {
                self.facilityListView = new FacilityListView({model:self.facilityList});
                $('#sidebar').html(self.facilityListView.render().el);
                if (self.requestedId) self.facilityDetails(self.requestedId);
            }
        });

        this.countryList = new CountryCollection();
        var self = this;
        this.countryList.fetch({
            success:function () {
                self.countryListView = new CountryListView({model:self.countryList});
                $('#search').html(self.countryListView.render().el);
            }
        });
    },

    facilityDetails:function (id) {
        if (this.facilityList) {
            this.facility = this.facilityList.get(id);
            if (this.facilityView) this.facilityView.close();
            this.facilityView = new FacilityView({model:this.facility});
            $('#content').html(this.facilityView.render().el);
        } else {
            this.requestedId = id;
            this.list();
        }
    },

    newFacility:function () {
        if (app.facilityView) app.facilityView.close();
        app.facilityView = new FacilityView({model:new Facility()});
        $('#content').html(app.facilityView.render().el);
    }

});

var app = new AppRouter();
Backbone.history.start();