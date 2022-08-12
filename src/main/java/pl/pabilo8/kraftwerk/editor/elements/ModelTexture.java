package pl.pabilo8.kraftwerk.editor.elements;

import com.google.gson.JsonObject;
import net.miginfocom.swing.MigLayout;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.ICopyAble;
import pl.pabilo8.kraftwerk.render.OpenGLTexture;
import pl.pabilo8.kraftwerk.utils.FileWatcher;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;

/**
 * @author Pabilo8
 * @since 15.08.2021
 */
public class ModelTexture implements ICopyAble
{
	public URL texturePath;
	public OpenGLTexture loadedTexture;
	public String resourceLocation; //For vanilla ResLoc
	public int width = 16, height = 16; //This allows for using textures with higher resolution without messing with model scale, like in case of II/IE items (railgun, machinegun)
	private ImageIcon thumbnailIcon; //For Panel Thumbnail
	private String thumbnailSizeText, thumbnailPath;
	private FileWatcher watcher;


	public ModelTexture(URL texturePath)
	{
		this.texturePath = texturePath;
		updateTexture();
		try
		{
			watcher = FileWatcher.onFileChange(Paths.get(new File(texturePath.toURI()).getPath()), this::updateTexture);
		}
		catch(IOException|URISyntaxException ignored)
		{

		}
		resourceLocation = ResourceUtils.attemptGetResourceLocation(texturePath);
	}

	public ModelTexture(JsonObject json) throws MalformedURLException
	{
		this(new URL(json.get("path").getAsString()));
		this.resourceLocation = json.get("resource_location").getAsString();
		this.width = json.get("width").getAsInt();
		this.height = json.get("height").getAsInt();
	}

	void updateTexture()
	{
		try
		{
			if(this.loadedTexture!=null)
				this.loadedTexture.updateTexture(texturePath.openStream(), true);
			else
				this.loadedTexture = new OpenGLTexture(texturePath.openStream(), true);
		}
		catch(IOException e)
		{
			if(this.loadedTexture!=null)
				this.loadedTexture.delete();

			Kraftwerk.logger.info("Invalid texture file, using fallback missingno.png "+e);
			this.loadedTexture = new OpenGLTexture(OpenGLTexture.default_texture, true, Kraftwerk.INSTANCE.panelTextures);
		}

		thumbnailIcon = null;
	}

	public JsonObject toJSON()
	{
		JsonObject object = new JsonObject();
		object.addProperty("resource_location", resourceLocation);
		object.addProperty("path", texturePath.toString());
		object.addProperty("width", width);
		object.addProperty("height", height);
		return object;
	}

	void compilePreview()
	{
		thumbnailIcon = new ImageIcon(loadedTexture.getBufferedImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		thumbnailSizeText = String.format(
				(width==loadedTexture.getWidth()&&height==loadedTexture.getHeight())?
						ResourceUtils.translateString(Kraftwerk.res, "tabs.textures.label.size"):
						ResourceUtils.translateString(Kraftwerk.res, "tabs.textures.label.size_real")
				, width, height, loadedTexture.getWidth(), loadedTexture.getHeight());
		thumbnailPath = String.format("<html><b><i>%s</b></i></html>", texturePath.getFile());
	}

	public void onRemove()
	{
		loadedTexture.delete();
		watcher.stop();
		removeTextureFrom(Kraftwerk.INSTANCE.currentProject.rootElement);
		((DefaultTreeModel)(Kraftwerk.INSTANCE.currentProject.elementTreeModel)).reload();
	}

	public void removeTextureFrom(DefaultMutableTreeNode element)
	{
		Enumeration modelElements = element.children();
		while(modelElements.hasMoreElements())
		{
			Object o = modelElements.nextElement();
			if(o instanceof DefaultMutableTreeNode)
			{
				ModelElement model = null;
				if(((DefaultMutableTreeNode)o).getUserObject() instanceof ModelElement)
					model = (ModelElement)((DefaultMutableTreeNode)o).getUserObject();
				else if(o instanceof ModelElement)
					model = (ModelElement)o;
				if(model==null)
					continue;

				if(model.texture==this)
					model.texture = null;

				if(((DefaultMutableTreeNode)o).getChildCount() > 0)
					removeTextureFrom((DefaultMutableTreeNode)o);
			}
		}
	}

	@Override
	public JsonObject handleCopy()
	{
		return toJSON();
	}

	@Override
	public void handleRemove()
	{
		onRemove();
	}

	@Override
	public void handlePaste(JsonObject element)
	{

	}

	public static class TextureListCellRenderer extends JPanel implements ListCellRenderer<ModelTexture>
	{
		private final JLabel icon, res, size, path;
		final MigLayout layout = new MigLayout("fillx, aligny top, toptobottom");

		public TextureListCellRenderer()
		{
			setOpaque(true);
			setLayout(layout);
			add(this.icon = new JLabel(), "cell 0 0 1 3, width 64:64:64");
			add(this.res = new JLabel(), "cell 1 0");
			add(this.size = new JLabel(), "cell 1 1");
			add(this.path = new JLabel(), "cell 1 2");
		}

		//

		@Override
		public Component getListCellRendererComponent(JList<? extends ModelTexture> list, ModelTexture value, int index, boolean isSelected, boolean cellHasFocus)
		{
			if(value==null)
			{

				icon.setIcon(null);
				res.setText("");
				size.setText("");
				path.setText("minecraft:blocks/missingno");
				setToolTipText("minecraft:blocks/missingno");

				return this;
			}

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
				setToolTipText(value.thumbnailPath);
			}

			return this;
		}
	}
}
