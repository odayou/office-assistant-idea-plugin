package com.tomy.ideaplugin.actions;

/**
 * @author tomy.li
 * @date : 2023/8/25 17:43
 */

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.tomy.ideaplugin.helper.ClipboardHelper;
import com.tomy.ideaplugin.helper.NotifyHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitWordsWithUnderLineAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        // 获取当前编辑器
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);

        // 获取选中的文本
        String selectedText = editor.getSelectionModel().getSelectedText();

        // 检查是否有选中的文本
        if (selectedText != null) {
            // 拆分单词并使用点号拼接
            String[] words = selectedText.split("(?=[A-Z])|(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");
            String result = String.join("_", words).toLowerCase();

            // 显示结果对话框
            ClipboardHelper.copyToClipboard(result);
            NotifyHelper.info(project, "已成功复制到剪切板:<br>" + result);
        }
    }
}