// The root URL for the RESTful services
var rootURLCountry = "http://localhost:8080/kenefa/rest/countries";

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
    tagName:'span',
    initialize:function () {
         this.render();
    },

    render:function (eventName) {
        _.each(this.model.models, function (country) {
            $(this.el).append(new CountryListItemView({model:country}).render().el);
        }, this);
        return this;
    }
});

window.CountryListItemView = Backbone.View.extend({
    tagName:'select',
    template:_.template($('#tpl-AllCountry').html()),
    initialize:function () {
         this.render;
    },
    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    }
});

// Router
var AppRouterC = Backbone.Router.extend({

    routes:{
        "":"list"
    },

    list:function () {
        this.countryList = new CountryCollection();
        alert('list :');
        var self = this;
        this.countryList.fetch({
            success:function () {
                self.countryListView = new CountryListView({model:self.countryList});
                $('#search').html(self.countryListView.render().el);
            }
        });
    }

});

var appC = new AppRouterC();

