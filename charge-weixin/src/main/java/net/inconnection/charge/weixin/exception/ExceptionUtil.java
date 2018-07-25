package net.inconnection.charge.weixin.exception;

import net.inconnection.charge.weixin.code.RespCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class ExceptionUtil implements Serializable {
    private static final long serialVersionUID = -7043157895966852464L;
    private static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    public ExceptionUtil() {
    }

    public static Exception handlerException(Exception e) {
        Exception ex = null;
        if (!(e instanceof Exception)) {
            logger.error("无法识别的异常类型", e);
            return null;
        } else {
            try {
                if (e instanceof BizException) {
                    ex = new BizException(((BizException)e).getErrorCode(), ((BizException)e).getErrorMessage(), e);
                } else if (e instanceof Exception) {
                    ex = new BizException(((BizException)e).getErrorCode(), ((BizException)e).getErrorMessage(), e);
                }
            } catch (Exception var3) {
                logger.error("ExceptionUtil.handlerException处理异常,Exception=" + var3.getMessage());
                ex = new BizException(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            return ex;
        }
    }
}

