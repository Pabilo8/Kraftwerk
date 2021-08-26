package pl.pabilo8.kraftwerk.editor.modelworkers;

import pl.pabilo8.kraftwerk.gui.action.ActionModelExport;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public abstract class ModelExporter extends ModelWorker<ActionModelExport>
{
	public ModelExporter(String name, String... extensions)
	{
		super(name, extensions);
	}
}
