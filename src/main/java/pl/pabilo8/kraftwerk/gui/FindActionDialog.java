package pl.pabilo8.kraftwerk.gui;

import com.github.weisj.darklaf.components.border.DarkBorders;
import com.github.weisj.darklaf.components.text.SearchEvent;
import com.github.weisj.darklaf.components.text.SearchListener;
import com.github.weisj.darklaf.components.text.SearchTextFieldWithHistory;
import javafx.scene.input.KeyCode;
import net.miginfocom.swing.MigLayout;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.gui.action.ActionCommand;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Optional;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class FindActionDialog extends JDialog
{
	public FindActionDialog()
	{
		JPanel frameMainPanel = new JPanel();
		frameMainPanel.setBorder(DarkBorders.createWidgetLineBorder(1, 1, 1, 1));
		this.setContentPane(frameMainPanel);

		this.setSize((int)(Kraftwerk.INSTANCE.panelModelEditor.getWidth()*0.85f), (int)(Kraftwerk.INSTANCE.panelModelEditor.getHeight()*0.5f));
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
		this.setPreferredSize(getSize());

		this.setLocationRelativeTo(Kraftwerk.INSTANCE.panelModelEditor);

		this.setResizable(false);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setAlwaysOnTop(true);
		this.setVisible(true);

		this.addWindowFocusListener(new WindowFocusListener()
		{
			@Override
			public void windowLostFocus(WindowEvent e)
			{
				dispose();
			}

			@Override
			public void windowGainedFocus(WindowEvent e)
			{

			}
		});

		SearchTextFieldWithHistory searchField = new SearchTextFieldWithHistory();
		searchField.addSearchListener(searchEvent -> {
			String s = searchEvent.getText();
			if(s!=null&&!s.isEmpty())
			{
				Optional<ActionCommand> first = Kraftwerk.actionCommands.stream()
						.filter(actionCommand -> actionCommand.command.equals(s))
						.findFirst();

				FindActionDialog.this.executeActionCommand(first.orElse(null));
			}
		});

		this.setLayout(new MigLayout("fillx, aligny top"));
		this.add(searchField, "growx, center, wrap");

		DefaultListModel<ActionCommand> commands = new DefaultListModel<>();
		Kraftwerk.actionCommands.forEach(commands::addElement);

		JList<ActionCommand> commandJList = new JList<>(commands);
		commandJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		commandJList.setCellRenderer(new CommandCellRenderer());
		commandJList.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				FindActionDialog.this.executeActionCommand(commandJList.getSelectedValue());
			}
		});
		commandJList.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				super.keyPressed(e);
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					FindActionDialog.this.executeActionCommand(commandJList.getSelectedValue());
			}
		});

		this.add(commandJList, "growx, growy, center, wrap");
		this.pack();
	}

	private void executeActionCommand(@Nullable ActionCommand command)
	{
		if(command==null)
			return;

		this.setAlwaysOnTop(false);
		this.setVisible(false);
		this.dispose();

		SwingUtilities.invokeLater(() -> Kraftwerk.INSTANCE.commandPerformed(command));
	}

	private static class CommandCellRenderer extends DefaultListCellRenderer
	{
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			setText(((ActionCommand)value).command);
			setInheritsPopupMenu(true);
			return this;
		}
	}
}
