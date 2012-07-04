(function() {
	var window_ = this;
	
	/**
	 * UIApplicationCategorySelector constructor.
	 */
	function UIApplicationCategorySelector(params) {
	  UIItemSelector = eXo.webui.UIItemSelector;
		var applicationCategoryIds = (params.applicationCategoryIds).split(',');
		var allApplicationCategorySize = parseInt(params.allApplicationCategorySize);
		var applicationCategoryEls = [];

		if(applicationCategoryIds != null) {
			for (var i = 0; i < allApplicationCategorySize; i++) {
				var currEl = gj(applicationCategoryIds[i]);

				(function(element) {
				    $(element).on('mouseover focus', function(evt){
                UIItemSelector.onOver(this,true);
            });

            $(element).on('mouseout blur', function(evt){
                UIItemSelector.onOver(this,false);
            });
        })(currEl);
			}
		}
	}
	
  //expose
  window_.eXo = window_.eXo || {};
  window_.eXo.social = window_.eXo.social || {};
  window_.eXo.social.webui = window_.eXo.social.webui || {};
  window_.eXo.social.webui.UIApplicationCategorySelector = UIApplicationCategorySelector;
})();