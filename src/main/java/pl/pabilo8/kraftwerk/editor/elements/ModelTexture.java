package pl.pabilo8.kraftwerk.editor.elements;

import com.google.gson.JsonObject;
import net.miginfocom.swing.MigLayout;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.render.OpenGLTexture;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

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

	private ImageIcon thumbnailIcon; //For Panel Thumbnail
	private String thumbnailSizeText, thumbnailPath;


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
			this.loadedTexture = new OpenGLTexture(OpenGLTexture.default_texture, true, Kraftwerk.INSTANCE.panelTextures);
		}

		resourceLocation = ResourceUtils.attemptGetResourceLocation(texturePath);
	}

	public ModelTexture(JsonObject json)
	{

	}

	void compilePreview()
	{
		thumbnailIcon = new ImageIcon(loadedTexture.getBufferedImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		thumbnailSizeText = String.format(
				(width==loadedTexture.getWidth()&&height==loadedTexture.getHeight())?
						"Size: %d x %d": "Size: %d x %d (%d x %d)"
				, width, height, loadedTexture.getWidth(), loadedTexture.getHeight());
		thumbnailPath = String.format("<html><b><i>%s</b></i></html>", texturePath.getFile());
	}

	public static class TextureListCellRenderer extends JPanel implements ListCellRenderer<ModelTexture>
	{
		private final JLabel icon, res, size, path;
		private final JToolTip toolTip;
		final MigLayout layout = new MigLayout("fillx, aligny top, toptobottom");

		public TextureListCellRenderer()
		{
			setOpaque(true);
			setLayout(layout);
			add(this.icon = new JLabel(), "cell 0 0 1 3, width 64:64:64");
			add(this.res = new JLabel(), "cell 1 0");
			add(this.size = new JLabel(), "cell 1 1");
			add(this.path = new JLabel(), "cell 1 2");
			toolTip = createToolTip();
		}

		//

		@Override
		public Component getListCellRendererComponent(JList<? extends ModelTexture> list, ModelTexture value, int index, boolean isSelected, boolean cellHasFocus)
		{
			if(value.thumbnailIcon==null)
			{
				if(value.loadedTexture.isDownloaded())
					value.compilePreview();
			}
			else
			{
				icon.setIcon(value.thumbnailIcon);
				res.setText(value.resourceLocation);
				size.setText(value.thumbnailSizeText);
				path.setText(value.thumbnailPath);
				toolTip.setTipText(value.thumbnailPath);
			}

			return this;
		}
	}
}
