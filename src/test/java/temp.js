// ==UserScript==
// @name         爱心小拳拳
// @namespace    http://tampermonkey.net/
// @version      2018.10.09
// @description  爱心小拳拳
// @author       Jackson
// @match        https://euipo.europa.eu/eSearch/
// @grant        none
// ==/UserScript==
(function () {

    var wordArr;//点击确定后切割好的单词，按行切割
    var word_position = 0;//当前的单词
    //var trDom = ['<input type="button" id="jackson_paramShow"  style="position: fixed; top: 50px;right:0px;"  value="参数设置">'].join("");
    var trDom = ['<input type="button" id="btn_jackson_paramHide" style="position: fixed; top: 35px;right:15px;height:30px;display:none" value="关">',
        '<input type="button" id="btn_jackson_paramShow" style="position: fixed; top: 80px;right:15px;height:30px;" value="开">'].join("");

    var paramDiv = [
        // '<div id="jackson_paramDiv" style="position: fixed;top: 50px;right:-350px;border: 0px solid;">',
        '<div id="jackson_paramDiv" style="position: fixed;top: 90px;right:15px;border: 0px solid;display:none">',
        '    <div style="">',
        '        <p>全部参数</p >',
        '        <textarea id="allText" style="height: 100px;width: 130px"></textarea>',
        '		<br>',
        '    </div>',
        '    <input type="button" id="jackson_btnSave"  style="top:150px" value="保存">',
        '</div>'
    ].join("");


    var resultHaveDivStr = ['<div id="jackson_resultHave" style="position: fixed;top: 50px;left:5px;border: 0px solid;">',
        '    <div style="">',
        '        <p>搜索有结果的名称</p >',
        '        <textarea id="jackson_resultHave_textarea" style="height: 100px;width: 130px"></textarea>',
        '		<br>',
        '    </div>',
        '</div>'
    ].join("");

    var resultNoneDivStr = ['<div id="jackson_resultNone" style="position: fixed;top: 300px;left:5px;border: 0px solid;">',
        '    <div style="">',
        '        <p>搜索无结果的名称</p >',
        '        <textarea id="jackson_resultNone_textarea" style="height: 100px;width: 130px"></textarea>',
        '		<br>',
        '    </div>',
        '</div>'
    ].join("");


    $("body").append(trDom);
    $("body").append(paramDiv);
    $("body").append(resultHaveDivStr);
    $("body").append(resultNoneDivStr);
    var trDom2 = ['<div style="position: fixed; top: 270px;right:0px;width:160px">',
        ' <div style=" text-align:center;font-size: 20px;">	<p style="font-size: 20px;" id="current_word" >先保存</p></div>',
        '	<input type="button" id="jackson_search"   value="搜索" style="height: 500px;width: 100px">',
        // '	<input type="button" id="jackson_copy"   value="复制" style="height: 500px;width: 50px">',
        '	<input type="button" id="jackson_pre"   value="上一个" style="height: 500px;width: 50px">',
        '</div>'].join("");
    $("body").append(trDom2);


    $("#jackson_copy").on('click', jackson_copy);
    $("#jackson_pre").on('click', jackson_pre);

    $("#jackson_search").on('click', jackson_search);
    $("#jackson_btnSave").click(saveWordArr);

    //按钮文案"参数配置"-->"开"
    //显示按钮"关"
    $("#btn_jackson_paramShow").on("click", toggle_click_show);
    $("#btn_jackson_paramHide").on("click", toggle_click_hide);

    function toggle_click_show() {
        $("#btn_jackson_paramHide").show();
        $("#jackson_paramDiv").show();

    }

    function toggle_click_hide() {
        $("#jackson_paramDiv").hide();
    }

    function copyToClipboard(text) {
        console.log("copyToClipboard " + text);
        if (text.indexOf('-') !== -1) {
            let arr = text.split('-');
            text = arr[0] + arr[1];
        }
        var textArea = document.createElement("textarea");
        textArea.style.position = 'fixed';
        textArea.style.top = '0';
        textArea.style.left = '0';
        textArea.style.width = '2em';
        textArea.style.height = '2em';
        textArea.style.padding = '0';
        textArea.style.border = 'none';
        textArea.style.outline = 'none';
        textArea.style.boxShadow = 'none';
        textArea.style.background = 'transparent';
        textArea.value = text;
        document.body.appendChild(textArea);
        textArea.select();

        try {
            var successful = document.execCommand('copy');
            var msg = successful ? '成功复制到剪贴板' : '复制失败';
            console.log(msg);
        } catch (err) {
            console.log(err);
            alert('该浏览器不支持点击复制到剪贴板');
        }

        document.body.removeChild(textArea);
    }

    function setFocus() {
        getSearchInput().focus();
    }

    function getSearchInput(){
        return $("input[name='MarkVerbalElementText']");
    }

    function jackson_copy() {
        copyToClipboard(myCurrentWord());
        setFocus();
    }

    function saveWordArr() {
        var str = $("#allText").val();
        var arr = str.split("\n");
        wordArr = arr;
        word_position = 0;
        var word = myCurrentWord();
        $("#current_word").text(word);
        copyToClipboard(myCurrentWord());
    }

    function nextWord() {
        word_position++;
        if (word_position > wordArr.length - 1) {
            word_position = wordArr.length - 1;
        }
        var word = wordArr[word_position];
        console.log("nextWord:" + word);
        return word;
    }


    function getNextWord() {
        temp_word_position = word_position;
        temp_word_position++;
        if (temp_word_position > wordArr.length - 1) {
            temp_word_position = wordArr.length - 1;
            alert("已经到最后一个了");
        }
        var word = wordArr[temp_word_position];
        return word;
    }

    function myCurrentWord() {
        return wordArr[word_position];
    }

    function prevWord() {
        word_position--;
        if (word_position <= 0) {
            word_position = 0;
            alert("已经到第一个了");
        }
        var word = wordArr[word_position];
        console.log("prevWord:" + word);
        return word;
    }

    function auto_pre_search() {
        // $(".doSearchBt.btn.btn-primary.btn-large").hide();
        // $(".resetToDefaultBt.btn.btn-large.search-button").hide();
        // $(".clearCriteriaBt.btn.btn-large.search-button").hide();
        $(".doSearchBt.btn.btn-primary.btn-large")[0].click();
        getSearchInput().val("");
        setFocus();
    }

    function jackson_pre() {
        var word = prevWord();
        $("#current_word").text(word);
        copyToClipboard(word);
    }

    function appendWord(id, msg) {
        var mTextarear = $(id);
        mTextarear.val(mTextarear.val() + msg + "\r\n");
    }


    function jackson_Have() {
        var mCurrentWord = myCurrentWord();
        appendWord("#jackson_resultHave_textarea", mCurrentWord);
        console.log("jackson_Has currentWord:" + mCurrentWord);
        var word = nextWord();

        $("#current_word").text(word);
        auto_pre_search();
    }

    function jackson_None() {
        var mCurrentWord = myCurrentWord();
        appendWord("#jackson_resultNone_textarea", mCurrentWord);
        console.log("jackson_None currentWord:" + mCurrentWord);
        var word = nextWord();
        $("#current_word").text(word);
        auto_pre_search();
    }

    function jackson_search() {
        var jqSearchInput = getSearchInput();
        if(jqSearchInput == undefined){
            setFocus();
            return;
        }
        if(strIsEmpty(jqSearchInput.val())){
            setFocus();
            return;
        }
        copyToClipboard(getNextWord());
        $(".doSearchBt.btn.btn-primary.btn-large")[0].click();
        getSearchInput().val("");
        starTimmer();
    }

    function strIsEmpty(obj){
        if(typeof obj == "undefined" || obj == null || obj == ""){
            return true;
        }else{
            return false;
        }
    }


    function exitTimmer() {
        console.log("exitTimmer");
        clearInterval(timer);
        isTimerStar = false;
    }

    var timer;
    var isTimerStar = false;
    function starTimmer() {
        if(isTimerStar)return;
        isTimerStar = true;
        console.log("starTimmer");
        timer = setInterval(function () {
            if ($(".flOverlay")[0] != undefined) {
                console.log("还在搜索");
                return;
            }
            if ($("div.searchInfo.pull-right")[0] == undefined) {
                jackson_None();
            } else {
                jackson_Have();
                //$("div.searchInfo.pull-right").html().trim().split(" ")[0];
            }
            exitTimmer();
        }, 200);
    }

    function* sleep(ms) {
        yield new Promise(function (resolve, reject) {
            setTimeout(resolve, ms);
        })
    }

    function startLooper() {
        console.log("looper");
        sleep(200).next().value.then(() => {
            if ($(".flOverlay")[0] != undefined) {
                console.log("还在搜索");
                startLooper();
                return;
            }
            if ($("div.searchInfo.pull-right")[0] == undefined) {
                jackson_None();
            } else {
                jackson_Have();
                //$("div.searchInfo.pull-right").html().trim().split(" ")[0];
            }
        })
    }


    var showCount = 1;

    function toggle_click1() {
        var btn_paramShow = $("#jackson_paramShow");
        var div_param = $("#jackson_paramDiv");
        if (showCount % 2 == 0) {
            div_param.css("right", "-350px");
            btn_paramShow.click(toggle_click1);
        } else {
            div_param.css("right", "0px");
            btn_paramShow.click(toggle_click1);
        }
        showCount++;
    }

    $("#jackson_paramShow").click(toggle_click1);

})();