package pl.pabilo8.kraftwerk.editor.modelworkers;

import javax.annotation.Nullable;
import javax.swing.*;

/**
 * @author Pabilo8
 * @since 15.08.2021
 */
public abstract class ModelWorker<T extends Action>
{
	public String name;
	public String[] extensions;
	@Nullable
	public Icon icon = null;
	public T action;

	public ModelWorker(String name, String... extensions)
	{
		this.name = name;
		this.extensions = extensions;
	}

	public void setIcon(@Nullable Icon icon)
	{
		this.icon = icon;
	}
}
