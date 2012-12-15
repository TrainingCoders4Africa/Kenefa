// Facility module
define([
  // Application.
  "app"
],

// Map dependencies from above array.
function(app) {

  // Create a new module.
  var Facility = app.module();

  // Default Model.
  Facility.Model = Backbone.Model.extend({
        // Default values for all of the Facility Model attributes
        defaults: {
            name: "",
            address: "",
            email: "",
            phone: "",
            latitude: 14,
            longitude: -17
        },

        // RegEx Patterns
        // ==============
        patterns: {
            specialCharacters: "[^a-zA-Z 0-9]+",
            digits: "[0-9]",
            email: "^[a-zA-Z0-9._-]+@[a-zA-Z0-9][a-zA-Z0-9.-]*[.]{1}[a-zA-Z]{2,6}$",
            phone: "^([0-9]{3})?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$"
        }//,

        // Validators
        // ==========
        /*validators: {
            minLength: function(value, minLength) {
                return value.length >= minLength;
            },
            maxLength: function(value, maxLength) {
                return value.length <= maxLength;
            },
            pattern: function(value, pattern) {
                return new RegExp(pattern, "gi").test(value) ? true : false;
            },
            isEmail: function(value) {  
               return Facility.prototype.validators.pattern(value, Facility.prototype.patterns.email);
            },
            isPhone: function(value) {
                return Facility.prototype.validators.pattern(value, Facility.prototype.patterns.phone);
            },
            hasSpecialCharacter: function(value) { 
                return Facility.prototype.validators.pattern(value, Facility.prototype.patterns.specialCharacters);
            },
            hasDigit: function(value) {                 
                return Facility.prototype.validators.pattern(value, Facility.prototype.patterns.digits);
            }
        },*/
        // Validate
        // ========
       /* validate: function(attrs) {         
            if(attrs.name != null) {
                if (!attrs.name) return { field: "name", error: "Name is required" };
                else if(!this.validators.minLength(attrs.name, 2)) return { field: "name", error: "Name is too short" };
                else if(!this.validators.maxLength(attrs.name, 25)) return { field: "name", error: "Name is too large" };
                else if(this.validators.hasSpecialCharacter(attrs.name)) return { field: "name", error: "Name cannot contain special characters" };
            }
            if(attrs.address != null) {
                if (!attrs.address) return { field: "address", error: "Last Name is required" };
                else if(!this.validators.minLength(attrs.address, 2)) return { field: "address", error: "Address is too short" };
                else if(!this.validators.maxLength(attrs.address, 50)) return { field: "address", error: "Address is too large" };
                //else if(this.validators.hasSpecialCharacter(attrs.address)) return { field: "address", error: "Address cannot contain special characters" };  
            } 
                if(attrs.email != null) {
                if (!attrs.email) return { field: "email", error: "Email is required" };
                else if(!this.validators.isEmail(attrs.email)) return { field: "email", error: "Email is not valid" };
            }
            if(attrs.phone != null) {
                if(!attrs.phone) return { field: "phone", "error": "Phone number is required" };
                else if(!this.validators.isPhone(attrs.phone)) return { field: "phone", error: "Phone Number is invalid" };
            }
        }*/

  });

  // Default Collection.
  Facility.Collection = Backbone.Collection.extend({
    model: Facility.Model
  });

  // Default View.
  Facility.Views.Formu = Backbone.Layout.extend({
      el:"#createFacility",
      events:{
           "click #create": "addFacility"
      },

      addFacility: function() {
          alert('add facility');
          var facility = new Facility.Model();
          facility.set({ "name": $("#name").val(), "address": $("#address").val(), "email": $("#email").val(), "phone": $("#phone").val(), "latitude": $("#latitude").val(), "longitude":$("#longitude").val()},
           {error: function(obj, error) {
              $(".errors").text(error.error);
              $("#" + error.field).focus();
          }
          });
          if(facility.isValid()) {
              this.collection.add(facility);
              $("#myModal").modal("hide");
          }
      }
  });

  Facility.Views.Layout = Backbone.Layout.extend({
        //el:"#main2",
	    template: "facility",
	    // View constructor
        initialize: function() {
        //this.template = _.template( $("#example").html(), { facilities: this.collection } );
            this.collection.on("add remove", this.render, this);
            // Twitter Bootstrap Modal Logic
            $("#myModal").on("hidden", function() {
                  // Reset's the form input fields
                $("form#createFacility input").val("");
                // Clear's any error messages
                $(".errors").empty();
            });

             /*$("#create").on("click", function() {
                alert('create');
            });*/

        },
        events: {
            "mouseenter tr": "showOption",
            "mouseleave tr": "hideOption",
            "click .icon-remove": "removeFacility",
            "click .icon-hand-up": "editFacility"
            //"click #create": "addFacility"
        },

       /* addFacility: function() {
           // alert('add facility');
            var facility = new Facility.Model();
            facility.set({ "name": $("#name").val(), "address": $("#address").val(), "email": $("#email").val(), "phone": $("#phone").val(), "latitude": $("#latitude").val(), "longitude":$("#longitude").val()},
             {error: function(obj, error) {
                $(".errors").text(error.error);
                $("#" + error.field).focus();
            }
            });
            if(facility.isValid()) {
                this.collection.add(facility);
                $("#myModal").modal("hide");
            }
        },*/
        
        editFacility: function(event) {
            var id = $(event.currentTarget).closest("tr").attr("data-id");
            var facility = this.collection.at(id);
           // $("#name").val(facility.get('name'));
            ///$("#address").attr('value',facility.get('address'));
           console.log('edit :'+JSON.stringify(facility));
            app.useLayout().setView("#main2", 
                     new Facility.Views.Formu({
                template:"facilityForm",
                model: this.collection.at(id)
            })).render();
        },
        removeFacility: function(event) {
            var id = $(event.currentTarget).closest("tr").attr("data-id");
            //alert('at '+JSON.stringify(this.collection));
            this.collection.remove(this.collection.at(id));
            this.render;
        },
        showOption: function(event) {
            $(event.currentTarget).find(".icon-remove").show();
            $(event.currentTarget).find(".icon-hand-up").show();
        },
        hideOption: function(event) {
            $(event.currentTarget).find(".icon-remove").hide();
            $(event.currentTarget).find(".icon-hand-up").hide();
        }

  });

    Facility.Views.Main = Backbone.Layout.extend({
        el:"#main2",
        events:{
             "click #create": "addFacility"
        },

        addFacility: function() {
            alert('add facility');
            var facility = new Facility.Model();
            facility.set({ "name": $("#name").val(), "address": $("#address").val(), "email": $("#email").val(), "phone": $("#phone").val(), "latitude": $("#latitude").val(), "longitude":$("#longitude").val()},
             {error: function(obj, error) {
                $(".errors").text(error.error);
                $("#" + error.field).focus();
            }
            });
            if(facility.isValid()) {
                this.collection.add(facility);
                $("#myModal").modal("hide");
            }
        }
    });


  // Return the module for AMD compliance.
  return Facility;

});
