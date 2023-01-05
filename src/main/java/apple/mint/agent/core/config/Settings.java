package apple.mint.agent.core.config;

public class Settings {
    
    String version;

    String author;

    String date;
 

    ChannelConfig channelConfig;

    ChannelWrapperConfig channelWrapperConfig; 

    ServiceMapperConfig serviceMapperConfig;

    ClassLoaderConfig classLoaderConfig;
 
    

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
