package pl.pabilo8.kraftwerk.gui;

import com.github.weisj.darklaf.icons.DarkSVGIcon;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.swing.*;
import java.net.URI;

/**
 * @author Pabilo8
 * @since 15.08.2021
 */
@SuppressWarnings("unused")
public class Icons
{
	public static Icon newIcon;
	public static Icon openIcon;
	public static Icon saveIcon;
	public static Icon saveAsIcon;
	public static Icon recentIcon;
	public static Icon importIcon;
	public static Icon exportIcon;

	public static Icon screenshotIcon;
	public static Icon cameraIcon;
	public static Icon settingsIcon;
	public static Icon searchIcon;
	public static Icon runCommandIcon;
	public static Icon docsIcon;
	public static Icon gettingStartedIcon;
	public static Icon changelogIcon;
	public static Icon aboutIcon;
	public static Icon showConsole;

	public static Icon pluginInstallIcon;
	public static Icon pluginUninstallIcon;

	public static Icon propertiesIcon;
	public static Icon previewIcon;
	public static Icon elementsIcon;
	public static Icon propsIcon;
	public static Icon uvIcon;
	public static Icon texturesIcon;

	public static Icon openTextureIcon;
	public static Icon removeTextureIcon;
	public static Icon editTextureIcon;

	public static Icon undoIcon;
	public static Icon redoIcon;
	public static Icon cutIcon;
	public static Icon copyIcon;
	public static Icon pasteIcon;
	public static Icon removeIcon;

	public static Icon gridIcon;

	public static Icon markerIcon; // TODO 11.12.2021
	public static Icon faceIcon; // TODO 11.12.2021
	public static Icon boxIcon; // TODO 11.12.2021
	public static Icon shapeboxIcon; // TODO 11.12.2021
	public static Icon shapeIcon; // TODO 11.12.2021

	public static Icon visibleIcon;
	public static Icon invisibleIcon; // TODO 11.12.2021

	public static void loadIcons()
	{
		aboutIcon = loadSVGIcon("about");
		settingsIcon = loadSVGIcon("settings");
		visibleIcon = loadSVGIcon("visible");
		searchIcon = loadSVGIcon("search");
		runCommandIcon = loadSVGIcon("run_command");
		docsIcon = loadSVGIcon("docs");
		gettingStartedIcon = loadSVGIcon("getting_started");
		changelogIcon = loadSVGIcon("changelog");

		screenshotIcon = loadSVGIcon("screenshot");
		cameraIcon = loadSVGIcon("camera");

		elementsIcon = loadSVGIcon("tabs/elements");
		previewIcon = loadSVGIcon("tabs/preview");
		propertiesIcon = loadSVGIcon("tabs/properties");
		propsIcon = loadSVGIcon("tabs/props");
		uvIcon = loadSVGIcon("tabs/uv");
		texturesIcon = loadSVGIcon("tabs/textures");

		openTextureIcon = loadSVGIcon("textures/open_texture");
		removeTextureIcon= loadSVGIcon("textures/remove_texture");
		editTextureIcon= loadSVGIcon("textures/edit_texture");

		openIcon = loadSVGIcon("file/open");
		newIcon = loadSVGIcon("file/new");
		saveIcon = loadSVGIcon("file/save");
		saveAsIcon = loadSVGIcon("file/save_as");
		recentIcon = loadSVGIcon("file/recent");
		importIcon = loadSVGIcon("file/import");
		exportIcon = loadSVGIcon("file/export");

		pluginInstallIcon = loadSVGIcon("plugins/install");
		pluginUninstallIcon = loadSVGIcon("plugins/uninstall");

		gridIcon = loadSVGIcon("grid");

		cutIcon = loadSVGIcon("edit/cut");
		copyIcon = loadSVGIcon("edit/copy");
		pasteIcon = loadSVGIcon("edit/paste");
		removeIcon = loadSVGIcon("edit/remove");
		undoIcon = loadSVGIcon("edit/undo");
		redoIcon = loadSVGIcon("edit/redo");

		showConsole = loadSVGIcon("show_console");

	}

	public static DarkSVGIcon loadSVGIcon(String name)
	{
		final URI uri = ResourceUtils.getIconURI(name);
		if(uri!=null)
		{
			DarkSVGIcon icon = new DarkSVGIcon(() -> uri, 16, 16);
			UIManager.put("kraftwerk.icons."+name, icon);
			return icon;
		}
		return null;
	}
}
