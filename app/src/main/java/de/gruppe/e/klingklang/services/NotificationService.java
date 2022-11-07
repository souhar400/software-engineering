package de.gruppe.e.klingklang.services;

public interface NotificationService<T> {

    void sendNotification(T notification);
}
