package apple.mint.agent.core.config;

public class ServiceGroupConfig {
    
    String id;

    String name;

    long delay = 5000;

    long delayBetweenService = 10;

    boolean executeExceptionSkipMode = true;
    
    String[] serviceCds;

    boolean disabled = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getDelayBetweenService() {
        return delayBetweenService;
    }

    public void setDelayBetweenService(long delayBetweenService) {
        this.delayBetweenService = delayBetweenService;
    }

    public boolean isExecuteExceptionSkipMode() {
        return executeExceptionSkipMode;
    }

    public void setExecuteExceptionSkipMode(boolean executeExceptionSkipMode) {
        this.executeExceptionSkipMode = executeExceptionSkipMode;
    }

    public String[] getServiceCds() {
        return serviceCds;
    }

    public void setServiceCds(String[] serviceCds) {
        this.serviceCds = serviceCds;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    
}
