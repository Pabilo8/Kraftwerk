package pl.pabilo8.kraftwerk.utils;

import com.jogamp.opengl.GL4bc;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.render.GLAllocation;
import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

import java.awt.*;
import java.nio.FloatBuffer;

/**
 * @author Pabilo8
 * @since 19.12.2021
 */
public class ModelEditorUtils
{
	/**
	 * Float buffer used to set OpenGL material colors
	 */
	private static final FloatBuffer COLOR_BUFFER = GLAllocation.createDirectFloatBuffer(4);
	private static final Vec3d LIGHT0_POS = (new Vec3d(0.20000000298023224D, 1.0D, -0.699999988079071D)).normalize();
	private static final Vec3d LIGHT1_POS = (new Vec3d(-0.20000000298023224D, 1.0D, 0.699999988079071D)).normalize();

	public final static Color[] FACE_COLORS = new Color[]{
			new Color(0x4078E0),
			new Color(0x305AA8),
			new Color(0xE05555),
			new Color(0x983939),
			new Color(0x62B543),
			new Color(0x437C2E),
	};

	/**
	 * Disables the OpenGL lighting properties enabled by enableStandardItemLighting
	 */
	public static void disableStandardItemLighting()
	{
		Kraftwerk.gl.glDisable(GL4bc.GL_LIGHTING);
		Kraftwerk.gl.glDisable(GL4bc.GL_LIGHT0);
		Kraftwerk.gl.glDisable(GL4bc.GL_LIGHT1);
		Kraftwerk.gl.glDisable(GL4bc.GL_COLOR_MATERIAL);
	}

	/**
	 * Sets the OpenGL lighting properties to the values used when rendering blocks as items
	 */
	public static void enableStandardItemLighting()
	{
		Kraftwerk.gl.glEnable(GL4bc.GL_LIGHTING);
		Kraftwerk.gl.glEnable(GL4bc.GL_LIGHT0);
		Kraftwerk.gl.glEnable(GL4bc.GL_LIGHT1);
		Kraftwerk.gl.glEnable(GL4bc.GL_COLOR_MATERIAL);
		Kraftwerk.gl.glColorMaterial(1032, 5634);
		Kraftwerk.gl.glLightfv(16384, 4611, setColorBuffer(LIGHT0_POS.x, LIGHT0_POS.y, LIGHT0_POS.z, 0.0D));
		//float f = 0.6F;
		Kraftwerk.gl.glLightfv(16384, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
		Kraftwerk.gl.glLightfv(16384, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
		Kraftwerk.gl.glLightfv(16384, 4610, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
		Kraftwerk.gl.glLightfv(16385, 4611, setColorBuffer(LIGHT1_POS.x, LIGHT1_POS.y, LIGHT1_POS.z, 0.0D));
		Kraftwerk.gl.glLightfv(16385, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
		Kraftwerk.gl.glLightfv(16385, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
		Kraftwerk.gl.glLightfv(16385, 4610, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
		Kraftwerk.gl.glShadeModel(7424);
		//float f1 = 0.4F;
		Kraftwerk.gl.glLightModelfv(2899, setColorBuffer(0.4F, 0.4F, 0.4F, 1.0F));
	}

	/**
	 * Update and return colorBuffer with the RGBA values passed as arguments
	 */
	private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_)
	{
		return setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
	}

	/**
	 * Update and return colorBuffer with the RGBA values passed as arguments
	 */
	public static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_)
	{
		COLOR_BUFFER.clear();
		COLOR_BUFFER.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
		COLOR_BUFFER.flip();
		return COLOR_BUFFER;
	}

	/**
	 * Sets OpenGL lighting for rendering blocks as items inside GUI screens (such as containers).
	 */
	public static void enableGUIStandardItemLighting()
	{
		Kraftwerk.gl.glPushMatrix();
		Kraftwerk.gl.glRotatef(-30.0F, 0.0F, 1.0F, 0.0F);
		Kraftwerk.gl.glRotatef(165.0F, 1.0F, 0.0F, 0.0F);
		enableStandardItemLighting();
		Kraftwerk.gl.glPopMatrix();
	}

}
