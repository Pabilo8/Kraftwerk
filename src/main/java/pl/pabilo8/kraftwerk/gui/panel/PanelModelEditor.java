package pl.pabilo8.kraftwerk.gui.panel;

import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.glu.GLU;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.render.OpenGLTexture;
import pl.pabilo8.kraftwerk.render.PanelOpenGLBase;
import pl.pabilo8.kraftwerk.render.model.ModelPlayer;
import pl.pabilo8.kraftwerk.utils.MathUtils;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;
import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author Pabilo8
 * @since 13.08.2021
 */
public class PanelModelEditor extends PanelOpenGLBase implements MouseWheelListener
{
	public static final double FOV = 75;
	public GLU glu;

	public ArrayList<Integer> keyPressed = new ArrayList<>();
	public boolean isShiftDown = false;
	private int[] lastDrag = null;
	float scrollIn = 0;

	boolean cameraCenterMode = false;
	float cameraZoom = 0.25f;
	float cameraX = 0;
	float cameraY = 2;
	float cameraZ = 0;
	float cameraYaw = 180f;
	float cameraPitch = -12.5f;

	private final ModelPlayer modelPlayer = new ModelPlayer(0, false);
	private OpenGLTexture textureHans;

	public PanelModelEditor()
	{
		super(Kraftwerk.INSTANCE.defaultFPS);
		addMouseWheelListener(this);
	}

	public void setup()
	{
		textureHans = new OpenGLTexture(ResourceUtils.texResource("editor/hans.png"), true);

		getGl().setSwapInterval(1);
		glu = GLU.createGLU(getGl());
		getGl().glShadeModel(GL4bc.GL_FLAT);
		Kraftwerk.gl = getGl();
		logger.info("Stage Initialized");

	}

	@Override
	public void draw()
	{
		GL4bc gl = getGl();


		Color b = getBackground();
		gl.glClearColor(b.getRed()/255f, b.getGreen()/255f, b.getBlue()/255f, 1f);

		gl.glTexParameteri(GL4bc.GL_TEXTURE_2D, GL4bc.GL_TEXTURE_MAG_FILTER, GL4bc.GL_NEAREST);
		gl.glTexParameteri(GL4bc.GL_TEXTURE_3D, GL4bc.GL_TEXTURE_MAG_FILTER, GL4bc.GL_NEAREST);

		gl.glPushMatrix();
		gl.glTranslated(getWidth()/2f, getHeight()/2f, 0);

		gl.glMatrixMode(GL4bc.GL_PROJECTION);
		doCameraControls();
		setupCameraTransform();

		gl.glPushMatrix();
		this.orientCamera();
		//drawModel();
		drawHans();
		gl.glPopMatrix();

		endCameraTransform();

		drawDirectionThingy();
		gl.glPopMatrix();

	}

	private void drawHans()
	{
		GL4bc gl = getGl();

		gl.glPushMatrix();

		gl.glTranslated(0, 2, 0.5);


		gl.glEnable(GL4bc.GL_BLEND);

		gl.glPushMatrix();
		gl.glTranslated(0, 0, 0);
		gl.glScaled(1, -1, 1);
		textureHans.use(gl);
		modelPlayer.render(0, 0, 45, 0, 0.0625f);

		gl.glPopMatrix();

		gl.glDisable(GL4bc.GL_BLEND);

		gl.glPopMatrix();
	}

	private void drawDirectionThingy()
	{
		GL4bc gl = getGl();

		gl.glPushMatrix();
		//gl.glTranslatef((-getWidth()/2f)+32, (getHeight()/2f)-32, 0);
		//gl.glRotatef(-cameraPitch, 1, 0, 0);
		//gl.glTranslated(0, -cameraY, 0);
		//gl.glRotatef(-cameraYaw, 0, 1, 0);
		gl.glTranslated(1, 0, 0);

		//gl.glScalef(1.0F, -1.0F, -1.0F);
		renderDirections(4);
		gl.glPopMatrix();
	}

