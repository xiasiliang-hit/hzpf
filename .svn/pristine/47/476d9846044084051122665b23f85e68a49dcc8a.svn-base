package com.line.bqxd.platform.manager.claim;

import com.line.bqxd.platform.client.dataobject.ClaimApplyDO;
import com.line.bqxd.platform.client.dataobject.query.UserClaimApplyQueryDO;

import java.util.List;

/**
 * Created by huangjianfei on 16/9/19.
 */
public interface ClaimApplyManager {

    /**
     *
     * @param claimApplyDO
     * @return
     */
    public boolean insert(ClaimApplyDO claimApplyDO);

    /**
     *
     * @param claimApplyDO
     * @return
     */
    public boolean update(ClaimApplyDO claimApplyDO);

    /**
     *
     * @param queryDO
     * @return
     */
    public List<ClaimApplyDO> selectByQuery(UserClaimApplyQueryDO queryDO);

    /**
     *
     * @param queryDO
     * @return
     */
    public int countByQuery(UserClaimApplyQueryDO queryDO);

    /**
     * 获取互助成功理赔事件
     * @param concurPlanId
     * @return
     */
    public int gtCountClaimSuccess(long concurPlanId);

    /**
     *
     * @param id
     * @return
     */
    public ClaimApplyDO selectById(long id);

    /**
     * 案件发布理赔
     * @param id
     */
    public void publishClaimById(long id);

    /**
     * 案件打款开始
     * @param id
     */
    public void bandyClaimById(long id);

    /**
     * 案件结束
     * @param id
     */
    public void endClaimById(long id,String bankProofPic);

    /**
     * 关闭案件
     * @param id
     */
    public void closeClaimById(long id);

    /**
     * 案件拒绝
     * @param id

     */
    public void refushClaimById(long id,String result,int verifyUserResult,int verifyResult);

    /**
     * 案件平台受理
     * @param id
     */
    public void acceptClaimById(long id,String result,int verifyUserResult,int verifyResult,long preCollectMoney);
    /**
     * 案件平台接收
     * @param id
     */
    public void receiveClaimById(long id);

    /**
     * 案件筹款结束
     * @param id
     */
    public void collectingClaimById(long id);


    /**
     * 获取结束理赔数量
     * @param concurPlanId
     * @return
     */
    public int getCountEndClaimByConcurPlanId(long concurPlanId);

    /**
     * 下载图片到本地服务器
     * @param appid
     * @param serverId
     * @return
     */
    public String downloadPic(String appid,String serverId);

    public String getDownloadPicPrefix();




}
