package pl.pabilo8.kraftwerk.render.model;

import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.render.PositionTextureVertex;
import pl.pabilo8.kraftwerk.render.TexturedQuad;

/**
 * @author Pabilo8
 * Parts of this code were unrightfully stolen from Minecraft's source code (pls don't sue)
 * @since 22.07.2021
 */
public class ModelPlayer extends ModelBase
{
	public ModelRenderer bipedHead;
	public ModelRenderer bipedHeadwear;

	public ModelRenderer bipedBody;
	public ModelRenderer bipedBodyWear;
	private final ModelRenderer bipedCape;

	private final boolean smallArms;

	public ModelRenderer bipedRightArm, bipedRightArmwear, bipedRightHand;

	public ArmPose leftArmPose;
	public ModelRenderer bipedLeftArm, bipedLeftArmwear, bipedLeftHand;

	public ArmPose rightArmPose;
	public ModelRenderer bipedRightLeg, bipedRightLegwear, bipedRightFoot;

	public ModelRenderer bipedLeftLeg, bipedLeftLegwear, bipedLeftFoot;

	public boolean isSneak;

	public ModelPlayer(float expand, boolean smallArmsIn)
	{
		this.leftArmPose = ArmPose.EMPTY;
		this.rightArmPose = ArmPose.EMPTY;
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, expand);
		this.bipedHead.setRotationPoint(0.0F, 0.0f, 0.0F);
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, expand+0.5F);
		this.bipedHeadwear.setRotationPoint(0.0F, 0.0f, 0.0F);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-4.0f, 0.0F, -2.0f, 8, 12, 4, expand);
		this.bipedBody.setRotationPoint(0.0F, 0.0f, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, expand);
		this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, expand);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0f, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-2.0f, 0.0F, -2.0f, 4, 12, 4, expand);
		this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-2.0f, 0.0F, -2.0f, 4, 12, 4, expand);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0f, 0.0F);

		this.smallArms = smallArmsIn;
		this.bipedCape = new ModelRenderer(this, 0, 0);
		this.bipedCape.setTextureSize(64, 32);
		this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, expand);

		if(smallArmsIn)
		{
			this.bipedLeftArm = new ModelRenderer(this, 32, 48);
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, expand);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
			this.bipedRightArm = new ModelRenderer(this, 40, 16);
			this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, expand);
			this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
			this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
			this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, expand+0.25F);
			this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
			this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
			this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, expand+0.25F);
			this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
		}
		else
		{
			this.bipedLeftArm = new ModelRenderer(this, 32, 48);
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, expand);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
			this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
			this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, expand+0.25F);
			this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
			this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
			this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, expand+0.25F);
			this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
		}

		this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expand);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
		this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expand+0.25F);
		this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
		this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expand+0.25F);
		this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.bipedBodyWear = new ModelRenderer(this, 16, 32);
		this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, expand+0.25F);
		this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);


		BipedTextureUVs uvs = new BipedTextureUVs(40, 16, 40, 16, 0, 16, 0, 16);

		this.bipedLeftHand = addLimb(this.bipedLeftArm, true, uvs.armLeftU, uvs.armLeftV, -1.0F, -2.0F, -2.0F, expand);
		this.bipedRightHand = addLimb(this.bipedRightArm, false, uvs.armRightU, uvs.armRightV, -3.0F, -2.0F, -2.0F, expand);

		this.bipedLeftFoot = addLimb(this.bipedLeftLeg, true, uvs.legLeftU, uvs.legLeftV, -2.0F, 0.0F, -2.0F, expand);
		this.bipedLeftFoot.rotationPointY += 2;
		this.bipedRightFoot = addLimb(this.bipedRightLeg, false, uvs.legRightU, uvs.legRightV, -2.0F, 0.0F, -2.0F, expand);
		this.bipedRightFoot.rotationPointY += 2;
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float scale)
	{
		setRotationAngles(limbSwing, limbSwingAmount, headYaw, headPitch, scale);
		Kraftwerk.gl.glPushMatrix();
		Kraftwerk.gl.glRotatef(headYaw, 0, 1, 0);

		if(isSneak)
		{
			Kraftwerk.gl.glTranslatef(0.0F, 0.2F, 0.0F);
		}

		this.bipedHead.render(scale);
		this.bipedBody.render(scale);
		this.bipedBodyWear.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedRightArmwear.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedLeftArmwear.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedRightLegwear.render(scale);
		this.bipedLeftLeg.render(scale);
		this.bipedLeftLegwear.render(scale);
		this.bipedHeadwear.render(scale);

		Kraftwerk.gl.glPopMatrix();
	}

	private void setRotationAngles(float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float scale)
	{
		/*
		Vec3d vHl = getRotationsForSkeletonParts(skeleton,Skeleton.HAND_LEFT,Skeleton.ELBOW_LEFT);
		leftHand.rotateAngleZ=0;
		leftHand.rotateAngleX=bipedLeftArm.rotateAngleX-(float)((-vHl.z/0.25)*1.57);
		 */


		copyModelAngles(this.bipedHead, this.bipedHeadwear);
		copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
		copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
		copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
		copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
		copyModelAngles(this.bipedBody, this.bipedBodyWear);

		if(isSneak)
			this.bipedCape.rotationPointY = 2.0F;
		else
			this.bipedCape.rotationPointY = 0.0F;
	}

	public enum ArmPose
	{
		EMPTY,
		ITEM,
		BLOCK,
		BOW_AND_ARROW
	}

	private ModelRenderer addLimb(ModelRenderer bipedLeftArm, boolean l, int u, int v, float x, float y, float z, float expand)
	{
		bipedLeftArm.cubeList.clear();
		bipedLeftArm.cubeList.add(new ModelBoxCustomizable(bipedLeftArm, u, v, x, y, z, 4, 5, 4, expand, 0, -4));

		// TODO: 19.05.2021 corners
		//bipedLeftArm.cubeList.add(new ModelBoxCustomizable(bipedLeftArm, uvs.armLeftU, uvs.armLeftV, -1.0F, 2.0F, -2.0F, 4, 4, 4, expand-0.01f,0,-4));

		ModelRenderer mod = new ModelRenderer(this);
		mod.rotationPointY = 4f;
		mod.cubeList.add(new ModelBoxCustomizable(mod, u, v+5, x, -1, z, 4, 7, 4, expand, -5, 0));
		bipedLeftArm.addChild(mod);
		return mod;
	}

	public static class BipedTextureUVs
	{
		int armLeftU, armLeftV;
		int armRightU, armRightV;
		int legLeftU, legLeftV;
		int legRightU, legRightV;
		int bodyU, bodyV;

		public BipedTextureUVs(int armLeftU, int armLeftV, int armRightU, int armRightV, int legLeftU, int legLeftV, int legRightU, int legRightV)
		{
			this(armLeftU, armLeftV, armRightU, armRightV, legLeftU, legLeftV, legRightU, legRightV, 16, 16);
		}

		public BipedTextureUVs(int armLeftU, int armLeftV, int armRightU, int armRightV, int legLeftU, int legLeftV, int legRightU, int legRightV, int bodyU, int bodyV)
		{
			this.armLeftU = armLeftU;
			this.armLeftV = armLeftV;
			this.armRightU = armRightU;
			this.armRightV = armRightV;
			this.legLeftU = legLeftU;
			this.legLeftV = legLeftV;
			this.legRightU = legRightU;
			this.legRightV = legRightV;
			this.bodyU = bodyU;
			this.bodyV = bodyV;
		}
	}

	//stolen from the Betweenlands,
	private static class ModelBoxCustomizable extends ModelBox
	{
		public static final int SIDE_LEFT = 1;

		public static final int SIDE_RIGHT = 2;

		public static final int SIDE_TOP = 4;

		public static final int SIDE_BOTTOM = 8;

		public static final int SIDE_FRONT = 16;

		public static final int SIDE_BACK = 32;

		public static final int SIDE_ALL = SIDE_LEFT|SIDE_RIGHT|SIDE_TOP|SIDE_BOTTOM|SIDE_FRONT|SIDE_BACK;

		private final PositionTextureVertex[] vertexPositions;

		private final TexturedQuad[] quadList;

		private final float posX1;

		private final float posY1;

		private final float posZ1;

		private final float posX2;

		private final float posY2;

		private final float posZ2;

		private int visibleSides;

		public ModelBoxCustomizable(ModelRenderer model, int u, int v, float x1, float y1, float z1, int width, int height, int depth, float expand, int topVOffset, int bottomUOffset)
		{
			super(model, u, v, x1, y1, z1, width, height, depth, expand);
			posX1 = x1;
			posY1 = y1;
			posZ1 = z1;
			posX2 = x1+width;
			posY2 = y1+height;
			posZ2 = z1+depth;
			vertexPositions = new PositionTextureVertex[8];
			quadList = new TexturedQuad[6];
			float x2 = x1+width;
			float y2 = y1+height;
			float z2 = z1+depth;
			x1 -= expand;
			y1 -= expand;
			z1 -= expand;
			x2 += expand;
			y2 += expand;
			z2 += expand;
			if(model.mirror)
			{
				float x = x2;
				x2 = x1;
				x1 = x;
			}
			PositionTextureVertex v000 = new PositionTextureVertex(x1, y1, z1, 1.3563156E-19F, 1.3563156E-19F);
			PositionTextureVertex v100 = new PositionTextureVertex(x2, y1, z1, 1.3563264E-19F, 4.966073E28F);
			PositionTextureVertex v110 = new PositionTextureVertex(x2, y2, z1, 1.9364292E31F, 1.7032407E25F);
			PositionTextureVertex v010 = new PositionTextureVertex(x1, y2, z1, 1.9441665E31F, 1.9264504E-19F);
			PositionTextureVertex v001 = new PositionTextureVertex(x1, y1, z2, 4.076745E22F, 1.3563156E-19F);
			PositionTextureVertex v101 = new PositionTextureVertex(x2, y1, z2, 2.0113521E-19F, 3.0309808E24F);
			PositionTextureVertex v111 = new PositionTextureVertex(x2, y2, z2, 6.977187E22F, 1.88877E31F);
			PositionTextureVertex v011 = new PositionTextureVertex(x1, y2, z2, 1.0943429E31F, 2.0958594E-19F);
			vertexPositions[0] = v000;
			vertexPositions[1] = v100;
			vertexPositions[2] = v110;
			vertexPositions[3] = v010;
			vertexPositions[4] = v001;
			vertexPositions[5] = v101;
			vertexPositions[6] = v111;
			vertexPositions[7] = v011;
			quadList[0] = new TexturedQuad(new PositionTextureVertex[]{v101, v100, v110, v111}, u+depth+width, v+depth, u+depth+width+depth, v+depth+height, model.textureWidth, model.textureHeight);
			quadList[1] = new TexturedQuad(new PositionTextureVertex[]{v000, v001, v011, v010}, u, v+depth, u+depth, v+depth+height, model.textureWidth, model.textureHeight);
			quadList[2] = new TexturedQuad(new PositionTextureVertex[]{v101, v001, v000, v100}, u+depth, v+topVOffset, u+depth+width, v+depth+topVOffset, model.textureWidth, model.textureHeight);
			quadList[3] = new TexturedQuad(new PositionTextureVertex[]{v110, v010, v011, v111}, u+depth+width+bottomUOffset, v+depth+topVOffset, u+depth+width+width+bottomUOffset, v+topVOffset, model.textureWidth, model.textureHeight);
			quadList[4] = new TexturedQuad(new PositionTextureVertex[]{v100, v000, v010, v110}, u+depth, v+depth, u+depth+width, v+depth+height, model.textureWidth, model.textureHeight);
			quadList[5] = new TexturedQuad(new PositionTextureVertex[]{v001, v101, v111, v011}, u+depth+width+depth, v+depth, u+depth+width+depth+width, v+depth+height, model.textureWidth, model.textureHeight);
			if(model.mirror)
			{
				for(int i = 0; i < quadList.length; i++)
				{
					quadList[i].flipFace();
				}
			}
			setVisibleSides(SIDE_ALL);
		}

		public void setVisibleSides(int visibleSides)
		{
			this.visibleSides = visibleSides&SIDE_ALL;
		}

		@Override
		public void render(float scale)
		{
			Kraftwerk.gl.glEnable(3553);
			for(int i = 0; i < quadList.length; i++)
			{
				if((visibleSides&(1<<i))!=0)
				{
					quadList[i].draw(scale);
				}
			}
			Kraftwerk.gl.glDisable(3553);
		}
	}
}
