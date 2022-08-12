package pl.pabilo8.kraftwerk.editor.elements;

import com.github.weisj.darklaf.components.DynamicUI;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.miginfocom.swing.MigLayout;
import pl.pabilo8.kraftwerk.gui.Icons;
import pl.pabilo8.kraftwerk.gui.panel.PanelTabProperties;
import pl.pabilo8.kraftwerk.gui.panel.PanelTabProperties.PropertiesEditorPanel;
import pl.pabilo8.kraftwerk.render.PositionTextureVertex;
import pl.pabilo8.kraftwerk.render.TexturedQuad;
import pl.pabilo8.kraftwerk.utils.vector.Vec2f;
import pl.pabilo8.kraftwerk.utils.vector.Vec2i;
import pl.pabilo8.kraftwerk.utils.vector.Vec3d;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

import static pl.pabilo8.kraftwerk.Kraftwerk.INSTANCE;

/**
 * @author Pabilo8
 * @since 26.08.2021
 */
public class ModelElementShapebox extends ModelElementBox
{
	private static final Icon ICON = Icons.loadSVGIcon("elements/shapebox");
	public Vec3d[] corners = new Vec3d[]{Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO}; //x8

	public ModelElementShapebox()
	{
		super();
		name = String.format("Shapebox #%d", INSTANCE.currentProject.getPartCount());
	}

	@Override
	protected void buildFaces()
	{
		super.buildFaces();
		/*if(corners==null)
			corners = new Vec3d[]{Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO};

		Vec3d[] NORMALS = {
				Vec3d.ZERO,
				Vec3d.ZERO,
				Vec3d.ZERO,
				Vec3d.ZERO,
				Vec3d.ZERO,
				Vec3d.ZERO,
				Vec3d.ZERO,
				Vec3d.ZERO,
		};

		PositionTextureVertex v_xyz = new PositionTextureVertex(corners[0].scale(NORMALS[0]).add(offset), 0.0F, 0.0F);
		PositionTextureVertex v_Xyz = new PositionTextureVertex(corners[1].scale(NORMALS[1]).add(offset).add(size.x, 0, 0), 0.0F, 1f);
		PositionTextureVertex v_XYz = new PositionTextureVertex(corners[2].scale(NORMALS[2]).add(offset).add(size.x, size.y, 0), 1f, 1f);
		PositionTextureVertex v_xYz = new PositionTextureVertex(corners[3].scale(NORMALS[3]).add(offset).add(0, size.y, 0), 1f, 0.0F);
		PositionTextureVertex v_xyZ = new PositionTextureVertex(corners[4].scale(NORMALS[4]).add(offset).add(0, 0, size.z), 0.0F, 0.0F);
		PositionTextureVertex v_XyZ = new PositionTextureVertex(corners[5].scale(NORMALS[5]).add(offset).add(size.x, 0, size.z), 0.0F, 1f);
		PositionTextureVertex v_XYZ = new PositionTextureVertex(corners[6].scale(NORMALS[6]).add(offset).add(size.x, size.y, size.z), 1f, 1f);
		PositionTextureVertex v_xYZ = new PositionTextureVertex(corners[7].scale(NORMALS[7]).add(offset).add(0, size.y, size.z), 1f, 0.0F);

		int texW = texture!=null?texture.width: 16;
		int texH = texture!=null?texture.height: 16;

		if(uv.scale==null)
			uv.scale = new Vec2f[]{Vec2f.ONE, Vec2f.ONE, Vec2f.ONE, Vec2f.ONE, Vec2f.ONE, Vec2f.ONE};
		if(uv.pos==null)
			uv.pos = new Vec2i[]{Vec2i.ZERO, Vec2i.ZERO, Vec2i.ZERO, Vec2i.ZERO, Vec2i.ZERO, Vec2i.ZERO};

		if(uv.isBoxUV)
		{
			Vec2i origin = uv.pos[0];
			this.faces[0] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_Xyz, v_XYz, v_XYZ}, (int)(origin.x+size.z+size.x), (int)(origin.y+size.z), (int)(origin.x+size.z+size.x+size.z), (int)(origin.y+size.z+size.y), texW, texH);
			this.faces[1] = new TexturedQuad(new PositionTextureVertex[]{v_xyz, v_xyZ, v_xYZ, v_xYz}, origin.x, (int)(origin.y+size.z), (int)(origin.x+size.z), (int)(origin.y+size.z+size.y), texW, texH);
			this.faces[2] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_xyZ, v_xyz, v_Xyz}, (int)(origin.x+size.z), origin.y, (int)(origin.x+size.z+size.x), (int)(origin.y+size.z), texW, texH);
			this.faces[3] = new TexturedQuad(new PositionTextureVertex[]{v_XYz, v_xYz, v_xYZ, v_XYZ}, (int)(origin.x+size.z+size.x), (int)(origin.y+size.z), (int)(origin.x+size.z+size.x+size.x), origin.y, texW, texH);
			this.faces[4] = new TexturedQuad(new PositionTextureVertex[]{v_Xyz, v_xyz, v_xYz, v_XYz}, (int)(origin.x+size.z), (int)(origin.y+size.z), (int)(origin.x+size.z+size.x), (int)(origin.y+size.z+size.y), texW, texH);
			this.faces[5] = new TexturedQuad(new PositionTextureVertex[]{v_xyZ, v_XyZ, v_XYZ, v_xYZ}, (int)(origin.x+size.z+size.x+size.z), (int)(origin.y+size.z), (int)(origin.x+size.z+size.x+size.z+size.x), (int)(origin.y+size.z+size.y), texW, texH);
		}
		else
		{
			this.faces[0] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_Xyz, v_XYz, v_XYZ}, uv.pos[0].x, uv.pos[0].y, (int)(size.z*uv.scale[0].x), (int)(size.y*uv.scale[0].y), texW, texH);
			this.faces[1] = new TexturedQuad(new PositionTextureVertex[]{v_xyz, v_xyZ, v_xYZ, v_xYz}, uv.pos[1].x, uv.pos[1].y, (int)(size.z*uv.scale[1].x), (int)(size.y*uv.scale[1].y), texW, texH);
			this.faces[2] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_xyZ, v_xyz, v_Xyz}, uv.pos[2].x, uv.pos[2].y, (int)(size.x*uv.scale[2].x), (int)(size.z*uv.scale[2].y), texW, texH);
			this.faces[3] = new TexturedQuad(new PositionTextureVertex[]{v_XYz, v_xYz, v_xYZ, v_XYZ}, uv.pos[3].x, uv.pos[3].y, (int)(size.x*uv.scale[3].x), (int)(size.z*uv.scale[3].y), texW, texH);
			this.faces[4] = new TexturedQuad(new PositionTextureVertex[]{v_Xyz, v_xyz, v_xYz, v_XYz}, uv.pos[4].x, uv.pos[4].y, (int)(size.x*uv.scale[4].x), (int)(size.y*uv.scale[4].y), texW, texH);
			this.faces[5] = new TexturedQuad(new PositionTextureVertex[]{v_xyZ, v_XyZ, v_XYZ, v_xYZ}, uv.pos[5].x, uv.pos[5].y, (int)(size.x*uv.scale[5].x), (int)(size.y*uv.scale[5].y), texW, texH);
		}*/
	}

