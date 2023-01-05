package apple.mint.agent.core.service;

import java.util.LinkedHashMap;

import java.util.Map;

import apple.mint.agent.core.channel.SendChannelWrapper;
import apple.mint.agent.core.config.ServiceGroupConfig;
import pep.per.mint.common.data.basic.ComMessage;

public class ServiceManager {

    ServiceGroupConfig[] configs;

    SendChannelWrapper sendChannelWrapper;

    ServiceMapper serviceMapper;

    Map<String, ServiceGroup> groupMap;

    public ServiceManager(ServiceGroupConfig[] configs, ServiceMapper serviceMapper,
            SendChannelWrapper sendChannelWrapper) {
        this(configs, serviceMapper, sendChannelWrapper, null);
    }

    ClassLoader classLoader;

    public ServiceManager(ServiceGroupConfig[] configs, ServiceMapper serviceMapper,
            SendChannelWrapper sendChannelWrapper, ClassLoader classLoader) {
        this.configs = configs;
        this.serviceMapper = serviceMapper;
        this.sendChannelWrapper = sendChannelWrapper;
        groupMap = new LinkedHashMap<String, ServiceGroup>();
        for (ServiceGroupConfig config : configs) {
            ServiceGroup group = new ServiceGroup(config, sendChannelWrapper, serviceMapper, classLoader);
            groupMap.put(config.getId(), group);
        }
    }

    public void startServiceGroup(String groupId) {
        groupMap.get(groupId).start();
    }

    public void stopServiceGroup(String groupId) {
        groupMap.get(groupId).stop();
    }

    public void startServiceGroupAll() {
        for (ServiceGroupConfig config : configs) {
            if (!config.isDisabled())
                groupMap.get(config.getId()).start();
        }
    }

    public void stopServiceGroupAll() {
        for (ServiceGroupConfig config : configs) {
            if (!config.isDisabled())
                groupMap.get(config.getId()).stop();
        }
    }

    public ComMessage<?, ?> executeService(String serviceCd, ComMessage<?, ?> request) throws Exception {
        Service service = serviceMapper.getService(serviceCd);
        if (service == null)
            throw new Exception("The service is not found by name:".concat(serviceCd));

        ComMessage<?, ?> msg = null;
        if (service instanceof RequestService) {
            msg = ((RequestService) service).sendRequest();
        } else if (service instanceof ResponseService) {
            msg = ((ResponseService) service).sendResponse(request);
        } else if (service instanceof PushService) {
            msg = ((PushService) service).push();
        } else {
            throw new Exception(
                    "The service is not supported. The Only supported services are RequestService, ResponseService, PushSerice.");
        }
        return msg;
    }

}
