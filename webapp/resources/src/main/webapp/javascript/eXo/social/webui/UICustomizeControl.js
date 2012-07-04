function UICustomizeControl() {
}

UICustomizeControl.prototype.onLoad = function(uicomponentId) {
	var UIForm = eXo.webui.UIForm;
	gj('#' + uicomponentId).click(function() {
	  gj('input').each(function() {
      var form = gj(this).closest('.UIForm');
      if (form != null ) UIForm.submitForm(form.attr("id"), 'ChangeOption', true);
    });
  });
}

/*===================================================================*/
if(!eXo.social) eXo.social = {};
if(!eXo.social.webui) eXo.social.webui = {};
eXo.social.webui.UICustomizeControl = new UICustomizeControl();