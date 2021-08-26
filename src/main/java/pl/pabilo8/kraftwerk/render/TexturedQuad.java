package pl.pabilo8.kraftwerk.render;

import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

/**
 * @author Pabilo8
 * @since 22.07.2021
 */
public class TexturedQuad
{
	public PositionTextureVertex[] vertexPositions;
	public int nVertices;
	private boolean invertNormal;

	public TexturedQuad(PositionTextureVertex[] vertices)
	{
		this.vertexPositions = vertices;
		this.nVertices = vertices.length;
	}

	public TexturedQuad(PositionTextureVertex[] vertices, int texcoordU1, int texcoordV1, int texcoordU2, int texcoordV2, float textureWidth, float textureHeight)
	{
		this(vertices);
		float f = 0.0F/textureWidth;
		float f1 = 0.0F/textureHeight;
		vertices[0] = vertices[0].setTexturePosition((float)texcoordU2/textureWidth-f, (float)texcoordV1/textureHeight+f1);
		vertices[1] = vertices[1].setTexturePosition((float)texcoordU1/textureWidth+f, (float)texcoordV1/textureHeight+f1);
		vertices[2] = vertices[2].setTexturePosition((float)texcoordU1/textureWidth+f, (float)texcoordV2/textureHeight-f1);
		vertices[3] = vertices[3].setTexturePosition((float)texcoordU2/textureWidth-f, (float)texcoordV2/textureHeight-f1);
	}

	public void flipFace()
	{
		PositionTextureVertex[] apositiontexturevertex = new PositionTextureVertex[this.vertexPositions.length];

		for(int i = 0; i < this.vertexPositions.length; ++i)
		{
			apositiontexturevertex[i] = this.vertexPositions[this.vertexPositions.length-i-1];
		}

		this.vertexPositions = apositiontexturevertex;
	}

	/**
	 * Draw this primitve. This is typically called only once as the generated drawing instructions are saved by the
	 * renderer and reused later.
	 */
	public void draw(float scale)
	{
		Kraftwerk.gl.glPushMatrix();

		Vec3d vec3d = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[0].vector3D);
		Vec3d vec3d1 = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[2].vector3D);
		Vec3d vec3d2 = vec3d1.crossProduct(vec3d).normalize();
		float f = (float)vec3d2.x;
		float f1 = (float)vec3d2.y;
		float f2 = (float)vec3d2.z;

		if(this.invertNormal)
		{
			f = -f;
			f1 = -f1;
			f2 = -f2;
		}

		Kraftwerk.gl.glEnable(3553);
		Kraftwerk.gl.glBegin(7);

		//Kraftwerk.gl.glNormal3d(0.0D, 0.0D, 1.0D);

		for(int i = 0; i < 4; ++i)
		{
			PositionTextureVertex positiontexturevertex = this.vertexPositions[i];
			Kraftwerk.gl.glNormal3d(f, f1, f2);
			Kraftwerk.gl.glTexCoord2d(positiontexturevertex.texturePositionX, 1f-positiontexturevertex.texturePositionY);
			Kraftwerk.gl.glVertex3d(positiontexturevertex.vector3D.x*(double)scale, positiontexturevertex.vector3D.y*(double)scale, positiontexturevertex.vector3D.z*(double)scale);
		}

		Kraftwerk.gl.glEnd();
		Kraftwerk.gl.glDisable(3553);

		Kraftwerk.gl.glPopMatrix();
	}
}
