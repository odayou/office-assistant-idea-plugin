package com.tomy.ideaplugin.actions;

/**
 * @author tomy.li
 * @date : 2023/8/25 17:43
 */

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.tomy.ideaplugin.helper.TextHelper;

public class TransformToHumpAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        Application application = ApplicationManager.getApplication();
        application.runWriteAction(() -> {
            // 获取当前编辑器
            Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);

            // 获取选中的文本
            String selectedText = editor.getSelectionModel().getSelectedText();

            // 检查是否有选中的文本
            if (selectedText != null) {
                String result = TextHelper.joinWordsFromDelimiter(TextHelper.joinWordsFromDelimiter(TextHelper.joinWordsFromDelimiter(selectedText, "."), "-"), "_");
                // 获取文档
                Document document = editor.getDocument();

                // 获取选中文本的起始和结束位置
                int startOffset = editor.getSelectionModel().getSelectionStart();
                int endOffset = editor.getSelectionModel().getSelectionEnd();

                // 使用 WriteCommandAction 执行文档修改操作
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    // 在这里执行您的文档修改操作
//                    document.insertString(0, "Hello, World!");

                    // 替换选中文本为转换后的内容
                    document.replaceString(startOffset, endOffset, result);
                });
            }
        });
    }
}