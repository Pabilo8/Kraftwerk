package pl.pabilo8.kraftwerk.utils;

import pl.pabilo8.kraftwerk.Kraftwerk;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author Pabilo8
 * @since 13.08.2021
 */
public class ResourceUtils
{
	public static ResourceBundle getResourceBundle(Locale locale)
	{
		URL resource = Kraftwerk.class.getResource("lang/");
		ClassLoader loader = new URLClassLoader(new URL[]{resource});
		InputStream resourceAsStream = loader.getResourceAsStream(locale.toLanguageTag().replace('-', '_')+".properties");
		try
		{
			Kraftwerk.logger.info("Loaded Language File for "+locale);
			return new PropertyResourceBundle(resourceAsStream);
		}
		catch(IOException e)
		{
			Kraftwerk.logger.severe("Couldn't load language file for "+locale);
			return ResourceBundle.getBundle("");
		}
	}

	public static InputStream webStream(String filename)
	{
		InputStream is = null;
		String convertedFileName = filename.replace('\\', '/');
		if(filename.length() > 4&&filename.startsWith("http"))
		{
			try
			{
				URL url = new URL(convertedFileName);
				URLConnection conn = url.openConnection();
				is = conn.getInputStream();
			}
			catch(IOException e)
			{
				Kraftwerk.logger.info(String.format("Exception loading web image %s. %s", filename, e.getMessage()));
			}
		}

		return is;
	}

	public static InputStream fileStream(String s)
	{
		InputStream is = null;
		String convertedFileName = s.replace('\\', '/');

		try
		{
			is = new FileInputStream(convertedFileName);
		}
		catch(IOException e)
		{
			Kraftwerk.logger.info(String.format("Exception loading file %s", s));
		}

		return is;
	}

	public static InputStream texStream(String s)
	{
		URL resource = Kraftwerk.class.getResource("textures/");
		ClassLoader loader = new URLClassLoader(new URL[]{resource});
		return loader.getResourceAsStream(s);
	}

	public static InputStream fontStream(String s)
	{
		URL resource = Kraftwerk.class.getResource("fonts/");
		ClassLoader loader = new URLClassLoader(new URL[]{resource});
		return loader.getResourceAsStream(s);
	}

	public static BufferedImage texResource(String s)
	{
		InputStream stream = texStream(s);
		try
		{
			assert stream!=null;
			return ImageIO.read(stream);
		}
		catch(Exception e)
		{
			Kraftwerk.logger.info(String.format("Exception loading internal image %s", s));
		}
		return null;
	}

	@Nonnull
	public static String translateString(ResourceBundle res, String s)
	{
		try
		{
			return res.containsKey(s)?res.getString(s): Kraftwerk.fallbackRes.getString(s);
		}
		catch(Exception ignored)
		{
			Kraftwerk.logger.warning(String.format("No language entry for %s", s));
			return s;
		}
	}

	@Nonnull
	public static String formatString(ResourceBundle res, String s, Object... objects)
	{
		return String.format(translateString(res, s), objects);
	}

	public static String attemptGetResourceLocation(URL texturePath)
	{
		String file = texturePath.getFile();
		if(file.contains("assets")&&file.contains("textures"))
		{
			file = file.substring(file.indexOf("assets")+("assets/".length()));
			String domain = file.substring(0, file.indexOf("/textures"));
			file = file.substring(file.indexOf("/textures")+"/textures/".length(), file.contains(".")?file.lastIndexOf('.'): file.length());
			return String.format("%s:%s", domain, file);
		}

		return "minecraft:blocks/missingno";
	}

	@Nullable
	public static URI getIconURI(String undo)
	{
		URL resource = Kraftwerk.class.getResource(String.format("textures/icons/%s.svg", undo));
		try
		{
			return resource==null?null: resource.toURI();
		}
		catch(URISyntaxException e)
		{
			return null;
		}
	}
}
