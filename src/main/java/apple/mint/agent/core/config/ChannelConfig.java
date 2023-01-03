package apple.mint.agent.core.config;

public class ChannelConfig {
    
    String uri;

    String [] uriParameters;

    long connectedDelay;

    long closedDelay;

    long sendDelay;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String[] getUriParameters() {
        return uriParameters;
    }

    public void setUriParameters(String[] uriParameters) {
        this.uriParameters = uriParameters;
    }

    public long getConnectedDelay() {
        return connectedDelay;
    }

    public void setConnectedDelay(long connectedDelay) {
        this.connectedDelay = connectedDelay;
    }

    public long getClosedDelay() {
        return closedDelay;
    }

    public void setClosedDelay(long closedDelay) {
        this.closedDelay = closedDelay;
    }

    public long getSendDelay() {
        return sendDelay;
    }

    public void setSendDelay(long sendDelay) {
        this.sendDelay = sendDelay;
    }

    

}
