package pl.pabilo8.kraftwerk.editor.modelworkers;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class DefaultModelExporters
{
	public static class VanillaJavaExporter extends ModelWorker.ModelExporter
	{
		public VanillaJavaExporter()
		{
			super("vanilla_java", "java");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class VanillaJSONExporter extends ModelWorker.ModelExporter
	{
		public VanillaJSONExporter()
		{
			super("vanilla_json", "json");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class ForgeMultipartExporter extends ModelWorker.ModelExporter
	{
		public ForgeMultipartExporter()
		{
			super("forge_multipart", "json");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class JavaTMTExporter extends ModelWorker.ModelExporter
	{
		public JavaTMTExporter()
		{
			super("tmt", "java", "tmt");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class JsonTMTExporter extends ModelWorker.ModelExporter
	{
		public JsonTMTExporter()
		{
			super("jtmt", "json", "jtmt");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class SMPToolboxExporter extends ModelWorker.ModelExporter
	{
		public SMPToolboxExporter()
		{
			super("smp_toolbox", "mtb");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class BlockBenchExporter extends ModelWorker.ModelExporter
	{
		public BlockBenchExporter()
		{
			super("blockbench", "bbmodel", "json");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class TechneExporter extends ModelWorker.ModelExporter
	{
		public TechneExporter()
		{
			super("techne", "tcn");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class TabulaExporter extends ModelWorker.ModelExporter
	{
		public TabulaExporter()
		{
			super("tabula", "tbl");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class ModelLoaderMCXExporter extends ModelWorker.ModelExporter
	{
		public ModelLoaderMCXExporter()
		{
			super("modelloader_mcx", "mcx");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}

	public static class OBJExporter extends ModelWorker.ModelExporter
	{
		public OBJExporter()
		{
			super("obj", "obj");
		}

		@Override
		void exportModel(@Nonnull File file)
		{

		}
	}
}
