package pl.pabilo8.kraftwerk.gui.panel;

import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.EditorProject.ModelRestrictionTemplate;
import pl.pabilo8.kraftwerk.editor.modelworkers.ModelWorker.ModelExporter;
import pl.pabilo8.kraftwerk.editor.modelworkers.ModelWorker.ModelImporter;
import pl.pabilo8.kraftwerk.gui.Icons;
import pl.pabilo8.kraftwerk.gui.action.ActionCommand;
import pl.pabilo8.kraftwerk.gui.action.ActionNewProjectFromTemplate;
import pl.pabilo8.kraftwerk.utils.GuiUtils;

import javax.swing.*;

/**
 * @author Pabilo8
 * @since 25.11.2021
 */

@SuppressWarnings("unused")
public class MenuBarTop extends JMenuBar
{
	private final JMenu menuFile, menuEdit, menuView, menuModel, menuRendering, menuHelp;
	private final JMenu menuItemNew, menuItemRecent, menuItemImport, menuItemExport;
	private final JMenuItem menuItemOpen, menuItemSave, menuItemSaveAs, menuSettings;
	private final JMenuItem menuItemUndo, menuItemRedo, menuItemCut, menuItemCopy, menuItemPaste, menuItemDelete;
	private final JMenuItem menuItemToggleCamera;
	private final JMenuItem menuItemFindAction, menuItemDocs, menuItemGettingStarted, menuItemChangelog, menuItemAbout;
	private final JMenuItem menuItemScreenshot, menuItemRecord360;

	public MenuBarTop()
	{
		this.menuFile = GuiUtils.getMenu("menubar.file", 'F');
		this.menuEdit = GuiUtils.getMenu("menubar.edit", 'E');
		this.menuView = GuiUtils.getMenu("menubar.view", 'V');
		this.menuModel = GuiUtils.getMenu("menubar.model", 'M');
		this.menuRendering = GuiUtils.getMenu("menubar.render", 'R');
		this.menuHelp = GuiUtils.getMenu("menubar.help", 'H');

		this.add(this.menuFile);
		this.add(this.menuEdit);
		this.add(this.menuView);
		this.add(this.menuModel);
		this.add(this.menuRendering);
		this.add(this.menuHelp);

		this.menuItemNew = GuiUtils.getMenu("menubar.file.new", 'N', Icons.newIcon);
		this.menuItemRecent = GuiUtils.getMenu("menubar.file.recent", 'R', Icons.recentIcon);
		this.menuItemImport = GuiUtils.getMenu("menubar.file.import", 'I', Icons.importIcon);
		this.menuItemExport = GuiUtils.getMenu("menubar.file.export", 'E', Icons.exportIcon);

		for(ModelImporter importer : Kraftwerk.INSTANCE.modelImporters)
		{
			JMenuItem item = GuiUtils.registerLocalized(new JMenuItem("model_worker."+importer.name));
			item.setAction(ActionCommand.getActionCommand("import_model", importer.name));
			item.setText("model_worker."+importer.name);
			if(importer.icon!=null)
				item.setIcon(importer.icon);
			this.menuItemImport.add(item);
		}

		for(ModelExporter exporter : Kraftwerk.INSTANCE.modelExporters)
		{
			JMenuItem item = GuiUtils.registerLocalized(new JMenuItem("model_worker."+exporter.name));
			item.setAction(ActionCommand.getActionCommand("export_model", exporter.name));
			item.setText("model_worker."+exporter.name);
			if(exporter.icon!=null)
				item.setIcon(exporter.icon);
			this.menuItemExport.add(item);
		}

		for(ModelRestrictionTemplate template : ModelRestrictionTemplate.values())
		{
			JMenuItem item = GuiUtils.registerLocalized(new JMenuItem("model_template."+template.getName()));
			item.setAction(new ActionNewProjectFromTemplate(template));
			item.setText("model_template."+template.getName());
			if(template.icon!=null)
				item.setIcon(template.icon);
			this.menuItemNew.add(item);
		}


		GuiUtils.addToMenu(this.menuFile,
				this.menuItemNew,
				this.menuItemRecent,
				this.menuItemOpen = GuiUtils.getActionItem("menubar.file.open", "open", 'O', Icons.openIcon),
				this.menuItemSave = GuiUtils.getActionItem("menubar.file.save", "save", 'S', Icons.saveIcon),
				this.menuItemSaveAs = GuiUtils.getActionItem("menubar.file.save_as", "save_as", 'A',Icons.saveAsIcon),
				this.menuItemImport,
				this.menuItemExport,
				this.menuSettings = GuiUtils.getActionItem("menubar.file.settings", "settings", 'T', Icons.settingsIcon)
		);

		GuiUtils.addToMenu(this.menuEdit,
				this.menuItemUndo = GuiUtils.getActionItem("action.undo", "undo", null, Icons.undoIcon),
				this.menuItemRedo = GuiUtils.getActionItem("action.redo", "redo", null, Icons.redoIcon),
				this.menuItemCut = GuiUtils.getActionItem("action.cut", "cut", null, Icons.cutIcon),
				this.menuItemCopy = GuiUtils.getActionItem("action.copy", "copy", null, Icons.copyIcon),
				this.menuItemPaste = GuiUtils.getActionItem("action.paste", "paste", null, Icons.pasteIcon),
				this.menuItemDelete = GuiUtils.getActionItem("action.remove", "delete", null, Icons.removeIcon)
		);

		GuiUtils.addToMenu(this.menuView,
				this.menuItemToggleCamera = GuiUtils.getActionItem("menubar.view.toggle_camera", "toggle_camera", 'C',Icons.cameraIcon)
		);

		GuiUtils.addToMenu(this.menuRendering,
				this.menuItemScreenshot = GuiUtils.getActionItem("menubar.render.screenshot", "screenshot", null, Icons.screenshotIcon),
				this.menuItemRecord360 = GuiUtils.getActionItem("menubar.render.record360", "record360")
		);

		GuiUtils.addToMenu(this.menuHelp,
				this.menuItemFindAction = GuiUtils.getActionItem("menubar.help.find_action", "find_action", 'F', Icons.runCommandIcon),
				this.menuItemDocs = GuiUtils.getActionItem("menubar.help.docs", "docs", 'D', Icons.docsIcon),
				this.menuItemGettingStarted = GuiUtils.getActionItem("menubar.help.getting_started", "getting_started", 'S', Icons.gettingStartedIcon),
				this.menuItemChangelog = GuiUtils.getActionItem("menubar.help.changelog", "changelog", 'C', Icons.changelogIcon),
				this.menuItemAbout = GuiUtils.getActionItem("menubar.help.about", "about", 'A', Icons.aboutIcon)

		);
	}
}
