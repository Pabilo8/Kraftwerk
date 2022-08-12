package pl.pabilo8.kraftwerk.gui.panel;

import com.github.weisj.darklaf.components.OverlayScrollPane;
import com.github.weisj.darklaf.components.text.SearchTextField;
import com.github.weisj.darklaf.util.Alignment;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.elements.ModelElement;
import pl.pabilo8.kraftwerk.gui.Icons;
import pl.pabilo8.kraftwerk.gui.JTreeModelElements;
import pl.pabilo8.kraftwerk.gui.WrapLayout;
import pl.pabilo8.kraftwerk.gui.action.ActionCommand;
import pl.pabilo8.kraftwerk.utils.GuiUtils;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

import static pl.pabilo8.kraftwerk.Kraftwerk.INSTANCE;

/**
 * @author Pabilo8
 * @since 26.08.2021
 */
public class PanelTabElements extends PanelTab
{
	public PanelTabElements()
	{
		super("tabs.elements", Icons.elementsIcon, 256, Alignment.WEST, true, false);
	}

	public JTreeModelElements modelTree;
	private SearchTextField search;
	private JMenuBar shapeToolBar;

	@Override
	public void init()
	{
		search = new SearchTextField();
		add(search, "growx, wrap");

		shapeToolBar = new JMenuBar();
		shapeToolBar.setLayout(new WrapLayout(FlowLayout.LEFT, 0, 0));
		add(shapeToolBar, "grow, wrap");
	}

	@Override
	public void refresh()
	{
		removeAll();
		add(search, "growx, wrap");
		add(shapeToolBar, "grow, wrap");

		if(modelTree!=null)
			this.remove(modelTree);

		if(INSTANCE.currentProject==null)
			modelTree = new JTreeModelElements();
		else
			modelTree = new JTreeModelElements(INSTANCE.currentProject.rootElement);
		INSTANCE.currentProject.elementTreeModel = modelTree.getModel();

		search.addSearchListener(modelTree);
		add(new OverlayScrollPane(modelTree), "grow, push");
		modelTree.addTreeSelectionListener(INSTANCE.panelPartProperties);

		shapeToolBar.removeAll();

		INSTANCE.modelElementSuppliers.keySet().forEach(this::addElementButton);
	}

	public void addElementButton(String name)
	{
		JButton button = GuiUtils.getIconButton(Icons.loadSVGIcon("elements/"+name), 32, ActionCommand.getActionCommand("add_element", name));
		button.setToolTipText("element."+name);
		INSTANCE.localizeList.add(button);
		shapeToolBar.add(button);
	}

	public void addModelElement(String element)
	{
		ModelElement m = null;
		if(modelTree.getLastSelectedPathComponent() instanceof ModelElement)
		{
			m = ((ModelElement)modelTree.getLastSelectedPathComponent());
			if(m.isLeaf())
			{
				TreeNode node = m.getParent();
				m = node instanceof ModelElement?((ModelElement)node): null;
			}
		}
		ModelElement modelElement = Kraftwerk.supplyElement(element);
		if(INSTANCE.currentProject.addElement(modelElement, m))
		{
			DefaultTreeModel model = (DefaultTreeModel)modelTree.getModel();

			assert modelElement!=null;
			modelTree.setSelectionPath(new TreePath(modelElement));

			model.reload(m==null?INSTANCE.currentProject.rootElement: m);
		}
	}
}
