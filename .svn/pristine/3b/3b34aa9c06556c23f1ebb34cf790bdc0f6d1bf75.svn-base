package com.line.bqxd.platform.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjianfei on 16/5/25.
 */
public class CycleLifeManagerImpl implements CycleLifeManager, CycleLife  {

    private static Logger logger = LoggerFactory.getLogger(CycleLifeManagerImpl.class);

    private List<CycleLife> cycleLifeList = new ArrayList<CycleLife>();

    @Override
    public void init() {
/*
        logger.warn("CycleLifeManager init print start");

        for(CycleLife cycleLife:cycleLifeList){
            logger.warn("CycleLife init object {}",cycleLife);

        }
        logger.warn("CycleLifeManager init print end");

*/
    }

    @Override
    public void reInit() {
        logger.warn("CycleLifeManager rtInit print start");

        for(CycleLife cycleLife:cycleLifeList){
            logger.warn("CycleLifeManager rtInit object, {}",cycleLife);
            cycleLife.reInit();

        }
        logger.warn("CycleLifeManager rtInit print end");
    }


    @Override
    public void stop() {

    }

    @Override
    public void addCycleLife(CycleLife cycleLife) {
        if(!cycleLifeList.contains(cycleLife)) {
            cycleLifeList.add(cycleLife);
        }
    }

    @Override
    public void removeCycleLife(CycleLife cycleLife) {
        cycleLifeList.remove(cycleLife);
    }

}
