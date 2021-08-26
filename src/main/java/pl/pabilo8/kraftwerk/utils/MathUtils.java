package pl.pabilo8.kraftwerk.utils;

import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

import java.util.Random;

/**
 * @author Pabilo8
 * @since 21.07.2021
 * Based on unrightfully stolen Minecraft's MathHelper class (pls don't sue)
 */
public class MathUtils
{
	public static final float SQRT_2 = sqrt(2.0F);
	/**
	 * A table of sin values computed from 0 (inclusive) to 2*pi (exclusive), with steps of 2*PI / 65536, improves performance.
	 */
	private static final float[] SIN_TABLE = new float[65536];
	private static final Random RANDOM = new Random();
	private static final double[] ASINE_TAB;
	private static final double[] COS_TAB;

	static
	{
		for(int i = 0; i < 65536; ++i)
		{
			SIN_TABLE[i] = (float)Math.sin((double)i*Math.PI*2.0D/65536.0D);
		}

		ASINE_TAB = new double[257];
		COS_TAB = new double[257];

		for(int j = 0; j < 257; ++j)
		{
			double d0 = (double)j/256.0D;
			double d1 = Math.asin(d0);
			COS_TAB[j] = Math.cos(d1);
			ASINE_TAB[j] = d1;
		}
	}

	/**
	 * sin looked up in a table
	 */
	public static float sin(float value)
	{
		return SIN_TABLE[(int)(value*10430.378F)&65535];
	}

	/**
	 * cos looked up in the sin table with the appropriate offset
	 */
	public static float cos(float value)
	{
		return SIN_TABLE[(int)(value*10430.378F+16384.0F)&65535];
	}

	public static float sqrt(float value)
	{
		return (float)Math.sqrt((double)value);
	}

	public static float sqrt(double value)
	{
		return (float)Math.sqrt(value);
	}

	public static Vec3d offsetPosDirection(float offset, double yaw, double pitch)
	{
		double yy = (sin((float)pitch)*offset);
		double true_offset = (cos((float)pitch)*offset);

		double xx = (sin((float)yaw)*true_offset);
		double zz = (cos((float)yaw)*true_offset);

		return new Vec3d(xx, yy, zz);
	}


	/**
	 * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
	 * third parameters.
	 */
	public static int clamp(int num, int min, int max)
	{
		if (num < min)
		{
			return min;
		}
		else
		{
			return num > max ? max : num;
		}
	}

	/**
	 * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
	 * third parameters
	 */
	public static float clamp(float num, float min, float max)
	{
		if (num < min)
		{
			return min;
		}
		else
		{
			return num > max ? max : num;
		}
	}

	public static double clamp(double num, double min, double max)
	{
		if (num < min)
		{
			return min;
		}
		else
		{
			return num > max ? max : num;
		}
	}

	/**
	 * Rounds the first parameter up to the next interval of the second parameter.
	 *
	 * For instance, {@code roundUp(1, 4)} returns 4; {@code roundUp(0, 4)} returns 0; and {@code roundUp(4, 4)} returns
	 * 4.
	 */
	public static int roundUp(int number, int interval)
	{
		if (interval == 0)
		{
			return 0;
		}
		else if (number == 0)
		{
			return interval;
		}
		else
		{
			if (number < 0)
			{
				interval *= -1;
			}

			int i = number % interval;
			return i == 0 ? number : number + interval - i;
		}
	}
}
