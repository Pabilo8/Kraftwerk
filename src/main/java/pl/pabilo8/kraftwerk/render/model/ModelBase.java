package pl.pabilo8.kraftwerk.render.model;

import pl.pabilo8.kraftwerk.utils.vector.Vec2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Unrightfully stolen from Minecraft's source code (pls don't sue)
 * @since 13.08.2021
 */
public class ModelBase
{
	public float swingProgress;
	public boolean isRiding;
	public boolean isChild = false;
	/** This is a list of all the boxes (ModelRenderer.class) in the current model. */
	public List<ModelRenderer> boxList = new ArrayList<>();
	/** A mapping for all texture offsets */
	private final HashMap<String, Vec2i> modelTextureMap = new HashMap<>();
	public int textureWidth = 64;
	public int textureHeight = 32;


	public void render()
	{

	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	public void setRotationAngles()
	{
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	 * and third as in the setRotationAngles method.
	 */
	public void setLivingAnimations(float partialTicks)
	{
	}

	protected void setTextureOffset(String partName, int x, int y)
	{
		this.modelTextureMap.put(partName, new Vec2i(x, y));
	}

	public Vec2i getTextureOffset(String partName)
	{
		return this.modelTextureMap.get(partName);
	}

	/**
	 * Copies the angles from one object to another. This is used when objects should stay aligned with each other, like
	 * the hair over a players head.
	 */
	public static void copyModelAngles(ModelRenderer source, ModelRenderer dest)
	{
		dest.rotateAngleX = source.rotateAngleX;
		dest.rotateAngleY = source.rotateAngleY;
		dest.rotateAngleZ = source.rotateAngleZ;
		dest.rotationPointX = source.rotationPointX;
		dest.rotationPointY = source.rotationPointY;
		dest.rotationPointZ = source.rotationPointZ;
	}

	public void setModelAttributes(ModelBase model)
	{
		this.swingProgress = model.swingProgress;
		this.isRiding = model.isRiding;
		this.isChild = model.isChild;
	}
}
