$(document).ready(function() {
	var template_mode = "none";
	// [none] for no bill selected
	// [view] for detail loaded
	// [new] for create template
	
	loadTemplates();

	$('#addTemplate').click(function(){
		template_new();
	});

	$("#templateList").click(function(e){
		if(e.target == this && template_mode == "view") {
			var tr = $('#templates tbody tr');
			if( tr.hasClass("selected") ) {
				tr.removeClass('selected');
			}
			if(!isDetailClear()) {
				clearDetail();
			}
		}
	});

	function loadTemplates(){
		$.ajax({
			url: 'LoadTemplates',
			type: 'POST',
			dataType: 'json'
		})
		.done(function(response) {
			
			$("#templates tbody").html("");

			if(response.success == true) {
				var array = response.data;				
				for(var i=0, len=array.length; i<len; i++) {
					$('#templates tbody').append(
						"<tr status='" + array[i].status 
						+ "'><td>" + array[i].templateCode 
						+ "</td><td>" + array[i].templateName 
						+ "</td><td>" + array[i].createrName 
						+ "</td><td>" + array[i].createTime 
						+ "</td><td>" + array[i].statusName
						+ "</td><td></td></tr>" 
					);
				}

				// $('#templates tbody tr[status="-1"]').css({'background-color':'#EEE', 'color':'#CCC'});

				$('#templates tbody tr[status="-1"] td:nth-child(6)').append("<a class='view'>查看</a><a class='enable'>启用</a><a class='delete'>删除</a>");
				$('#templates tbody tr[status="1"] td:nth-child(6)').append("<a class='view'>查看</a><a class='disable'>禁用</a><a class='delete'>删除</a>");
				$('#templates tbody tr td:nth-child(6) a').css("cursor", "pointer");
				$('a.view').css('margin-right', '1em');
				$('a.delete').css('margin-left', '1em');
				
				$('a.view').click(function(e){
					var tcode = $(this).parent().prev().prev().prev().prev().prev().text();
					// console.debug("tcode: " + tcode );

					if( $("#templates tbody tr").hasClass("selected") ) {
						$("#templates tbody tr").removeClass('selected');
					} $(this).parent().parent().addClass('selected');

					loadTemplateDetails(tcode);
				});
				$('a.enable').click(function(e){
					var tcode = $(this).parent().prev().prev().prev().prev().prev().text();
					template_update(tcode, '1');
				});
				$('a.disable').click(function(e){
					var tcode = $(this).parent().prev().prev().prev().prev().prev().text();
					template_update(tcode, '-1');
				});
				$('a.delete').click(function(e){
					var tcode = $(this).parent().prev().prev().prev().prev().prev().text();
					template_update(tcode, '-2');
				});

				$("#templates").tablesorter( {
					headers:{ 5:{ sorter:false } }
				});
				
				// Click to load detail
				/*$("#templates tbody tr").click(function(e) {
					// console.debug( $(this).attr('index') );
					if( $("#templates tbody tr").hasClass("selected") ) {
						$("#templates tbody tr").removeClass('selected');
					} $(this).addClass('selected');

					var tcode = $(this).find('td:first-child').val();
					loadTemplateDetails(tcode);
				});*/

				console.info("[INFO] " + array.length + " templates loaded");
			} else {
				$("#templates").append('<p class="errorInfo">没有发现任何模板</p>');
				console.info("[INFO] templates not found");
			}
		})
		.fail(function() {
			if( $("#templates p.errorInfo") ) {
				$('#templates p.errorInfo').remove();
			}
			$("#templates").append('<p class="errorInfo">加载失败，请<a id="reloadTemplates" onclick="loadBills()">重试</a></p>');
			$("#reloadTemplates").css('cursor', 'pointer');
			$("#reloadTemplates").click(function(){
				loadTemplates();
			});
			console.error("[ERROR] error occur on loading templates");
		})
		.always(function() {
			if(!isDetailClear()) {
				clearDetail();
			}
		});
	}

	function loadTemplateDetails(tcode) {
		$.ajax({
			url: 'LoadTemplateDetails',
			type: 'POST',
			dataType: 'json',
			data: {'tcode': tcode}
		})
		.done(function(response) {
			$('#preview').html("");

			if(response.success == true) {
				template_mode = "view";
				console.info("[INFO] template_mode: " + template_mode);

				$('#preview').append("<fieldset id='detail'><legend>模板详情</legend><table class='table table-bordered table-condensed'><thead><tr><th>序号</th><th>定值项名称</th></thead><tbody></tbody></table></fieldset>");
				// Load details
				var array = response.details;
				for(var i=0, len=array.length; i<len; i++) {
					$('#detail table tbody').append( "<tr><td>" + (i+1) + "</td><td>" + array[i].name + "</td></tr>" );
				}
				console.info("[INFO] "+ array.length +" detail loaded for template " + tcode);

			} else {	// end of "response.success == true"
				if($("#preview p.errorInfo").length < 1) {
					$("#preview").prepend('<p class="errorInfo">获取定值单详情失败，请<a id="reloadDetail">重试</a></p>');
					$("#reloadDetail").css('cursor', 'pointer');
					$("#reloadDetail").click(function(){
						loadTemplateDetails(tcode);
					});
				}
				console.error("[ERROR] returned null JSONObject");
				template_mode = "none";
				console.info("[INFO] template_mode: " + template_mode);
			}
		})
		.fail(function() {
			$('#preview').html("");
			$("#preview").append('<p class="errorInfo">获取定值单详情失败，请<a id="reloadDetail">重试</a></p>');
			$("#reloadDetail").css('cursor', 'pointer');
			$("#reloadDetail").click(function(){
				loadTemplateDetails(tcode);
			});
			console.error("[ERROR] no related detail found");
			template_mode = "none";
			console.info("[INFO] template_mode: " + template_mode);
		});
	}

	function isDetailClear() {
		return ( $('#preview_placeholder').length > 0 );
	}

	function clearDetail() {
		$('#preview').html("");
		if($("#preview_placeholder").length < 1) {
			$("#preview").prepend('<p id="preview_placeholder">请从左边列表中选择一张定值单</p>');
		}
		console.info("[INFO] detail clear");
		template_mode = "none";
		console.info("[INFO] template_mode: " + template_mode);
	}

	function template_update(tcode, status) {
		// console.debug("tcode: " + tcode);
		// console.debug("status: " + status);
		
		$.ajax({
			url: 'UpdateTemplateStatus',
			type: 'POST',
			dataType: 'text',
			data: {tcode:tcode, status:status}
		})
		.done(function(response) {
			if(parseInt(response) >= 1) {
				// console.debug("response: " + parseInt(response));
				console.info("[INFO] template status " + tcode);
				alert("操作成功");
			} else {
				console.error("[ERROR] error occured on updating status of template " + tcode + ". response: " + response);
				alert("操作失败");
			}
		})
		.fail(function() {
			console.error("[ERROR] fail to update status of template " + tcode + ". response: " + response);
			alert("操作失败");
		})
		.always(function() {
			loadTemplates();
			setTimeout(function(){
				// console.debug( "len:" + $("#templateList div.tab-content div.active table tbody tr[index = '" + billcode + "']").length );
				if($("#templates tbody tr td[status = '" + tcode + "']").length > 0) {
					$("#templates tbody tr td[status = '" + tcode + "']").addClass('selected');
					loadTemplateDetails(tcode);
					template_mode = "view";
					console.info("[INFO] template_mode: " + template_mode);
				} else {
					template_mode = "none";
					console.info("[INFO] template_mode: " + template_mode);
				}
			}, 500);
		});
	}

	function template_new() {
		template_mode = "new";
		console.info("[INFO] creating a new template");

		var tr = $('#templates tbody tr');
		if( tr.hasClass("selected") ) {
			tr.removeClass('selected');
		}

		$('#preview').html("<div class='options_top'><a class='btn btn-default giveup'>放弃</a><a class='btn btn-primary save'>保存</a></div><table id='newTemplate' class='table table-condensed'><thead><tr><th>名称：</th><th><input id='tname' type='text' conflict='true' /></th><th><span class='validateInfo'></span></th></tr><tr><th>序号</th><th>定值项名称</th><th>操作</th></tr></thead><tbody><tr><td>1</td><td><input type='text' /></td><td><a class='remove'>删除该行</a><a class='insertBefore'>插入一行</a></td></tr></tbody></table><a class='add'>添加一行</a>");
		
		$('a.add').click(function(e){
			var curRows = $('#newTemplate tbody tr').length;
			$('#newTemplate tbody').append("<tr><td>" + (curRows+1) + "</td><td><input type='text' /></td><td><a class='remove'>删除该行</a><a class='insertBefore'>插入一行</a></td></tr>");
			console.info("[INFO] " + (curRows+1) + " rows in the form");
		});

		$('#newTemplate').delegate("a.remove", "click", function(e){
			if($('#newTemplate tbody tr').length > 1) {
				$(this).parent().parent().remove();
				var curRows = $('#newTemplate tbody tr').length;
				for(var i=1; i<=curRows; i++) {
					var selector = "#newTemplate tbody tr:nth-child("+i+") td:first-child";
					var txt = i + "";
					$(selector).text(txt);
				}
			} else {
				alert("只剩一行了，不能再减了");
			}
			console.info("[INFO] " + $('#newTemplate tbody tr').length + " rows in the form");
		});

		$('#newTemplate').delegate("a.insertBefore", "click", function(e){
			var num = $(this).parent().prev().prev().text();
			var insertPoint = "#newTemplate tbody tr:nth-child(" + num + ")";
			$("<tr><td></td><td><input type='text' /></td><td><a class='remove'>删除该行</a><a class='insertBefore'>插入一行</a></td></tr>").insertBefore(insertPoint);
			
			var curRows = $('#newTemplate tbody tr').length;
			for(var i=1; i<=curRows; i++) {
				var selector = "#newTemplate tbody tr:nth-child("+i+") td:first-child";
				var txt = i + "";
				$(selector).text(txt);
			}
			console.info("[INFO] " + $('#newTemplate tbody tr').length + " rows in the form");
		});

		$('a.giveup').click(function(e){
			clearDetail();
		});

		$('a.save').click(function(e){
			var tname = $('#tname').val();
			// console.debug("tname:" + tname);
			template_validateTname(tname);
			setTimeout(function(){
				var isConflict = $('#tname').attr('conflict');
				// console.debug("isConflict=" + isConflict);
				if( isConflict == "false" ) {
					console.info("[INFO] saving template (tname:" + tname + ")");
					var template = {};
					template.tname = tname;
					var itemsArray = [];
					var rows = $('#newTemplate tbody tr');
					for(var i=0, len = rows.length; i<len; i++) {
						var item = {};
						item.name = rows.get(i).getElementsByTagName("td")[1].getElementsByTagName("input")[0].value;
						itemsArray.push(item);
					}
					template.items = itemsArray;
					
					var data = JSON.stringify(template);
					console.debug("json: " + data);

					$.ajax({
						url: 'StoreTemplates',
						type: 'POST',
						dataType: 'json',
						data: {data: data}
					})
					.done(function(response) {
						if(response.success == true) {
							console.info("[INFO] template " + response.tcode + " saved");
							alert("保存成功,模板编号为：" + response.tcode);
							clearDetail();
							loadTemplates();
						} else {
							console.info("[INFO] error occur on saving template");
							alert("保存失败");
						}
					})
					.fail(function() {
						console.info("[INFO] error occur on saving template");
						alert("保存失败");
					});
				} else {
					console.error("[ERROR] tname conflict. submittion abort");
				}
			}, 500);
		});

		$('#tname').blur(function(){
			var tname = $('#tname').val();
			var isTnameOK = false;
			template_validateTname(tname, isTnameOK);
		});
	}

	function template_validateTname(tname) {
		// console.debug("template_validateTname('"+tname+"') called");
		// var isTnameOK = false;
		$.ajax({
			url: 'ValidateTname',
			type: 'POST',
			dataType: 'text',
			data: {tname: tname},
		})
		.done(function(response) {
			// console.debug("response: " + response);
			if(parseInt(response)==0) {
				$('.validateInfo').text("");
				$('.validateInfo').css({'background-color':'none'});
				console.info("[INFO] tname not conflict");
				$('#tname').attr('conflict', 'false');
			} else if(parseInt(response)>0) {
				$('.validateInfo').text("该名称已存在");
				$('.validateInfo').css({'background-color':'#c12e2a', 'color':'#FFF'});
				console.info("[INFO] tname conflict");
				$('#tname').attr('conflict', 'true');
			} else {
				console.error("[ERROR] error occured while validating tname");
				$('#tname').attr('conflict', 'true');
			}
		})
		.fail(function() {
			console.error("[ERROR] error occured while validating tname");
			$('#tname').attr('conflict', 'true');
		});
		// console.debug("validation result: conflict=" + $('#tname').attr('conflict'));
	}
	
});

