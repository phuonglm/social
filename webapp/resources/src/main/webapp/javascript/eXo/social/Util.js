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
 * Util.js
 * Utility class
 * @author	<a href="mailto:hoatlevan@gmail.com">hoatle</a>
 * @since	Oct 20, 2009
 * @copyright	eXo Platform SEA
 */
 
/**
 * class definition
 */
 eXo.social.Util = function() {
 	//do not allow creating new object
 	if (this instanceof eXo.social.Util) {
		throw ("static class does not allow constructing a new object");
 	}
 }

/**
 * Checks if the passed argument is an array.
 *
 * @param obj
 * @return true or false
 */
 eXo.social.Util.isArray = function(obj) {
  if (Array.isArray) {
    return Array.isArray(obj);
  } else {
    return (obj.constructor.toString().indexOf("Array") !== -1);
  }
 }
/*
 *//**
  * gets element by id
  * @static
  *//*
 eXo.social.Util.getElementById = function(id) {
 	var el = document.getElementById(id);
 	if (!el) return null;
 	return el;
 }

 *//**
  * gets element by tagName
  * @param	tagName
  * @param	parent element
  * @static
  *//*
 eXo.social.Util.getElementsByTagName = function(tagName, parent) {
 	var parent = parent || document;
 	var els = parent.getElementsByTagName(tagName);
 	if (!els) return null;
 	return els;
 }
 */
 /**
 * Returns true if element has the css clazz
 * Uses a regular expression to search more quickly
 * @param	element
 * @param	clazz
 * @return	boolean
 * @static
 */
eXo.social.Util.hasClass = function(element, clazz) {
	var reg = new RegExp('(^|\\s+)' + clazz + '(\\s+|$)');
	return reg.test(element['className']);
} ;
 
 /**
  * gets element by clazz
  * @param	clazz
  * @param	parentElement
  * @return	array
  * @static
  */
 eXo.social.Util.getElementsByClass = function(root, tagName, clazz) {
 	var Util = eXo.social.Util;
	var list = [];
	var nodes = root.getElementsByTagName(tagName);
	for (var i = 0, l = nodes.length; i < l; i++)  {
		if (Util.hasClass(nodes[i], clazz)) list.push(nodes[i]);
	}
  	return list;
 }
 
 eXo.social.Util.getElementsByClassName = function(className, tag, elm){ 
		var testClass = new RegExp("(^|\\s)" + className + "(\\s|$)"); 
		var tag = tag || "*"; 
		var elm = elm || document; 
		var elements = (tag == "*" && elm.all)? elm.all : elm.getElementsByTagName(tag); 
		var returnElements = []; 
		var current; 
		var length = elements.length; 
		
		for(var i=0; i<length; i++){ 
		  current = elements[i]; 
		  if(testClass.test(current.className)){ 
			  returnElements.push(current); 
			} 
		} 
			
		return returnElements; 
	} 


/**
 * removes element from DOM by its id
 * @param	elementId
 * @static
 */
eXo.social.Util.removeElementById = function(elementId) {
    var element = document.getElementById(elementId);
    if (element === null) {
    	return;
    }
    element.parentNode.removeChild(element);
}

/**
 * hides element by its id
 * @param	elementId
 * @static
 */
eXo.social.Util.hideElement = function(elementId) {
	var element = document.getElementById(elementId);
	if (element === null) {
		return;
	}
	element.style.display='none';
}

/**
 * shows element by id
 * @param	elementI
 * @display	can be "inline" or "block" with default = "block"
 * @static
 */
eXo.social.Util.showElement = function(elementId, display) {
	if (display !== 'inline') {
		display = 'block';
	}
	var element = document.getElementById(elementId);
	if (element == null) {
		return;
	}
	element.style.display = display;
}

/**
 * inserts an element after an element
 * @param	newNode the node/ element to be inserted
 * @param	refNode the reference node/ element
 * @static 
 */
eXo.social.Util.insertAfter = function(newNode, refNode) {
	if (!newNode || !refNode) {
		return;
	}
	//checks if refNode.nextSibling is null
	refNode.parentNode.insertBefore(newNode, refNode.nextSibling);
}


