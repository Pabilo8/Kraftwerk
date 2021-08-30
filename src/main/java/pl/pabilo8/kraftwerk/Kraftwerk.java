package pl.pabilo8.kraftwerk;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.components.tabframe.JTabFrame;
import com.github.weisj.darklaf.components.tabframe.TabFramePopup;
import com.github.weisj.darklaf.components.tabframe.TabFrameTab;
import com.github.weisj.darklaf.components.tabframe.TabFrameTabLabel;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.util.DarkUIUtil;
import com.jogamp.opengl.GL4bc;
import jnafilechooser.api.JnaFileChooser;
import pl.pabilo8.kraftwerk.editor.EditorProject;
import pl.pabilo8.kraftwerk.editor.EditorProject.ModelRestrictionTemplate;
import pl.pabilo8.kraftwerk.editor.modelworkers.DefaultModelExporters;
import pl.pabilo8.kraftwerk.editor.modelworkers.DefaultModelImporters;
import pl.pabilo8.kraftwerk.editor.modelworkers.ModelExporter;
import pl.pabilo8.kraftwerk.editor.modelworkers.ModelImporter;
import pl.pabilo8.kraftwerk.gui.*;
import pl.pabilo8.kraftwerk.gui.action.ActionCommand;
import pl.pabilo8.kraftwerk.gui.action.ActionNewProjectFromTemplate;
import pl.pabilo8.kraftwerk.gui.panel.*;
import pl.pabilo8.kraftwerk.utils.GuiUtils;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Pabilo8
 * @since 13.08.2021
 */
public class Kraftwerk extends JFrame implements ActionListener, ChangeListener
{
	public int defaultFPS;
	public ArrayList<JComponent> localizeList = new ArrayList<>();

	public static Kraftwerk INSTANCE;
	private static final HashMap<KeyStroke, Action> keyBinds = new HashMap<>();

	JMenuBar menuBarTop;
	private JMenu menuFile, menuEdit, menuView, menuModel, menuRendering, menuHelp;
	private JMenu menuItemNew, menuItemRecent, menuItemImport, menuItemExport;
	private JMenuItem menuItemOpen, menuItemSave, menuItemSaveAs, menuSettings;
	private JMenuItem menuItemUndo, menuItemRedo, menuItemCut, menuItemCopy, menuItemPaste, menuItemDelete;
	private JMenuItem menuItemToggleCamera;
	private JMenuItem menuItemFindAction, menuItemDocs, menuItemGettingStarted, menuItemChangelog, menuItemAbout;
	private JMenuItem menuItemScreenshot, menuItemRecord360;

	public static Image programIcon;

	public JPanel panelMain;
	public PanelModelEditor panelModelEditor;
	public EditorProject currentProject;
	public JTabFrame tabFrame;

	private ArrayList<PanelTab> registeredTabs = new ArrayList<>();
	public PanelTab panelPartProperties, panelPartPreview, panelModelElements, panelModelProps, panelUV;
	public PanelTabTextures panelTextures;

	public ArrayList<ModelImporter> modelImporters = new ArrayList<>();
	public ArrayList<ModelExporter> modelExporters = new ArrayList<>();
	private final ArrayList<JMenuItem> modelImporterButtons = new ArrayList<>();
	private final ArrayList<JMenuItem> modelExporterButtons = new ArrayList<>();
	private final ArrayList<JMenuItem> modelTemplateButtons = new ArrayList<>();

	public static GL4bc gl;

	public static Logger logger = Logger.getLogger("Kraftwerk");
	public static Locale locale;
	public static ResourceBundle res, fallbackRes;

