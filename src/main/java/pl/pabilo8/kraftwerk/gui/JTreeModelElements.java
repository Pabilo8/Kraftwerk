package pl.pabilo8.kraftwerk.gui;

import com.github.weisj.darklaf.components.text.SearchEvent;
import com.github.weisj.darklaf.components.text.SearchListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.EditorProject;
import pl.pabilo8.kraftwerk.editor.ICopyAble;
import pl.pabilo8.kraftwerk.editor.ICopyAbleContainer;
import pl.pabilo8.kraftwerk.editor.elements.ModelElement;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Pabilo8
 * @since 16.08.2021
 */
@SuppressWarnings("unused")
public class JTreeModelElements extends JTree implements SearchListener, ICopyAbleContainer
{
	public JTreeModelElements(TreeNode root)
	{
		super(root);
		init();
	}

	public JTreeModelElements()
	{
		super();
		init();
	}

	private void init()
	{
		setDragEnabled(true);
		setDropMode(DropMode.ON_OR_INSERT);
		setTransferHandler(new TreeTransferHandler());
		getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		expandTree(this);
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(SwingUtilities.isRightMouseButton(e))
				{
					int selRow = getRowForLocation(e.getX(), e.getY());
					TreePath selPath = getPathForLocation(e.getX(), e.getY());

					boolean b = false;
					if(getSelectionPaths()!=null)
					{
						List<TreePath> treePaths = Arrays.asList(getSelectionPaths());
						if(treePaths.size() <= 1) b = true;
						else b = !treePaths.contains(selPath);
					}

					if(b)
					{
						setSelectionPath(selPath);
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(getRowForLocation(e.getX(), e.getY())==-1&&!e.isShiftDown()&&!isMenuShortcutKeyDown(e))
					clearSelection();
			}

			private boolean isMenuShortcutKeyDown(InputEvent event)
			{
				return (event.getModifiers()&Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())!=0;
			}
		});
		setComponentPopupMenu(new StandardPopupMenu());
		setCellRenderer(new DefaultTreeCellRenderer()
		{
			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused)
			{
				Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);

				ModelElement element = null;
				if(value instanceof ModelElement)
					element = ((ModelElement)value);
				else if(((DefaultMutableTreeNode)value).getUserObject() instanceof ModelElement)
					element = ((ModelElement)((DefaultMutableTreeNode)value).getUserObject());

				if(element!=null)
					setIcon(element.getIcon());

				return c;
			}
		});
		setRootVisible(false);
		setShowsRootHandles(true);
	}

	@Override
	public TreePath getPathForLocation(int x, int y)
	{
		TreePath closestPath = getClosestPathForLocation(x, y);
		if(closestPath!=null)
		{
			Rectangle pathBounds = getPathBounds(closestPath);
			if(pathBounds!=null&&
//					x >= pathBounds.x && x < (pathBounds.x + pathBounds.width) &&
					y >= pathBounds.y&&y < (pathBounds.y+pathBounds.height)) return closestPath;
		}
		return null;
	}

	private void expandTree(JTree tree)
	{
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
		Enumeration<?> e = root.breadthFirstEnumeration();
		while(e.hasMoreElements())
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();
			if(node.isLeaf()) continue;
			int row = tree.getRowForPath(new TreePath(node.getPath()));
			tree.expandRow(row);
		}
	}

	@Override
	public void searchPerformed(SearchEvent e)
	{
		// TODO: 26.08.2021 search
	}

	@Override
	public JsonArray handleCopy()
	{
		JsonArray array = new JsonArray();

		TreePath[] paths = getSelectionPaths();

		if(paths!=null)
		{
			int i = Arrays.stream(paths).map(TreePath::getPathCount).min(Integer::compareTo).orElse(2);

			for(TreePath selectionPath : paths)
			{
				if(selectionPath.getPathCount()!=i) continue;

				Object o = selectionPath.getLastPathComponent();
				ModelElement model = null;
				if(o instanceof ModelElement) model = ((ModelElement)o);
				else if(((DefaultMutableTreeNode)o).getUserObject() instanceof ModelElement)
					model = (ModelElement)((DefaultMutableTreeNode)o).getUserObject();

				if(model!=null)
				{
					JsonObject json = model.toJSON();
					if(model.getChildCount() > 0) json.add("children", EditorProject.getChildrenElementsJSON(model));
					array.add(json);
				}
			}
		}

		return array;
	}

	@Override
	public void handleRemove()
	{
		TreePath[] paths = getSelectionPaths();
		if(paths!=null)
		{
			for(TreePath path : paths)
			{
				Object component = path.getLastPathComponent();
				if(component instanceof ICopyAble) ((ICopyAble)component).handleRemove();
			}
		}

		DefaultTreeModel model = (DefaultTreeModel)getModel();
		model.reload();
		validate();

	}

	@Override
	public void handlePaste(JsonArray elements)
	{
		TreePath path = getSelectionPath();
		Object obj = path!=null&&isFocusOwner()?path.getLastPathComponent(): Kraftwerk.INSTANCE.currentProject.rootElement;

		if(obj instanceof DefaultMutableTreeNode)
		{
			Kraftwerk.INSTANCE.currentProject.loadElementsFromJSON(elements, ((DefaultMutableTreeNode)obj));
			DefaultTreeModel model = (DefaultTreeModel)getModel();
			model.reload(((DefaultMutableTreeNode)obj));
			expandPath(path);
		}

	}

	// TODO: 11.12.2021 json
	static class TreeTransferHandler extends TransferHandler
	{
		DataFlavor nodesFlavor;
		DataFlavor[] flavors = new DataFlavor[1];
		DefaultMutableTreeNode[] nodesToRemove;

		public TreeTransferHandler()
		{
			try
			{
				String mimeType = DataFlavor.javaJVMLocalObjectMimeType+";class=\""+javax.swing.tree.DefaultMutableTreeNode[].class.getName()+"\"";
				nodesFlavor = new DataFlavor(mimeType);
				flavors[0] = nodesFlavor;
			}
			catch(ClassNotFoundException e)
			{
				System.out.println("ClassNotFound: "+e.getMessage());
			}
		}

		public boolean canImport(TransferHandler.TransferSupport support)
		{
			if(!support.isDrop()) return false;
			support.setShowDropLocation(true);
			if(!support.isDataFlavorSupported(nodesFlavor)) return false;
			// Do not allow a drop on the drag source selections.
			JTree.DropLocation dl = (JTree.DropLocation)support.getDropLocation();
			JTree tree = (JTree)support.getComponent();
			int dropRow = tree.getRowForPath(dl.getPath());
			int[] selRows = tree.getSelectionRows();
			assert selRows!=null;
			for(int selRow : selRows)
				if(selRow==dropRow) return false;
			// Do not allow MOVE-action drops if a non-leaf node is
			// selected unless all of its children are also selected.
			int action = support.getDropAction();
			if(action==MOVE) return haveCompleteNode(tree);
			// Do not allow a non-leaf node to be copied to a level
			// which is less than its source level.
			TreePath dest = dl.getPath();
			DefaultMutableTreeNode target = (DefaultMutableTreeNode)dest.getLastPathComponent();
			TreePath path = tree.getPathForRow(selRows[0]);
			DefaultMutableTreeNode firstNode = (DefaultMutableTreeNode)path.getLastPathComponent();
			return firstNode.getChildCount() <= 0||target.getLevel() >= firstNode.getLevel();
		}

		private boolean haveCompleteNode(JTree tree)
		{
			int[] selRows = tree.getSelectionRows();
			assert selRows!=null;
			TreePath path = tree.getPathForRow(selRows[0]);
			DefaultMutableTreeNode first = (DefaultMutableTreeNode)path.getLastPathComponent();
			int childCount = first.getChildCount();
			// first has children and no children are selected.
			if(childCount > 0&&selRows.length==1) return false;
			// first may have children.
			for(int i = 1; i < selRows.length; i++)
			{
				path = tree.getPathForRow(selRows[i]);
				DefaultMutableTreeNode next = (DefaultMutableTreeNode)path.getLastPathComponent();
				// Found a child of first.
				// Not all children of first are selected.
				if(first.isNodeChild(next)) if(childCount > selRows.length-1) return false;
			}
			return true;
		}

		protected Transferable createTransferable(JComponent c)
		{
			JTree tree = (JTree)c;
			TreePath[] paths = tree.getSelectionPaths();
			if(paths!=null)
			{
				// Make up a node array of copies for transfer and
				// another for/of the nodes that will be removed in
				// exportDone after a successful drop.
				List<DefaultMutableTreeNode> copies = new ArrayList<>();
				List<DefaultMutableTreeNode> toRemove = new ArrayList<>();
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)paths[0].getLastPathComponent();
				DefaultMutableTreeNode copy = copy(node);
				copies.add(copy);
				toRemove.add(node);
				for(int i = 1; i < paths.length; i++)
				{
					DefaultMutableTreeNode next = (DefaultMutableTreeNode)paths[i].getLastPathComponent();
					// Do not allow higher level nodes to be added to list.
					if(next.getLevel() < node.getLevel()) break;
					else // child node
						// node already contains child
						if(next.getLevel() > node.getLevel()) copy.add(copy(next));
						else
						{                                        // sibling
							copies.add(copy(next));
							toRemove.add(next);
						}
				}
				DefaultMutableTreeNode[] nodes = copies.toArray(new DefaultMutableTreeNode[0]);
				nodesToRemove = toRemove.toArray(new DefaultMutableTreeNode[0]);
				return new NodesTransferable(nodes);
			}
			return null;
		}

		/**
		 * Defensive copy used in createTransferable.
		 */
		private DefaultMutableTreeNode copy(TreeNode node)
		{
			return new DefaultMutableTreeNode(node);
		}

		protected void exportDone(JComponent source, Transferable data, int action)
		{
			if((action&MOVE)==MOVE)
			{
				JTree tree = (JTree)source;
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				// Remove nodes saved in nodesToRemove in createTransferable.
				for(DefaultMutableTreeNode defaultMutableTreeNode : nodesToRemove)
					model.removeNodeFromParent(defaultMutableTreeNode);
			}
		}

		public int getSourceActions(JComponent c)
		{
			return COPY_OR_MOVE;
		}

		public boolean importData(TransferHandler.TransferSupport support)
		{
			if(!canImport(support)) return false;
			// Extract transfer data.
			DefaultMutableTreeNode[] nodes = null;
			try
			{
				Transferable t = support.getTransferable();
				nodes = (DefaultMutableTreeNode[])t.getTransferData(nodesFlavor);
			}
			catch(UnsupportedFlavorException ufe)
			{
				System.out.println("UnsupportedFlavor: "+ufe.getMessage());
			}
			catch(java.io.IOException ioe)
			{
				System.out.println("I/O error: "+ioe.getMessage());
			}
			// Get drop location info.
			JTree.DropLocation dl = (JTree.DropLocation)support.getDropLocation();
			int childIndex = dl.getChildIndex();
			TreePath dest = dl.getPath();
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)dest.getLastPathComponent();
			JTree tree = (JTree)support.getComponent();
			DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
			// Configure for drop mode.
			int index = childIndex;    // DropMode.INSERT
			// DropMode.ON
			if(childIndex==-1) index = parent.getChildCount();
			// Add data to model.
			assert nodes!=null;
			for(DefaultMutableTreeNode node : nodes)
				model.insertNodeInto(node, parent, index++);
			return true;
		}

		public String toString()
		{
			return getClass().getName();
		}

		public class NodesTransferable implements Transferable
		{
			DefaultMutableTreeNode[] nodes;

			public NodesTransferable(DefaultMutableTreeNode[] nodes)
			{
				this.nodes = nodes;
			}

			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
			{
				if(!isDataFlavorSupported(flavor)) throw new UnsupportedFlavorException(flavor);
				return nodes;
			}

			public DataFlavor[] getTransferDataFlavors()
			{
				return flavors;
			}

			public boolean isDataFlavorSupported(DataFlavor flavor)
			{
				return nodesFlavor.equals(flavor);
			}
		}
	}
}
