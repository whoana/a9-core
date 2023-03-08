package apple.mint.agent.core.service;

import java.util.Map;

import apple.mint.agent.core.channel.SendChannelWrapper;
import pep.per.mint.common.data.basic.ComMessage;

public abstract class PushService extends Service {

    public PushService(String cd, String name, ServiceContext serviceContext, SendChannelWrapper sendQueueWrapper, Map params, Boolean disabled) {
        super(cd, name, serviceContext, sendQueueWrapper, params, disabled);
    }

    abstract public ComMessage<?, ?> makePushMessage() throws Exception;
     
    public ComMessage<?, ?> push() throws Exception {
        ComMessage<?, ?> msg = makePushMessage();
        if(msg != null) send(msg);
        return msg;
    }
}
