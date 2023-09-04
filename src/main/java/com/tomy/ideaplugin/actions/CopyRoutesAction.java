package com.tomy.ideaplugin.actions;

/**
 * @author tomy.li
 * @date : 2023/8/25 17:43
 */
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.tomy.ideaplugin.helper.ClipboardHelper;
import com.tomy.ideaplugin.helper.NotifyHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
public class CopyRoutesAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final String selectedText = editor.getSelectionModel().getSelectedText();
        String title = "复制API路由信息<br>";
        if (selectedText != null) {
            String[] routes = parseRoutes(selectedText);
            if (routes.length == 0) {
                NotifyHelper.error(project, title + "没有匹配到内容");
                return;
            }
            String content = String.join("\n", routes);
            ClipboardHelper.copyToClipboard(content);
            NotifyHelper.info(project, title + "已成功复制到剪切板:<br>" + content);
        } else {
            NotifyHelper.error(project, title + "没有匹配到内容");
        }
    }

    private String[] parseRoutes(String text) {
        List<String> routes = new ArrayList<>();
        List<String> names = new ArrayList<>();

        // 获取路由位置
        Pattern pattern = Pattern.compile("@PostMapping\\(value = \"(.*?)\"");
        Matcher matcher = pattern.matcher(text);

        // 获取api描述名称 TODO 描述需要和api路径配套出现，目前复制多个的时候尚有问题
        Pattern pattern2 = Pattern.compile("@ApiOperation\\(value = \"(.*?)\"");
        Matcher matcher2 = pattern2.matcher(text);
        while (matcher2.find()) {
            String name = matcher2.group(1);
            routes.add(name);
        }

        while (matcher.find()) {
            String route = matcher.group(1);
            route = route.replaceFirst("/", "");
            routes.add(route);
            routes.add(route.replace("/", "."));
        }

        return routes.toArray(new String[0]);
    }
}