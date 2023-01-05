package apple.mint.agent.core.service;

import pep.per.mint.common.data.basic.agent.IIPAgentInfo;

public class ServiceContext {

    IIPAgentInfo agentInfo;
    
    RestartAgentService restartAgentService;

    public synchronized IIPAgentInfo getAgentInfo() {
        return agentInfo;
    }
    
    public synchronized void setAgentInfo(IIPAgentInfo agentInfo) {
        this.agentInfo = agentInfo;
    }

    public RestartAgentService getRestartAgentService() {
        return restartAgentService;
    }

    public void setRestartAgentService(RestartAgentService restartAgentService) {
        this.restartAgentService = restartAgentService;
    }
    
    
}
