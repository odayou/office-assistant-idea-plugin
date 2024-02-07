package com.tomy.ideaplugin.actions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.tomy.ideaplugin.helper.MessageHelper;

/**
 * @author tomy.li
 * @date : 2024/2/6 15:06
 */
public class JsonViewerAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        Application application = ApplicationManager.getApplication();
        application.runWriteAction(() -> {
            // 获取当前编辑器
            Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);

            // 获取选中的文本
            String selectedText = editor.getSelectionModel().getSelectedText();
            boolean endsWithEnter;
            // 检查是否有选中的文本
            if (selectedText != null) {
                // 判断selectedText的最后是否为换行符"\n"
                endsWithEnter = selectedText.endsWith("\n");

                // 检查是格式化还是压缩的 JSON
                String result;
                if (isFormattedJson(selectedText)) {
                    result = compactJson(selectedText);
                } else {
                    result = formatJson(selectedText);
                }
                if (result == null) {
                    MessageHelper.showWarningNotify(project, "失败， 文本非 JSON 格式");
                    return;
                }
                // 获取文档
                Document document = editor.getDocument();

                // 从文本插入符(caret) 中获取已选择文本的相关信息
                Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
                // 获取选中文本的起始和结束位置
                int start = primaryCaret.getSelectionStart();
                int end = primaryCaret.getSelectionEnd();
                // 获取选中文本的行尾符位置
                // 使用 WriteCommandAction 执行文档修改操作
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    // 替换选中文本为转换后的内容
                    document.replaceString(start, end, !endsWithEnter ? result : (result +"\n"));
                });
            } else {
                MessageHelper.showWarningNotify(project, "请先选中要转换的 JSON 文本");
            }
        });

    }

    /**
     * 紧凑的 JSON 字符串 转为 格式化的 JSON 字符串
     * @param json
     * @return
     */
    private String formatJson(String json) {
        try {
            Object obj = JSON.parse(json);
            return JSON.toJSONString(obj, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断 JSON 是否已经格式化
     *
     * @param json JSON 字符串
     * @return 如果 JSON 已经格式化则返回 true，否则返回 false
     */
    private boolean isFormattedJson(String json) {
        try {
            Object obj = JSON.parse(json);
            String formattedJson = JSON.toJSONString(obj, true);
            return json.equals(formattedJson);
        } catch (Exception e) {
            // 解析异常，说明不是 JSON 格式
            return false;
        }
    }

    /**
     * formatJson的反向操作
     * @param json
     * @return
     */
    private String compactJson(String json) {
        try {
            Object obj = JSON.parse(json);
            // 根据解析结果判断是对象还是数组
            if (obj instanceof JSONObject || obj instanceof JSONArray) {
                return JSON.toJSONString(obj);
            } else {
                // 非对象和数组的情况，直接返回原文本
                return json;
            }
        } catch (Exception e) {
            // 解析失败，返回原文本
            return null;
        }
    }
}