package pl.pabilo8.kraftwerk.gui.panel;

import com.github.weisj.darklaf.util.Alignment;
import net.miginfocom.swing.MigLayout;
import pl.pabilo8.kraftwerk.editor.SimpleDocumentListener;
import pl.pabilo8.kraftwerk.editor.elements.ModelElement;
import pl.pabilo8.kraftwerk.editor.elements.ModelTexture;
import pl.pabilo8.kraftwerk.editor.elements.ModelTexture.TextureListCellRenderer;
import pl.pabilo8.kraftwerk.gui.Icons;
import pl.pabilo8.kraftwerk.utils.GuiUtils;
import pl.pabilo8.kraftwerk.utils.MathUtils;
import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;

import static pl.pabilo8.kraftwerk.Kraftwerk.INSTANCE;

/**
 * @author Pabilo8
 * @since 26.08.2021
 */
public class PanelTabProperties extends PanelTab implements SimpleDocumentListener, TreeSelectionListener
{
	public PanelTabProperties()
	{
		super("tabs.properties", Icons.propertiesIcon, 256, Alignment.NORTH_WEST, true, false);
	}

	public ModelElement currentlySelected = null;
	@Nonnull
	public ArrayList<ModelElement> selectionList = new ArrayList<>();
	JTextField fieldName;
	JComboBox<ModelTexture> fieldTextures;
	JSpinner posX, posY, posZ, rotX, rotY, rotZ;
	PropertiesEditorPanel editor = null;

	@Override
	public void init()
	{

	}

	public JTextField addTextBoxProperty(JPanel panel, String text)
	{
		JTextField field = new JTextField();
		field.setHorizontalAlignment(JTextField.CENTER);
		field.setPreferredSize(new Dimension(32, 24));
		field.setMinimumSize(new Dimension(32, 24));
		JLabel label = new JLabel(text);
		label.setLabelFor(panel);
		panel.add(label, "");
		panel.add(field, "growx, push");
		field.getDocument().addDocumentListener(this);
		return field;
	}

	public <T> JComboBox<T> addComboBoxProperty(JPanel panel, String text, ArrayList<T> elements, @Nullable T current)
	{
		DefaultComboBoxModel<T> comboBoxModel = new DefaultComboBoxModel<>();

		comboBoxModel.addElement(null);
		for(T t : elements)
			comboBoxModel.addElement(t);
		JComboBox<T> comboBox = new JComboBox<>(comboBoxModel);
		comboBox.setSelectedIndex(current==null?0: elements.indexOf(current)+1);

		//comboBox.setHorizontalAlignment(JTextField.CENTER);
		comboBox.setPreferredSize(new Dimension(32, 24));
		comboBox.setMinimumSize(new Dimension(32, 24));

		JLabel label = new JLabel(text);
		label.setLabelFor(panel);
		panel.add(label, "");
		panel.add(comboBox, "growx, push");
		comboBox.addItemListener(this);
		return comboBox;
	}

	public JSpinner addDoubleProperty(JPanel panel, String text, double value)
	{
		return addDoubleProperty(panel, text, value, -1024, 1024);
	}

	public JSpinner addDoubleProperty(JPanel panel, String text, double value, double min, double max)
	{
		JSpinner field = new JSpinner(new SpinnerNumberModel(MathUtils.clamp(value, min, max), min, max, 1d));
		field.setEditor(new JSpinner.NumberEditor(field, "0.00"));
		field.setPreferredSize(new Dimension(32, 24));
		field.setMinimumSize(new Dimension(32, 24));
		field.setToolTipText(text);
		panel.add(field, "growx, push");
		field.addChangeListener(this);
		return field;
	}

	public JCheckBox addCheckBoxProperty(JPanel panel, String text)
	{
		JCheckBox field = new JCheckBox(text);
		field.setHorizontalAlignment(JCheckBox.LEFT);
		field.setMinimumSize(new Dimension(32, 24));
		panel.add(field, "growx, push");
		field.addChangeListener(this);
		return field;
	}

