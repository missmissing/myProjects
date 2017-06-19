/**
 * Created by dell on 2017/4/6.
 */
/**
 *
 */
// $(document).ready(function() {
//     $("#submit").click(function(){
//         alert("修改成功")
//     })
//
// });
/**
 *
 */
$(function(){

    var checkFlag = false;//校验成功标志
    var obj={};//定义全局对象

    var app = {
        init:function(){
            this.Submit();

        },

        getObject:function(){

            var list = $("form").serializeArray();
            $.each(list,function(i,n){
                obj[n.name] = n.value;
            });
            return obj;
        },

        errorMsg:function(msg){
            var html = '<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span><span>'+msg+'</span></div>';
            return html;
        },

        checkData:function(obj){
            $(".alert").remove();
            var confirmPwd = $("#repeat-password").val();
            if (obj["accountPassword"]=="") {
                var errorMsg = '请输入原始密码';
                $("#accountPassword").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }if (obj["accountPassword"].length<6 || obj["accountPassword"].length>50) {
                var errorMsg = '密码应该为6-50位之间';
                $("#accountPassword").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }if (!/^[A-Za-z0-9]+$/.test(obj["accountPassword"])) {
                var errorMsg = '密码必须由数字和字母组成';
                $("#accountPassword").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }
            if (obj["newAccountPassword"]=="") {
                var errorMsg = '请输入新密码密码';
                $("#newAccountPassword").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }if (obj["newAccountPassword"].length<6 || obj["newAccountPassword"].length>50) {
                var errorMsg = '密码应该为6-50位之间';
                $("#newAccountPassword").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }if (!/^[A-Za-z0-9]+$/.test(obj["newAccountPassword"])) {
                var errorMsg = '密码必须由数字和字母组成';
                $("#newAccountPassword").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }if (confirmPwd=="") {
                var errorMsg = '请输入确认密码';
                $("#J_repeat-password").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }if (confirmPwd!=obj["newAccountPassword"]) {
                var errorMsg = '两次密码输入不一样';
                $("#J_repeat-password").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            } else {
                checkFlag = true;
                $(".alert").remove();
            }
        },
        Submit:function(){
            $("#submit").bind("click",function(){
                var obj = app.getObject();
                app.checkData(obj);
                if (checkFlag) {
                    $.ajax({
                        type: "POST",
                        url: "/user/updatePassword",
                        data: JSON.stringify(obj),
                        contentType:'application/json;charset=utf-8',
                        success: function(res){
                            console.log(res);
                            if (res.messageCode=="10000") {
                                alert("修改密码成功")

                            }
                        }
                    });
                }
            })
        }
    }
    app.init();
})











