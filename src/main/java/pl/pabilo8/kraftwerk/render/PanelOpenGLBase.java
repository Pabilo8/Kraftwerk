package pl.pabilo8.kraftwerk.render;

import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLStateKeeper;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import pl.pabilo8.kraftwerk.Kraftwerk;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

/**
 * @author Pabilo8
 * @since 13.08.2021
 */
public abstract class PanelOpenGLBase extends JPanel implements GLEventListener, MouseListener, MouseMotionListener, KeyListener
{
	private final GLCanvas glArea;
	private final FPSAnimator animator;
	GL4bc gl = null;
	GLAutoDrawable drawable_ = null;

	public static Logger logger = Logger.getLogger("OPENGL");
	static {
		logger.setParent(Kraftwerk.logger);

		logger.info("Test!");
	}

	private long time_previous = -1L;
	private double _fps;
	private int fps_counter;
	private final boolean[] mouse_button_pressed = new boolean[3];
	private boolean first_setup = true;

	public PanelOpenGLBase(int fps)
	{
		this.setLayout(new BorderLayout());
		this.glArea = new GLCanvas();
		this.glArea.addGLEventListener(this);
		this.add(this.glArea);
		this.animator = new FPSAnimator(this.glArea, fps, false);
		this.animator.start();
		this._fps = fps;
	}

	public void startAnimation()
	{
		if(!this.animator.isAnimating())
		{
			this.animator.start();
		}

	}

	public void stopAnimation()
	{
		this.animator.stop();
	}

	public double fps()
	{
		return this._fps;
	}

	public void init(GLAutoDrawable drawable)
	{
		this.drawable_ = drawable;
		GL4bc gl = drawable.getGL().getGL4bc();
		this.gl = gl;
		gl.setSwapInterval(1);
		gl.glEnable(2929);
		gl.glEnable(2977);
		gl.glDepthFunc(515);
		gl.glHint(3152, 4353);
		gl.glBlendFunc(770, 771);
		if(this.first_setup)
		{
			this.constructor();
			this.first_setup = false;
		}

		this.setup();
		this.time_previous = -1L;
		this.fps_counter = 0;
		this.glArea.addMouseListener(this);
		this.glArea.addMouseMotionListener(this);
		this.glArea.addKeyListener(this);
	}

	public void constructor()
	{
	}

	public void dispose(GLAutoDrawable drawable)
	{
		this.gl = drawable.getGL().getGL4bc();
	}

	public void setup()
	{
		float[] pos = new float[]{5.0F, 5.0F, 10.0F, 0.0F};
		this.gl.glLightfv(16384, 4611, pos, 0);
		this.gl.glEnable(2884);
		this.gl.glEnable(16384);
		this.gl.glEnable(2929);
		this.gl.glEnable(2977);
		this.gl.glDepthFunc(515);
		this.gl.glHint(3152, 4354);
	}

	public abstract void draw();

	public void draw(GL4bc gl)
	{
		this.draw();
	}

	public void redraw()
	{
		this.drawable_.display();
	}

	public void display(GLAutoDrawable drawable)
	{
		GL4bc gl = drawable.getGL().getGL4bc();
		this.gl = gl;
		if(drawable instanceof GLJPanel&&!((GLJPanel)drawable).isOpaque()&&((GLJPanel)drawable).shouldPreserveColorBufferIfTranslucent())
		{
			gl.glClear(256);
		}
		else
		{
			gl.glClear(16640);
		}

		gl.glLoadIdentity();
		if(this.time_previous==-1L)
		{
			this.time_previous = System.currentTimeMillis();
		}

		if(this.fps_counter==20)
		{
			long t = System.currentTimeMillis();
			if(t-this.time_previous > 0L)
			{
				this._fps = this._fps*0.7D+6.0D/(double)(t-this.time_previous);
			}

			this.time_previous = t;
			this.fps_counter = 0;
		}
		else
		{
			++this.fps_counter;
		}

		this.drawable_ = drawable;
		this.draw(gl);
		gl.glFlush();
	}

	public boolean isMouseButtonPressed(int id)
	{
		return id > 0&&id < 4&&this.mouse_button_pressed[id-1];
	}

	public void mouseReleased(int x, int y, MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		if((e.getModifiers()&16)!=0)
		{
			this.mouse_button_pressed[0] = false;
		}

		if((e.getModifiers()&8)!=0)
		{
			this.mouse_button_pressed[1] = false;
		}

		if((e.getModifiers()&4)!=0)
		{
			this.mouse_button_pressed[2] = false;
		}

		this.mouseReleased(x, y, e);
	}

	public void mouseClicked(int x, int y, MouseEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		this.mouseClicked(x, y, e);
	}

	public void mouseDragged(int x, int y, MouseEvent e)
	{
	}

	public void mouseDragged(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		this.mouseDragged(x, y, e);
	}

