package apple.mint.agent.core.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apple.mint.agent.core.channel.SendChannelWrapper;
import pep.per.mint.common.data.basic.ComMessage;

public abstract class Service {

    protected Logger logger = LoggerFactory.getLogger(Service.class);

    protected String cd;

    protected String name;

    protected SendChannelWrapper sendChannelWrapper;

    protected Map<?, ?> params;

    protected String version = "1.0";

    protected Boolean disabled = false;

    protected ServiceContext serviceContext;

    public Service(String cd, String name, ServiceContext serviceContext, SendChannelWrapper sendChannelWrapper, Map<?, ?> params, Boolean disabled) {
        this.cd = cd;
        this.name = name;
        this.serviceContext = serviceContext;
        this.sendChannelWrapper = sendChannelWrapper;
        this.params = params;
        this.disabled = disabled;
    }

    public void send(ComMessage<?, ?> msg) throws Exception {
        sendChannelWrapper.send(msg);
    }
 
    public String getCd() {
        return cd;
    }

    public void setServiceCd(String cd) {
        this.cd = cd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    public void setServiceContext(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    
}
