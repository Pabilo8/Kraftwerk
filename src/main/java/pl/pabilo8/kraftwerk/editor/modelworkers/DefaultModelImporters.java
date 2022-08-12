package pl.pabilo8.kraftwerk.editor.modelworkers;

import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.elements.*;
import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

import javax.annotation.Nonnull;
import javax.swing.tree.DefaultTreeModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Pabilo8
 * @since 14.08.2021
 */
public class DefaultModelImporters
{
	public static class VanillaJavaImporter extends ModelWorker.ModelImporter
	{
		public VanillaJavaImporter()
		{
			super("vanilla_java", "java");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}

	public static class VanillaJSONImporter extends ModelWorker.ModelImporter
	{
		public VanillaJSONImporter()
		{
			super("vanilla_json", "json");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}

	public static class ForgeMultipartImporter extends ModelWorker.ModelImporter
	{
		public ForgeMultipartImporter()
		{
			super("forge_multipart", "json");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}

	public static class JavaTMTImporter extends ModelWorker.ModelImporter
	{
		public JavaTMTImporter()
		{
			super("tmt", "java", "tmt");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}

	public static class JsonTMTImporter extends ModelWorker.ModelImporter
	{
		public JsonTMTImporter()
		{
			super("jtmt", "json", "jtmt");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}

	public static class SMPToolboxImporter extends ModelWorker.ModelImporter
	{
		public SMPToolboxImporter()
		{
			super("smp_toolbox", "mtb");
		}

		@Override
		void importModel(@Nonnull File file)
		{
			try(ZipFile zipFile = new ZipFile(file))
			{
				ZipEntry texEntry = zipFile.getEntry("Model.png");
				ZipEntry modelEntry = zipFile.getEntry("Model.txt");

				if(texEntry!=null&&modelEntry!=null)
				{
					int xSize = 16, ySize = 16;
					String texPath = null;
					ArrayList<String[]> elements = new ArrayList<>();

					// TODO: 18.12.2021 element importing
					InputStream modelStream = zipFile.getInputStream(modelEntry);
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(modelStream));

					String s = bufferedReader.readLine();
					while(s!=null)
					{
						//Kraftwerk.logger.info(s);
						String[] split = s.split("\\|");
						switch(split[0])
						{
							case "TexName":
								if(split.length > 1)
									texPath = split[1];
								break;
							case "TexSizeX":
								if(split.length > 1)
									xSize = Integer.parseInt(split[1]);
								break;
							case "TexSizeY":
								if(split.length > 1)
									ySize = Integer.parseInt(split[1]);
								break;
							case "Element":
								elements.add(split);
								break;
							case "ModelPassword":
								if(split.length > 1)
									Kraftwerk.logger.info("Found ModelPassword )))");
								break;
						}

						s = bufferedReader.readLine();
					}


					// TODO: 18.12.2021 texture from config, size
					ModelTexture texture = null;
					if(texPath!=null)
					{
						File diskTexture = new File(texPath);
						if(diskTexture.exists())
						{
							for(ModelTexture tt : Kraftwerk.INSTANCE.currentProject.textures)
							{
								if(Objects.equals(tt.texturePath.getPath(), diskTexture.getPath()))
								{
									texture = tt;
									break;
								}
							}
							if(texture==null)
								texture = Kraftwerk.INSTANCE.currentProject.addTexture(diskTexture);
						}
						else
						{
							InputStream texStream = zipFile.getInputStream(texEntry);
							File targetFile = new File(file.getCanonicalPath().replace(".mtb", ".png"));
							if(targetFile.exists())
							{
								OutputStream texOutStream = new FileOutputStream(targetFile);
								int len;
								byte[] buffer = new byte[1024];
								while((len = texStream.read(buffer)) > 0)
									texOutStream.write(buffer, 0, len);
								texOutStream.close();
								texture = Kraftwerk.INSTANCE.currentProject.addTexture(targetFile);
							}
						}
					}

					if(texture!=null)
					{
						texture.width = xSize;
						texture.height = ySize;
					}

					for(String[] element : elements)
					{
						for(int i = 0; i < element.length; i++)
							element[i] = element[i].replace(',', '.'); //floats

						String name = element[3], type = element[5].toLowerCase();
						Vec3d pos = new Vec3d(Float.parseFloat(element[6]), Float.parseFloat(element[7]), Float.parseFloat(element[8]));
						Vec3d rot = new Vec3d(Float.parseFloat(element[12]), Float.parseFloat(element[13]), Float.parseFloat(element[14]));

						//20-43 - corners x-8,y-8,z-8
						Supplier<ModelElement> supplier = Kraftwerk.INSTANCE.modelElementSuppliers.get(type);
						if(supplier!=null)
						{
							ModelElement mod = supplier.get();
							mod.name = name;
							mod.pos = pos;
							mod.rot = rot;
							mod.texture = texture;

							if(mod instanceof ModelElementBox)
							{
								((ModelElementBox)mod).size = new Vec3d(Integer.parseInt(element[9]), Integer.parseInt(element[10]), Integer.parseInt(element[11]));
								((ModelElementBox)mod).offset = new Vec3d(Float.parseFloat(element[15]), Float.parseFloat(element[16]), Float.parseFloat(element[17]));

								mod.uv = new ModelUVConfig(true);
								mod.uv.setSimpleBoxUV(Integer.parseInt(element[18]), Integer.parseInt(element[19]));

							}
							if(mod instanceof ModelElementShapebox)
							{
								for(int i = 0; i < 8; i++)
								{
									((ModelElementShapebox)mod).corners[i] = new Vec3d(
											Float.parseFloat(element[20+(i*3)]),
											Float.parseFloat(element[21+(i*3)]),
											Float.parseFloat(element[22+(i*3)])
									);
								}
							}

							mod.forceRefresh();
							Kraftwerk.INSTANCE.currentProject.addElement(mod);
						}

						((DefaultTreeModel)Kraftwerk.INSTANCE.currentProject.elementTreeModel).reload();
					}

				}
			}
			catch(IOException e)
			{
				Kraftwerk.logger.info("Error reading model file. "+e.getMessage());
			}
		}
	}

	public static class BlockBenchImporter extends ModelWorker.ModelImporter
	{
		public BlockBenchImporter()
		{
			super("blockbench", ".bbmodel", "json");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}

	public static class TechneImporter extends ModelWorker.ModelImporter
	{
		public TechneImporter()
		{
			super("techne", "tcn");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}

	public static class TabulaImporter extends ModelWorker.ModelImporter
	{
		public TabulaImporter()
		{
			super("tabula", "tbl");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}

	public static class ModelLoaderMCXImporter extends ModelWorker.ModelImporter
	{
		public ModelLoaderMCXImporter()
		{
			super("modelloader_mcx", "mcx");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}

	public static class OBJImporter extends ModelWorker.ModelImporter
	{
		public OBJImporter()
		{
			super("obj", "obj");
		}

		@Override
		void importModel(@Nonnull File file)
		{

		}
	}
}
