package pl.pabilo8.kraftwerk.editor.elements;

import com.google.gson.JsonObject;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.ICopyAble;
import pl.pabilo8.kraftwerk.gui.panel.PanelTabProperties.PropertiesEditorPanel;
import pl.pabilo8.kraftwerk.render.TexturedQuad;
import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Pabilo8
 * @since 15.08.2021
 */
public abstract class ModelElement extends DefaultMutableTreeNode implements ICopyAble
{

	public String name = "";
	public Vec3d pos = Vec3d.ZERO, rot = Vec3d.ZERO;
	public TexturedQuad[] faces = new TexturedQuad[0];
	public ModelTexture texture = null;
	public ModelUVConfig uv = new ModelUVConfig(true);

	public ModelElement()
	{
		super();
	}

	@Nonnull
	public void fromJSON(JsonObject json)
	{
		this.name = json.get("name").getAsString();
		if(json.has("pos")&&json.get("pos").isJsonArray())
			this.pos = new Vec3d(json.get("pos").getAsJsonArray());
		if(json.has("rot")&&json.get("rot").isJsonArray())
			this.rot = new Vec3d(json.get("rot").getAsJsonArray());
		if(json.has("uv"))
			this.uv = new ModelUVConfig(json.get("uv").getAsJsonObject());
	}

	@Nonnull
	public JsonObject toJSON()
	{
		JsonObject json = new JsonObject();
		json.addProperty("type", getTypeName());
		json.addProperty("name", name);
		json.add("pos", pos.toJSON());
		json.add("rot", rot.toJSON());
		json.add("uv", uv.toJSON());
		return json;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public abstract boolean isCuboid();

	public abstract String getTypeName();

	@Nonnull
	public abstract String getNameForFace(int id);

	@Nullable
	public abstract PropertiesEditorPanel getEditorPanel();

	@Override
	public JsonObject handleCopy()
	{
		return toJSON();
	}

	@Override
	public void handleRemove()
	{
		removeFromParent();
	}

	@Override
	public void handlePaste(JsonObject element)
	{
		forceRefresh();
	}

	public abstract Icon getIcon();

	public abstract void forceRefresh();
}
