package com.design.graduation.inquiry.controller;

import com.design.graduation.inquiry.common.ResultInfo;
import com.design.graduation.inquiry.model.Account;
import com.design.graduation.inquiry.service.IAccountService;
import com.design.graduation.inquiry.utils.Constant;
import com.design.graduation.inquiry.utils.Md5Util;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping(value = "/user")
public class AccountController {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private IAccountService accountService;


    /**
     * 用户登录
     * @param account 账号对象
     * @return
     */
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo userLogin(@RequestBody Account account) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String accountCode = account.getAccountCode();
        String accountPassword = Md5Util.EncoderByMd5(account.getAccountPassword());
        ResultInfo resultInfo = new ResultInfo();
        Integer accountId = accountService.login(accountCode,accountPassword);
        if(null != accountId){
            httpServletRequest.getSession().setAttribute("userId",accountId);
            resultInfo.setMessageCode(Constant.SUCCESS_CODE);
        } else {
            resultInfo.setMessageCode(Constant.ERROR_USER_CHECK);
            resultInfo.setMessageContent(Constant.NORMAL_USER_CHECK_MSG);
        }
        return resultInfo;
    }

    /**
     * 用户注册
     * @param account 账号信息
     * @return 结果对象
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo register(@RequestBody Account account) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String accountPassword = Md5Util.EncoderByMd5(account.getAccountPassword());
        account.setAccountPassword(accountPassword);
        account.setAccountStatus("0");
        ResultInfo resultInfo = new ResultInfo();
        if(0 == accountService.register(account)){
            resultInfo.setMessageCode(Constant.ERROR_USER_CHECK);
        } else {
            resultInfo.setMessageCode(Constant.SUCCESS_CODE);
        }
        return resultInfo;
    }

    /**
     * 修改密码
     * @param account 账号信息
     * @return 结果对象
     * @throws UnsupportedEncodingException 异常
     * @throws NoSuchAlgorithmException 异常
     */
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updatePassword(@RequestBody Account account) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String accountPassword = Md5Util.EncoderByMd5(account.getAccountPassword());
        account.setAccountPassword(accountPassword);
        account.setAccountId((Integer) httpServletRequest.getSession().getAttribute("userId"));
        ResultInfo resultInfo = new ResultInfo();
        if(0 == accountService.updatePassword(account)){
            resultInfo.setMessageCode(Constant.ERROR_USER_CHECK);
        } else {
            resultInfo.setMessageCode(Constant.SUCCESS_CODE);
        }
        return resultInfo;
    }

    /**
     * 检查账号是否已存在
     * @param accountCode 账号
     * @return 结果对象
     */
    @RequestMapping(value = "/checkAccountIsExist/{accountCode}",method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo checkAccountIsExist(@PathVariable String accountCode) {
        ResultInfo resultInfo = new ResultInfo();
        if(0 == accountService.checkAccountIsExist(accountCode)){
            resultInfo.setMessageContent("notExist");
        } else {
            resultInfo.setMessageContent("exist");
        }
        resultInfo.setMessageCode(Constant.SUCCESS_CODE);
        return resultInfo;
    }
}
