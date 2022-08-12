package pl.pabilo8.kraftwerk.editor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.elements.ModelElement;
import pl.pabilo8.kraftwerk.editor.elements.ModelProp;
import pl.pabilo8.kraftwerk.editor.elements.ModelTexture;
import pl.pabilo8.kraftwerk.gui.Icons;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.stream.IntStream;

/**
 * An editor project class, contains all project information, such as elements, texture references, author, date.
 * It can be loaded from JSON trough {@link #fromJSON(JsonObject)} or created manually
 *
 * @author Pabilo8
 * @since 15.08.2021
 */
public class EditorProject
{
	//All the elements of the model (boxes, shapeboxes, etc.)
	public final DefaultMutableTreeNode rootElement = new DefaultMutableTreeNode("Root", true);
	public TreeModel elementTreeModel = null;

	//Props referenced in the model, in most cases these will not be exported, they should be included in screenshots/recordings
	public ArrayList<ModelProp> props = new ArrayList<>();
	//Textures referenced in the model, they are NOT saved in project files
	public ArrayList<ModelTexture> textures = new ArrayList<>();

	//URL to the model file, will be displayed inside [] in window title if it exists, null by default, null by default
	public URL filePath = null;
	//Project name
	public String name = "unnamed";
	//Model creator nickname
	public String author = "";
	//Model creation date, default for *now*
	public Date creationDate = new Date(System.currentTimeMillis());
	//Last edit date, default for *now*
	public Date lastEditDate = new Date(System.currentTimeMillis());
	//Model restrictions, this can disable shapes other than cubes, free rotation and box UVs
	public ModelRestrictionTemplate template = ModelRestrictionTemplate.FREE;
	//Whether the project has been changed since loading/saving, if true, a yes/no/cancel window will appear on quitting
	public boolean hasBeenChanged = false;

	/**
	 * @return a JSON object with project data
	 */
	public JsonObject toJSON()
	{
		JsonObject json = new JsonObject();
		json.addProperty("name", name);
		json.addProperty("author", author);
		json.addProperty("creation_date", SimpleDateFormat.getInstance().format(creationDate));
		json.addProperty("last_edit_date", SimpleDateFormat.getInstance().format(new Date(System.currentTimeMillis())));
		json.addProperty("restriction_template", template.getName());

		JsonArray textures = new JsonArray();
		for(ModelTexture texture : this.textures)
			textures.add(texture.toJSON());
		json.add("textures", textures);
		json.add("elements", getElementsJSON());


		return json;
	}

	/**
	 * @param json object acquired from {@link #toJSON()} method
	 * @return a project with data from JSON
	 */
	public static EditorProject fromJSON(JsonObject json)
	{
		EditorProject project = new EditorProject();
		project.name = json.get("name").getAsString();
		project.name = json.get("author").getAsString();
		try
		{
			project.creationDate = SimpleDateFormat.getInstance().parse(json.get("creation_date").getAsString());
			project.lastEditDate = SimpleDateFormat.getInstance().parse(json.get("creation_date").getAsString());
		}
		catch(ParseException exception)
		{
			Kraftwerk.logger.warning("Incorrect date on model file, you tinkered with it, didn't you?");
		}
		try
		{
			project.template = ModelRestrictionTemplate.valueOf(json.get("restriction_template").getAsString().toUpperCase());
		}
		catch(IllegalArgumentException exception)
		{
			Kraftwerk.logger.warning("Incorrect restriction template on model file, do NOT change stuff... or if you want, at least RTFM!");
		}

		if(json.has("textures"))
		{
			JsonArray textures = json.get("textures").getAsJsonArray();
			for(JsonElement tex : textures)
				if(tex.isJsonObject())
				{
					try
					{
						project.textures.add(new ModelTexture(tex.getAsJsonObject()));
					}
					catch(MalformedURLException e)
					{
						Kraftwerk.logger.warning("Incorrect texture data, have you tinkered with it?");
					}
				}
		}

		if(json.has("elements"))
		{
			project.loadElementsFromJSON(json.get("elements").getAsJsonArray(), project.rootElement);
		}

		Kraftwerk.logger.info(project.rootElement.toString());


		return project;
	}

