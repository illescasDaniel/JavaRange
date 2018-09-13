package es.illescasdaniel.commons.types;

import javax.validation.constraints.NotNull;

/**
 * A {@link Range} which last possible value is one less than a
 * closed range (Range class). Mathematically: [initialValue, lastValue)
 *
 * @author Daniel Illescas Romero (dillescas)
 * @see Range
 *
 * <br><a href="https://github.com/illescasDaniel/JavaRange">GitHub link</a>
 * <br><a href="https://github.com/illescasDaniel/JavaRange/blob/master/LICENSE">MIT LICENSE</a>
 */
public class OpenRange extends Range {

	public OpenRange(long first, long until, long stride) {
		super(first, (first != until) ? (until + ((first <= until) ? -1 : 1)) : until, stride);
	}

	public OpenRange(long first, long until) {
		this(first, until, 1);
	}

	@NotNull
	public static OpenRange until(final long last) {
		return new OpenRange(0, last);
	}

	@NotNull
	public static OpenRange until(final long last, final long stride) {
		return new OpenRange(0, last, stride);
	}

	@Deprecated
	@NotNull
	public static OpenRange to(final long last) {
		return OpenRange.until(last);
	}

	@Deprecated
	@NotNull
	public static OpenRange to(final long last, final long stride) {
		return OpenRange.until(last, stride);
	}
}
