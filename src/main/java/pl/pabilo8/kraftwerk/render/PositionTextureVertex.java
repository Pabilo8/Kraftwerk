package pl.pabilo8.kraftwerk.render;

import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

/**
 * Unrightfully stolen from Minecraft's source code (pls don't sue)
 *
 * @since 13.08.2021
 */
public class PositionTextureVertex
{
	public Vec3d pos;
	public float u, v;

	public PositionTextureVertex(float x, float y, float z, float u, float v)
	{
		this(new Vec3d(x, y, z), u, v);
	}

	public PositionTextureVertex setTexturePosition(float u, float v)
	{
		return new PositionTextureVertex(this, u, v);
	}

	public PositionTextureVertex(PositionTextureVertex vertex, float u, float v)
	{
		this.pos = vertex.pos;
		this.u = u;
		this.v = v;
	}

	public PositionTextureVertex(Vec3d pos, float u, float v)
	{
		this.pos = pos;
		this.u = u;
		this.v = v;
	}
}