	@Nonnull
	@Override
	public void fromJSON(JsonObject json)
	{
		if(json.has("corners"))
		{
			int i = 0;
			for(JsonElement element : json.get("corners").getAsJsonArray())
			{
				corners[i%8] = new Vec3d(element.getAsJsonArray());
				i++;
			}
		}

		super.fromJSON(json);
	}

	@Nonnull
	@Override
	public JsonObject toJSON()
	{
		JsonObject json = super.toJSON();
		JsonArray array = new JsonArray();
		for(Vec3d corner : corners)
			array.add(corner.toJSON());
		json.add("corners", array);
		return json;
	}

	@Override
	public boolean isCuboid()
	{
		return false;
	}

	@Override
	public String getTypeName()
	{
		return "shapebox";
	}

	@Override
	public Icon getIcon()
	{
		return ICON;
	}

	@Nullable
	@Override
	public PropertiesEditorPanel getEditorPanel()
	{
		return new PropertiesEditorShapeboxPanel();
	}

	private static class PropertiesEditorShapeboxPanel extends PropertiesEditorBoxPanel
	{
		JSpinner[][] values;

		@Override
		public void onInit(PanelTabProperties panelTabProperties, ModelElement currentlySelected, ModelElement[] selectedElements)
		{
			super.onInit(panelTabProperties, currentlySelected, selectedElements);

			assert currentlySelected instanceof ModelElementShapebox;
			values = new JSpinner[8][3];

			JPanel panelCorners = new JPanel(new MigLayout("align left, fillx"));
			DynamicUI.withDynamic(panelCorners, c -> {c.setBorder(BorderFactory.createTitledBorder("Corners"));});
			for(int i = 0; i < 8; i++)
			{
				JPanel panel = new JPanel(new MigLayout("align left, fillx"));
				values[i][0] = panelTabProperties.addDoubleProperty(panel, "X", ((ModelElementShapebox)currentlySelected).corners[i].x, 0, 1024);
				values[i][1] = panelTabProperties.addDoubleProperty(panel, "Y", ((ModelElementShapebox)currentlySelected).corners[i].y, 0, 1024);
				values[i][2] = panelTabProperties.addDoubleProperty(panel, "Z", ((ModelElementShapebox)currentlySelected).corners[i].z, 0, 1024);
				panelCorners.add(panel, "growx, center, wrap");
			}
			this.add(panelCorners,"growx, center, wrap");
		}

		@Override
		public void onFieldUpdate(ModelElement currentlySelected, ModelElement[] elements)
		{
			super.onFieldUpdate(currentlySelected, elements);

			if(values!=null)
			{
				for(int i = 0; i < values.length; i++)
					for(int j = 0; j < 3; j++)
					{
						//elements.
					}
			}
		}
	}
}
