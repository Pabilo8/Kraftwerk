package pl.pabilo8.kraftwerk.gui.action;

import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class ActionCommand extends AbstractAction
{
	public static final ArrayList<ActionCommand> COMMANDS = new ArrayList<>();

	public final String command;
	public final String[] arguments;

	private ActionCommand(String name, boolean hiddenFromSearch, String... arguments)
	{
		super(name);
		this.command = name;
		this.arguments = arguments;
		if(!hiddenFromSearch)
			Kraftwerk.actionCommands.add(this);
	}

	public static ActionCommand getActionCommand(String name, boolean hiddenFromSearch, String... arguments)
	{
		Optional<ActionCommand> first = COMMANDS.stream()
				.filter(actionCommand -> actionCommand.command.equals(name))
				.filter(actionCommand -> Arrays.equals(actionCommand.arguments, arguments))
				.findFirst();
		if(first.isPresent())
			return first.get();

		ActionCommand command = new ActionCommand(name, hiddenFromSearch, arguments);
		if(!hiddenFromSearch)
			COMMANDS.add(command);
		return command;
	}

	public static ActionCommand getActionCommand(String name, String... arguments)
	{
		return getActionCommand(name, false, arguments);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Kraftwerk.INSTANCE.commandPerformed(this);
	}

	public String getTranslatedName()
	{
		return ResourceUtils.translateString(Kraftwerk.res, "action."+command);
	}
}
