package com.line.bqxd.platform.controller.v2.concur;

import com.line.bqxd.platform.client.constant.BizFeeType;
import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dto.Result;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.common.BizResult;
import com.line.bqxd.platform.common.utils.CollectionUtils;
import com.line.bqxd.platform.common.utils.NginxTool;
import com.line.bqxd.platform.controller.admin.domain.UserConcurRelationVO;
import com.line.bqxd.platform.controller.common.BaseController;
import com.line.bqxd.platform.controller.domain.response.MyResponse;
import com.line.bqxd.platform.controller.domain.response.PayVO;
import com.line.bqxd.platform.dao.UserBalanceDAO;
import com.line.bqxd.platform.dao.UserTradeFillDAO;
import com.line.bqxd.platform.dao.UserTradePayDAO;
import com.line.bqxd.platform.dao.*;

import com.line.bqxd.platform.dataobject.GlobalObject;
import com.line.bqxd.platform.login.SessionUserInfo;
import com.line.bqxd.platform.manager.claim.ClaimApplyManager;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.dunk.DunkBillManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.wx.WXManager;
import com.line.bqxd.platform.manager.wxmenu.WXMenuManager;
import com.line.bqxd.platform.manager.wxpay.RequestHandler;
import com.line.bqxd.platform.manager.wxpay.TradeManager;
import com.line.bqxd.platform.manager.wxpay.WXPayManager;
import com.line.bqxd.platform.manager.wxpay.dataobject.WXPayResult;
import com.line.bqxd.platform.manager.wxpay.utils.PayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

/**
 * Created by huangjianfei on 16/5/16.
 */
