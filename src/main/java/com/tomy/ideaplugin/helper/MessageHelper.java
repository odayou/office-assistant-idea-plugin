package com.tomy.ideaplugin.helper;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

/**
 * @author tomy.li
 * @date : 2023/8/31 19:15
 */
public class MessageHelper {
    public static void showNotify(@Nullable Project project,
                                  String content, NotificationType notificationType) {
        // 获取通知组
        NotificationGroupManager.getInstance()
                // plugin.xml里配置的id
                .getNotificationGroup("office assistant")
                // 创建通知
                .createNotification(content, notificationType)
                // 通知
                .notify(project);
    }

    public static void showErrorNotify(@Nullable Project project,
                                       String content) {
        // 显示错误通知
        showNotify(project, content, NotificationType.ERROR);
    }

    public static void showWarningNotify(@Nullable Project project,
                                         String content) {
        // 显示警告通知
        showNotify(project, content, NotificationType.WARNING);
    }

    public static void showInfoNotify(@Nullable Project project,
                                      String content) {
        // 显示信息通知
        showNotify(project, content, NotificationType.INFORMATION);
    }
}
