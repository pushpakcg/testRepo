<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
ul, #myUL {
  list-style-type: none;
}

#myUL {
  margin: 0;
  padding: 0;
}

.caret {
  cursor: pointer;
  -webkit-user-select: none; /* Safari 3.1+ */
  -moz-user-select: none; /* Firefox 2+ */
  -ms-user-select: none; /* IE 10+ */
  user-select: none;
}

.caret::before {
  content: "\25B6";
  color: black;
  display: inline-block;
  margin-right: 6px;
}

.caret-down::before {
  -ms-transform: rotate(90deg); /* IE 9 */
  -webkit-transform: rotate(90deg); /* Safari */'
  transform: rotate(90deg);  
}

.nested {
  display: none;
}

.active {
  display: block;
}
</style>
</head>
<body>

<ul id="myUL">
  <li><span class="caret">Beverages</span>
    <ul class="nested">
      <li>Water</li>
      <li>Coffee</li>
      <li><span class="caret">Tea</span>
        <ul class="nested">
          <li>Black Tea</li>
          <li>White Tea</li>
          <li><span class="caret">Green Tea</span>
            <ul class="nested">
              <li>Sencha</li>
              <li>Gyokuro</li>
              <li>Matcha</li>
              <li>Pi Lo Chun</li>
            </ul>
          </li>
          
        </ul>
      </li>  
    </ul>
  </li>
  <ul id="treeDemo" class="ztree"><li id="treeDemo_1" class="level0" tabindex="0" hidefocus="true" treenode=""><span id="treeDemo_1_switch" title="" class="button level0 switch roots_open" treenode_switch=""></span><a id="treeDemo_1_a" class="level0" treenode_a="" onclick="" target="_blank" style="" title="pNode 01"><span id="treeDemo_1_ico" title="" treenode_ico="" class="button ico_open" style=""></span><span id="treeDemo_1_span" class="node_name">pNode 01</span></a><ul id="treeDemo_1_ul" class="level0 line" style="display:block"><li id="treeDemo_2" class="level1" tabindex="0" hidefocus="true" treenode=""><span id="treeDemo_2_switch" title="" class="button level1 switch center_close" treenode_switch=""></span><a id="treeDemo_2_a" class="level1" treenode_a="" onclick="" target="_blank" style="" title="pNode 11"><span id="treeDemo_2_ico" title="" treenode_ico="" class="button ico_close" style=""></span><span id="treeDemo_2_span" class="node_name">pNode 11</span></a></li><li id="treeDemo_7" class="level1" tabindex="0" hidefocus="true" treenode=""><span id="treeDemo_7_switch" title="" class="button level1 switch center_close" treenode_switch=""></span><a id="treeDemo_7_a" class="level1" treenode_a="" onclick="" target="_blank" style="" title="pNode 12"><span id="treeDemo_7_ico" title="" treenode_ico="" class="button ico_close" style=""></span><span id="treeDemo_7_span" class="node_name">pNode 12</span></a></li><li id="treeDemo_12" class="level1" tabindex="0" hidefocus="true" treenode=""><span id="treeDemo_12_switch" title="" class="button level1 switch bottom_close" treenode_switch=""></span><a id="treeDemo_12_a" class="level1" treenode_a="" onclick="" target="_blank" style="" title="pNode 13 - no child"><span id="treeDemo_12_ico" title="" treenode_ico="" class="button ico_close" style=""></span><span id="treeDemo_12_span" class="node_name">pNode 13 - no child</span></a></li></ul></li><li id="treeDemo_13" class="level0" tabindex="0" hidefocus="true" treenode=""><span id="treeDemo_13_switch" title="" class="button level0 switch center_close" treenode_switch=""></span><a id="treeDemo_13_a" class="level0" treenode_a="" onclick="" target="_blank" style="" title="pNode 02"><span id="treeDemo_13_ico" title="" treenode_ico="" class="button ico_close" style=""></span><span id="treeDemo_13_span" class="node_name">pNode 02</span></a></li><li id="treeDemo_29" class="level0" tabindex="0" hidefocus="true" treenode=""><span id="treeDemo_29_switch" title="" class="button level0 switch bottom_close" treenode_switch=""></span><a id="treeDemo_29_a" class="level0" treenode_a="" onclick="" target="_blank" style="" title="pNode 3 - no child"><span id="treeDemo_29_ico" title="" treenode_ico="" class="button ico_close" style=""></span><span id="treeDemo_29_span" class="node_name">pNode 3 - no child</span></a></li></ul>
</ul>

<script>
var toggler = document.getElementsByClassName("caret");
var i;

for (i = 0; i < toggler.length; i++) {
  toggler[i].addEventListener("click", function() {
    this.parentElement.querySelector(".nested").classList.toggle("active");
    this.classList.toggle("caret-down");
  });
}
</script>

</body>
</html>
