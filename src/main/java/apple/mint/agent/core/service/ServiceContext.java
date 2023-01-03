package apple.mint.agent.core.service;

import pep.per.mint.common.data.basic.agent.IIPAgentInfo;

public class ServiceContext {

    IIPAgentInfo agentInfo;

    public synchronized IIPAgentInfo getAgentInfo() {
        return agentInfo;
    }

    public synchronized void setAgentInfo(IIPAgentInfo agentInfo) {
        this.agentInfo = agentInfo;
    }

}
