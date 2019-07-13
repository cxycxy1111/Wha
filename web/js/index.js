$(document).ready(

);

/**
 * index.html
 * @type {number}
 */
const POSITION_IMPORTANT = 1;
const POSITION_DISCOVER = 2;
const POSITION_PAGE = 3;
const POSITION_LINKED_NEWS = 4;
const POSITION_LINKED_PAGE = 5;

var current_position = POSITION_IMPORTANT;

var important_page_no = 1;
var discover_page_no = 1;
var page_page_no = 1;
var linked_news_page_no = 1;
var linked_pages_page_no = 1;

var current_selected_event = 0;

var url_query_important = "/user/timeline/qry/important";
var url_query_discover = "/user/timeline/qry/full";
var url_query_page = "/user/page/qry";
var url_query_timeline_detail = "/user/timeline/qry/detail";
var url_query_page_detail = "/user/page/qry/detail";
var url_query_linked_news = "/admin/timeline/qry/event";
var url_query_linked_page = "/user/page/qry/event";

/**
 * index
 */

/**
 * 加载新闻与分析
 */
function loadImportantNews() {

    var e = window.event;
    var current_target = e.target.id;

    $("p.head-tab-paragraph").css("color","#333");
    $("p.head-tab-paragraph").css("font-weight","400");
    important_page_no = 1;
    discover_page_no = 1;
    page_page_no = 1;
    linked_news_page_no = 1;
    linked_pages_page_no = 1;

    if (current_target == "important-paragraph") {
        current_position = POSITION_IMPORTANT;
        $("p#important-paragraph").css("color","black");
        $("p#important-paragraph").css("font-weight","700");
        indexLoadNewsList(true,url_query_important,"page_no="+important_page_no,important_page_no);
    } else if (current_target == "discover-paragraph") {
        current_position = POSITION_DISCOVER;
        $("p#discover-paragraph").css("color","black");
        $("p#discover-paragraph").css("font-weight","700");
        indexLoadNewsList(true,url_query_discover,"page_no="+discover_page_no,discover_page_no);
    } else if (current_target == "page-paragraph") {
        current_position = POSITION_PAGE;
        $("p#page-paragraph").css("color","black");
        $("p#page-paragraph").css("font-weight","700");
        indexLoadNewsList(true,url_query_page,"page_no="+page_page_no,page_page_no);
    } else {
        current_position = POSITION_IMPORTANT;
        $("p#important-paragraph").css("color","black");
        $("p#important-paragraph").css("font-weight","700");
        indexLoadNewsList(true, url_query_important,"page_no="+important_page_no, important_page_no);
    }
}

/**
 * 加载更多新闻与分析
 */
function loadMoreImportantNews() {
    if (current_position == POSITION_IMPORTANT) {
        indexLoadNewsList(false,url_query_important,"page_no="+important_page_no,important_page_no);
    }else if (current_position == POSITION_DISCOVER) {
        indexLoadNewsList(false,url_query_discover,"page_no="+discover_page_no,discover_page_no);
    }else if (current_position == POSITION_PAGE) {
        indexLoadNewsList(false,url_query_page,"page_no="+page_page_no,page_page_no);
    }else if (current_position == POSITION_LINKED_NEWS) {
        indexLoadNewsList(false,url_query_linked_news,"page_no="+linked_news_page_no + "&event=" + current_selected_event,linked_news_page_no);
    }else if (current_position == POSITION_LINKED_PAGE) {

    }
}

/**
 * 首页加载
 * @param isReload
 * @param url
 * @param param
 * @param page_no
 */
function indexLoadNewsList(isReload,url,param,page_no) {
    if(isReload) {
        $("div.div-content").remove();
        $("div.end-of-load").remove();
        $("div.load-more-news").remove();
    }
    httpRequest("GET",url,param,function (data) {
        var str = "";
        for(var i = 0;i < data.length;i++) {
            if (current_position ==POSITION_PAGE || current_position == POSITION_LINKED_PAGE) {
                str = str +
                    "<div class=\"div-content div-content-common\"  onclick='openPageDetail(this)' id='" + data[i].id + "'>\n" +
                    "   <h1 class=\"p-title\">" + data[i].page_title +"</h1>\n" +
                    "   <p class=\"p-content\">" + data[i].page_summary +"</p>\n" +
                    "   <p class=\"p-time\">" + data[i].happen_time.substring(0,10) +"</p>\n" +
                    "</div>";
            }else {
                str = str +
                    "<div class=\"div-content div-content-common\" onclick='openTimelineDetail(this)' id='" + data[i].id + "'>\n" +
                    "   <h1 class=\"p-title\">" + data[i].title +"</h1>\n" +
                    "   <p class=\"p-content\">" + data[i].summary +"</p>\n" +
                    "   <p class=\"p-time\">" + data[i].happen_time.substring(0,10) +"</p>\n" +
                    "</div>";
            }

        }
        if (data.length < 20) {
            str = str + "<div class=\"end-of-load\">\n" +
                "<p class=\"paragraph-end-of-load\">已加载完毕</p>\n" +
                "</p>\n";
            $("div.load-more-news").remove();
        }else {
            if (isReload) {
                str = str + "<div class=\"load-more-news\" onclick='loadMoreImportantNews()'>\n" +
                    "<p class=\"paragraph-load-more-news\">加载更多</p>\n" +
                    "</p>\n";
            } else  {

            }
        }
        if (isReload) {
            $("div.body").prepend(str);
        }else {
            $("div.div-content:last").after(str);
        }
        if (current_position == POSITION_IMPORTANT) {
            important_page_no = important_page_no+1;
        }else if (current_position == POSITION_DISCOVER) {
            discover_page_no = discover_page_no + 1;
        }else if (current_position == POSITION_PAGE) {
            page_page_no = page_page_no + 1;
        }else if (current_position == POSITION_LINKED_NEWS){
            linked_news_page_no = linked_news_page_no + 1;
        }else if (current_position == POSITION_LINKED_PAGE){
            linked_pages_page_no = linked_pages_page_no + 1;
        }
        //改变样式
        if (current_position == POSITION_LINKED_PAGE || current_position == POSITION_LINKED_NEWS) {
            $(".div-content").css("top","0px");
            $(".end-of-load").css("top","0px");
            $(".load-more-news").css("top","0px");
        }

    }, function (data) {

    }, function (data) {

    });
}

