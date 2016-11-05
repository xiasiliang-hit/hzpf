package com.line.bqxd.platform.manager.claim.impl;

import com.alibaba.fastjson.JSON;
import com.line.bqxd.platform.client.constant.BillListType;
import com.line.bqxd.platform.client.constant.BizTradeType;
import com.line.bqxd.platform.client.constant.Status;
import com.line.bqxd.platform.client.dataobject.*;
import com.line.bqxd.platform.client.dataobject.query.UserClaimApplyQueryDO;
import com.line.bqxd.platform.client.utils.BizUtils;
import com.line.bqxd.platform.client.utils.DateUtils;
import com.line.bqxd.platform.common.HttpRetryHandle;
import com.line.bqxd.platform.dao.UserClaimApplyDAO;
import com.line.bqxd.platform.manager.claim.ClaimApplyManager;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.dunk.DunkBillManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.user.UserPayManager;
import com.line.bqxd.platform.manager.wx.TemplateMsgManager;
import com.line.bqxd.platform.manager.wx.dataobject.TemplateMsgDO;
import com.line.bqxd.platform.v2.PfApplication;
import com.line.bqxd.platform.v2.manager.PfWeixinAuthManager;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * Created by huangjianfei on 16/9/19.
 */
public class ClaimApplyManagerImpl implements ClaimApplyManager {
    public final static List<Integer> PUSHLISTSTATUS = new ArrayList<Integer>();
    public final static List<Integer> NOPUSHLISTSTATUS = new ArrayList<Integer>();
    public final static List<Integer> MUSTCLAIMSTATUS = new ArrayList<Integer>();
    private static final Logger logger = LoggerFactory.getLogger(ClaimApplyManagerImpl.class);

    static {
        PUSHLISTSTATUS.add(Status.ClaimApply.PUBLISH.getValue());
        PUSHLISTSTATUS.add(Status.ClaimApply.COLLECTEND.getValue());
        PUSHLISTSTATUS.add(Status.ClaimApply.BANDY.getValue());
        PUSHLISTSTATUS.add(Status.ClaimApply.END.getValue());
        PUSHLISTSTATUS.add(Status.ClaimApply.OPPUGN.getValue());

        NOPUSHLISTSTATUS.add(Status.ClaimApply.NEW.getValue());
        NOPUSHLISTSTATUS.add(Status.ClaimApply.RECEIVE.getValue());
        NOPUSHLISTSTATUS.add(Status.ClaimApply.CHECK.getValue());
        NOPUSHLISTSTATUS.add(Status.ClaimApply.ACCEPT.getValue());
        NOPUSHLISTSTATUS.add(Status.ClaimApply.REFUSE.getValue());

        MUSTCLAIMSTATUS.add(Status.ClaimApply.BANDY.getValue());
        MUSTCLAIMSTATUS.add(Status.ClaimApply.END.getValue());
    }

    private static final String DOWNLOAD_PIC_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=";

    private String uploadPicPath = null;

    private String downloadPicPrefix = null;

    @Autowired
    private UserClaimApplyDAO<ClaimApplyDO> userClaimApplyDAO;

    @Autowired
    private ConcurManager concurManager;

    @Autowired
    private PfApplication pfApplication;

    @Autowired
    private HttpClient bqxdHttpClient;

    @Autowired
    private HttpRetryHandle bqxdHttpRetryHandle;

    @Autowired
    private DunkBillManager dunkBillManager;

    @Autowired
    private UserPayManager userPayManager;

    private final static int BUFFER = 1024;

    @Autowired
    private TemplateMsgManager templateMsgManager;

    @Autowired
    private PfWeixinAuthManager pfWeixinAuthManager;

    @Autowired
    private UserManager userManager;

