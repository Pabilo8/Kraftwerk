package pl.pabilo8.kraftwerk.render;

import com.jogamp.opengl.GL4bc;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

/**
 * @author Pabilo8
 * @since 22.07.2021
 */
@SuppressWarnings("unused")
public class TexturedQuad
{
	public PositionTextureVertex[] vertexPositions;
	private boolean invertNormal;

	public TexturedQuad(PositionTextureVertex[] vertices)
	{
		this.vertexPositions = vertices;
	}

	public TexturedQuad(PositionTextureVertex[] vertices, int texcoordU1, int texcoordV1, int texcoordU2, int texcoordV2, float textureWidth, float textureHeight)
	{
		this(vertices);
		vertices[0] = vertices[0].setTexturePosition((float)texcoordU2/textureWidth, (float)texcoordV1/textureHeight);
		vertices[1] = vertices[1].setTexturePosition((float)texcoordU1/textureWidth, (float)texcoordV1/textureHeight);
		vertices[2] = vertices[2].setTexturePosition((float)texcoordU1/textureWidth, (float)texcoordV2/textureHeight);
		vertices[3] = vertices[3].setTexturePosition((float)texcoordU2/textureWidth, (float)texcoordV2/textureHeight);
	}

	public void flipFace()
	{
		PositionTextureVertex[] vertices = new PositionTextureVertex[this.vertexPositions.length];

		for(int i = 0; i < this.vertexPositions.length; ++i)
			vertices[i] = this.vertexPositions[this.vertexPositions.length-i-1];

		this.vertexPositions = vertices;
	}

	/**
	 * Draw this primitve. This is typically called only once as the generated drawing instructions are saved by the
	 * renderer and reused later.
	 */
	public void draw(float scale)
	{
		Kraftwerk.gl.glPushMatrix();

		Vec3d vec3d = this.vertexPositions[1].pos.subtractReverse(this.vertexPositions[0].pos);
		Vec3d vec3d1 = this.vertexPositions[1].pos.subtractReverse(this.vertexPositions[2].pos);
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

		Kraftwerk.gl.glBegin(7);

		for(int i = 0; i < 4; ++i)
		{
			PositionTextureVertex vertex = this.vertexPositions[i];
			Kraftwerk.gl.glNormal3d(f, f1, f2);
			Kraftwerk.gl.glTexCoord2d(vertex.u, 1f-vertex.v);
			Kraftwerk.gl.glVertex3d(vertex.pos.x*(double)scale, vertex.pos.y*(double)scale, vertex.pos.z*(double)scale);
		}

		Kraftwerk.gl.glEnd();

		Kraftwerk.gl.glPopMatrix();
	}

	public void drawWireFrame(float scale)
	{
		Kraftwerk.gl.glPushMatrix();
		Kraftwerk.gl.glBegin(GL4bc.GL_LINE_STRIP);

		for(PositionTextureVertex vertex : this.vertexPositions)
			Kraftwerk.gl.glVertex3d(vertex.pos.x*(double)scale, vertex.pos.y*(double)scale, vertex.pos.z*(double)scale);

		Kraftwerk.gl.glEnd();
		Kraftwerk.gl.glPopMatrix();
	}

	public void setInvertNormal(boolean invertNormal)
	{
		this.invertNormal = invertNormal;
	}
}
