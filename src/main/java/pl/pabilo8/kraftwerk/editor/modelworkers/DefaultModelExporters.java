package pl.pabilo8.kraftwerk.editor.modelworkers;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class DefaultModelExporters
{
	public static class VanillaJavaExporter extends ModelExporter
	{
		public VanillaJavaExporter()
		{
			super("vanilla_java", ".java");
		}
	}

	public static class VanillaJSONExporter extends ModelExporter
	{
		public VanillaJSONExporter()
		{
			super("vanilla_json", ".json");
		}
	}

	public static class ForgeMultipartExporter extends ModelExporter
	{
		public ForgeMultipartExporter()
		{
			super("forge_multipart", ".json");
		}
	}

	public static class JavaTMTExporter extends ModelExporter
	{
		public JavaTMTExporter()
		{
			super("java_tmt", ".java", ".tmt");
		}
	}

	public static class SMPToolboxExporter extends ModelExporter
	{
		public SMPToolboxExporter()
		{
			super("smp_toolbox", ".mtb");
		}
	}

	public static class BlockBenchExporter extends ModelExporter
	{
		public BlockBenchExporter()
		{
			super("blockbench", ".bbmodel", ".json");
		}
	}

	public static class TechneExporter extends ModelExporter
	{
		public TechneExporter()
		{
			super("techne", ".tcn");
		}
	}

	public static class TabulaExporter extends ModelExporter
	{
		public TabulaExporter()
		{
			super("tabula", ".tbl");
		}
	}

	public static class ModelLoaderMCXExporter extends ModelExporter
	{
		public ModelLoaderMCXExporter()
		{
			super("modelloader_mcx", ".mcx");
		}
	}
}
