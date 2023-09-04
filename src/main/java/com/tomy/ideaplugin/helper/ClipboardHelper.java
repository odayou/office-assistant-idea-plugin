package com.tomy.ideaplugin.helper;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

/**
 * @author tomy.li
 * @date : 2023/8/31 18:57
 */
public class ClipboardHelper {
    public static void copyToClipboard(String content) {
        StringSelection selection = new StringSelection(content);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }
}
