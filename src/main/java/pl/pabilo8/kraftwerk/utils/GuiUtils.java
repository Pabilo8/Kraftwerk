package pl.pabilo8.kraftwerk.utils;

import com.github.weisj.darklaf.components.tabframe.PanelPopup;
import com.github.weisj.darklaf.components.tabframe.TabFramePopup;
import com.github.weisj.darklaf.util.Alignment;
import com.github.weisj.darklaf.util.DarkUIUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.miginfocom.swing.MigLayout;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.EditorProject;
import pl.pabilo8.kraftwerk.gui.action.ActionCommand;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author Pabilo8
 * @since 16.08.2021
 */
public class GuiUtils
{
	public static PanelPopup addSideTab(final String name, final @Nullable Icon icon, final JPanel panel, final Alignment a)
	{
		PanelPopup popup = new PanelPopup(name, icon, panel);
		Kraftwerk.INSTANCE.tabFrame.insertTab((TabFramePopup)popup, name, icon, a, Kraftwerk.INSTANCE.tabFrame.tabsForAlignment(a).size());
		Kraftwerk.INSTANCE.localizeList.add(popup);
		return popup;
	}

	public static void addToMenu(JMenu menu, Component... components)
	{
		for(Component component : components)
			menu.add(component);
	}

	public static JMenuItem getActionItem(String id, String action)
	{
		return getActionItem(id, action, null, (Icon)null);
	}

	public static JMenuItem getActionItem(String id, String action, Character mnemonic)
	{
		return getActionItem(id, action, mnemonic, (Icon)null);
	}

	public static JMenu getMenu(String id, @Nullable Character mnemonic)
	{
		return getMenu(id, mnemonic, (Icon)null);
	}

	public static JMenu getMenu(String id, @Nullable Character mnemonic, @Nullable String icon)
	{
		return getMenu(id, mnemonic, icon==null?null: UIManager.getIcon(icon));
	}

	public static JMenu getMenu(String id, @Nullable Character mnemonic, @Nullable Icon icon)
	{
		JMenu menu = GuiUtils.registerLocalized(new JMenu(id));
		if(mnemonic!=null)
			menu.setMnemonic(mnemonic);
		if(icon!=null)
			menu.setIcon(icon);

		return menu;
	}

	public static JMenuItem getActionItem(String id, String action, @Nullable Character mnemonic, @Nullable Icon icon)
	{
		JMenuItem item = registerLocalized(new JMenuItem(id));
		item.setAction(new ActionCommand(action));
		item.setText(id);
		if(icon!=null)
			item.setIcon(UIManager.getIcon(icon));
		if(mnemonic!=null)
			item.setMnemonic(mnemonic);
		return item;
	}

	public static JMenuItem getActionItem(String id, String action, @Nullable Character mnemonic, @Nullable String icon)
	{
		return getActionItem(id, action, mnemonic, icon==null?null: UIManager.getIcon(icon));
	}

	public static JButton getIconButton(@Nullable Icon icon, int size)
	{
		JButton button = new JButton();
		if(icon!=null)
			button.setIcon(icon);
		button.setPreferredSize(new Dimension(size, size));
		button.setMinimumSize(new Dimension(size, size));
		button.setMaximumSize(new Dimension(size, size));
		button.setSize(new Dimension(size, size));
		return button;
	}

	public static <T extends JMenuItem> T registerLocalized(T jMenu)
	{
		Kraftwerk.INSTANCE.localizeList.add(jMenu);
		return jMenu;
	}

	public static JPanel createTabPanel(String name, @Nullable Icon icon, Dimension preferred, Alignment alignment, boolean open, boolean debug)
	{
		StringBuilder layout = new StringBuilder();
		if(debug)
			layout.append("debug, ");
		layout.append("fillx, aligny top, toptobottom");
		JPanel panel = new JPanel(new MigLayout(layout.toString()));
		panel.setOpaque(true);
		panel.setPreferredSize(preferred);
		panel.setSize(preferred);
		PanelPopup popup = addSideTab(name, icon, panel, alignment);
		Kraftwerk.INSTANCE.tabFrame.validate();
		if(open)
			Kraftwerk.INSTANCE.tabFrame.openTab((TabFramePopup)popup);
		return panel;
	}

	public static void saveToFile(boolean forceNewFile)
	{
		File file = null;

		if(forceNewFile||Kraftwerk.INSTANCE.currentProject==null||Kraftwerk.INSTANCE.currentProject.filePath==null)
		{
			JFileChooser chooser = new JFileChooser((String)null);
			chooser.addChoosableFileFilter(new FileNameExtensionFilter(ResourceUtils.translateString(Kraftwerk.res, "project_file")+" (.kftw)", "kftw"));
			chooser.setAcceptAllFileFilterUsed(false);
			SwingUtilities.invokeLater(() -> DarkUIUtil.getWindow(chooser).toFront());
			if(chooser.showSaveDialog(Kraftwerk.INSTANCE)==JFileChooser.APPROVE_OPTION)
				file = chooser.getSelectedFile();
		}
		else
		{
			try
			{
				file = new File(Kraftwerk.INSTANCE.currentProject.filePath.toURI());
			}
			catch(Exception e)
			{
				Kraftwerk.logger.info("Couldn't write to file. "+e.getMessage());
			}
		}

		if(file!=null)
		{
			try
			{
				if(!file.getAbsolutePath().endsWith(".kftw"))
				{
					file = new File(file.getAbsolutePath()+".kftw");
				}
				Gson gson = new GsonBuilder()
						.setPrettyPrinting()
						.create();
				JsonObject jsonObject = Kraftwerk.INSTANCE.currentProject.toJSON();
				FileWriter fileWriter = new FileWriter(file);
				gson.toJson(jsonObject, fileWriter);
				fileWriter.close();
				Kraftwerk.INSTANCE.currentProject.filePath = file.toURI().toURL();
				Kraftwerk.logger.info(String.format("Saved project file to %s.", Kraftwerk.INSTANCE.currentProject.filePath));
				Kraftwerk.onProjectLoad(Kraftwerk.INSTANCE.currentProject);
			}
			catch(Exception e)
			{
				Kraftwerk.logger.info("Couldn't write to file. "+e.getMessage());
			}
		}
	}

	public static void openProject(File file)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			JsonObject jsonObject = JsonParser.parseReader(br).getAsJsonObject();
			EditorProject project = EditorProject.fromJSON(jsonObject);
			project.filePath = file.toURI().toURL();
			Kraftwerk.logger.info(String.format("Loaded project file from %s.", project.filePath));
			Kraftwerk.onProjectLoad(project);
		}
		catch(Exception e)
		{
			Kraftwerk.logger.warning("Couldn't read project file. "+e.getMessage());
		}
	}
}
