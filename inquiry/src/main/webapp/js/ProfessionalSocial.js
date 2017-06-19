/**
 * Created by dell on 2017/4/5.
 */

$(function(){
    var app={

        init:function(){
            this.querySubmit();//点击查询按钮
        },


        getObj : function(){//取值
            var list = $("form").serializeArray();
            var o = {};
            for (var i = 0; i < list.length; i++) {
                o[list[i].name]=list[i].value;
            }
            return o;
        },

        checkData : function(o){//验证
            $('.alert').hide();
            if (o["userNameCondition"]=="") {
                $("#J_errorMes").html("请输入姓名");
                $('.alert').show();
                return cookieLogin = false;
            }
            if (o["idCardCondition"]=="") {
                $("#J_errorMes").html("请输入身份证号");
                $('.alert').show();
                return cookieLogin = false;
            }
            if (o["phoneCondition"]=="") {
                $("#J_errorMes").html("请输入手机号码");
                $('.alert').show();
                return cookieLogin = false;
            }
            else{
                return true;
            }
        },

        querySubmit : function(){//提交
            $("#query-submit").on("click",function(){
                console.log(11)
                var o = app.getObj();
                var flag = true;
                console.log(flag);
                flag = app.checkData(o);
                if (flag) {
                    $.ajax({
                        type: "POST",
                        url: "/query/queryContactByCondition",
                        data: JSON.stringify(o),
                        contentType:'application/json;charset=utf-8',
                        success: function(res){
                            if (res.messageCode==10000) {
                                $("#ProfessionalSocialForm").empty();
                                $('#ProfessionalSocial_template').tmpl(res.result.professionalSocial).appendTo('#ProfessionalSocialForm')
                            }
                        }
                    });
                }
            });
        }
    }
    app.init();
})