    private ExecutorService executorService = Executors.newFixedThreadPool(1,
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "dunk balance thread");
                }
            });

    @Override
    public boolean insert(ClaimApplyDO claimApplyDO) {
        String currentDaty = DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date());
        claimApplyDO.setStatus(Status.ClaimApply.NEW.getValue());
        claimApplyDO.setEventSubmitDay(currentDaty);
        long joinUserCount = concurManager.getBeforeUserCountByConcurPlianID(claimApplyDO.getConcurPlanId(), currentDaty);
        claimApplyDO.setPreJoinUserCount(joinUserCount);
        long rt = userClaimApplyDAO.insert(claimApplyDO);
        return rt > 0 ? true : false;
    }

    @Override
    public boolean update(ClaimApplyDO claimApplyDO) {
        return userClaimApplyDAO.update(claimApplyDO);
    }

    @Override
    public List<ClaimApplyDO> selectByQuery(UserClaimApplyQueryDO queryDO) {
        return userClaimApplyDAO.selectByQuery(queryDO);
    }

    @Override
    public int countByQuery(UserClaimApplyQueryDO queryDO) {
        return userClaimApplyDAO.countByQuery(queryDO);
    }

    @Override
    public int gtCountClaimSuccess(long concurPlanId) {
        UserClaimApplyQueryDO queryDO = new UserClaimApplyQueryDO();
        queryDO.setConcurPlanId(concurPlanId);
        queryDO.setStatusList(MUSTCLAIMSTATUS);
        return countByQuery(queryDO);
    }

    @Override
    public ClaimApplyDO selectById(long id) {
        return (ClaimApplyDO) userClaimApplyDAO.selectById(id);
    }

    @Override
    public void publishClaimById(long id) {
        ClaimApplyDO claimApplyDO = selectById(id);
        claimApplyDO.setPublishStartDay(getCurrentDay());
        claimApplyDO.setMonthIndex(getMonthIndex(claimApplyDO.getConcurPlanId()));
        claimApplyDO.setStatus(Status.ClaimApply.PUBLISH.getValue());
        update(claimApplyDO);
    }

    @Override
    public void bandyClaimById(long id) {
        ClaimApplyDO claimApplyDO = selectById(id);
        claimApplyDO.setBandyStartDay(getCurrentDay());
        claimApplyDO.setStatus(Status.ClaimApply.BANDY.getValue());
        update(claimApplyDO);
    }

    @Override
    public void endClaimById(long id, String bankProofPic) {
        ClaimApplyDO claimApplyDO = selectById(id);
        claimApplyDO.setEventEndDay(getCurrentDay());
        claimApplyDO.setStatus(Status.ClaimApply.END.getValue());
        claimApplyDO.setBankProofPic(bankProofPic);
        update(claimApplyDO);
    }

    @Override
    public void closeClaimById(long id) {
        ClaimApplyDO claimApplyDO = selectById(id);
        claimApplyDO.setStatus(Status.ClaimApply.CLOSE.getValue());
        update(claimApplyDO);
    }

    @Override
    public void refushClaimById(long id, String result, int verifyUserResult, int verifyResult) {
        ClaimApplyDO claimApplyDO = selectById(id);
        claimApplyDO.setStatus(Status.ClaimApply.REFUSE.getValue());
        claimApplyDO.setResult(result);
        claimApplyDO.setVerifyResult(verifyResult);
        claimApplyDO.setVerifyUserResult(verifyUserResult);
        update(claimApplyDO);
        //用户保障拒绝
        concurManager.refuseClaimRelation(claimApplyDO.getUserConcurRelationId());
    }

    @Override
    public void acceptClaimById(long id, String result, int verifyUserResult, int verifyResult, long preCollectMoney) {
        ClaimApplyDO claimApplyDO = selectById(id);
        claimApplyDO.setStatus(Status.ClaimApply.ACCEPT.getValue());
        claimApplyDO.setResult(result);
        claimApplyDO.setVerifyResult(verifyResult);
        claimApplyDO.setVerifyUserResult(verifyUserResult);
        claimApplyDO.setPreCollectMoney(preCollectMoney);
        update(claimApplyDO);
    }


    @Override
    public void receiveClaimById(long id) {
        ClaimApplyDO claimApplyDO = selectById(id);
        claimApplyDO.setStatus(Status.ClaimApply.RECEIVE.getValue());
        update(claimApplyDO);
    }

    @Override
    public void collectingClaimById(long id) {
        //异步线程处理
        ClaimApplyDO claimApplyDO = selectById(id);
        dunkBalance(claimApplyDO);
    }

    private void dunkBalance(final ClaimApplyDO claimApplyDO) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Date date = claimApplyDO.getGmtCreate();
                long cliamRelationId = claimApplyDO.getUserConcurRelationId();
                ConcurPlanDO concurPlanDO = concurManager.getConcurPlanById(claimApplyDO.getConcurPlanId());
                PfWeixinAuthDO pfWeixinAuthDO=pfWeixinAuthManager.selectByid(concurPlanDO.getPfAppId());

                UserConcurRelationDO cliamRelationDO = concurManager.selectUserConcurRelation(cliamRelationId);
                String startJoinDate = DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, date);
                long ratioAll = concurManager.statisticsRatio(claimApplyDO.getConcurPlanId(), startJoinDate);
                long perCollectMoney = claimApplyDO.getPreCollectMoney();
                long remainder = perCollectMoney % ratioAll;
                //每个系数需要扣款
                long preRatioMoney = remainder > 0 ? (perCollectMoney / ratioAll + 1) : (perCollectMoney / ratioAll);
                List<UserConcurRelationDO> list = concurManager.getBeforeUserListByConcurPlianID(claimApplyDO.getConcurPlanId(), startJoinDate);
                if (CollectionUtils.isEmpty(list)) {
                    return;
                }
                long allRealDunkBalance = 0;
                long count = 0;
                for (UserConcurRelationDO userConcurRelationDO : list) {
                    long requireDunkBalance = userConcurRelationDO.getRatio() * preRatioMoney;
                    long upperLimit = userConcurRelationDO.getUpperLimit();
                    long realDunkBalance = 0;
                    if (upperLimit > requireDunkBalance) {
                        realDunkBalance = requireDunkBalance;
                    } else {
                        realDunkBalance = upperLimit;
                    }
                    //同一个人保障用户不需要理赔
                    if(userConcurRelationDO.getId()==cliamRelationDO.getId()){
                        logger.warn("claim user the same userRelaitonId={}",cliamRelationDO.getId());
                        continue;
                    }
                    try {
                        long userBalance = userManager.getBalance(userConcurRelationDO.getUserId(), userConcurRelationDO.getConcurPlanId());
                        if (userBalance >= realDunkBalance) {
                            String explains = "支付" + cliamRelationDO.getEnsureName() + "互助费用";
                            //执行扣款操作
                            userManager.reduceBalance(userConcurRelationDO.getUserId(), userConcurRelationDO.getConcurPlanId(), realDunkBalance);
                            DunkBillDO dunkBillDO = new DunkBillDO();
                            dunkBillDO.setBalance(realDunkBalance);
                            dunkBillDO.setUserClaimId(claimApplyDO.getId());
                            dunkBillDO.setUserId(userConcurRelationDO.getUserId());
                            dunkBillDO.setUserConcurRelationId(userConcurRelationDO.getId());
                            dunkBillDO.setConcurPlanId(userConcurRelationDO.getConcurPlanId());
                            dunkBillDO.setExplains(explains);
                            if (dunkBillManager.inert(dunkBillDO)) {
                                allRealDunkBalance += realDunkBalance;
                            }
                            String openId=userManager.getOpenId(pfWeixinAuthDO.getId(),userConcurRelationDO.getUserId());
                            insertTradeBill(userConcurRelationDO, claimApplyDO, cliamRelationDO, explains, realDunkBalance);
                            sendMsg(concurPlanDO,userConcurRelationDO,claimApplyDO,realDunkBalance,pfWeixinAuthDO.getAuthorizerAppid(),openId);
                            count++;
                        } else {
                            //userManager.delUser();
                            boolean reslut = concurManager.invalidUserRelation(userConcurRelationDO.getId());
                            if (reslut) {
                                logger.error("user relation invaild success,relationId={},claimId={}", userConcurRelationDO.getId(), claimApplyDO.getId());
                            } else {
                                logger.error("user relation invaild fail,relationId={},claimId={}", userConcurRelationDO.getId(), claimApplyDO.getId());
                            }
                        }
                    } catch (RuntimeException e) {
                        logger.error("dunk user balance fail", e);
                    }

                }

                ClaimApplyDO updateDO = selectById(claimApplyDO.getId());
                updateDO.setStatus(Status.ClaimApply.COLLECTEND.getValue());
                updateDO.setCollectMoney(allRealDunkBalance);
                updateDO.setJoinUserCount(count);
                updateDO.setDunkDay(getCurrentDay());
                update(updateDO);
                //update.setCoolectDay();

            }
        });
    }
    private void sendMsg(ConcurPlanDO concurPlanDO, UserConcurRelationDO userConcurRelationDO, ClaimApplyDO claimApplyDO, long fee,String authAppId,String openId){
        try{
            TemplateMsgDO templateMsgDO= templateMsgManager.getDunkBalanceMsgDO(concurPlanDO,userConcurRelationDO.getEnsureName(),claimApplyDO.getId(),openId,fee);
            templateMsgManager.sendWxTemplateNotify(templateMsgDO,authAppId);
        }catch (Exception e){
            logger.error("send msg fail",e);
        }
    }

    private void insertTradeBill(UserConcurRelationDO userConcurRelationDO, ClaimApplyDO claimApplyDO, UserConcurRelationDO cliamRelationDO, String explains,long fee) {
        Map<String,String> map =new HashMap<String,String>();
        map.put("claimId",String.valueOf(claimApplyDO.getId()));
        map.put("bizFeeType","concurfee");

        UserTradeBillDO userTradeBillDO = new UserTradeBillDO();
        userTradeBillDO.setExplains(explains);
        userTradeBillDO.setFee(fee);
        String tradeId = BizUtils.getTradeIdV1("P", BizTradeType.PAY);
        userTradeBillDO.setTradeId(tradeId);
        userTradeBillDO.setUserId(userConcurRelationDO.getUserId());
        userTradeBillDO.setAttach(JSON.toJSONString(map));
        userTradeBillDO.setListType(BillListType.PAY.getValue());
        userTradeBillDO.setConcurId(claimApplyDO.getConcurPlanId());
        boolean result=userPayManager.insertUserTradeBillDO(userTradeBillDO);
        if(result){
            if(logger.isDebugEnabled()){
                logger.debug("insertTradeBill success,{}",userTradeBillDO);
            }
        }else{
            logger.debug("insertTradeBill error,{}",userTradeBillDO);
        }
    }

    @Override
    public int getCountEndClaimByConcurPlanId(long concurPlanId) {
        UserClaimApplyQueryDO queryDO = new UserClaimApplyQueryDO();
        queryDO.setStatusList(MUSTCLAIMSTATUS);
        queryDO.setConcurPlanId(concurPlanId);
        return userClaimApplyDAO.countByQuery(queryDO);

    }

    @Override
    public String downloadPic(String appid, String serverId) {
        String accessToken = pfApplication.getAccessTokenByAppid(appid);
        String url = builderUrl(accessToken, serverId).toString();
        GetMethod httpGet = new GetMethod(url);
        InputStream in = null;
        FileOutputStream out = null;
        try {
            httpGet.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, bqxdHttpRetryHandle);
            bqxdHttpClient.executeMethod(httpGet);

            in = httpGet.getResponseBodyAsStream();
            long length = httpGet.getResponseContentLength();
            boolean ispic = isPic(httpGet);
            String fileName = null;
            if (ispic) {
                fileName = getFileName(httpGet);
                String filePath = uploadPicPath + fileName;
                out = new FileOutputStream(new File(filePath));
                byte[] b = new byte[BUFFER];
                int len = 0;
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
                logger.warn("downloadPic url={}", filePath);
            } else {
                byte[] b = new byte[(int) length];
                in.read(b);
                logger.error("downloadPic error,{}", new String(b));
            }

            return fileName;
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            httpGet.releaseConnection();
        }
        return null;
    }

    @Override
    public String getDownloadPicPrefix() {
        return this.downloadPicPrefix;
    }

    private static boolean isPic(GetMethod httpGet) {
        Header typeHeader = httpGet.getResponseHeader("Content-Type");
        if (typeHeader != null) {
            String value = typeHeader.getValue();
            if (value.indexOf("text") >= 0) {
                return false;
            } else {
                return true;
            }

        }
        return true;
    }

    private static String getFileName(GetMethod httpGet) {
        String fileName = null;
        if (httpGet == null) {
            return null;
        }
        Header header = httpGet.getResponseHeader("Content-disposition");
        if (header != null) {
            logger.warn("download pic Content-disposition={}", header);
            String value = header.getValue();
            String[] array = value.split("=");
            if (array != null && array.length == 2) {
                fileName = array[1].replaceAll("\"", "");
            }
        } else {
            Header typeHeader = httpGet.getResponseHeader("Content-Type");
            if (typeHeader != null) {
                logger.warn("download pic Content-Type={}", typeHeader);
                String value = typeHeader.getValue();
                if (value.indexOf("jpeg") >= 0 || value.indexOf("jpg") >= 0) {
                    fileName = System.currentTimeMillis() + ".jpg";
                }
            }
        }

        if (StringUtils.isBlank(fileName)) {
            logger.warn("download pic response header {}", httpGet.getResponseHeaders());
            fileName = System.currentTimeMillis() + ".png";
        }
        return fileName;
    }

    private String builderUrl(String accessToken, String serverId) {
        StringBuilder sb = new StringBuilder(DOWNLOAD_PIC_URL);
        sb.append(accessToken);
        sb.append("&media_id=");
        sb.append(serverId);
        return sb.toString();
    }

    private int getMonthIndex(long concurPlanId) {
        Date current = new Date();
        UserClaimApplyQueryDO queryDO = new UserClaimApplyQueryDO();
        queryDO.setStartSubmitDay(getFirstDayOfMonth(current));
        queryDO.setEndSubmitDay(getLastDayOfMonth(current));
        queryDO.setStatusList(PUSHLISTSTATUS);
        queryDO.setConcurPlanId(concurPlanId);
        int count = userClaimApplyDAO.countByQuery(queryDO);
        return ++count;
    }

    private static String getCurrentDay() {
        return DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, new Date());
    }

    /**
     * 得到本月第一天的日期
     *
     * @return Date
     * @Methods Name getFirstDayOfMonth
     */
    public static String getFirstDayOfMonth(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, cDay.getTime());
    }

    /**
     * 得到本月最后一天的日期
     *
     * @return Date
     * @Methods Name getLastDayOfMonth
     */
    public static String getLastDayOfMonth(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DateUtils.getDateTimeFormat(DateUtils.DATA_FORMAT2, cDay.getTime());
    }

    public static void main(String[] args) {
        /*
        Date d = new Date();
        System.out.println(getFirstDayOfMonth(d));
        System.out.println(getLastDayOfMonth(d));
        */
        long perCollectMoney = 123;
        long ratioAll = 5;
        System.out.println(perCollectMoney % ratioAll);
    }

    public void setUploadPicPath(String uploadPicPath) {
        this.uploadPicPath = uploadPicPath;
    }

    public void setDownloadPicPrefix(String downloadPicPrefix) {
        this.downloadPicPrefix = downloadPicPrefix;
    }


}
