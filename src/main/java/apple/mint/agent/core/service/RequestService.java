package apple.mint.agent.core.service;

import java.util.Map;

import apple.mint.agent.core.channel.SendChannelWrapper;
import pep.per.mint.common.data.basic.ComMessage;

public abstract class RequestService<S, R> extends Service {

    public RequestService(String cd, String name, ServiceContext serviceContext, SendChannelWrapper sendQueueWrapper, Map params, Boolean disabled) {
        super(cd, name, serviceContext, sendQueueWrapper, params, disabled);
    }

    /**
     * This method will be done when the response is comming.
     */
    public abstract ComMessage<S, R> receive(ComMessage<S, R> response) throws Exception;

    public abstract ComMessage<S, R> request() throws Exception;

    /**
     * <pre>
     *  보낼 메시지가 NULL 값이면 예외처리? 할지 그냥 전송하지 말지 고민해보자 20221223 
     * </pre>
     * @return
     * @throws Exception
     */
    public ComMessage<S, R> sendRequest() throws Exception {
        ComMessage<S, R> request = request();
        if(request != null) send(request);
        return request;
    }
}
