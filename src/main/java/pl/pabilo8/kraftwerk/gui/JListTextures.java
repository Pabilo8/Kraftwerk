package pl.pabilo8.kraftwerk.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.ICopyAbleContainer;
import pl.pabilo8.kraftwerk.editor.elements.ModelTexture;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.net.MalformedURLException;

/**
 * @author Pabilo8
 * @since 11.12.2021
 */
public class JListTextures extends JListDeselectable<ModelTexture> implements ICopyAbleContainer
{
	public JListTextures(ListModel<ModelTexture> dataModel)
	{
		super(dataModel);
	}

	@Nonnull
	@Override
	public JsonArray handleCopy()
	{
		JsonArray array = new JsonArray();

		for(ModelTexture modelTexture : getSelectedValuesList())
			array.add(modelTexture.toJSON());

		Kraftwerk.INSTANCE.panelTextures.refresh();
		return array;
	}

	@Override
	public void handleRemove()
	{
		for(ModelTexture modelTexture : getSelectedValuesList())
		{
			Kraftwerk.INSTANCE.currentProject.textures.remove(modelTexture);
			modelTexture.onRemove();
		}
		Kraftwerk.INSTANCE.panelTextures.refresh();
	}

	@Override
	public void handlePaste(JsonArray elements)
	{
		for(JsonElement tex : elements)
			if(tex.isJsonObject())
			{
				try
				{
					Kraftwerk.INSTANCE.currentProject.textures.add(new ModelTexture(tex.getAsJsonObject()));
				}
				catch(MalformedURLException e)
				{
					Kraftwerk.logger.warning("Incorrect texture data, have you tinkered with it?");
				}
			}
		Kraftwerk.INSTANCE.panelTextures.refresh();
	}
}
