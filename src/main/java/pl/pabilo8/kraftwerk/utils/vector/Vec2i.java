package pl.pabilo8.kraftwerk.utils.vector;

import com.google.gson.JsonArray;

/**
 * @author Pabilo8
 * @since 22.07.2021
 */
@SuppressWarnings("unused")
public class Vec2i
{
	public static final Vec2i ZERO = new Vec2i(0, 0);

	/**
	 * The x coordinate offset of the texture
	 */
	public final int x;
	/**
	 * The y coordinate offset of the texture
	 */
	public final int y;

	public Vec2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Vec2i(JsonArray array)
	{
		this(array.get(0).getAsInt(), array.get(1).getAsInt());
	}

	public JsonArray toJSON()
	{
		JsonArray array = new JsonArray();
		array.add(x);
		array.add(y);
		return array;
	}
}
