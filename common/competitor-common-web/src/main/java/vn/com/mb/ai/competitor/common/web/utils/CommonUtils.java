package vn.com.mb.ai.competitor.common.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import vn.com.mb.ai.competitor.common.constants.Constants;

import java.util.Objects;
import java.util.UUID;

/**
 * @author hungdp
 */
@Slf4j
public final class CommonUtils {
    private CommonUtils() {

    }

    public static String getClientMessageId() {
        String clientMessageId = ThreadContext.get(Constants.CLIENT_MESSAGE_ID);
        if (StringUtils.isBlank(clientMessageId)) {
            clientMessageId = UUID.randomUUID().toString();
            setClientMessageId(clientMessageId);
        }
        return clientMessageId;
    }

    public static void setClientMessageId(String clientMessageId) {
        String oldClientMessageId = ThreadContext.get(Constants.CLIENT_MESSAGE_ID);
        if (!Objects.equals(clientMessageId, oldClientMessageId)) {
            ThreadContext.put(Constants.CLIENT_MESSAGE_ID, clientMessageId);
            log.info("oldClientMessageId: {}", oldClientMessageId);
        }
    }
}
