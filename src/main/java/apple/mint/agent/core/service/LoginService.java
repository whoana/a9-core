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

public class LoginService extends RequestService<Object, IIPAgentInfo> {

    Logger logger = LoggerFactory.getLogger(LoginService.class);

    private String agentNm;

    private boolean login = false;

    public LoginService(String name, String cd, String agentNm, String password, ServiceContext serviceContext,
            SendChannelWrapper sendQueueWrapper,
            boolean disabled) {
        super(cd, name, serviceContext, sendQueueWrapper, null, disabled);
        this.agentNm = agentNm;
    }

    @Override
    public ComMessage<Object, IIPAgentInfo> request() throws Exception {
        ComMessage<Object, IIPAgentInfo> msg = new ComMessage<Object, IIPAgentInfo>();
        msg.setId(UUID.randomUUID().toString());
        msg.setUserId(agentNm); //에이전트 로그인 로그아웃 시 서비스에서 nm 기준으로 처리  
        msg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
        Extension ext = new Extension();
        ext.setMsgType(Extension.MSG_TYPE_REQ);
        ext.setServiceCd(ServiceCodeConstant.WS0025);
        msg.setExtension(ext);
        return msg;
    }

    @Override
    public ComMessage<Object, IIPAgentInfo> receive(ComMessage<Object, IIPAgentInfo> response) throws Exception {
        logger.debug("msg:".concat(Util.toJSONPrettyString(response)));

        // AgentInfo 정보를 모든 서비스에서 사용 가능하도록 세팅한다.
        IIPAgentInfo agentInfo = response.getResponseObject();
        if (agentInfo != null){
            serviceContext.setAgentInfo(agentInfo);
            login = true;
        }

        return response;
    }

    
    public boolean login() {
        return login;
    }

}
