package pl.pabilo8.kraftwerk.render.model;

import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.render.PositionTextureVertex;
import pl.pabilo8.kraftwerk.render.TexturedQuad;

/**
 * Unrightfully stolen from Minecraft's source code (pls don't sue)
 * @since 13.08.2021
 */
public class ModelBox
{
	/**
	 * The (x,y,z) vertex positions and (u,v) texture coordinates for each of the 8 points on a cube
	 */
	private final PositionTextureVertex[] vertexPositions;
	/**
	 * An array of 6 TexturedQuads, one for each face of a cube
	 */
	private final TexturedQuad[] quadList;
	/**
	 * X vertex coordinate of lower box corner
	 */
	public final float posX1;
	/**
	 * Y vertex coordinate of lower box corner
	 */
	public final float posY1;
	/**
	 * Z vertex coordinate of lower box corner
	 */
	public final float posZ1;
	/**
	 * X vertex coordinate of upper box corner
	 */
	public final float posX2;
	/**
	 * Y vertex coordinate of upper box corner
	 */
	public final float posY2;
	/**
	 * Z vertex coordinate of upper box corner
	 */
	public final float posZ2;
	public String boxName;

	public ModelBox(ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta)
	{
		this(renderer, texU, texV, x, y, z, dx, dy, dz, delta, renderer.mirror);
	}

	public ModelBox(ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta, boolean mirror)
	{
		this.posX1 = x;
		this.posY1 = y;
		this.posZ1 = z;
		this.posX2 = x+(float)dx;
		this.posY2 = y+(float)dy;
		this.posZ2 = z+(float)dz;
		this.vertexPositions = new PositionTextureVertex[8];
		this.quadList = new TexturedQuad[6];
		float f = x+(float)dx;
		float f1 = y+(float)dy;
		float f2 = z+(float)dz;
		x = x-delta;
		y = y-delta;
		z = z-delta;
		f = f+delta;
		f1 = f1+delta;
		f2 = f2+delta;

		if(mirror)
		{
			float f3 = f;
			f = x;
			x = f3;
		}

		PositionTextureVertex v_xyz = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
		PositionTextureVertex v_Xyz = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
		PositionTextureVertex v_XYz = new PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
		PositionTextureVertex v_xYz = new PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
		PositionTextureVertex v_xyZ = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
		PositionTextureVertex v_XyZ = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
		PositionTextureVertex v_XYZ = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
		PositionTextureVertex v_xYZ = new PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
		this.vertexPositions[0] = v_xyz;
		this.vertexPositions[1] = v_Xyz;
		this.vertexPositions[2] = v_XYz;
		this.vertexPositions[3] = v_xYz;
		this.vertexPositions[4] = v_xyZ;
		this.vertexPositions[5] = v_XyZ;
		this.vertexPositions[6] = v_XYZ;
		this.vertexPositions[7] = v_xYZ;
		this.quadList[0] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_Xyz, v_XYz, v_XYZ}, texU+dz+dx, texV+dz, texU+dz+dx+dz, texV+dz+dy, renderer.textureWidth, renderer.textureHeight);
		this.quadList[1] = new TexturedQuad(new PositionTextureVertex[]{v_xyz, v_xyZ, v_xYZ, v_xYz}, texU, texV+dz, texU+dz, texV+dz+dy, renderer.textureWidth, renderer.textureHeight);
		this.quadList[2] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_xyZ, v_xyz, v_Xyz}, texU+dz, texV, texU+dz+dx, texV+dz, renderer.textureWidth, renderer.textureHeight);
		this.quadList[3] = new TexturedQuad(new PositionTextureVertex[]{v_XYz, v_xYz, v_xYZ, v_XYZ}, texU+dz+dx, texV+dz, texU+dz+dx+dx, texV, renderer.textureWidth, renderer.textureHeight);
		this.quadList[4] = new TexturedQuad(new PositionTextureVertex[]{v_Xyz, v_xyz, v_xYz, v_XYz}, texU+dz, texV+dz, texU+dz+dx, texV+dz+dy, renderer.textureWidth, renderer.textureHeight);
		this.quadList[5] = new TexturedQuad(new PositionTextureVertex[]{v_xyZ, v_XyZ, v_XYZ, v_xYZ}, texU+dz+dx+dz, texV+dz, texU+dz+dx+dz+dx, texV+dz+dy, renderer.textureWidth, renderer.textureHeight);

		if(mirror)
		{
			for(TexturedQuad texturedquad : this.quadList)
			{
				texturedquad.flipFace();
			}
		}
	}

	public void render(float scale)
	{
		Kraftwerk.gl.glEnable(3553);
		for(TexturedQuad texturedquad : this.quadList)
		{
			texturedquad.draw(scale);
		}
		Kraftwerk.gl.glDisable(3553);
	}

	public ModelBox setBoxName(String name)
	{
		this.boxName = name;
		return this;
	}
}
