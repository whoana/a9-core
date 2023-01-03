package apple.mint.agent.core.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apple.mint.agent.core.channel.SendChannelWrapper;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.msg.handler.ServiceCodeConstant;
import pep.per.mint.common.util.Util;

public class LogoutService extends RequestService<Object, IIPAgentInfo> {

    Logger logger = LoggerFactory.getLogger(LoginService.class);

    private String agentCd;

    public LogoutService(String name, String cd, String agentCd, ServiceContext serviceContext,
            SendChannelWrapper sendQueueWrapper,
            boolean disabled) {
        super(cd, name, serviceContext, sendQueueWrapper, null, disabled);
        this.agentCd = agentCd;
    }

    @Override
    public ComMessage<Object, IIPAgentInfo> request() throws Exception {
        ComMessage<Object, IIPAgentInfo> msg = new ComMessage<Object, IIPAgentInfo>();
        msg.setId(UUID.randomUUID().toString());
        msg.setUserId(agentCd);
        msg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
        Extension ext = new Extension();
        ext.setMsgType(Extension.MSG_TYPE_REQ);
        ext.setServiceCd(ServiceCodeConstant.WS0026);
        msg.setExtension(ext);
        return msg;
    }

    @Override
    public ComMessage<Object, IIPAgentInfo> receive(ComMessage<Object, IIPAgentInfo> response) throws Exception {
        logger.debug("msg:".concat(Util.toJSONPrettyString(response)));
        IIPAgentInfo agentInfo = response.getResponseObject();
        if (agentInfo != null)
            serviceContext.setAgentInfo(agentInfo);
        return response;
    }

}
