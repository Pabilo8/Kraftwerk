package pl.pabilo8.kraftwerk.gui.panel;

import com.github.weisj.darklaf.ui.list.DarkListUI;
import com.github.weisj.darklaf.util.Alignment;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.elements.ModelTexture;
import pl.pabilo8.kraftwerk.editor.elements.ModelTexture.TextureListCellRenderer;
import pl.pabilo8.kraftwerk.gui.Icons;
import pl.pabilo8.kraftwerk.gui.JListTextures;
import pl.pabilo8.kraftwerk.gui.StandardPopupMenu;
import pl.pabilo8.kraftwerk.gui.WrapLayout;
import pl.pabilo8.kraftwerk.render.OpenGLTexture.ITextureRefreshListener;
import pl.pabilo8.kraftwerk.utils.GuiUtils;
import pl.pabilo8.kraftwerk.utils.GuiUtils.LocalizeMethod;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Pabilo8
 * @since 26.08.2021
 */
public class PanelTabTextures extends PanelTab implements ITextureRefreshListener
{
	public PanelTabTextures()
	{
		super("tabs.textures", Icons.texturesIcon, 312, Alignment.SOUTH_EAST, true, false);
	}

	JListTextures list;
	private final DefaultListModel<ModelTexture> textureList = new DefaultListModel<>();

	@Override
	public void init()
	{
		JMenuBar textureToolBar = new JMenuBar();
		textureToolBar.setLayout(new WrapLayout(FlowLayout.LEFT, 0, 0));
		textureToolBar.add(textureToolBar.add(GuiUtils.registerLocalized(GuiUtils.getIconButton(Icons.openTextureIcon, 32, "open_texture"), LocalizeMethod.ACTION_TOOLTIP)));
		textureToolBar.add(textureToolBar.add(GuiUtils.registerLocalized(GuiUtils.getIconButton(Icons.propertiesIcon, 32, "texture_properties"), LocalizeMethod.ACTION_TOOLTIP)));
		textureToolBar.add(textureToolBar.add(GuiUtils.registerLocalized(GuiUtils.getIconButton(Icons.editTextureIcon, 32, "edit_texture"), LocalizeMethod.ACTION_TOOLTIP)));
		textureToolBar.add(textureToolBar.add(GuiUtils.registerLocalized(GuiUtils.getIconButton(Icons.removeTextureIcon, 32, "remove_texture"), LocalizeMethod.ACTION_TOOLTIP)));
		add(textureToolBar, "grow, wrap");


		list = new JListTextures(textureList);
		list.setCellRenderer(new TextureListCellRenderer());
		list.putClientProperty(DarkListUI.KEY_ALTERNATE_ROW_COLOR, true);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFixedCellHeight(80);
		add(new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), "grow, push");

		setComponentPopupMenu(new StandardPopupMenu());
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
		list.handleRemove();
		onReload();
		list.clearSelection();
	}

	public void editSelectedTexture()
	{
		try
		{
			for(int selectedIndex : list.getSelectedIndices())
			{
				list.clearSelection();
				Desktop.getDesktop().edit(new File(textureList.get(selectedIndex).texturePath.toURI()));

			}
		}
		catch(IOException|URISyntaxException ignored)
		{

		}
		onReload();
	}
}