	//Change logger format to [LOGGER NAME] [TIME] MESSAGE
	static
	{
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tT] [%4$s] %5$s%6$s%n");
	}

	public Kraftwerk() throws HeadlessException
	{
		super("Kraftwerk");
	}

	public static void main(String[] args)
	{
		logger.info("Starting");
		LafManager.install();
		setLookAndFeel();
		INSTANCE = new Kraftwerk();
		INSTANCE.defaultFPS = INSTANCE.getDefaultFPS();

		Icons.loadIcons();

		INSTANCE.panelMain = new JPanel();
		INSTANCE.panelMain.setLayout(new BorderLayout());
		INSTANCE.setLocationRelativeTo(null);
		INSTANCE.setContentPane(INSTANCE.panelMain);
		INSTANCE.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		INSTANCE.setVisible(true);
		INSTANCE.setSize(800, 600);

		//Yes/No/Cancel
		INSTANCE.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent evt)
			{
				if(INSTANCE.currentProject.hasBeenChanged)
				{
					int resp = createQuitDialog();

					switch(resp)
					{
						case JOptionPane.YES_OPTION:
						case JOptionPane.NO_OPTION:
							INSTANCE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							break;
						case JOptionPane.CANCEL_OPTION:
							INSTANCE.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							break;
					}
				}
			}
		});
		//KeyStrokes (i.e. ctrl+o opens a file)
		//https://stackoverflow.com/a/8485873/9876980
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher(e -> {
			KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
			if(keyBinds.containsKey(keyStroke))
			{
				final Action a = keyBinds.get(keyStroke);
				final ActionEvent event = new ActionEvent(e.getSource(), e.getID(), null);
				SwingUtilities.invokeLater(() -> a.actionPerformed(event));
				return true;
			}
			return false;
		});

		programIcon = Objects.requireNonNull(ResourceUtils.texResource("kraftwerk_icon.png")).getScaledInstance(256, 256, Image.SCALE_SMOOTH);

		registerModelImporters();
		registerModelExporters();

		initCenter();
		preInitTabs();
		initTabs();
		initKeyBinds();

		if(Kraftwerk.INSTANCE.currentProject==null)
			logger.info("no project!");

		Kraftwerk.INSTANCE.currentProject = new EditorProject();
		if(args.length > 0)
		{
			try
			{
				GuiUtils.openProject(new File(args[0]));
			}
			catch(Exception e)
			{
				logger.warning("Couldn't read project file, invalid file path. "+e);
			}

		}
		onProjectLoad(Kraftwerk.INSTANCE.currentProject);

		refresh();


		logger.info("GUI Initialization Completed");
	}

	public static int createQuitDialog()
	{
		int resp = JOptionPane.showConfirmDialog(INSTANCE, ResourceUtils.formatString(res, "window.quit", INSTANCE.currentProject.name),
				ResourceUtils.translateString(res, "window.quit.title"), JOptionPane.YES_NO_CANCEL_OPTION);
		if(resp==JOptionPane.YES_OPTION)
		{
			GuiUtils.saveToFile(false);
			//logger.info("TOD0: Add project saving");
		}
		return resp;
	}

	private static void registerModelImporters()
	{
		registerModelImporter(new DefaultModelImporters.VanillaJavaImporter());
		registerModelImporter(new DefaultModelImporters.VanillaJSONImporter());
		registerModelImporter(new DefaultModelImporters.ForgeMultipartImporter());
		registerModelImporter(new DefaultModelImporters.JavaTMTImporter());
		registerModelImporter(new DefaultModelImporters.SMPToolboxImporter());
		registerModelImporter(new DefaultModelImporters.BlockBenchImporter());
		registerModelImporter(new DefaultModelImporters.TechneImporter());
		registerModelImporter(new DefaultModelImporters.TabulaImporter());
		registerModelImporter(new DefaultModelImporters.ModelLoaderMCXImporter());
	}

	private static void registerModelExporters()
	{
		registerModelExporter(new DefaultModelExporters.VanillaJavaExporter());
		registerModelExporter(new DefaultModelExporters.VanillaJSONExporter());
		registerModelExporter(new DefaultModelExporters.ForgeMultipartExporter());
		registerModelExporter(new DefaultModelExporters.JavaTMTExporter());
		registerModelExporter(new DefaultModelExporters.SMPToolboxExporter());
		registerModelExporter(new DefaultModelExporters.BlockBenchExporter());
		registerModelExporter(new DefaultModelExporters.TechneExporter());
		registerModelExporter(new DefaultModelExporters.TabulaExporter());
		registerModelExporter(new DefaultModelExporters.ModelLoaderMCXExporter());
	}

	private static void refresh()
	{
		initMenuBar();
		INSTANCE.setIconImage(programIcon);
		INSTANCE.validate();
		INSTANCE.registeredTabs.forEach(PanelTab::refresh);

		//setLookAndFeel();
		updateLanguage();
		onProjectLoad(Kraftwerk.INSTANCE.currentProject);
	}

	public static void registerTab(PanelTab panelTab)
	{
		Kraftwerk.INSTANCE.registeredTabs.add(panelTab);
	}

	private int getDefaultFPS()
	{
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
	}

	private static void initMenuBar()
	{
		INSTANCE.menuBarTop = new JMenuBar();
		INSTANCE.setJMenuBar(INSTANCE.menuBarTop);

		INSTANCE.menuFile = GuiUtils.getMenu("menubar.file", 'F');
		INSTANCE.menuEdit = GuiUtils.getMenu("menubar.edit", 'E');
		INSTANCE.menuView = GuiUtils.getMenu("menubar.view", 'V');
		INSTANCE.menuModel = GuiUtils.getMenu("menubar.model", 'M');
		INSTANCE.menuRendering = GuiUtils.getMenu("menubar.render", 'R');
		INSTANCE.menuHelp = GuiUtils.getMenu("menubar.help", 'H');

		INSTANCE.menuBarTop.add(INSTANCE.menuFile);
		INSTANCE.menuBarTop.add(INSTANCE.menuEdit);
		INSTANCE.menuBarTop.add(INSTANCE.menuView);
		INSTANCE.menuBarTop.add(INSTANCE.menuModel);
		INSTANCE.menuBarTop.add(INSTANCE.menuRendering);
		INSTANCE.menuBarTop.add(INSTANCE.menuHelp);

		INSTANCE.menuItemNew = GuiUtils.getMenu("menubar.file.new", 'N', "FileView.fileIcon");
		INSTANCE.menuItemRecent = GuiUtils.getMenu("menubar.file.recent", 'R');
		INSTANCE.menuItemImport = GuiUtils.getMenu("menubar.file.import", 'I');
		INSTANCE.menuItemExport = GuiUtils.getMenu("menubar.file.export", 'E');

		Kraftwerk.INSTANCE.modelImporterButtons.clear();
		for(ModelImporter importer : Kraftwerk.INSTANCE.modelImporters)
		{
			JMenuItem item = GuiUtils.registerLocalized(new JMenuItem("model_worker."+importer.name));
			if(importer.icon!=null)
				item.setIcon(importer.icon);

			item.setAction(importer.action);
			item.setText("model_worker."+importer.name);
			Kraftwerk.INSTANCE.modelImporterButtons.add(item);
			INSTANCE.menuItemImport.add(item);
		}

		Kraftwerk.INSTANCE.modelExporterButtons.clear();
		for(ModelExporter exporter : Kraftwerk.INSTANCE.modelExporters)
		{
			JMenuItem item = GuiUtils.registerLocalized(new JMenuItem("model_worker."+exporter.name));
			if(exporter.icon!=null)
				item.setIcon(exporter.icon);
			item.setAction(exporter.action);
			item.setText("model_worker."+exporter.name);
			Kraftwerk.INSTANCE.modelExporterButtons.add(item);
			INSTANCE.menuItemExport.add(item);
		}

		Kraftwerk.INSTANCE.modelTemplateButtons.clear();
		for(ModelRestrictionTemplate template : ModelRestrictionTemplate.values())
		{
			JMenuItem item = GuiUtils.registerLocalized(new JMenuItem("model_template."+template.getName()));
			if(template.icon!=null)
				item.setIcon(template.icon);
			item.setAction(new ActionNewProjectFromTemplate(template));
			item.setText("model_template."+template.getName());
			Kraftwerk.INSTANCE.modelTemplateButtons.add(item);
			INSTANCE.menuItemNew.add(item);
		}


		GuiUtils.addToMenu(INSTANCE.menuFile,
				INSTANCE.menuItemNew,
				INSTANCE.menuItemRecent,
				INSTANCE.menuItemOpen = GuiUtils.getActionItem("menubar.file.open", "open", 'O', "FileView.directoryIcon"),
				INSTANCE.menuItemSave = GuiUtils.getActionItem("menubar.file.save", "save", 'S', "FileView.floppyDriveIcon"),
				INSTANCE.menuItemSaveAs = GuiUtils.getActionItem("menubar.file.save_as", "save_as", 'A'),
				INSTANCE.menuItemImport,
				INSTANCE.menuItemExport,
				INSTANCE.menuSettings = GuiUtils.getActionItem("menubar.file.settings", "settings", 'T', UIManager.getIcon("ThemeSettings.icon"))
		);

		GuiUtils.addToMenu(INSTANCE.menuEdit,
				INSTANCE.menuItemUndo = GuiUtils.getActionItem("menubar.edit.undo", "undo", null),
				INSTANCE.menuItemRedo = GuiUtils.getActionItem("menubar.edit.redo", "redo", null),
				INSTANCE.menuItemCut = GuiUtils.getActionItem("menubar.edit.cut", "cut", null),
				INSTANCE.menuItemCopy = GuiUtils.getActionItem("menubar.edit.copy", "copy", null),
				INSTANCE.menuItemPaste = GuiUtils.getActionItem("menubar.edit.paste", "paste", null),
				INSTANCE.menuItemDelete = GuiUtils.getActionItem("menubar.edit.delete", "delete", null)
		);

		GuiUtils.addToMenu(INSTANCE.menuView,
				INSTANCE.menuItemToggleCamera = GuiUtils.getActionItem("menubar.view.toggle_camera", "toggle_camera")
		);

		GuiUtils.addToMenu(INSTANCE.menuRendering,
				INSTANCE.menuItemScreenshot = GuiUtils.getActionItem("menubar.render.screenshot", "screenshot"),
				INSTANCE.menuItemRecord360 = GuiUtils.getActionItem("menubar.render.record360", "record360")
		);

		GuiUtils.addToMenu(INSTANCE.menuHelp,
				INSTANCE.menuItemFindAction = GuiUtils.getActionItem("menubar.help.find_action", "find_action", 'F'),
				INSTANCE.menuItemDocs = GuiUtils.getActionItem("menubar.help.docs", "docs", 'D', "FileView.textFileIcon"),
				INSTANCE.menuItemGettingStarted = GuiUtils.getActionItem("menubar.help.getting_started", "getting_started", 'S'),
				INSTANCE.menuItemChangelog = GuiUtils.getActionItem("menubar.help.changelog", "changelog", 'C'),
				INSTANCE.menuItemAbout = GuiUtils.getActionItem("menubar.help.about", "about", 'A')

		);
	}

	public static void registerModelImporter(ModelImporter modelImporter)
	{
		Kraftwerk.INSTANCE.modelImporters.add(modelImporter);
	}

	public static void registerModelExporter(ModelExporter modelExporter)
	{
		Kraftwerk.INSTANCE.modelExporters.add(modelExporter);
	}

	private static void initCenter()
	{
		INSTANCE.tabFrame = new JTabFrame();
		INSTANCE.tabFrame.setDndEnabled(true);

		INSTANCE.panelModelEditor = new PanelModelEditor();
		INSTANCE.panelModelEditor.setMinimumSize(new Dimension(256, 256));

		INSTANCE.panelMain.add(INSTANCE.tabFrame, BorderLayout.CENTER);
		INSTANCE.tabFrame.setContent(INSTANCE.panelModelEditor);
	}

	private static void preInitTabs()
	{
		INSTANCE.panelModelElements = new PanelTabElements();
		INSTANCE.panelModelProps = new PanelTabProps();
		INSTANCE.panelPartProperties = new PanelTabProperties();
		INSTANCE.panelPartPreview = new PanelTabPreview();
		INSTANCE.panelTextures = new PanelTabTextures();
		INSTANCE.panelUV = new PanelTabUV();
	}

	private static void initTabs()
	{
		for(PanelTab tab : INSTANCE.registeredTabs)
			tab.init();
	}

	private static void initKeyBinds()
	{
		addKeyBind("open", KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		addKeyBind("save", KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		addKeyBind("save_as", KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK+KeyEvent.SHIFT_MASK));

		addKeyBind("undo", KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		addKeyBind("redo", KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_MASK));
		addKeyBind("cut", KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		addKeyBind("copy", KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		addKeyBind("paste", KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
	}

	public static void addKeyBind(String action, KeyStroke key)
	{
		keyBinds.put(key, new ActionCommand(action));
	}

	public static void setLookAndFeel()
	{
		LafManager.installTheme(new DarculaTheme());
		logger.info("Loaded Darcula Theme");
	}

	public static void updateLanguage()
	{
		locale = new Locale("pl", "PL");
		UIManager.getDefaults().setDefaultLocale(locale);

		//Ah yes, the Programming Patriotism™
		if(locale.equals(new Locale("pl", "PL")))
			UIManager.getDefaults().addResourceBundle("pl.pabilo8.kraftwerk.lang.uimanager.windows");

		res = ResourceUtils.getResourceBundle(locale);
		fallbackRes = ResourceUtils.getResourceBundle(new Locale("en", "US"));


		for(JComponent component : Kraftwerk.INSTANCE.localizeList)
		{
			if(component instanceof JMenuItem)
				((JMenuItem)component).setText(ResourceUtils.translateString(res, ((JMenuItem)component).getText()));
			else if(component instanceof TabFramePopup)
			{
				TabFramePopup tab = (TabFramePopup)component;
				String title = ResourceUtils.translateString(res, (tab).getTitle());
				tab.setTitle(title);
				TabFrameTab componentAt = tab.getTabFrame().getTabComponentAt(((TabFramePopup)component).getAlignment(), ((TabFramePopup)component).getIndex());
				if(componentAt instanceof TabFrameTabLabel)
					((TabFrameTabLabel)componentAt).setTitle(title);
			}
		}

		Kraftwerk.INSTANCE.localizeList.clear();

		logger.info("Updated Language");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

	}

	public void commandPerformed(ActionCommand command)
	{
		switch(command.command)
		{
			case "about":
				new AboutDialog();
				break;
			case "open":
			{
				JnaFileChooser chooser = new JnaFileChooser();
				chooser.addFilter(ResourceUtils.translateString(res, "project_file")+" (.kftw)", "kftw");
				if(chooser.showOpenDialog(Kraftwerk.INSTANCE))
					GuiUtils.openProject(chooser.getSelectedFile());
			}
			break;
			case "save":
				GuiUtils.saveToFile(false);
				break;
			case "save_as":
				GuiUtils.saveToFile(true);
				break;
			case "settings":
				new SettingsDialog();
				break;
			case "open_texture":
			{
				GuiUtils.openTexture();
			}
			break;
			case "edit_texture":
			{

			}
			break;
			case "remove_texture":
			{
				INSTANCE.panelTextures.removeSelectedTexture();
			}
			break;
			case "toggle_camera":
				panelModelEditor.toggleCamera();
				break;
			case "refresh":
				refresh();
				break;
		}
		logger.info(String.format("command>%s", command.command));
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{

	}

	public static void onProjectLoad(@Nullable EditorProject project)
	{
		//Shouldn't happen!
		if(project==null)
		{
			Kraftwerk.INSTANCE.setTitle("Kraftwerk");
			return;
		}
		if(project.filePath==null)
			Kraftwerk.INSTANCE.setTitle("Kraftwerk - "+project.name);
		else
		{
			try
			{
				Kraftwerk.INSTANCE.setTitle("Kraftwerk - "+project.name+(String.format(" [%s]", new File(project.filePath.toURI()).getName())));

			}
			catch(URISyntaxException e)
			{
				Kraftwerk.INSTANCE.setTitle("Kraftwerk - "+project.name);
			}
		}

		if(Kraftwerk.INSTANCE.currentProject!=project)
			Kraftwerk.INSTANCE.currentProject = project;
	}
}
