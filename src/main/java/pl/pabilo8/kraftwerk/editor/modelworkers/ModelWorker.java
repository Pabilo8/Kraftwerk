package pl.pabilo8.kraftwerk.editor.modelworkers;

import jnafilechooser.api.JnaFileChooser;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.gui.Icons;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.io.File;

/**
 * @author Pabilo8
 * @since 15.08.2021
 */
public abstract class ModelWorker
{
	public String name;
	public String[] extensions;
	@Nullable
	public Icon icon = null;

	public ModelWorker(String name, String... extensions)
	{
		this.name = name;
		this.extensions = extensions;
		this.icon = Icons.loadSVGIcon("model_worker/"+name);
	}

	public void setIcon(@Nullable Icon icon)
	{
		this.icon = icon;
	}

	public void handleDialog()
	{
		JnaFileChooser chooser = new JnaFileChooser();
		String[] ext = new String[extensions.length+1];
		ext[0] = ResourceUtils.translateString(Kraftwerk.res, "model_worker."+name);
		System.arraycopy(extensions, 0, ext, 1, extensions.length);
		chooser.addFilter(ext);

		perform(chooser);
	}

	public abstract void perform(JnaFileChooser chooser);

	/**
	 * @author Pabilo8
	 * @since 14.08.2021
	 */
	public abstract static class ModelExporter extends ModelWorker
	{
		public ModelExporter(String name, String... extensions)
		{
			super(name, extensions);
		}

		@Override
		public void perform(JnaFileChooser chooser)
		{
			if(chooser.showSaveDialog(Kraftwerk.INSTANCE))
			{
				File file = chooser.getSelectedFile();
				if(file!=null)
					exportModel(file);
			}
		}

		abstract void exportModel(@Nonnull File file);
	}

	/**
	 * @author Pabilo8
	 * @since 14.08.2021
	 */
	public abstract static class ModelImporter extends ModelWorker
	{
		public ModelImporter(String name, String... extensions)
		{
			super(name, extensions);
		}

		@Override
		public void perform(JnaFileChooser chooser)
		{
			if(chooser.showOpenDialog(Kraftwerk.INSTANCE))
			{
				File file = chooser.getSelectedFile();
				if(file!=null)
					importModel(file);
			}
		}

		abstract void importModel(@Nonnull File file);
	}
}
