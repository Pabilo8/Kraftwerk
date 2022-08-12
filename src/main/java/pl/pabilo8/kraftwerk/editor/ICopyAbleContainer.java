package pl.pabilo8.kraftwerk.editor;

import com.google.gson.JsonArray;

import javax.annotation.Nonnull;

/**
 * @author Pabilo8
 * @since 11.12.2021
 */
public interface ICopyAbleContainer
{
	@Nonnull
	JsonArray handleCopy();

	@Nonnull
	default JsonArray handleCut()
	{
		handleRemove();
		return handleCopy();
	}

	void handleRemove();

	void handlePaste(JsonArray elements);
}
