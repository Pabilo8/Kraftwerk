package pl.pabilo8.kraftwerk.utils;

import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.gui.action.ActionCommand;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Pabilo8
 * @since 31.08.2021
 */
public class CommandLineUtils
{
	public static Scanner scanner = new Scanner(System.in);

	public static class CommandThread implements Runnable
	{
		@Override
		public void run()
		{
			while(!Thread.currentThread().isInterrupted())
			{
				if(scanner.hasNext())
				{
					String[] s = scanner.next().trim().split(" ");
					if(s.length > 0)
						Kraftwerk.INSTANCE.commandPerformed(ActionCommand.getActionCommand(s[0], true, s.length > 1?Arrays.copyOfRange(s, 1, s.length-1): new String[0]));
				}
			}
		}
	}
}
