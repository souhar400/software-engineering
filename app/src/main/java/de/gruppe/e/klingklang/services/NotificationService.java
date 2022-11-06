package de.gruppe.e.klingklang.services;

public interface NotificationService<T> {

    void pushNotification(T notification);
}
