(function(window,$){
  'use strict';
  function TextParser(element){
    this.editor=$(element);
    this.parsePattern={
      '\t':'&nbsp;&nbsp;&nbsp;&nbsp;',
      '\n':'<br/>',
      ' ':'&nbsp;',
      '\"':'&quot;',
      '<':'&lt;',
      '>':'&gt;',
      '\'':'&apos;',
      '&':'&amp;'
    };
    this.inversePattern={};
    this.reg='';
    this.inverseReg='';
    for(var each in this.parsePattern){
      this.reg+=each;
      this.inversePattern[this.parsePattern[each]]=each;
      this.inverseReg+=this.parsePattern[each]+'|';
    }

    this.reg='['+this.reg+']';
    this.inverseReg=this.inverseReg.substr(0,this.inverseReg.length-1);
  }

  TextParser.prototype.parse=function(){
    var content=this.editor.val();
    var that=this;
    content=content.replace(new RegExp(this.reg,'g'),function(str){
      return that.parsePattern[str];
    });
    return content;
  };
  TextParser.prototype.inverseParse=function(){
    var content=this.editor.val();
    var that=this;
    content=content.replace(new RegExp(this.inverseReg,'g'),function(str){
      return that.inversePattern[str];
    });
    return content;
  };
  /*
   *  Represenets an textpaser
   *  @constructor
   *  @param {object} - The default options selected by the user.
   */
  $.fn.textParser=function(){
    return new TextParser(this);
  };

  //以下部分将来可以整合进入TextParser中
  $.fn.extend({
    insertContent : function(myValue, t) {
      var $t = $(this)[0];
      if (document.selection) { // ie
        this.focus();
        var sel = document.selection.createRange();
        sel.text = myValue;
        this.focus();
        sel.moveStart('character', -l);
        var wee = sel.text.length;
        if (arguments.length == 2) {
          var l = $t.value.length;
          sel.moveEnd("character", wee + t);
          t <= 0 ? sel.moveStart("character", wee - 2 * t - myValue.length) : sel.moveStart( "character", wee - t - myValue.length);
          sel.select();
        }
      }
      else if ($t.selectionStart || $t.selectionStart == '0') {
        var startPos = $t.selectionStart;
        var endPos = $t.selectionEnd;
        var scrollTop = $t.scrollTop;
        $t.value = $t.value.substring(0, startPos)
        + myValue
        + $t.value.substring(endPos,$t.value.length);
        this.focus();
        $t.selectionStart = startPos + myValue.length;
        $t.selectionEnd = startPos + myValue.length;
        $t.scrollTop = scrollTop;
        if (arguments.length == 2) {
          $t.setSelectionRange(startPos - t,
          $t.selectionEnd + t);
          this.focus();
        }
      }
      else {
        this.value += myValue;
        this.focus();
      }
    }
  });

})(window,window.jQuery);