	@Nullable
	public ModelTexture addTexture(@Nullable File file)
	{
		if(file==null)
			return null;

		if(template.onlyOneTexture&&textures.size() > 0)
			return null;

		try
		{
			ModelTexture texture = new ModelTexture(file.toURI().toURL());
			textures.add(texture);
			SwingUtilities.invokeLater(Kraftwerk.INSTANCE.panelTextures::refresh);
			SwingUtilities.invokeLater(Kraftwerk.INSTANCE.panelPartProperties::refresh);
			return texture;
		}
		catch(MalformedURLException e)
		{
			Kraftwerk.logger.warning("Couldn't load texture from file, "+e.getMessage());
		}
		return null;
	}

	public boolean addElement(ModelElement element)
	{
		return addElement(element, null);
	}

	public boolean addElement(ModelElement element, @Nullable DefaultMutableTreeNode parent)
	{
		if(template.onlyCuboids&&!element.isCuboid())
			return false;
		if(parent!=null)
			parent.add(element);
		else
			rootElement.add(element);
		return true;
	}

	public int getPartCount()
	{
		if(elementTreeModel==null)
			return 0;
		return getPartCount(elementTreeModel, rootElement)-1;
	}

	private int getPartCount(TreeModel model, Object node)
	{
		int count = 1;
		int nChildren = model.getChildCount(node);
		count += IntStream.range(0, nChildren).map(i -> getPartCount(model, model.getChild(node, i))).sum();
		return count;
	}

	public JsonArray getElementsJSON()
	{
		JsonArray elements = new JsonArray();
		if(elementTreeModel==null)
			return elements;

		Enumeration modelElements = rootElement.children();
		while(modelElements.hasMoreElements())
		{
			Object o = modelElements.nextElement();
			if(o instanceof ModelElement)
			{
				ModelElement model = (ModelElement)o;
				JsonObject json = model.toJSON();
				if(model.getChildCount() > 0)
					json.add("children", getChildrenElementsJSON(model));
				elements.add(json);
			}
		}
		return elements;
	}

	public static JsonArray getChildrenElementsJSON(DefaultMutableTreeNode element)
	{
		JsonArray elements = new JsonArray();

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

				JsonObject json = model.toJSON();
				if(((DefaultMutableTreeNode)o).getChildCount() > 0)
					json.add("children", getChildrenElementsJSON((DefaultMutableTreeNode)o));
				elements.add(json);
			}
		}
		return elements;
	}

	public void loadElementsFromJSON(JsonArray array, DefaultMutableTreeNode parent)
	{
		for(JsonElement jsonElement : array)
		{
			if(!jsonElement.isJsonObject())
				break;
			JsonObject obj = jsonElement.getAsJsonObject();
			ModelElement element = Kraftwerk.supplyElement(obj.get("type").getAsString());
			if(element!=null)
			{
				Kraftwerk.logger.info(obj.toString());
				element.fromJSON(obj);
				if(obj.has("children"))
					loadElementsFromJSON(obj.get("children").getAsJsonArray(), element);
				addElement(element, parent);
			}
		}
	}

	/**
	 * Restriction Templates defines what the model cannot contain
	 * f.e. Vanilla models support 22.5 based rotation angles and doesn't allow elements other than cuboids
	 */
	public enum ModelRestrictionTemplate
	{
		FREE(false, false, false, false, true),
		VANILLA_JSON(false, false, true, true, false),
		FORGE_MULTIPART(false, false, true, true, true),
		VANILLA_JAVA(true, true, false, true, false),
		TMT(true, true, false, false, false);

		@Nullable
		final public Icon icon;

		//The rules
		final public boolean onlyOneTexture;
		final public boolean onlyBoxUVs;
		final public boolean onlyVanillaRotations;
		final public boolean onlyCuboids;
		final public boolean multipartMaterials;

		ModelRestrictionTemplate(boolean onlyOneTexture, boolean onlyBoxUVs, boolean onlyVanillaRotations, boolean onlyCuboids, boolean multipartMaterials)
		{
			this.onlyOneTexture = onlyOneTexture;
			this.onlyBoxUVs = onlyBoxUVs;
			this.onlyVanillaRotations = onlyVanillaRotations;
			this.onlyCuboids = onlyCuboids;
			this.multipartMaterials = multipartMaterials;
			this.icon = Icons.loadSVGIcon("model_worker/"+getName());
		}

		public String getName()
		{
			return super.name().toLowerCase();
		}
	}
}
