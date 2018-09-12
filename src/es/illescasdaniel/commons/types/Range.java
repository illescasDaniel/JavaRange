package es.illescasdaniel.commons.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.val;

import javax.validation.constraints.NotNull;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Represents a view over a closed collection of values ranging from
 * {@link Range#first} to {@link Range#last}. It doesn't generate the intermediate values of
 * the range at any moment unless you call
 * {@link Range#toList()} or
 * {@link Range#toTreeSet()}.<BR>
 * <p>
 * It provides an easy way to tell (for example) if a given value is inside a
 * range, or use it instead of the classic {@code for (int i = 0; i < 10; i++)},
 * {@code for (long value: new Range(0,9))}.
 *
 * @author Daniel Illescas Romero (dillescas)
 * @see OpenRange
 *
 * <br><a href="https://github.com/illescasDaniel/JavaRange">GitHub link</a>
 * <br><a href="https://github.com/illescasDaniel/JavaRange/blob/master/LICENSE">MIT LICENSE</a>
 */
@ToString
@EqualsAndHashCode
public class Range implements Iterable<Long> {

	protected enum Order {
		ASCENDING, DESCENDING
	}

	@Getter
	protected final long first;
	@Getter
	protected final long last;
	@Getter
	protected final long stride;
	protected final Order order;

	public Range(final long first, final long last, final long stride) {

		if (first <= last) {
			this.order = Order.ASCENDING;
		} else {
			this.order = Order.DESCENDING;
		}
		this.first = first;
		this.last = last;
		this.stride = stride;
	}

	public Range(final long first, final long last) {
		this(first, last, 1);
	}

	@NotNull
	public static Range to(final long last) {
		return new Range(0, last);
	}

	@NotNull
	public static Range to(final long last, final long stride) {
		return new Range(0, last, stride);
	}

	public boolean contains(final long value) {
		val valueInRangeWithoutStride = Math.min(this.first, this.last) <= value
				&& value <= Math.max(this.first, this.last);
		return valueInRangeWithoutStride
				&& ((this.order == Order.ASCENDING ? (value - this.first) : (this.first - value)) % stride == 0);
	}

	public List<Long> toList() {
		return StreamSupport.stream(this.spliterator(), false).collect(Collectors.toList());
	}

	public TreeSet<Long> toTreeSet() {
		val elements = new TreeSet<Long>();
		this.forEach(elements::add);
		return elements;
	}

	public Range reversed() {
		return new Range(last, first, stride);
	}

	public long size() {
		val sizeWithoutStride = Math.max(this.first, this.last) - Math.min(this.first, this.last) + 1;
		return (long) Math.ceil((double) sizeWithoutStride / this.stride);
	}

	@Override
	public Iterator<Long> iterator() {
		return new Iterator<Long>() {

			private Long currentValue = null;

			@Override
			public boolean hasNext() {
				if (currentValue == null) {
					this.currentValue = first;
				} else {
					this.currentValue += ((order == Order.ASCENDING) ? 1 : -1) * stride;
				}

				return (order == Order.ASCENDING) ? (this.currentValue <= last) : (this.currentValue >= last);
			}

			@Override
			public Long next() {
				return this.currentValue;
			}
		};
	}
}
