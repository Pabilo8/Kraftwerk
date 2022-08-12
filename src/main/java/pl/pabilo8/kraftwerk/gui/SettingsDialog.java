package pl.pabilo8.kraftwerk.gui;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.components.DefaultButton;
import com.github.weisj.darklaf.settings.ThemeSettings;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.gui.action.ActionCommand;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class SettingsDialog extends JDialog
{
	static ActionCommand refreshCommand = ActionCommand.getActionCommand("refresh");

	public SettingsDialog()
	{
		this.setTitle(ResourceUtils.translateString(Kraftwerk.res, "menubar.file.settings"));

		JTabbedPane mainPane = new JTabbedPane();
		mainPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mainPane.setTabPlacement(JTabbedPane.LEFT);

		this.setLocationRelativeTo(Kraftwerk.INSTANCE);
		this.setContentPane(mainPane);
		this.setResizable(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setIconImage(Kraftwerk.programIcon);
		this.setSize(800, 600);

		int i = 0;
		JPanel appearancePanel = new JPanel(new BorderLayout());
		ThemeSettings settings = ThemeSettings.getInstance();
		JComponent settingsPanel = settings.getSettingsPanel();
		appearancePanel.add(settingsPanel, BorderLayout.CENTER);
		Box box = Box.createHorizontalBox();
		box.setBorder(settingsPanel.getBorder());
		box.add(Box.createHorizontalGlue());
		box.add(new DefaultButton("Apply")
		{
			{
				addActionListener(e -> {
					settings.apply();
					SwingUtilities.invokeLater(() -> Kraftwerk.INSTANCE.commandPerformed(refreshCommand));
				});
			}
		});
		box.add(new JButton("Revert")
		{
			{
				addActionListener(e -> {
					settings.revert();
					SwingUtilities.invokeLater(() -> Kraftwerk.INSTANCE.commandPerformed(refreshCommand));
				});
			}
		});
		appearancePanel.add(box, BorderLayout.SOUTH);

		mainPane.insertTab("Appearance", null, appearancePanel, "", i++);
		mainPane.insertTab("Keymap", null, new JPanel(), "", i);
		mainPane.insertTab("Editor", null, new JPanel(), "", i);
		mainPane.insertTab("Rendering", null, new JPanel(), "", i);
		mainPane.insertTab("Plugins", null, new JPanel(), "", i);

		Color accentColor = LafManager.getTheme().getAccentColorRule().getAccentColor();
		for(int j = 0; j < mainPane.getTabCount(); j++)
			mainPane.setBackgroundAt(j,accentColor);

		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
}
