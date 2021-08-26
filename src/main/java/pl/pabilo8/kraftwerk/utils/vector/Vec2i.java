package pl.pabilo8.kraftwerk.utils.vector;

/**
 * @author Pabilo8
 * @since 22.07.2021
 */
public class Vec2i
{
	public static final Vec2i ZERO = new Vec2i(0,0);

	/** The x coordinate offset of the texture */
	public final int x;
	/** The y coordinate offset of the texture */
	public final int y;

	public Vec2i(int textureOffsetXIn, int textureOffsetYIn)
	{
		this.x = textureOffsetXIn;
		this.y = textureOffsetYIn;
	}
}
