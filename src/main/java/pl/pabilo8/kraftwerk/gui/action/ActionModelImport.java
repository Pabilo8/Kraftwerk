package pl.pabilo8.kraftwerk.gui.action;

import pl.pabilo8.kraftwerk.editor.modelworkers.ModelImporter;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class ActionModelImport extends AbstractAction
{
	public ActionModelImport(ModelImporter importer)
	{
		super("import_model_"+importer.name);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO: 14.08.2021 model exporting
	}
}