/**
 * checks if keyNum == ENTER key
 * @param	event
 * @static
 */
eXo.social.Util.isEnterKey = function(e) {
  return (13 == (e.which ? e.which : e.keyCode));
}

/**
 * gets value of an attribute name from an element
 * @param	dom element
 * @param	attribute name
 * @return	attribute value
 * @static
 * @deprecated
 */
eXo.social.Util.getAttributeValue = function(element, attrName) {
	for(var x = 0, l = element.attributes.length; x < l; x++) {
	  if(element.attributes[x].nodeName.toLowerCase() == attrName) {
		return element.attributes[x].nodeValue;
	  }
	}
	return null;
}

eXo.social.Util.addEventListener = function(obj, evts, fnc, useCapture) {
  gj(obj).on(evts, fnc);
}

/**
 * Cross browser add event listener method. For 'evt' pass a string value with the leading "on" omitted
 * e.g. Util.addEventListener(window,'load',myFunctionNameWithoutParenthesis,false);
 * @param	obj object to attach event
 * @param	evt event name or array of event names: click, mouseover, focus, blur...
 * @param	func	function name
 * @param	useCapture	true or false; if false => use bubbling
 * @static
 * @see		http://phrogz.net/JS/AttachEvent_js.txt
 */
eXo.social.Util.addEventListener_Suppressed = function(obj, evts, fnc, useCapture) {
	if (obj === null || evt === null || fnc ===  null || useCapture === null) {
		alert('all params are required from Util.addEventListener!');
		return;
	}
  if (!eXo.social.Util.isArray(evts)) {
    evts = [evts];
  }
  for (var i = 0, len = evts.length; i < len; i++) {
    var evt = evts[i];
    if (!useCapture) useCapture = false;
    if (obj.addEventListener){
      obj.addEventListener(evt, fnc, useCapture);
    } else if (obj.attachEvent) {
      obj.attachEvent('on'+evt, function(evt) {
        fnc.call(obj, evt);
      });
    } else{
      //myAttachEvent(obj, evt, fnc);
      if (!obj.myEvents) obj.myEvents={};
      if (!obj.myEvents[evt]) obj.myEvents[evt]=[];
      var evts = obj.myEvents[evt];
      evts[evts.length] = fnc;
      obj['on'+evt] = function() { 
      //myFireEvent(obj,evt) 
	      if (!obj || !obj.myEvents || !obj.myEvents[evt]) return;
	      var evts = obj.myEvents[evt];
	      for (var i=0,len=evts.length;i<len;i++) evts[i]();
      };
    }

    //The following are for browsers like NS4 or IE5Mac which don't support either
    //attachEvent or addEventListener
    function myAttachEvent(obj, evt, fnc) {
      if (!obj.myEvents) obj.myEvents={};
      if (!obj.myEvents[evt]) obj.myEvents[evt]=[];
      var evts = obj.myEvents[evt];
      evts[evts.length] = fnc;
    }

    function myFireEvent(obj, evt) {
      if (!obj || !obj.myEvents || !obj.myEvents[evt]) return;
      var evts = obj.myEvents[evt];
      for (var i=0,len=evts.length;i<len;i++) evts[i]();
    }
  }
}

/**
 * removes event listener. 
 * @param	obj element
 * @param	evt event name, 'click', 'blur'. 'focus'...
 * @func	function name to be removed if found
 * @static
 * //TODO make sure method cross-browsered
 */
eXo.social.Util.removeEventListener = function(obj, evt, func, useCapture) {
	if (!useCapture) useCapture = false;
	if (obj.removeEventListener) {
		obj.removeEventListener(evt, func, useCapture);
	} else if (obj.detachEvent) {//IE
		obj.detachEvent('on'+evt, func)
	}
}

/**
 * strips htmlString, keeps allowedTags
 * @param	allowedTags Array
 * @param	escapedHtmlString String
 * @return	stripedHtml	String
 * @static
 */
