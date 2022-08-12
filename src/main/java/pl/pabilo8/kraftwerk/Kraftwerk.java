package pl.pabilo8.kraftwerk;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.components.tabframe.JTabFrame;
import com.github.weisj.darklaf.components.tabframe.TabFramePopup;
import com.github.weisj.darklaf.components.tabframe.TabFrameTab;
import com.github.weisj.darklaf.components.tabframe.TabFrameTabLabel;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jogamp.opengl.GL4bc;
import jnafilechooser.api.JnaFileChooser;
import pl.pabilo8.kraftwerk.editor.EditorProject;
import pl.pabilo8.kraftwerk.editor.ICopyAbleContainer;
import pl.pabilo8.kraftwerk.editor.elements.ModelElement;
import pl.pabilo8.kraftwerk.editor.elements.ModelElementBox;
import pl.pabilo8.kraftwerk.editor.elements.ModelElementShapebox;
import pl.pabilo8.kraftwerk.editor.modelworkers.*;
import pl.pabilo8.kraftwerk.gui.AboutDialog;
import pl.pabilo8.kraftwerk.gui.FindActionDialog;
import pl.pabilo8.kraftwerk.gui.Icons;
import pl.pabilo8.kraftwerk.gui.SettingsDialog;
import pl.pabilo8.kraftwerk.gui.action.ActionCommand;
import pl.pabilo8.kraftwerk.gui.panel.*;
import pl.pabilo8.kraftwerk.utils.CommandLineUtils.CommandThread;
import pl.pabilo8.kraftwerk.utils.GuiUtils;
import pl.pabilo8.kraftwerk.utils.GuiUtils.LocalizeMethod;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
	public static final ArrayList<ActionCommand> actionCommands = new ArrayList<>();

	public static Image programIcon;

	public Thread threadCMD;
	public JPanel panelMain;
	public PanelModelEditor panelModelEditor;
	public EditorProject currentProject;
	public JTabFrame tabFrame;

	private final ArrayList<PanelTab> registeredTabs = new ArrayList<>();
	public PanelTab panelPartPreview, panelModelProps, panelUV;
	public PanelTabTextures panelTextures;
	public PanelTabElements panelModelElements;
	public PanelTabProperties panelPartProperties;

	public ArrayList<ModelWorker.ModelImporter> modelImporters = new ArrayList<>();
	public ArrayList<ModelWorker.ModelExporter> modelExporters = new ArrayList<>();
	public HashMap<String, Supplier<ModelElement>> modelElementSuppliers = new HashMap<>();

	public static GL4bc gl;

	public static Logger logger = Logger.getLogger("Kraftwerk");
	public static Locale locale;
	public static ResourceBundle res, fallbackRes;

	//Change logger format to [LOGGER NAME] [TIME] MESSAGE
	static
	{
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tT] [%4$s] %5$s%6$s%n");
		try
		{

			// This block configure the logger with handler and formatter
			FileHandler fh = new FileHandler("kraftwerk.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		}
		catch(SecurityException|IOException e)
		{
			logger.severe("Unable to save log to a file. Messages will be available in console only.");
		}

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
		registerModelElementSuppliers();

		initCenter();
		preInitTabs();
		initTabs();
		initKeyBinds();
		initBottomBar();

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
		if(Arrays.asList(args).contains("--cmd"))
			createCommandLineThread();

		logger.info("GUI Initialization Completed");
	}

	public static void createCommandLineThread()
	{
		logger.info("Initialized command line reader");
		Kraftwerk.INSTANCE.threadCMD = new Thread(new CommandThread());
		Kraftwerk.INSTANCE.threadCMD.start();
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
		registerModelImporter(new DefaultModelImporters.JsonTMTImporter());
		registerModelImporter(new DefaultModelImporters.SMPToolboxImporter());
		registerModelImporter(new DefaultModelImporters.BlockBenchImporter());
		registerModelImporter(new DefaultModelImporters.TechneImporter());
		registerModelImporter(new DefaultModelImporters.TabulaImporter());
		registerModelImporter(new DefaultModelImporters.ModelLoaderMCXImporter());
		registerModelImporter(new DefaultModelImporters.OBJImporter());
	}

	private static void registerModelExporters()
	{
		registerModelExporter(new DefaultModelExporters.VanillaJavaExporter());
		registerModelExporter(new DefaultModelExporters.VanillaJSONExporter());
		registerModelExporter(new DefaultModelExporters.ForgeMultipartExporter());
		registerModelExporter(new DefaultModelExporters.JavaTMTExporter());
		registerModelExporter(new DefaultModelExporters.JsonTMTExporter());
		registerModelExporter(new DefaultModelExporters.SMPToolboxExporter());
		registerModelExporter(new DefaultModelExporters.BlockBenchExporter());
		registerModelExporter(new DefaultModelExporters.TechneExporter());
		registerModelExporter(new DefaultModelExporters.TabulaExporter());
		registerModelExporter(new DefaultModelExporters.ModelLoaderMCXExporter());
		registerModelExporter(new DefaultModelExporters.OBJExporter());
	}

	private static void registerModelElementSuppliers()
	{
		//registerModelSupplier("face", ModelElementFace::new);
		registerModelSupplier("box", ModelElementBox::new);
		registerModelSupplier("shapebox", ModelElementShapebox::new);
		//registerModelSupplier("shape", ModelElementShape::new);
	}

	private static void refresh()
	{
		actionCommands.clear();
		INSTANCE.setJMenuBar(new MenuBarTop());
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

	public static void registerModelImporter(ModelWorker.ModelImporter modelImporter)
	{
		Kraftwerk.INSTANCE.modelImporters.add(modelImporter);
	}

	public static void registerModelExporter(ModelWorker.ModelExporter modelExporter)
	{
		Kraftwerk.INSTANCE.modelExporters.add(modelExporter);
	}

	public static void registerModelSupplier(String name, Supplier<ModelElement> supplier)
	{
		Kraftwerk.INSTANCE.modelElementSuppliers.put(name, supplier);
	}

	@Nullable
	public static ModelElement supplyElement(String name)
	{
		Supplier<ModelElement> supplier = Kraftwerk.INSTANCE.modelElementSuppliers.get(name);
		if(supplier==null)
			return null;
		return supplier.get();
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

		INSTANCE.panelModelElements.setSize((int)(INSTANCE.getWidth()*0.25f),INSTANCE.panelModelElements.getHeight());
		INSTANCE.panelTextures.setSize((int)(INSTANCE.getWidth()*0.3f),INSTANCE.panelModelElements.getHeight());
	}

	private static void initBottomBar()
	{

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
		addKeyBind("remove", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.KEY_LOCATION_UNKNOWN));

		addKeyBind("find_action", KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK+KeyEvent.SHIFT_MASK));
	}

	public static void addKeyBind(String action, KeyStroke key)
	{
		keyBinds.put(key, ActionCommand.getActionCommand(action));
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
			else if(component instanceof JButton)
			{
				LocalizeMethod method = ((LocalizeMethod)component.getClientProperty("kraftwerk.i18n"));

				if(method==null||method==LocalizeMethod.TOOLTIP)
					component.setToolTipText(ResourceUtils.translateString(Kraftwerk.res, component.getToolTipText()));
				else if(method==LocalizeMethod.ACTION_TOOLTIP)
					component.setToolTipText(((ActionCommand)((JButton)component).getAction()).getTranslatedName());

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
		logger.info(String.format("command>%s%s", command.command,
				Arrays.toString(command.arguments)
						.replace('[', ' ')
						.replace(']', ' ')
						.replaceAll("\\s+$", "")
		));

		switch(command.command)
		{
			case "about":
				new AboutDialog();
				break;
			case "find_action":
				new FindActionDialog();
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
			case "import_model":
			{
				if(command.arguments.length > 0)
				{
					Optional<ModelWorker.ModelImporter> first = modelImporters.stream().filter(modelImporter -> modelImporter.name.equals(command.arguments[0])).findFirst();
					first.ifPresent(ModelWorker::handleDialog);
				}
			}
			break;
			case "export_model":
			{
				if(command.arguments.length > 0)
				{
					Optional<ModelWorker.ModelExporter> first = modelExporters.stream().filter(modelExporter -> modelExporter.name.equals(command.arguments[0])).findFirst();
					first.ifPresent(ModelWorker::handleDialog);
				}
			}
			break;
			case "open_texture":
			{
				GuiUtils.openTexture();
			}
			break;
			case "texture_properties":
			{

			}
			break;
			case "remove_texture":
			{
				INSTANCE.panelTextures.removeSelectedTexture();
			}
			break;
			case "edit_texture":
			{
				INSTANCE.panelTextures.editSelectedTexture();
			}
			break;
			case "add_element":
			{
				if(command.arguments.length > 0)
					INSTANCE.panelModelElements.addModelElement(command.arguments[0]);
			}
			break;
			case "toggle_camera":
				panelModelEditor.toggleCamera();
				break;
			case "refresh":
				refresh();
				break;
			case "cut":
			case "copy":
			{
				Component owner = INSTANCE.getMostRecentFocusOwner();
				if(owner instanceof ICopyAbleContainer)
				{
					ICopyAbleContainer tree = (ICopyAbleContainer)owner;
					JsonArray array = command.command.equals("copy")?tree.handleCopy(): tree.handleCut();

					if(!array.isEmpty())
					{
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						StringSelection selection = new StringSelection(array.toString());
						clipboard.setContents(selection, selection);

						logger.info(selection.toString());
					}
				}
			}
			break;
			case "paste":
			{
				Component owner = INSTANCE.getMostRecentFocusOwner();
				Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);

				if(owner instanceof ICopyAbleContainer&&contents.isDataFlavorSupported(DataFlavor.stringFlavor))
				{
					try
					{
						String data = (String)contents.getTransferData(DataFlavor.stringFlavor);
						JsonElement jsonElement = JsonParser.parseString(data);
						JsonArray array = null;
						if(jsonElement.isJsonArray())
							array = ((JsonArray)jsonElement);
						else if(jsonElement.isJsonObject())
						{
							array = new JsonArray();
							array.add(jsonElement);
						}

						if(array!=null)
							((ICopyAbleContainer)owner).handlePaste(array);

					}
					catch(Exception ignored)
					{

					}

				}
			}
			break;
			case "remove":
			{
				Component owner = INSTANCE.getMostRecentFocusOwner();
				if(owner instanceof ICopyAbleContainer)
					((ICopyAbleContainer)owner).handleRemove();
			}
			break;
		}
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

		Kraftwerk.INSTANCE.registeredTabs.forEach(PanelTab::refresh);
		Kraftwerk.INSTANCE.registeredTabs.forEach(PanelTab::validate);
		Kraftwerk.INSTANCE.registeredTabs.forEach(PanelTab::repaint);
	}
}
