package pl.pabilo8.kraftwerk;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * A program that draws with JOGL in a NEWT GLWindow.
 *
 */
public class JOGL2NewtDemo {
	private static String TITLE = "JOGL 2 with NEWT";  // window's title
	private static final int WINDOW_WIDTH = 640;  // width of the drawable
	private static final int WINDOW_HEIGHT = 480; // height of the drawable
	private static final int FPS = 60; // animator's target frames per second

	static {
		GLProfile.initSingleton();  // The method allows JOGL to prepare some Linux-specific locking optimizations
	}

	/**
	 * The entry main() method.
	 */
	public static void main(String[] args) {
		// Get the default OpenGL profile, reflecting the best for your running platform
		GLProfile glp = GLProfile.getDefault();
		// Specifies a set of OpenGL capabilities, based on your profile.
		GLCapabilities caps = new GLCapabilities(glp);
		// Create the OpenGL rendering canvas
		GLWindow window = GLWindow.create(caps);

		// Create a animator that drives canvas' display() at the specified FPS.
		final FPSAnimator animator = new FPSAnimator(window, FPS, true);

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyNotify(WindowEvent arg0) {
				// Use a dedicate thread to run the stop() to ensure that the
				// animator stops before program exits.
				new Thread() {
					@Override
					public void run() {
						if (animator.isStarted())
							animator.stop();    // stop the animator loop
						System.exit(0);
					}
				}.start();
			}
		});

		window.addGLEventListener(new JOGL2Renderer());
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setTitle(TITLE);
		window.setVisible(true);
		animator.start();  // start the animator loop
	}


	/**
	 * Class handles the OpenGL events to render graphics.
	 *
	 */
	public static class JOGL2Renderer implements GLEventListener
	{
		private double theta = 0.0f;  // rotational angle

		@Override
		public void init(GLAutoDrawable drawable)
		{

		}

		@Override
		public void dispose(GLAutoDrawable drawable)
		{

		}

		/**
		 * Called back by the drawable to render OpenGL graphics
		 */
		@Override
		public void display(GLAutoDrawable drawable) {
			GL2 gl = drawable.getGL().getGL2();   // get the OpenGL graphics context

			gl.glClear(GL2.GL_COLOR_BUFFER_BIT);    // clear background
			gl.glLoadIdentity();                   // reset the model-view matrix

			// Rendering code - draw a triangle
			float sine = (float)Math.sin(theta);
			float cosine = (float)Math.cos(theta);
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(1, 0, 0);
			gl.glVertex2d(-cosine, -cosine);
			gl.glColor3f(0, 1, 0);
			gl.glVertex2d(0, cosine);
			gl.glColor3f(0, 0, 1);
			gl.glVertex2d(sine, -sine);
			gl.glEnd();

			update();
		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
		{

		}

		/**
		 * Update the rotation angle after each frame refresh
		 */
		private void update() {
			theta += 0.01;
		}

		/*... Other methods leave blank ...*/
	}
}