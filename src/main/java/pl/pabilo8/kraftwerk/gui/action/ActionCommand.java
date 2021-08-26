package pl.pabilo8.kraftwerk.gui.action;

import pl.pabilo8.kraftwerk.Kraftwerk;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class ActionCommand extends AbstractAction
{
	public final String command;

	public ActionCommand(String name)
	{
		super(name);
		this.command = name;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Kraftwerk.INSTANCE.commandPerformed(this);
	}
}
