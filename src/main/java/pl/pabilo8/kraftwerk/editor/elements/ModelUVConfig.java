package pl.pabilo8.kraftwerk.editor.elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import pl.pabilo8.kraftwerk.utils.vector.Vec2f;
import pl.pabilo8.kraftwerk.utils.vector.Vec2i;

/**
 * @author Pabilo8
 * @since 18.12.2021
 */
public class ModelUVConfig
{
	public boolean isBoxUV;
	public Vec2i[] pos;
	public Vec2f[] scale;

	public ModelUVConfig(boolean isBoxUV)
	{
		this.isBoxUV = isBoxUV;
		if(isBoxUV)
			setSimpleBoxUV(0,0);
	}

	public ModelUVConfig(JsonObject json)
	{
		isBoxUV = json.get("boxUV").getAsBoolean();

		if(!isBoxUV&&json.has("pos")&&json.has("scale"))
		{
			JsonArray pp = json.get("pos").getAsJsonArray();
			JsonArray ss = json.get("scale").getAsJsonArray();

			pos = new Vec2i[pp.size()];
			scale = new Vec2f[pp.size()];

			for(int i = 0; i < pp.size(); i++)
			{
				pos[i] = new Vec2i(ss.get(i).getAsJsonArray());
				scale[i] = new Vec2f(ss.get(i).getAsJsonArray());
			}
		}
		else
			pos = new Vec2i[]{new Vec2i(json.get("pos").getAsJsonArray())};
	}

	public JsonObject toJSON()
	{
		JsonObject json = new JsonObject();
		json.addProperty("boxUV", isBoxUV);
		if(!isBoxUV)
		{
			JsonArray array = new JsonArray();
			for(Vec2i p : pos)
				array.add(p.toJSON());
			json.add("pos", array);

			array = new JsonArray();
			for(Vec2f p : scale)
				array.add(p.toJSON());
			json.add("scale", array);
		}
		else
			json.add("pos", pos[0].toJSON());

		return json;
	}

	public void setSimpleBoxUV(int x, int y)
	{
		pos = new Vec2i[]{new Vec2i(x, y)};
		scale = new Vec2f[]{Vec2f.ONE};
	}
}