	@Override
	public void refresh()
	{
		removeAll();
		removeReferences();

		if(currentlySelected!=null)
		{
			JPanel mainPanel = new JPanel(new MigLayout("fillx"));
			JScrollPane pane = new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			mainPanel.setMinimumSize(new Dimension(32, 32));
			pane.setMinimumSize(new Dimension(32, 32));
			pane.getVerticalScrollBar().setUnitIncrement(16);
			add(pane, "grow, push, wrap");

			JPanel namePanel = new JPanel(new MigLayout("align left"));
			fieldName = addTextBoxProperty(namePanel, "Name");
			fieldName.setText(currentlySelected.name);
			mainPanel.add(namePanel, "growx, center, wrap");

			assert INSTANCE.panelModelElements.modelTree.getSelectionPaths()!=null;
			if(INSTANCE.panelModelElements.modelTree.getSelectionPaths().length > 1)
				fieldName.setEditable(false);

			JPanel panelPos = GuiUtils.getTitledInternalPanel("Position");
			posX = addDoubleProperty(panelPos, "X", currentlySelected.pos.x);
			posY = addDoubleProperty(panelPos, "Y", currentlySelected.pos.y);
			posZ = addDoubleProperty(panelPos, "Z", currentlySelected.pos.z);
			mainPanel.add(panelPos, "growx, center, wrap");

			JPanel panelRot = GuiUtils.getTitledInternalPanel("Rotation");
			rotX = addDoubleProperty(panelRot, "X", currentlySelected.rot.x, -360, 360);
			rotY = addDoubleProperty(panelRot, "Y", currentlySelected.rot.y, -360, 360);
			rotZ = addDoubleProperty(panelRot, "Z", currentlySelected.rot.z, -360, 360);
			mainPanel.add(panelRot, "growx, center, wrap");

			editor = currentlySelected.getEditorPanel();
			if(editor!=null)
			{
				editor.onInit(this, currentlySelected, getSelectedElements());
				mainPanel.add(editor, "growx, center, wrap");
			}

			JPanel panelTexture = new JPanel(new MigLayout("align left"));
			fieldTextures = addComboBoxProperty(panelTexture, "Texture", INSTANCE.currentProject.textures, currentlySelected.texture);
			fieldTextures.setPreferredSize(new Dimension(32, 48));
			fieldTextures.setMinimumSize(new Dimension(32, 48));
			fieldTextures.setRenderer(new TextureListCellRenderer());
			mainPanel.add(panelTexture, "growx, center, wrap");

		}

		SwingUtilities.invokeLater(this::validate);
		SwingUtilities.invokeLater(this::repaint);
		INSTANCE.panelUV.refresh();

	}

	@Override
	public void valueChanged(TreeSelectionEvent e)
	{

		Object obj = e.getNewLeadSelectionPath()!=null?e.getPath().getLastPathComponent(): null;
		if(obj==null)
			currentlySelected = null;
		else if(obj instanceof ModelElement)
			currentlySelected = (ModelElement)obj;
		else if(obj instanceof DefaultMutableTreeNode&&((DefaultMutableTreeNode)obj).getUserObject() instanceof ModelElement)
			currentlySelected = (ModelElement)((DefaultMutableTreeNode)obj).getUserObject();

		if(currentlySelected==null)
			selectionList = new ArrayList<>();
		else
			getSelectedElements();

		this.refresh();
	}

	private void removeReferences()
	{
		fieldName = null;
		posX = null;
		posY = null;
		posZ = null;
		rotX = null;
		rotY = null;
		rotZ = null;
		editor = null;
		fieldTextures = null;
	}

	@Override
	public void onFieldUpdate()
	{
		if(currentlySelected==null)
			return;

		if(fieldName==null||posX==null||posY==null||posZ==null||rotX==null||rotY==null||rotZ==null||fieldTextures==null)
			return;

		if(fieldName.isFocusOwner())
			currentlySelected.name = fieldName.getText().isEmpty()?currentlySelected.name: fieldName.getText();

		ModelElement[] elements = getSelectedElements();
		Vec3d diffPos, diffRot;
		diffPos = new Vec3d(((Double)posX.getValue()), ((Double)posY.getValue()), ((Double)posZ.getValue()))
				.subtract(currentlySelected.pos);
		diffRot = new Vec3d(((Double)rotX.getValue()), ((Double)rotY.getValue()), ((Double)rotZ.getValue()))
				.subtract(currentlySelected.rot);

		for(ModelElement e : elements)
		{
			e.pos = e.pos.add(diffPos);
			e.rot = e.rot.add(diffRot);
			e.texture = ((ModelTexture)fieldTextures.getSelectedItem());
		}

		if(editor!=null)
		{
			editor.onFieldUpdate(currentlySelected, elements);
		}

		INSTANCE.panelModelElements.modelTree.treeDidChange();
		SwingUtilities.invokeLater(INSTANCE.panelUV::refresh);
		//INSTANCE.panelUV.refresh();
	}

	@Nonnull
	public ModelElement[] getSelectedElements()
	{
		TreePath[] paths = INSTANCE.panelModelElements.modelTree.getSelectionPaths();
		if(paths==null)
		{
			this.selectionList = new ArrayList<>();
			return new ModelElement[0];
		}
		ArrayList<ModelElement> elements = new ArrayList<>();
		for(TreePath path : paths)
		{
			Object obj = path.getLastPathComponent();
			if(obj==null)
				break;

			if(obj instanceof ModelElement)
				elements.add((ModelElement)obj);
			else if(obj instanceof DefaultMutableTreeNode&&((DefaultMutableTreeNode)obj).getUserObject() instanceof ModelElement)
				elements.add((ModelElement)((DefaultMutableTreeNode)obj).getUserObject());
		}

		this.selectionList = elements;
		return elements.toArray(new ModelElement[0]);
	}

	public static abstract class PropertiesEditorPanel extends JPanel
	{
		public PropertiesEditorPanel(String name)
		{
			super();
			this.setLayout(new MigLayout("align left, fillx, ins 0"));
		}

		public abstract void onInit(PanelTabProperties panelTabProperties, ModelElement currentlySelected, ModelElement[] selectedElements);

		public abstract void onFieldUpdate(ModelElement currentlySelected, ModelElement[] elements);
	}
}
