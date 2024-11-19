var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var app = $("meta[name='_app']").attr("content");

// Get table list
function get_table_list(div_id, url) {
	// div_id is container div tag to appear table
	var myTableDiv = document.getElementById(div_id);
	var table='<table  class="table table-sm table-hover table-striped">';
	var tHead = "<thead/><tr/>";
	var tBody = "<tbody/>";
  
	$.get(url, function (json) {		
		 $.each(json, function (i, rows) {		
		    tBody += "<tr>";
		    
		   $.each(rows, function (j, cols) {			  
		       if (i === 0) {
		    	   tHead += "<th>" + j + "</th>";
		    	   //tHead += "<th>" + Object.keys(rows[0])[j] + "</th>";
		       }
			   tBody += "<th>" + cols + "</th>";
		    }); 
		    
		    tBody += "</tr>";
		});
		
		table+=tHead+tBody+'</table>';
		$(myTableDiv).html(table);
		 
		//console.log(tHead);  
	});
}
//Get search dropdown list while input search text
function get_search_list(select_class,url){
	$(select_class).select2({
        ajax: {
            url: function (params) {
                return url + params.term;
            },
            cache: false,
            processResults: function (data) {
                var result = $.map(data, function (item) { return { id: item.Code, text: item.Name } });
                return { results: result }
            }
        },
        placeholder: 'Search...',
        minimumInputLength: 1,
    });	
}
// for dropdown function to get json data
function get_name_code(url, from, to) {

  var _to = $('#' + to);
  _to.empty();

  var id = document.getElementById(from).value;
  $.get(app+url + id, function (json) {

    _to.append('<option></option>');

    $.each(json, function (i, v) {
      _to.append('<option value="' + v.code + '">' + v.name + '</option>');
    });
  });
}

function GoToUrl(url) {
  // alert(url);
  // var str = window.location.href;
  // if (str.substr(str.length - 1) === '/') {
  //     window.location.href = str  + url.substr(1);
  // } else {
  //     window.location.href += url;
  // }
  window.location.href = url;
}

function concateSTR(target, source) {
  var str = '';
  $.each(source, function (i, v) {
    str += ' ' + $('#' + v).val();
  });
  $('#' + target).val(str);
}


// datatable js
$(document).ready(function () {
  $('.easy_table').DataTable({
    "info": false
  });
});

// display message for 5 second than animate hide
$(".delay5000").show().delay(5000).fadeOut();

// for tree view 
$(document).ready(function () {
  var toggler = document.getElementsByClassName("caret");
  var i;

  for (i = 0; i < toggler.length; i++) {
    toggler[i].addEventListener("click", function () {
      this.parentElement.querySelector(".nested").classList.toggle("active");
      this.classList.toggle("caret-down");
    });
  }
});

// overide default scrill
// $(document).ready(function () {
// $('.lhSlimScroll').slimScroll({
//     size: '8px',
//     width: '100%',
//     height: '100%',
//     color: '#ffffff',
//     allowPageScroll: true,
//     alwaysVisible: true
// });
// });

// filter Ul Li in main screen
function filter(inputSelector, dataSelector) {
  let input = document.getElementById(inputSelector);
  let filter = input.value.toUpperCase();
  let ul = document.getElementById(dataSelector);
  let items = ul.getElementsByTagName('li');

  // Treffer merken
  let matches = [];

  // Suchlauf: Treffer ermitteln, DOM-Elemente zeigen/verstecken
  for (let i = 0; i < items.length; i++) {
    currentItem = items[i];

    txtValue = currentItem.textContent || currentItem.innerText || '';

    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      currentItem.style.display = '';
      matches.push(currentItem);
    } else {
      currentItem.style.display = 'none';
    }
  }

  // Trefferlauf: Alle getroffenen Elemente ablaufen und deren Kinder prüfen
  for (let i = 0; i < matches.length; i++) {
    let childListItemsOfMatch = matches[i].getElementsByTagName('li');

    // Ermitteln, ob der Treffer ggf. bei einem Kind lag
    let allChildrenInvisible = true;
    for (let k = 0; k < childListItemsOfMatch.length; k++) {
      if (childListItemsOfMatch[k].style.display == '') {
        allChildrenInvisible = false;
        break;
      }
    }

    // Treffer lag beim Parent, also alle Kinder anzeigen
    if (allChildrenInvisible) {
      for (let k = 0; k < childListItemsOfMatch.length; k++) {
        childListItemsOfMatch[k].style.display = '';
      }
    }
  }
}

function yyyyMMdd(date) {
  var d = new Date(date),
    month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();

  if (month.length < 2)
    month = '0' + month;
  if (day.length < 2)
    day = '0' + day;

  return [year, month, day].join('-');
}

function dynamic_table_json(tbl_class, json_data) {
  var myTableDiv = document.getElementById("tbl_emp_leave");
  //var myTableDiv = document.getElementById(tbl_class);
  var tHead = "<thead><tr>";
  var tBody = "<tbody>";

  $.each(json_data, function (i, v) {
    tBody += "<tr>";
    $.each(v, function (ii, vv) {

//       if (ii === 0) {
//       var keys = Object.keys(vv);
//       tHead += "<th>" + String(keys[ii]) + "</th>";
//       }
      tBody += "<td>" + vv + "</td>";

    });
    tBody += "</tr>";
  });
  tHead += "</tr></thead>";
  tBody += "</thead>";
//  $(myTableDiv + " thead").html(tHead);
  $(myTableDiv + " tbody").html(tBody);
  console.log(tHead);
}

//dashboard
//create card html
function createCard(jdashboard) {
  $(jdashboard).each(function (i, v) {
    $(".Dashboard").append("<div class=\" col-md-6\"> <div class=\"card\"><div class=\"card-header\">"+v.name+"</div><div class=\"card-body\"> <canvas id=" + v.code + "></canvas></div></div></div>"
    );
    loadCardData(v);
  });
}
//load data to card

function loadCardData(dashboard) {

  var labels = dashboard.dashboardData.map(function (e) {
    return e.name;
  });
  var datas = dashboard.dashboardData.map(function (e) {
    return e.value;
  });
  var rgbas = dashboard.dashboardData.map(function (e) {
    return random_rgba();
  });


  
  new Chart(document.getElementById(dashboard.code).getContext('2d'), {
    type: dashboard.chartType,
    data: {
      labels: labels,
      datasets: [{
        labels: labels,
        data: datas,
        backgroundColor: rgbas,
        borderColor: rgbas,
        borderWidth: 1
      }]
    }
  });

 
}

function random_rgba() {
  var o = Math.round, r = Math.random, s = 255;
  return 'rgba(' + o(r() * s) + ',' + o(r() * s) + ',' + o(r() * s) + ',' + 0.8 + ')';
}


$(function() {
  //The passed argument has to be at least a empty object or a object with your desired options
  $('body,.sidebar').overlayScrollbars({ });
});