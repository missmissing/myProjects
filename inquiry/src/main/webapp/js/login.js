/**
 *
 */
$(function(){
    var cookieLogin = false;

    var app={

        init:function(){
            this.jump();//登录按钮跳转
            this.loginSubmit();//点击登录按钮
        },


        jump:function(){
            $("#btn-login").bind("click",function(){
                location.href="./template/regist.html";
            })
        },

        getObj : function(){//取值
            var list = $("form").serializeArray();
            var o = {};
            for (var i = 0; i < list.length; i++) {
                o[list[i].name]=list[i].value;
            }
            return o;
        },

        /*
         *  功能：拼接错误提示
         *  说明：
         */
        errorMsg:function(msg){
            var html = '<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span><span>'+msg+'</span></div>';
            return html;
        },

        checkData : function(o){//验证
            $('.alert').hide();
            if (o["accountCode"]=="") {
                $("#J_errorMes").html("请输入用户名");
                $('.alert').show();
                return cookieLogin = false;
            }
            if (o["accountPassword"]=="") {
                $("#J_errorMes").html("请输入用户名密码");
                $('.alert').show();
                return cookieLogin = false;
            }
            else{
                return true;
            }
        },

        loginSubmit : function(){//提交
            $("#J_login-submit").bind("click",function(){
                var o = app.getObj();
                var flag = true;
                flag = app.checkData(o);
                if (flag) {
                    $.ajax({
                        type: "post",
                        url: "/user/userLogin",
                        data: JSON.stringify(o),
                        contentType:'application/json;charset=utf-8',
                        success: function(res){
                            if (res.messageCode==10000) {
                                location.href="./template/index.html";
                            }else {
                                    $("#J_errorMes").html("用户名或密码错误");
                                    $('.alert').show();
                            }
                        }
                    });
                }
            });
        }
    }
    app.init();
})