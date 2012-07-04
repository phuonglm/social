/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 
/**
 * UIComposerLinkExtension.js
 */
 
 (function() {
  var window_ = this,
      Util = eXo.social.Util,
      HTTP = "http://",
      GRAY_COLOR = "gray",
      BLACK_COLOR = "black",
      uiComposerLinkExtension;
  
  function changeLinkContent() {
    var link = this.linkData.link,
    title = this.linkData.title,
    image = this.linkData.image;
    description = this.linkData.description;
    var queryString = 'link='+encodeURIComponent(link)
	    + '&title='+encodeURIComponent(title)
	    + '&description='+encodeURIComponent(description);
    
    if(image != null){
    	queryString += '&image='+encodeURIComponent(image)
    }

    var url = this.changeLinkContentUrl.replace(/&amp;/g, "&") + "&ajaxRequest=true";
    eXo.social.PortalHttpRequest.ajaxPostRequest(url, queryString, true, function(req) {
     //callbacked
    });
  }
  
  /**
   * creates input/ textarea element for edit inline
   * if tagName = input 
   * <input type="text" id="editableText" value="" />
   * if tagName = textarea
   * <textarea cols="10" rows="3">value</textarea>
   */
  function addEditableText(oldEl, tagName, title) {
    var dataElement = gj(oldEl);
    var content = dataElement.html().trim();
    var editableEl = gj("<" + tagName + " />").attr("title", title).val(content);
    if('input' === tagName){
      editableEl.attr('type', 'text');
      editableEl.attr('size', 50);
      editableEl.attr('class', 'InputTitle');
      editableEl.attr('className', 'InputTitle');
    } else if ('textarea' === tagName) {
      editableEl.attr('cols', 50);
      editableEl.attr('rows', 5);
      editableEl.attr('class', 'InputDescription');
      editableEl.attr('className', 'InputDescription');
    }

    editableEl.insertAfter(dataElement);
    dataElement.hide();
    editableEl.focus();
    editableEl.keypress(function(e){
      if (Util.isEnterKey(e)) {
        updateElement(this);
      }
    });
    editableEl.blur(function(e){
      updateElement(this);
    });

    var updateElement = function(editableEl) {
        //hide this, set new value and display
        var oldEl = editableEl.previousSibling;
        if (oldEl.innerText != null) { //IE
            oldEl.innerText = editableEl.value;
        } else {
            oldEl.textContent = editableEl.value;
        }
        //updates data
        //detects element by class, if class contains ContentTitle -> update title,
        // if class contains ContentDescription -> update description
        oldEl.style.display="block";
        if (Util.hasClass(oldEl, 'Title')) {
          uiComposerLinkExtension.linkData.title = editableEl.value;
          changeLinkContent.apply(uiComposerLinkExtension);
        } else if (Util.hasClass(oldEl, 'Content')) {
          uiComposerLinkExtension.linkData.description = editableEl.value;
          changeLinkContent.apply(uiComposerLinkExtension);
        }
        editableEl.parentNode.removeChild(editableEl);
    }
}
  
  function UIComposerLinkExtension(params) {
    uiComposerLinkExtension = this;
    this.configure(params);
    this.init();
  }
  
  UIComposerLinkExtension.prototype.configure = function(params) {
    this.titleEditable = params.titleEditable || "";
    this.linkInfoDisplayed = params.linkInfoDisplayed || false;
    this.inputLinkId = params.inputLinkId || 'inputLink';
    this.attachButtonId = params.attachButtonId || 'attachButton';
    this.attachUrl = params.attachUrl || null;
    this.changeLinkContentUrl = params.changeLinkContentUrl || null;
    this.shownThumbnailIndex = params.shownThumbnailIndex || 0;
    this.uiThumbnailDisplayId = params.uiThumbnailDisplayId || 'UIThumbnailDisplay';
    this.thumbnailsId = params.thumbnailsId || 'Thumbnails';
    this.backThumbnailId = params.backThumbnailId || 'BackThumbnail';
    this.nextThumbnailId = params.nextThumbnailId || 'NextThumbnail';
    this.statsId = params.statsId || 'Stats';
    this.thumbnailCheckboxId = params.thumbnailCheckboxId || 'ThumbnailCheckbox';
    this.linkData = params.linkData || {};
    if (!this.attachUrl) {
      alert('error: attachUrl is null!');
    }
  }
  
  UIComposerLinkExtension.prototype.resetIsReady = function() {
    
    if (this.linkInfoDisplayed) {
      
    } else {

    }
  }
  
  UIComposerLinkExtension.prototype.init = function() {
  
    function showThumbnail() {
      for (var i = 0, l = this.images.length; i < l; i++) {
        this.images[i].style.display = 'none';
      }
      this.images[this.shownThumbnailIndex].style.display = 'block';
      doStats.apply(this);
    }
    
    function doStats() {
      this.stats.innerHTML = (this.shownThumbnailIndex + 1) + ' / ' + this.images.length;
    }
    
    var shareButton = gj('#ShareButton');
    shareButton.className = 'ShareButton';
    uiComposerLinkExtension = this;
    if (this.linkInfoDisplayed) {
      //trick: enable share button
      if (shareButton) {
        shareButton.attr('disable',false);
        shareButton.attr('class','ShareButton');
      }
      
      this.uiThumbnailDisplay = gj('#' + this.uiThumbnailDisplayId);
      this.thumbnails = gj('#' + this.thumbnailsId);
      this.backThumbnail = gj('#' + this.backThumbnailId);
      this.nextThumbnail = gj('#' + this.nextThumbnailId);
      this.stats = gj('#' + this.statsId);
      this.linkTitle = gj('#' + 'LinkTitle');
      this.linkDescription = gj('#' + 'LinkDescription');
      
      var titleParam = this.titleEditable;
      if (this.linkTitle) {
        Util.addEventListener(this.linkTitle, 'click', function(evt) {
          addEditableText(this, 'input', titleParam);
        }, false); 
      }
      
      if (this.linkDescription) {
        Util.addEventListener(this.linkDescription, 'click', function(evt) {
          addEditableText(this, 'textarea', titleParam);
        }, false);
      }
      
      if (this.thumbnails) {
        this.thumbnailCheckbox = gj('#' + this.thumbnailCheckboxId);
        this.images = gj('img',this.thumbnails);
        doStats.apply(this);

        this.backThumbnail.on('click', function(evt) {
          if (uiComposerLinkExtension.shownThumbnailIndex > 0) {
            uiComposerLinkExtension.shownThumbnailIndex--;
            showThumbnail.apply(uiComposerLinkExtension);
            uiComposerLinkExtension.linkData.image = Util.getAttributeValue(uiComposerLinkExtension.images[uiComposerLinkExtension.shownThumbnailIndex], 'src');
            changeLinkContent.apply(uiComposerLinkExtension);
          }
        });
        
        this.nextThumbnail.on( 'click', function(evt) {
          if (uiComposerLinkExtension.shownThumbnailIndex < uiComposerLinkExtension.images.length - 1) {
            uiComposerLinkExtension.shownThumbnailIndex++;
            showThumbnail.apply(uiComposerLinkExtension);
            uiComposerLinkExtension.linkData.image = gj(uiComposerLinkExtension.images[uiComposerLinkExtension.shownThumbnailIndex]).attr('src');
            changeLinkContent.apply(uiComposerLinkExtension);
          }
        });
        
        this.thumbnailCheckbox.on('click', function(evt) {
          if (uiComposerLinkExtension.thumbnailCheckbox.checked == true) {
            uiComposerLinkExtension.uiThumbnailDisplay.parentNode.style.height = '50px';
            uiComposerLinkExtension.uiThumbnailDisplay.style.display = 'none';
            uiComposerLinkExtension.linkData.image = '';
          } else {
            uiComposerLinkExtension.uiThumbnailDisplay.parentNode.style.height = '';
            uiComposerLinkExtension.uiThumbnailDisplay.style.display = 'block';
            uiComposerLinkExtension.linkData.image = Util.getAttributeValue(uiComposerLinkExtension.images[uiComposerLinkExtension.shownThumbnailIndex], 'src');
          }
          changeLinkContent.apply(uiComposerLinkExtension);
        });
      } else {
        this.images = [];
      }

    } else {

      if (shareButton) {
        shareButton.disabled = true;
        shareButton.className = 'ShareButtonDisable';
      }
      this.inputLink = gj('#' + this.inputLinkId);
      this.attachButton = gj('#' + this.attachButtonId);
      this.inputLink.val(HTTP);
      this.inputLink.css('color', GRAY_COLOR);
      var uiComposerLinkExtension = this;
      var inputLink = this.inputLink;
      Util.addEventListener(inputLink, 'focus', function(evt) {
        if (inputLink.val() === HTTP) {
          inputLink.val('');
          inputLink.css('color',BLACK_COLOR);
        }
      });
      
      this.inputLink.on('blur', function(evt) {
        if (inputLink.val() === '') {
          inputLink.val(HTTP);
          inputLink.css('color',GRAY_COLOR);
        }
      });
      
      Util.addEventListener(this.inputLink, 'keypress', function(evt) {
        //if enter submit link
      });
      this.attachButton.attr('disabled',false);
      this.attachButton.on('click', function(evt) {
        if (inputLink.val() === '' || inputLink.val() === HTTP) {
          return;
        }
        var url = uiComposerLinkExtension.attachUrl.replace(/&amp;/g, "&") + '&objectId='+ encodeURIComponent(inputLink.val()) + '&ajaxRequest=true';
        ajaxGet(url);
      });
      
    }
  }
  
  //expose
  window_.eXo = window_.eXo || {};
  window_.eXo.social = window_.eXo.social || {};
  window_.eXo.social.webui = window_.eXo.social.webui || {};
  window_.eXo.social.webui.UIComposerLinkExtension = UIComposerLinkExtension;
 })();
