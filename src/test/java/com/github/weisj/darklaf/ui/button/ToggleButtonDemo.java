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
package com.github.weisj.darklaf.ui.button;

import com.github.weisj.darklaf.ui.ComponentDemo;
import com.github.weisj.darklaf.ui.DemoPanel;
import com.github.weisj.darklaf.ui.DemoResources;
import com.github.weisj.darklaf.ui.togglebutton.ToggleButtonConstants;

import javax.swing.*;

public class ToggleButtonDemo extends AbstractButtonDemo<JToggleButton> {

    public static void main(final String[] args) {
        ComponentDemo.showDemo(new ToggleButtonDemo());
    }

    @Override
    protected void addControls(final DemoPanel panel, final JToggleButton button) {
        super.addControls(panel, button);
        JPanel controlPanel = panel.addControls();
        controlPanel.add(new JLabel(ToggleButtonConstants.KEY_VARIANT + ":"));
        controlPanel.add(new JComboBox<String>() {
            {
                addItem(ToggleButtonConstants.VARIANT_SLIDER);
                addItem("none");
                setSelectedItem("none");
                addItemListener(e -> button.putClientProperty("JToggleButton.variant", e.getItem()));
            }
        });
    }

    @Override
    protected JToggleButton createButton() {
        Icon icon = DemoResources.FOLDER_ICON;
        return new JToggleButton("Test ToggleButton", icon);
    }

    @Override
    public String getTitle() {
        return "ToggleButton Demo";
    }
}
