// City module
define([
  // Application.
  "app"
],

// Map dependencies from above array.
function(app) {

  // Create a new module.
  var City = app.module();

  // Default Model.
  City.Model = Backbone.Model.extend({
    defaults:function() {
      vrzion:1
    }
  });

  // Default Collection.
  City.Collection = Backbone.Collection.extend({
    model: City.Model,

    parse:function(obj) {
      //
    },

    initialize:function() {
     
    }
  });

	if(!City.cities){
	  City.cities=new City.Collection([
	    new City.Model({id:1,name:"Dakar"}),
	    new City.Model({id:2,name:"Louga"})
	  ]);
	}

 //alert('city :'+ JSON.stringify(City.cities));
  // Default View.

  City.Views.List = Backbone.Layout.extend({
    tagName: "select",
    id:"city",
    serialize:function() {
      return {model:this.model};
    },
    collection: City.cities,
    beforeRender:function() {
      //alert(JSON.stringify(this.collection));
      this.collection.each(function(model) {
        this.insertView(new City.Views.Layout({
            model:model
          }));
        this.$el.empty();
        this.$el.append("<option value=''>Select a city</option>")
      },this)
    },
    initialize:function(){
      
    },

    events:{
      "change": "changeCity"
    },

    changeCity:function() {
      //alert(this.$el.val());
      var country = this.$el.val();
      var city="Dakar";
      if(country != ''){
        app.router.go("country",country,"city", city);
      }

     }

  });

  City.Views.Layout = Backbone.Layout.extend({
    template: "city",
    tagName: "option",
    serialize:function() {
      return {model:this.model};
    },
    initialize:function () {
      this.$el.attr('value',this.model.get("id"));
    }
    
  });

  // Return the module for AMD compliance.
  return City;

});
