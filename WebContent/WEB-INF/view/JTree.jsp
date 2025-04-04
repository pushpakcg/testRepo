<!DOCTYPE html>
<html>
<head>
<%-- <title>jQuery Multi Nested Lists Plugin Demo</title>--%>
 <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="style.css">
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"> 
 <style>
ul {
    padding: 0em;
}

ul li, ul li ul li {
    position:relative;
    top:0;
    bottom:0;
    padding-bottom: 7px;

}

ul li ul {
    margin-left: 4em;
}

li {
    list-style-type: none;
}

li a {
    padding:0 0 0 10px;
    position: relative;
    top:1em;
}

li a:hover {
    text-decoration: none;
}

a.addBorderBefore:before {
    content: "";
    display: inline-block;
    width: 2px;
    height: 28px;
    position: absolute;
    left: -47px;
    top:-16px;
    border-left: 1px solid gray;
}

li:before {
    content: "";
    display: inline-block;
    width: 25px;
    height: 0;
    position: relative;
    left: 0em;
    top:1em;
    border-top: 1px solid gray;
}

ul li ul li:last-child:after, ul li:last-child:after {
    content: '';
    display: block;
    width: 1em;
    height: 1em;
    position: relative;
    background: #fff;
    top: 9px;
    left: -1px;
}
</style>
</head>
<body>
<%-- <div id="jquery-script-menu">
<div class="jquery-script-center">
<ul>
<li><a href="http://www.jqueryscript.net/layout/Animated-Tree-View-Plugin-For-jQuery-Bootstrap-3-MultiNestedLists.html">Download This Plugin</a></li>
<li><a href="http://www.jqueryscript.net/">Back To jQueryScript.Net</a></li>
</ul>
<div class="jquery-script-ads"><script type="text/javascript"><!--
google_ad_client = "ca-pub-2783044520727903";
/* jQuery_demo */
google_ad_slot = "2780937993";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script></div>
<div class="jquery-script-clear"></div>
</div>
</div>
<h1 style="margin-top:150px;">jQuery Multi Nested Lists Plugin Demo</h1>--%>
<div class="container">
<ul>
<li><a href="#">FA Income Tax</a>
<ul>
<li><a href="#">FA120 - Tax Addition Report</a></li>
<li><a href="#">FA120 - Tax Retirement Report</a></li>
<li><a href="#">FA120 - Tax Register Report</a></li>
<li><a href="#">FA250 - Tax Addition Report</a></li>
<li><a href="#">FA250 - Tax Retirement Report</a></li>
<li><a href="#">FA250 - Tax Register Report</a></li>
<li><a href="#">FA500 - Tax Addition Report</a></li>
<li><a href="#">FA500 - Tax Retirement Report</a></li>
<li><a href="#">FA500 - Tax Register Report</a></li>
</ul>
<li><a href="#">FA Historical</a>
<ul>
<li><a href="#">2016 RECALCULATED USING 2018/Q3 DATA</a>
<ul>
<li><a href="#">FA120 - USRG</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
<li><a href="#">FA250 - Legacy NBC</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
</ul>
<li><a href="#">2017 RECALCULATED USING 2018/Q3 DATA</a>
<ul>
<li><a href="#">FA120 - USRG</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
<li><a href="#">FA250 - Legacy NBC</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
</ul>
<li><a href="#">2018 FORECAST USING 2018/Q3 DATA</a>
<ul>
<li><a href="#">FA120 - USRG</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
<li><a href="#">FA250 - Legacy NBC</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
</ul>
<li><a href="#">2018 YEAR END REPORTS</a>
<ul>
<li><a href="#">FA120 - USRG</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
<li><a href="#">FA250 - Legacy NBC</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
</ul>
<li><a href="#">2017 YEAR END REPORTS</a>
<ul>
<li><a href="#">FA120 - USRG</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
<li><a href="#">FA250 - Legacy NBC</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
</ul>
<li><a href="#">2017 ADS DEPR REPORTS</a>
<ul>
<li><a href="#">FA120 - USRG</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
<li><a href="#">FA250 - Legacy NBC</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
</ul>
<li><a href="#">2017 RECALCULATED USING Q4/2018</a>
<ul>
<li><a href="#">FA120 - USRG</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
<li><a href="#">FA250 - Legacy NBC</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
</ul>
<li><a href="#">2018 RECALCULATED USING Q3/2019</a>
<ul>
<li><a href="#">FA120 - USRG</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
<li><a href="#">FA250 - Legacy NBC</a>
<ul>
<li><a href="#">Tax Addition Report</a></li>
<li><a href="#">Tax Retirement Report</a></li>
<li><a href="#">Tax Register Report</a></li>
</ul>
</ul>
</ul>
<li><a href="#">FA Financial</a>
<ul>
<li><a href="#">FA120-Asset Addition Report</a></li>
<li><a href="#">FA250-Asset Addition Report</a></li>
<li><a href="#">FA500-Asset Addition Report</a></li>
<li><a href="#">FA120-Asset Register Report</a></li>
<li><a href="#">FA250-Asset Register Report</a></li>
<li><a href="#">FA500-Asset Register Report</a></li>
<li><a href="#">FA120-Asset Retirement Report</a></li>
<li><a href="#">FA250-Asset Retirement Report</a></li>
<li><a href="#">FA500-Asset Retirement Report</a></li>
</ul>
</li>
<li><a href="#">FA Dashboard</a>
<ul>
<li><a href="#">FA Asset Dashboard</a></li>
</ul>
</li>
<%-- <li><a href="#">Joy Division</a>
<ul>
<li><a href="#">Unknown Pleasures</a></li>
<li><a href="#">Closer</a></li>
<li><a href="#">Still</a></li>
</ul>
</li>
</ul>
</li>
<li><a href="#">Liverpool</a>
<ul>
<li><a href="#">OMD</a>
<ul>
<li><a href="#">OMD</a></li>
<li><a href="#">Organisation</a></li>
</ul>
</li>
<li><a href="#">Echo & the Bunnymen</a>
<ul>
<li><a href="#">Crocodiles</a></li>
<li><a href="#">Heaven Up Here</a></li>
<li><a href="#">Porcupine</a></li>
</ul>--%>
</li>
</ul>
</li>
</ul>
</div>
</body>
</html>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="MultiNestedList.js"></script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36251023-1']);
  _gaq.push(['_setDomainName', 'jqueryscript.net']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
//Select the main list and add the class "hasSubmenu" in each LI that contains an UL
  $('ul').each(function(){
    $this = $(this);
    $this.find("li").has("ul").addClass("hasSubmenu");
  });
  // Find the last li in each level
  $('li:last-child').each(function(){
    $this = $(this);
    // Check if LI has children
    if ($this.children('ul').length === 0){
      // Add border-left in every UL where the last LI has not children
      $this.closest('ul').css("border-left", "1px solid gray");
    } else {
      // Add border in child LI, except in the last one
      $this.closest('ul').children("li").not(":last").css("border-left","1px solid gray");
      // Add the class "addBorderBefore" to create the pseudo-element :defore in the last li
      $this.closest('ul').children("li").last().children("a").addClass("addBorderBefore");
      // Add margin in the first level of the list
      $this.closest('ul').css("margin-top","20px");
      // Add margin in other levels of the list
      $this.closest('ul').find("li").children("ul").css("margin-top","20px");
    };
  });
  // Add bold in li and levels above
  $('ul li').each(function(){
    $this = $(this);
    $this.mouseenter(function(){
      $( this ).children("a").css({"font-weight":"bold","color":"#336b9b"});
    });
    $this.mouseleave(function(){
      $( this ).children("a").css({"font-weight":"normal","color":"#428bca"});
    });
  });
  // Add button to expand and condense - Using FontAwesome
  $('ul li.hasSubmenu').each(function(){
    $this = $(this);
    $this.prepend("<a href='#'><i class='fa fa-minus-circle'></i><i style='display:none;' class='fa fa-plus-circle'></i></a>");
    $this.children("a").not(":last").removeClass().addClass("toogle");
  });
  // Actions to expand and consense
  $('ul li.hasSubmenu a.toogle').click(function(){
    $this = $(this);
    $this.closest("li").children("ul").toggle("slow");
    $this.children("i").toggle();
    return false;
  });

</script>