package apple.mint.agent.core.config;

import lemon.balm.core.data.S9L;
import rose.mary.trace.core.esnecil.S9LManager;

public class Config {

    String agentId;

    String agentNm;

    String agentCd;

    String password;

    String serverAddress;

    String serverPort;

    String settings;

    long tryConfigDelay = 10000;

    int maxConfigCount = 5;
 

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

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public long getTryConfigDelay() {
        return tryConfigDelay;
    }

    public void setTryConfigDelay(long tryConfigDelay) {
        this.tryConfigDelay = tryConfigDelay;
    }

    public int getMaxConfigCount() {
        return maxConfigCount;
    }

    public void setMaxConfigCount(int maxConfigCount) {
        this.maxConfigCount = maxConfigCount;
    }

}
