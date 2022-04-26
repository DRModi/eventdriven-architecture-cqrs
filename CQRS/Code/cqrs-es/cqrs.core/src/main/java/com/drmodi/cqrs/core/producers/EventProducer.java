package com.drmodi.cqrs.core.producers;

import com.drmodi.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
