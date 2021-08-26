/*
 * MIT License
 *
 * Copyright (c) 2021 Jannis Weis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.github.weisj.darklaf.ui;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.icons.SolidColorIcon;
import com.github.weisj.darklaf.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PreferenceChangeDemo implements ComponentDemo {

    public static void main(final String[] args) {
        LafManager.enabledPreferenceChangeReporting(false);
        ComponentDemo.showDemo(new PreferenceChangeDemo());
    }

    @Override
    public JComponent createComponent() {
        LafManager.addThemePreferenceChangeListener(LafManager::installTheme);
        DemoPanel panel = new DemoPanel(new JToggleButton("Start") {
            {
                addActionListener(e -> {
                    setText(isSelected() ? "Stop" : "Start");
                    LafManager.enabledPreferenceChangeReporting(isSelected());
                });
            }
        });
        Icon accentColorIcon = new SolidColorIcon() {
            @Override
            public Color getColor() {
                return LafManager.getTheme().getAccentColorRule().getAccentColor();
            }
        };
        Icon selectionColorIcon = new SolidColorIcon() {
            @Override
            public Color getColor() {
                return LafManager.getTheme().getAccentColorRule().getSelectionColor();
            }
        };
        JPanel controlPanel = panel.addControls();
        controlPanel.add(new JLabel("Accent Color", accentColorIcon, JLabel.LEFT));
        controlPanel.add(new JLabel("Selection Color", selectionColorIcon, JLabel.LEFT));

        controlPanel = panel.addControls();
        controlPanel.add(new JTextArea() {
            {
                setMargin(new Insets(5, 5, 5, 5));
                setEditable(false);
                setText("Press start/stop to enable/disable preference monitoring.\n" + "Then do one of the following\n"
                        + " - switch between dark/light com.github.weisj.theme (Windows/macOS)\n"
                        + " - toggle high contrast mode (Windows/macOS)\n" + " - change accent color (Windows/macOS)\n"
                        + " - change selection color (macOS)\n" + " - change font scaling (Windows)\n"
                        + "The com.github.weisj.theme should then adjust automatically (if monitoring is started).\n");
            }
        });
        return panel;
    }

    @Override
    public WindowListener createWindowListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                LafManager.enabledPreferenceChangeReporting(false);
            }
        };
    }

    @Override
    public String getTitle() {
        return "Preference Change Demo";
    }

    @Override
    public Theme createTheme() {
        return LafManager.themeForPreferredStyle(LafManager.getPreferredThemeStyle());
    }

    @Override
    public JMenuBar createMenuBar() {
        return null;
    }
}
