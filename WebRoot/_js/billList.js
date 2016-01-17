$(document).ready(function() {
	var bill_mode = "none";
	// [none] for no bill selected
	// [view] for detail loaded
	// [edit] for edit
	// [new]  for new
	var isBriefEdited = false;
	var isDetailEdited = false;

	showTemplateTab();
	loadBills("todo");

	$('#addBill').click(function(){
		bill_new();
	});

	$("#billList").click(function(e){
		if(e.target == this && bill_mode == "view") {
			var tr = $('#billList div.tab-content div.tab-pane.active table tbody tr');
			if( tr.hasClass("selected") ) {
				tr.removeClass('selected');
			}
			if(!isDetailClear()) {
				clearDetail();
			}
		}
	});

	function showTemplateTab() {
		$.ajax({
			url: 'QueryAuthority',
			type: 'POST',
			dataType: 'text'
		})
		.done(function(response) { 
			if(response == 2) {
				$('#mainNav').append("<li><a href='template.jsp'>模板</a></li>");
			}
			console.info("[INFO] template tab added");
		})
		.fail(function() {
			console.error("[ERROR] error occured on getting authority");
		});
	}

	function loadBills(type){
		$.ajax({
			url: 'LoadBills',
			type: 'POST',
			dataType: 'json',
			data: {'type': type}
		})
		.done(function(response) {
			
			// basic structures
			$("#billList ul").html("");
			$("#billList div.tab-content").html("");

			var authority = response.authority;
			if(authority != 1) {
				$("#addBill").hide();
			} else {
				$("#addBill").show();
			}
			if(authority == 1 || authority == 2) {
				// Navigation Tab
				$("#billList ul.nav").append('<li><a id="todo" href="#todoPane" data-toggle="tab">待处理</a></li><li><a id="success" href="#successPane" data-toggle="tab">已完成/已反馈</a></li><li><a id="fail" href="#failPane" data-toggle="tab">已作废</a></li>');
				// bill list
				$("#billList div.tab-content").append('<div class="table-responsive tab-pane" id="todoPane"><table id="bills" class="table table-hover tablesorter"><thead></thead><tbody></tbody></table></div><div class="table-responsive tab-pane" id="successPane"><table id="billsSuccess" class="table table-hover tablesorter"><thead></thead><tbody></tbody></table></div><div class="table-responsive tab-pane" id="failPane"><table id="bills" class="table table-hover tablesorter"><thead></thead><tbody></tbody></table></div>');
			} else if (authority == 3) {
				$("#billList ul.nav").append('<li><a id="todo" href="#todoPane" data-toggle="tab">待处理</a></li><li><a id="success" href="#successPane" data-toggle="tab">已反馈</a></li><li><a id="fail" href="#failPane" data-toggle="tab">已驳回</a></li>');
				$("#billList div.tab-content").append('<div class="table-responsive tab-pane" id="todoPane"><table id="bills" class="table table-hover tablesorter"><thead></thead><tbody></tbody></table></div><div class="table-responsive tab-pane" id="successPane"><table id="billsSuccess" class="table table-hover tablesorter"><thead></thead><tbody></tbody></table></div><div class="table-responsive tab-pane" id="failPane"><table id="bills" class="table table-hover tablesorter"><thead></thead><tbody></tbody></table></div>');
			} else if (authority == 4){
				$("#billList ul.nav").append('<li><a id="todo" href="#todoPane" data-toggle="tab">待处理</a></li><li><a id="success" href="#successPane" data-toggle="tab">已完成</a></li>');
				$("#billList div.tab-content").append('<div class="table-responsive tab-pane" id="todoPane"><table id="bills" class="table table-hover tablesorter"><thead></thead><tbody></tbody></table></div><div class="table-responsive tab-pane" id="successPane"><table id="billsSuccess" class="table table-hover tablesorter"><thead></thead><tbody></tbody></table></div>');
			}

			if($("#billList ul.nav li").hasClass('active')) {
				$("#billList ul.nav li").removeClass('active');
			}	$("#billList ul.nav li a#"+type).parent().addClass('active');

			if($("#billList div.tab-content div.tab-pane").hasClass('active')) {
				$("#billList div.tab-content div.tab-pane").removeClass('active');
			}	$("#billList div.tab-content div.tab-pane#"+type+"Pane").addClass('active');

			$("#todo").click(function(e){
				e.preventDefault();
				$(this).tab('show');
				loadBills("todo");
			});
			
			$("#success").click(function(e){
				e.preventDefault();
				$(this).tab('show');
				loadBills("success");
			});
			
			$("#fail").click(function(e){
				e.preventDefault();
				$(this).tab('show');
				loadBills("fail");
			});

			if(response.success == true) {
				var array = response.data;
				
				if(response.authority == 1 || response.authority == 2) {
					$("#billList div.tab-content div.tab-pane.active table thead").append("<tr class='header'><th>定值单号</th><th class='header'>目标单位</th><th class='header'>制单人</th><th class='header'>最后编辑人</th><th class='header'>创建日期</th><th class='header'>最后编辑日期</th><th class='header'>状态</th></tr>");
					for(var i=0, len=array.length; i<len; i++) {
						$('#billList div.tab-content div.tab-pane.active table tbody').append(
							"<tr index='" + array[i].billCode 
							+ "'><td>" + array[i].billCode 
							+ "</td><td>" + array[i].locationToName 
							+ "</td><td>" + array[i].createrName 
							+ "</td><td>" + array[i].lastEditorName 
							+ "</td><td>" + array[i].createTime 
							+ "</td><td>" + array[i].lastEditTime 
							+ "</td><td>" + array[i].statusName 
							+ "</tr>" 
						);
					}
				} else if(response.authority == 3 || response.authority == 4) {
					$("#billList div.tab-content div.tab-pane.active table thead").append("<tr><th class='header'>定值单号</th><th class='header'>制单单位</th><th class='header'>制单人</th><th class='header'>最后编辑人</th><th class='header'>创建日期</th><th class='header'>最后编辑日期</th><th class='header'>状态</th></tr>");
					for(var i=0, len=array.length; i<len; i++) {
						$('#billList div.tab-content div.tab-pane.active table tbody').append(
							"<tr index='" + array[i].billCode 
							+ "'><td>" + array[i].billCode 
							+ "</td><td>" + array[i].locationFromName 
							+ "</td><td>" + array[i].createrName 
							+ "</td><td>" + array[i].lastEditorName 
							+ "</td><td>" + array[i].createTime 
							+ "</td><td>" + array[i].lastEditTime 
							+ "</td><td>" + array[i].statusName 
							+ "</tr>" 
						);
					}
				}
				$("#billList div.tab-content div.tab-pane.active table").tablesorter();
				
				// Click to load detail
				$("#billList div.tab-content div.tab-pane.active table tr[index]").click(function(e) {
					// console.debug( $(this).attr('index') );

					var tr = $("#billList div.tab-content table tr[index]");
					if( tr.hasClass("selected") ) {
						tr.removeClass('selected');
					} $(this).addClass('selected');

					var index = $(this).attr('index');
					loadDetails(index);
				});

				console.info("[INFO] " + array.length + " bills loaded");
			} else {
				$("#billList div.tab-content").append('<p class="errorInfo">没有发现任何定值单</p>');
				console.info("[INFO] bills not found");
			}
		})
		.fail(function() {
			if( $("#billList div.tab-content p.errorInfo") ) {
				$('#billList div.tab-content p.errorInfo').remove();
			}
			$("#billList div.tab-content").append('<p class="errorInfo">加载失败，请<a id="reloadBills" onclick="loadBills()">重试</a></p>');
			$("#reloadBills").css('cursor', 'pointer');
			$("#reloadBills").click(function(){
				loadBills(type);
			});
			console.error("[ERROR] error occur on loading bills");
		})
		.always(function() {
			if(!isDetailClear()) {
				clearDetail();
			}
		});
	}

	function loadDetails(index) {
		var confirmed = new Boolean();
		if(bill_mode == "new") {
			confirmed = confirm("放弃当前创建的定值单？");
		} else {
			confirmed = true;
		}

		if(confirmed) {
			$.ajax({
				url: 'LoadDetails',
				type: 'POST',
				dataType: 'json',
				data: {'billcode': index}
			})
			.done(function(response) {
				$('#preview').html("");
				$('#preview').append('<div id="option"></div>');
				$('#preview').append('<fieldset id="history"></fieldset>');
				$('#preview').append('<fieldset id="brief"></fieldset>');
				$('#preview').append('<fieldset id="detail"></fieldset>');

				if(response.success == true) {
					bill_mode = "view";
					console.info("[INFO] bill_mode: " + bill_mode);

					var basic_info = response.basic;
					
					$('#history').append('<legend>操作历史</legend><table></table>');
					var history = $('#history table');
					
					// Load history
					if( basic_info.createrName && basic_info.createTime ) {
						history.append('<tr><td>制单：</td><td>' + basic_info.createrName + '</td><td>' + basic_info.createTime + '</td></tr>');
					}
					if( basic_info.lastEditorName && basic_info.lastEditTime ) {
						history.append('<tr><td>最后编辑：</td><td>' + basic_info.lastEditorName + '</td><td>' + basic_info.lastEditTime + '</td></tr>');
					}
					if( basic_info.checkerName && basic_info.checkTime ) {
						history.append('<tr><td>校对：</td><td>' + basic_info.checkerName + '</td><td>' + basic_info.checkTime + '</td></tr>');
					}
					if( basic_info.auditorName && basic_info.auditTime && !basic_info.auditCancelRemark) {
						history.append('<tr><td>审核：</td><td>' + basic_info.auditorName + '</td><td>' + basic_info.auditTime + '</td></tr>');
					}
					if( basic_info.receiverName && basic_info.receiveTime ) {
						history.append('<tr><td>确认：</td><td>' + basic_info.receiverName + '</td><td>' + basic_info.receiveTime + '</td></tr>');
					}
					if( basic_info.executerName && basic_info.executeFeedbackTime ) {
						history.append('<tr><td>执行：</td><td>' + basic_info.executerName + '</td><td>' + basic_info.executeFeedbackTime + '</td></tr>');
					}

					// Load brief
					$('#brief').append("<legend>基本信息</legend><table class='table table-bordered table-condensed'></table>");
					$('#brief table').append("<tr><td>单号</td><td colspan='3'>" + basic_info.billCode
						+ "<tr><td>制单单位</td><td colspan='3'>" + basic_info.locationFromName
						+ "<tr><td>目标单位</td><td colspan='3'>" + basic_info.locationToName
						+ "</td></tr><tr><td colspan='1'>设备所属</td><td>" + basic_info.deviceBelongingName
						+ "</td><td>所在线路</td><td>" + basic_info.lineName
						+ "</td></tr><tr><td>装置名称</td><td>" + basic_info.deviceName
						+ "</td><td>装置型号</td><td>" + basic_info.deviceModelName
						+ "</td></tr><tr><td>备注</td><td colspan='3'>" + basic_info.remark
						+ "</td></tr><tr><td>模板</td><td colspan='3'>" + basic_info.templateName
						+ "</td></tr>"
						);
					
					// Load details
					$("#detail").append("<legend>定值详情</legend><table class='table table-bordered table-condensed'><thead><tr><th rowspan='2'>序号</th><th rowspan='2'>定值项</th><th colspan='2'>改变前</th><th colspan='2'>改变后</th></tr><tr><th>一次值</th><th>二次值</th><th>一次值</th><th>二次值</th></tr></thead><tbody></tbody></table>");
					var array = response.details;
					for(var i=0, len=array.length; i<len; i++) {
						$('#detail table tbody').append(
							"<tr><td>" + (i+1)
							+ "</td><td>" + array[i].name 
							+ "</td><td>" + array[i].before1
							+ "</td><td>" + array[i].before2 
							+ "</td><td>" + array[i].after1 
							+ "</td><td>" + array[i].after2 
							+ "</td></tr>"
						);
					}
					console.info("[INFO] "+ array.length +" detail loaded for bill " + basic_info.billCode);

					// Load option buttons
					var status = basic_info.status;
					var authority = response.authority;
					var billcode = basic_info.billCode;
					// alert("Billcode: " + billcode + " \nauthority: " + authority + " \nstatus: " + status);
					if(authority == 1 && (Math.abs(status) == 100 || status == -200 || status == -300)) {
						$('#option').append('<a class="btn btn-default option_left edit">编辑</a>');
						$('#option').append('<a class="btn btn-primary option_right check">核对</a>');
						$('#option').append('<a class="btn btn-danger option_right trash">作废</a>');
						$('a.edit').click(function(){bill_edit(billcode);});
						$('a.check').click(function(){bill_updateStatus(billcode, 'check');});
						$('a.trash').click(function(){bill_updateStatusWithRemark(billcode, 'trash', 'TrashRemark');});
					} else if((authority == 1 && status == 150) || (authority == 2 && status == 200)) {
						$('#option').append('<a class="btn btn-danger option_right recover">收回</a>');
						$('a.recover').click(function(){bill_updateStatus(billcode, 'recover');});
					} else if(authority == 2 && status == 150) {
						$('#option').append('<a class="btn btn-primary option_right pass">同意</a>');
						$('#option').append('<a class="btn btn-danger option_right refuse">拒绝</a>');
						$('a.pass').click(function(){bill_updateStatus(billcode, 'pass');});
						$('a.refuse').click(function(){bill_updateStatusWithRemark(billcode, 'refuse', 'AuditCancelRemark');});
					} else if(authority == 3 && status == 200) {
						$('#option').append('<a class="btn btn-primary option_right receive">接收</a>');
						$('#option').append('<a class="btn btn-danger option_right reject">驳回</a>');
						$('a.receive').click(function(){bill_updateStatusAndAssign(billcode, 'receive');});
						$('a.reject').click(function(){bill_updateStatusWithRemark(billcode, 'reject', 'ReceiveCancelRemark');});
					} else if(authority == 3 && status == 400) {
						$('#option').append('<a class="btn btn-primary option_right report">反馈</a>');
						$('a.report').click(function(){bill_updateStatusWithRemark(billcode, 'report', 'ReportFeedbackRemark');});
					} else if(authority == 4 && status == 300) {
						$('#option').append('<a class="btn btn-primary option_right finish">完成</a>');
						$('a.finish').click(function(){bill_updateStatusWithRemark(billcode, 'finish', 'ExecuteFeedbackRemark');});
					}

					/*
					待核对：100		待审核：150		已收回：-100		已作废：-500
					已审核：200		审核未通过：-200
					已接收：300		已反馈：500		被驳回：-300
					已完成：400
					
					*/
				} else {	// end of "response.success == true"
					if($("#preview p.errorInfo").length < 1) {
						$("#preview").prepend('<p class="errorInfo">获取定值单详情失败，请<a id="reloadDetail">重试</a></p>');
						$("#reloadDetail").css('cursor', 'pointer');
						$("#reloadDetail").click(function(){
							loadDetails(index);
						});
					}
					console.error("[ERROR] returned null JSONObject");
					bill_mode = "none";
					console.info("[INFO] bill_mode: " + bill_mode);
				}
			})
			.fail(function() {
				$('#preview').html("");
				$("#preview").append('<p class="errorInfo">获取定值单详情失败，请<a id="reloadDetail">重试</a></p>');
				$("#reloadDetail").css('cursor', 'pointer');
				$("#reloadDetail").click(function(){
					loadDetails(index);
				});
				console.error("[ERROR] no related detail found");
				bill_mode = "none";
				console.info("[INFO] bill_mode: " + bill_mode);
			});
		} // end of confirm(0)
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
		bill_mode = "none";
		console.info("[INFO] bill_mode: " + bill_mode);
	}

	function bill_edit(billcode) {
		$.ajax({
			url: 'LoadDetailsForEdit',
			type: 'POST',
			dataType: 'json',
			data: {billcode:billcode},
		})
		.done(function(response) {
			if(response.success == true) {
				bill_mode = "edit";
				console.info("[INFO] bill_mode: " + bill_mode);

				$('#option').html("");
				$("#option").append('<a class="btn btn-default option_left cancel">取消</a>');
				$("#option").append('<a class="btn btn-primary option_right save">保存</a>');
				$("#option").append('<a class="btn btn-default option_right import">从文件导入</a>');
				$("a.cancel").click(function() { loadDetails(billcode); });
				$("a.save").click(function() { 
					bill_saveEdit(billcode);
				});
				$("a.import").click(function() {
					popupDropzone();
				});
				
				$('#history').remove();

				// loading brief
				$('#brief').html("");
				$('#brief').append("<legend>基本信息</legend><table class='table table-condensed'></table>");
				$('#brief table').append("<tr><td style='text-align:right'>单号：</td><td colspan='3'><input id='inputBillCode' type='text' style='width:100%' disabled='disabled' value='" + response.brief.billcode + "' /></td></tr>"
					+ "<tr><td style='text-align:right'>制单单位：</td><td colspan='3'><input id='inputLocationFrom' type='text' style='width:100%' disabled='disabled' value='" + response.brief.locationFromName + "' /></td></tr>"
					+ "<tr><td style='text-align:right'>目标单位：</td><td colspan='3'><select style='width:100%' id='selectLocationTo'></select></td></tr>"
					+ "<tr><td style='text-align:right' colspan='1'>设备所属：</td><td><select style='width:100%' id='selectDeviceBelonging'></select></td>"
					+ "<td style='text-align:right' colspan='1'>所在线路：</td><td id='txtLine' lineID='" + response.lineID + "'>" + response.lineName + "</td></tr>"
					+ "<tr><td style='text-align:right'>装置名称：</td><td><select style='width:100%' id='selectDevice'></select></td>"
					+ "<td style='text-align:right'>装置型号：</td><td><select style='width:100%' id='selectModel'></select></td></tr>"
					+ "<tr><td style='text-align:right' class='edit_text'>备注：</td><td colspan='3'><input id='inputRemark' type='text' style='width:100%' value='" + response.brief.remark + "' /></td></tr>"
					+ "<tr><td style='text-align:right'>模板：</td><td colspan='3'><select id='template' disabled='disabled' style='width:100%'><option value='" + response.brief.templateCode + "'>" + response.brief.templateName + "</option></select></td></tr>"
					);

				$('#brief table tr td').css('border', 'none');
				$('#brief table tr td input:disabled').css('cursor', 'not-allowed');
				$('#brief table tr td select:disabled').css('cursor', 'not-allowed');
				
				// load options
				$.each(response.company, function(index, val) {
					$('#selectLocationTo').append("<option value='" + index + "'>" + val + "</option>'" );
				});
				$.each(response.devicebelonging, function(index, val) {
					$('#selectDeviceBelonging').append("<option value='" + index + "'>" + val + "</option>'" );
				});
				$.each(response.device, function(index, val) {
					$('#selectDevice').append("<option value='" + index + "'>" + val + "</option>'" );
				});
				$.each(response.model, function(index, val) {
					$('#selectModel').append("<option value='" + index + "'>" + val + "</option>'" );
				});

				// set delected option
				$('#selectLocationTo').val(response.brief.locationToID);
				$('#selectDeviceBelonging').val(response.brief.deviceBelongingID);
				$('#selectDevice').val(response.brief.deviceID);
				$('#selectModel').val(response.brief.deviceID);

				// handle onChange
				$('#selectLocationTo').change(function() {
					query_brief( 'locationTo', $('#selectLocationTo').val() );
					isBriefEdited = true;
				});
				$('#selectDeviceBelonging').change(function() {
					query_brief( 'deviceBelonging', $('#selectDeviceBelonging').val() );
					isBriefEdited = true;
				});
				$('#selectDevice').change(function() {
					query_brief( 'device', $('#selectDevice').find("option:selected").text() + "," + $('#selectDeviceBelonging').val() );
					isBriefEdited = true;
				});
				
				// loading details				
				$('#detail').html("");
				$("#detail").append("<legend>定值详情</legend><table class='table table-bordered table-condensed'><thead><tr><th rowspan='2'>序号</th><th rowspan='2'>定值项</th><th colspan='2'>改变前</th><th colspan='2'>改变后</th></tr><tr><th>一次值</th><th>二次值</th><th>一次值</th><th>二次值</th></tr></thead><tbody></tbody></table>");
				var array = response.details;
				for(var i=0, len=array.length; i<len; i++) {
					$('#detail table tbody').append(
						"<tr><td>" + (i+1) + "</td>"
						+ "<td>" + array[i].name + "</td>"
						+ "<td class='edit_text'><input type='text' style='width:100%' value='" + array[i].before1 + "' /></td>"
						+ "<td class='edit_text'><input type='text' style='width:100%' value='" + array[i].before2 + "' /></td>"
						+ "<td class='edit_text'><input type='text' style='width:100%' value='" + array[i].after1  + "' /></td>"
						+ "<td class='edit_text'><input type='text' style='width:100%' value='" + array[i].after2  + "' /></td></tr>"
					);
				}

				// handle onChange
				$('#detail table tbody tr td input').change(function(){
					var detail = $(this).parent().parent();
					if( !detail.hasClass('edited')) {
						// console.debug(detail.get(0).tagName);
						detail.addClass("edited");
					}
					isDetailEdited = true;
				});

				console.info("[INFO] "+ array.length +" detail loaded for bill " + response.brief.billcode);
			} else {
				console.err("[ERROR] error occured while loading detail for edit");
				bill_mode = "view";
				console.info("[INFO] bill_mode: " + bill_mode);
				alert("[ERROR] error occured while loading detail for edit");
			}
		})
		.fail(function() {
			console.err("[ERROR] error occured while loading detail for edit");
			bill_mode = "view";
			console.info("[INFO] bill_mode: " + bill_mode);
			alert("[ERROR] error occured while loading detail for edit");
		});
	}

	function bill_saveEdit(billcode) {
		var bill = {};
		console.debug("billcode: " + billcode);
		console.debug("isBriefEdited: " + isBriefEdited);

		if( isBriefEdited ) {
			bill.brief = {};
			bill.brief.locationTo = $('#selectLocationTo').val();
			bill.brief.devicebelonging = $('#selectDeviceBelonging').val();
			bill.brief.line = $('#txtLine').attr('lineID');
			bill.brief.device = $('#selectModel').val();	// val() of deviceID might be incorrect because of the GROUP BY prase. but model carries the correct one.
			bill.brief.remark = $('#inputRemark').val();
		}

		if( isDetailEdited ) {
			var detailEdited = $("tr.edited");
			// console.debug("length: " + detailEdited.length);
			var detailsArray = [];
			for(var i=0, len=detailEdited.length; i<len; i++) {
				var detail = {};
				// console.debug(i + "-1:" + detailEdited.get(i).getElementsByTagName("td")[1].innerText);
				// console.debug(i + "-2:" + detailEdited.get(i).getElementsByTagName("td")[2].getElementsByTagName("input")[0].value);
				// console.debug(i + "-3:" + detailEdited.get(i).getElementsByTagName("td")[3].getElementsByTagName("input")[0].value);
				// console.debug(i + "-4:" + detailEdited.get(i).getElementsByTagName("td")[4].getElementsByTagName("input")[0].value);
				// console.debug(i + "-5:" + detailEdited.get(i).getElementsByTagName("td")[5].getElementsByTagName("input")[0].value);
				detail.item = detailEdited.get(i).getElementsByTagName("td")[1].innerText;
				detail.before1 = detailEdited.get(i).getElementsByTagName("td")[2].getElementsByTagName("input")[0].value;
				detail.before2 = detailEdited.get(i).getElementsByTagName("td")[3].getElementsByTagName("input")[0].value;
				detail.after1  = detailEdited.get(i).getElementsByTagName("td")[4].getElementsByTagName("input")[0].value;
				detail.after2  = detailEdited.get(i).getElementsByTagName("td")[5].getElementsByTagName("input")[0].value;
				detailsArray.push(detail);
			}
			bill.details = detailsArray;
		}
		var data = JSON.stringify(bill);
		console.debug("json: " + data);

		if( isBriefEdited || isDetailEdited) {
			$.ajax({
				url: 'StoreBills',
				type: 'POST',
				dataType: 'text',
				data: {billcode:billcode
					, data:data
					, isBriefEdited:isBriefEdited
					, isDetailEdited:isDetailEdited
				}
			})
			.done(function(response) {
				console.debug("[response]: " + response);
				if(parseInt(response) >= 1) {
					isBriefEdited = false;
					isDetailEdited = false;
					console.info("[INFO] bill " + billcode + " saved");
					alert("保存成功");
					loadBills("todo");
					setTimeout(loadDetails(billcode), 500);
				} else {
					alert("保存失败，请重试");
					console.error("[ERROR] error occured while saving bill");
				}
			})
			.fail(function() {
				alert("保存失败，请重试");
				console.error("[ERROR] error occured while saving bill");
			});
		} else {
			alert("定值单没有修改，将返回预览");
			loadDetails(billcode);
		}
		

		// console.err("[ERROR] saving bill " + billcode + " failed");
	}

	function bill_saveNew() {
		var bill = {};
		bill.brief = {};
		bill.brief.locationTo = $('#selectLocationTo').val();
		bill.brief.devicebelonging = $('#selectDeviceBelonging').val();
		bill.brief.line = $('#txtLine').attr('lineID');
		bill.brief.device = $('#selectModel').val();	// val() of deviceID might be incorrect because of the GROUP BY prase. but model carries the correct one.
		bill.brief.remark = $('#inputRemark').val();

		var trs = $("#detail table tbody tr");
		// console.debug("length: " + detailEdited.length);
		var detailsArray = [];
		for(var i=0, len=trs.length; i<len; i++) {
			var detail = {};
			// console.debug(i + "-1:" + detailEdited.get(i).getElementsByTagName("td")[1].innerText);
			// console.debug(i + "-2:" + detailEdited.get(i).getElementsByTagName("td")[2].getElementsByTagName("input")[0].value);
			// console.debug(i + "-3:" + detailEdited.get(i).getElementsByTagName("td")[3].getElementsByTagName("input")[0].value);
			// console.debug(i + "-4:" + detailEdited.get(i).getElementsByTagName("td")[4].getElementsByTagName("input")[0].value);
			// console.debug(i + "-5:" + detailEdited.get(i).getElementsByTagName("td")[5].getElementsByTagName("input")[0].value);
			detail.item = trs.get(i).getElementsByTagName("td")[1].innerText;
			detail.before1 = trs.get(i).getElementsByTagName("td")[2].getElementsByTagName("input")[0].value;
			detail.before2 = trs.get(i).getElementsByTagName("td")[3].getElementsByTagName("input")[0].value;
			detail.after1  = trs.get(i).getElementsByTagName("td")[4].getElementsByTagName("input")[0].value;
			detail.after2  = trs.get(i).getElementsByTagName("td")[5].getElementsByTagName("input")[0].value;
			detailsArray.push(detail);
		}
		bill.details = detailsArray;
		var data = JSON.stringify(bill);
		// console.debug("json: " + data);

		$.ajax({
			url: 'StoreNewBill',
			type: 'POST',
			dataType: 'json',
			data: {data:data}
		})
		.done(function(response) {
			// console.debug("[response]: " + JSON.stringify(response));
			if( response.success == true) {
				console.info("[INFO] bill " + response.billcode + " saved");
				alert("保存成功， 定值单号：" + response.billcode);
				loadBills("todo");
				if(!isDetailClear) {
					clearDetail();
				}
			} else {
				alert("保存失败，请重试");
				console.error("[ERROR] error occured while saving bill");
			}
		})
		.fail(function() {
			alert("保存失败，请重试");
			console.error("[ERROR] error occured while saving bill");
		});
	}

	function bill_new() {
		var confirmed = new Boolean();
		if(bill_mode == "edit") {
			confirmed = confirm("放弃当前编辑？");
		} else {
			confirmed = true;
		}

		if(confirmed) {
			bill_mode = "new";
			console.info("[INFO] bill_mode: " + bill_mode);

			$('#preview').html("");
			$('#preview').append("<div id='option'><a class='btn btn-default option_left giveup'>放弃</a><a class='btn btn-primary option_right save'>保存</a><a class='btn btn-default option_right import'>从文件导入</a></div>");
			$('#preview').append("<fieldset id='brief'><legend>基本信息</legend><table class='table table-condensed'><tr><td style='text-align:right'>目标单位：</td><td colspan='3'><select style='width:100%' id='selectLocationTo'></select></td></tr><tr><td style='text-align:right' colspan='1'>设备所属：</td><td><select style='width:100%' id='selectDeviceBelonging'></select></td><td style='text-align:right' colspan='1'>所在线路：</td><td id='txtLine' lineID=''></td></tr><tr><td style='text-align:right'>装置名称：</td><td><select style='width:100%' id='selectDevice'></select></td><td style='text-align:right'>装置型号：</td><td><select style='width:100%' id='selectModel'></select></td></tr><tr><td style='text-align:right' class='edit_text'>备注：</td><td colspan='3'><input id='inputRemark' type='text' style='width:100%' placeholder='备注' /></td></tr><tr><td style='text-align:right'>模板：</td><td colspan='3'><select id='chooseTemplate' style='width:100%'></select></td></tr></table></fieldset>");
			$('#preview').append("<fieldset id='detail'><legend>定值详情</legend><table class='table table-condensed'><thead><tr><th rowspan='2'>序号</th><th rowspan='2'>定值项</th><th colspan='2'>改变前</th><th colspan='2'>改变后</th></tr><tr><th>一次值</th><th>二次值</th><th>一次值</th><th>二次值</th></tr></thead><tbody></tbody></table></fieldset>");
			$('#brief table tr td').css('border', 'none');
			// $("label[for='chooseTemplate']").css('margin-bottom', '20px');
			// $("#chooseTemplate").css('margin-bottom', '20px');
			// $('#detail table').css('margin-top', '20px');

			// load options
			$("a.giveup").click(function(e) {
				clearDetail();
			});
			$("a.save").click(function(e) { 
				bill_saveNew('insert');
			});
			$("a.import").click(function() {
				popupDropzone();
			});

			setTimeout(query_locationTo(), 500);
			
			// handle onChange
			$('#selectLocationTo').change(function() {
				query_brief( 'locationTo', $('#selectLocationTo').val() );
			});
			$('#selectDeviceBelonging').change(function() {
				query_brief( 'deviceBelonging', $('#selectDeviceBelonging').val() );
			});
			$('#selectDevice').change(function() {
				query_brief( 'device', $('#selectDevice').find("option:selected").text() + "," + $('#selectDeviceBelonging').val() );
			});
			
			setTimeout(query_template(), 500);
			$('#chooseTemplate').change(function() {
				var tcode = $('#chooseTemplate').val();
				bill_loadTemplate(tcode);
			});
		} // end of confirm()
	}

	function bill_updateStatus(billcode, action) {
		// console.debug("billcode: " + billcode);
		// console.debug("action: " + action);
		
		var actioned = "";
		var actioning = "";
		if(action.substring(action.length-1) == "e") {
			actioned = action + "d";
			actioning = action.substring(0, action.length-1) + "ing";
		} else { 
			actioned = action + "ed";
			actioning = action + "ing";
		}

		$.ajax({
			url: 'UpdateBillStatus',
			type: 'POST',
			dataType: 'text',
			data: {billcode:billcode, action:action}
		})
		.done(function(response) {
			if(parseInt(response) >= 1) {
				// console.debug("response: " + parseInt(response));
				console.info("[INFO] bill " + billcode + " " + actioned);
				alert("操作成功");
			} else {
				console.error("[ERROR] error occured on " + actioning + " bill '" + billcode + "'. response: " + response);
				alert("操作失败");
			}
		})
		.fail(function() {
			console.error("[ERROR] fail to " + action + " bill. " + billcode + "response: " + response);
			alert("操作失败");
		})
		.always(function() {
			loadBills("todo");
			setTimeout(function(){
				// console.debug( "len:" + $("#billList div.tab-content div.active table tbody tr[index = '" + billcode + "']").length );
				if($("#billList div.tab-content div.tab-pane.active table tbody tr[index = '" + billcode + "']").length > 0) {
					$("#billList div.tab-content div.tab-pane.active table tbody tr[index = '" + billcode + "']").addClass('selected');
					loadDetails(billcode);
					bill_mode = "view";
					console.info("[INFO] bill_mode: " + bill_mode);
				} else {
					bill_mode = "none";
					console.info("[INFO] bill_mode: " + bill_mode);
				}
			}, 500);
		});
	}

	function bill_updateStatusWithRemark(billcode, action, remarkType) {
		// console.debug("billcode: " + billcode);
		// console.debug("action: " + action);
		// console.debug("remarkType: " + remarkType);
		var remark = prompt(remarkType);
		// console.debug("remark: " + remark);

		var actioned = "";
		var actioning = "";
		if(action.substring(action.length-1) == "e") {
			actioned = action + "d";
			actioning = action.substring(0, action.length-1) + "ing";
		} else { 
			actioned = action + "ed";
			actioning = action + "ing";
		}

		if(remark != null) {
			$.ajax({
				url: 'UpdateBillStatus',
				type: 'POST',
				dataType: 'text',
				data: {billcode:billcode, action:action, remarkType:remarkType, remark:remark}
			})
			.done(function(response) {
				if(parseInt(response) >= 1) {
					// console.debug("response: " + parseInt(response));
					console.info("[INFO] bill " + billcode + " " + actioned + " with " + remarkType + ": \"" + remark + "\"");
					alert("操作成功");
				} else {
					console.error("[ERROR] error occured on " + actioning + " bill '" + billcode + "'. response: " + response);
					alert("操作失败");
				}
			})
			.fail(function() {
				console.error("[ERROR] fail to " + action + " bill. " + billcode + "response: " + response);
				alert("操作失败");
			})
			.always(function() {
				loadBills("todo");
				setTimeout(function(){
					// console.debug( "len:" + $("#billList div.tab-content div.active table tbody tr[index = '" + billcode + "']").length );
					if($("#billList div.tab-content div.tab-pane.active table tbody tr[index = '" + billcode + "']").length > 0) {
						$("#billList div.tab-content div.tab-pane.active table tbody tr[index = '" + billcode + "']").addClass('selected');
						loadDetails(billcode);
						bill_mode = "view";
						console.info("[INFO] bill_mode: " + bill_mode);
					} else {
						bill_mode = "none";
						console.info("[INFO] bill_mode: " + bill_mode);
					}
				}, 500);
			});
		}
	}

	function bill_updateStatusAndAssign(billcode, action) {
		// console.debug("billcode: " + billcode);
		// console.debug("action: " + action);
		var assignTo = prompt("AssignTo(ex.4)");	// TODO: definately gonna customize this, use dropdown list
		// console.debug("assignTo: " + assignTo);
		
		var actioned = "";
		var actioning = "";
		if(action.substring(action.length-1) == "e") {
			actioned = action + "d";
			actioning = action.substring(0, action.length-1) + "ing";
		} else { 
			actioned = action + "ed";
			actioning = action + "ing";
		}

		if(assignTo != null) {
			$.ajax({
				url: 'UpdateBillStatus',
				type: 'POST',
				dataType: 'text',
				data: {billcode:billcode, action:action, assignTo:assignTo}
			})
			.done(function(response) {
				if(parseInt(response) >= 1) {
					// console.debug("response: " + parseInt(response));
					console.info("[INFO] bill " + billcode + " " + actioned + ", and has been assigned to user " + assignTo);
					alert("操作成功");
				} else {
					console.error("[ERROR] error occured on " + actioning + " bill '" + billcode + "'. response: " + response);
					alert("操作失败");
				}
			})
			.fail(function() {
				console.error("[ERROR] fail to " + action + " bill. " + billcode + "response: " + response);
				alert("操作失败");
			})
			.always(function() {
				loadBills("todo");
				setTimeout(function(){
					// console.debug( "len:" + $("#billList div.tab-content div.active table tbody tr[index = '" + billcode + "']").length );
					if($("#billList div.tab-content div.tab-pane.active table tbody tr[index = '" + billcode + "']").length > 0) {
						$("#billList div.tab-content div.tab-pane.active table tbody tr[index = '" + billcode + "']").addClass('selected');
						loadDetails(billcode);
						bill_mode = "view";
						console.info("[INFO] bill_mode: " + bill_mode);
					} else {
						bill_mode = "none";
						console.info("[INFO] bill_mode: " + bill_mode);
					}
				}, 500);
			});
		}
	}

	function query_locationTo() {
		$.ajax({
			url: 'QueryLocationTo',
			type: 'POST',
			dataType: 'json'
		})
		.done(function(response) {
			$.each(response.locationto, function(index, val) {
				$('#selectLocationTo').append("<option value='" + index + "'>" + val + "</option>'" );
			});
			query_brief( 'locationTo', $('#selectLocationTo').val() );
		})
		.fail(function() {
			console.error("[ERROR] error occur while loading locationTo options");
		});
	}

	function bill_loadTemplate(tcode) {
		$.ajax({
			url: 'LoadTemplateDetails',
			type: 'POST',
			dataType: 'json',
			data: {'tcode': tcode}
		})
		.done(function(response) {
			$('#detail table tbody').html("");
			var array = response.details;
			for(var i=0, len=array.length; i<len; i++) {
				$('#detail table tbody').append( "<tr><td>" + (i+1) + "</td><td>" + array[i].name + "</td><td><input type='text' style='width:100%' /></td><td><input type='text' style='width:100%' /></td><td><input type='text' style='width:100%' /></td><td><input type='text' style='width:100%' /></td></tr>" );
			}
			console.info("[INFO] "+ array.length +" detail loaded for template " + tcode);
		})
		.fail(function() {
			console.error("[ERROR] no related detail found");
		});
	}

	function query_brief(trigger, val) {
		// console.debug("trigger: " + trigger);
		// console.debug("val: " + val);

		$.ajax({
			url: 'QueryBrief',
			type: 'POST',
			dataType: 'json',
			data: {trigger:trigger, val:val}
		})
		.done(function(response) {
			if(trigger == "locationTo") {
				$('#selectDeviceBelonging').html("");
				$.each(response.devicebelonging, function(index, val) {
					$('#selectDeviceBelonging').append("<option value='" + index + "'>" + val + "</option>'" );
				});

				query_brief( 'deviceBelonging', $('#selectDeviceBelonging').val() );
			} else if (trigger == 'deviceBelonging'){
				$('#selectDevice').html("");
				$.each(response.device, function(index, val) {
					$('#selectDevice').append("<option value='" + index + "'>" + val + "</option>'" );
				});
				$('#txtLine').attr('lineID', response.line.LineID);
				$('#txtLine').html(response.line.LineName);
				
				query_brief( 'device', $('#selectDevice').find("option:selected").text() + "," + $('#selectDeviceBelonging').val() );
			} else if(trigger == 'device') {
				$('#selectModel').html("");
				$.each(response.model, function(index, val) {
					$('#selectModel').append("<option value='" + index + "'>" + val + "</option>'" );
				});
			}
			console.info("[INFO] " + trigger + " updated");
		})
		.fail(function() {
			console.error("[ERROR] error occur while querying " + trigger);
		});
	}
	
	function query_template() {
		$.ajax({
			url: 'QueryTemplate',
			type: 'POST',
			dataType: 'json'
		})
		.done(function(response) {
			$.each(response.template, function(index, val) {
				$('#chooseTemplate').append("<option value='" + index + "'>" + val + "</option>'" );
			});

			var tcode = $('#chooseTemplate').val();
			bill_loadTemplate(tcode);
		})
		.fail(function() {
			console.error("[ERROR] error occur while loading locationTo options");
		});
	}

	function popupDropzone() {
		$("body").append("<div id='shadow'><div id='popup'><div id='dropzone'>拖拽文件到这里</div><a class='closePopup'>关闭</a></div></div>");
		
		var ww = $(window).width();
		var wh = $(window).height();
		$("#shadow").css("width", ww+"px" );
		$("#shadow").css("height", wh+"px" );
		var left = ( ww - 320 ) / 2;
		var top = (wh - 320 ) / 2;
		// console.debug(ww + " " + wh + " " + left);
		$("#popup").css('left', left+'px');
		$("#popup").css('top', top+'px');
		
		$(window).resize(function(){
			var ww = $(window).width();
			var wh = $(window).height();
			$("#shadow").css("width", ww+"px" );
			$("#shadow").css("height", wh+"px" );
			var left = ( ww - 320 ) / 2;
			var top = (wh - 320 ) / 2;
			// console.debug(ww + " " + wh + " " + left);
			$("#popup").css('left', left+'px');
			$("#popup").css('top', top+'px');
		});

		$("a.closePopup").click(function(){
			closePopup();
		});

		// drag to load file
		var dropzone = document.getElementById("dropzone");

		dropzone.addEventListener('dragenter', function(e){
			// console.debug("dragenter");
			$("#dropzone").text("松开鼠标");
			$("#popup").css("border-color", "#00D42E");
		}, false);

		dropzone.addEventListener('dragleave', function(e){
			// console.debug("dragleave");
			$("#dropzone").text("拖拽文件到这里");
			$("#popup").css("border-color", "#EEE");
		}, false);
		
		dropzone.addEventListener('dragover', function(e){
			// console.debug("dragover");
			e.preventDefault();
		}, false);


		dropzone.addEventListener('drop', function(e){
			// console.debug("drop");
			var fileList = e.dataTransfer.files;
			// console.debug("fileList.length: " + fileList.length);
			// console.debug("fileList[0].type: " + fileList[0].type);
			
			if(fileList.length == 0) { return; };
			if(fileList[0].type.indexOf('ms-excel') === -1) { alert("文件格式错误，仅支持csv"); return;}
			var reader = new FileReader();
			reader.readAsText(fileList[0]);
			reader.onload = function(f){
				// console.debug("file content: " + this.result);
				$("#dropzone").text("文件读取成功");
				$("#popup").css("border-color", "#00B24D");
				var contentArray = this.result.split("\r\n");
				for(var i=0, len=contentArray.length; i<len; i++) {
					console.debug("contentArray[" + i + "]: " + contentArray[i]);
				}

				// console.debug("[Comparing]"+contentArray[0] + " with " + $("#chooseTemplate").val());

				if( contentArray[0] == $("#chooseTemplate").val() ) {
					setTimeout(closePopup(), 2000);
					for(var i=1, len=contentArray.length; i<len; i++) {
						var detailArray = contentArray[i].split(",");
						var selector = "#detail table tbody tr:nth-child(" + i +") td:nth-child(3)";
						$(selector).children().val(detailArray[0]);
						$(selector).next().children().val(detailArray[1]);
						$(selector).next().next().children().val(detailArray[2]);
						$(selector).next().next().next().children().val(detailArray[3]);
					}
				} else {
					$("#dropzone").text("模板不匹配");
					$("#popup").css("border-color", "#F00");
				}
			};
			e.preventDefault();
		}, false);
	}

	function closePopup() {
		$("#popup").remove();
		$("#shadow").remove();
	}





});