	private void doCameraControls()
	{
		float camSpeed = isShiftDown?0.5f: 0.15f;
		if(scrollIn!=cameraZoom)
		{
			cameraZoom += Math.signum(scrollIn-cameraZoom)*0.0625f;
			cameraZoom = MathUtils.clamp(cameraZoom, Math.max(0.025f,scrollIn), Math.min(5f,scrollIn));
		}

		for(Integer i : keyPressed)
		{
			double true_angle = Math.toRadians(cameraYaw);
			double true_angle2 = Math.toRadians(cameraYaw-90);
			double true_angle3 = Math.toRadians(-cameraPitch);

			//camera position control
			Vec3d move = new Vec3d(0, 0, 0);

			switch(i)
			{
				case KeyEvent.VK_UP:
					cameraPitch += 2;
					break;
				case KeyEvent.VK_DOWN:
					cameraPitch -= 2;
					break;
				case KeyEvent.VK_LEFT:
					cameraYaw += 2;
					break;
				case KeyEvent.VK_RIGHT:
					cameraYaw -= 2;
					break;
				case KeyEvent.VK_W:
					move = MathUtils.offsetPosDirection(-camSpeed, true_angle, true_angle3);
					break;
				case KeyEvent.VK_S:
					move = MathUtils.offsetPosDirection(camSpeed, true_angle, true_angle3);
					break;
				case KeyEvent.VK_A:
					move = MathUtils.offsetPosDirection(camSpeed, true_angle2, 0);
					break;
				case KeyEvent.VK_D:
					move = MathUtils.offsetPosDirection(-camSpeed, true_angle2, 0);
					break;
				case KeyEvent.VK_E:
					move = new Vec3d(0, camSpeed*0.65, 0);
					break;
				case KeyEvent.VK_Q:
					move = new Vec3d(0, -camSpeed*0.65, 0);
					break;
			}

			cameraX += cameraCenterMode?-move.x: move.x;
			cameraY += move.y;
			cameraZ += cameraCenterMode?-move.z: move.z;
		}
	}

	private void setupCameraTransform()
	{
		GL4bc gl = getGl();
		gl.glMatrixMode(5889);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		glu.gluPerspective(FOV, (float)(this.getWidth())/(float)(this.getHeight()), 0.05F, 100*MathUtils.SQRT_2);
		gl.glMatrixMode(5888);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

	}

	private void orientCamera()
	{
		GL4bc gl = getGl();

		if(!cameraCenterMode)
		{
			gl.glRotatef(-cameraPitch, 1, 0, 0);
			gl.glTranslated(0, -cameraY, 0);
			gl.glRotatef(-cameraYaw, 0, 1, 0);
			gl.glTranslated(-cameraX, 0, -cameraZ);
		}
		else
		{
			double true_angle = Math.toRadians(cameraYaw);
			double true_angle3 = Math.toRadians(cameraPitch);
			Vec3d t = MathUtils.offsetPosDirection(cameraZoom*20f, true_angle, true_angle3);

			gl.glRotatef(-cameraPitch, 1, 0, 0);
			gl.glRotatef(-cameraYaw, 0, 1, 0);
			gl.glTranslated(-t.x, t.y, -t.z);
			gl.glTranslated(0, -cameraY, 0);
			gl.glTranslated(cameraX, 0, cameraZ);


		}
	}

	private void endCameraTransform()
	{
		GL4bc gl = getGl();
		//Tessellator tessellator = Tessellator.getInstance();
		//BufferBuilder bufferbuilder = tessellator.getBuffer();

		//bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
		gl.glColorMask(true, true, true, true);
		gl.glMatrixMode(5889);
		gl.glPopMatrix();
		gl.glMatrixMode(5888);
		gl.glPopMatrix();
		gl.glDepthMask(true);
	}

	@Override
	public void keyPressed(char keyChar, KeyEvent e)
	{
		super.keyPressed(keyChar, e);
		isShiftDown = e.isShiftDown();
		if(!keyPressed.contains(e.getKeyCode()))
			keyPressed.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(char keyChar, KeyEvent e)
	{
		super.keyPressed(keyChar, e);
		isShiftDown = e.isShiftDown();
		keyPressed.removeIf(integer -> integer==e.getKeyCode());
	}

	@Override
	protected void processFocusEvent(FocusEvent e)
	{
		if(e.getID()==FocusEvent.FOCUS_LOST)
			keyPressed.clear();
		super.processFocusEvent(e);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		super.mousePressed(e);
		if(isMouseButtonPressed(MouseEvent.BUTTON3))
			lastDrag = new int[]{e.getX(), e.getY()};
	}

	@Override
	public void mouseDragged(int x, int y, MouseEvent e)
	{
		super.mouseDragged(x, y, e);
		if(isMouseButtonPressed(MouseEvent.BUTTON3))
		{
			int dx = e.getX()-lastDrag[0];
			int dy = e.getY()-lastDrag[1];
			cameraYaw = cameraYaw-(dx/2f);
			cameraPitch = cameraPitch-(dy/2f);
		}
		lastDrag = new int[]{e.getX(), e.getY()};
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		int mouseChange = e.getWheelRotation();
		scrollIn += mouseChange/16f;
		scrollIn = MathUtils.clamp(scrollIn, 0.025f, 5f);
	}

	public void toggleCamera()
	{
		cameraCenterMode = !cameraCenterMode;
	}
}

