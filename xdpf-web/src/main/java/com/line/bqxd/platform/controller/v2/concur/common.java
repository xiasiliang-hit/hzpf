package com.line.bqxd.platform.controller.v2.concur;

import com.line.bqxd.platform.client.constant.ResultEnum;
import com.line.bqxd.platform.client.dataobject.ClaimApplyDO;
import com.line.bqxd.platform.common.IDCard;
import org.apache.commons.lang.StringUtils;

/**
 * Created by danny on 10/21/16.
 */
public class common {

    public static ResultEnum verify(ClaimApplyDO claimApplyDO, String bankNo, String bankName) {

        if (StringUtils.isBlank(claimApplyDO.getVerifyHospital())) {
            return ResultEnum.CLAIM_VERIFY_HOSPITAL_BLANK;
        }
        if (StringUtils.isBlank(claimApplyDO.getVerifyTime())) {
            return ResultEnum.CLAIM_VERIFY_TIME_BLANK;
        }
        if (StringUtils.isBlank(claimApplyDO.getVerifyUser())) {
            return ResultEnum.CLAIM_VERIFY_USER_BLANK;
        }
        if (claimApplyDO.getVerifyEventId() <= 0) {
            return ResultEnum.CLAIM_VERIFY_EVENT_ID_BLANK;
        }
        if (StringUtils.isBlank(claimApplyDO.getVerifyTel())) {
            return ResultEnum.CLAIM_VERIFY_TEL_FAIL;
        } else {
            if (claimApplyDO.getVerifyTel().length() < 7) {
                return ResultEnum.CLAIM_VERIFY_TEL_FAIL;
            }
        }
        ResultEnum resultEnum=verifyBank(bankNo,bankName);
        if(resultEnum!=ResultEnum.SUCCESS){
            return resultEnum;
        }
        if (StringUtils.isBlank(claimApplyDO.getDescr())) {
            return ResultEnum.CLAIM_DESCR_BLANK;
        }
        if (StringUtils.isBlank(claimApplyDO.getPicArray())) {
            return ResultEnum.CLAIM_PIC_UPLOAD_BLANK;
        }
        return ResultEnum.SUCCESS;
    }



    private static ResultEnum verifyBank(String bankNo, String bankName) {

        if (StringUtils.isBlank(bankName)) {
            return ResultEnum.CLAIM_VERIFY_BANK_NAME_BLANK;
        }

        char[] ch = bankName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!IDCard.isChinese(c)) {
                return ResultEnum.CLAIM_VERIFY_BANK_NAME_FAIL;
            }
        }

        if (bankName.length() < 4) {
            return ResultEnum.CLAIM_VERIFY_BANK_NAME_FAIL;
        }

        if (StringUtils.isBlank(bankNo)) {
            return ResultEnum.CLAIM_VERIFY_BANK_NO_BLANK;
        }
        if (!IDCard.isNumeric(bankNo)) {
            return ResultEnum.CLAIM_VERIFY_BANK_NO_FAIL;
        }
        if (bankNo.length() < 16) {
            return ResultEnum.CLAIM_VERIFY_BANK_NO_FAIL;
        }
        return ResultEnum.SUCCESS;
    }
}
