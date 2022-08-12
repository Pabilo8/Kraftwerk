package pl.pabilo8.kraftwerk.editor.elements;

import com.github.weisj.darklaf.components.DynamicUI;
import com.google.gson.JsonObject;
import net.miginfocom.swing.MigLayout;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.gui.Icons;
import pl.pabilo8.kraftwerk.gui.panel.PanelTabProperties;
import pl.pabilo8.kraftwerk.gui.panel.PanelTabProperties.PropertiesEditorPanel;
import pl.pabilo8.kraftwerk.render.PositionTextureVertex;
import pl.pabilo8.kraftwerk.render.TexturedQuad;
import pl.pabilo8.kraftwerk.utils.ResourceUtils;
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
public class ModelElementBox extends ModelElement
{
	private static final Icon ICON = Icons.loadSVGIcon("elements/box");
	private static String[] FACES = {"element.face.n", "element.face.e", "element.face.s", "element.face.w", "element.face.t", "element.face.b"};
	public Vec3d size = new Vec3d(1, 1, 1);
	public Vec3d offset = Vec3d.ZERO;

	public ModelElementBox()
	{
		super();
		name = String.format("Box #%d", INSTANCE.currentProject.getPartCount());
		faces = new TexturedQuad[6];
		uv = new ModelUVConfig(true);

		forceRefresh();
	}

