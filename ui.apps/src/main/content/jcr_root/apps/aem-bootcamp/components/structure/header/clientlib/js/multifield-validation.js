(function ($, window) 

{ 

  $(window).adaptTo("foundation-registry").register("foundation.validation.validator", { 

    selector: "[data-foundation-validation^='multifield-max']", 

    validate: function(el) { 

      var validationName = el.getAttribute("data-validation") 

      var max = validationName.replace("multifield-max-", ""); 

      max = parseInt(max); 

      if (el.items.length > max){ 

        return "Max allowed items is "+ max 

      } 

    } 

  }); 

 

}) 

($, window); 