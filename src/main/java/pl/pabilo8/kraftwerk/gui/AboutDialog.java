package pl.pabilo8.kraftwerk.gui;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.components.uiresource.JLabelUIResource;
import com.github.weisj.darklaf.theme.Theme;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class AboutDialog extends JDialog
{
	public AboutDialog()
	{
		this.setTitle(ResourceUtils.translateString(Kraftwerk.res, "menubar.help.about"));

		JPanel frameMainPanel = new JPanel();
		this.setContentPane(frameMainPanel);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setIconImage(Kraftwerk.programIcon);
		this.setSize(400, 400);

		JLabelUIResource labelLogo = new JLabelUIResource(new ImageIcon(Objects.requireNonNull(ResourceUtils.texResource(getLogoImage())).getScaledInstance(400, 96, Image.SCALE_SMOOTH)));
		labelLogo.setSize(400, 96);

		JEditorPane content = new JEditorPane();
		content.setContentType("text/html");
		content.setEditable(false);
		content.setHighlighter(null);
		content.setText(getInfoText());
		content.addHyperlinkListener(event -> {
			if(event.getEventType()==HyperlinkEvent.EventType.ACTIVATED)
			{
				try
				{
					Desktop.getDesktop().browse(event.getURL().toURI());
				}
				catch(Exception e1)
				{
					Kraftwerk.logger.info("Error opening link "+event.getURL());
				}
			}
		});

		GroupLayout layout = new GroupLayout(frameMainPanel);
		frameMainPanel.setLayout(layout);

		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addComponent(labelLogo)
						.addComponent(content)
		);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
						.addComponent(labelLogo)
						.addComponent(content)
		);

		this.setAlwaysOnTop(true);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);

	}

	private String getInfoText()
	{
		StringBuilder aboutHTML = new StringBuilder();
		aboutHTML.append("<html>");
		aboutHTML.append("<h3>").append(ResourceUtils.translateString(Kraftwerk.res, "window.about.motto")).append("<br>");
		aboutHTML.append(String.format(ResourceUtils.translateString(Kraftwerk.res, "window.about.version"), "0.1.0")).append("</h3>");
		aboutHTML.append("<hr>");
		aboutHTML.append("<h4>").append(ResourceUtils.translateString(Kraftwerk.res, "window.about.license")).append("<br>");
		aboutHTML.append(ResourceUtils.translateString(Kraftwerk.res, "window.about.libraries"));
		aboutHTML.append(" <a href=\"https://jogamp.org/\">JogAmp</a>");
		aboutHTML.append(", <a href=\"https://github.com/weisJ/darklaf\">DarkLaf</a>");
		aboutHTML.append(", <a href=\"https://www.miglayout.com/\">MigLayout</a>.<br>");

		String github = ResourceUtils.translateString(Kraftwerk.res, "window.about.github")
				.replaceAll("\\[", "<a href=\"https://github.com/Pabilo8/kraftwerk\">")
				.replaceAll("]", "</a>");

		aboutHTML.append(github);
		aboutHTML.append("<br><hr><br>");

		String credits = ResourceUtils.translateString(Kraftwerk.res, "window.about.credits")
				.replaceAll("\\[Pabilo8]", "<a href=\"https://github.com/Pabilo8\">@Pabilo8</a>");

		aboutHTML.append(credits).append("<br>");
		aboutHTML.append("</h4></html>");
		return aboutHTML.toString();
	}

	private String getLogoImage()
	{
		return Theme.isDark(LafManager.getInstalledTheme())?"kraftwerk_logo_white.png": "kraftwerk_logo.png";
	}
}