	protected void buildFaces()
	{
		PositionTextureVertex v_xyz = new PositionTextureVertex(offset, 0.0F, 0.0F);
		PositionTextureVertex v_Xyz = new PositionTextureVertex(offset.add(size.x, 0, 0), 0.0F, 1f);
		PositionTextureVertex v_XYz = new PositionTextureVertex(offset.add(size.x, size.y, 0), 1f, 1f);
		PositionTextureVertex v_xYz = new PositionTextureVertex(offset.add(0, size.y, 0), 1f, 0.0F);
		PositionTextureVertex v_xyZ = new PositionTextureVertex(offset.add(0, 0, size.z), 0.0F, 0.0F);
		PositionTextureVertex v_XyZ = new PositionTextureVertex(offset.add(size.x, 0, size.z), 0.0F, 1f);
		PositionTextureVertex v_XYZ = new PositionTextureVertex(offset.add(size.x, size.y, size.z), 1f, 1f);
		PositionTextureVertex v_xYZ = new PositionTextureVertex(offset.add(0, size.y, size.z), 1f, 0.0F);

		int texW = texture!=null?texture.width: 16;
		int texH = texture!=null?texture.height: 16;

		if(uv.scale==null)
			uv.scale = new Vec2f[]{Vec2f.ONE, Vec2f.ONE, Vec2f.ONE, Vec2f.ONE, Vec2f.ONE, Vec2f.ONE};
		if(uv.pos==null)
			uv.pos = new Vec2i[]{Vec2i.ZERO, Vec2i.ZERO, Vec2i.ZERO, Vec2i.ZERO, Vec2i.ZERO, Vec2i.ZERO};

		if(uv.isBoxUV)
		{
			Vec2i origin = uv.pos[0];

			this.faces[0] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_Xyz, v_XYz, v_XYZ}, (int)(origin.x+size.z+size.x), (int)(origin.y+size.z), (int)(origin.x+size.z+size.x+size.z), (int)(origin.y+size.z+size.y), texW, texH); //north
			this.faces[1] = new TexturedQuad(new PositionTextureVertex[]{v_xyz, v_xyZ, v_xYZ, v_xYz}, origin.x, (int)(origin.y+size.z), (int)(origin.x+size.z), (int)(origin.y+size.z+size.y), texW, texH); //south
			this.faces[2] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_xyZ, v_xyz, v_Xyz}, (int)(origin.x+size.z), origin.y, (int)(origin.x+size.z+size.x), (int)(origin.y+size.z), texW, texH); //bottom
			this.faces[3] = new TexturedQuad(new PositionTextureVertex[]{v_XYz, v_xYz, v_xYZ, v_XYZ}, (int)(origin.x+size.z+size.x), (int)(origin.y+size.z), (int)(origin.x+size.z+size.x+size.x), origin.y, texW, texH); //top
			this.faces[4] = new TexturedQuad(new PositionTextureVertex[]{v_Xyz, v_xyz, v_xYz, v_XYz}, (int)(origin.x+size.z), (int)(origin.y+size.z), (int)(origin.x+size.z+size.x), (int)(origin.y+size.z+size.y), texW, texH); //west
			this.faces[5] = new TexturedQuad(new PositionTextureVertex[]{v_xyZ, v_XyZ, v_XYZ, v_xYZ}, (int)(origin.x+size.z+size.x+size.z), (int)(origin.y+size.z), (int)(origin.x+size.z+size.x+size.z+size.x), (int)(origin.y+size.z+size.y), texW, texH); //east
		}
		else
		{
			this.faces[0] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_Xyz, v_XYz, v_XYZ}, uv.pos[0].x, uv.pos[0].y, (int)(size.z*uv.scale[0].x), (int)(size.y*uv.scale[0].y), texW, texH);
			this.faces[1] = new TexturedQuad(new PositionTextureVertex[]{v_xyz, v_xyZ, v_xYZ, v_xYz}, uv.pos[1].x, uv.pos[1].y, (int)(size.z*uv.scale[1].x), (int)(size.y*uv.scale[1].y), texW, texH);
			this.faces[2] = new TexturedQuad(new PositionTextureVertex[]{v_XyZ, v_xyZ, v_xyz, v_Xyz}, uv.pos[2].x, uv.pos[2].y, (int)(size.x*uv.scale[2].x), (int)(size.z*uv.scale[2].y), texW, texH);
			this.faces[3] = new TexturedQuad(new PositionTextureVertex[]{v_XYz, v_xYz, v_xYZ, v_XYZ}, uv.pos[3].x, uv.pos[3].y, (int)(size.x*uv.scale[3].x), (int)(size.z*uv.scale[3].y), texW, texH);
			this.faces[4] = new TexturedQuad(new PositionTextureVertex[]{v_Xyz, v_xyz, v_xYz, v_XYz}, uv.pos[4].x, uv.pos[4].y, (int)(size.x*uv.scale[4].x), (int)(size.y*uv.scale[4].y), texW, texH);
			this.faces[5] = new TexturedQuad(new PositionTextureVertex[]{v_xyZ, v_XyZ, v_XYZ, v_xYZ}, uv.pos[5].x, uv.pos[5].y, (int)(size.x*uv.scale[5].x), (int)(size.y*uv.scale[5].y), texW, texH);
		}
	}

	@Nonnull
	@Override
	public void fromJSON(JsonObject json)
	{
		super.fromJSON(json);
		if(json.has("size")&&json.get("size").isJsonArray())
			this.size = new Vec3d(json.get("size").getAsJsonArray());
		this.buildFaces();
	}

	@Nonnull
	@Override
	public JsonObject toJSON()
	{
		JsonObject json = super.toJSON();
		json.add("size", size.toJSON());
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
		return "box";
	}

	@Nonnull
	@Override
	public String getNameForFace(int id)
	{
		return ResourceUtils.translateString(Kraftwerk.res, FACES[id%6]);
	}

	@Nullable
	@Override
	public PropertiesEditorPanel getEditorPanel()
	{
		return new PropertiesEditorBoxPanel();
	}

	@Override
	public Icon getIcon()
	{
		return ICON;
	}

	@Override
	public void forceRefresh()
	{
		buildFaces();
	}

	protected static class PropertiesEditorBoxPanel extends PropertiesEditorPanel
	{
		JSpinner sizeX, sizeY, sizeZ;
		JSpinner offsetX, offsetY, offsetZ;

		public PropertiesEditorBoxPanel()
		{
			super("Dimensions");
		}

		@Override
		public void onInit(PanelTabProperties panelTabProperties, ModelElement currentlySelected, ModelElement[] selectedElements)
		{
			assert currentlySelected instanceof ModelElementBox;

			//arbitrary limit, remove if needed
			JPanel panel = new JPanel(new MigLayout("align left, fillx"));
			DynamicUI.withDynamic(panel, c -> {
				c.setBorder(BorderFactory.createTitledBorder("Dimensions"));
			});
			sizeX = panelTabProperties.addDoubleProperty(panel, "X", ((ModelElementBox)currentlySelected).size.x, 0, 1024);
			sizeY = panelTabProperties.addDoubleProperty(panel, "Y", ((ModelElementBox)currentlySelected).size.y, 0, 1024);
			sizeZ = panelTabProperties.addDoubleProperty(panel, "Z", ((ModelElementBox)currentlySelected).size.z, 0, 1024);
			this.add(panel, "growx, center, wrap");

			panel = new JPanel(new MigLayout("align left, fillx"));
			DynamicUI.withDynamic(panel, c -> {
				c.setBorder(BorderFactory.createTitledBorder("Offset"));
			});
			offsetX = panelTabProperties.addDoubleProperty(panel, "X", ((ModelElementBox)currentlySelected).offset.x, -1024, 1024);
			offsetY = panelTabProperties.addDoubleProperty(panel, "Y", ((ModelElementBox)currentlySelected).offset.y, -1024, 1024);
			offsetZ = panelTabProperties.addDoubleProperty(panel, "Z", ((ModelElementBox)currentlySelected).offset.z, -1024, 1024);
			this.add(panel, "growx, center, wrap");
		}

		@Override
		public void onFieldUpdate(ModelElement currentlySelected, ModelElement[] elements)
		{
			Vec3d diffSize = new Vec3d(((Double)sizeX.getValue()), ((Double)sizeY.getValue()), ((Double)sizeZ.getValue()))
					.subtract(((ModelElementBox)currentlySelected).size);
			Vec3d diffOffset = new Vec3d(((Double)offsetX.getValue()), ((Double)offsetY.getValue()), ((Double)offsetZ.getValue()))
					.subtract(((ModelElementBox)currentlySelected).offset);


			for(ModelElement e : elements)
			{
				if(e instanceof ModelElementBox)
				{
					((ModelElementBox)e).size = ((ModelElementBox)e).size.add(diffSize);
					((ModelElementBox)e).offset = ((ModelElementBox)e).offset.add(diffOffset);
					((ModelElementBox)e).buildFaces();
				}
			}
		}
	}
}
