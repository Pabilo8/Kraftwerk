package pl.pabilo8.kraftwerk.gui.panel;

import com.github.weisj.darklaf.ui.tabbedpane.DarkTabbedPaneUI;
import com.github.weisj.darklaf.util.Alignment;
import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.elements.ModelElement;
import pl.pabilo8.kraftwerk.gui.Icons;
import pl.pabilo8.kraftwerk.render.TexturedQuad;
import pl.pabilo8.kraftwerk.utils.ModelEditorUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * @author Pabilo8
 * @since 26.08.2021
 */
public class PanelTabUV extends PanelTab
{
	JTabbedPane pane = null;

	public PanelTabUV()
	{
		super("tabs.uv", Icons.uvIcon, 312, Alignment.EAST, true, false);
	}

	@Override
	public void init()
	{

	}

	@Override
	public void refresh()
	{
		PanelTabProperties properties = Kraftwerk.INSTANCE.panelPartProperties;
		this.removeAll();

		pane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

		if(properties.currentlySelected!=null)
		{
			pane.setEnabled(!properties.currentlySelected.uv.isBoxUV);
			for(int i = 0; i < properties.currentlySelected.faces.length; i++)
			{
				String name = properties.currentlySelected.getNameForFace(i);

				if(properties.currentlySelected.texture!=null)
				{
					pane.insertTab(name, null, new DrawStuff(properties.currentlySelected.uv.isBoxUV?-1: i), name, i);
				}

				JPanel check = new JPanel();

				JCheckBox field = new JCheckBox("Box UV");
				field.setHorizontalAlignment(JCheckBox.LEFT);
				field.setMinimumSize(new Dimension(32, 24));
				field.setSelected(properties.currentlySelected.uv.isBoxUV);
				check.add(field, "growx, push");
				pane.putClientProperty(DarkTabbedPaneUI.KEY_TRAILING_COMP, check);

				Kraftwerk.INSTANCE.localizeList.add(pane);
			}
		}

		this.add(pane, "growx, center, wrap");

		SwingUtilities.invokeLater(this::validate);
		SwingUtilities.invokeLater(this::repaint);
	}

	private static class DrawStuff extends JComponent
	{
		private final int quad;

		public DrawStuff(int quad)
		{
			this.quad = quad;
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
					RenderingHints.VALUE_STROKE_PURE);
			ModelElement element = Kraftwerk.INSTANCE.panelPartProperties.currentlySelected;

			float proportion = element.texture.height/(float)element.texture.width;
			int xx = Math.min(getWidth(), getHeight());
			int yy = (int)(xx*proportion);

			g2.drawImage(element.texture.loadedTexture.getBufferedImage(), 0, 0, xx, yy, (img, infoflags, x, y, width, height) -> false);

			int i = 0;
			for(TexturedQuad face : element.faces)
			{
				//Vec2i pos = element.uv.pos[i];
				if(quad==i)
					g2.setPaint(Color.WHITE);
				else if(quad==-1)
					g2.setPaint(ModelEditorUtils.FACE_COLORS[i%6]);
				else
					g2.setPaint(ModelEditorUtils.FACE_COLORS[i%6].darker());


				g2.drawPolygon(
						Arrays.stream(face.vertexPositions).mapToInt(p -> (int)(p.u*xx)).toArray(),
						Arrays.stream(face.vertexPositions).mapToInt(p -> (int)(p.v*yy)).toArray(),
						face.vertexPositions.length
				);
				i++;
			}
		}
	}
}
