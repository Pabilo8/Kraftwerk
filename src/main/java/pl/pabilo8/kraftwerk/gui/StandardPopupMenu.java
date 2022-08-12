package pl.pabilo8.kraftwerk.gui;

import com.github.weisj.darklaf.components.JXPopupMenu;
import pl.pabilo8.kraftwerk.utils.GuiUtils;

import java.awt.*;

/**
 * @author Pabilo8
 * @since 25.11.2021
 */
public class StandardPopupMenu extends JXPopupMenu
{
	public StandardPopupMenu()
	{
		add(GuiUtils.getActionItem("action.undo","undo", null, Icons.undoIcon));
		add(GuiUtils.getActionItem("action.redo","redo", null, Icons.redoIcon));
		add(GuiUtils.getActionItem("action.cut","cut", null, Icons.cutIcon));
		add(GuiUtils.getActionItem("action.copy","copy", null, Icons.copyIcon));
		add(GuiUtils.getActionItem("action.paste","paste", null, Icons.pasteIcon));
		add(GuiUtils.getActionItem("action.remove","remove", null, Icons.removeIcon));
	}

	@Override
	public void show(Component invoker, int x, int y)
	{
		super.show(invoker, x, y);
		invoker.requestFocus();
	}

	public StandardPopupMenu(String label)
	{
		super(label);
	}
}
