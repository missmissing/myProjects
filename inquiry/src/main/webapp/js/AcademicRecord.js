/**
 * Created by dell on 2017/4/5.
 */
/**
 * Created by dell on 2017/4/4.
 */
/**
 *
 */
$(function(){

    var checkFlag = false;//校验成功标志


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

        errorMsg:function(msg){
            var html = '<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span><span>'+msg+'</span></div>';
            return html;
        },


        // checkData:function(o) {
        //     $(".alert").hide();
        //     if (o["userNameCondition"] == "") {
        //         $("#J_errorMes").html("请输入姓名");
        //         $('.alert').show();
        //         return checkFlag = false;
        //     }if (!( /[a-zA-Z]+\/[a-zA-Z]+/.test(o["userNameCondition"]))) {
        //         $("#J_errorMes").html("姓名格式不正确");
        //         $('.alert').show();
        //         return checkFlag = false;
        //     }
        //
        //     if (o["idCardCondition"] == "") {
        //         $("#J_errorMes").html("请输入身份证号");
        //         $('.alert').show();
        //         return checkFlag = false;
        //     }
        //     if (!/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(o["accountPassword"])) {
        //         $("#J_errorMes").html("身份证号格式不正确");
        //         $('.alert').show();
        //         return checkFlag = false;
        //     }
        //     if (o["phoneCondition"] == "") {
        //         $("#J_errorMes").html("请输入手机号");
        //         $('.alert').show();
        //         return checkFlag = false;
        //     }
        //     if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(o["phoneCondition"]))) {
        //         $("#J_errorMes").html("手机号格式不正确");
        //         $('.alert').show();
        //         return checkFlag = false;
        //     } else
        //         return true;
        //
        // },

        checkData:function(o) {
            $(".alert").remove();
            if (o["userNameCondition"] == "") {
                var errorMsg = '请输入姓名';
                $("#userNameCondition").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }

            if (o["idCardCondition"] == "") {
                var errorMsg = '请输入身份证号';
                $("#idCardCondition").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }
            if (!/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/.test(o["accountPassword"])) {
                var errorMsg = '身份证号格式不正确';
                $("#idCardCondition").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }
            if (o["phoneCondition"] == "") {
                var errorMsg = '请输入手机号';
                $("#phoneCondition").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            }
            if (!/^0?(13[0-9]|15[012356789]|18[0-9]|17[0-9])[0-9]{8}$/.test(o["phoneCondition"])) {
                var errorMsg = '身份证号必须是15位或者是18位';
                $("#phoneCondition").parent().append(app.errorMsg(errorMsg));
                return checkFlag = false;
            } else
               return true;

        },



        querySubmit: function(){//提交
            $("#query-submit").on("click",function(){
                console.log(11)
                var o = app.getObj();
                var flag = true;
                console.log(flag);
                flag = app.checkData(o);
                if (flag) {
                    $.ajax({
                        type: "post",
                        url: "/query/queryEducationByCondition",
                        data: JSON.stringify(o),
                        contentType:'application/json;charset=utf-8',
                        success: function(res){
                            if (res.messageCode==10000) {
                                $("#AcademicRecordForm").empty();
                                $('#AcademicRecordForm_template').tmpl(res.result.academicRecord).appendTo('#AcademicRecordForm')
                            }
                        }
                    });
                }
            });
        }
    }
    app.init();
})