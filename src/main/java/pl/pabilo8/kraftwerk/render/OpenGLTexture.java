package pl.pabilo8.kraftwerk.render;

import com.jogamp.opengl.GL4bc;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author Pabilo8
 * @since 13.08.2021
 */
public class OpenGLTexture
{
	private int texture_id;
	private boolean alpha;
	private boolean error;
	private OpenGLTexture.ImageDownloader downloader;
	private GL4bc gl;
	private boolean glLoaded;
	private String name;
	private String filename;
	private String path;
	public static BufferedImage default_texture;
	private int rendering_quality;

	static
	{
		default_texture = ResourceUtils.texResource("editor/missingno.png");
	}

	public void setRenderingQualityToNearest()
	{
		this.rendering_quality = 9728;
	}

	public void setRenderingQualityToLinear()
	{
		this.rendering_quality = 9729;
	}

	public static void setDefaultImage(InputStream is)
	{
		if(is!=null)
		{
			try
			{
				default_texture = ImageIO.read(is);
				is.close();
			}
			catch(IOException ignored)
			{
			}

		}
	}

	public OpenGLTexture()
	{
		this.texture_id = -1;
		this.alpha = false;
		this.error = false;
		this.downloader = null;
		this.gl = null;
		this.glLoaded = false;
		this.name = "";
		this.filename = null;
		this.path = null;
		this.rendering_quality = 9729;
		this.error = true;
	}

	public OpenGLTexture(InputStream imagefile, boolean alpha)
	{
		this.texture_id = -1;
		this.alpha = false;
		this.error = false;
		this.downloader = null;
		this.gl = null;
		this.glLoaded = false;
		this.name = "";
		this.filename = null;
		this.path = null;
		this.rendering_quality = 9729;
		this.loadImage(imagefile, alpha);
	}

	public OpenGLTexture(InputStream is)
	{
		this(is, false);
	}


	public OpenGLTexture(BufferedImage img)
	{
		this(img, false);
	}

	public OpenGLTexture(BufferedImage img, boolean alpha)
	{
		this.texture_id = -1;
		this.alpha = false;
		this.error = false;
		this.downloader = null;
		this.gl = null;
		this.glLoaded = false;
		this.name = "";
		this.filename = null;
		this.path = null;
		this.rendering_quality = 9729;
		this.error = false;
		this.glLoaded = false;
		this.alpha = alpha;
		this.updateTexture(img);
	}

	public void startDownloadingNow(GL4bc gl)
	{
		this.gl = gl;
		this.createTexture();
		this.error = false;
		this.glLoaded = false;
		this.downloadImageSilent(this.path, this.filename);
	}

	public void loadImage(InputStream imagefile, boolean alpha)
	{
		this.error = false;
		this.alpha = alpha;
		this.glLoaded = false;
		this.createTexture();
		this.downloadImageSilent(imagefile);
	}

	public boolean isDownloaded()
	{
		return this.downloader!=null&&this.downloader.isDownloaded();
	}

	private void downloadImageSilent(InputStream imagefile)
	{
		if(!this.error)
		{
			if(this.downloader!=null)
			{
				this.downloader.stop();
			}

			this.downloader = new OpenGLTexture.ImageDownloader(imagefile);
		}
	}

	private void downloadImageSilent(String path, String filename)
	{
		if(!this.error)
		{
			if(this.downloader!=null)
			{
				this.downloader.stop();
			}

			this.downloader = new OpenGLTexture.ImageDownloader(path, filename);
		}
	}

	public void updateTexture(BufferedImage img)
	{
		this.downloader = new OpenGLTexture.ImageDownloader(img);
		this.glLoaded = false;
	}

	public void updateTexture(InputStream imagefile, boolean alpha)
	{
		if(imagefile!=null)
		{
			this.alpha = alpha;
			this.glLoaded = false;
			this.downloadImageSilent(imagefile);
		}
	}

	public void updateTexture(InputStream imagefile)
	{
		this.updateTexture(imagefile, false);
	}

	public BufferedImage getBufferedImage()
	{
		return this.downloader==null?null: this.downloader.img;
	}

	public int getWidth()
	{
		if(this.downloader==null)
		{
			return 0;
		}
		else
		{
			return this.downloader.img==null?0: this.downloader.img.getWidth();
		}
	}

	public int getHeight()
	{
		if(this.downloader==null)
		{
			return 0;
		}
		else
		{
			return this.downloader.img==null?0: this.downloader.img.getHeight();
		}
	}

	public void setGL(GL4bc gl)
	{
		this.gl = gl;
	}

	public void releaseGL()
	{
		this.deleteTexture();
		this.glLoaded = false;
	}

	public void use(GL4bc gl)
	{
		if(this.gl!=null&&this.gl!=gl)
		{
			this.texture_id = -1;
			this.gl = gl;
			this.createTexture();
			this.glLoaded = false;
		}

		this.gl = gl;
		this.use();
	}

	public void use()
	{
		if(!this.error&&this.gl!=null)
		{
			if(!this.glLoaded)
			{
				this.loadTexture();
			}

			this.gl.glBindTexture(3553, this.texture_id);
		}
	}

