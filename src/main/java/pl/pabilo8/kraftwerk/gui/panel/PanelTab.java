package pl.pabilo8.kraftwerk.gui.panel;

import com.github.weisj.darklaf.components.tabframe.PanelPopup;
import com.github.weisj.darklaf.components.tabframe.TabFramePopup;
import com.github.weisj.darklaf.util.Alignment;
import net.miginfocom.swing.MigLayout;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.gui.StandardPopupMenu;
import pl.pabilo8.kraftwerk.utils.GuiUtils;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 27.08.2021
 */
public abstract class PanelTab extends JPanel
{
	public PanelTab(String name, @Nullable Icon icon, int width, Alignment alignment, boolean open, boolean debug)
	{
		super(new MigLayout(getLayoutString(open, debug)));
		this.setOpaque(true);
		Dimension preferred = new Dimension(width, Kraftwerk.INSTANCE.panelMain.getHeight());
		this.setPreferredSize(preferred);
		this.setSize(preferred);
		PanelPopup popup = GuiUtils.addSideTab(name, icon, this, alignment);
		Kraftwerk.INSTANCE.tabFrame.validate();
		if(open)
			Kraftwerk.INSTANCE.tabFrame.openTab((TabFramePopup)popup);
		Kraftwerk.registerTab(this);
		//setComponentPopupMenu(new StandardPopupMenu());
	}

	private static String getLayoutString(boolean open, boolean debug)
	{
		StringBuilder layout = new StringBuilder();
		if(debug)
			layout.append("debug, ");
		layout.append("fillx, aligny top, toptobottom");
		return layout.toString();
	}

	public abstract void init();

	public abstract void refresh();
}
