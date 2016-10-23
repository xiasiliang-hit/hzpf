package com.line.bqxd.platform.domain;

import com.line.bqxd.platform.client.common.Base;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by huangjianfei on 16/5/16.
 */
public class ActivityDomain extends Base{
    public final static String APP_MESSAGE_FROM="a";

    public final static String TIME_LINE_FROM="t";

    private long activityId;
    private long shareUserId;

    private long shareTextId;

    private long channelId;

    //分享的层次
    private int shareLevel;

    private String from;

    private long concurPlanId;
    private long concurPlanGruopId;


    public ActivityDomain(long activityId, long shareUserId, long shareTextId, long channelId, int shareLevel, String from) {
        this.activityId = activityId;
        this.shareUserId = shareUserId;
        this.shareTextId = shareTextId;
        this.channelId = channelId;
        this.shareLevel = shareLevel;
        this.from = from;
    }

    public ActivityDomain(long shareUserId, long concurPlanId, long concurPlanGruopId) {
        this.concurPlanId = concurPlanId;
        this.concurPlanGruopId = concurPlanGruopId;
    }

    public ActivityDomain( long concurPlanId, long concurPlanGruopId) {
        this.concurPlanId = concurPlanId;
        this.concurPlanGruopId = concurPlanGruopId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(long shareUserId) {
        this.shareUserId = shareUserId;
    }

    public long getConcurPlanId() {
        return concurPlanId;
    }

    public void setConcurPlanId(long concurPlanId) {
        this.concurPlanId = concurPlanId;
    }

    public long getConcurPlanGruopId() {
        return concurPlanGruopId;
    }

    public void setConcurPlanGruopId(long concurPlanGruopId) {
        this.concurPlanGruopId = concurPlanGruopId;
    }

    public int getShareLevel() {
        return shareLevel;
    }

    public void setShareLevel(int shareLevel) {
        this.shareLevel = shareLevel;
    }

    public long getShareTextId() {
        return shareTextId;
    }

    public void setShareTextId(long shareTextId) {
        this.shareTextId = shareTextId;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isExsitShareUser() {
        return shareUserId > 0 ? true : false;
    }

    public boolean isActivityUser() {
        if (activityId > 0 && shareUserId > 0 && shareTextId > 0 && channelId > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isConcurPlan() {
        if (concurPlanId > 0 && concurPlanGruopId > 0) {
            return true;
        } else {
            return false;
        }
    }
}
