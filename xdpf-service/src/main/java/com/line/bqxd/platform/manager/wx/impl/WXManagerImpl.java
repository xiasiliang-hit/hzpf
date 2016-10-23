package com.line.bqxd.platform.manager.wx.impl;

import com.line.bqxd.platform.client.dataobject.UserDO;
import com.line.bqxd.platform.client.dataobject.WXAttentionDO;
import com.line.bqxd.platform.client.dataobject.query.WXAttentionQueryDO;
import com.line.bqxd.platform.dao.WXAttentionDAO;
import com.line.bqxd.platform.manager.concur.ConcurManager;
import com.line.bqxd.platform.manager.user.UserManager;
import com.line.bqxd.platform.manager.wx.WXManager;
import com.line.bqxd.platform.manager.wxmenu.WXMenuManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/17.
 */
public class WXManagerImpl implements WXManager {
    private static Logger logger = LoggerFactory.getLogger(WXManagerImpl.class);

    @Resource
    private WXAttentionDAO<WXAttentionDO> wxAttentionDAO;

    @Resource
    private ConcurManager concurManager;

    private final static String SERVE_TAG = "serve";

    @Resource
    private UserManager userManager;

    @Override
    public void wxServeAttention(String openId) {
        if (StringUtils.isBlank(openId)) {
            return;
        }
        WXAttentionDO wxAttentionDO = new WXAttentionDO();
        wxAttentionDO.setTag(SERVE_TAG);
        wxAttentionDO.setOpenId(openId);
        long rt = wxAttentionDAO.insert(wxAttentionDO);
        if (rt > 0) {
            //TODO 待处理
            //concurManager.addWXAttentionAmount(openId, 1);

            //concurManager.addWXAttentionAmount(openId, Long.parseLong(concurManager.getDefaultConcurPlanId()));
        } else {
            logger.warn("wxServeAttention insert fail,{}", wxAttentionDO);
        }
    }

    @Override
    public void wxServeCancleAttention(String openId) {
        if (StringUtils.isBlank(openId)) {
            return;
        }

        boolean rt = wxAttentionDAO.deleteByOpenId(openId);
        if (rt) {
            //TODO 待处理
            //concurManager.cleanWXAttentionAmount(openId, 1);
            //concurManager.cleanWXAttentionAmount(openId, Long.parseLong(concurManager.getDefaultConcurPlanId()));

            UserDO userDO=userManager.selectByOpenId(openId);
            if (userDO != null) {
                logger.warn("user cancle attention,userId={},openId={},userName={},mobile={}", userDO.getUserId(), openId, userDO.getUserName(), userDO.getMobile());
            }
        } else {
            logger.warn("wxServeCancleAttention insert fail,{}", openId);

        }
    }

    @Override
    public boolean isUserAttention(String openId) {
        WXAttentionQueryDO wxAttentionQueryDO = new WXAttentionQueryDO();
        wxAttentionQueryDO.setOpenId(openId);
        List<WXAttentionDO> list = wxAttentionDAO.selectByQuery(wxAttentionQueryDO);
        return CollectionUtils.isEmpty(list) ? false : true;
    }
}
