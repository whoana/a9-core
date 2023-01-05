package apple.mint.agent.core.service;

import java.util.Map;
 
import apple.mint.agent.core.channel.SendChannelWrapper;
import pep.per.mint.common.data.basic.ComMessage;

public abstract class ResponseService extends Service {

    public ResponseService(String cd, String name, ServiceContext serviceContext, SendChannelWrapper sendQueueWrapper, Map params, Boolean disabled) {
        super(cd, name, serviceContext, sendQueueWrapper, params, disabled);
    }

    public abstract ComMessage<?, ?> response(ComMessage<?, ?> request) throws Exception;

    public ComMessage<?, ?> sendResponse(ComMessage<?, ?> request) throws Exception {
        ComMessage<?, ?> response = response(request);
        if(response != null) send(response);
        return response;
    }
}
