package pl.pabilo8.kraftwerk.gui.panel;

import com.github.weisj.darklaf.ui.list.DarkListUI;
import com.github.weisj.darklaf.util.Alignment;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.elements.ModelTexture;
import pl.pabilo8.kraftwerk.editor.elements.ModelTexture.TextureListCellRenderer;
import pl.pabilo8.kraftwerk.gui.JListDeselectable;
import pl.pabilo8.kraftwerk.gui.WrapLayout;
import pl.pabilo8.kraftwerk.gui.action.ActionCommand;
import pl.pabilo8.kraftwerk.render.OpenGLTexture.ITextureRefreshListener;
import pl.pabilo8.kraftwerk.utils.GuiUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 26.08.2021
 */
public class PanelTabTextures extends PanelTab implements ITextureRefreshListener
{
	public PanelTabTextures()
	{
		super("tabs.textures", null, 312, Alignment.SOUTH_EAST, true, false);
	}

	private JButton addButton, editButton, removeButton;
	JListDeselectable<ModelTexture> list;
	private DefaultListModel<ModelTexture> textureList = new DefaultListModel<>();

	@Override
	public void init()
	{
		JMenuBar textureToolBar = new JMenuBar();
		textureToolBar.setLayout(new WrapLayout(FlowLayout.LEFT, 0, 0));
		textureToolBar.add(addButton = GuiUtils.getIconButton(UIManager.getIcon("FileView.directoryIcon"), 32));
		textureToolBar.add(editButton = GuiUtils.getIconButton(UIManager.getIcon("FileView.fileIcon"), 32));
		textureToolBar.add(removeButton = GuiUtils.getIconButton(UIManager.getIcon("FileView.fileIcon"), 32));
		add(textureToolBar, "grow, wrap");

		addButton.setAction(new ActionCommand("open_texture"));
		editButton.setAction(new ActionCommand("edit_texture"));
		removeButton.setAction(new ActionCommand("remove_texture"));


		list = new JListDeselectable<>(textureList);
		list.setCellRenderer(new TextureListCellRenderer());
		list.putClientProperty(DarkListUI.KEY_ALTERNATE_ROW_COLOR, true);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFixedCellHeight(80);
		add(new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), "grow, push");
	}

	@Override
	public void refresh()
	{
		textureList.clear();
		for(ModelTexture texture : Kraftwerk.INSTANCE.currentProject.textures)
			textureList.addElement(texture);

	}

	@Override
	public void onReload()
	{
		SwingUtilities.invokeLater(this::refresh);
	}

	public void removeSelectedTexture()
	{
		for(int selectedIndex : list.getSelectedIndices())
		{
			list.clearSelection();
			ModelTexture value = textureList.get(selectedIndex);
			value.loadedTexture.delete();
			Kraftwerk.INSTANCE.currentProject.textures.remove(selectedIndex);
			onReload();
		}
	}

	public void editSelectedTexture()
	{

	}
}
