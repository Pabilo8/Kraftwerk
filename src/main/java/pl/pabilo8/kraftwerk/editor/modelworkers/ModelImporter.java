package pl.pabilo8.kraftwerk.editor.modelworkers;

import pl.pabilo8.kraftwerk.gui.action.ActionModelImport;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public abstract class ModelImporter extends ModelWorker<ActionModelImport>
{
	public ModelImporter(String name, String... extensions)
	{
		super(name, extensions);
	}
}
