package apple.mint.agent.core.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apple.mint.agent.core.channel.ChannelState;
import apple.mint.agent.core.channel.SendChannelWrapper;
import apple.mint.agent.core.config.ServiceGroupConfig;
import pep.per.mint.common.data.basic.ComMessage;

public class ServiceGroup implements Runnable {

    Logger logger = LoggerFactory.getLogger(ServiceGroup.class);

    ServiceGroupConfig config;

    SendChannelWrapper sendChannelWrapper;

    ServiceMapper serviceMapper;

    long lastRequested;

    Thread thread;

    ClassLoader classLoader;

    public ServiceGroup(ServiceGroupConfig config, SendChannelWrapper sendChannelWrapper, ServiceMapper serviceMapper) {
        this.config = config;
        this.sendChannelWrapper = sendChannelWrapper;
        this.serviceMapper = serviceMapper;
    }

    public ServiceGroup(ServiceGroupConfig config, SendChannelWrapper sendChannelWrapper, ServiceMapper serviceMapper,
            ClassLoader classLoader) {
        this(config, sendChannelWrapper, serviceMapper);
        this.classLoader = classLoader;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, config.getName().concat("@"+this.hashCode()));
        } else {
            stop();
        }

        if (classLoader != null)
            thread.setContextClassLoader(classLoader);
        thread.start();
    }

    public void stop() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    @Override
    public void run() {
        logger.info(config.getName().concat(" are started.."));
        String[] serviceCds = config.getServiceCds();

        while (thread != null && Thread.currentThread().equals(thread)) {
            try {

                if (ChannelState.connected.equals(sendChannelWrapper.getState())) {
                    long current = System.currentTimeMillis();
                    if (config.getDelay() > (current - lastRequested)) {
                        continue;
                    }

                    for (String serviceCd : serviceCds) {

                        ComMessage<?, ?> msg = null;

                        Service service = serviceMapper.getService(serviceCd);

                        if (!service.isDisabled()) {

                            boolean result = false;
                            long elapsed = System.currentTimeMillis();
                            try {
                                logger.info(config.getId().concat(".").concat(serviceCd).concat(" are start"));
                                if (service instanceof PushService) {                                    
                                    msg = ((PushService) service).push();
                                    result = true;
                                } else if (service instanceof RequestService) {
                                    msg = ((RequestService) service).sendRequest();
                                    result = true;
                                } else {
                                    throw new Exception("NotImplementedException");
                                }
                            } catch (Exception e) {
                                if (config.isExecuteExceptionSkipMode()) {
                                    logger.error("ServiceExecuteException", e);
                                    continue;
                                } else {
                                    throw e;
                                }
                            } finally {
                                logger.info(
                                        config.getId().concat(".")
                                                .concat(serviceCd)
                                                .concat(" are finised(result:" + result + ", elased[ms]:"
                                                        + (System.currentTimeMillis() - elapsed)
                                                        + ")"));
                                try {
                                    Thread.sleep(config.getDelayBetweenService());
                                } catch (InterruptedException e) {
                                    break;
                                }
                            }

                        }
                    }

                    lastRequested = current;
                } else {
                    logger.info(config.getName().concat(" can't push data, channel is not working."));
                }

            } catch (Exception e) {
                logger.error("Exception", e);
            } finally {
                try {
                    Thread.sleep(config.getDelay());
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        logger.info(Thread.currentThread().getName().concat(" are finished."));
    }

}
