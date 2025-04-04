var setting = {
	data:{
			simpleData: {
				enable: true
			}
		},
	callback:{
				onClick: goToReportPage		
			}
};

$(document).ready(function(){
	var treeNodes = JSON.parse($("#treeNodes").val());
	if(treeNodes!='')
	{
		$.fn.zTree.init($("#reportsTree"), setting, treeNodes);
	}
});

function goToReportPage(event, treeId, treeNode){
	if(treeNode.isActive && !treeNode.isParent){
		$('#reportName').val(treeNode.name); 	
		$('#instanceName').val(getInstance(treeNode));
		$('#instanceCode').val(treeNode.instanceCode);
		$('#reportForm').submit();
	}
}

function getInstance(treeNode){
	var node = treeNode;
	/*while(node.getParentNode().name != "FA Tax Reports"){
		node = node.getParentNode();
	}*/
	return node.name;
}

function getInstanceCode(treeNode){
	var parentNodeName = "";
		if(treeNode.hasOwnProperty("pId"))
		{
			var pIdOfSelected = treeNode.pId;
			var data = JSON.parse(document.getElementById('treeNodes').value);
			var i = "";
			for (i = 0; i< data.length; i++) {
				if (data[i].id == pIdOfSelected) {
				parentNodeName = data[i].name;
	        }
	
		}
	}
	return parentNodeName;
}