// Country module
define([
  // Application.
  "app"
],

// Map dependencies from above array.
function(app) {

  // Create a new module.
  var Country = app.module();

  // Default Model.
  Country.Model = Backbone.Model.extend({
    defaults:function() {
      //
    }
  });

  // Default Collection.
  Country.Collection = Backbone.Collection.extend({
    model: Country.Model,

    parse:function(obj) {
      //
    },

    initialize:function() {
     
    }
  });

if(!Country.pays){
  //alert('initialization country.payz collection');
  Country.pays= new Country.Collection([
              new Country.Model({id:"1",name:"Senegal",cities:[{id:1,name:"Dakar"},{id:2,name:"Louga"}]}),
              new Country.Model({id:"2",name:"Cote d'Ivoire"}),
              new Country.Model({id:"3",name:"Burkina Fasso"}),
              new Country.Model({id:"4",name:"Benin"}),
              new Country.Model({id:"5",name:"Mali"})
  ]);
}

  // Default View.
  
  Country.Views.List = Backbone.Layout.extend({
    //el:"#country",
    tagName: "select",
    id:"country",
    beforeRender:function() {
        this.collection.each(function(model) {
        this.insertView(new Country.Views.Layout({
            model:model
          }));
        this.$el.empty();
        this.$el.append("<option value=''> Select a country</option>")
      },this)
    },

    initialize:function() {
      /*this.collection.on("add",function(model) {
        $this.insertView(new Country.Views.Layout({model:model})).render();
       },this);*/
    },

    events:{
      "change": "changeCountry"
    },

    changeCountry:function() {
      console.log(this.$el);
      var id = this.$el.val();
      if(id != ''){
        app.router.go("countries", id);
      }

     }

  });

  

  Country.Views.Layout = Backbone.Layout.extend({
    template: "country",
    tagName: "option",
    serialize:function() {
      return {model:this.model};
    },
    initialize:function () {
      this.$el.attr('value',this.model.get("id"));
    }
    
  });

  // Return the module for AMD compliance.
  return Country;

});
