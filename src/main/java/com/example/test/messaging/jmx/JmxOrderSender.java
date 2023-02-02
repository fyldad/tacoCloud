package com.example.test.messaging.jmx;

import com.example.test.model.TacoOrder;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.util.concurrent.atomic.AtomicLong;

@Service
@ManagedResource
public class JmxOrderSender implements NotificationPublisherAware {

    private final AtomicLong atomicLong = new AtomicLong();
    private NotificationPublisher notificationPublisher;

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    @ManagedOperation
    public Long postOrder(TacoOrder order) {
        atomicLong.getAndIncrement();
        long orderNum = atomicLong.getAndIncrement();
        Notification notification = new Notification(
                "TacoOrder", this, orderNum, order.toString());
        notificationPublisher.sendNotification(notification);
        return orderNum;
    }

}
