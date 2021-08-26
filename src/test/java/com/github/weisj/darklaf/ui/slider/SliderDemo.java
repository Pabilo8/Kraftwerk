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
package com.github.weisj.darklaf.ui.slider;

import com.github.weisj.darklaf.ui.ComponentDemo;
import com.github.weisj.darklaf.ui.DemoPanel;

import javax.swing.*;
import java.awt.*;

public class SliderDemo implements ComponentDemo {

    public static void main(final String[] args) {
        ComponentDemo.showDemo(new SliderDemo());
    }

    @Override
    public JComponent createComponent() {
        JSlider slider = new JSlider();
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        DemoPanel panel = new DemoPanel(slider);

        JPanel controlPanel = panel.addControls();
        controlPanel.add(new JLabel("Orientation:"));
        controlPanel.add(new JComboBox<String>() {
            {
                setEditable(false);
                addItem("Horizontal");
                addItem("Vertical");
                setSelectedItem("Horizontal");
                addItemListener(
                        e -> slider.setOrientation(e.getItem() == "Vertical" ? JSlider.VERTICAL : JSlider.HORIZONTAL));
            }
        });
        controlPanel = panel.addControls();
        controlPanel.add(new JCheckBox("enabled") {
            {
                setSelected(slider.isEnabled());
                addActionListener(e -> slider.setEnabled(isSelected()));
            }
        });
        controlPanel.add(new JCheckBox("LeftToRight") {
            {
                setSelected(slider.getComponentOrientation().isLeftToRight());
                addActionListener(e -> slider.setComponentOrientation(
                        isSelected() ? ComponentOrientation.LEFT_TO_RIGHT : ComponentOrientation.RIGHT_TO_LEFT));
            }
        });
        controlPanel.add(new JCheckBox("inverted") {
            {
                setSelected(slider.getInverted());
                addActionListener(e -> slider.setInverted(isSelected()));
            }
        });
        controlPanel.add(new JCheckBox("snap to ticks") {
            {
                setSelected(slider.getSnapToTicks());
                addActionListener(e -> slider.setSnapToTicks(isSelected()));
            }
        });
        controlPanel.add(new JCheckBox("paint ticks") {
            {
                setSelected(slider.getPaintTicks());
                addActionListener(e -> slider.setPaintTicks(isSelected()));
            }
        });
        controlPanel.add(new JCheckBox("paint labels") {
            {
                setSelected(slider.getPaintLabels());
                addActionListener(e -> slider.setPaintLabels(isSelected()));
            }
        });
        controlPanel.add(new JCheckBox("paint track") {
            {
                setSelected(slider.getPaintTrack());
                addActionListener(e -> slider.setPaintTrack(isSelected()));
            }
        });
        controlPanel.add(new JCheckBox(DarkSliderUI.KEY_VARIANT + " = " + DarkSliderUI.VARIANT_VOLUME) {
            {
                addActionListener(e -> slider.putClientProperty(DarkSliderUI.KEY_VARIANT,
                        isSelected() ? DarkSliderUI.VARIANT_VOLUME : null));
            }
        });
        controlPanel.add(new JCheckBox(DarkSliderUI.KEY_INSTANT_SCROLL) {
            {
                addActionListener(e -> slider.putClientProperty(DarkSliderUI.KEY_INSTANT_SCROLL, isSelected()));
            }
        });
        controlPanel.add(new JCheckBox(DarkSliderUI.KEY_SHOW_VOLUME_ICON) {
            {
                addActionListener(e -> slider.putClientProperty(DarkSliderUI.KEY_SHOW_VOLUME_ICON, isSelected()));
            }
        });
        return panel;
    }

    @Override
    public String getTitle() {
        return "Slider Demo";
    }
}
