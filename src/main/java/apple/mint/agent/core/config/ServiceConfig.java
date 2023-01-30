package apple.mint.agent.core.config;

import java.util.Map;

public class ServiceConfig {
    String cd;

    String name;

    String description;

    String classPath;

    String className;

    Map<?, ?> params;

    boolean disabled = false;

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
 
    public Map<?, ?> getParams() {
        return params;
    }

    public void setParams(Map<?, ?> params) {
        this.params = params;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

}
