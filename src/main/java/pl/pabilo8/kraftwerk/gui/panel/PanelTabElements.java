package pl.pabilo8.kraftwerk.gui.panel;

import com.github.weisj.darklaf.components.OverlayScrollPane;
import com.github.weisj.darklaf.components.text.SearchTextField;
import com.github.weisj.darklaf.util.Alignment;
import pl.pabilo8.kraftwerk.editor.elements.ModelElementShapebox;
import pl.pabilo8.kraftwerk.gui.JTreeDragAndDrop;
import pl.pabilo8.kraftwerk.gui.WrapLayout;
import pl.pabilo8.kraftwerk.utils.GuiUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 26.08.2021
 */
public class PanelTabElements extends PanelTab
{
	public PanelTabElements()
	{
		super("tabs.elements", null, 256, Alignment.WEST, true, false);
	}

	@Override
	public void init()
	{
		SearchTextField search = new SearchTextField();
		add(search, "growx, wrap");

		JMenuBar shapeToolBar = new JMenuBar();
		shapeToolBar.setLayout(new WrapLayout(FlowLayout.LEFT, 0, 0));
		shapeToolBar.add(GuiUtils.getIconButton(UIManager.getIcon("FileView.directoryIcon"), 32));
		shapeToolBar.add(GuiUtils.getIconButton(UIManager.getIcon("FileView.fileIcon"), 32));
		shapeToolBar.add(GuiUtils.getIconButton(UIManager.getIcon("FileView.fileIcon"), 32));
		shapeToolBar.add(GuiUtils.getIconButton(UIManager.getIcon("FileView.fileIcon"), 32));
		shapeToolBar.add(GuiUtils.getIconButton(UIManager.getIcon("FileView.fileIcon"), 32));
		add(shapeToolBar, "grow, wrap");

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		for(int i = 0; i < 40; i++)
			root.add(new ModelElementShapebox(String.format("Shapebox #%d", i), 0, i, 0));
		JTreeDragAndDrop modelTree = new JTreeDragAndDrop(root);
		modelTree.setRootVisible(false);
		modelTree.setShowsRootHandles(true);
		modelTree.setCellRenderer(new DefaultTreeCellRenderer());
		search.addSearchListener(modelTree);
		add(new OverlayScrollPane(modelTree), "grow, push");
	}

	@Override
	public void refresh()
	{

	}
}
