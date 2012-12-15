define([
  // Application.
  "app",

  //Modules
  "modules/country",
  "modules/city",
  "modules/facility"
],

function(app, Country, City, Facility) {

  // Defining the application router, you can attach sub routers here.
  var Router = Backbone.Router.extend({
    routes: {
      "": "index",
      "countries/:id":"countries",
      "country/:pays/city/:city":"facilities"
    },

    index: function() {
      //bloc bienvenue
      app.useLayout().setView("header", new Backbone.View({
            tagName : "h1",
            beforeRender:function() {
              this.$el.html("Bienvenue sur KENEFA");
            }

         })).render();
      //bloc country
        console.log(Country.pays);
        app.useLayout().setView("#countries", 
          new Country.Views.List({
              collection:Country.pays
          })).render();

      },

    countries:function(id) {
        app.useLayout().setView("header",new Backbone.View({
            tagName : "h1",
            collection:Country.pays,
            beforeRender:function() {
              this.$el.empty();
              this.$el.html(this.collection.get(id).get("name"));
            }
         })).render();
    
        app.useLayout().setView("#cities",new Backbone.View({
            tagName : "select",
            id:"city",
            collection:City.cities,
            beforeRender:function() {
              app.useLayout().setView("#cities", 
                new City.Views.List({
                collection:this.collection
              })).render();
            }
        })).render();
    },

    facilities: function(country,city){
    	var facility = new Facility.Model({ "name": "CHU Fann", "address": "Avenue Cheikh Anta Diop ", "email": "chu-fann@gmail.com", "phone": "703-243-7371","latitude":13,"longitude":-19});
    	var facility2 = new Facility.Model({ "name": "DANTEC", "address": "Dakar Plateau Lamine Gueye ", "email": "dantc@dantec.com", "phone": "233-277-7356","latitude":15,"longitude":-17});
      var facility3 = new Facility.Model({ "name": "CLINIQUE LES MAMELLES", "address": "Dakar Ngor ", "email": "mamelle@mam.com", "phone": "778-297-7356","latitude":14,"longitude":-19});
        facilities = new Facility.Collection([facility,facility2,facility3]);
        //facilities.add(facility2);
        //console.log("facilitis country and city :"+ country + ' '+ city);
      app.useLayout().setView("#facilityTbody", new Facility.Views.Layout({
            template:"facility",
            el:"#facilityTbody",
            collection: facilities
      })).render();
    	app.useLayout().setView("#main2", new Facility.Views.Main({
            //template:"facility",
            el:"#main2",
            collection: facilities
    	})).render();
      

    },

    // Shortcut for building a url.
    go: function() {
      return this.navigate(_.toArray(arguments).join("/"), true);
    }

  });

  return Router;

});
