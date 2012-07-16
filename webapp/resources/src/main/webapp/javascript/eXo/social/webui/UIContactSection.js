(function () {
	var window_ = this;
	
	function UIContactSection() {
	}
	
	/**
	 * Inits textbox to add focus and blur capability.
	 * @param elementIds Id of component which will be added focus and blur capability.
	 * @param defaultValue Default value when initing.
	 */
	UIContactSection.prototype.initInputTextBox = function(elementIds, defaultValue) {
		var idList = elementIds.split(',');
		
		for (var i = 0; i<idList.length; i++) {
			var urlChildInput = gj("#"+ idList[i]);
			if (urlChildInput.length == 0) {
		    continue;	
		  }
		  
			(function(idx) {
				 var input = gj("#" + idList[idx]);
		     var inputValue = gj(input).val().trim();
		     if (inputValue != defaultValue) {
		       gj(input).css('color','#000000');
		     } else {
		       gj(input).css('color','#545454');
		     }
		     
		     var uiContactSection = this;
		     
		     gj(input).focus(function() {
			       if (gj(this).val().trim() == defaultValue) {
			         gj(this).val('');
			         gj(this).css('color','#000000');
			       }
		     });
		     
		     gj(input).blur(function() {
		       if (gj(this).val().trim() == "") {
		         gj(this).val(defaultValue);
		         gj(this).css('color','#545454');
		       }
		     });
		     
		  })(i);
		}
	}
	  //expose
  window_.eXo.social.webui.UIContactSection = UIContactSection;
})();