@Controller
@RequestMapping("/v2/concur")
public class ConcurMyController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ConcurMyController.class);
	private static Logger bizLogger = LoggerFactory.getLogger("bizLogger");
    /*
    @Resource  //自动注入,默认按名称
    private UserTradeBillDAO<UserTradeBillDO> userTradeBillDAO;
*/
    @Resource
    private UserTradePayDAO<UserTradePayDO> userTradePayDAO;

	@Resource
	private UserTradeCashDAO<UserTradeCashDO> userTradeCashDAO;
	
    @Resource
    private UserTradeFillDAO<UserTradeFillDO> userTradeFillDAO;

	//    @Resource
    //private UserBalanceDAO<UserBalanceDO> userBalanceDAO;

    @Autowired
    private WXMenuManager wxMenuManager;

    @Autowired
    private GlobalObject globalObject;

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private WXManager wxManager;

    @Autowired
    private WXPayManager wxPayManager;


    @Autowired
    private ClaimApplyManager claimApplyManager;

    @Autowired
    private DunkBillManager dunkBillManager;

    @Autowired
    private TradeManager tradeManager;

    @RequestMapping("/my")
    public ModelAndView index(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId, HttpServletRequest request, Model model) {
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);

        List<UserConcurRelationDO> list = concurManager.getUserConcurRelation(sessionUserInfo.getUserId(), concurPlanId);
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        ModelAndView mav = new ModelAndView();
        UserDO userDO = userManager.selectByUserId(sessionUserInfo.getUserId());
        MyResponse myResponse = new MyResponse();
        myResponse.setConcurPlanDO(concurPlanDO);
        myResponse.setUserDO(userDO);
        if (list == null || list.size() == 0) {
            logger.warn("user not join concur plan userId={},userName={}", sessionUserInfo.getUserId(), sessionUserInfo.getUserName());
            mav.addObject("errorCode", "notJoin");
            mav.addObject("errorDesc", "用户还没有加入互助计划");
        } else {

            //check if the mobile does not exist, step to input mobile
            if (userDO.checkUserMobile(userDO) == false) {
                mav.setViewName("/v2/concur/addStep2");
            } else {
                mav.setViewName("/v2/concur/my");
            }

            try {
                List<UserConcurRelationVO> voList = conver(list);
                myResponse.setUserList(voList);
                myResponse.setApplyConcur(isApplyConcur(voList));
            } catch (Exception e) {
                logger.error("ConcurMyController index error", e);
            }
        }

        handleQuantity(myResponse, concurPlanId);
        mav.addObject("myConcur", myResponse);
        setSignView(mav, request, sessionUserInfo.getAppid());
        return mav;
    }


    @RequestMapping(value = "/transaction")
    public ModelAndView showTransaction(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId, HttpServletRequest request, Model model) {
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);

        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        ModelAndView mav = new ModelAndView();
        UserDO userDO = userManager.selectByUserId(sessionUserInfo.getUserId());
        MyResponse myResponse = new MyResponse();
        myResponse.setConcurPlanDO(concurPlanDO);
        myResponse.setUserDO(userDO);

        String msg = "";

        //UserTradeFillDAO userTradeFillDAO = new UserTradeFillDAO();
        List<UserTradeFillDO> userTradeFillDOList = null;
        try {
            userTradeFillDOList = userTradeFillDAO.selectAll();
            if (!userTradeFillDOList.isEmpty())
            {
                List<UserTradeFillDO> currentUserFillList = new ArrayList<UserTradeFillDO>();

                for (UserTradeFillDO u : userTradeFillDOList) {
                    if (u.getUserId() == userDO.getUserId()) {
                        currentUserFillList.add(u);
                    }
					else {
                    }
                }
                mav.addObject("userFillTrans", currentUserFillList);
            }

            long balance = 0;

            //UserBalanceDO userBalanceDO = (UserBalanceDO)(userBalanceDAO.selectByUserId(userDO.getUserId()));

/*
            List<UserBalanceDO> userBalanceDOList = userBalanceDAO.selectAll();
            for (UserBalanceDO ba : userBalanceDOList)
            {
                if (ba.getUserId() == userDO.getUserId())
                {
                    balance = ba.getBalance();
                }
            }
*/

			/*
			List<UserTradePayDO> userTradePayDOList = null;
			try {

				userTradePayDOList = userTradePayDAO.selectAll();

				if (!userTradeFillDOList.isEmpty()) {

					List<UserTradePayDO> currentUserpayList = new ArrayList<UserTradePayDO>();

					for (UserTradePayDO u : userTradePayDOList) {

						if (u.getUserId() == userDO.getUserId()) {

							currentUserpayList.add(u);

						} else {

						}
					}
					mav.addObject("userPayTrans", currentUserpayList);
				}

			}
			catch (Exception e)
			{

				msg += e.toString();
			}
			*/

			
		List<UserTradeCashDO> userTradeCashDOList= userTradeCashDAO.selectAll();
		/*
		List<UserTradeCashDO> displayUserTradeCashDO = new ArrayList<UserTradeCashDO>();
		for (UserTradeCashDO cashDO : userTradeCashDOList)
		{
			if (cashDO.getUserId() == userDO.getUserId())
				{
					displayUserTradeCashDO.add(cashDO);
					
				}

			
		}
			
		mav.addObject("userCashTrans", displayUserTradeCashDO);
			*/
		
            balance = myResponse.getUserBalanceQuantity();
            //balance = userBalanceDO.getBalance();
            mav.addObject("balance", balance);

            String user_pic = userDO.getHeadImgurl();
            mav.addObject("user_pic", user_pic);

        } catch (Exception e) {
            msg +=  "in /transaction" + e.toString();
        }
		
		

		
					
			
        /*
        UserTradeBillQueryDO userTradeBillQueryDO = new UserTradeBillQueryDO();
        userTradeBillQueryDO.setUserId(userDO.getUserId());


        List<UserTradeBillDO> userTradeBillDOList = null;
        try {

            if (!userTradeBillDOList.isEmpty())
            {
                msg = msg + userTradeBillDOList.get(0).getListType();

                List<UserTradeBillDO> userFillTrans = new ArrayList<UserTradeBillDO>();  //充值
                List<UserTradeBillDO> userPayTrans = new ArrayList<UserTradeBillDO>();  //取现


                for (UserTradeBillDO bill : userTradeBillDOList) {
                    //if (bill.getUserId() == userDO.getUserId()) {
                    if(1== 1){
                        if (bill.getListType().equals("fill") ) {
                            userFillTrans.add(bill);
                        }
                        else if (bill.getListType().equals("pay") )
                        {
                            userPayTrans.add(bill);
                        }

                        else
                        {
                            //userPayTrans.add();
                        }

                    } else
                        {
                            continue; // not this user
                    }
                }

                //msg = userPayTrans.get(0).getListType();
                mav.addObject("userFillTrans", userFillTrans);
                mav.addObject("userPayTrans", userPayTrans);
            }
            else
            {
                //msg = msg + "empty";
           }

        } catch (Exception e) {
            msg = "wrong selectAll " + e.toString();
        }
        */