	private void loadTexture()
	{
		if(!this.error&&!this.glLoaded&&this.gl!=null)
		{
			if(this.isDownloaded())
			{
				if(this.texture_id < 0)
				{
					this.createTexture();
				}

				if(this.alpha)
				{
					this.glLoadTextureAlpha();
				}
				else
				{
					this.glLoadTexture();
				}
			}

		}
	}

	private void createTexture()
	{
		if(this.texture_id >= 0)
		{
			this.deleteTexture();
		}

		if(this.gl!=null)
		{
			int[] tmp = new int[1];
			this.gl.glGenTextures(1, tmp, 0);
			this.texture_id = tmp[0];
			if(this.texture_id < 0)
			{
				this.error = true;
			}
			else if(default_texture!=null)
			{
				this.gl.glBindTexture(3553, this.texture_id);
				this.gl.glTexImage2D(3553, 0, 6408, default_texture.getWidth(), default_texture.getHeight(), 0, 6408, 5121, this.getTextureByteBuffer(true, default_texture));
				this.gl.glTexParameteri(3553, 10241, this.rendering_quality);
				this.gl.glTexParameteri(3553, 10240, this.rendering_quality);
			}

		}
	}

	private void deleteTexture()
	{
		if(this.texture_id >= 0&&this.gl!=null)
		{
			int[] tmp = new int[]{this.texture_id};
			this.gl.glDeleteTextures(1, tmp, 0);
			this.texture_id = -1;
			this.glLoaded = false;
		}
	}

	public void delete()
	{
		this.error = true;
		this.glLoaded = false;
		if(this.downloader!=null)
		{
			this.downloader.stop();
			this.downloader = null;
		}

		this.deleteTexture();
	}

	private ByteBuffer getTextureByteBuffer(boolean useAlphaChannel, BufferedImage img)
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

	private void glLoadTexture()
	{
		if(this.texture_id >= 0&&this.isDownloaded())
		{
			this.gl.glBindTexture(3553, this.texture_id);
			this.gl.glTexImage2D(3553, 0, 6407, this.downloader.img.getWidth(), this.downloader.img.getHeight(), 0, 6407, 5121, this.getTextureByteBuffer(false, this.downloader.img));
			this.gl.glTexParameteri(3553, 10241, this.rendering_quality);
			this.gl.glTexParameteri(3553, 10240, this.rendering_quality);
			this.glLoaded = true;
		}
	}

	private void glLoadTextureAlpha()
	{
		if(this.texture_id >= 0&&this.isDownloaded())
		{
			this.gl.glBindTexture(3553, this.texture_id);
			this.gl.glTexImage2D(3553, 0, 6408, this.downloader.img.getWidth(), this.downloader.img.getHeight(), 0, 6408, 5121, this.getTextureByteBuffer(true, this.downloader.img));
			this.gl.glTexParameteri(3553, 10241, this.rendering_quality);
			this.gl.glTexParameteri(3553, 10240, this.rendering_quality);
			this.glLoaded = true;
		}
	}

	public String toString()
	{
		return this.alpha?"alphaTexture texture"+this.texture_id+" "+this.filename: "texture texture"+this.texture_id+" "+this.filename;
	}

	public int getId()
	{
		return this.texture_id;
	}

	public String getName()
	{
		return this.name;
	}

	private class ImageDownloader implements Runnable
	{
		public BufferedImage img = null;
		private Thread thread = null;
		private InputStream imagefile;
		private boolean stopped = false;
		private boolean image_downloaded = false;
		private String filename = null;
		private String path = null;

		public ImageDownloader(BufferedImage img)
		{
			this.img = img;
			this.image_downloaded = true;
		}

		public ImageDownloader(InputStream imagefile)
		{
			this.imagefile = imagefile;
			this.thread = new Thread(this);
			this.thread.start();
		}

		public ImageDownloader(String path, String filename)
		{
			this.path = path;
			this.filename = filename;
			this.imagefile = null;
			this.thread = new Thread(this);
			this.thread.start();
		}

		public void run()
		{
			try
			{
				if(!this.stopped)
				{
					if(this.imagefile==null&&this.filename!=null)
					{
						if(this.path!=null&&this.path.length()!=0)
						{
							this.imagefile = ResourceUtils.webStream(this.path+File.separatorChar+this.filename);
						}
						else
						{
							this.imagefile = ResourceUtils.webStream(this.filename);
						}
					}

					if(this.imagefile==null)
					{
						this.thread = null;
						return;
					}

					this.img = ImageIO.read(this.imagefile);
					this.imagefile.close();
					this.image_downloaded = true;
				}
			}
			catch(IOException var2)
			{
				this.thread = null;
			}

		}

		public void stop()
		{
			this.stopped = true;
			this.thread = null;
			if(!this.image_downloaded&&this.imagefile!=null)
			{
				try
				{
					this.imagefile.close();
				}
				catch(IOException ignored)
				{
				}
			}

		}

		public boolean isDownloaded()
		{
			return this.image_downloaded;
		}
	}
}
