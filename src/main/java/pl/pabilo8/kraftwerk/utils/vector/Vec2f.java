package pl.pabilo8.kraftwerk.utils.vector;

import com.google.gson.JsonArray;

/**
 * @author Pabilo8
 * @since 22.07.2021
 */
@SuppressWarnings("unused")
public class Vec2f
{
	public static final Vec2f ZERO = new Vec2f(0, 0);
	public static final Vec2f ONE = new Vec2f(1, 1);

	/**
	 * The x coordinate offset of the texture
	 */
	public final float x;
	/**
	 * The y coordinate offset of the texture
	 */
	public final float y;

	public Vec2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Vec2f(JsonArray array)
	{
		this(array.get(0).getAsFloat(), array.get(1).getAsFloat());
	}

	public JsonArray toJSON()
	{
		JsonArray array = new JsonArray();
		array.add(x);
		array.add(y);
		return array;
	}
}
