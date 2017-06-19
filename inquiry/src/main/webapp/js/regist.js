/**
 *
 */
$(function(){
    
    var checkFlag = false;//校验成功标志
    var obj={};//定义全局对象
    
    var app = {
            
            /*
             *  功能：初始化方法
             *  说明：需要在加载页面时初始化的项全部在此加载
             */
            init:function(){
                this.jump();//登录按钮跳转
                this.checkUserName();//验证用户名存不存在
                this.registSubmit();//注册提交


            },
            
            jump:function(){
                $(".regist .btn-login").bind("click",function(){
                    location.href="../login.html";
                })
            },
            
            getObject:function(){
                
                var list = $("form").serializeArray();
                $.each(list,function(i,n){
                	obj[n.name] = n.value;
                });
                return obj;
            },
            
            
            /*
             *  功能：拼接错误提示
             *  说明：
             */
            errorMsg:function(msg){
                var html = '<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span><span>'+msg+'</span></div>';
                return html;
            },
            
            
            /*
             *  功能：判断用户名是否存在
             *  说明：
             */
            checkUserName:function(){
                $("#accountCode").blur(function(){
                    var data = {
                           "accountCode":$(this).val()
                    }
                    $(".alert").remove();
                    if (data["accountCode"].length<4 || data["accountCode"].length>20) {
                        var errorMsg = '用户名应该为4-20位之间';
                        $(this).parent().append(app.errorMsg(errorMsg));
                        return false;
                    }if(!/[0-9]+[a-zA-Z]+[0-9a-zA-Z]*|[a-zA-Z]+[0-9]+[0-9a-zA-Z]*/.test(data["accountCode"])){
                        var errorMsg = '用户名必须由数字和字母组成';
                        $(this).parent().append(app.errorMsg(errorMsg));
                        return false;
                    }else {
                        $.ajax({
                            type: "POST",
                            url: "/user/accountCodeCopy",
                            data: JSON.stringify(data),
                            contentType:'application/json;charset=utf-8',
                            success: function(res){
                                $(".alert").remove();
                                if (res.messageCode=="10000") {
                                    checkFlag = true;
                                }else {
                                    var errorMsg = '用户名已存在';
                                    $("#accountCode").parent().append(app.errorMsg(errorMsg));
                                    checkFlag = false;
                                }
                            }
                         });
                    }
                });
            },
            
            
            /*
             *  功能：输入格式校验
             *  说明：
             */
            checkData:function(obj){
                $(".alert").remove();
                var confirmPwd = $("#repeat-password").val();
                if (obj["accountCode"]=="") {
                    var errorMsg = '请输入用户名';
                    $("#accountCode").parent().append(app.errorMsg(errorMsg));
                    return checkFlag = false;
                }if (obj["accountCode"].length<4 || obj["accountCode"].length>20) {
                    var errorMsg = '用户名应该为4-20位之间';
                    $("#accountCode").parent().append(app.errorMsg(errorMsg));
                    return checkFlag = false;
                }if (!/[0-9]+[a-zA-Z]+[0-9a-zA-Z]*|[a-zA-Z]+[0-9]+[0-9a-zA-Z]*/.test(obj["accountCode"])) {
                    var errorMsg = '用户名必须由数字和字母组成';
                    $("#accountCode").parent().append(app.errorMsg(errorMsg));
                    return checkFlag = false;
                }if (obj["accountPassword"]=="") {
                    var errorMsg = '请输入用户名密码';
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
                }if (confirmPwd=="") {
                    var errorMsg = '请输入确认密码';
                    $("#J_repeat-password").parent().append(app.errorMsg(errorMsg));
                    return checkFlag = false;
                }if (confirmPwd!=obj["accountPassword"]) {
                    var errorMsg = '两次密码输入不一样';
                    $("#J_repeat-password").parent().append(app.errorMsg(errorMsg));
                    return checkFlag = false;
                } else {
                    checkFlag = true;
                    $(".alert").remove();
                }
            },

            /*
             *  功能：注册提交
             *  说明：
             */
            registSubmit:function(){
                $("#J_regist-submit").bind("click",function(){
                    var obj = app.getObject();
                    app.checkData(obj);
                    console.log(obj);
                    if (checkFlag) {
                        $.ajax({
                            type: "POST",
                            url: "/user/register",
                            data: JSON.stringify(obj),
                            contentType:'application/json;charset=utf-8',
                            success: function(res){
                                console.log(res);
                                if (res.messageCode=="10000") {
                                    location.href="../login.html";
                                }
                            }
                         });
                    }
                })
            }
    }
    app.init();
})