/**
 * 打开时间线详情操作
 * @param that
 */
function openTimelineDetail(that) {
    window.open("TimelineDetail.html?id=" + $(that).attr("id"));
}

/**
 * 打开分析操作
 */
function openPageDetail(that) {
    window.open("PageDetail.html?id=" + $(that).attr("id"));
}

/**
 * TimelineDetail
 */

/**
 * 加载时间线详情
 */
function onTimelineDetailLoad() {
    var id = window.location.search;
    var strs = id.split("=");

    httpRequest("GET",url_query_timeline_detail,"id="+strs[1],function (data) {
        var dic = data[0];
        var content = dic.content;
        content = "<p class=\"p-content\">" + content;
        document.title = dic.title;
        content = content.replace(/\n/g,"</p><p class=\"p-content\">");
        console.log(content);
        var str = "";
        str = str +
            "<div class=\"div-content-detail div-content-common\">\n" +
            "   <h1 class=\"p-title\">" +dic.title +"</h1>\n" +
            content +
            "   <p class=\"p-time\">" + dic.happen_time.substring(0,10) +"</p>\n" +
            "</div>";
        $("div.body").prepend(str);
        console.log("event_id:" + dic.event_id);
        $("div.div-button-container").attr("event_id",dic.event_id);
    }, function (data) {

    }, function (data) {

    });
}

function openLinkedNews(that) {
    window.open("LinkedTimeline.html?id=" + $(that).attr("event_id"));
}

function openLinkedPage(that) {
    window.open("LinkedPage.html?id=" + $(that).attr("event_id"));
}

/**
 * LinkedTimeline
 */
/**
 * 加载相关新闻
 */
function loadLinkedNews() {
    var id = window.location.search;
    var strs = id.split("=");
    current_position = POSITION_LINKED_NEWS;
    current_selected_event = strs[1];
    indexLoadNewsList(true, url_query_linked_news,"page_no="+linked_news_page_no + "&event=" + strs[1], linked_news_page_no);
}

/**
 * LinkedPage
 */
/**
 * 加载相关分析
 */
function loadLinkedPage() {
    var id = window.location.search;
    var strs = id.split("=");
    current_position = POSITION_LINKED_PAGE;
    current_selected_event = strs[1];
    indexLoadNewsList(true, url_query_linked_page,"page_no="+linked_pages_page_no + "&event=" + strs[1], linked_pages_page_no);
}

/**
 * PageDetail
 */
/**
 * 加载分析详情
 */
function onPageDetailLoad() {
    var id = window.location.search;
    var strs = id.split("=");

    httpRequest("GET",url_query_page_detail,"id="+strs[1],function (data) {
        var dic = data[0];
        var content = dic.content;
        content = "<p class=\"p-content\">" + content;
        document.title = dic.page_title;
        content = content.replace(/\n/g,"</p><p class=\"p-content\">");
        var str = "";
        str = str +
            "<div class=\"div-content-detail div-content-common\">\n" +
            "   <h1 class=\"p-title\">" +dic.page_title +"</h1>\n" +
            content +
            "   <p class=\"p-time\">" + dic.happen_time.substring(0,10) +"</p>\n" +
            "</div>";
        $("div.body").append(str);
    }, function (data) {

    }, function (data) {

    });
}

/**
 * 公共模块
 * @param method
 * @param url
 * @param param
 * @param successArr
 * @param successDic
 * @param error
 */
function httpRequest(method,url,param,successArr,successDic,error) {
    $.ajax({
        type: method,
        contentType: 'application/json;charset=UTF-8',
        url: url + "?" + param,
        success: function (result) {
            var data = JSON.parse(result);
            if (result.startsWith("{", 0)) {
                successDic(data);
            } else if (result.startsWith("[", 0)) {
                successArr(data);
            } else {
                error(result);
            }

        },
        error: function (e) {
            console.log(e.status);
            console.log(e.responseText);
        }
    })
}