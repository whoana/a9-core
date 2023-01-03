package apple.mint.agent.core.config;

public class Config {
    
    String agentId;

    String agentNm;

    String agentCd;

    String password;

    ChannelConfig channelConfig;

    ChannelWrapperConfig channelWrapperConfig;

    // ServiceConfig [] serviceConfigs;

    ServiceMapperConfig serviceMapperConfig;

    ClassLoaderConfig classLoaderConfig;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentNm() {
        return agentNm;
    }

    public void setAgentNm(String agentNm) {
        this.agentNm = agentNm;
    }

    public String getAgentCd() {
        return agentCd;
    }

    public void setAgentCd(String agentCd) {
        this.agentCd = agentCd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ChannelConfig getChannelConfig() {
        return channelConfig;
    }

    public void setChannelConfig(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
    }

    public ChannelWrapperConfig getChannelWrapperConfig() {
        return channelWrapperConfig;
    }

    public void setChannelWrapperConfig(ChannelWrapperConfig channelWrapperConfig) {
        this.channelWrapperConfig = channelWrapperConfig;
    }

    
    // public ServiceConfig[] getServiceConfigs() {
    //     return serviceConfigs;
    // }

    // public void setServiceConfigs(ServiceConfig[] serviceConfigs) {
    //     this.serviceConfigs = serviceConfigs;
    // }

    public ServiceMapperConfig getServiceMapperConfig() {
        return serviceMapperConfig;
    }

    public void setServiceMapperConfig(ServiceMapperConfig serviceMapperConfig) {
        this.serviceMapperConfig = serviceMapperConfig;
    }

    public ClassLoaderConfig getClassLoaderConfig() {
        return classLoaderConfig;
    }

    public void setClassLoaderConfig(ClassLoaderConfig classLoaderConfig) {
        this.classLoaderConfig = classLoaderConfig;
    }

    
    
    
}
