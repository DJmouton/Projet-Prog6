package Structure;

import Modele.Coup;

import java.util.ArrayList;

public class Tree {
	private int value;
	private Coup coup;
	private ArrayList<Tree> children;

	public Tree(int value, Coup coup) {
		this.value = value;
		this.coup = coup;
		this.children = new ArrayList<>();
	}

	public int getValue() {
		return value;
	}

	public Coup getCoup() {
		return coup;
	}

	public ArrayList<Tree> getChildren() {
		return children;
	}

	public void addChildren(Tree tree) {
		this.children.add(tree);
	}

	public void setValue(int value) {
		this.value = value;
	}
}
