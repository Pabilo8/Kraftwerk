package pl.pabilo8.kraftwerk.editor.modelworkers;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class DefaultModelImporters
{
	public static class VanillaJavaImporter extends ModelImporter
	{
		public VanillaJavaImporter()
		{
			super("vanilla_java", ".java");
		}
	}

	public static class VanillaJSONImporter extends ModelImporter
	{
		public VanillaJSONImporter()
		{
			super("vanilla_json", ".json");
		}
	}

	public static class ForgeMultipartImporter extends ModelImporter
	{
		public ForgeMultipartImporter()
		{
			super("forge_multipart", ".json");
		}
	}

	public static class JavaTMTImporter extends ModelImporter
	{
		public JavaTMTImporter()
		{
			super("java_tmt", ".java", ".tmt");
		}
	}

	public static class SMPToolboxImporter extends ModelImporter
	{
		public SMPToolboxImporter()
		{
			super("smp_toolbox", ".mtb");
		}
	}

	public static class BlockBenchImporter extends ModelImporter
	{
		public BlockBenchImporter()
		{
			super("blockbench", ".bbmodel", ".json");
		}
	}

	public static class TechneImporter extends ModelImporter
	{
		public TechneImporter()
		{
			super("techne", ".tcn");
		}
	}

	public static class TabulaImporter extends ModelImporter
	{
		public TabulaImporter()
		{
			super("tabula", ".tbl");
		}
	}

	public static class ModelLoaderMCXImporter extends ModelImporter
	{
		public ModelLoaderMCXImporter()
		{
			super("modelloader_mcx", ".mcx");
		}
	}
}
