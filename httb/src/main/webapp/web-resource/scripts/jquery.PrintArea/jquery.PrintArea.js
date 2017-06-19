/**
 *  Version 2.1
 *      -Contributors: "mindinquiring" : filter to exclude any stylesheet other than print.
 *  Tested ONLY in IE 8 and FF 3.6. No official support for other browsers, but will
 *      TRY to accomodate challenges in other browsers.
 *  Example:
 *      Print Button: <div id="print_button">Print</div>
 *      Print Area  : <div class="PrintArea"> ... html ... </div>
 *      Javascript  : <script>
 *                       $("div#print_button").click(function(){
 *                           $("div.PrintArea").printArea( [OPTIONS] );
 *                       });
 *                     </script>
 *  options are passed as json (json example: {mode: "popup", popClose: false})
 *
 *  {OPTIONS} | [type]    | (default), values      | Explanation
 *  --------- | --------- | ---------------------- | -----------
 *  @mode     | [string]  | ("iframe"),"popup"     | printable window is either iframe or browser popup
 *  @popHt    | [number]  | (500)                  | popup window height
 *  @popWd    | [number]  | (400)                  | popup window width
 *  @popX     | [number]  | (500)                  | popup window screen X position
 *  @popY     | [number]  | (500)                  | popup window screen Y position
 *  @popTitle | [string]  | ('')                   | popup window title element
 *  @popClose | [boolean] | (false),true           | popup window close after printing
 *  @strict   | [boolean] | (undefined),true,false | strict or loose(Transitional) html 4.01 document standard or undefined to not include at all (only for popup option)
 */
(function($) {
$.fn.printArea = function() {
var ele = $(this);
var printCss = '';
$(document).find("link").filter(function() {
return $(this).attr("rel").toLowerCase() == "stylesheet";
}).each(
function() {
printCss = printCss + '<link type="text/css" rel="stylesheet" href="' + $(this).attr("href") + '" >';
});

var printContent = '<div class="' + $(ele).attr("class") + '">' + $(ele).html() + '</div>';
var windowUrl = 'about:blank';
var uniqueName = new Date();
var windowName = 'Print' + uniqueName.getTime();

var printWindow = window.open(windowUrl, windowName, 'left=0,top=0,width=' + screen.width + ',height=' + screen.height);
var BodyHtml = '<body>';
var BodyEnd = "</body>";
printWindow.document.write(printCss + BodyHtml + printContent + BodyEnd);
printWindow.document.close();
printWindow.focus();
printWindow.print();
printWindow.close();
}
})(jQuery);
