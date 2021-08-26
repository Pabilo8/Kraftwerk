package pl;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Pabilo8
 * @since 16.08.2021
 *
 * It's my first time doing unit tests in Java ;-;
 * Not sure if this is a correct use case, but compiles faster so works for me
 */
public class ResourceUtils
{
	@Test
	void attemptGetResourceLocation() throws MalformedURLException
	{
		URL url = new URL("file:///Z:/JAVA/ImmersiveIntelligence/src/main/resources/assets/immersiveintelligence/textures/blocks/cloth_decoration/coil_rope_side.png");
		assertEquals("immersiveintelligence:blocks/cloth_decoration/coil_rope_side", pl.pabilo8.kraftwerk.utils.ResourceUtils.attemptGetResourceLocation(url));
	}
}