/*
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        long balance = 0;
        try {
            userBalanceDO = (UserBalanceDO) (userBalanceDAO.selectById(userDO.getUserId()));
            balance = userBalanceDO.getBalance();
        }
        catch (Exception e)
        {
            msg += e.toString();
        }

        mav.addObject("balance", balance);
*/
        // user fill history
        mav.addObject("myConcur", myResponse);

        mav.addObject("msg", msg);
        mav.setViewName("/v2/concur/transaction");
        return mav;
    }



    @RequestMapping(value = "/withdraw")
    public ModelAndView withdraw(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId,
                                 UserTradeCashDO userTradeCashDO,
                                 HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        String msg = "";

        SessionUserInfo userInfo = getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView();
        UserDO userDO = userManager.selectByUserId(userInfo.getUserId());
        MyResponse myResponse = new MyResponse();

        //myResponse.set(concurPlanDO);
        try {
            myResponse.setUserDO(userDO);
        }
        catch (Exception e)
        {
            msg = e.toString();
        }

        /*
        filter(userTradeCashDO);
        String bankNo = request.getParameter("bankNo");
        String bankName = request.getParameter("bankName");

        ResultEnum resultEnum = verify(userTradeCashDO, bankNo, bankName);

        if (resultEnum != ResultEnum.SUCCESS) {
            return Result.of(resultEnum.getCode(), resultEnum.getDesc());
        }
        else
        {
            return Result.of(ResultEnum.CLAIM_SUBMIT_FAIL.getCode(), ResultEnum.CLAIM_SUBMIT_FAIL.getDesc());
        }
		*/
		
        //WithdrawManager withdrawManager = new WithdrawManager();
        // UserTradeCashDAO.insert(userTradeCashDO);

		mav.addObject("user_pic", userDO.getHeadImgurl());
        mav.addObject("myConcur", myResponse);
        mav.addObject("msg", msg);
        mav.setViewName("/v2/concur/withdraw");
        return mav;
    }


    @RequestMapping(value = "/on_withdraw", method= RequestMethod.POST)
    public ModelAndView onWithdraw( @RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId,
                                   @RequestParam(value = "bankNo") String bankNo, @RequestParam(value = "bankName") String bankName,
                                    @RequestParam(value = "amount") String amount,
                                   HttpServletRequest request) throws Exception
	{
        String msg = "";
        ModelAndView mav = new ModelAndView();

        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        UserDO userDO = userManager.selectByUserId(sessionUserInfo.getUserId());

        MyResponse myResponse = new MyResponse();
        myResponse.setConcurPlanDO(concurPlanDO);
        myResponse.setUserDO(userDO);

        try {
			UserTradeCashDO userTradeCashDO = new UserTradeCashDO();
			/*
            UserTradeCashDO userTradeCashDO = new UserTradeCashDO(bankNo, bankName, userDO.getUserId(), userDO.getUserName(),"提现", //explains
																  amount,
																  null, // tradeid
																  null );
			*/
			userTradeCashDO.setUserId(userDO.getUserId());
			userTradeCashDO.setOpenId(userDO.getOpenid());
			userTradeCashDO.setBankNo(bankNo);
			userTradeCashDO.setBankName(bankName);
			userTradeCashDO.setWithdrawAmount(Long.parseLong(amount));
			userTradeCashDO.setStatus(3);
			
			long result  = userTradeCashDAO.insert(userTradeCashDO);
			msg += Long.toString(result);
        }
        catch (Exception e)
        {
			msg += "UserTradeCashDO insert" + e.toString();
        }
		
		//        String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
        /*
        Map<String, Object> map = new HashedMap() ;
        Map <String, String> content = new HashedMap();

        content.put("type", "view");
        content.put("name", "jump");
        content.put("url", "wwww.xiongdihuzhu.com");
        map.put("button", content);
*/
		//        BizResult bizResult = wxMenuManager.createMenu(jsonStr);
        //msg += bizResult.toString();

        /*
        String menu = "";
            //if (StringUtils.isNotBlank(menu)) {
            if (1==1)
            {
                try {
                    //Map<String, Object> map = JSON.parseObject(menu, Map.class);
                    if (org.springframework.util.CollectionUtils.isEmpty(map)) {
                        //return new BizResult(ResultEnum.ARGUMENT_VERIFY_FAIL);
                    }
                    //JSON.parseObject()
                } catch (Exception e) {
                    logger.error("json parse createMenu error", e);
                    msg += e.toString();
                    //return new BizResult(ResultEnum.SYSERROR);
                }
                String url = CREATE_MENU_URL + globalObject.getAccessToken();
                try {
                    HttpResult result = HttpUtil.sendOriginally(url, menu, "UTF-8");
                    if (result == null || !result.isSuccess()) {
                        logger.warn("createMenu result,false {}", result == null ? null : result);
                        BizResult bizResult = new BizResult(ResultEnum.SYSERROR);
                        if (result == null) {
                            bizResult.setDetailInfo("null");
                        } else {
                            bizResult.setDetailInfo(result.getContent());
                        }
                        //return bizResult;
                        msg += bizResult.toString();
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("createMenu request success {}", result);
                        }
                        JSONObject jsonObject=JSONObject.parseObject(result.getContent());
                        if(jsonObject.getString("errcode").equals("0")&&jsonObject.getString("errmsg").equals("ok")) {
                            //return new BizResult(ResultEnum.SUCCESS);
                            msg+="success";
                        }else{
                            BizResult bizResult=new BizResult(ResultEnum.SYSERROR);
                            bizResult.setDetailInfo(result.getContent());
                            //return bizResult;
                            msg += bizResult.toString();
                        }
                    }
                } catch (Exception e) {
                    logger.error("createMenu error", e);
                    //return new BizResult(ResultEnum.SYSERROR);
                    msg += e.toString();

                }
            } else {
                msg += "ResultEnum.ARGUMENT_VERIFY_FAIL";
                //return new BizResult(ResultEnum.ARGUMENT_VERIFY_FAIL);
            }
            */
		long userBalance = userManager.getBalance(sessionUserInfo.getUserId(), concurPlanId); 
		mav.addObject("balance",userBalance) ;
		mav.addObject("msg", msg);
        mav.setViewName("/v2/concur/my");
        return mav;
    }

    @RequestMapping("/update_withdraw")
    @ResponseBody
    public Result onwithdraw(@RequestParam("bankNo") String bankNo, @RequestParam("bankName") String bankName, HttpServletRequest request, HttpServletResponse response, Model model) {
        //response.getWriter().write("dd");
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        //ResultEnum resultEnum = IDCard.isMobile(mobile);

//        ResultEnum resultEnum  = insert(bankName, bankNo, sessionUserInfo.getUserId());
        ResultEnum resultEnum = ResultEnum.SUCCESS;
/*
        if (resultEnum == ResultEnum.SUCCESS) {
            if (StringUtils.isBlank(mcode)) {
                resultEnum = ResultEnum.CODE_BLANK_FAIL;
            }
        }
        if (resultEnum == ResultEnum.SUCCESS) {
            boolean result = smsManager.verifySms(mcode, mobile, false);
            if (!result) {
                resultEnum = ResultEnum.CODE_VERIFY_FAIL;
            } else {
                userManager.perfectUser(sessionUserInfo.getUserId(), null, null, mobile);

            }
        }
        if (resultEnum == ResultEnum.SUCCESS) {
            RegMobileTempDO regMobileTempDO = regMobileTempDAO.selectByMobile(mobile);
            if (regMobileTempDO == null) {
                regMobileTempDO = new RegMobileTempDO();
                regMobileTempDO.setMobile(mobile);
                regMobileTempDAO.insert(regMobileTempDO);
            }
        }
*/

        return Result.of(resultEnum.getCode(), resultEnum.getDesc());

    }

        @RequestMapping("/topup")
    public ModelAndView topup(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId,
                                 HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

//        SessionUserInfo userInfo = getSessionUserInfo(request)
        String msg = "";
        ModelAndView mav = new ModelAndView();

		bizLogger.debug("DANNYin topup");
		/*

        UserDO userDO = userManager.selectByUserId(userInfo.getUserId());
        MyResponse myResponse = new MyResponse();
        //myResponse.set(concurPlanDO);
        myResponse.setUserDO(userDO);
*/

        /*
        filter(userTradeCashDO);
        String bankNo = request.getParameter("bankNo");
        String bankName = request.getParameter("bankName");

        ResultEnum resultEnum = verify(userTradeCashDO, bankNo, bankName);

        if (resultEnum != ResultEnum.SUCCESS) {
            return Result.of(resultEnum.getCode(), resultEnum.getDesc());
        }
        else
        {
            return Result.of(ResultEnum.CLAIM_SUBMIT_FAIL.getCode(), ResultEnum.CLAIM_SUBMIT_FAIL.getDesc());
        }
*/
        //WithdrawManager withdrawManager = new WithdrawManager();
        // UserTradeCashDAO.insert(userTradeCashDO);

        //mav.addObject("myConcur", myResponse);

        mav.addObject("msg", msg);
        mav.setViewName("/v2/concur/topup");
        return mav;
    }


    @RequestMapping(value="/pay_process")
	@ResponseBody
    public Result  payProcess(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId,
                          //@RequestParam(value = "amount") long amount,
                          HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        SessionUserInfo userInfo = getSessionUserInfo(request);
        String msg = "in pay_process";
        ModelAndView mav = new ModelAndView();

		bizLogger.debug("DANNYDEBUG1: in pay_proces");  

		ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(concurPlanId);
        //String ids = request.getParameter("ids");

        PayVO payVO = null;
        try {
            //Long amount = Long.parseLong(request.getParameter("amount"));
            Long amount = (long)0.001;
            String ids = "123";			
			msg += "amount" + Long.toString(amount);
			msg += "concurPlanId" + concurPlanId;
			msg += "ids" + ids;

			payVO = concurFeePay(request, amount, concurPlanId, ids);			
						
			/*
            UserTradeFillDO userTradeFillDO = new UserTradeFillDO();
            userTradeFillDO.setUserId(userInfo.getUserId());
            userTradeFillDO.setOpenId(userInfo.getOpenid());
            userTradeFillDO.setAttach(null);
            userTradeFillDO.setExplains("会员费充值");
            userTradeFillDO.setPayStatus(3);
            userTradeFillDO.setTotalFee(amount);
            userTradeFillDO.setPayType("wxpay");
            userTradeFillDO.setFeeType("CNY");
            userTradeFillDO.setTimeStart(null);
            userTradeFillDO.setWxTradeType("JSAPI");
            userTradeFillDO.setErrCode(null);
            userTradeFillDO.setErrCodeDes(null);
            userTradePayDAO.insert(userTradeFillDO);
            */
        }
        catch (Exception e)
        {
			msg += payVO.getMsg();
		    msg += e.toString();
		}
/*
        //mav.addObject("payVO", payVO);

        /*
        mav.setViewName("v2/concur/");


        if (payVO != null && payVO.isResult()) {
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), payVO);
        } else {
            return Result.of(ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getCode(), ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getDesc());
        }
*/
        mav.addObject("msg", msg);
        mav.addObject("payVO", payVO);
        mav.setViewName("/v2/concur/my");
		//return mav;
		
        if (payVO != null && payVO.isResult()) {
            return Result.of(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), payVO);
        } else {
            return Result.of(ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getCode(), ResultEnum.PAY_FILL_CASH_WX_PAY_FAIL.getDesc(), payVO.getMsg());
        }		
    }
	
	/*
	@RequestMapping(value="/on_topup", method= RequestMethod.POST)
	public ModelAndView onTopup(@RequestParam(value = "concurPlanId", required = false, defaultValue = "0") long concurPlanId,
								HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		ModelAndView mv = new ModelAndView();
		
		
		mv.setViewName("v2/concur/my");
		return mv;
	}	
	*/
    private boolean isApplyConcur(List<UserConcurRelationVO> list) {
        boolean isApplyConcur = false;
        if (!CollectionUtils.isEmpty(list)) {
            for (UserConcurRelationVO relationVO : list) {
                if ("保障中".equals(relationVO.getEnsureDesc())) {
                    isApplyConcur = true;
                    break;
                }
                if (relationVO.getEnsureStatus() == Status.EnsureStatus.CLIAMREFUSE.getValue()) {
                    isApplyConcur = true;
                    break;
                }
            }
        }
        return isApplyConcur;

    }

    private void handleQuantity(MyResponse myResponse, long concurPlanId) {
        long userId = myResponse.getUserDO().getUserId();
        long concurBalanceQuantity = concurManager.getConcurPlanMoneySumQuantity(concurPlanId);
        long concurUserQuantity = concurManager.getConcurPlanSumUserQuantity(concurPlanId);
        long userBalance = userManager.getBalance(userId, concurPlanId);

        myResponse.setConcurBalanceQuantity(concurBalanceQuantity);
        myResponse.setConcurUserQuantity(concurUserQuantity);
        myResponse.setUserBalanceQuantity(userBalance);


        myResponse.setConcurEventQuantity(claimApplyManager.gtCountClaimSuccess(concurPlanId));
        myResponse.setUserDonateBalanceQuantity(dunkBillManager.statisticsConsume(userId, concurPlanId));
    }


    private List<UserConcurRelationVO> conver(List<UserConcurRelationDO> list) throws Exception {
        List<UserConcurRelationVO> voList = new ArrayList<UserConcurRelationVO>();
        for (UserConcurRelationDO relationDO : list) {
			UserConcurRelationVO vo = new UserConcurRelationVO();
            BeanUtils.copyProperties(relationDO, vo);
            getDesc(vo);
            voList.add(vo);
        }

        return voList;
    }

    private void getDesc(UserConcurRelationVO vo) throws Exception {
        int birthDays = (int) DateUtils.daysBetween(vo.getBirthDay(),
                DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), DateUtils.DATA_FORMAT2) + 1;
        if (birthDays < 365) {

            vo.setAgeDesc(birthDays + "天");
        } else {
            int age = birthDays / 365;
            vo.setAgeDesc(age + "");
        }

        int between = (int) DateUtils.daysBetween(
                DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date()), vo.getEnsureStartTime(), DateUtils.DATA_FORMAT2);
        if (vo.getEnsureStatus() == Status.EnsureStatus.CLIAM.getValue()) {
            vo.setEnsureDesc("提交审核中");
        } else if (vo.getEnsureStatus() == Status.EnsureStatus.CLIAMREFUSE.getValue()) {
            vo.setEnsureDesc("理赔拒绝");
        } else if (between <= 0) {
            vo.setEnsureDesc("保障中");
        } else {
            String formatDay = DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT1, DateUtils.Convert(vo.getEnsureStartTime()));
            vo.setEnsureDesc("等待期还剩" + between + "天，至" + formatDay);
        }

        vo.setUpperLimitYuan(BizUtils.changeF2Y(vo.getUpperLimit()));
        vo.setRatioFormat(BizUtils.changeF2Y(vo.getRatio()));
    }

    private PayVO concurFeePay(HttpServletRequest request, long concurFee, long concurId, String ids) {
        SessionUserInfo sessionUserInfo = getSessionUserInfo(request);
        PayVO payVO = null;

		bizLogger.debug("DANNYDEBUG1: in concurFee");  		
        try {
            BizResult<WXPayResult> result = tradeManager.fillCash(sessionUserInfo.getUserId(), concurId, concurFee, sessionUserInfo.getOpenid(), NginxTool.getIpByNginx(request), BizFeeType.CONCURFEE.getValue(), ids);
            if (result.getModel() != null) {
                WXPayResult wxPayResult = result.getModel();
                if (WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getReturnCode()) && WXPayResult.WX_SUCCESS_CODE.equals(wxPayResult.getResultCode())) {
                    String appid = wxPayResult.getAppId();
                    String nonce_str = wxPayResult.getNonceStr();
                    String signType = "MD5";
                    String packageStr = "prepay_id=" + wxPayResult.getPrepayId();
                    String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
                    RequestHandler requestHandler = new RequestHandler();
                    requestHandler.setParameter("appId", appid);
                    requestHandler.setParameter("nonceStr", nonce_str);
                    requestHandler.setParameter("signType", signType);
                    requestHandler.setParameter("package", packageStr);
                    requestHandler.setParameter("timeStamp", timeStamp);

					
                    String sign = PayUtil.createWXPaySign("UTF-8", requestHandler.getParameters(), wxPayManager.getWxPaySecret(sessionUserInfo.getPfAppid()));
                    payVO = PayVO.ofSuccess();
                    payVO.setAppId(appid);
                    payVO.setConcurFee(concurFee);	
                    payVO.setPaySecret(nonce_str, signType, packageStr, timeStamp, sign);

					bizLogger.debug("DANNYDEBUG1: in PayVO");
					bizLogger.debug(Long.toString(concurId), "" ,concurFee);
					bizLogger.debug(appid, nonce_str, signType, packageStr, timeStamp);
					bizLogger.debug(sign);
					
					
					if (logger.isDebugEnabled()) {
                        logger.debug("member fee pay success,userId={},concurFee={}", sessionUserInfo.getUserId(), concurFee);
						bizLogger.debug("DANNYDEBUG2: ");  
                    }
                } else {
                    payVO = PayVO.ofFail();
                    logger.error("member fee pay fail,userId={},concurFee={},{}", sessionUserInfo.getUserId(), concurFee, wxPayResult);
					payVO.addMsg("member fee pay fail,userId={},concurFee={},{}" +  Long.toString(sessionUserInfo.getUserId()) + concurFee + wxPayResult);
					bizLogger.debug("DANNYDEBUG3 ");  
                }
            } else {
                payVO = PayVO.ofFail();
                logger.error("charge fail,userId={},concurFee={}", sessionUserInfo.getUserId(), concurFee);
				payVO.addMsg("charge fail,userId={},concurFee={}" + sessionUserInfo.getUserId() + concurFee);
				bizLogger.debug("DANNYDEBUG4");  
            }
        } catch (Exception e) {
            logger.error("charge is error", e);
			
            payVO = PayVO.ofFail();
			payVO.addMsg("charge is error" + e.toString());
			bizLogger.debug("DANNYDEBUG5");  
        }

        return payVO;
    }

}
