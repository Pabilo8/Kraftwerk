package pl.pabilo8.kraftwerk.editor.elements;

import com.google.gson.JsonObject;
import net.miginfocom.swing.MigLayout;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.render.OpenGLTexture;
import pl.pabilo8.kraftwerk.utils.GuiUtils;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author Pabilo8
 * @since 15.08.2021
 */
public class ModelTexture
{
	public URL texturePath;
	public OpenGLTexture loadedTexture;
	public String resourceLocation; //For vanilla ResLoc
	public int width = 16, height = 16; //This allows for using textures with higher resolution without messing with model scale, like in case of II/IE items (railgun, machinegun)

	public ModelTexture(URL texturePath)
	{
		this.texturePath = texturePath;
		try
		{
			this.loadedTexture = new OpenGLTexture(texturePath.openStream(), true);
		}
		catch(IOException e)
		{
			Kraftwerk.logger.info("Invalid texture file, using fallback missingno.png "+e);
			this.loadedTexture = new OpenGLTexture(OpenGLTexture.default_texture);
		}

		resourceLocation = ResourceUtils.attemptGetResourceLocation(texturePath);
	}

	public ModelTexture(JsonObject json)
	{

	}

	public static class TextureListCellRenderer extends JPanel implements ListCellRenderer<ModelTexture>
	{
		private final JLabel icon, res, size, path;

		public TextureListCellRenderer()
		{
			setOpaque(true);
			setLayout(new MigLayout("fillx, aligny top, toptobottom"));
			add(this.icon = new JLabel(), "cell 0 0 1 3, width 64:64:64");
			add(this.res = new JLabel(), "cell 1 0");
			add(this.size = new JLabel(), "cell 1 1");
			add(this.path = new JLabel(), "cell 1 2");
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends ModelTexture> list, ModelTexture value, int index, boolean isSelected, boolean cellHasFocus)
		{
			if(icon.getIcon()==null&&value.loadedTexture.isDownloaded())
			{
				icon.setIcon(new ImageIcon(value.loadedTexture.getBufferedImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
				final String s = String.format(
						(value.width==value.loadedTexture.getWidth()&&value.height==value.loadedTexture.getHeight())?
								"Size: %d x %d": "Size: %d x %d (%d x %d)"
						, value.width, value.height, value.loadedTexture.getWidth(), value.loadedTexture.getHeight());
				res.setText(value.resourceLocation);
				size.setText(s);
				path.setText(String.format("<html><b><i>%s</b></i></html>", value.texturePath.getFile()));
			}

			return this;
		}
	}
}
