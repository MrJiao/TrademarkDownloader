// ==UserScript==
// @name         老婆我喜欢你
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  爱心小拳拳
// @author       Jackson
// @match        https://euipo.europa.eu/eSearch/
// @grant        none
// ==/UserScript==
(function () {

    var wordArr;//点击确定后切割好的单词，按行切割
    var word_position = 0;//当前的单词
    var trDom = ['<input type="button" id="jackson_paramShow"  style="position: fixed; top: 50px;right:0px;"  value="参数设置">'].join("");

    var paramDiv = ['<div id="jackson_paramDiv" style="position: fixed;top: 50px;right:-350px;border: 0px solid;">',
        '    <div style="">',
        '        <p>全部参数</p >',
        '        <textarea id="allText" style="height: 100px;width: 130px"></textarea>',
        '		<br>',
        '    </div>',
        '    <input type="button" id="jackson_btnSave"  value="保存">',
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
    var trDom2 = ['<div style="position: fixed; top: 270px;right:0px;border: 1px solid;width:160px">',
        '	<p id="current_word">文件名称</p><br>',
        '	<input type="button" id="jackson_search"   value="搜索" style="height: 300px;width: 50px">',
        '	<input type="button" id="jackson_copy"   value="复制" style="height: 300px;width: 50px">',
        //'	<input type="button" id="jackson_has"   value="有" style="height: 300px;width: 50px">',
        //'	<input type="button" id="jackson_no"   value="没有" style="height: 300px;width: 50px"><br>',
        '	<input type="button" id="jackson_oneMore"   value="上一个" style="height: 300px;width: 50px">',


        '</div>'].join("");
    $("body").append(trDom2);


    $("#jackson_copy").on('click', jackson_copy);
    $("#jackson_oneMore").on('click', jackson_pre);

    $("#jackson_search").on('click', jackson_search);
    $("#jackson_has").on('click', jackson_Have);
    $("#jackson_no").on('click', jackson_None);
    $("#jackson_btnSave").click(saveWordArr);

    /*    $(document).keyup(function (event) {
            if (event.ctrlKey && event.keyCode === 13) {
                $(".doSearchBt.btn.btn-primary.btn-large")[0].click();
                startLooper();
            }

            console.log(event.keyCode);
        });*/

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
        $("input[name='MarkVerbalElementText']").focus();
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

    function myCurrentWord() {
        return wordArr[word_position];
    }

    function prevWord() {
        word_position--;
        if (word_position <= 0) {
            word_position = 0;
        }
        var word = wordArr[word_position];
        console.log("prevWord:" + word);
        return word;
    }

    function auto_pre_search() {
        $(".doSearchBt.btn.btn-primary.btn-large")[0].click();
        $("input[name='MarkVerbalElementText']").val("");
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
        $(".doSearchBt.btn.btn-primary.btn-large")[0].click();
        startLooper();
    }


    function exitTimmer() {
        console.log("exitTimmer");
        clearInterval(timer);
    }

    var timer;

    function starTimmer() {
        console.log("starTimmer");
        timer = setInterval(function () {
            if ($(".flOverlay")[0] != undefined) {
                console.log("还在搜索");
                return;
            }
            if ($("div.searchInfo.pull-right")[0] == undefined) {
                exitTimmer();
                jackson_None();
            } else {
                exitTimmer();
                jackson_Have();
                //$("div.searchInfo.pull-right").html().trim().split(" ")[0];
            }
        }, 200);
    }

    function* sleep(ms) {
        yield new Promise(function (resolve, reject) {
            setTimeout(resolve, ms);
        })
    }

    function startLooper() {
        console.log("looper");
        sleep(1000).next().value.then(() => {
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