eXo.social.Util.stripHtml = function(/*Array*/ allowedTags, /*String*/ escapedHtmlString) {
  if (!allowedTags || !escapedHtmlString) {
    return escapedHtmlString;
  }
  escapedHtmlString = escapedHtmlString.replace(/&#60;/g, '<').replace(/&#62;/g, '>').replace(/&#34;/g, '"');
  if (allowedTags.length === 0) {
    return escapedHtmlString;
  }
  //lowercased allowedTags
  var lowerCasedTags = [];
  var l = allowedTags.length;
  while (l--) {
    lowerCasedTags.push(allowedTags[l].toLowerCase());
  }
  var result = [];
  var handler = {
    getText: true,
    start: function(tag, attrs, unary) {
      if (lowerCasedTags.indexOf(tag) > -1) {
        result.push('<' + tag);
        for (var i = 0, l = attrs.length; i < l; i++) {
          result.push(' ' + attrs[i].name + '="' + attrs[i].escaped + '"');
        }
        result.push((unary ? "/" : "") + ">");
        this.getText = true;
      } else {
        this.getText = false;
      }

    },
    end: function(tag) {
      if (lowerCasedTags.indexOf(tag) > -1) {
        result.push('</' + tag + '>');
      }
    },
    chars: function(text) {
      if (this.getText) {
        result.push(text);
      }
    },
    comment: function(text) {
      //ignore this?
      //result.push('<!--' + text + '-->');
    }
  };
  HTMLParser(escapedHtmlString, handler);
  return result.join('');
}

/*
*Social jQuery plugin
*/ 
// Placeholder plugin for HTML 5
;(function($) {
    
	function Placeholder(input) {
    this.input = input;

    // In case of submitting, ignore placeholder value
    $(input[0].form).submit(function() {
        if (input.hasClass('placeholder') && input.val() == input.attr('placeholder')) {
            input.val('');
        }
    });
	}
	
	Placeholder.prototype = {
    show : function(loading) {
        if (this.input.val() === '' || (loading && this.isDefaultPlaceholderValue())) {
            this.input.addClass('placeholder');
            this.input.val(this.input.attr('placeholder'));
        }
    },
    hide : function() {
        if (this.isDefaultPlaceholderValue() && this.input.hasClass('placeholder')) {
            this.input.removeClass('placeholder');
            this.input.val('');
        }
    },
    isDefaultPlaceholderValue : function() {
        return this.input.val() == this.input.attr('placeholder');
    }
	};
	
	var HAS_SUPPORTED = !!('placeholder' in document.createElement('input'));
	
	$.fn.placeholder = function() {
    return HAS_SUPPORTED ? this : this.each(function() {
        var input = $(this);
        var placeholder = new Placeholder(input);
        
        placeholder.show(true);
        
        input.focus(function() {
            placeholder.hide();
        });
        
        input.blur(function() {
            placeholder.show(false);
        });
    });
	}
})(gj);

// Autosuggestion plugin.
;(function($) {

    $.fn.autosuggest = function(url, options) {
      var KEYS = {
        ENTER : 13,
        DOWN : 40,
        UP : 38
      },
      DELIMITER = ',',
      DELIMITER_AND_SPACE = ', ';

	    var COLOR = {
	      FOCUS : "#000000",
	      BLUR : "#C7C7C7"
	    };

      var defaults = {
        defaultVal: undefined,
        onSelect: undefined,
        maxHeight: 150,
        multisuggestion : false,
        width: undefined
      };

      options = $.extend(defaults, options);

     return this.each(function() {

	      var input = $(this),
	          results = $('<div />'),
	          currentSelectedItem, posX, posY;

        $(results).addClass('suggestions')
                    .css({
                      'top': (input.position().top + input.height() + 5) + 'px',
                      'left': input.position().left + 'px',
                      'width': options.width || (input.width() + 'px')
                    })
                    .hide();

        // append to target input
	      input.after(results)
	           .keyup(keysActionListener)
	           .blur(function(e) {
	                var resPos = $(results).offset();
	                
	                resPosBottom = resPos.top + $(results).height();
	                resPosRight = resPos.left + $(results).width();
	                
	                if (posY < resPos.top || posY > resPosBottom || posX < resPos.left || posX > resPosRight) {
                    $(results).hide();
	                }

	                if ($(this).val().trim().length == 0) {
	                  $(input).val(options.defaultVal);
	                  $(input).css('color', COLOR.BLUR);
	                }
	           })
	           .focus(function(e) {
	                $(results).css({
	                  'top': (input.position().top + input.height() + 5) + 'px',
	                  'left': input.position().left + 'px'
	                });

	                if ($('div', results).length > 0) {
	                  $(results).show();
	                }
	                
	                if (options.defaultVal && $(this).val() == options.defaultVal) {
	                  $(this).val('');
	                  $(this).css('color', COLOR.FOCUS);
	                } 
	                
	           })
	           .attr('autocomplete', 'off');

	        function buildResults(searchedResults) {
	            var i, iFound = 0;

	            $(results).html('').hide();

              if (searchedResults == null) return;

	            // build list of item over searched result
	            for (i = 0; i < searchedResults.length; i += 1) {
                var item = $('<div />'),
                    text = searchedResults[i];

                $(item).append('<p class="text">' + text + '</p>');

                if (typeof searchedResults[i].extra === 'string') {
                  $(item).append('<p class="extra">' + searchedResults[i].extra + '</p>');
                }

                $(item).addClass('resultItem')
                    .click(function(n) { return function() {
                      selectResultItem(searchedResults[n]);
                    };}(i))
                    .mouseover(function(el) { return function() {
                      changeHover(el);
                    };}(item));

                $(results).append(item);

                iFound += 1;
                if (typeof options.maxResults === 'number' && iFound >= options.maxResults) {
                  break;
                }
	            }

	            if ($('div', results).length > 0) { // if have any element then display the list
                currentSelectedItem = undefined;
                $(results).show().css('height', 'auto');
                if ($(results).height() > options.maxHeight) {
                    $(results).css({'overflow': 'auto', 'height': options.maxHeight + 'px'});
                }
	            }
	        };

	        function reloadData() {
	          var val = input.val();
	          var search_str;
	          
	          if (val.length > 0) val = $.trim(val);
	          
	          if (options.multisuggestion) {
	            search_str = getSearchString(val);
	          } else {
	            search_str = val;
	          }
	          
	          var restUrl = url.replace('input_value', search_str);
	          $.ajax({
	                  type: "GET",
	                  url: restUrl,
	                  complete: function(jqXHR) {
					            if(jqXHR.readyState === 4) {
					              buildResults($.parseJSON(jqXHR.responseText).names);
					            }
	                  }
	          })
	        };

	        function selectResultItem(item) {

	          setValues(item);
	          
	          $(results).html('').hide();
	          if (typeof options.onSelect === 'function') {
	            options.onSelect(item);
	          }
	        };

          function getSearchString(val) {
			      var arr = val.split(DELIMITER);
			      return $.trim(arr[arr.length - 1]);
			    };

	        function changeHover(element) {
            $('div.resultItem', results).removeClass('hover');
            $(element).addClass('hover');
            currentSelectedItem = element;
          };

          function setValues(item) {
            var currentVals = $.trim(input.val());
            var selectedVals;
            
            if (options.multisuggestion) {
	            if(currentVals.indexOf(DELIMITER) >= 0) {
	              selectedVals = currentVals.substr(0, currentVals.lastIndexOf(DELIMITER)) + DELIMITER_AND_SPACE + item;
	              input.val(selectedVals);
	            } else {
	              input.val(item);
	            }
	          } else {
	            input.val(item);
	          }
          };
          
	        function keysActionListener(event) {
	          var keyCode = event.keyCode || event.which;

	          switch (keyCode) {
	            case KEYS.ENTER:
	                if (options.multisuggestion) {
	                   $(currentSelectedItem).trigger('click');
                     return false;
	                }
	                
	                if (currentSelectedItem) {
                    $(currentSelectedItem).trigger('click');
	                } else {
	                  options.onSelect();
	                }

	                return false;
	            case KEYS.DOWN:
	                if (typeof currentSelectedItem === 'undefined') {
	                  currentSelectedItem = $('div.resultItem:first', results).get(0);
	                } else {
	                  currentSelectedItem = $(currentSelectedItem).next().get(0);
	                }

	                changeHover(currentSelectedItem);
	                if (currentSelectedItem) {
	                  $(results).scrollTop(currentSelectedItem.offsetTop);
	                }

	                return false;
	            case KEYS.UP:
	                if (typeof currentSelectedItem === 'undefined') {
	                  currentSelectedItem = $('div.resultItem:last', results).get(0);
	                } else {
	                  currentSelectedItem = $(currentSelectedItem).prev().get(0);
	                }

	                changeHover(currentSelectedItem);
	                if (currentSelectedItem) {
	                  $(results).scrollTop(currentSelectedItem.offsetTop);
	                }

	                return false;
	            default:
	                reloadData.apply(this, [event]);
	          }
	        };

	        $('body').mousemove(function(e) {
            posX = e.pageX;
            posY = e.pageY;
          });
	    });
    }
})(gj);


// Tooltip plugin
;(function($) {
    $.fn.toolTip = function(url, settings) {

        var defaultSettings = {
	        className   : 'UserName',
	        color       : 'yellow',
	        onHover     : undefined,
	        timeout     : 300
        };
        
        /* Combining the default settings object with the supplied one */
        settings = $.extend(defaultSettings, settings);

        return this.each(function() {

            var elem = $(this);
            
            // Continue with the next element in case of not effected element
            if(!elem.hasClass(settings.className)) return true;
            
            var scheduleEvent = new eventScheduler();
            var tip = new Tip();

            elem.append(tip.generate()).addClass('UIToolTipContainer');

            elem.addClass(settings.color);
            
            elem.hover(function() {
              reLoadPopup();
	            tip.show();
	            scheduleEvent.clear();
            },function(){
	            scheduleEvent.set(function(){
	              tip.hide();
	            }, settings.timeout);
            });
            
            function reLoadPopup() {
              var hrefValue = elem.attr('href');
              var personId = hrefValue.substr(hrefValue.lastIndexOf("/") + 1);
              
              var restUrl = url.replace('person_Id', personId);
              
              $.ajax({
                      type: "GET",
                      url: restUrl,
                      complete: function(jqXHR) {
                                if(jqXHR.readyState === 4) {
                                  var avatarURL = ($.parseJSON(jqXHR.responseText)).avatarURL;
										              var activityTitle = ($.parseJSON(jqXHR.responseText)).activityTitle;
										              var relationStatus = ($.parseJSON(jqXHR.responseText)).relationshipType;
										              
										              var html = [];
						                      html.push('<div style="float: right; cursor:pointer;">');
						                      html.push('  <div id="ClosePopup" class="ClosePopup" title="Close">[x]</div>');
						                      html.push('</div>');
						                      html.push('<div id="UserAvatar" class="UserAvatar">');
						                      html.push('  <img title="Avatar" alt="Avatar" src="' + avatarURL + '"></img>'); 
						                      html.push('</div>');
						                      html.push('<div id="UserTitle" class="UserTitle">');
						                      html.push('  <span>');
						                      html.push(     activityTitle);
						                      html.push('  </span>');
						                      html.push('</div>');
						                      html.push('<div id="UserAction" class="UserAction">');
						                      html.push('<span>');
										              html.push('</span>');
						                      html.push('</div>');
						                      $('.UIToolTip').html(html.join(''));
                                }
                      }
              })
            };
            
            function buildContent(resp) {
              
            }
        });
    };


    function eventScheduler(){};
    
    eventScheduler.prototype = {
      set : function (func,timeout){
        this.timer = setTimeout(func,timeout);
      },
      clear: function(){
        clearTimeout(this.timer);
      }
    };

    function Tip(){
	    this.shown = false;
    };
    
    Tip.prototype = {
	    generate: function(){
	        return this.tip || (this.tip = $('<span class="UIToolTip"><span class="pointyTipShadow"></span><span class="pointyTip"></span></span>'));
	    },
	    show: function(){
	        if(this.shown) return;
	        
	        this.tip.css('margin-left',-this.tip.outerWidth()/2).fadeIn('fast');
	        this.shown = true;
	    },
	    hide: function(){
	        this.tip.fadeOut();
	        this.shown = false;
	    }
    };
})(gj);