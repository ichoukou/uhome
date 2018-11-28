/*

 文件描述:验证类
 创建日期:13-02-15
 修改日期:13-02-15
 最后修改人:Merlin

 */



//申明类
function validator() {
}

//验证是否为空
validator.checkEmpty = function (textbox, msgbox, msg) {

    if (textbox.val() == "") {
        msgbox.show();
        msgbox.text(msg);
        return false;
    } else {
        msgbox.hide();
        return true;
    }
};

//验证是否为email
//1 = show message  2= alert
validator.checkEmail = function (textbox, msgbox, msg, type) {
    var re = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;

    if (!re.test($.trim(textbox.val()))) {
        if (type == 1) {
            msgbox.show();
            msgbox.text(msg);
        } else if (type == 2) {
            alert(msg);
        }
        return false;
    } else {
        if (type == 1) {
            msgbox.hide();
        }
        return true;
    }

};

validator.checkMobel = function (textbox, msg) {
    value = textbox.val()
    var re1 = /^13\d{9}$/;
    var re2 = /^15[0-35-9]\d{8}$/;
    var re3 = /^18[05-9]\d{8}$/;

    if (re1.test(value) || re2.test(value) || re3.test(value)) {

        return true;

    }
    else {
        alert(msg);
        return false;
    }

};

function changeImg() {
    document.getElementById("codeimg").src = "user_control.php?action=captcha&" + Math.random();
}
	
	

