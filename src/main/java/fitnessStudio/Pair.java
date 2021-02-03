package fitnessStudio;

import java.io.Serializable;

public class Pair<L,R> implements Serializable {

	private final L left;
	private final R right;

	public Pair(L left, R right) {
		assert left != null;
		assert right != null;

		this.left = left;
		this.right = right;
	}

	public L getFirst() { return left; }
	public R getSecond() { return right; }

	@Override
	public int hashCode() { return left.hashCode() ^ right.hashCode(); }

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair)) {
			return false;
		}
		Pair pairo = (Pair) o;
		if(this.left == null) {
			return true;
		}
		if(this.right == null) {
			return false;
		}
	
		return this.left.equals(pairo.getFirst()) &&
				this.right.equals(pairo.getSecond());
	}

}