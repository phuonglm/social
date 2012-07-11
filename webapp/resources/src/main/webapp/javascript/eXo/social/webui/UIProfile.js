/**
 * UIProfile.js
 */
(function() {
  // private class
  function UIProfile(params) {
    // use this to mark are local variables
    this.positionId = params.positionId || null;
    this.saveButtonId = params.saveButtonId || null;
    
    // initialize
    this.init();
  }
  
  var KEYS = {
    ENTER : 13
  }
  
  UIProfile.prototype.init = function() {
    var positionEl = gj("#" + this.positionId);
    var saveButtonEl = gj("#" + this.saveButtonId);
    
    if (positionEl.length > 0 && saveButtonEl.length > 0) {
      positionEl.keydown(function(event) {
        if ((event.keyCode || event.which) == KEYS.ENTER) {
          saveButtonEl.click();
          eXo.core.EventManager.cancelEvent(event);
          return;
        }
      });
    }
  }
  
  //namespace and exposion
  eXo = eXo || {};
  eXo.social = eXo.social || {};
  eXo.social.webui = eXo.social.webui || {};
  eXo.social.webui.UIProfile = UIProfile;
})();
