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
package com.github.weisj.darklaf.ui.tabbedPane;

import com.github.weisj.darklaf.components.ClosableTabbedPane;
import com.github.weisj.darklaf.components.TabEvent;
import com.github.weisj.darklaf.components.TabListener;
import com.github.weisj.darklaf.ui.ComponentDemo;
import com.github.weisj.darklaf.util.LogUtil;

import java.util.logging.Logger;

public class ClosableTabbedPaneDemo extends AbstractTabbedPaneDemo<ClosableTabbedPane> implements TabListener {

    private static final Logger LOGGER = LogUtil.getDetachedLogger(ClosableTabbedPane.class);

    public static void main(final String[] args) {
        ComponentDemo.showDemo(new ClosableTabbedPaneDemo());
    }

    protected ClosableTabbedPane createTabbedPane() {
        ClosableTabbedPane tabbedPane = new ClosableTabbedPane();
        tabbedPane.addTabListener(this);
        return tabbedPane;
    }

    @Override
    protected void setupTabbedPane(final ClosableTabbedPane tabbedPane) {
        super.setupTabbedPane(tabbedPane);
        tabbedPane.setEnabledAt(2, false);
        tabbedPane.setTabClosable(1, false);
    }

    @Override
    protected int getTabCount() {
        return 4;
    }

    @Override
    public String getTitle() {
        return "ClosableTabbPane Demo";
    }

    @Override
    public void tabOpened(final TabEvent e) {
        LOGGER.info(e.toString());
    }

    @Override
    public void tabClosed(final TabEvent e) {
        LOGGER.info(e.toString());
    }

    @Override
    public void tabClosing(final TabEvent e) {
        LOGGER.info(e.toString());
    }
}
