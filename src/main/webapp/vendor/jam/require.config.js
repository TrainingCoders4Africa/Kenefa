var jam = {
    "packages": [
        {
            "name": "modernizr",
            "location": "../vendor/jam/modernizr",
            "main": "modernizr.js"
        },
        {
            "name": "jquery",
            "location": "../vendor/jam/jquery",
            "main": "jquery.js"
        },
        {
            "name": "lodash",
            "location": "../vendor/jam/lodash",
            "main": "./lodash.js"
        },
        {
            "name": "underscore",
            "location": "../vendor/jam/underscore",
            "main": "underscore.js"
        },
        {
            "name": "backbone",
            "location": "../vendor/jam/backbone",
            "main": "backbone.js"
        },
        {
            "name": "backbone.validateAll",
            "location": "../vendor/jam/Backbone.validateAll",
            "main": "Backbone.validateAll.js"
        },
        {
            "name": "backbone.layoutmanager",
            "location": "../vendor/jam/backbone.layoutmanager",
            "main": "backbone.layoutmanager.js"
        },
        
        {
            "name": "bootstrap",
            "location": "../vendor/jam/bootstrap",
            "main": "bootstrap.js"
        }
        
    ],
    "version": "0.2.11",
    "shim": {
        "backbone": {
            "deps": [
                "jquery",
                "underscore",
                "bootstrap"
            ],
            "exports": "Backbone"
        },
        "backbone.layoutmanager": {
            "deps": [
                "jquery",
                "backbone",
                "lodash"
            ],
            "exports": "Backbone.LayoutManager"
        },
        // Backbone.validateAll depends on Backbone.
        "backbone.validateAll": {
            "deps": [
                "backbone"
            ],
            "exports": "Backbone.validateAll"
        },
        "bootstrap": {
            "deps": [
                "modernizr"
            ],
            "exports": "Modernizr"
        } 
    }
};

if (typeof require !== "undefined" && require.config) {
    require.config({packages: jam.packages, shim: jam.shim});
}
else {
    var require = {packages: jam.packages, shim: jam.shim};
}

if (typeof exports !== "undefined" && typeof module !== "undefined") {
    module.exports = jam;
}