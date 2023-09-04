package com.tomy.ideaplugin.helper;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

/**
 * @author tomy.li
 * @date : 2023/8/31 19:15
 */
public class NotifyHelper {
    public static void notify(@Nullable Project project,
                              String content, NotificationType notificationType) {
        NotificationGroupManager.getInstance()
                // plugin.xml里配置的id
                .getNotificationGroup("tomy asst")
                .createNotification(content, notificationType)
                .notify(project);
    }

    public static void error(@Nullable Project project,
                              String content) {
        notify(project, content, NotificationType.ERROR);
    }

    public static void warning(@Nullable Project project,
                             String content) {
        notify(project, content, NotificationType.WARNING);
    }

    public static void info(@Nullable Project project,
                     String content) {
        notify(project, content, NotificationType.INFORMATION);
    }
}
