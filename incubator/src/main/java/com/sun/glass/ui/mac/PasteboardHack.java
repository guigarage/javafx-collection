package com.sun.glass.ui.mac;

import com.sun.glass.ui.ClipboardAssistance;
import com.sun.glass.ui.SystemClipboard;
import com.sun.javafx.tk.TKClipboard;
import javafx.scene.input.Clipboard;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by hendrikebbers on 12.01.15.
 */
public class PasteboardHack {


    public static String getUTF16String() {
        try {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            TKClipboard tkClipboard = getPrivileged(clipboard.getClass().getDeclaredField("peer"), clipboard);
            ClipboardAssistance clipboardAssistance = getPrivileged(tkClipboard.getClass().getDeclaredField("systemAssistant"), tkClipboard);
            SystemClipboard systemClipboard = getPrivileged(clipboardAssistance.getClass().getDeclaredField("clipboard"), clipboardAssistance);
            Object pasteboard = getPrivileged(systemClipboard.getClass().getDeclaredField("pasteboard"), systemClipboard);

            Object ret = callPrivileged(pasteboard.getClass().getDeclaredMethod("getItemStringForUTF", Integer.TYPE, String.class), pasteboard, 0, "public.utf8-plain-text");

            byte[] bytes = callPrivileged(pasteboard.getClass().getDeclaredMethod("getItemBytesForUTF", Integer.TYPE, String.class), pasteboard, 0, "public.utf8-plain-text");
            String s = new String(bytes, Charset.forName("UTF-8"));

            return s;
        } catch (Exception e) {
            return null;
        }
        //return ret.toString();
    }


    public static <T> T getPrivileged(final Field field, final Object bean) {
        return AccessController.doPrivileged(new PrivilegedAction<T>() {
            @Override
            public T run() {
                boolean wasAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    return (T) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    throw new IllegalStateException("Cannot access field: "
                            + field, ex);
                } finally {
                    field.setAccessible(wasAccessible);
                }
            }
        });
    }

    public static <T> T callPrivileged(final Method method, final Object bean, Object... args) {
        return AccessController.doPrivileged(new PrivilegedAction<T>() {
            @Override
            public T run() {
                boolean wasAccessible = method.isAccessible();
                try {
                    method.setAccessible(true);
                    return (T) method.invoke(bean, args);
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                    throw new IllegalStateException("Cannot call Method: "
                            + method, ex);
                } finally {
                    method.setAccessible(wasAccessible);
                }
            }
        });
    }
}