	public void mouseMoved(int x, int y, MouseEvent e)
	{
	}

	public void mouseMoved(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		this.mouseMoved(x, y, e);
	}

	public void keyPressed(char keyChar, KeyEvent e)
	{
	}

	public void keyPressed(KeyEvent e)
	{
		char k = e.getKeyChar();
		this.keyPressed(k, e);
	}

	public void keyReleased(char keyChar, KeyEvent e)
	{
	}

	public void keyReleased(KeyEvent e)
	{
		char k = e.getKeyChar();
		this.keyReleased(k, e);
	}

	public void keyTyped(char keyChar, KeyEvent e)
	{
	}

	public void keyTyped(KeyEvent e)
	{
		char k = e.getKeyChar();
		this.keyTyped(k, e);
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
	{
	}

	public void mouseEntered(int x, int y, MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		this.mouseEntered(x, y, e);
	}

	public void mouseExited(int x, int y, MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		this.mouseExited(x, y, e);
	}

	public void mousePressed(int x, int y, MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		if((e.getModifiers()&16)!=0)
		{
			this.mouse_button_pressed[0] = true;
		}

		if((e.getModifiers()&8)!=0)
		{
			this.mouse_button_pressed[1] = true;
		}

		if((e.getModifiers()&4)!=0)
		{
			this.mouse_button_pressed[2] = true;
		}

		this.mousePressed(x, y, e);
	}

	public void reshape(int width, int height)
	{
		float w = (float)width/(float)height;
		this.gl.glMatrixMode(5889);
		this.gl.glLoadIdentity();
		this.gl.glFrustum(-0.1F*w, 0.1F*w, -0.10000000149011612D, 0.10000000149011612D, 0.2214999943971634D, 100.0D);
		this.gl.glMatrixMode(5888);
		this.gl.glLoadIdentity();
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		this.gl = drawable.getGL().getGL4bc();
		this.drawable_ = drawable;
		this.reshape(width, height);
	}

	public GL4bc getGl()
	{
		return this.gl;
	}

	public int loadTexture()
	{
		int[] tmp = new int[1];
		this.gl.glGenTextures(1, tmp, 0);
		return tmp[0];
	}

	public void deleteTexture(int tex_id)
	{
		int[] tmp = new int[]{tex_id};
		this.gl.glDeleteTextures(1, tmp, 0);
	}

	ByteBuffer getTextureByteBuffer(boolean useAlphaChannel, BufferedImage img)
	{
		int[] packedPixels = new int[img.getWidth()*img.getHeight()];
		PixelGrabber pixelgrabber = new PixelGrabber(img, 0, 0, img.getWidth(), img.getHeight(), packedPixels, 0, img.getWidth());

		try
		{
			pixelgrabber.grabPixels();
		}
		catch(InterruptedException var10)
		{
			throw new RuntimeException();
		}

		int bytesPerPixel = useAlphaChannel?4: 3;
		ByteBuffer unpackedPixels = ByteBuffer.allocate(packedPixels.length*bytesPerPixel);

		for(int row = img.getHeight()-1; row >= 0; --row)
		{
			for(int col = 0; col < img.getWidth(); ++col)
			{
				int packedPixel = packedPixels[row*img.getWidth()+col];
				unpackedPixels.put((byte)(packedPixel >> 16&255));
				unpackedPixels.put((byte)(packedPixel >> 8&255));
				unpackedPixels.put((byte)(packedPixel >> 0&255));
				if(useAlphaChannel)
				{
					unpackedPixels.put((byte)(packedPixel >> 24&255));
				}
			}
		}

		unpackedPixels.flip();
		return unpackedPixels;
	}

	public int loadTexture(InputStream imagefile)
	{
		int texid = this.loadTexture();
		if(imagefile==null)
		{
			return texid;
		}
		else
		{
			this.gl.glBindTexture(3553, texid);

			try
			{
				BufferedImage im = ImageIO.read(imagefile);
				this.gl.glTexImage2D(3553, 0, 6407, im.getWidth(), im.getHeight(), 0, 6407, 5121, this.getTextureByteBuffer(false, im));
				this.gl.glTexParameteri(3553, 10241, 9729);
				this.gl.glTexParameteri(3553, 10240, 9729);
			}
			catch(IOException var5)
			{
				int[] tmp = new int[]{texid};
				this.gl.glDeleteTextures(1, tmp, 0);
				texid = -1;
				System.err.println("ERROR: Cannot load image: "+imagefile);
			}

			return texid;
		}
	}

	public int loadTextureAlpha(InputStream imagefile)
	{
		int texid = this.loadTexture();
		if(imagefile==null)
		{
			return texid;
		}
		else
		{
			this.gl.glBindTexture(3553, texid);

			try
			{
				BufferedImage im = ImageIO.read(imagefile);
				this.gl.glTexImage2D(3553, 0, 6408, im.getWidth(), im.getHeight(), 0, 6408, 5121, this.getTextureByteBuffer(true, im));
				this.gl.glTexParameteri(3553, 10241, 9729);
				this.gl.glTexParameteri(3553, 10240, 9729);
			}
			catch(IOException var5)
			{
				int[] tmp = new int[]{texid};
				this.gl.glDeleteTextures(1, tmp, 0);
				texid = -1;
				System.err.println("ERROR: Cannot load image: "+imagefile);
			}

			return texid;
		}
	}

	public void updateTexture(int texture_id, InputStream imagefile)
	{
		if(imagefile!=null)
		{
			this.gl.glBindTexture(3553, texture_id);

			try
			{
				BufferedImage im = ImageIO.read(imagefile);
				this.gl.glTexImage2D(3553, 0, 6407, im.getWidth(), im.getHeight(), 0, 6407, 5121, this.getTextureByteBuffer(false, im));
				this.gl.glTexParameteri(3553, 10241, 9729);
				this.gl.glTexParameteri(3553, 10240, 9729);
			}
			catch(IOException var4)
			{
				System.err.println("ERROR: Cannot load image: "+imagefile);
			}

		}
	}

	public void updateTexture(int texture_id, byte[] data, int w, int h)
	{
		this.gl.glBindTexture(3553, texture_id);
		this.gl.glTexImage2D(3553, 0, 6407, w, h, 0, 6407, 5121, ByteBuffer.wrap(data));
		this.gl.glTexParameteri(3553, 10241, 9729);
		this.gl.glTexParameteri(3553, 10240, 9729);
	}

	public void updateTexture(int texture_id, ByteBuffer data, int w, int h)
	{
		this.gl.glBindTexture(3553, texture_id);
		this.gl.glTexImage2D(3553, 0, 6407, w, h, 0, 6407, 5121, data);
		this.gl.glTexParameteri(3553, 10241, 9729);
		this.gl.glTexParameteri(3553, 10240, 9729);
	}

	public void updateTextureAlpha(int texture_id, InputStream imagefile)
	{
		if(imagefile!=null)
		{
			this.gl.glBindTexture(3553, texture_id);

			try
			{
				BufferedImage im = ImageIO.read(imagefile);
				this.gl.glTexImage2D(3553, 0, 6407, im.getWidth(), im.getHeight(), 0, 6408, 5121, this.getTextureByteBuffer(true, im));
				this.gl.glTexParameteri(3553, 10241, 9729);
				this.gl.glTexParameteri(3553, 10240, 9729);
			}
			catch(IOException var4)
			{
				System.err.println("ERROR: Cannot load image: "+imagefile);
			}

		}
	}

	public void updateTextureAlpha(int texture_id, byte[] data, int w, int h)
	{
		this.gl.glBindTexture(3553, texture_id);
		this.gl.glTexImage2D(3553, 0, 6407, w, h, 0, 6408, 5121, ByteBuffer.wrap(data));
		this.gl.glTexParameteri(3553, 10241, 9729);
		this.gl.glTexParameteri(3553, 10240, 9729);
	}

	public void updateTextureAlpha(int texture_id, ByteBuffer data, int w, int h)
	{
		this.gl.glBindTexture(3553, texture_id);
		this.gl.glTexImage2D(3553, 0, 6407, w, h, 0, 6408, 5121, data);
		this.gl.glTexParameteri(3553, 10241, 9729);
		this.gl.glTexParameteri(3553, 10240, 9729);
	}

	public void renderDirections(double vectorLength)
	{
		this.gl.glPushMatrix();

		this.gl.glDisable(GL4bc.GL_TEXTURE);
		this.gl.glLineWidth(4.0F);

		this.gl.glBegin(GL4bc.GL_LINES);

		this.gl.glLineWidth(2.0F);
		this.gl.glBegin(GL4bc.GL_LINES);
		this.gl.glColor3d(1, 0, 0);
		this.gl.glVertex3d(0.0D, 0.0D, 0.0D);
		this.gl.glColor3d(1, 0, 0);
		this.gl.glVertex3d(vectorLength, 0.0D, 0.0D);
		this.gl.glColor3d(0, 1, 0);
		this.gl.glVertex3d(0.0D, 0.0D, 0.0D);
		this.gl.glColor3d(0, 1, 0);
		this.gl.glVertex3d(0.0D, vectorLength, 0.0D);
		this.gl.glColor3d(0.5, 0.5, 1);
		this.gl.glVertex3d(0.0D, 0.0D, 0.0D);
		this.gl.glColor3d(0.5, 0.5, 1);
		this.gl.glVertex3d(0.0D, 0.0D, vectorLength);
		this.gl.glEnd();
		this.gl.glLineWidth(1.0F);
		this.gl.glEnable(GL4bc.GL_TEXTURE);

		this.gl.glPopMatrix();
	}
}
