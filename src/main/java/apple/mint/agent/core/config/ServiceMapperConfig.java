package apple.mint.agent.core.config;
 

public class ServiceMapperConfig {
    
    boolean exceptionSkipMode;

    ServiceConfig [] serviceConfigs;

    ServiceGroupConfig[] serviceGroupConfigs;    

    public ServiceConfig[] getServiceConfigs() {
        return serviceConfigs;
    }

    public void setServiceConfigs(ServiceConfig[] serviceConfigs) {
        this.serviceConfigs = serviceConfigs;
    }

    public boolean isExceptionSkipMode() {
        return exceptionSkipMode;
    }

    public void setExceptionSkipMode(boolean exceptionSkipMode) {
        this.exceptionSkipMode = exceptionSkipMode;
    }

    public ServiceGroupConfig[] getServiceGroupConfigs() {
        return serviceGroupConfigs;
    }

    public void setServiceGroupConfigs(ServiceGroupConfig[] serviceGroupConfigs) {
        this.serviceGroupConfigs = serviceGroupConfigs;
    }

    

    
}
